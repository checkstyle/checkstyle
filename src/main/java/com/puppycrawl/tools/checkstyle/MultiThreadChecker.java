////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileSetCheck;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * A multithread version of AbstractChecker.
 */
public class MultiThreadChecker extends AbstractChecker {
    /** A list of all fileset checks for the current thread. */
    private static final ThreadLocal<List<FileSetCheck>> THREAD_LOCAL_FILESET_CHECK_CLONES =
            new ThreadLocal<>();

    /** A set of all fileset clones. */
    private final Set<FileSetCheck> fileSetCheckClones = ConcurrentHashMap.newKeySet();

    /** The checks executor. */
    private ExecutorService executor;

    @Override
    public int process(List<File> files) throws CheckstyleException {
        if (executor == null) {
            final DefaultConfiguration configuration = (DefaultConfiguration) getConfiguration();
            final ThreadModeSettings threadModeSettings =
                    configuration.getThreadModeSettings();
            final int threadsNumber = threadModeSettings.getCheckerThreadsNumber();
            executor = Executors.newFixedThreadPool(threadsNumber);
        }
        return super.process(files);
    }

    @Override
    protected void processFilteredFiles(List<File> filteredFiles) throws CheckstyleException {
        final List<Callable<CheckerExecutionResult>> tasks = filteredFiles.stream()
            .map(this::createProcessFileCallable)
            .collect(Collectors.toList());

        try {
            runAllTasks(tasks);
        }
        catch (InterruptedException | ExecutionException ex) {
            throw new CheckstyleException("Unable to execute checkstyle tasks", ex);
        }
        finally {
            executor.shutdownNow();
        }
    }

    @Override
    protected void finishFileSetChecksProcessing() {
        super.finishFileSetChecksProcessing();
        final Set<FileSetCheck> originalFileSetChecks = new HashSet<>(getFileSetChecks());
        for (FileSetCheck clone : fileSetCheckClones) {
            if (!originalFileSetChecks.contains(clone)) {
                clone.finishProcessing();
            }
        }
    }

    @Override
    protected void destroyFileSetChecks() {
        super.destroyFileSetChecks();
        final Set<FileSetCheck> originalFileSetChecks = new HashSet<>(getFileSetChecks());
        for (FileSetCheck clone : fileSetCheckClones) {
            if (!originalFileSetChecks.contains(clone)) {
                clone.destroy();
            }
        }
    }

    /**
     * Runs all the given tasks.
     * @param tasks A list of tasks to run.
     * @throws InterruptedException see {@link MultiThreadChecker#finishTask(Future)}.
     * @throws CheckstyleException see {@link MultiThreadChecker#finishTask(Future)}.
     * @throws ExecutionException see {@link MultiThreadChecker#finishTask(Future)}.
     */
    private void runAllTasks(List<Callable<CheckerExecutionResult>> tasks)
            throws InterruptedException, CheckstyleException, ExecutionException {

        final List<Future<CheckerExecutionResult>> futures = tasks.stream()
                .map(task -> executor.submit(task))
                .collect(Collectors.toList());

        for (Future<CheckerExecutionResult> future : futures) {
            finishTask(future);
        }
    }

    /**
     * Finishes the given future.
     * @param future A future to finish.
     * @throws InterruptedException might be thrown from {@link Future#get()}.
     * @throws CheckstyleException might be thrown from a task.
     * @throws ExecutionException might be thrown from {@link Future#get()}.
     */
    private static void finishTask(Future<CheckerExecutionResult> future)
            throws InterruptedException, CheckstyleException, ExecutionException {

        final CheckerExecutionResult result = future.get();
        if (result.getThrownException() != null) {
            throw result.getThrownException();
        }
    }

    /**
     * Creates a callable for processing a particular file.
     * @param file A file to process.
     * @return A callable which processes the given file.
     */
    private Callable<CheckerExecutionResult> createProcessFileCallable(File file) {
        return () -> {
            CheckerExecutionResult result;
            try {
                fireEventsAndProcessFile(file);
                result = new CheckerExecutionResult();
            }
            catch (CheckstyleException ex) {
                result = new CheckerExecutionResult(ex);
            }

            return result;
        };
    }

    @Override
    protected SortedSet<LocalizedMessage> processFile(File file) throws CheckstyleException {
        List<FileSetCheck> clones = THREAD_LOCAL_FILESET_CHECK_CLONES.get();
        if (clones == null) {
            clones = CheckCloneService.cloneFileSetChecks(getFileSetChecks());
            THREAD_LOCAL_FILESET_CHECK_CLONES.set(clones);
            fileSetCheckClones.addAll(clones);
        }

        return doProcessFile(file, clones);
    }

    /**
     * A checker execution result. Contains all information about executed task -
     * a succesful execution result or thrown exception.
     * @author Andrew Kuchev.
     */
    private static final class CheckerExecutionResult {
        /** The exception, thrown while processing the task. */
        private final CheckstyleException thrownException;

        /**
         * Creates the execution result.
         */
        CheckerExecutionResult() {
            this(null);
        }

        /**
         * Creates the execution result.
         * @param thrownException The exception, thrown while processing the file.
         */
        CheckerExecutionResult(CheckstyleException thrownException) {
            this.thrownException = thrownException;
        }

        /**
         * Returns thrown exception.
         * @return thrown exception.
         */
        public CheckstyleException getThrownException() {
            return thrownException;
        }
    }
}

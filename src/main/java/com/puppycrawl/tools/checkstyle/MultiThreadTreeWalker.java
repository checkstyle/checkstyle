package com.puppycrawl.tools.checkstyle;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;

/**
 * The multi thread version of {@link AbstractTreeWalker}.
 */
public final class MultiThreadTreeWalker extends AbstractTreeWalker {
    /** The service to execute checks. */
    private ExecutorService executor;

    /** The current file contents. */
    private FileContents fileContents;

    @Override
    public void init() {
        super.init();

        if (executor == null) {
            final DefaultConfiguration configuration = (DefaultConfiguration) getConfiguration();
            final ThreadModeSettings threadModeSettings =
                    configuration.getThreadModeSettings();
            final int threadsNumber = threadModeSettings.getTreeWalkerThreadsNumber();
            executor = Executors.newFixedThreadPool(threadsNumber);
        }
    }

    @Override
    protected void beforeNotifyBegin(DetailAST rootAST, FileContents contents) {
        super.beforeNotifyBegin(rootAST, contents);
        fileContents = contents;
    }

    @Override
    protected void doVisitToken(DetailAST ast, Collection<AbstractCheck> visitors) {
        final List<Future<TreeWalkerExecutionResult>> taskFutures = visitors.stream()
                .map(x -> createDoVisitTokenCallable(ast, x))
                .map(executor::submit)
                .collect(Collectors.toList());

        for (Future<TreeWalkerExecutionResult> future : taskFutures) {
            final TreeWalkerExecutionResult checksExecutionResult;
            try {
                checksExecutionResult = future.get();
            }
            catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

            if (checksExecutionResult.getThrownException() != null) {
                throw new RuntimeException("Unable to finish task",
                        checksExecutionResult.getThrownException());
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        executor.shutdownNow();
    }

    /**
     * Creates the callable for visiting a token.
     * @param ast token to visit.
     * @param check the token visitor.
     * @return the callable for visiting a token.
     */
    private Callable<TreeWalkerExecutionResult> createDoVisitTokenCallable(
            DetailAST ast, AbstractCheck check) {
        return () -> {
            TreeWalkerExecutionResult result;
            try {
                // fileContents has thread local access, so that we have to set it,
                // while starting new thread
                check.setFileContents(fileContents);
                check.visitToken(ast);
                result = new TreeWalkerExecutionResult();
            }
            catch (Exception e) {
                result = new TreeWalkerExecutionResult(e);
            }

            return result;
        };
    }

    /**
     * The three walker execution result.
     */
    private static final class TreeWalkerExecutionResult {
        /** The exception, thrown while executing a task. */
        private final Exception thrownException;

        /**
         * Creates the succesful execution resut.
         */
        public TreeWalkerExecutionResult() {
            this(null);
        }

        /**
         * Creates the failure execution result.
         * @param thrownException the exception, thrown by a task.
         */
        public TreeWalkerExecutionResult(Exception thrownException) {
            this.thrownException = thrownException;
        }

        /**
         * Returns the thrown exception.
         * @return thrown exception.
         */
        public Exception getThrownException() {
            return thrownException;
        }
    }
}

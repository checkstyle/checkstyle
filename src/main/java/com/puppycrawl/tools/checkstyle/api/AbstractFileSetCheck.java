////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import java.io.File;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Provides common functionality for many FileSetChecks.
 *
 * @noinspection NoopMethodInAbstractClass
 */
public abstract class AbstractFileSetCheck
    extends AbstractViolationReporter
    implements FileSetCheck {

    /**
     * The check context.
     *
     * @noinspection ThreadLocalNotStaticFinal
     */
    private final ThreadLocal<FileContext> context = ThreadLocal.withInitial(FileContext::new);

    /** The dispatcher errors are fired to. */
    private MessageDispatcher messageDispatcher;

    /** Specify the file type extension of files to process. */
    private String[] fileExtensions = CommonUtil.EMPTY_STRING_ARRAY;

    /** The tab width for column reporting. */
    private int tabWidth = CommonUtil.DEFAULT_TAB_WIDTH;

    /**
     * Called to process a file that matches the specified file extensions.
     *
     * @param file the file to be processed
     * @param fileText the contents of the file.
     * @throws CheckstyleException if error condition within Checkstyle occurs.
     */
    protected abstract void processFiltered(File file, FileText fileText)
            throws CheckstyleException;

    @Override
    public void init() {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public void destroy() {
        context.remove();
    }

    @Override
    public void beginProcessing(String charset) {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public final SortedSet<LocalizedMessage> process(File file, FileText fileText)
            throws CheckstyleException {
        final SortedSet<LocalizedMessage> messages = context.get().messages;
        context.get().fileContents = new FileContents(fileText);
        messages.clear();
        // Process only what interested in
        if (CommonUtil.matchesFileExtension(file, fileExtensions)) {
            processFiltered(file, fileText);
        }
        final SortedSet<LocalizedMessage> result = new TreeSet<>(messages);
        messages.clear();
        return result;
    }

    @Override
    public void finishProcessing() {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public final void setMessageDispatcher(MessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
    }

    /**
     * A message dispatcher is used to fire violation messages to
     * interested audit listeners.
     *
     * @return the current MessageDispatcher.
     */
    protected final MessageDispatcher getMessageDispatcher() {
        return messageDispatcher;
    }

    /**
     * Returns the sorted set of {@link LocalizedMessage}.
     *
     * @return the sorted set of {@link LocalizedMessage}.
     */
    public SortedSet<LocalizedMessage> getMessages() {
        return new TreeSet<>(context.get().messages);
    }

    /**
     * Set the file contents associated with the tree.
     *
     * @param contents the manager
     */
    public final void setFileContents(FileContents contents) {
        context.get().fileContents = contents;
    }

    /**
     * Returns the file contents associated with the file.
     *
     * @return the file contents
     */
    protected final FileContents getFileContents() {
        return context.get().fileContents;
    }

    /**
     * Makes copy of file extensions and returns them.
     *
     * @return file extensions that identify the files that pass the
     *     filter of this FileSetCheck.
     */
    public String[] getFileExtensions() {
        return Arrays.copyOf(fileExtensions, fileExtensions.length);
    }

    /**
     * Setter to specify the file type extension of files to process.
     *
     * @param extensions the set of file extensions. A missing
     *         initial '.' character of an extension is automatically added.
     * @throws IllegalArgumentException is argument is null
     */
    public final void setFileExtensions(String... extensions) {
        if (extensions == null) {
            throw new IllegalArgumentException("Extensions array can not be null");
        }

        fileExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            final String extension = extensions[i];
            if (CommonUtil.startsWithChar(extension, '.')) {
                fileExtensions[i] = extension;
            }
            else {
                fileExtensions[i] = "." + extension;
            }
        }
    }

    /**
     * Get tab width to report audit events with.
     *
     * @return the tab width to report audit events with
     */
    protected final int getTabWidth() {
        return tabWidth;
    }

    /**
     * Set the tab width to report audit events with.
     *
     * @param tabWidth an {@code int} value
     */
    public final void setTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
    }

    /**
     * Adds the sorted set of {@link LocalizedMessage} to the message collector.
     *
     * @param messages the sorted set of {@link LocalizedMessage}.
     */
    protected void addMessages(SortedSet<LocalizedMessage> messages) {
        context.get().messages.addAll(messages);
    }

    @Override
    public final void log(int line, String key, Object... args) {
        context.get().messages.add(
                new LocalizedMessage(line,
                        getMessageBundle(),
                        key,
                        args,
                        getSeverityLevel(),
                        getId(),
                        getClass(),
                        getCustomMessages().get(key)));
    }

    @Override
    public final void log(int lineNo, int colNo, String key,
            Object... args) {
        final int col = 1 + CommonUtil.lengthExpandedTabs(
                context.get().fileContents.getLine(lineNo - 1), colNo, tabWidth);
        context.get().messages.add(
                new LocalizedMessage(lineNo,
                        col,
                        getMessageBundle(),
                        key,
                        args,
                        getSeverityLevel(),
                        getId(),
                        getClass(),
                        getCustomMessages().get(key)));
    }

    /**
     * Notify all listeners about the errors in a file.
     * Calls {@code MessageDispatcher.fireErrors()} with
     * all logged errors and then clears errors' list.
     *
     * @param fileName the audited file
     */
    protected final void fireErrors(String fileName) {
        final SortedSet<LocalizedMessage> errors = new TreeSet<>(context.get().messages);
        context.get().messages.clear();
        messageDispatcher.fireErrors(fileName, errors);
    }

    /**
     * The actual context holder.
     */
    private static class FileContext {

        /** The sorted set for collecting messages. */
        private final SortedSet<LocalizedMessage> messages = new TreeSet<>();

        /** The current file contents. */
        private FileContents fileContents;

    }

}

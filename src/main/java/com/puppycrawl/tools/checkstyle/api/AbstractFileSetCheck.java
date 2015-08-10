////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import java.util.List;
import java.util.SortedSet;

import com.puppycrawl.tools.checkstyle.Utils;

/**
 * Provides common functionality for many FileSetChecks.
 *
 * @author lkuehne
 * @author oliver
 */
public abstract class AbstractFileSetCheck
    extends AbstractViolationReporter
    implements FileSetCheck {
    /** The dispatcher errors are fired to. */
    private MessageDispatcher messageDispatcher;

    /** the file extensions that are accepted by this filter */
    private String[] fileExtensions = {};

    /** collects the error messages */
    private final LocalizedMessages messages = new LocalizedMessages();

    /**
     * Called to process a file that matches the specified file extensions.
     * @param file the file to be processed
     * @param lines an immutable list of the contents of the file.
     */
    protected abstract void processFiltered(File file, List<String> lines);

    @Override
    public void init() {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public void destroy() {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public void beginProcessing(String charset) {
        // No code by default, should be overridden only by demand at subclasses
    }

    @Override
    public final SortedSet<LocalizedMessage> process(File file,
                                                   List<String> lines) {
        getMessageCollector().reset();
        // Process only what interested in
        if (Utils.fileExtensionMatches(file, fileExtensions)) {
            processFiltered(file, lines);
        }
        return getMessageCollector().getMessages();
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
     * @return file extensions that identify the files that pass the
     * filter of this FileSetCheck.
     */
    public String[] getFileExtensions() {
        return Arrays.copyOf(fileExtensions, fileExtensions.length);
    }

    /**
     * Sets the file extensions that identify the files that pass the
     * filter of this FileSetCheck.
     * @param extensions the set of file extensions. A missing
     *         initial '.' character of an extension is automatically added.
     * @throws IllegalArgumentException is arument is null
     */
    public final void setFileExtensions(String... extensions) {
        if (extensions == null) {
            throw new IllegalArgumentException("Extensions array can not be null");
        }

        fileExtensions = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            final String extension = extensions[i];
            if (Utils.startsWithChar(extension, '.')) {
                fileExtensions[i] = extension;
            }
            else {
                fileExtensions[i] = "." + extension;
            }
        }
    }

    /**
     * Returns the collector for violation messages.
     * Subclasses can use the collector to find out the violation
     * messages to fire via the message dispatcher.
     *
     * @return the collector for localized messages.
     */
    protected final LocalizedMessages getMessageCollector() {
        return messages;
    }

    @Override
    public final void log(int line, String key, Object... args) {
        log(line, 0, key, args);
    }

    @Override
    public final void log(int lineNo, int colNo, String key,
            Object... args) {
        getMessageCollector().add(
            new LocalizedMessage(lineNo,
                                 colNo,
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
     * all logged errors and than clears errors' list.
     * @param fileName the audited file
     */
    protected final void fireErrors(String fileName) {
        final SortedSet<LocalizedMessage> errors = getMessageCollector()
                .getMessages();
        getMessageCollector().reset();
        getMessageDispatcher().fireErrors(fileName, errors);
    }
}

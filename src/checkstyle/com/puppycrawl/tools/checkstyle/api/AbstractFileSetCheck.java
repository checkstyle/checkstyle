////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import java.util.List;
import java.util.TreeSet;

/**
 * Provides common functionality for many FileSetChecks.
 *
 * @author lkuehne
 * @author oliver
 */
public abstract class AbstractFileSetCheck
    extends AbstractViolationReporter
    implements FileSetCheck
{
    /** The dispatcher errors are fired to. */
    private MessageDispatcher mDispatcher;

    /** the file extensions that are accepted by this filter */
    private String[] mFileExtensions = {};

    /** collects the error messages */
    private final LocalizedMessages mMessages = new LocalizedMessages();

    /**
     * Called to process a file that matches the specified file extensions.
     * @param aFile the file to be processed
     * @param aLines an immutable list of the contents of the file.
     */
    protected abstract void processFiltered(File aFile, List<String> aLines);

    /** {@inheritDoc} */
    public void init()
    {
    }

    /** {@inheritDoc} */
    public void destroy()
    {
    }

    /** {@inheritDoc} */
    public void beginProcessing(String aCharset)
    {
    }

    /** {@inheritDoc} */
    public final TreeSet<LocalizedMessage> process(File aFile,
                                                   List<String> aLines)
    {
        getMessageCollector().reset();
        // Process only what interested in
        if (fileExtensionMatches(aFile)) {
            processFiltered(aFile, aLines);
        }
        return getMessageCollector().getMessages();
    }

    /** {@inheritDoc} */
    public void finishProcessing()
    {
    }

    /** {@inheritDoc} */
    public final void setMessageDispatcher(MessageDispatcher aDispatcher)
    {
        mDispatcher = aDispatcher;
    }

    /**
     * A message dispatcher is used to fire violation messages to
     * interested audit listeners.
     *
     * @return the current MessageDispatcher.
     */
    protected final MessageDispatcher getMessageDispatcher()
    {
        return mDispatcher;
    }

    /**
     * Sets the file extensions that identify the files that pass the
     * filter of this FileSetCheck.
     * @param aExtensions the set of file extensions. A missing
     * initial '.' character of an extension is automatically added.
     */
    public final void setFileExtensions(String[] aExtensions)
    {
        if (aExtensions == null) {
            mFileExtensions = null;
            return;
        }

        mFileExtensions = new String[aExtensions.length];
        for (int i = 0; i < aExtensions.length; i++) {
            final String extension = aExtensions[i];
            if (extension.startsWith(".")) {
                mFileExtensions[i] = extension;
            }
            else {
                mFileExtensions[i] = "." + extension;
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
    protected final LocalizedMessages getMessageCollector()
    {
        return mMessages;
    }

    @Override
    public final void log(int aLine, String aKey, Object... aArgs)
    {
        log(aLine, 0, aKey, aArgs);
    }

    @Override
    public final void log(int aLineNo, int aColNo, String aKey,
            Object... aArgs)
    {
        getMessageCollector().add(
            new LocalizedMessage(aLineNo,
                                 aColNo,
                                 getMessageBundle(),
                                 aKey,
                                 aArgs,
                                 getSeverityLevel(),
                                 getId(),
                                 this.getClass(),
                                 this.getCustomMessages().get(aKey)));
    }

    /**
     * Notify all listeners about the errors in a file.
     * Calls <code>MessageDispatcher.fireErrors()</code> with
     * all logged errors and than clears errors' list.
     * @param aFileName the audited file
     */
    protected final void fireErrors(String aFileName)
    {
        final TreeSet<LocalizedMessage> errors = getMessageCollector()
                .getMessages();
        getMessageCollector().reset();
        getMessageDispatcher().fireErrors(aFileName, errors);
    }

    /**
     * Returns whether the file extension matches what we are meant to
     * process.
     * @param aFile the file to be checked.
     * @return whether there is a match.
     */
    private boolean fileExtensionMatches(File aFile)
    {
        if ((null == mFileExtensions) || (mFileExtensions.length == 0)) {
            return true;
        }

        // normalize extensions so all of them have a leading dot
        final String[] withDotExtensions = new String[mFileExtensions.length];
        for (int i = 0; i < mFileExtensions.length; i++) {
            final String extension = mFileExtensions[i];
            if (extension.startsWith(".")) {
                withDotExtensions[i] = extension;
            }
            else {
                withDotExtensions[i] = "." + extension;
            }
        }

        final String fileName = aFile.getName();
        for (final String fileExtension : withDotExtensions) {
            if (fileName.endsWith(fileExtension)) {
                return true;
            }
        }

        return false;
    }
}

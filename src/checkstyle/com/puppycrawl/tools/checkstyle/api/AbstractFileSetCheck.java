////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
import java.io.UnsupportedEncodingException;

/**
 * Provides common functionality for many FileSetChecks.
 *
 * @author lkuehne
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

    /** Name of a charset */
    private String mCharset = System.getProperty("file.encoding", "UTF-8");

    /** @see com.puppycrawl.tools.checkstyle.api.FileSetCheck */
    public void destroy()
    {
    }

    /** @return the name of the charset */
    public String getCharset()
    {
        return mCharset;
    }

    /**
     * Sets a named charset.
     * @param aCharset the name of a charset
     * @throws UnsupportedEncodingException if aCharset is unsupported.
     */
    public void setCharset(String aCharset)
        throws UnsupportedEncodingException
    {
        // TODO: This is a hack to check that aCharset is supported.
        // TODO: Find a better way in Java 1.3
        try {
            new String(new byte[] {}, aCharset);
        }
        catch (final UnsupportedEncodingException es) {
            final String message = "unsupported charset: " + es.getMessage();
            throw new UnsupportedEncodingException(message);
        }
        mCharset = aCharset;
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
     * Determines the set of files this FileSetCheck is interested in.
     * Returns the files that have one of the currently active file extensions.
     * If no file extensions are active the argument array is returned.
     *
     * <p>
     * This method can be used in the implementation of <code>process()</code>
     * to filter it's argument list for interesting files.
     * </p>
     *
     * @param aFiles the candidates for processing
     * @return the subset of aFiles that this FileSetCheck should process
     * @see FileSetCheck#process
     */
    protected final File[] filter(File[] aFiles)
    {
        return Utils.filterFilesByExtension(aFiles, mFileExtensions);
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

    /**
     * Adds a violation message to the
     * {@link #getMessageCollector message collector}.
     * {@inheritDoc}
     */
    protected final void log(int aLine, String aKey, Object aArgs[])
    {
        log(aLine, 0, aKey, aArgs);
    }

    /**
     * Adds a violation message to the
     * {@link #getMessageCollector message collector}.
     * {@inheritDoc}
     */
    protected final void log(int aLineNo, int aColNo,
                             String aKey, Object[] aArgs)
    {
        getMessageCollector().add(
            new LocalizedMessage(aLineNo,
                                 aColNo,
                                 getMessageBundle(),
                                 aKey,
                                 aArgs,
                                 getSeverityLevel(),
                                 getId(),
                                 this.getClass()));
    }

    /**
     * Notify all listeners about the errors in a file.
     * Calls <code>MessageDispatcher.fireErrors()</code> with
     * all logged errors and than clears errors' list.
     * @param aFileName the audited file
     */
    protected final void fireErrors(String aFileName)
    {
        final LocalizedMessage[] errors = getMessageCollector().getMessages();
        getMessageCollector().reset();
        getMessageDispatcher().fireErrors(aFileName, errors);
    }
}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
import java.util.ArrayList;

/**
 * Provides common functionality for many FileSetChecks.
 *
 * @author lkuehne
 */
public abstract class AbstractFileSetCheck
        extends AutomaticBean implements FileSetCheck
{
    /** The dispatcher errors are fired to. */
    private MessageDispatcher mDispatcher = null;

    /** the file extensions that are accepted by this filter */
    private String[] mFileExtensions = {};

    /**
     * Does nothing.
     * @see com.puppycrawl.tools.checkstyle.api.FileSetCheck
     */
    public void destroy()
    {
    }

    /** @see com.puppycrawl.tools.checkstyle.api.FileSetCheck */
    public final void setMessageDispatcher(MessageDispatcher aDispatcher)
    {
        mDispatcher = aDispatcher;
    }

    /** @return the current MessageDispatcher. */
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
        if (mFileExtensions == null || mFileExtensions.length == 0) {
            return aFiles;
        }
        ArrayList files = new ArrayList(aFiles.length);
        for (int i = 0; i < aFiles.length; i++) {
            File file = aFiles[i];
            final String fileName = file.getName();
            for (int j = 0; j < mFileExtensions.length; j++) {
                String fileExtension = mFileExtensions[j];
                if (fileName.endsWith(fileExtension)) {
                    files.add(file);
                }
            }
        }
        return (File[]) files.toArray(new File[files.size()]);
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
            String extension = aExtensions[i];
            if (extension.startsWith(".")) {
                mFileExtensions[i] = extension;
            }
            else {
                mFileExtensions[i] = "." + extension;
            }
        }
    }

    /**
     * Returns the Message bundle name to use for this FileSetCheck.
     * The default implementation uses the <code>messages</code> bundle
     * in the same package as this FileSetCeck.
     * @return the message bundle name
     */
    protected String getMessageBundle()
    {
        final String className = getClass().getName();
        final int pkgEndIndex = className.lastIndexOf('.');
        final String pkgName = className.substring(0, pkgEndIndex);
        final String bundle = pkgName + ".messages";
        return bundle;
    }
}

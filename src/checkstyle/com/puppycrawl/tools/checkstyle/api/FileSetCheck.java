////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
 * Interface for Checking a set of files for some criteria.
 *
 * @author lkuehne
 * @author oliver
 */
public interface FileSetCheck
    extends Configurable, Contextualizable
{
    /**
     * Sets the MessageDispatcher that is used to dispatch error
     * messages to AuditListeners during processing.
     * @param aDispatcher the dispatcher
     */
    void setMessageDispatcher(MessageDispatcher aDispatcher);

    /**
     * Initialise the instance. This is the time to verify that everything
     * required to perform it job.
     */
    void init();

    /** Cleans up the object. **/
    void destroy();

    /**
     * Called when about to be called to process a set of files.
     * @param aCharset the character set used to read the files.
     */
    void beginProcessing(String aCharset);

    /**
     * Request to process a file. The implementation should use the supplied
     * contents and not read the contents again. This reduces the amount of
     * file I/O.
     * <p>
     * The file set to process might contain files that are not
     * interesting to the FileSetCheck. Such files should be ignored,
     * no error message should be fired for them. For example a FileSetCheck
     * that checks java files should ignore HTML or properties files.
     * <p>
     * The method should return the set of messages to be logged.
     *
     * @param aFile the file to be processed
     * @param aLines an immutable list of the contents of the file.
     * @return the list of messages to be logged.
     */
    TreeSet<LocalizedMessage> process(File aFile, List<String> aLines);

    /**
     * Called when all the files have been processed. This is the time to
     * perform any checks that need to be done across a set of files. In this
     * method, the implementation is responsible for the logging of messages.
     */
    void finishProcessing();
}

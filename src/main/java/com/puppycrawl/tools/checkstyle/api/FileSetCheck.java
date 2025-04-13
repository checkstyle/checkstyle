///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.api;

import java.io.File;
import java.util.SortedSet;

/**
 * Interface for Checking a set of files for some criteria.
 *
 */
public interface FileSetCheck
    extends Configurable, Contextualizable {

    /**
     * Sets the MessageDispatcher that is used to dispatch audit events
     * to AuditListeners during processing.
     *
     * @param dispatcher the dispatcher
     */
    void setMessageDispatcher(MessageDispatcher dispatcher);

    /**
     * Initialise the instance. This is the time to verify that everything
     * required to perform its job.
     */
    void init();

    /** Cleans up the object. **/
    void destroy();

    /**
     * Called when about to be called to process a set of files.
     *
     * @param charset the character set used to read the files.
     */
    void beginProcessing(String charset);

    /**
     * Request to process a file. The implementation should use the supplied
     * contents and not read the contents again. This reduces the amount of
     * file I/O.
     *
     * <p>
     * The file set to process might contain files that are not
     * interesting to the FileSetCheck. Such files should be ignored,
     * no audit event should be fired for them. For example a FileSetCheck
     * that checks java files should ignore HTML or properties files.
     * </p>
     *
     * <p>
     * The method should return the set of violations to be logged.
     * </p>
     *
     * @param file the file to be processed
     * @param fileText the contents of the file.
     * @return the sorted set of violations to be logged.
     * @throws CheckstyleException if error condition within Checkstyle occurs
     */
    SortedSet<Violation> process(File file, FileText fileText) throws CheckstyleException;

    /**
     * Called when all the files have been processed. This is the time to
     * perform any checks that need to be done across a set of files. In this
     * method, the implementation is responsible for the logging of violations.
     */
    void finishProcessing();

}

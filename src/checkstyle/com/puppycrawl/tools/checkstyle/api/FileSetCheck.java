////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

/**
 * Interface for Checking a set of files for some criteria.
 *
 * @author lkuehne
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
     * Processes a set of files and fires errors to the MessageDispatcher.
     *
     * The file set to process might contain files that are not
     * interesting to the FileSetCheck. Such files should be ignored,
     * no error message should be fired for them. For example a FileSetCheck
     * that checks java files should ignore html or properties files.
     *
     * Once processiong is done, it is highly recommended to call for
     * the destroy method to close and remove the listeners.
     *
     * @param aFiles the files to be audited.
     * @see #destroy()
     */
    void process(List<File> aFiles);

    /** Cleans up the object. **/
    void destroy();
}

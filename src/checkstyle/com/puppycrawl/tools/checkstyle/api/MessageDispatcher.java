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

import java.util.TreeSet;

/**
 * Used by FileSetChecks to distribute AuditEvents to AuditListeners.
 * @author lkuehne
 */
public interface MessageDispatcher
{
    /**
     * Notify all listeners about the beginning of a file audit.
     * @param aFileName the file to be audited
     */
    void fireFileStarted(String aFileName);

    /**
     * Notify all listeners about the end of a file audit.
     * @param aFileName the audited file
     */
    void fireFileFinished(String aFileName);

    /**
     * Notify all listeners about the errors in a file.
     * @param aFileName the audited file
     * @param aErrors the audit errors from the file
     */
    void fireErrors(String aFileName, TreeSet<LocalizedMessage> aErrors);
}

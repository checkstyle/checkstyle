///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.api;

import java.util.SortedSet;

/**
 * Used by FileSetChecks to distribute AuditEvents to AuditListeners.
 */
public interface MessageDispatcher {

    /**
     * Notify all listeners about the beginning of a file audit.
     *
     * @param fileName the file to be audited
     */
    void fireFileStarted(String fileName);

    /**
     * Notify all listeners about the end of a file audit.
     *
     * @param fileName the audited file
     */
    void fireFileFinished(String fileName);

    /**
     * Notify all listeners about the violations in a file.
     *
     * @param fileName the audited file
     * @param errors the violations from the file
     */
    void fireErrors(String fileName, SortedSet<Violation> errors);

}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks.header;

/**
 * Interface for reporting header violations.
 * This interface exists to abstract away the different
 * log methods that need to be called from Checks / FileSetChecks
 * to report a violation.
 *
 * @author lkuehne
 */
interface HeaderViolationMonitor
{
    /**
     * Report that the currently checked file does not have any header.
     */
    void reportHeaderMissing();

    /**
     * Report that the header of currently checked file does not match
     * the expected header.
     *
     * @param aLineNo line number in the file
     * @param aHeaderLine the expected line
     */
    void reportHeaderMismatch(int aLineNo, String aHeaderLine);
}

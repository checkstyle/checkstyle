////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * This is a very simple filter based on severity matching.
 * The filter admits option severity and accepts an AuditEvent
 * if its severity equals the filter's severity.
 * @author Rick Giles
 */
public class SeverityMatchFilter
    extends AutomaticBean
    implements Filter {
    /** The severity level to accept. */
    private SeverityLevel severity = SeverityLevel.ERROR;

    /** Whether to accept or reject on severity matches. */
    private boolean acceptOnMatch = true;

    /**
     * Sets the severity level.  The string should be one of the names
     * defined in the {@code SeverityLevel} class.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     */
    public final void setSeverity(String severity) {
        this.severity = SeverityLevel.getInstance(severity);
    }

    /**
     * Sets whether to accept or reject on matching severity level.
     * @param acceptOnMatch if true, accept on matches; if
     *     false, reject on matches.
     */
    public final void setAcceptOnMatch(boolean acceptOnMatch) {
        this.acceptOnMatch = acceptOnMatch;
    }

    @Override
    public boolean accept(AuditEvent event) {
        final boolean result = severity == event.getSeverityLevel();
        if (acceptOnMatch) {
            return result;
        }
        return !result;
    }
}

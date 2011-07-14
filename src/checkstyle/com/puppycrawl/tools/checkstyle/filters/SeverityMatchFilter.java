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
    implements Filter
{
    /** the severity level to accept */
    private SeverityLevel mSeverityLevel = SeverityLevel.ERROR;

    /** whether to accept or reject on severity matches */
    private boolean mAcceptOnMatch = true;

    /**
     * Sets the severity level.  The string should be one of the names
     * defined in the <code>SeverityLevel</code> class.
     *
     * @param aSeverity  The new severity level
     * @see SeverityLevel
     */
    public final void setSeverity(String aSeverity)
    {
        mSeverityLevel = SeverityLevel.getInstance(aSeverity);
    }

    /**
     * Sets whether to accept or reject on matching severity level.
     * @param aAcceptOnMatch if true, accept on matches; if
     * false, reject on matches.
     */
    public final void setAcceptOnMatch(boolean aAcceptOnMatch)
    {
        mAcceptOnMatch = aAcceptOnMatch;
    }

    /** {@inheritDoc} */
    public boolean accept(AuditEvent aEvent)
    {
        final boolean result = mSeverityLevel.equals(aEvent.getSeverityLevel());
        if (mAcceptOnMatch) {
            return result;
        }
        return !result;
    }
}

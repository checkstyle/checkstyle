////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
 * The filter admits two options severity and acceptOnMatch.
 * If there is an exact match between the value of the
 * severity option and the severity of the AuditEvent,
 * then the decide(AuditEvent) method
 * returns Filter.ACCEPT in case the acceptOnMatch option value is
 * set to true, if it is false then Filter.DENY is returned.
 * If there is no match, or the filtered Object is not
 * an AuditEvent, Filter.NEUTRAL is returned.
 * @author Rick Giles
 */
public class SeverityMatchFilter
    extends AutomaticBean
    implements Filter
{
    /** the severity level to accept */
    private SeverityLevel mSeverityLevel = SeverityLevel.ERROR;

    /**
     * Do we return ACCEPT when a match occurs?
     * Default is <code>true</code>.
     */
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
     *  Get the severity level's name.
     *
     *  @return  the check's severity level name.
     */
    public final String getSeverity()
    {
        return mSeverityLevel.getName();
    }

    /**
     * Sets whether we return ACCEPT or DENY when a match occurs.
     * @param aAcceptOnMatch if true return ACCEPT when a match
     * occurs; if false, return DENY.
     */
    public final void setAcceptOnMatch(boolean aAcceptOnMatch)
    {
        mAcceptOnMatch = aAcceptOnMatch;
    }

    /**
     * Returns whether we return ACCEPT or DENY when a match occurs.
     * @return true if return ACCEPT when a match occurs;
     * return false if DENY.
     */
    public boolean getAcceptOnMatch()
    {
        return mAcceptOnMatch;
    }

    /** @see com.puppycrawl.tools.checkstyle.filter.Filter */
    public int decide(Object aObject)
    {
        if (!(aObject instanceof AuditEvent)) {
            return Filter.NEUTRAL;
        }

        final AuditEvent event = (AuditEvent) aObject;

        if (mSeverityLevel == null) {
            return Filter.NEUTRAL;
        }

        boolean matchOccurred = false;
        if (mSeverityLevel.equals(event.getSeverityLevel())) {
            matchOccurred = true;
        }

        if (matchOccurred) {
            if (mAcceptOnMatch) {
                return Filter.ACCEPT;
            }
            else {
                return Filter.DENY;
            }
        }
        else {
            return Filter.NEUTRAL;
        }
    }
}

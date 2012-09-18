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

/**
 * An audit listener that counts how many {@link AuditEvent AuditEvents}
 * of a given severity have been generated.
 *
 * @author lkuehne
 */
public final class SeverityLevelCounter implements AuditListener
{
    /** The severity level to watch out for. */
    private SeverityLevel mLevel;

    /** Keeps track of the number of counted events. */
    private int mCount;

    /**
     * Creates a new counter.
     * @param aLevel the severity level events need to have, must be non-null.
     */
    public SeverityLevelCounter(SeverityLevel aLevel)
    {
        if (aLevel == null) {
            throw new IllegalArgumentException();
        }
        mLevel = aLevel;
    }

    /** {@inheritDoc} */
    public void addError(AuditEvent aEvt)
    {
        if (mLevel.equals(aEvt.getSeverityLevel())) {
            mCount++;
        }
    }

    /** {@inheritDoc} */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        if (SeverityLevel.ERROR.equals(mLevel)) {
            mCount++;
        }
    }

    /** {@inheritDoc} */
    public void auditStarted(AuditEvent aEvt)
    {
        mCount = 0;
    }

    /** {@inheritDoc} */
    public void fileStarted(AuditEvent aEvt)
    {
    }

    /** {@inheritDoc} */
    public void auditFinished(AuditEvent aEvt)
    {
    }

    /** {@inheritDoc} */
    public void fileFinished(AuditEvent aEvt)
    {
    }

    /**
     * Returns the number of counted events since audit started.
     * @return the number of counted events since audit started.
     */
    public int getCount()
    {
        return mCount;
    }
}

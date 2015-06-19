////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
public final class SeverityLevelCounter implements AuditListener {
    /** The severity level to watch out for. */
    private SeverityLevel level;

    /** Keeps track of the number of counted events. */
    private int count;

    /**
     * Creates a new counter.
     * @param level the severity level events need to have, must be non-null.
     */
    public SeverityLevelCounter(SeverityLevel level) {
        if (level == null) {
            throw new IllegalArgumentException();
        }
        this.level = level;
    }

    /** {@inheritDoc} */
    @Override
    public void addError(AuditEvent evt) {
        if (level == evt.getSeverityLevel()) {
            count++;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addException(AuditEvent evt, Throwable throwable) {
        if (SeverityLevel.ERROR == level) {
            count++;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void auditStarted(AuditEvent evt) {
        count = 0;
    }

    /** {@inheritDoc} */
    @Override
    public void fileStarted(AuditEvent evt) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /** {@inheritDoc} */
    @Override
    public void auditFinished(AuditEvent evt) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /** {@inheritDoc} */
    @Override
    public void fileFinished(AuditEvent evt) {
        // No code by default, should be overridden only by demand at subclasses
    }

    /**
     * Returns the number of counted events since audit started.
     * @return the number of counted events since audit started.
     */
    public int getCount() {
        return count;
    }
}

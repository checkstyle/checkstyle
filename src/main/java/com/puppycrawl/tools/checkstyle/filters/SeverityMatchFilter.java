////
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

package com.puppycrawl.tools.checkstyle.filters;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * <div>
 * Filter {@code SeverityMatchFilter} decides audit events according to the
 * <a href="https://checkstyle.org/config.html#Severity">severity level</a> of the event.
 * </div>
 *
 * <p>
 * SeverityMatchFilter can suppress Checks that have Treewalker or Checker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code acceptOnMatch} - Control whether the filter accepts an audit
 * event if and only if there is a match between the event's severity level and
 * property severity. If acceptOnMatch is {@code false}, then the filter accepts
 * an audit event if and only if there is not a match between the event's severity
 * level and property severity.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code severity} - Specify the severity level of this filter.
 * Type is {@code com.puppycrawl.tools.checkstyle.api.SeverityLevel}.
 * Default value is {@code error}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 3.2
 */
public class SeverityMatchFilter
    extends AbstractAutomaticBean
    implements Filter {

    /** Specify the severity level of this filter. */
    private SeverityLevel severity = SeverityLevel.ERROR;

    /**
     * Control whether the filter accepts an audit event if and only if there
     * is a match between the event's severity level and property severity.
     * If acceptOnMatch is {@code false}, then the filter accepts an audit event
     * if and only if there is not a match between the event's severity level
     * and property severity.
     */
    private boolean acceptOnMatch = true;

    /**
     * Setter to specify the severity level of this filter.
     *
     * @param severity  The new severity level
     * @see SeverityLevel
     * @since 3.2
     */
    public final void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

    /**
     * Setter to control whether the filter accepts an audit event if and only if there
     * is a match between the event's severity level and property severity.
     * If acceptOnMatch is {@code false}, then the filter accepts an audit event
     * if and only if there is not a match between the event's severity level and property severity.
     *
     * @param acceptOnMatch if true, accept on matches; if
     *     false, reject on matches.
     * @since 3.2
     */
    public final void setAcceptOnMatch(boolean acceptOnMatch) {
        this.acceptOnMatch = acceptOnMatch;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(AuditEvent event) {
        final boolean severityMatches = severity == event.getSeverityLevel();
        return acceptOnMatch == severityMatches;
    }

}

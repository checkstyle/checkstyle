////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.Filter;

/**
 * <p>
 * Filter {@code SuppressByMessageFilter} suppresses audit events for checks whose violation
 * messages match specified regular expression.
 * </p>
 * <p>
 * Rationale: To allow users to suppress any or all violations for certain identifiers by specifying
 * RegExp in the same configuration module as their other modules.
 * </p>
 * <p>
 * {@code SuppressByMessageFilter} can suppress violations for checks that have Treewalker or
 * Checker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code checkFormat} - Define the RegExp for matching against Check name which throws
 * a violation.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code messageFormat} - Define the RegExp for matching against the violation message
 * thrown by a check.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 * <p>
 * The following configuration will suppress all violations for the {@code RequireThis} check.
 * </p>
 * <pre>
 * &lt;module name="SuppressByMessageFilter"&gt;
 *   &lt;property name="checkFormat" value="RequireThisCheck"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * The following configuration will suppress all violations for any variable with a single letter
 * identifier.
 * </p>
 * <pre>
 * &lt;module name="SuppressByMessageFilter"&gt;
 *   &lt;property name="messageFormat" value="('[a-z]{1}')"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.Checker}
 * </p>
 *
 * @since 9.0.2
 */
public class SuppressByMessageFilter extends AutomaticBean implements Filter {

    /** Define the RegExp for matching against Check name which throws a violation. */
    private Pattern checkFormat;

    /** Define the RegExp for matching against the violation message thrown by a check. */
    private Pattern messageFormat;

    /**
     * Setter to define the RegExp for matching against Check name which throws a violation.
     *
     * @param checkFormat regular expression string for check format
     */
    public void setCheckFormat(String checkFormat) {
        this.checkFormat = Pattern.compile(checkFormat);
    }

    /**
     * Setter to define the RegExp for matching against the violation message thrown by a check.
     *
     * @param messageFormat regular expression string for violation message format
     */
    public void setMessageFormat(String messageFormat) {
        this.messageFormat = Pattern.compile(messageFormat);
    }

    /**
     * Checks whether {@code checkFormat} matches with violation source.
     *
     * @param event the {@code AuditEvent} to match
     * @return whether a match was found or not
     */
    private boolean matchCheckFormat(AuditEvent event) {
        boolean matchFound = false;
        if (checkFormat != null && !checkFormat.toString().isEmpty()) {
            matchFound = checkFormat.matcher(event.getSourceName()).find();
        }
        return matchFound;
    }

    /**
     * Checks whether {@code messageFormat} matches with violation message.
     *
     * @param event the {@code AuditEvent} to match
     * @return whether a match was found or not
     */
    private boolean matchMessageFormat(AuditEvent event) {
        boolean matchFound = false;
        if (messageFormat != null && !messageFormat.toString().isEmpty()) {
            matchFound = messageFormat.matcher(event.getMessage()).find();
        }
        return matchFound;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(AuditEvent event) {
        return !matchCheckFormat(event) && !matchMessageFormat(event);
    }
}
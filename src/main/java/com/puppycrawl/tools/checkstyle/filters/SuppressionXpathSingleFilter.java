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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean;
import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;

/**
 * <div>
 * Filter {@code SuppressionXpathSingleFilter} suppresses audit events for Checks
 * violations in the specified file, class, checks, message, module id, and xpath.
 * </div>
 *
 * <p>
 * Rationale: To allow users to use suppressions configured in the same config as other modules.
 * {@code SuppressionFilter} and {@code SuppressionXpathFilter} require a separate file.
 * </p>
 *
 * <p>
 * Advice: If checkstyle configuration is used for several projects, single suppressions
 * on common files/folders is better to put in checkstyle configuration as common rule.
 * All suppression that are for specific file names is better to keep in project
 * specific config file.
 * </p>
 *
 * <p>
 * Attention: This filter only supports single suppression, and will need multiple
 * instances if users wants to suppress multiple violations.
 * </p>
 *
 * <p>
 * {@code SuppressionXpathSingleFilter} can suppress Checks that have {@code Treewalker} as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code checks} - Define a Regular Expression matched against the name
 * of the check associated with an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code files} - Define a Regular Expression matched against the file
 * name associated with an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code id} - Define a string matched against the ID of the check
 * associated with an audit event.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code message} - Define a Regular Expression matched against the message
 * of the check associated with an audit event.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code query} - Define a string xpath query.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * @since 8.18
 */
public class SuppressionXpathSingleFilter extends AbstractAutomaticBean implements
    TreeWalkerFilter {
    /**
     * XpathFilterElement instance.
     */
    private XpathFilterElement xpathFilter;
    /**
     * Define a Regular Expression matched against the file name associated with an audit event.
     */
    private Pattern files;
    /**
     * Define a Regular Expression matched against the name of the check associated
     * with an audit event.
     */
    private Pattern checks;
    /**
     * Define a Regular Expression matched against the message of the check
     * associated with an audit event.
     */
    private Pattern message;
    /**
     * Define a string matched against the ID of the check associated with an audit event.
     */
    private String id;
    /**
     * Define a string xpath query.
     */
    private String query;

    /**
     * Setter to define a Regular Expression matched against the file name
     * associated with an audit event.
     *
     * @param files the name of the file
     * @since 8.18
     */
    public void setFiles(String files) {
        if (files == null) {
            this.files = null;
        }
        else {
            this.files = Pattern.compile(files);
        }
    }

    /**
     * Setter to define a Regular Expression matched against the name of the check
     * associated with an audit event.
     *
     * @param checks the name of the check
     * @since 8.18
     */
    public void setChecks(String checks) {
        if (checks == null) {
            this.checks = null;
        }
        else {
            this.checks = Pattern.compile(checks);
        }
    }

    /**
     * Setter to define a Regular Expression matched against the message of
     * the check associated with an audit event.
     *
     * @param message the message of the check
     * @since 8.18
     */
    public void setMessage(String message) {
        if (message == null) {
            this.message = null;
        }
        else {
            this.message = Pattern.compile(message);
        }
    }

    /**
     * Setter to define a string matched against the ID of the check associated
     * with an audit event.
     *
     * @param id the ID of the check
     * @since 8.18
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter to define a string xpath query.
     *
     * @param query the xpath query
     * @since 8.18
     */
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    protected void finishLocalSetup() {
        xpathFilter = new XpathFilterElement(files, checks, message, id, query);
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
        return xpathFilter.accept(treeWalkerAuditEvent);
    }

}

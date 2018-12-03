////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;

/**
 * Filter {@code SuppressionXpathSingleFilter} suppresses audit events for
 * Checks violations in the specified file, class, checks, message, module id,
 * and xpath.
 * Attention: This filter only supports single suppression, and will need
 * multiple instances if users wants to suppress multiple violations.
 */
public class SuppressionXpathSingleFilter extends AutomaticBean implements
        TreeWalkerFilter {
    /**
     * XpathFilter instance.
     */
    private XpathFilter xpathFilter;
    /**
     * The pattern for file names.
     */
    private Pattern files;
    /**
     * The pattern for check class names.
     */
    private Pattern checks;
    /**
     * The pattern for message names.
     */
    private Pattern message;
    /**
     * Module id of filter.
     */
    private String id;
    /**
     * Xpath query.
     */
    private String query;

    /**
     * Set the regular expression to specify names of files to suppress.
     * @param files the name of the file
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
     * Set the regular expression to specify the name of the check to suppress.
     * @param checks the name of the check
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
     * Set the regular expression to specify the message of the check to suppress.
     * @param message the message of the check
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
     * Set the ID of the check to suppress.
     * @param id the ID of the check
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Set the xpath query.
     * @param query the xpath query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    protected void finishLocalSetup() {
        xpathFilter = new XpathFilter(files, checks, message, id, query);
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
        return xpathFilter.accept(treeWalkerAuditEvent);
    }

}

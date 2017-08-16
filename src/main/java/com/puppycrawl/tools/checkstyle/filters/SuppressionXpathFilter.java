////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.utils.FilterUtils;

/**
 * This filter accepts TreeWalkerAuditEvents according to file, check and xpath query,
 * as specified in a suppression file.
 *
 * @author Timur Tibeyev.
 * @noinspection NonFinalFieldReferenceInEquals, NonFinalFieldReferencedInHashCode
 */
public class SuppressionXpathFilter extends AutomaticBean implements
        TreeWalkerFilter, ExternalResourceHolder {

    /** Filename of supression file. */
    private String file;
    /** Tells whether config file existence is optional. */
    private boolean optional;
    /** Set of individual xpath suppresses. */
    private Set<TreeWalkerFilter> filters = new HashSet<>();

    /**
     * Sets name of the supression file.
     * @param fileName name of the suppressions file.
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Sets whether config file existence is optional.
     * @param optional tells if config file existence is optional.
     */
    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SuppressionXpathFilter suppressionXpathFilter = (SuppressionXpathFilter) obj;
        return Objects.equals(filters, suppressionXpathFilter.filters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filters);
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent treeWalkerAuditEvent) {
        boolean result = true;
        for (TreeWalkerFilter filter : filters) {
            if (!filter.accept(treeWalkerAuditEvent)) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public Set<String> getExternalResourceLocations() {
        return Collections.singleton(file);
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        if (file != null) {
            if (optional) {
                if (FilterUtils.isFileExists(file)) {
                    filters = SuppressionsLoader.loadXpathSuppressions(file);
                }
                else {
                    filters = new HashSet<>();
                }
            }
            else {
                filters = SuppressionsLoader.loadXpathSuppressions(file);
            }
        }
    }
}

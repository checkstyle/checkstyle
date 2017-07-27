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

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import net.sf.saxon.om.Item;
import net.sf.saxon.sxpath.XPathDynamicContext;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;

/**
 * This filter processes {@link TreeWalkerAuditEvent}
 * objects based on the criteria of file, check, module id, xpathQuery.
 *
 * @author Timur Tibeyev.
 */
public class XpathFilter implements TreeWalkerFilter {
    /** The regexp to match file names against. */
    private final Pattern fileRegexp;

    /** The pattern for file names. */
    private final String filePattern;

    /** The regexp to match check names against. */
    private final Pattern checkRegexp;

    /** The pattern for check class names. */
    private final String checkPattern;

    /** Module id filter. */
    private final String moduleId;

    /** Xpath expression. */
    private final XPathExpression xpathExpression;

    /** Xpath query. */
    private final String xpathQuery;

    /**
     * Creates a {@code XpathElement} instance.
     * @param files regular expression for names of filtered files
     * @param checks regular expression for filtered check classes
     * @param moduleId the module id
     * @param query the xpath query
     */
    public XpathFilter(String files, String checks,
                       String moduleId, String query) {
        filePattern = files;
        fileRegexp = Pattern.compile(files);
        checkPattern = checks;
        if (checks == null) {
            checkRegexp = null;
        }
        else {
            checkRegexp = CommonUtils.createPattern(checks);
        }
        this.moduleId = moduleId;
        xpathQuery = query;
        if (xpathQuery == null) {
            xpathExpression = null;
        }
        else {
            final XPathEvaluator xpathEvaluator = new XPathEvaluator();
            try {
                xpathExpression = xpathEvaluator.createExpression(xpathQuery);
            }
            catch (XPathException ex) {
                throw new IllegalStateException("Unexpected xpath query: " + xpathQuery, ex);
            }
        }
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        return !isFileNameAndModuleAndCheckNameMatching(event)
                || !isXpathQueryMatching(event);
    }

    /**
     * Is matching by file name, moduleId and Check name.
     * @param event event
     * @return true if it is matching
     */
    private boolean isFileNameAndModuleAndCheckNameMatching(TreeWalkerAuditEvent event) {
        return event.getFileName() != null
                && fileRegexp.matcher(event.getFileName()).find()
                && event.getLocalizedMessage() != null
                && (moduleId == null || moduleId.equals(event.getModuleId()))
                && (checkRegexp == null || checkRegexp.matcher(event.getSourceName()).find());
    }

    /**
     * Is matching by xpath query.
     * @param event event
     * @return true is matching
     */
    private boolean isXpathQueryMatching(TreeWalkerAuditEvent event) {
        boolean isMatching = false;
        if (xpathExpression != null) {
            final List<Item> items = getItems(event);
            for (Item item : items) {
                final AbstractNode abstractNode = (AbstractNode) item;
                isMatching = abstractNode.getTokenType() == event.getTokenType()
                        && abstractNode.getLineNumber() == event.getLine()
                        && abstractNode.getColumnNumber() == event.getColumn();
                if (isMatching) {
                    break;
                }
            }
        }
        return isMatching;
    }

    /**
     * Returns list of nodes matching xpath expression given event.
     * @param event {@code TreeWalkerAuditEvent} object
     * @return list of nodes matching xpath expression given event
     */
    private List<Item> getItems(TreeWalkerAuditEvent event) {
        final RootNode rootNode;
        if (event.getRootAst() == null) {
            rootNode = null;
        }
        else {
            rootNode = new RootNode(event.getRootAst());
        }
        final List<Item> items;
        try {
            final XPathDynamicContext xpathDynamicContext =
                    xpathExpression.createDynamicContext(rootNode);
            items = xpathExpression.evaluate(xpathDynamicContext);
        }
        catch (XPathException ex) {
            throw new IllegalStateException("Cannot initialize context and evaluate query: "
                    + xpathQuery, ex);
        }
        return items;
    }

    @Override
    public int hashCode() {
        return Objects.hash(filePattern, checkPattern, moduleId, xpathQuery);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final XpathFilter xpathFilter = (XpathFilter) other;
        return Objects.equals(filePattern, xpathFilter.filePattern)
                && Objects.equals(checkPattern, xpathFilter.checkPattern)
                && Objects.equals(moduleId, xpathFilter.moduleId)
                && Objects.equals(xpathQuery, xpathFilter.xpathQuery);
    }
}

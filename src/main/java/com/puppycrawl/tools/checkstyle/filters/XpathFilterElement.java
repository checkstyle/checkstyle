///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.xpath.AbstractNode;
import com.puppycrawl.tools.checkstyle.xpath.RootNode;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.Item;
import net.sf.saxon.sxpath.XPathDynamicContext;
import net.sf.saxon.sxpath.XPathEvaluator;
import net.sf.saxon.sxpath.XPathExpression;
import net.sf.saxon.trans.XPathException;

/**
 * This filter element is immutable and processes {@link TreeWalkerAuditEvent}
 * objects based on the criteria of file, check, module id, xpathQuery.
 *
 */
public class XpathFilterElement implements TreeWalkerFilter {

    /** The regexp to match file names against. */
    private final Pattern fileRegexp;

    /** The pattern for file names. */
    private final String filePattern;

    /** The regexp to match check names against. */
    private final Pattern checkRegexp;

    /** The pattern for check class names. */
    private final String checkPattern;

    /** The regexp to match message names against. */
    private final Pattern messageRegexp;

    /** The pattern for message names. */
    private final String messagePattern;

    /** Module id filter. */
    private final String moduleId;

    /** Xpath expression. */
    private final XPathExpression xpathExpression;

    /** Xpath query. */
    private final String xpathQuery;

    /** Indicates if all properties are set to null. */
    private final boolean isEmptyConfig;

    /**
     * Creates a {@code XpathElement} instance.
     *
     * @param files regular expression for names of filtered files
     * @param checks regular expression for filtered check classes
     * @param message regular expression for messages.
     * @param moduleId the module id
     * @param query the xpath query
     * @throws IllegalArgumentException if the xpath query is not expected.
     */
    public XpathFilterElement(String files, String checks,
                       String message, String moduleId, String query) {
        this(Optional.ofNullable(files).map(Pattern::compile).orElse(null),
             Optional.ofNullable(checks).map(CommonUtil::createPattern).orElse(null),
             Optional.ofNullable(message).map(Pattern::compile).orElse(null),
             moduleId,
             query);
    }

    /**
     * Creates a {@code XpathElement} instance.
     *
     * @param files regular expression for names of filtered files
     * @param checks regular expression for filtered check classes
     * @param message regular expression for messages.
     * @param moduleId the module id
     * @param query the xpath query
     * @throws IllegalArgumentException if the xpath query is not correct.
     */
    public XpathFilterElement(Pattern files, Pattern checks, Pattern message,
                           String moduleId, String query) {
        if (files == null) {
            filePattern = null;
            fileRegexp = null;
        }
        else {
            filePattern = files.pattern();
            fileRegexp = files;
        }
        if (checks == null) {
            checkPattern = null;
            checkRegexp = null;
        }
        else {
            checkPattern = checks.pattern();
            checkRegexp = checks;
        }
        if (message == null) {
            messagePattern = null;
            messageRegexp = null;
        }
        else {
            messagePattern = message.pattern();
            messageRegexp = message;
        }
        this.moduleId = moduleId;
        xpathQuery = query;
        if (xpathQuery == null) {
            xpathExpression = null;
        }
        else {
            final XPathEvaluator xpathEvaluator = new XPathEvaluator(
                    Configuration.newConfiguration());
            try {
                xpathExpression = xpathEvaluator.createExpression(xpathQuery);
            }
            catch (XPathException ex) {
                throw new IllegalArgumentException("Incorrect xpath query: " + xpathQuery, ex);
            }
        }
        isEmptyConfig = fileRegexp == null
                             && checkRegexp == null
                             && messageRegexp == null
                             && moduleId == null
                             && xpathExpression == null;
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        return isEmptyConfig
                || !isFileNameAndModuleAndModuleNameMatching(event)
                || !isMessageNameMatching(event)
                || !isXpathQueryMatching(event);
    }

    /**
     * Is matching by file name, module id and Check name.
     *
     * @param event event
     * @return true if it is matching
     */
    private boolean isFileNameAndModuleAndModuleNameMatching(TreeWalkerAuditEvent event) {
        return event.getFileName() != null
                && (fileRegexp == null || fileRegexp.matcher(event.getFileName()).find())
                && event.getViolation() != null
                && (moduleId == null || moduleId.equals(event.getModuleId()))
                && (checkRegexp == null || checkRegexp.matcher(event.getSourceName()).find());
    }

    /**
     * Is matching by message.
     *
     * @param event event
     * @return true if it is matching or not set.
     */
    private boolean isMessageNameMatching(TreeWalkerAuditEvent event) {
        return messageRegexp == null || messageRegexp.matcher(event.getMessage()).find();
    }

    /**
     * Is matching by xpath query.
     *
     * @param event event
     * @return true if it is matching or not set.
     */
    private boolean isXpathQueryMatching(TreeWalkerAuditEvent event) {
        boolean isMatching;
        if (xpathExpression == null) {
            isMatching = true;
        }
        else {
            isMatching = false;
            final List<AbstractNode> nodes = getItems(event)
                    .stream().map(AbstractNode.class::cast).collect(Collectors.toList());
            for (AbstractNode abstractNode : nodes) {
                isMatching = abstractNode.getTokenType() == event.getTokenType()
                        && abstractNode.getLineNumber() == event.getLine()
                        && abstractNode.getColumnNumber() == event.getColumnCharIndex();
                if (isMatching) {
                    break;
                }
            }
        }
        return isMatching;
    }

    /**
     * Returns list of nodes matching xpath expression given event.
     *
     * @param event {@code TreeWalkerAuditEvent} object
     * @return list of nodes matching xpath expression given event
     * @throws IllegalStateException if the xpath query could not be evaluated.
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
        return Objects.hash(filePattern, checkPattern, messagePattern,
            moduleId, xpathQuery);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final XpathFilterElement xpathFilter = (XpathFilterElement) other;
        return Objects.equals(filePattern, xpathFilter.filePattern)
                && Objects.equals(checkPattern, xpathFilter.checkPattern)
                && Objects.equals(messagePattern, xpathFilter.messagePattern)
                && Objects.equals(moduleId, xpathFilter.moduleId)
                && Objects.equals(xpathQuery, xpathFilter.xpathQuery);
    }

}

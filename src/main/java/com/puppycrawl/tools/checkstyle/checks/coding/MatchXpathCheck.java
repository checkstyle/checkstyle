///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.List;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
 * <div>
 * Evaluates Xpath query and report violation on all matching AST nodes. This check allows
 * user to implement custom checks using Xpath. If Xpath query is not specified explicitly,
 * then the check does nothing.
 * </div>
 *
 * <p>
 * It is recommended to define custom message for violation to explain what is not allowed and what
 * to use instead, default message might be too abstract. To customize a message you need to
 * add {@code message} element with <b>matchxpath.match</b> as {@code key} attribute and
 * desired message as {@code value} attribute.
 * </p>
 *
 * <p>
 * Please read more about Xpath syntax at
 * <a href="https://www.saxonica.com/html/documentation10/expressions/index.html">Xpath Syntax</a>.
 * Information regarding Xpath functions can be found at
 * <a href="https://www.saxonica.com/html/documentation10/functions/fn/index.html">
 *     XSLT/XPath Reference</a>.
 * Note, that <b>@text</b> attribute can be used only with token types that are listed in
 * <a href="https://github.com/checkstyle/checkstyle/search?q=%22TOKEN_TYPES_WITH_TEXT_ATTRIBUTE+%3D+Arrays.asList%22">
 *     XpathUtil</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code query} - Specify Xpath query.
 * Type is {@code java.lang.String}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code matchxpath.match}
 * </li>
 * </ul>
 *
 * @since 8.39
 */
@StatelessCheck
public class MatchXpathCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text provided by user.
     */
    public static final String MSG_KEY = "matchxpath.match";

    /** Specify Xpath query. */
    private String query = "";

    /** Xpath expression. */
    private XPathExpression xpathExpression;

    /**
     * Setter to specify Xpath query.
     *
     * @param query Xpath query.
     * @throws IllegalStateException if creation of xpath expression fails
     * @since 8.39
     */
    public void setQuery(String query) {
        this.query = query;
        try {
            final XPathEvaluator xpathEvaluator =
                new XPathEvaluator(Configuration.newConfiguration());
            xpathExpression = xpathEvaluator.createExpression(query);
        }
        catch (XPathException ex) {
            throw new IllegalStateException("Creating Xpath expression failed: " + query, ex);
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        if (!query.isEmpty()) {
            final List<DetailAST> matchingNodes = findMatchingNodesByXpathQuery(rootAST);
            matchingNodes.forEach(node -> log(node, MSG_KEY));
        }
    }

    /**
     * Find nodes that match query.
     *
     * @param rootAST root node
     * @return list of matching nodes
     * @throws IllegalStateException if evaluation of xpath query fails
     */
    private List<DetailAST> findMatchingNodesByXpathQuery(DetailAST rootAST) {
        try {
            final RootNode rootNode = new RootNode(rootAST);
            final XPathDynamicContext xpathDynamicContext =
                    xpathExpression.createDynamicContext(rootNode);
            final List<Item> matchingItems = xpathExpression.evaluate(xpathDynamicContext);
            return matchingItems.stream()
                    .map(item -> (DetailAST) ((AbstractNode) item).getUnderlyingNode())
                    .collect(Collectors.toUnmodifiableList());
        }
        catch (XPathException ex) {
            throw new IllegalStateException("Evaluation of Xpath query failed: " + query, ex);
        }
    }
}

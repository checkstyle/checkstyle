////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.List;

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
 * <p>
 * Evaluates Xpath query and report violation on matching AST nodes. All nodes
 * retrieved by Xpath evaluator will be logged as violations. Current check allows
 * user to implement custom checks using Xpath.
 * </p>
 * <ul>
 * <li>
 * Property {@code query} - Xpath query.
 * Type is {@code java.lang.String}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"/&gt;
 * </pre>
 * <p>
 * To fail with violation if method name is 'test' or 'foo'
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt; &lt;property name="query"
 * value="//METHOD_DEF[./IDENT[@text='test' or @text='foo']]"/&gt; &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public void func1() throws RuntimeException {} // violation
 *  public void func2() throws Exception {} // ok
 *  public void func3() throws Error {} // violation
 *  public void func4() throws Throwable {} // violation
 *  public void func5() throws NullPointerException {} // ok
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code xpath.finding}
 * </li>
 * </ul>
 *
 * @since 8.37
 */
@StatelessCheck
public class MatchXpathCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text provided by user.
     */
    public static final String MSG_KEY = "xpath.finding";

    /** Xpath query. */
    private String query = "";

    @Override
    public void beginTree(DetailAST rootAST) {
        if (!query.isEmpty()) {
            evaluateXpathQuery(rootAST);
        }
    }

    private void evaluateXpathQuery(DetailAST rootAST) {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        try {
            final RootNode rootNode = new RootNode(rootAST);
            final XPathExpression xpathExpression = xpathEvaluator.createExpression(query);
            final XPathDynamicContext xpathDynamicContext =
                    xpathExpression.createDynamicContext(rootNode);
            final List<Item> matchingItems = xpathExpression.evaluate(xpathDynamicContext);

            matchingItems.forEach(node -> {
                final AbstractNode xpathNode = (AbstractNode) node;
                log(xpathNode.getUnderlyingNode(), MSG_KEY);
            });
        }
        catch (XPathException ex) {
            throw new IllegalStateException(ex);
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

    /**
     * Setter to xpath query.
     *
     * @param query xpath query.
     */
    public void setQuery(String query) {
        this.query = query;
    }
}

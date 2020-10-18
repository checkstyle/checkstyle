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
 * <p>
 * Evaluates Xpath query and report violation on all matching AST nodes. Current check allows
 * user to implement custom checks using Xpath.
 * Please read more about xpath syntax at <a href="https://www.w3schools.com/xml/xpath_syntax.asp">
 * W3Schools Xpath Syntax</a>.
 * If xpath query is not specified explicitly, then check does nothing.
 * </p>
 * <ul>
 * <li>
 * Property {@code query} - Xpath query.
 * Type is {@code java.lang.String}.
 * Default value is {@code ""}.
 * </li>
 * </ul>
 * <p>
 * The following example demonstrates validation of methods order, so that public methods should
 * come before the private ones:
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt; &lt;property name="query"
 * value="//METHOD_DEF[.//LITERAL_PRIVATE and
 * following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/&gt; &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public void method1() { }
 *  private void method2() { } // violation
 *  public void method3() { }
 *  private void method4() { } // violation
 *  public void method5() { }
 *  private void method6() { } // ok
 * }
 * </pre>
 * <p>
 * To fail with violation if there are any parametrized constructors
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt; &lt;property name="query"
 * value="//CTOR_DEF[count(./PARAMETERS/node()) &gt; 0]"/&gt; &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *  public Test(Object c) { } // violation
 *  public Test(int a, HashMap&lt;String, Integer&gt; b) { } // violation
 *  public Test() { } // ok
 * }
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
 *  public void test() {} // violation
 *  public void getName() {} // ok
 *  public void foo() {} // violation
 *  public void sayHello() {} // ok
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
 * {@code matchxpath.match}
 * </li>
 * </ul>
 *
 * @since 8.38
 */
@StatelessCheck
public class MatchXpathCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text provided by user.
     */
    public static final String MSG_KEY = "matchxpath.match";

    /** Xpath query. */
    private String query = "";

    /**
     * Setter to xpath query.
     *
     * @param query xpath query.
     */
    public void setQuery(String query) {
        this.query = query;
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
    public void beginTree(DetailAST rootAST) {
        if (!query.isEmpty()) {
            final List<DetailAST> matchingNodes = findMatchingNodesByXpathQuery(rootAST);
            matchingNodes.forEach(node -> log(node, MSG_KEY));
        }
    }

    private List<DetailAST> findMatchingNodesByXpathQuery(DetailAST rootAST) {
        final XPathEvaluator xpathEvaluator = new XPathEvaluator(Configuration.newConfiguration());
        try {
            final RootNode rootNode = new RootNode(rootAST);
            final XPathExpression xpathExpression = xpathEvaluator.createExpression(query);
            final XPathDynamicContext xpathDynamicContext =
                    xpathExpression.createDynamicContext(rootNode);
            final List<Item> matchingItems = xpathExpression.evaluate(xpathDynamicContext);

            return matchingItems.stream()
                    .map(item -> ((AbstractNode) item).getUnderlyingNode())
                    .collect(Collectors.toList());
        }
        catch (XPathException ex) {
            throw new IllegalStateException("Evaluation of Xpath query failed: " + query, ex);
        }
    }
}

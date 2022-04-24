////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
 * Evaluates Xpath query and report violation on all matching AST nodes. This check allows
 * user to implement custom checks using Xpath. If Xpath query is not specified explicitly,
 * then the check does nothing.
 * </p>
 * <p>
 * It is recommended to define custom message for violation to explain what is not allowed and what
 * to use instead, default message might be too abstract. To customize a message you need to
 * add {@code message} element with <b>matchxpath.match</b> as {@code key} attribute and
 * desired message as {@code value} attribute.
 * </p>
 * <p>
 * Please read more about Xpath syntax at
 * <a href="https://www.saxonica.com/html/documentation10/expressions/">Xpath Syntax</a>.
 * Information regarding Xpath functions can be found at
 * <a href="https://www.saxonica.com/html/documentation10/functions/fn/">XSLT/XPath Reference</a>.
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
 * <p>
 * Checkstyle provides <a href="https://checkstyle.org/cmdline.html">command line tool</a>
 * and <a href="https://checkstyle.org/writingchecks.html#The_Checkstyle_SDK_Gui">GUI
 * application</a> with options to show AST and to ease usage of Xpath queries.
 * </p>
 * <p><b>-T</b> option prints AST tree of the checked file.</p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar -T Main.java
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * |--MODIFIERS -&gt; MODIFIERS [1:0]
 * |   `--LITERAL_PUBLIC -&gt; public [1:0]
 * |--LITERAL_CLASS -&gt; class [1:7]
 * |--IDENT -&gt; Main [1:13]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:18]
 * |--LCURLY -&gt; { [1:18]
 * |--METHOD_DEF -&gt; METHOD_DEF [2:4]
 * |   |--MODIFIERS -&gt; MODIFIERS [2:4]
 * |   |   `--LITERAL_PUBLIC -&gt; public [2:4]
 * |   |--TYPE -&gt; TYPE [2:11]
 * |   |   `--IDENT -&gt; String [2:11]
 * |   |--IDENT -&gt; sayHello [2:18]
 * |   |--LPAREN -&gt; ( [2:26]
 * |   |--PARAMETERS -&gt; PARAMETERS [2:27]
 * |   |   `--PARAMETER_DEF -&gt; PARAMETER_DEF [2:27]
 * |   |       |--MODIFIERS -&gt; MODIFIERS [2:27]
 * |   |       |--TYPE -&gt; TYPE [2:27]
 * |   |       |   `--IDENT -&gt; String [2:27]
 * |   |       `--IDENT -&gt; name [2:34]
 * |   |--RPAREN -&gt; ) [2:38]
 * |   `--SLIST -&gt; { [2:40]
 * |       |--LITERAL_RETURN -&gt; return [3:8]
 * |       |   |--EXPR -&gt; EXPR [3:25]
 * |       |   |   `--PLUS -&gt; + [3:25]
 * |       |   |       |--STRING_LITERAL -&gt; "Hello, " [3:15]
 * |       |   |       `--IDENT -&gt; name [3:27]
 * |       |   `--SEMI -&gt; ; [3:31]
 * |       `--RCURLY -&gt; } [4:4]
 * `--RCURLY -&gt; } [5:0]
 * </pre>
 * <p><b>-b</b> option shows AST nodes that match given Xpath query. This command can be used to
 * validate accuracy of Xpath query against given file.</p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar Main.java -b "//METHOD_DEF[./IDENT[@text='sayHello']]"
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:18]
 * |--METHOD_DEF -&gt; METHOD_DEF [2:4]
 * </pre>
 * <p>
 * The following example demonstrates validation of methods order, so that public methods should
 * come before the private ones:
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//METHOD_DEF[.//LITERAL_PRIVATE and
 *  following-sibling::METHOD_DEF[.//LITERAL_PUBLIC]]"/&gt;
 *  &lt;message key=&quot;matchxpath.match&quot;
 *  value=&quot;Private methods must appear after public methods&quot;/&gt;
 * &lt;/module&gt;
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
 * To violate if there are any parametrized constructors
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//CTOR_DEF[count(./PARAMETERS/*) &gt; 0]"/&gt;
 *  &lt;message key=&quot;matchxpath.match&quot;
 *  value=&quot;Parameterized constructors are not allowed, there should be only default
 *  ctor&quot;/&gt;
 * &lt;/module&gt;
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
 * To violate if method name is 'test' or 'foo'
 * </p>
 * <pre>
 * &lt;module name="MatchXpath"&gt;
 *  &lt;property name="query" value="//METHOD_DEF[./IDENT[@text='test' or @text='foo']]"/&gt;
 *  &lt;message key=&quot;matchxpath.match&quot;
 *  value=&quot;Method name should not be 'test' or 'foo'&quot;/&gt;
 * &lt;/module&gt;
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
 * To violate if new instance creation was done without <b>var</b> type
 * </p>
 * <pre>
 * &lt;module name=&quot;MatchXpath&quot;&gt;
 *  &lt;property name=&quot;query&quot; value=&quot;//VARIABLE_DEF[./ASSIGN/EXPR/LITERAL_NEW
 *  and not(./TYPE/IDENT[@text='var'])]&quot;/&gt;
 *  &lt;message key=&quot;matchxpath.match&quot;
 *  value=&quot;New instances should be created via 'var' keyword to avoid duplication of type
 *  reference in statement&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class Test {
 *   public void foo() {
 *     SomeObject a = new SomeObject(); // violation
 *     var b = new SomeObject(); // OK
 *   }
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
     */
    public void setQuery(String query) {
        this.query = query;
        if (!query.isEmpty()) {
            try {
                final XPathEvaluator xpathEvaluator =
                        new XPathEvaluator(Configuration.newConfiguration());
                xpathExpression = xpathEvaluator.createExpression(query);
            }
            catch (XPathException ex) {
                throw new IllegalStateException("Creating Xpath expression failed: " + query, ex);
            }
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
        if (xpathExpression != null) {
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
                    .map(item -> ((AbstractNode) item).getUnderlyingNode())
                    .collect(Collectors.toList());
        }
        catch (XPathException ex) {
            throw new IllegalStateException("Evaluation of Xpath query failed: " + query, ex);
        }
    }
}

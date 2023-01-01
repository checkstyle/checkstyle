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

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.ExternalResourceHolder;
import com.puppycrawl.tools.checkstyle.utils.FilterUtil;

/**
 * <p>
 * Filter {@code SuppressionXpathFilter} works as
 * <a href="https://checkstyle.org/config_filters.html#SuppressionFilter">SuppressionFilter</a>.
 * Additionally, filter processes {@code suppress-xpath} elements,
 * which contains xpath-expressions. Xpath-expressions are queries for
 * suppressed nodes inside the AST tree.
 * </p>
 * <p>
 * Currently, filter does not support the following checks:
 * </p>
 * <ul id="SuppressionXpathFilter_IncompatibleChecks">
 * <li>
 * NoCodeInFile (reason is that AST is not generated for a file not containing code)
 * </li>
 * <li>
 * Regexp (reason is at
 * <a href="https://github.com/checkstyle/checkstyle/issues/7759#issuecomment-605525287"> #7759</a>)
 * </li>
 * <li>
 * RegexpSinglelineJava (reason is at
 * <a href="https://github.com/checkstyle/checkstyle/issues/7759#issuecomment-605525287"> #7759</a>)
 * </li>
 * </ul>
 * <p>
 * Also, the filter does not support suppressions inside javadoc reported by Javadoc checks:
 * </p>
 * <ul id="SuppressionXpathFilter_JavadocChecks">
 * <li>
 * AtclauseOrder
 * </li>
 * <li>
 * JavadocBlockTagLocation
 * </li>
 * <li>
 * JavadocMethod
 * </li>
 * <li>
 * JavadocMissingLeadingAsterisk
 * </li>
 * <li>
 * JavadocMissingWhitespaceAfterAsterisk
 * </li>
 * <li>
 * JavadocParagraph
 * </li>
 * <li>
 * JavadocStyle
 * </li>
 * <li>
 * JavadocTagContinuationIndentation
 * </li>
 * <li>
 * JavadocType
 * </li>
 * <li>
 * MissingDeprecated
 * </li>
 * <li>
 * NonEmptyAtclauseDescription
 * </li>
 * <li>
 * RequireEmptyLineBeforeBlockTagGroup
 * </li>
 * <li>
 * SingleLineJavadoc
 * </li>
 * <li>
 * SummaryJavadoc
 * </li>
 * <li>
 * WriteTag
 * </li>
 * </ul>
 * <p>
 * Note, that support for these Checks will be available after resolving issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/5770">#5770</a>.
 * </p>
 * <p>
 * Currently, filter supports the following xpath axes:
 * </p>
 * <ul>
 * <li>
 * ancestor
 * </li>
 * <li>
 * ancestor-or-self
 * </li>
 * <li>
 * attribute
 * </li>
 * <li>
 * child
 * </li>
 * <li>
 * descendant
 * </li>
 * <li>
 * descendant-or-self
 * </li>
 * <li>
 * following
 * </li>
 * <li>
 * following-sibling
 * </li>
 * <li>
 * parent
 * </li>
 * <li>
 * preceding
 * </li>
 * <li>
 * preceding-sibling
 * </li>
 * <li>
 * self
 * </li>
 * </ul>
 * <p>
 * You can use the command line helper tool to generate xpath suppressions based on your
 * configuration file and input files. See <a href="https://checkstyle.org/cmdline.html">here</a>
 * for more details.
 * </p>
 * <p>
 * The suppression file location is checked in following order:
 * </p>
 * <ol>
 * <li>
 * as a filesystem location
 * </li>
 * <li>
 * if no file found, and the location starts with either {@code http://} or {@code https://},
 * then it is interpreted as a URL
 * </li>
 * <li>
 * if no file found, then passed to the {@code ClassLoader.getResource()} method.
 * </li>
 * </ol>
 * <p>
 * SuppressionXpathFilter can suppress Checks that have Treewalker as parent module.
 * </p>
 * <ul>
 * <li>
 * Property {@code file} - Specify the location of the <em>suppressions XML document</em> file.
 * Type is {@code java.lang.String}.
 * Default value is {@code null}.
 * </li>
 * <li>
 * Property {@code optional} - Control what to do when the file is not existing.
 * If optional is set to false the file must exist, or else it ends with error.
 * On the other hand if optional is true and file is not found, the filter accepts all audit events.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * For example, the following configuration fragment directs the Checker to use a
 * {@code SuppressionXpathFilter} with suppressions file {@code config/suppressions.xml}:
 * </p>
 * <pre>
 * &lt;module name=&quot;SuppressionXpathFilter&quot;&gt;
 *   &lt;property name=&quot;file&quot; value=&quot;config/suppressions.xml&quot;/&gt;
 *   &lt;property name=&quot;optional&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * A <a href="https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd"><em>
 * suppressions XML document</em></a>
 * contains a set of {@code suppress} and {@code suppress-xpath} elements,
 * where each {@code suppress-xpath} element can have the following attributes:
 * </p>
 * <ul>
 * <li>
 * {@code files} - a <a href="https://checkstyle.org/property_types.html#Pattern">Pattern</a>
 * matched against the file name associated with an audit event. It is optional.
 * </li>
 * <li>
 * {@code checks} - a <a href="https://checkstyle.org/property_types.html#Pattern">Pattern</a>
 * matched against the name of the check associated with an audit event.
 * Optional as long as {@code id} or {@code message} is specified.
 * </li>
 * <li>
 * {@code message} - a <a href="https://checkstyle.org/property_types.html#Pattern">Pattern</a>
 * matched against the message of the check associated with an audit event.
 * Optional as long as {@code checks} or {@code id} is specified.
 * </li>
 * <li>
 * {@code id} - a <a href="https://checkstyle.org/property_types.html#String">String</a> matched against
 * the ID of the check associated with an audit event.
 * Optional as long as {@code checks} or {@code message} is specified.
 * </li>
 * <li>
 * {@code query} - a <a href="https://checkstyle.org/property_types.html#String">String</a> xpath query. It is optional.
 * </li>
 * </ul>
 * <p>
 * Each audit event is checked against each {@code suppress} and {@code suppress-xpath} element.
 * It is suppressed if all specified attributes match against the audit event.
 * </p>
 * <p>
 * ATTENTION: filtering by message is dependent on runtime locale.
 * If project is running in different languages it is better to avoid filtering by message.
 * </p>
 * <p>
 * The following suppressions XML document directs a {@code SuppressionXpathFilter} to reject
 * {@code CyclomaticComplexity} violations for all methods with name <i>sayHelloWorld</i> inside
 * <i>FileOne</i> and <i>FileTwo</i> files:
 * </p>
 * <p>
 * Currently, xpath queries support one type of attribute {@code @text}. {@code @text} -
 * addresses to the text value of the node. For example: variable name, annotation name,
 * text content, etc. Only the following token types support {@code @text} attribute:
 * {@code TokenTypes.IDENT}, {@code TokenTypes.STRING_LITERAL}, {@code TokenTypes.CHAR_LITERAL},
 * {@code TokenTypes.NUM_LONG}, {@code TokenTypes.NUM_INT}, {@code TokenTypes.NUM_DOUBLE},
 * {@code TokenTypes.NUM_FLOAT}.
 * These token types were selected because only their text values are different
 * in content from token type and represent text value from file and can be used
 * in xpath queries for more accurate results. Other token types always have constant values.
 * </p>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 *
 * &lt;!DOCTYPE suppressions PUBLIC
 * &quot;-//Checkstyle//DTD SuppressionXpathFilter Experimental Configuration 1.2//EN&quot;
 * &quot;https://checkstyle.org/dtds/suppressions_1_2_xpath_experimental.dtd&quot;&gt;
 *
 * &lt;suppressions&gt;
 *   &lt;suppress-xpath checks=&quot;CyclomaticComplexity&quot;
 *   files=&quot;FileOne.java,FileTwo.java&quot;
 *   query=&quot;//METHOD_DEF[./IDENT[@text='sayHelloWorld']]&quot;/&gt;
 * &lt;/suppressions&gt;
 * </pre>
 * <p>
 * Suppress checks for package definitions:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=".*" query="/PACKAGE_DEF"/&gt;
 * </pre>
 * <p>
 * Suppress checks for parent element of the first variable definition:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=".*" query="(//VARIABLE_DEF)[1]/.."/&gt;
 * </pre>
 * <p>
 * Suppress checks for elements which are either class definitions, either method definitions.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=".*" query="//CLASS_DEF | //METHOD_DEF"/&gt;
 * </pre>
 * <p>
 * Suppress checks for certain methods:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=&quot;.*&quot; query=&quot;//METHOD_DEF[./IDENT[@text='getSomeVar'
 *           or @text='setSomeVar']]&quot;/&gt;
 * </pre>
 * <p>
 * Suppress checks for variable <i>testVariable</i> inside <i>testMethod</i>
 * method inside <i>TestClass</i> class.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=&quot;.*&quot; query=&quot;//CLASS_DEF[@text='TestClass']
 *           //METHOD_DEF[./IDENT[@text='testMethod']]
 *           //VARIABLE_DEF[./IDENT[@text='testVariable']]&quot;/&gt;
 * </pre>
 * <p>
 * In the following sample, violations for {@code LeftCurly} check will be suppressed
 * for classes with name <i>Main</i> or for methods with name <i>calculate</i>.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=&quot;LeftCurly&quot; query=&quot;//CLASS_DEF[./IDENT[@text='Main']]//*
 *           | //METHOD_DEF[./IDENT[@text='calculate']]/*&quot;/&gt;
 * </pre>
 * <p>
 * The following example demonstrates how to suppress {@code RequireThis} violations
 * for variable <i>age</i> inside <i>changeAge</i> method.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=&quot;RequireThis&quot;
 *      query=&quot;//CLASS_DEF[./IDENT[@text='InputTest']]
 *           //METHOD_DEF[./IDENT[@text='changeAge']]//ASSIGN/IDENT[@text='age']&quot;/&gt;
 * </pre>
 * <pre>
 * public class InputTest {
 *   private int age = 23;
 *
 *   public void changeAge() {
 *     age = 24; //violation will be suppressed
 *   }
 * }
 * </pre>
 * <p>
 * Suppress {@code IllegalThrows} violations only for methods with name <i>throwsMethod</i>
 * and only for {@code RuntimeException} exceptions. Double colon is used for axis iterations.
 * In the following example {@code ancestor} axis is used to iterate all ancestor nodes
 * of the current node with type {@code METHOD_DEF} and name <i>throwsMethod</i>.
 * Please read more about xpath axes at <a href="https://www.w3schools.com/xml/xpath_axes.asp">
 * W3Schools Xpath Axes</a>.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks="IllegalThrows" query="//LITERAL_THROWS
 *           /IDENT[@text='RuntimeException' and
 *           ./ancestor::METHOD_DEF[./IDENT[@text='throwsMethod']]]"/&gt;
 * </pre>
 * <pre>
 * public class InputTest {
 *   public void throwsMethod() throws RuntimeException { // violation will be suppressed
 *   }
 *
 *   public void sampleMethod() throws RuntimeException { // will throw violation here
 *   }
 * }
 * </pre>
 * <p>
 * The following sample demonstrates how to suppress all violations for method
 * itself and all descendants. {@code descendant-or-self} axis iterates through
 * current node and all children nodes at any level. Keyword {@code node()}
 * selects node elements. Please read more about xpath syntax at
 * <a href="https://www.w3schools.com/xml/xpath_syntax.asp">W3Schools Xpath Syntax</a>.
 * </p>
 * <pre>
 * &lt;suppress-xpath checks=".*" query="//METHOD_DEF[./IDENT[@text='legacyMethod']]
 *           /descendant-or-self::node()"/&gt;
 * </pre>
 * <p>
 * Some elements can be suppressed in different ways. For example, to suppress
 * violation on variable {@code wordCount} in following code:
 * </p>
 * <pre>
 * public class InputTest {
 *     private int wordCount = 11;
 * }
 * </pre>
 * <p>
 * You need to look at AST of such code by our CLI tool:
 * </p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar -t InputTest.java
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * |--MODIFIERS -&gt; MODIFIERS [1:0]
 * |   `--LITERAL_PUBLIC -&gt; public [1:0]
 * |--LITERAL_CLASS -&gt; class [1:7]
 * |--IDENT -&gt; InputTest [1:13]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:23]
 * |--LCURLY -&gt; { [1:23]
 * |--VARIABLE_DEF -&gt; VARIABLE_DEF [2:4]
 * |   |--MODIFIERS -&gt; MODIFIERS [2:4]
 * |   |   `--LITERAL_PRIVATE -&gt; private [2:4]
 * |   |--TYPE -&gt; TYPE [2:12]
 * |   |   `--LITERAL_INT -&gt; int [2:12]
 * |   |--IDENT -&gt; wordCount [2:16]
 * |   |--ASSIGN -&gt; = [2:26]
 * |   |   `--EXPR -&gt; EXPR [2:28]
 * |   |       `--NUM_INT -&gt; 11 [2:28]
 * |   `--SEMI -&gt; ; [2:30]
 * `--RCURLY -&gt; } [3:0]
 * </pre>
 * <p>
 * The easiest way is to suppress by variable name. As you can see {@code VARIABLE_DEF}
 * node refers to variable declaration statement and has child node with token type
 * {@code IDENT} which is used for storing class, method, variable names.
 * </p>
 * <p>
 * The following example demonstrates how variable can be queried by its name:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks="." query="//VARIABLE_DEF[
 *             ./IDENT[@text='wordCount']]"/&gt;
 * </pre>
 * <p>
 * Another way is to suppress by variable value. Again, if you look at the printed
 * AST tree above, you will notice that one of the grandchildren of {@code VARIABLE_DEF}
 * node is responsible for storing variable value -{@code NUM_INT} with value <b>11</b>.
 * </p>
 * <p>
 * The following example demonstrates how variable can be queried by its value,
 * same approach applies to {@code String, char, float, double, int, long} data types:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks="." query="//VARIABLE_DEF[.//NUM_INT[@text=11]]"/&gt;
 * </pre>
 * <p>
 * Next example is about suppressing method with certain annotation by its name and element value.
 * </p>
 * <pre>
 * public class InputTest {
 *             &#64;Generated("first") // should not be suppressed
 *             public void test1() {
 *             }
 *
 *             &#64;Generated("second") // should be suppressed
 *             public void test2() {
 *             }
 *         }
 * </pre>
 * <p>
 * First of all we need to look at AST tree printed by our CLI tool:
 * </p>
 * <pre>
 * $ java -jar checkstyle-X.XX-all.jar -t InputTest.java
 * CLASS_DEF -&gt; CLASS_DEF [1:0]
 * |--MODIFIERS -&gt; MODIFIERS [1:0]
 * |   `--LITERAL_PUBLIC -&gt; public [1:0]
 * |--LITERAL_CLASS -&gt; class [1:7]
 * |--IDENT -&gt; InputTest [1:13]
 * `--OBJBLOCK -&gt; OBJBLOCK [1:23]
 * |--LCURLY -&gt; { [1:23]
 * |--METHOD_DEF -&gt; METHOD_DEF [2:4]
 * |   |--MODIFIERS -&gt; MODIFIERS [2:4]
 * |   |   |--ANNOTATION -&gt; ANNOTATION [2:4]
 * |   |   |   |--AT -&gt; @ [2:4]
 * |   |   |   |--IDENT -&gt; Generated [2:5]
 * |   |   |   |--LPAREN -&gt; ( [2:14]
 * |   |   |   |--EXPR -&gt; EXPR [2:15]
 * |   |   |   |   `--STRING_LITERAL -&gt; "first" [2:15]
 * |   |   |   `--RPAREN -&gt; ) [2:22]
 * |   |   `--LITERAL_PUBLIC -&gt; public [3:4]
 * |   |--TYPE -&gt; TYPE [3:11]
 * |   |   `--LITERAL_VOID -&gt; void [3:11]
 * |   |--IDENT -&gt; test1 [3:16]
 * |   |--LPAREN -&gt; ( [3:21]
 * |   |--PARAMETERS -&gt; PARAMETERS [3:22]
 * |   |--RPAREN -&gt; ) [3:22]
 * |   `--SLIST -&gt; { [3:24]
 * |       `--RCURLY -&gt; } [4:4]
 * |--METHOD_DEF -&gt; METHOD_DEF [6:4]
 * |   |--MODIFIERS -&gt; MODIFIERS [6:4]
 * |   |   |--ANNOTATION -&gt; ANNOTATION [6:4]
 * |   |   |   |--AT -&gt; @ [6:4]
 * |   |   |   |--IDENT -&gt; Generated [6:5]
 * |   |   |   |--LPAREN -&gt; ( [6:14]
 * |   |   |   |--EXPR -&gt; EXPR [6:15]
 * |   |   |   |   `--STRING_LITERAL -&gt; "second" [6:15]
 * |   |   |   `--RPAREN -&gt; ) [6:23]
 * |   |   `--LITERAL_PUBLIC -&gt; public [7:4]
 * |   |--TYPE -&gt; TYPE [7:11]
 * |   |   `--LITERAL_VOID -&gt; void [7:11]
 * |   |--IDENT -&gt; test2 [7:16]
 * |   |--LPAREN -&gt; ( [7:21]
 * |   |--PARAMETERS -&gt; PARAMETERS [7:22]
 * |   |--RPAREN -&gt; ) [7:22]
 * |   `--SLIST -&gt; { [7:24]
 * |       `--RCURLY -&gt; } [8:4]
 * `--RCURLY -&gt; } [9:0]
 * </pre>
 * <p>
 * AST node {@code ANNOTATION -> ANNOTATION [6:4]} has direct child
 * {@code IDENT -> Generated [6:5]}, therefore can be queried by {@code IDENT} value:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks="." query="//METHOD_DEF[
 *             .//ANNOTATION/IDENT[@text='Generated']]"/&gt;
 * </pre>
 * <p>
 * The problem with query above that it will suppress violations for all methods
 * with annotation {@code @Generated}. In order to suppress methods with
 * {@code @Generated("second")} annotations only, you need to look at AST tree again.
 * Value of the {@code ANNOTATION} node is stored inside sub-node with token type
 * {@code STRING_LITERAL}. Use the following query to suppress methods with
 * {@code @Generated("second")} annotation:
 * </p>
 * <pre>
 * &lt;suppress-xpath checks="." query="//METHOD_DEF[.//ANNOTATION[
 *             ./IDENT[@text='Generated'] and ./EXPR/STRING_LITERAL[@text='second']]]"/&gt;
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * @since 8.6
 */
public class SuppressionXpathFilter extends AutomaticBean implements
        TreeWalkerFilter, ExternalResourceHolder {

    /** Set of individual xpath suppresses. */
    private final Set<TreeWalkerFilter> filters = new HashSet<>();

    /** Specify the location of the <em>suppressions XML document</em> file. */
    private String file;
    /**
     * Control what to do when the file is not existing.
     * If optional is set to false the file must exist, or else it ends with error.
     * On the other hand if optional is true and file is not found,
     * the filter accepts all audit events.
     */
    private boolean optional;

    /**
     * Setter to specify the location of the <em>suppressions XML document</em> file.
     *
     * @param fileName name of the suppressions file.
     */
    public void setFile(String fileName) {
        file = fileName;
    }

    /**
     * Setter to control what to do when the file is not existing.
     * If optional is set to false the file must exist, or else it ends with error.
     * On the other hand if optional is true and file is not found,
     * the filter accepts all audit events.
     *
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
                if (FilterUtil.isFileExists(file)) {
                    filters.addAll(SuppressionsLoader.loadXpathSuppressions(file));
                }
            }
            else {
                filters.addAll(SuppressionsLoader.loadXpathSuppressions(file));
            }
        }
    }

}

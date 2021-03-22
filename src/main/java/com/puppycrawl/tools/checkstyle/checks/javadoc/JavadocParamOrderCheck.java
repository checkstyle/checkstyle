////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * <p>
 * Checks that the parameter order in javadoc is matching with the parameter order in class,
 * method, generic methods, annotations, interface, records, constructors.
 * It also includes type parameters.
 * </p>
 * <p>
 * Examples:
 * </p>
 * <ul>
 * <li>
 * Example of method parameters:
 * <pre>
 * class methodParams {
 *   &#47;**
 *    * Description
 *    *
 *    * &#64;param input description
 *    * &#64;param output desc
 *    *&#47;
 *   public void method(String input, int output) {
 *   }
 * }
 * </pre>
 * </li>
 * <li>
 * Example of type parameters of generic method:
 * <pre>
 * class genericMethodParams {
 *   &#47;**
 *    * Description.
 *    *
 *    * &#64;version Some number
 *    * &#64;param &lt;T&gt; description
 *    * &#64;param p1 desc
 *    * &#64;return p1 desc
 *    * &#64;see more link.
 *    *&#47;
 *   public &lt;T&gt; String genericMethod (T p1) {
 *     return p1;
 *   }
 * }
 * </pre>
 * </li>
 * </ul>
 * <p>
 * Notes:
 * </p>
 * <ul>
 * <li>
 * This check will only report if the parameter in type or method definition did not match the
 * parameter in javadoc.
 * </li>
 * <li>This check does not validate the presence of {@code @param} tags,
 * it only validates that existing tags are in the order that they appear in the
 * type or method definition. Use <a href="config_javadoc.html#JavadocMethod">
 * JavadocMethod</a> check to validate that all tags are present.
 * </li>
 * <li>
 * This check will not report any violations for missing parameter in javadoc.
 * </li>
 * <li>
 * This check will not report any violations for extra parameter in javadoc.
 * </li>
 * </ul>
 * <ul>
 * <li>
 * Property {@code violateExecutionOnNonTightHtml} - Control when to print violations if the
 * Javadoc being examined by this check violates the tight html rules defined at
 * <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">Tight-HTML Rules</a>.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the default check the order of parameter in type or method definition and
 * in javadoc:
 * </p>
 * <pre>
 * &lt;module name=&quot;JavadocParamOrder&quot;/&gt;
 * </pre>
 * <p>
 * Examples:
 * </p>
 * <pre>
 * class example1 {
 *   &#47;**
 *    * Description.
 *    *
 *    * &#64;version Some javadoc.
 *    * &#64;param &lt;T&gt; description
 *    * &#64;param E desc          // violation, wrong order for "E" tag
 *    * &#64;param &lt;S&gt; desc
 *    * &#64;param array desc
 *    * &#64;see Some javadoc
 *    * &#64;param e desc          // violation, wrong order for "e" tag
 *    * &#64;param str desc        // violation, wrong order for "str" tag
 *    * &#64;return Some number.
 *    *&#47;
 *   public static &lt;T, E, S&gt; int sampleMethod(T[] array, String str, E e ) {
 *     return 2021;
 *   }
 * }
 * </pre>
 * <p>
 * This check will not report any violations for missing parameter in javadoc.
 * </p>
 * <pre>
 * class example2 {
 *   &#47;**
 *    * No violation for missing "str" parameter.
 *    *
 *    * &#64;version Some javadoc.
 *    * &#64;param &lt;T&gt; description
 *    * &#64;param E desc          // violation, wrong order for "E" tag
 *    * &#64;param &lt;S&gt; desc
 *    * &#64;param array desc
 *    * &#64;see Some javadoc
 *    * &#64;param e desc          // violation, wrong order for "e" tag
 *    * &#64;return Some number.
 *    *&#47;
 *   public static &lt;T, E, S&gt; int sampleMethod(T[] array, String str, E e) {
 *     return 2021;
 *   }
 *
 *   &#47;**
 *    * No violation for missing "output" parameter.
 *    *
 *    * &#64;param input desc      // ok
 *    *&#47;
 *   public void sampleMethod2(String input, String output) {
 *   }
 * }
 * </pre>
 * <p>
 * This check will not report any violations for extra parameter in javadoc.
 * </p>
 * <pre>
 * class example3 {
 *   &#47;**
 *    * No violation for extra "extra" parameter.
 *    *
 *    * &#64;version Some javadoc.
 *    * &#64;param &lt;T&gt; description
 *    * &#64;param E desc          // violation, wrong order for "E" tag
 *    * &#64;param &lt;S&gt; desc
 *    * &#64;param array desc
 *    * &#64;see Some javadoc
 *    * &#64;param e desc          // violation, wrong order for "e" tag
 *    * &#64;param str desc        // violation, wrong order for "str" tag
 *    * &#64;param extra desc      // ok, Extra parameter
 *    * &#64;return Some number
 *    *&#47;
 *   public static &lt;T, E, S&gt; int sampleMethod(T[] array, String str, E e ) {
 *     return 2021;
 *   }
 *
 *   &#47;**
 *    * No violation for extra "t" parameter.
 *    *
 *    * &#64;param t desc          // ok, Extra parameter
 *    *&#47;
 *   public void sampleMethod2() {}
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
 * {@code javadoc.missed.html.close}
 * </li>
 * <li>
 * {@code javadoc.parse.rule.error}
 * </li>
 * <li>
 * {@code javadoc.tag.order}
 * </li>
 * <li>
 * {@code javadoc.wrong.singleton.html.tag}
 * </li>
 * </ul>
 *
 * @since 8.43
 */
@StatelessCheck
public class JavadocParamOrderCheck extends AbstractJavadocCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_JAVADOC_TAG_ORDER = "javadoc.tag.order";

    /**
     * Specify the list.
     */
    private final List<Integer> targetParent = Arrays.asList(
        TokenTypes.INTERFACE_DEF,
        TokenTypes.RECORD_DEF,
        TokenTypes.MODIFIERS,
        TokenTypes.TYPE,
        TokenTypes.TYPE_PARAMETERS,
        TokenTypes.CTOR_DEF
    );

    /**
     * Specify the list.
     */
    private final List<Integer> targetSibiling = Arrays.asList(
        TokenTypes.PARAMETERS,
        TokenTypes.RECORD_COMPONENTS,
        TokenTypes.INTERFACE_DEF
    );

    @Override
    public int[] getDefaultJavadocTokens() {
        return new int[] {
            JavadocTokenTypes.JAVADOC,
        };
    }

    @Override
    public int[] getRequiredJavadocTokens() {
        return getAcceptableJavadocTokens();
    }

    @Override
    public void visitJavadocToken(DetailNode node) {
        DetailAST ast = getBlockCommentAst().getParent();

        if (ast != null) {
            if (ast.getType() == TokenTypes.ANNOTATION) {
                ast = ast.getParent();
            }
            if (targetParent.contains(ast.getType())) {
                DetailAST typeDef = ast.getParent();
                final DetailAST prevSib = getBlockCommentAst().getParent();
                if (prevSib.getFirstChild() != null && prevSib.getFirstChild().getType()
                        == TokenTypes.MODIFIERS) {
                    typeDef = ast;
                }
                for (Integer type : targetSibiling) {
                    if (typeDef.getType() == type) {
                        final List<DetailAST> methodTypeParams =
                            CheckUtil.getTypeParameters(typeDef);
                        checkParamsOrder(node, new ArrayList<>(), methodTypeParams);
                    }
                    if (typeDef.findFirstToken(type) != null) {
                        final List<DetailAST> methodParams =
                            getParameters(typeDef.findFirstToken(type));
                        final List<DetailAST> methodTypeParams =
                            CheckUtil.getTypeParameters(typeDef);
                        checkParamsOrder(node, methodParams, methodTypeParams);
                    }
                }
            }
        }

    }

    /**
     * Checks order of param tags for matching method parameters.
     *
     * @param tagNode javadoc root node.
     * @param methodParams list of method parameters.
     * @param methodTypeParams list of method type parameters.
     */
    private void checkParamsOrder(DetailNode tagNode,
                                  final List<DetailAST> methodParams,
                                  final List<DetailAST> methodTypeParams) {
        final int listSize = methodTypeParams.size();
        methodTypeParams.addAll(methodParams);
        final ListIterator<DetailAST> typeParamsIt = methodTypeParams.listIterator();
        int count = 0;
        DetailNode javadocTagSection =
            JavadocUtil.findFirstToken(tagNode, JavadocTokenTypes.JAVADOC_TAG);

        while (javadocTagSection != null && typeParamsIt.hasNext()) {
            for (DetailNode child : javadocTagSection.getChildren()) {
                if (child.getType() == JavadocTokenTypes.PARAMETER_NAME) {
                    count++;
                    final String tagText = child.getText();
                    final DetailAST typeParam = typeParamsIt.next();
                    final String param = typeParam.findFirstToken(TokenTypes.IDENT).getText();

                    if (count <= listSize) {
                        final boolean flag = CommonUtil.startsWithChar(tagText, '<')
                            && CommonUtil.endsWithChar(tagText, '>');
                        if (!flag || !param.equals(tagText.substring(1, tagText.length() - 1))) {
                            log(child.getLineNumber(), MSG_JAVADOC_TAG_ORDER, tagText);
                        }
                    }
                    else {
                        if (!param.equals(tagText)) {
                            log(child.getLineNumber(), MSG_JAVADOC_TAG_ORDER, tagText);
                        }
                    }
                }
            }
            javadocTagSection = JavadocUtil.getNextSibling(
                javadocTagSection, JavadocTokenTypes.JAVADOC_TAG);
        }
    }

    /**
     * Computes the parameter nodes for a record.
     *
     * @param ast the method node.
     * @return the list of parameter nodes for ast.
     */
    private static List<DetailAST> getParameters(DetailAST ast) {
        final List<DetailAST> returnValue = new ArrayList<>();

        DetailAST child = ast.getFirstChild();
        while (child != null) {
            if (child.getType() == ast.getFirstChild().getType()) {
                returnValue.add(child);
            }
            child = child.getNextSibling();
        }
        return returnValue;
    }
}

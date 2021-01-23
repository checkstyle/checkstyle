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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that annotations are located on the same line with their targets.
 * Verifying with this check is not good practice, but it is using by some style guides.
 * </p>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#COMPACT_CTOR_DEF">
 * COMPACT_CTOR_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;AnnotationOnSameLine&quot;/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Foo {
 *
 *   &#64;SuppressWarnings("deprecation")  // violation, annotation should be on the same line
 *   public Foo() {
 *   }
 *
 *   &#64;SuppressWarnings("unchecked") public void fun2() {  // OK
 *   }
 *
 * }
 *
 * &#64;SuppressWarnings("unchecked") class Bar extends Foo {  // OK
 *
 *   &#64;Deprecated public Bar() {  // OK
 *   }
 *
 *   &#64;Override  // violation, annotation should be on the same line
 *   public void fun1() {
 *   }
 *
 *   &#64;Before &#64;Override public void fun2() {  // OK
 *   }
 *
 *   &#64;SuppressWarnings("deprecation")  // violation, annotation should be on the same line
 *   &#64;Before public void fun3() {
 *   }
 *
 * }
 * </pre>
 * <p>
 * To configure the check to check for annotations applied on
 * interfaces, variables and constructors:
 * </p>
 * <pre>
 * &lt;module name=&quot;AnnotationOnSameLine&quot;&gt;
 *   &lt;property name=&quot;tokens&quot;
 *       value=&quot;INTERFACE_DEF, VARIABLE_DEF, CTOR_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * &#64;Deprecated interface Foo {  // OK
 *
 *   void doSomething();
 *
 * }
 *
 * class Bar implements Foo {
 *
 *   &#64;SuppressWarnings("deprecation")  // violation, annotation should be on the same line
 *   public Bar() {
 *   }
 *
 *   &#64;Override  // OK
 *   public void doSomething() {
 *   }
 *
 *   &#64;Nullable  // violation, annotation should be on the same line
 *   String s;
 *
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
 * {@code annotation.same.line}
 * </li>
 * </ul>
 *
 * @since 8.2
 */
@StatelessCheck
public class AnnotationOnSameLineCheck extends AbstractCheck {

    /** A key is pointing to the warning message text in "messages.properties" file. */
    public static final String MSG_KEY_ANNOTATION_ON_SAME_LINE = "annotation.same.line";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.IMPLEMENTS_CLAUSE,
            TokenTypes.TYPE_ARGUMENT,
            TokenTypes.LITERAL_NEW,
            TokenTypes.DOT,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST nodeWithAnnotations = ast;
        if (ast.getType() == TokenTypes.TYPECAST) {
            nodeWithAnnotations = ast.findFirstToken(TokenTypes.TYPE);
        }
        DetailAST modifiersNode = nodeWithAnnotations.findFirstToken(TokenTypes.MODIFIERS);
        if (modifiersNode == null) {
            modifiersNode = nodeWithAnnotations.findFirstToken(TokenTypes.ANNOTATIONS);
        }
        if (modifiersNode != null) {
            for (DetailAST annotationNode = modifiersNode.getFirstChild();
                    annotationNode != null;
                    annotationNode = annotationNode.getNextSibling()) {
                if (annotationNode.getType() == TokenTypes.ANNOTATION
                        && !TokenUtil.areOnSameLine(annotationNode, getNextNode(annotationNode))) {
                    log(annotationNode, MSG_KEY_ANNOTATION_ON_SAME_LINE,
                          getAnnotationName(annotationNode));
                }
            }
        }
    }

    /**
     * Finds next node of ast tree.
     *
     * @param node current node
     * @return node that is next to given
     */
    private static DetailAST getNextNode(DetailAST node) {
        DetailAST nextNode = node.getNextSibling();
        if (nextNode == null) {
            nextNode = node.getParent().getNextSibling();
        }
        return nextNode;
    }

    /**
     * Returns the name of the given annotation.
     *
     * @param annotation annotation node.
     * @return annotation name.
     */
    private static String getAnnotationName(DetailAST annotation) {
        DetailAST identNode = annotation.findFirstToken(TokenTypes.IDENT);
        if (identNode == null) {
            identNode = annotation.findFirstToken(TokenTypes.DOT).getLastChild();
        }
        return identNode.getText();
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Verifies that annotations are properly placed on their target nodes.
 * Declaration annotations must either reside entirely on a single line or
 * have each annotation placed on its own separate line. Annotations should
 * not share a line with the target declaration, except for single-line methods.
 * This convention follows the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-annotations">
 * OpenJDK Style Guide</a>.
 * </div>
 *
 * <p>
 * Attention: Elements that cannot have JavaDoc comments like local variables are not in the
 * scope of this check even though a token type like {@code VARIABLE_DEF} would match them.
 * </p>
 *
 * <p>
 * Attention: Annotations among modifiers are ignored (looks like false-negative)
 * as there might be a problem with annotations for return types:
 * </p>
 * <div class="wrapper"><pre class="prettyprint"><code class="language-java">
 * public @Nullable Long getStartTimeOrNull() { ... }
 * </code></pre></div>
 *
 * @since 13.9.0
 */
@StatelessCheck
public class OpenjdkAnnotationLocationCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_ALONE_OR_SAME = "annotation.alone.or.same";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ANNOTATION_ON_TARGET_LINE = "annotation.on.target.line";

    /**
     * Creates a new {@code OpenjdkAnnotationLocationCheck} instance.
     */
    public OpenjdkAnnotationLocationCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
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
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ANNOTATION_DEF,
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
        if (isAllowedAst(ast)) {
            final DetailAST annotationParentNode = getAnnotationsNode(ast);
            DetailAST annotation = annotationParentNode.getFirstChild();
            boolean isSingleLine = true;
            boolean isSameLine = true;
            final DetailAST startOfTargetNode = getStartingAst(annotationParentNode);
            final DetailAST firstAnnotation = annotation;
            while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
                if (TokenUtil.areOnSameLine(annotation, startOfTargetNode)
                        && !isSingleLineMethod(ast, startOfTargetNode)) {
                    log(annotation, MSG_KEY_ANNOTATION_ON_TARGET_LINE,
                            getAnnotationName(annotation));
                }

                isSingleLine = isSingleLine && !hasNodeBeside(annotation);
                isSameLine = isSameLine && firstAnnotation.getLineNo() == annotation.getLineNo();

                if (!isSingleLine && !isSameLine) {
                    log(annotation, MSG_KEY_ANNOTATION_ALONE_OR_SAME,
                            getAnnotationName(annotation));
                }
                annotation = annotation.getNextSibling();
            }
        }
    }

    /**
     * Checks whether the token is allowed to be checked.
     *
     * @param ast token.
     * @return true if the token is allowed to be checked.
     */
    private static boolean isAllowedAst(DetailAST ast) {
        // ignore variable def tokens that are not field definitions
        return ast.getType() != TokenTypes.VARIABLE_DEF
                || ast.getParent().getType() == TokenTypes.OBJBLOCK
                || ast.getParent().getType() == TokenTypes.COMPACT_COMPILATION_UNIT;
    }

    /**
     * Gets the parent node of annotations.
     *
     * @param ast token.
     * @return the parent of annotations.
     */
    private static DetailAST getAnnotationsNode(DetailAST ast) {
        DetailAST annotationParentNode = ast.findFirstToken(TokenTypes.MODIFIERS);
        if (annotationParentNode == null) {
            annotationParentNode = ast.findFirstToken(TokenTypes.ANNOTATIONS);
        }
        return annotationParentNode;
    }

    /**
     * Checks whether an annotation node has any node before on the same line.
     *
     * @param annotation annotation node.
     * @return true if an annotation node has any node before on the same line.
     */
    private static boolean hasNodeBefore(DetailAST annotation) {
        final int annotationLineNo = annotation.getLineNo();
        final DetailAST previousNode = annotation.getPreviousSibling();

        return previousNode != null && annotationLineNo == previousNode.getLineNo();
    }

    /**
     * Checks whether an annotation node has any node before or after on the same line.
     *
     * @param annotation annotation node.
     * @return true if an annotation node has any node before or after on the same line.
     */
    private static boolean hasNodeBeside(DetailAST annotation) {
        return hasNodeBefore(annotation) || hasNodeAfter(annotation);
    }

    /**
     * Checks whether an annotation node has any node after on the same line.
     *
     * @param annotation annotation node.
     * @return true if an annotation node has any node after on the same line.
     */
    private static boolean hasNodeAfter(DetailAST annotation) {
        final int annotationLineNo = annotation.getLineNo();
        DetailAST nextNode = annotation.getNextSibling();

        if (nextNode == null) {
            nextNode = annotation.getParent().getNextSibling();
        }

        return annotationLineNo == nextNode.getLineNo();
    }

    /**
     * Finds the first node other than the annotation node in target ast.
     *
     * @param targetNode target node.
     * @return the ast of the starting point
     */
    private static DetailAST getStartingAst(DetailAST targetNode) {
        DetailAST annotation = targetNode.getFirstChild();
        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            annotation = annotation.getNextSibling();
        }

        final DetailAST startingAst;
        if (annotation != null) {
            startingAst = annotation;
        }
        else {
            startingAst = targetNode.getNextSibling();
        }
        return startingAst;
    }

    /**
     * Checks whether a method is single line or not.
     *
     * @param targetNode ast of target node.
     * @param startOfTargetNode ast of starting point of target node.
     * @return true if the method is single line.
     */
    private static boolean isSingleLineMethod(DetailAST targetNode, DetailAST startOfTargetNode) {
        boolean result = false;
        if (targetNode.getType() == TokenTypes.METHOD_DEF
                || targetNode.getType() == TokenTypes.ANNOTATION_FIELD_DEF) {
            final DetailAST lastToken = targetNode.getLastChild();
            if (lastToken.getType() == TokenTypes.SEMI) {
                result = TokenUtil.areOnSameLine(startOfTargetNode, lastToken);
            }
            else {
                final DetailAST rightCurly = lastToken.getLastChild();
                result = TokenUtil.areOnSameLine(startOfTargetNode, rightCurly);
            }
        }

        return result;
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
            identNode = annotation.findFirstToken(TokenTypes.DOT).findFirstToken(TokenTypes.IDENT);
        }
        return identNode.getText();
    }

}

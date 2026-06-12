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
 * Checks location of annotation on language elements.
 * </div>
 *
 * <p>
 * This check verifies that annotations are located according to the OpenJDK style guide.
 * Annotations should be located on separate lines from the target element.
 * An exception is made for a single parameterless annotation, which can be located on the same
 * line as a single-line method.
 * </p>
 *
 * <p>
 * Multiple annotations must either be all on separate lines or all on the same line.
 * </p>
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
 * @since 13.7.0
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
        // ignore variable def tokens that are not field definitions
        if (ast.getType() != TokenTypes.VARIABLE_DEF
                || ast.getParent().getType() == TokenTypes.OBJBLOCK) {
            DetailAST node = ast.findFirstToken(TokenTypes.MODIFIERS);
            if (node == null) {
                node = ast.findFirstToken(TokenTypes.ANNOTATIONS);
            }
            checkAnnotationsGrouping(node, ast);
        }
    }

    /**
     * Checks whether the annotation locations are correct or not.
     *
     * @param modifierAst modifiers node.
     * @param parentAst parent node.
     */
    private void checkAnnotationsGrouping(DetailAST modifierAst, DetailAST parentAst) {

        DetailAST annotation = modifierAst.getFirstChild();
        boolean isSingleLine = true;
        boolean isSameLine = true;
        final boolean isAllowedForTargetNode = isAllowedForTargetNode(modifierAst, parentAst);
        final DetailAST startOfTargetNode = getStartingAst(modifierAst);

        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {

            if (TokenUtil.areOnSameLine(annotation, startOfTargetNode)
                        && !(isAllowedForTargetNode && !isParameterized(annotation))) {

                log(annotation, MSG_KEY_ANNOTATION_ON_TARGET_LINE, getAnnotationName(annotation));
            }

            isSingleLine = isSingleLine && !hasNodeBeside(annotation);
            isSameLine = isSameLine && hasNodeBeside(annotation);

            if (!isSingleLine && !isSameLine) {
                log(annotation, MSG_KEY_ANNOTATION_ALONE_OR_SAME, getAnnotationName(annotation));
            }
            annotation = annotation.getNextSibling();
        }
    }

    /**
     * Checks if the target node is allowed to have an annotation on the same line.
     *
     * @param modifierAst modifiers node.
     * @param parentAst parent node.
     * @return true if the target node is allowed to have an annotation on the same line.
     */
    private static boolean isAllowedForTargetNode(DetailAST modifierAst, DetailAST parentAst) {
        return isSingleLineMethod(parentAst) && countAnnotation(modifierAst) <= 1;
    }

    /**
     * Count the number of annotations.
     *
     * @param modifierAst target node.
     * @return count of annotations.
     */
    private static int countAnnotation(DetailAST modifierAst) {
        int count = 0;
        DetailAST annotation = modifierAst.getFirstChild();
        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            count++;
            annotation = annotation.getNextSibling();
        }
        return count;
    }

    /**
     * Checks whether an annotation has parameters.
     *
     * @param annotation annotation node.
     * @return true if the annotation has parameters.
     */
    private static boolean isParameterized(DetailAST annotation) {
        return TokenUtil.findFirstTokenByPredicate(annotation, ast -> {
            return ast.getType() == TokenTypes.EXPR
                || ast.getType() == TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR;
        }).isPresent();
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
        final DetailAST nextNode = annotation.getNextSibling();

        return nextNode != null
                    && nextNode.getType() == TokenTypes.ANNOTATION
                    && annotationLineNo == nextNode.getLineNo();
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
     * @return true if targetNode is a single line method.
     */
    private static boolean isSingleLineMethod(DetailAST targetNode) {
        boolean result = false;
        if (targetNode.getType() == TokenTypes.METHOD_DEF
                || targetNode.getType() == TokenTypes.ANNOTATION_FIELD_DEF) {
            final DetailAST lastToken = targetNode.getLastChild();
            if (lastToken.getType() == TokenTypes.SEMI) {
                result = TokenUtil.areOnSameLine(targetNode, lastToken);
            }
            else {
                final DetailAST rightCurly = lastToken.getLastChild();
                result = TokenUtil.areOnSameLine(targetNode, rightCurly);
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

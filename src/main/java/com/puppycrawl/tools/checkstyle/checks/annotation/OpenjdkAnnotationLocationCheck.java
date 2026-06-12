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

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Verifies that annotations are properly placed by
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-annotations">
 * OpenJDK Style</a>.
 * Declaration annotations must either reside entirely on a single line or
 * have each annotation placed on its own separate line. Annotations should
 * not share a line with the target declaration, except for single-line methods and fields.
 * </div>
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
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
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
        if (!isLocalVariable(ast)) {
            final DetailAST annotationParentNode = getAnnotationsNode(ast);
            final DetailAST startOfTargetNode = getStartingAst(annotationParentNode);
            final List<DetailAST> annotationList = getAnnotations(annotationParentNode);

            if (!isAllOnSeparateLines(annotationList) && !isAllOnSameLine(annotationList)) {
                log(startOfTargetNode, MSG_KEY_ANNOTATION_ALONE_OR_SAME, getTargetName(ast));
            }
            if (isAnnotationOnTargetLine(startOfTargetNode, annotationList)
                    && !isSingleLineMethodOrField(ast)) {
                log(startOfTargetNode, MSG_KEY_ANNOTATION_ON_TARGET_LINE, getTargetName(ast));
            }
        }
    }

    /**
     * Checks whether the variable is local or not.
     *
     * @param ast variable.
     * @return true if local variable.
     */
    private static boolean isLocalVariable(DetailAST ast) {
        return ast.getType() == TokenTypes.VARIABLE_DEF
                && ast.getParent().getType() != TokenTypes.OBJBLOCK
                && ast.getParent().getType() != TokenTypes.COMPACT_COMPILATION_UNIT;
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
     * Gets all annotations of a target node.
     *
     * @param annotationParentNode parent node of annotations.
     * @return the list of annotations.
     */
    private static List<DetailAST> getAnnotations(DetailAST annotationParentNode) {
        final List<DetailAST> annotationList = new ArrayList<>();
        DetailAST annotation = annotationParentNode.getFirstChild();
        while (annotation != null && annotation.getType() == TokenTypes.ANNOTATION) {
            annotationList.add(annotation);
            annotation = annotation.getNextSibling();
        }
        return annotationList;
    }

    /**
     * Checks whether all annotations are on the same line.
     *
     * @param annotationList list of annotations.
     * @return true if all annotations are on the same line.
     */
    private static boolean isAllOnSameLine(List<DetailAST> annotationList) {
        boolean isOnSameLine = true;
        if (annotationList != null) {
            isOnSameLine = annotationList.get(0).getLineNo()
                    == annotationList.get(annotationList.size() - 1).getLineNo();
        }
        return isOnSameLine;
    }

    /**
     * Checks whether all annotations are on a separate line.
     *
     * @param annotationList list of annotations.
     * @return true if all annotations are on separate lines.
     */
    private static boolean isAllOnSeparateLines(List<DetailAST> annotationList) {
        boolean isOnSeparateLine = true;
        for (int index = 0; index < annotationList.size() - 1; index++) {
            if (annotationList.get(index).getLineNo()
                    == annotationList.get(index + 1).getLineNo()) {
                isOnSeparateLine = false;
            }
        }
        return isOnSeparateLine;
    }

    /**
     * Checks whether an annotation is on the target line.
     *
     * @param startOfTargetNode ast of starting point of target node.
     * @param annotationList list of annotations.
     * @return true if an annotation is on the target line.
     */
    private static boolean isAnnotationOnTargetLine(DetailAST startOfTargetNode,
            List<DetailAST> annotationList) {
        boolean isOnTargetLine = false;
        for (final DetailAST annotation : annotationList) {
            if (TokenUtil.areOnSameLine(annotation, startOfTargetNode)) {
                isOnTargetLine = true;
            }
        }
        return isOnTargetLine;
    }

    /**
     * Checks whether a method or field is single line or not.
     *
     * @param targetNode ast of target node
     * @return true if the method or field is single line.
     */
    private static boolean isSingleLineMethodOrField(DetailAST targetNode) {
        boolean result = false;
        if (targetNode.getType() == TokenTypes.METHOD_DEF
                || targetNode.getType() == TokenTypes.ANNOTATION_FIELD_DEF
                || targetNode.getType() == TokenTypes.VARIABLE_DEF) {
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
     * Returns the name of the given target node.
     *
     * @param targetNode target node.
     * @return target name.
     */
    private static String getTargetName(DetailAST targetNode) {
        DetailAST identNode = targetNode.findFirstToken(TokenTypes.IDENT);
        if (identNode == null) {
            identNode = targetNode.findFirstToken(TokenTypes.DOT).findFirstToken(TokenTypes.IDENT);
        }
        return identNode.getText();
    }

}

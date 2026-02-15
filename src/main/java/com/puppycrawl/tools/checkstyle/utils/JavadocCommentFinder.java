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

package com.puppycrawl.tools.checkstyle.utils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Utility class for finding javadoc comments in the AST.
 */
public final class JavadocCommentFinder {

    /**
     * Private constructor to prevent instantiation.
     */
    private JavadocCommentFinder() {
    }

    /**
     * Finds the javadoc comment for a given constructor/method AST node.
     * It looks for javadoc comments immediately before the constructor/method and uses
     * the last (nearest) one found. If there are multiple consecutive javadoc comments,
     * the one closest to the method/constructor is used.
     * Javadocs that appear after annotations are ignored - only javadocs before the
     * first annotation are considered valid.
     * If no javadoc is found before annotations in the MODIFIERS node, it looks in
     * TYPE_PARAMETERS and TYPE nodes.
     *
     * @param ast the constructor/method AST node
     * @return the javadoc comment AST node, or null if not found
     */
    public static DetailAST findJavadocComment(DetailAST ast) {
        final DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);
        DetailAST javadoc = findJavadocInModifiers(modifiers);
        if (javadoc == null) {
            final DetailAST typeParams = ast.findFirstToken(TokenTypes.TYPE_PARAMETERS);
            if (typeParams != null) {
                javadoc = findJavadocInTypeParameters(typeParams, modifiers);
            }
        }
        if (javadoc == null) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            if (type != null) {
                javadoc = findJavadocInType(type, modifiers);
            }
        }
        if (javadoc == null) {
            // For constructors without modifiers, javadoc may be direct child
            javadoc = findLastJavadocInDirectChildren(ast, modifiers);
        }
        return javadoc;
    }

    /**
     * Finds the last javadoc comment as a direct child of the given AST node.
     * Only considers javadocs that appear before the first annotation.
     *
     * @param ast the AST node
     * @param firstAnnotation the first annotation node, or null if none
     * @return the last javadoc comment node, or null if not found
     */
    private static DetailAST findLastJavadocInDirectChildren(
            DetailAST ast, DetailAST firstAnnotation) {
        DetailAST lastJavadoc = null;
        DetailAST child = ast.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null && isBeforeAnnotation(javadoc, firstAnnotation)) {
                lastJavadoc = javadoc;
            }
            child = child.getNextSibling();
        }
        return lastJavadoc;
    }

    /**
     * Returns the given AST node if it is a javadoc comment, null otherwise.
     *
     * @param node the AST node to check
     * @return the node if it is a javadoc comment, null otherwise
     */
    private static DetailAST getJavadocIfPresent(DetailAST node) {
        DetailAST result = null;
        if (node.getType() == TokenTypes.BLOCK_COMMENT_BEGIN
                && isJavadocCommentContent(node)) {
            result = node;
        }
        return result;
    }

    /**
     * Finds javadoc comment in TYPE_PARAMETERS node.
     * Only considers javadocs that appear before the first annotation.
     *
     * @param typeParams the TYPE_PARAMETERS node
     * @param firstAnnotation the first annotation node, or null if none
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInTypeParameters(DetailAST typeParams,
            DetailAST firstAnnotation) {
        return findLastJavadocInDirectChildren(typeParams, firstAnnotation);
    }

    /**
     * Finds javadoc comment in TYPE node, searching recursively for fully qualified types.
     * Only considers javadocs that appear before the first annotation.
     *
     * @param type the TYPE node
     * @param firstAnnotation the first annotation node, or null if none
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInType(DetailAST type, DetailAST firstAnnotation) {
        DetailAST javadoc = findLastJavadocInDirectChildren(type, firstAnnotation);
        final DetailAST child = type.getFirstChild();
        if (child.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(child, firstAnnotation);
        }
        return javadoc;
    }

    /**
     * Recursively finds javadoc comment in DOT node for fully qualified types.
     * Only considers javadocs that appear before the first annotation.
     *
     * @param dotNode the DOT node
     * @param firstAnnotation the first annotation node, or null if none
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInDotNode(DetailAST dotNode, DetailAST firstAnnotation) {
        DetailAST javadoc = findLastJavadocInDirectChildren(dotNode, firstAnnotation);
        final DetailAST firstChild = dotNode.getFirstChild();
        if (firstChild.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(firstChild, firstAnnotation);
        }
        return javadoc;
    }

    /**
     * Finds javadoc comment in MODIFIERS node before any annotations.
     * Returns the last (nearest) javadoc comment found before the first annotation.
     * Javadocs inside or after annotations are ignored.
     *
     * @param modifiers the MODIFIERS node
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInModifiers(DetailAST modifiers) {
        DetailAST lastJavadoc = null;
        final DetailAST firstAnnotation = findFirstAnnotation(modifiers);
        DetailAST child = modifiers.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null) {
                lastJavadoc = javadoc;
            }
            else if (child == firstAnnotation) {
                lastJavadoc = findJavadocBeforeAnnotation(child);
            }
            child = child.getNextSibling();
        }
        return lastJavadoc;
    }

    /**
     * Finds the first annotation in MODIFIERS.
     *
     * @param modifiers the MODIFIERS node
     * @return the first annotation node, or null if none
     */
    private static DetailAST findFirstAnnotation(DetailAST modifiers) {
        DetailAST result = null;
        result = modifiers.getFirstChild();
        return result;
    }

    /**
     * Finds javadoc comment inside an ANNOTATION node that appears before the annotation.
     * Only returns javadocs whose line number is less than the annotation's line number.
     *
     * @param annotation the ANNOTATION node
     * @return the javadoc comment node, or null if not found or if javadoc is after annotation
     */
    private static DetailAST findJavadocBeforeAnnotation(DetailAST annotation) {
        DetailAST result = null;
        final int annotationLine = annotation.getLineNo();
        DetailAST child = annotation.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null && javadoc.getLineNo() < annotationLine) {
                result = javadoc;
            }
            child = child.getNextSibling();
        }
        return result;
    }

    /**
     * Checks if a javadoc node is before an annotation.
     *
     * @param javadoc the javadoc AST node
     * @param firstAnnotation the first annotation node, or null if none
     * @return true if the javadoc is before the annotation (or no annotation exists)
     */
    private static boolean isBeforeAnnotation(DetailAST javadoc, DetailAST firstAnnotation) {
        return javadoc.getLineNo() < firstAnnotation.getLineNo();
    }

    /**
     * Checks if a block comment is a javadoc comment based on its content only.
     * This method does not check position, only that the content starts with '*'.
     *
     * @param blockCommentBegin the block comment AST node
     * @return true if this is a javadoc comment
     */
    private static boolean isJavadocCommentContent(DetailAST blockCommentBegin) {
        final String commentContent = JavadocUtil.getBlockCommentContent(blockCommentBegin);
        return JavadocUtil.isJavadocComment(commentContent);
    }
}

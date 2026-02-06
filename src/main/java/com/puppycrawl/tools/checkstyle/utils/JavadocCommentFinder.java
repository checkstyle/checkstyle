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
        final int firstAnnotationLine = findFirstAnnotationLine(modifiers);
        DetailAST javadoc = findJavadocInModifiers(modifiers);
        if (javadoc == null) {
            final DetailAST typeParams = ast.findFirstToken(TokenTypes.TYPE_PARAMETERS);
            if (typeParams != null) {
                javadoc = findJavadocInTypeParameters(typeParams, firstAnnotationLine);
            }
        }
        if (javadoc == null) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            if (type != null) {
                javadoc = findJavadocInType(type, firstAnnotationLine);
            }
        }
        if (javadoc == null) {
            // For constructors without modifiers, javadoc may be direct child
            javadoc = findLastJavadocInDirectChildren(ast, firstAnnotationLine);
        }
        return javadoc;
    }

    /**
     * Finds the last javadoc comment as a direct child of the given AST node.
     * Only considers javadocs that appear before the first annotation line.
     *
     * @param ast the AST node
     * @param firstAnnotationLine the line number of the first annotation
     * @return the last javadoc comment node, or null if not found
     */
    private static DetailAST findLastJavadocInDirectChildren(
            DetailAST ast, int firstAnnotationLine) {
        DetailAST lastJavadoc = null;
        DetailAST child = ast.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null && isBeforeLine(javadoc, firstAnnotationLine)) {
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
     * Only considers javadocs that appear before the first annotation line.
     *
     * @param typeParams the TYPE_PARAMETERS node
     * @param firstAnnotationLine the line number of the first annotation
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInTypeParameters(DetailAST typeParams,
            int firstAnnotationLine) {
        return findLastJavadocInDirectChildren(typeParams, firstAnnotationLine);
    }

    /**
     * Finds javadoc comment in TYPE node, searching recursively for fully qualified types.
     * Only considers javadocs that appear before the first annotation line.
     *
     * @param type the TYPE node
     * @param firstAnnotationLine the line number of the first annotation
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInType(DetailAST type, int firstAnnotationLine) {
        DetailAST javadoc = findLastJavadocInDirectChildren(type, firstAnnotationLine);
        final DetailAST child = type.getFirstChild();
        if (child.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(child, firstAnnotationLine);
        }
        return javadoc;
    }

    /**
     * Recursively finds javadoc comment in DOT node for fully qualified types.
     * Only considers javadocs that appear before the first annotation line.
     *
     * @param dotNode the DOT node
     * @param firstAnnotationLine the line number of the first annotation
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInDotNode(DetailAST dotNode, int firstAnnotationLine) {
        DetailAST javadoc = findLastJavadocInDirectChildren(dotNode, firstAnnotationLine);
        final DetailAST firstChild = dotNode.getFirstChild();
        if (firstChild.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(firstChild, firstAnnotationLine);
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
        DetailAST child = modifiers.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null) {
                lastJavadoc = javadoc;
            }
            else {
                final DetailAST javadocInAnnotation = findJavadocInAnnotation(child);
                if (javadocInAnnotation != null) {
                    lastJavadoc = javadocInAnnotation;
                }
            }
            child = child.getNextSibling();
        }
        return lastJavadoc;
    }

    /**
     * Finds the line number of the first annotation in MODIFIERS.
     *
     * @param modifiers the MODIFIERS node
     * @return the line number of the first annotation, or Integer.MAX_VALUE if none
     */
    private static int findFirstAnnotationLine(DetailAST modifiers) {
        int firstLine = Integer.MAX_VALUE;
        DetailAST child = modifiers.getFirstChild();
        while (child != null && firstLine == Integer.MAX_VALUE) {
            if (child.getType() == TokenTypes.ANNOTATION) {
                firstLine = child.getLineNo();
            }
            child = child.getNextSibling();
        }
        return firstLine;
    }

    /**
     * Checks if a node is before a given line number.
     *
     * @param node the AST node
     * @param line the line number to compare against
     * @return true if the node's line is before the given line
     */
    private static boolean isBeforeLine(DetailAST node, int line) {
        return node.getLineNo() < line;
    }

    /**
     * Finds javadoc comment inside an ANNOTATION node.
     * Only checks direct children of the annotation.
     *
     * @param annotation the ANNOTATION node
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInAnnotation(DetailAST annotation) {
        DetailAST result = null;
        DetailAST child = annotation.getFirstChild();
        while (child != null && result == null) {
            result = getJavadocIfPresent(child);
            child = child.getNextSibling();
        }
        return result;
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

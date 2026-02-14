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
     * If no javadoc is found before the constructor/method, it looks inside the
     * MODIFIERS node and its children (including annotations) for a javadoc, returning
     * the last non-empty javadoc if found, or the last javadoc if all are empty.
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
                javadoc = findJavadocInTypeParameters(typeParams);
            }
        }
        if (javadoc == null) {
            final DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
            if (type != null) {
                javadoc = findJavadocInType(type);
            }
        }
        if (javadoc == null) {
            // For constructors without modifiers, javadoc may be direct child
            javadoc = findLastJavadocInDirectChildren(ast);
        }
        return javadoc;
    }

    /**
     * Finds the last javadoc comment as a direct child of the given AST node.
     *
     * @param ast the AST node
     * @return the last javadoc comment node, or null if not found
     */
    private static DetailAST findLastJavadocInDirectChildren(DetailAST ast) {
        DetailAST lastJavadoc = null;
        DetailAST child = ast.getFirstChild();
        while (child != null) {
            final DetailAST javadoc = getJavadocIfPresent(child);
            if (javadoc != null) {
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
     *
     * @param typeParams the TYPE_PARAMETERS node
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInTypeParameters(DetailAST typeParams) {
        return findLastJavadocInDirectChildren(typeParams);
    }

    /**
     * Finds javadoc comment in TYPE node, searching recursively for fully qualified types.
     *
     * @param type the TYPE node
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInType(DetailAST type) {
        DetailAST javadoc = findLastJavadocInDirectChildren(type);
        final DetailAST child = type.getFirstChild();
        if (child.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(child);
        }
        return javadoc;
    }

    /**
     * Recursively finds javadoc comment in DOT node for fully qualified types.
     *
     * @param dotNode the DOT node
     * @return the javadoc comment node, or null if not found
     */
    private static DetailAST findJavadocInDotNode(DetailAST dotNode) {
        DetailAST javadoc = findLastJavadocInDirectChildren(dotNode);
        final DetailAST firstChild = dotNode.getFirstChild();
        if (firstChild.getType() == TokenTypes.DOT) {
            javadoc = findJavadocInDotNode(firstChild);
        }
        return javadoc;
    }

    /**
     * Finds javadoc comment in MODIFIERS node and its children (including annotations).
     * Returns the last (nearest) javadoc comment found.
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
                final DetailAST comment = findJavadocInAnnotation(child);
                if (comment != null) {
                    lastJavadoc = comment;
                }
            }
            child = child.getNextSibling();
        }
        return lastJavadoc;
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

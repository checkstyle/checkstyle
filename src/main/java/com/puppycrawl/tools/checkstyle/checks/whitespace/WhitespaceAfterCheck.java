///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that a token is followed by whitespace, with the exception that it
 * does not check for whitespace after the semicolon of an empty for iterator.
 * Use Check
 * <a href="https://checkstyle.org/checks/whitespace/emptyforiteratorpad.html">
 * EmptyForIteratorPad</a> to validate empty for iterators.
 * </div>
 *
 * @since 3.0
 */
@StatelessCheck
public class WhitespaceAfterCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_TYPECAST = "ws.typeCast";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_YIELD,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.DO_WHILE,
            TokenTypes.ELLIPSIS,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_WHEN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_YIELD,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.DO_WHILE,
            TokenTypes.ELLIPSIS,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_WHEN,
            TokenTypes.ANNOTATIONS,
            TokenTypes.TYPE,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.TYPECAST) {
            final DetailAST targetAST = ast.findFirstToken(TokenTypes.RPAREN);
            final int[] line = getLineCodePoints(targetAST.getLineNo() - 1);
            if (!isFollowedByWhitespace(targetAST, line)) {
                log(targetAST, MSG_WS_TYPECAST);
            }
        }
        else if (ast.getType() == TokenTypes.TYPE) {
            visitType(ast);
        }
        else if (ast.getType() == TokenTypes.ANNOTATIONS) {
            if (ast.getFirstChild() != null) {
                DetailAST targetAST = ast.getFirstChild().getLastChild();
                if (targetAST.getType() == TokenTypes.DOT) {
                    targetAST = targetAST.getLastChild();
                }
                final int[] line = getLineCodePoints(targetAST.getLineNo() - 1);
                if (!isFollowedByWhitespace(targetAST, line)) {
                    final Object[] message = {targetAST.getText()};
                    log(targetAST, MSG_WS_NOT_FOLLOWED, message);
                }
            }
        }
        else {
            final int[] line = getLineCodePoints(ast.getLineNo() - 1);
            if (!isFollowedByWhitespace(ast, line)) {
                final Object[] message = {ast.getText()};
                log(ast, MSG_WS_NOT_FOLLOWED, message);
            }
        }
    }

    /**
     * Checks whether token is followed by a whitespace.
     *
     * @param targetAST Ast token.
     * @param line Unicode code points array of line associated with the ast token.
     * @return true if ast token is followed by a whitespace.
     */
    private static boolean isFollowedByWhitespace(DetailAST targetAST, int... line) {
        final int after =
            targetAST.getColumnNo() + targetAST.getText().length();
        boolean followedByWhitespace = true;

        if (after < line.length) {
            final int codePoint = line[after];

            followedByWhitespace = codePoint == ';'
                || codePoint == ')'
                || codePoint == ','
                || Character.isWhitespace(codePoint);
        }
        return followedByWhitespace;
    }

    /**
     * Performs a whitespace check on a {@code TokenTypes.TYPE} token by locating the
     * rightmost token of the {@code TokenTypes.TYPE} expression and verifying that it
     * is followed by a whitespace character when appropriate. If the check fails, a log
     * message is recorded. The rightmost token may be {@code null} when
     * the {@code TokenTypes.TYPE} node has no meaningful trailing token to validate.
     *
     * @param typeAst AST node of type {@code TokenTypes.TYPE}
     */
    private void visitType(DetailAST typeAst) {
        final DetailAST targetAst = getTypeRightmostToken(typeAst);
        if (targetAst != null) {
            final int[] line = getLineCodePoints(targetAst.getLineNo() - 1);
            if (!isFollowedByWhitespace(targetAst, line)) {
                final Object[] args = {targetAst.getText()};
                log(targetAst, MSG_WS_NOT_FOLLOWED, args);
            }
        }
    }

    /**
     * Determines the appropriate AST node representing the end of a TYPE token.
     * Used to locate the final token of a type expression for whitespace checks.
     * Depending on context, this may be an ellipsis token, the deepest child of
     * the type node, or {@code null} if the type is already the rightmost element
     * and has no relevant token following it.
     *
     * @param typeAst AST node of type {@code TokenTypes.TYPE}
     * @return the rightmost AST token relevant to the TYPE, or {@code null}
     *         if no applicable token is found
     */
    private static DetailAST getTypeRightmostToken(DetailAST typeAst) {
        final int parentType = typeAst.getParent().getType();
        DetailAST targetAst = null;
        if (parentType == TokenTypes.PARAMETER_DEF
                || parentType == TokenTypes.RECORD_COMPONENT_DEF) {
            final DetailAST sibling = typeAst.getNextSibling();
            if (sibling.getType() == TokenTypes.ELLIPSIS) {
                targetAst = sibling;
            }
            else if (typeAst.hasChildren()) {
                targetAst = getDeepestLastChild(typeAst);
            }
        }
        else if (parentType != TokenTypes.RECORD_PATTERN_DEF) {
            targetAst = getDeepestLastChild(typeAst);
        }
        return targetAst;
    }

    /**
     * Finds the deepest descendant of the given AST by repeatedly
     * following the last child until a leaf node is reached.
     *
     * @param ast AST node from which to start the search.
     * @return the deepest last child AST node.
     */
    private static DetailAST getDeepestLastChild(DetailAST ast) {
        DetailAST current = ast;
        while (current.getLastChild() != null) {
            current = current.getLastChild();
        }
        return current;
    }

}

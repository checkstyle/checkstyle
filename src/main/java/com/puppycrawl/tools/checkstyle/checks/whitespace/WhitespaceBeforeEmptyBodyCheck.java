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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that a whitespace is present before the opening brace of an empty body.
 * </div>
 *
 * <p>
 * This check applies to the following tokens:
 * Methods and constructors, Classes, interfaces, enums, and records,
 * Annotation definitions, Loops: {@code while}, {@code for}, {@code do-while},
 * Lambdas and anonymous classes, Static initializer blocks,
 * Try-catch-finally statements, Synchronized blocks, Switch statements.
 * </p>
 *
 * <p>
 * A body containing only comments is considered empty by this check.
 * </p>
 *
 * @since 13.7.0
 */
@StatelessCheck
public class WhitespaceBeforeEmptyBodyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.notPreceded";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST lcurly = findOpeningBrace(ast);
        if (lcurly != null && isBodyEmpty(lcurly) && !precededByWhiteSpace(lcurly)) {
            log(lcurly, MSG_KEY, lcurly.getText());
        }
    }

    /**
     * Returns left curly token for an ast.
     *
     * @param ast the AST to get brace token for
     * @return the left curly token, or null if the construct has no-body
     */
    @Nullable
    private static DetailAST findOpeningBrace(DetailAST ast) {
        final int astType = ast.getType();
        return switch (astType) {
            case TokenTypes.CLASS_DEF,
                 TokenTypes.INTERFACE_DEF,
                 TokenTypes.ENUM_DEF,
                 TokenTypes.RECORD_DEF,
                 TokenTypes.ANNOTATION_DEF,
                 TokenTypes.LITERAL_NEW -> {
                final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
                DetailAST lcurly = null;
                if (objBlock != null) {
                    lcurly = objBlock.findFirstToken(TokenTypes.LCURLY);
                }
                yield lcurly;
            }
            case TokenTypes.LITERAL_SWITCH -> ast.findFirstToken(TokenTypes.LCURLY);
            default -> ast.findFirstToken(TokenTypes.SLIST);
        };
    }

    /**
     * Checks if the opening brace is preceded by whitespace.
     *
     * @param lcurly token representing the opening brace
     * @return true if preceded by whitespace
     */
    private static boolean precededByWhiteSpace(DetailAST lcurly) {
        DetailAST previousToken = lcurly.getPreviousSibling();
        if (previousToken != null) {
            previousToken = descendToLeafNode(previousToken);
        }
        if (previousToken == null
                || previousToken.getParent().getType() == TokenTypes.LAMBDA) {
            previousToken = NullUtil.notNull(lcurly.getParent());
        }
        if (previousToken.getType() == TokenTypes.OBJBLOCK) {
            previousToken = NullUtil.notNull(previousToken.getPreviousSibling());
            previousToken = descendToLeafNode(previousToken);
        }

        return !TokenUtil.areOnSameLine(lcurly, previousToken)
                || isSpaceBetweenTokens(previousToken, lcurly);
    }

    /**
     * Return the last nested child of the token.
     *
     * @param token the ast token
     * @return nested child
     */
    private static DetailAST descendToLeafNode(DetailAST token) {
        DetailAST current = token;
        while (current.hasChildren()) {
            current = current.getLastChild();
        }
        return current;
    }

    /**
     * Checks if the body is empty.
     *
     * @param lcurly the token representing the opening brace
     * @return true if the body is empty
     */
    private static boolean isBodyEmpty(DetailAST lcurly) {
        final DetailAST nextSibling = lcurly.getNextSibling();
        final DetailAST firstChild = lcurly.getFirstChild();
        return nextSibling != null && nextSibling.getType() == TokenTypes.RCURLY
                || firstChild != null && firstChild.getType() == TokenTypes.RCURLY;
    }

    /**
     * Checks if space exists between first and second token.
     *
     * @param firstToken token in ast which is behind second token
     * @param secondToken token next to firstToken
     * @return true if space exists between first and second tokens
     */
    private static boolean isSpaceBetweenTokens(DetailAST firstToken, DetailAST secondToken) {
        String tokenText = firstToken.getText();
        if (firstToken.getType() == TokenTypes.STATIC_INIT) {
            tokenText = "static";
        }
        final int firstLastColumn = firstToken.getColumnNo()
                + tokenText.length() - 1;
        return secondToken.getColumnNo() - firstLastColumn > 1;
    }

}

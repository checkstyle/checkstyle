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

import java.util.Map;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.NullUtil;

/**
 * <div>
 * Checks that a whitespace is present before the opening brace of an empty body.
 * </div>
 *
 * <p>
 * This check is applied to methods, constructors, classes, interfaces, enums, records,
 * annotation definitions, loops (while, for, do-while), lambdas, anonymous classes,
 * initializer blocks (static and instance), try-catch-finally statements, synchronized blocks,
 * and switch statements.
 * </p>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class WhitespaceBeforeEmptyBodyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "ws.empty.body.not.preceded";

    /** Map of token types to their human-readable names for error messages. */
    private static final Map<Integer, String> TOKEN_NAMES = Map.ofEntries(
        Map.entry(TokenTypes.LITERAL_NEW, "anonymous class"),
        Map.entry(TokenTypes.LITERAL_WHILE, "while"),
        Map.entry(TokenTypes.LITERAL_FOR, "for"),
        Map.entry(TokenTypes.LITERAL_DO, "do"),
        Map.entry(TokenTypes.STATIC_INIT, "static"),
        Map.entry(TokenTypes.INSTANCE_INIT, "instance initializer"),
        Map.entry(TokenTypes.LITERAL_TRY, "try"),
        Map.entry(TokenTypes.LITERAL_CATCH, "catch"),
        Map.entry(TokenTypes.LITERAL_FINALLY, "finally"),
        Map.entry(TokenTypes.LITERAL_SYNCHRONIZED, "synchronized"),
        Map.entry(TokenTypes.LITERAL_SWITCH, "switch"),
        Map.entry(TokenTypes.LAMBDA, "lambda")
    );

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
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_DO,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_NEW,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST braceToken = getBraceToken(ast);
        if (braceToken != null && isEmptyBody(braceToken) && !isPrecededByWhitespace(braceToken)) {
            log(braceToken, MSG_KEY, getConstructName(ast));
        }
    }

    /**
     * Gets the brace token (OBJBLOCK, SLIST, or LCURLY) for the given AST.
     *
     * @param ast the AST to get brace token for
     * @return the brace token, or null if not found
     */
    @Nullable
    private static DetailAST getBraceToken(DetailAST ast) {
        final DetailAST braceToken;
        final int astType = ast.getType();

        switch (astType) {
            case TokenTypes.CLASS_DEF:
            case TokenTypes.INTERFACE_DEF:
            case TokenTypes.ENUM_DEF:
            case TokenTypes.RECORD_DEF:
            case TokenTypes.ANNOTATION_DEF:
            case TokenTypes.LITERAL_NEW:
                braceToken = ast.findFirstToken(TokenTypes.OBJBLOCK);
                break;
            case TokenTypes.LITERAL_SWITCH:
                braceToken = ast.findFirstToken(TokenTypes.LCURLY);
                break;
            default:
                braceToken = ast.findFirstToken(TokenTypes.SLIST);
                break;
        }

        return braceToken;
    }

    /**
     * Checks if the body is empty.
     *
     * @param braceToken the brace token (OBJBLOCK, SLIST, or LCURLY)
     * @return true if the body is empty
     */
    private static boolean isEmptyBody(DetailAST braceToken) {
        final boolean isEmpty;
        final int tokenType = braceToken.getType();

        if (tokenType == TokenTypes.OBJBLOCK) {
            final DetailAST lcurly = NullUtil.notNull(braceToken.findFirstToken(TokenTypes.LCURLY));
            isEmpty = lcurly.getNextSibling().getType() == TokenTypes.RCURLY;
        }
        else if (tokenType == TokenTypes.SLIST) {
            isEmpty = braceToken.getFirstChild().getType() == TokenTypes.RCURLY;
        }
        else {
            isEmpty = braceToken.getNextSibling().getType() == TokenTypes.RCURLY;
        }

        return isEmpty;
    }

    /**
     * Checks if the opening brace is preceded by whitespace.
     *
     * @param braceToken the brace token representing the opening brace position
     * @return true if preceded by whitespace
     */
    private boolean isPrecededByWhitespace(DetailAST braceToken) {
        final int[] line = getLineCodePoints(braceToken.getLineNo() - 1);
        final int columnNo = braceToken.getColumnNo();
        boolean isPreceded = true;

        if (columnNo > 0) {
            isPreceded = CommonUtil.isCodePointWhitespace(line, columnNo - 1);
        }

        return isPreceded;
    }

    /**
     * Gets the name of the construct for error message.
     *
     * @param ast the construct AST
     * @return the name of the construct
     */
    private static String getConstructName(DetailAST ast) {
        String name = TOKEN_NAMES.get(ast.getType());
        if (name == null) {
            final DetailAST identToken = ast.findFirstToken(TokenTypes.IDENT);
            name = NullUtil.notNull(identToken).getText();
        }
        return name;
    }

}

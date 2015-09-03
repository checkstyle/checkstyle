////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that a token is followed by whitespace, with the exception that it
 * does not check for whitespace after the semicolon of an empty for iterator.
 * Use Check {@link EmptyForIteratorPadCheck EmptyForIteratorPad} to validate
 * empty for iterators.
 * </p>
 * <p> By default the check will check the following tokens:
 *  {@link TokenTypes#COMMA COMMA},
 *  {@link TokenTypes#SEMI SEMI},
 *  {@link TokenTypes#TYPECAST TYPECAST}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="WhitespaceAfter"/&gt;
 * </pre>
 * <p> An example of how to configure the check for whitespace only after
 * {@link TokenTypes#COMMA COMMA} and {@link TokenTypes#SEMI SEMI} tokens is:
 * </p>
 * <pre>
 * &lt;module name="WhitespaceAfter"&gt;
 *     &lt;property name="tokens" value="COMMA, SEMI"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @author Rick Giles
 */
public class WhitespaceAfterCheck
    extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_NOT_FOLLOWED = "ws.notFollowed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String WS_TYPECAST = "ws.typeCast";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.TYPECAST,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return ArrayUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        if (ast.getType() == TokenTypes.TYPECAST) {
            final DetailAST targetAST = ast.findFirstToken(TokenTypes.RPAREN);
            if (!isFollowedByWhitespace(targetAST, line)) {
                log(targetAST.getLineNo(),
                    targetAST.getColumnNo() + targetAST.getText().length(),
                    WS_TYPECAST);
            }
        }
        else {
            if (!isFollowedByWhitespace(ast, line)) {
                final Object[] message = {ast.getText()};
                log(ast.getLineNo(),
                    ast.getColumnNo() + ast.getText().length(),
                    WS_NOT_FOLLOWED,
                    message);
            }
        }
    }

    /**
     * Checks whether token is followed by a whitespace.
     * @param targetAST Ast token.
     * @param line The line associated with the ast token.
     * @return true if ast token is followed by a whitespace.
     */
    private static boolean isFollowedByWhitespace(DetailAST targetAST, String line) {
        final int after =
            targetAST.getColumnNo() + targetAST.getText().length();
        boolean followedByWhitespace = true;

        if (after < line.length()) {
            final char charAfter = line.charAt(after);
            followedByWhitespace = Character.isWhitespace(charAfter)
                || targetAST.getType() == TokenTypes.SEMI
                    && (charAfter == ';' || charAfter == ')');
        }
        return followedByWhitespace;
    }
}

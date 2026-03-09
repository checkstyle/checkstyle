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

package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div> Checks that hexadecimal literals are defined using uppercase letters {@code (A-F)}
 * rather than lowercase {@code (a-f)}.
 * This convention follows the
 * <a href="https://cr.openjdk.org/~alundblad/styleguide/index-v6.html#toc-literals">
 * OpenJDK Style Guide</a>.
 * </div>
 *
 * @since 12.1.0
 */
@StatelessCheck
public class HexLiteralCaseCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "hex.literal";

    /** ASCII value for lowercase 'a'. */
    private static final int A_LOWER_ASCII = 97;

    /** ASCII value for lowercase 'f'. */
    private static final int F_LOWER_ASCII = 102;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.NUM_LONG, TokenTypes.NUM_INT, TokenTypes.NUM_FLOAT,
            TokenTypes.NUM_DOUBLE,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        if (text.startsWith("0x") || text.startsWith("0X")) {
            final int type = ast.getType();
            if (type == TokenTypes.NUM_FLOAT || type == TokenTypes.NUM_DOUBLE) {
                if (containsLowerLetterInHexFloatLiteral(text)) {
                    log(ast, MSG_KEY);
                }
            }
            else {
                if (containsLowerLetter(text)) {
                    log(ast, MSG_KEY);
                }
            }
        }
    }

    /**
     * Checks if the given text contains any lowercase hexadecimal letter (a–f).
     *
     * @param text the literal text to check
     * @return true if the literal contains lowercase hex digits; false otherwise
     */
    private static boolean containsLowerLetter(final String text) {
        boolean result = false;
        final char[] characterList = text.toCharArray();
        for (char character : characterList) {
            if (character >= A_LOWER_ASCII && character <= F_LOWER_ASCII) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the given text contains any lowercase hexadecimal letter in
     * Hex Float Literal (a–f).
     *
     * @param text the literal text to check
     * @return true if the literal contains lowercase hex digits; false otherwise
     */
    public static boolean containsLowerLetterInHexFloatLiteral(final String text) {
        boolean result = false;

        final int index = text.length() - 1;
        for (char character : text.substring(0, index).toCharArray()) {
            if (character >= A_LOWER_ASCII && character <= F_LOWER_ASCII) {
                result = true;
                break;
            }
        }

        return result;
    }
}

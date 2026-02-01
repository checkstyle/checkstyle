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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that specified symbols (by Unicode code points or ranges) are not used in code.
 * By default, blocks common symbol ranges (U+2190–U+27BF and U+1F700–U+10FFFF).
 * </div>
 *
 * <p>
 * Rationale: This check helps prevent emoji symbols and special characters in code
 * (commonly added by AI tools), enforce coding standards, or forbid specific Unicode characters.
 * </p>
 *
 * <p>
 * Default ranges cover:
 * </p>
 * <ul>
 * <li>U+2190–U+27BF: Arrows, Mathematical Operators, Box Drawing, Geometric Shapes,
 * Miscellaneous Symbols, and Dingbats</li>
 * <li>U+1F700–U+10FFFF: Alchemical Symbols, Emoticons, Transport Symbols,
 * and all other pictographic symbols</li>
 * </ul>
 *
 * <p>
 * For a complete list of Unicode characters and ranges, see:
 * <a href="https://en.wikipedia.org/wiki/List_of_Unicode_characters">
 * List of Unicode characters</a>
 * </p>
 *
 * <ul>
 * <li>
 * Property {@code symbolCodes} - Specify the symbols to check for, as Unicode code points
 * or ranges. Format: comma-separated list of hex codes or ranges
 * (e.g., {@code "0x2705, 0x1F600-0x1F64F"}). To allow only ASCII characters,
 * use {@code "0x0080-0x10FFFF"}.
 * Type is {@code java.lang.String}.
 * Default value is {@code "0x2190-0x27BF, 0x1F700-0x10FFFF"}.
 * </li>
 * </ul>
 *
 * @since 13.1.0
 */
@StatelessCheck
public class IllegalSymbolCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "illegal.symbol";

    /** String Range Separator. */
    private static final String RANGE_SEPARATOR = "-";

    /** Specify the symbols to check for, as Unicode code points or ranges. */
    private String symbolCodes = "0x2190-0x27BF, 0x1F700-0x10FFFF";

    /**
     * Setter to specify the symbols to check for.
     * Format: comma-separated list of hex codes or ranges
     * (e.g., "0x2705, 0xd83c-0xd83e").
     *
     * @param symbols the symbols specification
     * @since 13.1.0
     */
    public void setSymbolCodes(String symbols) {
        symbolCodes = symbols;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMENT_CONTENT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMENT_CONTENT,
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
            TokenTypes.IDENT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String text = ast.getText();
        checkText(text, ast);
    }

    /**
     * Check the text for illegal symbols.
     *
     * @param text the text to check
     * @param ast the AST node
     */
    private void checkText(String text, DetailAST ast) {
        final int length = text.length();
        int offset = 0;

        while (offset < length) {
            final int codePoint = text.codePointAt(offset);

            if (isIllegalSymbol(codePoint)) {
                log(ast, MSG_KEY);
                break;
            }

            offset += Character.charCount(codePoint);
        }
    }

    /**
     * Check if a code point is illegal based on configured ranges.
     *
     * @param codePoint the code point to check
     * @return true if the code point is illegal
     */
    private boolean isIllegalSymbol(int codePoint) {
        return !symbolCodes.isEmpty() && isInSymbolCodes(codePoint);
    }

    /**
     * Check if code point is in the configured symbol codes.
     *
     * @param codePoint the code point to check
     * @return true if in symbol codes
     */
    private boolean isInSymbolCodes(int codePoint) {
        boolean found = false;
        final String[] parts = symbolCodes.split(",", -1);

        for (String part : parts) {
            final String trimmed = part.trim();
            if (trimmed.contains(RANGE_SEPARATOR)) {
                // Range format
                found = isInRange(codePoint, trimmed);
            }
            else {
                // Single code point
                final int checkPoint = parseCodePoint(trimmed);
                found = codePoint == checkPoint;
            }

            if (found) {
                break;
            }
        }

        return found;
    }

    /**
     * Check if code point is in the specified range.
     *
     * @param codePoint the code point to check
     * @param rangeStr the range string (e.g., "0x1F600-0x1F64F")
     * @return true if in range
     */
    private static boolean isInRange(int codePoint, String rangeStr) {
        final String[] range = rangeStr.split(RANGE_SEPARATOR, -1);
        boolean result = false;

        if (range.length == 2) {
            final int start = parseCodePoint(range[0].trim());
            final int end = parseCodePoint(range[1].trim());
            result = codePoint >= start && codePoint <= end;
        }

        return result;
    }

    /**
     * Parse a code point from string representation.
     * Supports formats: 0x1234, \\u1234, U+1234, or decimal.
     *
     * @param str the string to parse
     * @return the code point value
     */
    private static int parseCodePoint(String str) {
        final String cleaned = str.trim();
        final int hexRadix = 16;
        final int result;

        if (cleaned.startsWith("\\u")
                || cleaned.startsWith("0x")
                || cleaned.startsWith("0X")
                || cleaned.startsWith("U+")
                || cleaned.startsWith("u+")) {
            result = Integer.parseInt(cleaned.substring(2), hexRadix);
        }
        else {
            result = Integer.parseInt(cleaned, hexRadix);
        }
        return result;
    }
}

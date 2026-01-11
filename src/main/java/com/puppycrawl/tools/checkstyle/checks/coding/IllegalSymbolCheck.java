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

import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that specified symbols (by Unicode code points or ranges) are not used in code.
 * By default, blocks common symbol ranges.
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
 * <li>U+1F600–U+1F64F: Emoticons</li>
 * <li>U+1F680–U+1F6FF: Transport and Map Symbols</li>
 * <li>U+1F700–U+10FFFF: Alchemical Symbols and other pictographic symbols</li>
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
 * Default value is {@code "0x2190-0x27BF, 0x1F600-0x1F64F, 0x1F680-0x1F6FF, 0x1F700-0x1FFFFF"}.
 * </li>
 * </ul>
 *
 * @since 13.3.0
 */
@FileStatefulCheck
public class IllegalSymbolCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "illegal.symbol";

    /** String Range Separator. */
    private static final String RANGE_SEPARATOR = "-";

    /** Precomputed single code points. */
    private final Set<Integer> singleCodePoints = new HashSet<>();

    /** Precomputed code point ranges. */
    private final Set<CodePointRange> codePointRanges = new HashSet<>();

    /** Specify the symbols to check for, as Unicode code points or ranges. */
    private String symbolCodes = "0x2190-0x27BF, 0x1F600-0x1F64F, 0x1F680-0x1F6FF, "
            + "0x1F700-0x1FFFFF";

    /** Cached parsed symbol codes to detect if re-parsing is needed. */
    private String parsedSymbolCodes = "";

    /**
     * Setter to specify the symbols to check for.
     * Format: comma-separated list of hex codes or ranges
     * (e.g., "0x2705, 0x1F600-0x1F64F").
     *
     * @param symbols the symbols specification
     * @throws IllegalArgumentException if the format is invalid
     * @since 13.3.0
     */
    public void setSymbolCodes(String symbols) {
        symbolCodes = symbols;
        parseSymbolCodes();
    }

    /**
     * Parse the symbolCodes string into data structures.
     *
     * @throws IllegalArgumentException if the format is invalid
     */
    private void parseSymbolCodes() {
        singleCodePoints.clear();
        codePointRanges.clear();
        parsedSymbolCodes = symbolCodes;

        if (!symbolCodes.isEmpty()) {
            final String[] parts = symbolCodes.split(",", -1);
            for (String part : parts) {
                final String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    try {
                        if (trimmed.contains(RANGE_SEPARATOR)) {
                            parseRange(trimmed);
                        }
                        else {
                            singleCodePoints.add(parseCodePoint(trimmed));
                        }
                    }
                    catch (NumberFormatException exception) {
                        throw new IllegalArgumentException(
                                "Invalid symbol code format: " + trimmed, exception);
                    }
                }
            }
        }
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
     * Parses symbolCodes on first access if not yet parsed.
     *
     * @param codePoint the code point to check
     * @return true if in symbol codes
     */
    private boolean isInSymbolCodes(int codePoint) {
        if (!symbolCodes.equals(parsedSymbolCodes)) {
            parseSymbolCodes();
        }

        boolean found = false;

        // Check single code points
        if (singleCodePoints.contains(codePoint)) {
            found = true;
        }
        else {
            // Check ranges
            for (CodePointRange range : codePointRanges) {
                if (range.contains(codePoint)) {
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    /**
     * Parse and store a range.
     *
     * @param rangeStr the range string
     * @throws IllegalArgumentException if range format is invalid
     */
    private void parseRange(String rangeStr) {
        final String[] range = rangeStr.split(RANGE_SEPARATOR, -1);
        if (range.length != 2
                || CommonUtil.isBlank(range[0])
                || CommonUtil.isBlank(range[1])) {
            throw new IllegalArgumentException("Invalid range format: " + rangeStr);
        }

        final int start = parseCodePoint(range[0].trim());
        final int end = parseCodePoint(range[1].trim());

        if (start > end) {
            throw new IllegalArgumentException(
                    "Range start must be <= end: " + rangeStr);
        }

        codePointRanges.add(new CodePointRange(start, end));
    }

    /**
     * Parse a code point from string representation.
     * Supports formats: 0x1234, \\u1234, U+1234, or plain hex.
     *
     * @param str the string to parse
     * @return the code point value
     * @throws NumberFormatException if the string cannot be parsed
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
            if (cleaned.length() == 2) {
                throw new NumberFormatException("Invalid code point format: " + cleaned);
            }
            result = Integer.parseInt(cleaned.substring(2), hexRadix);
        }
        else {
            result = Integer.parseInt(cleaned, hexRadix);
        }
        return result;
    }

    /**
     * Represents a parsed Unicode range.
     *
     * @param start start of range
     * @param end end of range
     */
    private record CodePointRange(int start, int end) {

        /**
         * Check if code point is in this range.
         *
         * @param codePoint code point to check
         * @return true if in range
         */
        /* package */ boolean contains(int codePoint) {
            return codePoint >= start && codePoint <= end;
        }
    }
}

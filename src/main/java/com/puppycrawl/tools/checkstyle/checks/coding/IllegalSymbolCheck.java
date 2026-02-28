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
import java.util.Objects;
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

    /** Separator used for defining ranges. */
    private static final String RANGE_SEPARATOR = "-";

    /** Set of individual Unicode code points to disallow. */
    private Set<Integer> singleCodePoints;

    /** Set of Unicode ranges to disallow. */
    private Set<CodePointRange> codePointRanges;

    /** Specify the symbols to check for, as Unicode code points or ranges. */
    private String symbolCodes = "0x2190-0x27BF, 0x1F600-0x1F64F, 0x1F680-0x1F6FF, "
            + "0x1F700-0x1FFFFF";

    /**
     * Setter to specify the symbols to check for.
     *
     * @param symbols the symbols specification
     * @throws IllegalArgumentException if the format is invalid
     * @since 13.3.0
     */
    public void setSymbolCodes(String symbols) {
        symbolCodes = Objects.requireNonNullElse(symbols, "");
        parseSymbolCodes();
    }

    /**
     * Initializes the check after all properties are set.
     *
     * <p>
     * Ensures that the {@code symbolCodes} property is parsed
     * for both default configuration and custom user configuration.
     * </p>
     *
     * @throws IllegalArgumentException if the configured symbol format is invalid
     */
    @Override
    public void init() {
        parseSymbolCodes();
    }

    /**
     * Parses the configured symbolCodes string into singleCodePoints and codePointRanges.
     * Replaces both sets on every call so repeated invocations are safe.
     *
     * @throws IllegalArgumentException if format is invalid
     */
    private void parseSymbolCodes() {
        singleCodePoints = new HashSet<>();
        codePointRanges = new HashSet<>();

        for (String part : symbolCodes.split(",", -1)) {
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
        checkText(ast.getText(), ast);
    }

    /**
     * Checks text for illegal symbols, logging at most one violation per token.
     *
     * @param text the text content
     * @param ast AST node associated with text
     */
    private void checkText(String text, DetailAST ast) {
        final int length = text.length();
        int offset = 0;
        boolean foundIllegal = false;

        while (offset < length) {
            final int codePoint = text.codePointAt(offset);

            if (isIllegalSymbol(codePoint)) {
                foundIllegal = true;
            }

            offset += Character.charCount(codePoint);
        }

        if (foundIllegal) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Determines whether a code point is illegal.
     *
     * @param codePoint Unicode code point
     * @return true if illegal; false otherwise
     */
    private boolean isIllegalSymbol(int codePoint) {
        boolean illegal = singleCodePoints.contains(codePoint);

        for (CodePointRange range : codePointRanges) {
            if (range.contains(codePoint)) {
                illegal = true;
                break;
            }
        }

        return illegal;
    }

    /**
     * Parses and stores a Unicode range.
     *
     * @param rangeStr range definition string (already trimmed by caller)
     * @throws IllegalArgumentException if format is invalid
     */
    private void parseRange(String rangeStr) {
        final String[] parts = rangeStr.split(RANGE_SEPARATOR, -1);
        if (parts.length != 2
                || CommonUtil.isBlank(parts[0])
                || CommonUtil.isBlank(parts[1])) {
            throw new IllegalArgumentException(
                    "Invalid range format: " + rangeStr);
        }

        final int start = parseCodePoint(parts[0].trim());
        final int end = parseCodePoint(parts[1].trim());

        if (start > end) {
            throw new IllegalArgumentException(
                    "Range start must be <= end: " + rangeStr);
        }

        codePointRanges.add(new CodePointRange(start, end));
    }

    /**
     * Parses a Unicode code point from a trimmed string.
     * Supported formats: {@code 0x1234}, {@code \\u1234}, {@code U+1234}, or plain hex.
     *
     * @param str input string (already trimmed by caller)
     * @return parsed code point
     * @throws NumberFormatException if invalid format
     */
    private static int parseCodePoint(String str) {
        final int hexRadix = 16;
        final int result;

        final boolean hasPrefix =
                str.startsWith("\\u")
                        || str.startsWith("0x")
                        || str.startsWith("0X")
                        || str.startsWith("U+")
                        || str.startsWith("u+");

        if (hasPrefix) {
            result = Integer.parseInt(str.substring(2), hexRadix);
        }
        else {
            result = Integer.parseInt(str, hexRadix);
        }
        return result;
    }

    /**
     * Represents a Unicode code point range.
     *
     * @param start range start (inclusive)
     * @param end range end (inclusive)
     */
    private record CodePointRange(int start, int end) {

        /**
         * Checks if code point is within range.
         *
         * @param codePoint code point to test
         * @return true if within range; false otherwise
         */
        /* package */ boolean contains(int codePoint) {
            return codePoint >= start && codePoint <= end;
        }
    }
}

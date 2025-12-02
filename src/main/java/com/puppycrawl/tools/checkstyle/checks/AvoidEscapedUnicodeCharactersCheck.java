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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <div>
 * Restricts using
 * <a href = "https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.3">
 * Unicode escapes</a>
 * (such as &#92;u221e). It is possible to allow using escapes for
 * <a href="https://en.wiktionary.org/wiki/Appendix:Control_characters">
 * non-printable, control characters</a>.
 * Also, this check can be configured to allow using escapes
 * if trail comment is present. By the option it is possible to
 * allow using escapes if literal contains only them.
 * </div>
 *
 * @since 5.8
 */
@FileStatefulCheck
public class AvoidEscapedUnicodeCharactersCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "forbid.escaped.unicode.char";

    /** Regular expression for Unicode chars. */
    private static final Pattern UNICODE_REGEXP = Pattern.compile("\\\\u+[a-fA-F\\d]{4}");

    /**
     * Regular expression Unicode control characters.
     *
     * @see <a href="https://en.wiktionary.org/wiki/Appendix:Control_characters">
     *     Appendix:Control characters</a>
     */
    private static final Pattern UNICODE_CONTROL = Pattern.compile("\\\\u+"
            + "(00[0-1][\\dA-Fa-f]"
            + "|00[8-9][\\dA-Fa-f]"
            + "|00[aA][dD]"
            + "|034[fF]"
            + "|070[fF]"
            + "|180[eE]"
            + "|200[b-fB-F]"
            + "|202[a-eA-E]"
            + "|206[0-4a-fA-F]"
            + "|[fF]{3}[9a-bA-B]"
            + "|[fF][eE][fF]{2})");

    /**
     * Regular expression for all escaped chars.
     * See <a href="https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10.7">
     * EscapeSequence</a>
     */
    private static final Pattern ALL_ESCAPED_CHARS = Pattern.compile("^("
            + UNICODE_REGEXP.pattern()
            + "|\""
            + "|'"
            + "|\\\\"
            + "|\\\\b"
            + "|\\\\f"
            + "|\\\\n"
            + "|\\R"
            + "|\\\\r"
            + "|\\\\s"
            + "|\\\\t"
            + ")+$");

    /** Regular expression for escaped backslash. */
    private static final Pattern ESCAPED_BACKSLASH = Pattern.compile("\\\\\\\\");

    /** Regular expression for non-printable unicode chars. */
    private static final Pattern NON_PRINTABLE_CHARS = Pattern.compile("\\\\u0000"
            + "|\\\\u0009"
            + "|\\\\u000[bB]"
            + "|\\\\u000[cC]"
            + "|\\\\u0020"
            + "|\\\\u007[fF]"
            + "|\\\\u0085"
            + "|\\\\u009[fF]"
            + "|\\\\u00[aA]0"
            + "|\\\\u00[aA][dD]"
            + "|\\\\u04[fF]9"
            + "|\\\\u05[bB][eE]"
            + "|\\\\u05[dD]0"
            + "|\\\\u05[eE][aA]"
            + "|\\\\u05[fF]3"
            + "|\\\\u05[fF]4"
            + "|\\\\u0600"
            + "|\\\\u0604"
            + "|\\\\u061[cC]"
            + "|\\\\u06[dD]{2}"
            + "|\\\\u06[fF]{2}"
            + "|\\\\u070[fF]"
            + "|\\\\u0750"
            + "|\\\\u077[fF]"
            + "|\\\\u0[eE]00"
            + "|\\\\u0[eE]7[fF]"
            + "|\\\\u1680"
            + "|\\\\u180[eE]"
            + "|\\\\u1[eE]00"
            + "|\\\\u2000"
            + "|\\\\u2001"
            + "|\\\\u2002"
            + "|\\\\u2003"
            + "|\\\\u2004"
            + "|\\\\u2005"
            + "|\\\\u2006"
            + "|\\\\u2007"
            + "|\\\\u2008"
            + "|\\\\u2009"
            + "|\\\\u200[aA]"
            + "|\\\\u200[fF]"
            + "|\\\\u2025"
            + "|\\\\u2028"
            + "|\\\\u2029"
            + "|\\\\u202[fF]"
            + "|\\\\u205[fF]"
            + "|\\\\u2064"
            + "|\\\\u2066"
            + "|\\\\u2067"
            + "|\\\\u2068"
            + "|\\\\u2069"
            + "|\\\\u206[aA]"
            + "|\\\\u206[fF]"
            + "|\\\\u20[aA][fF]"
            + "|\\\\u2100"
            + "|\\\\u213[aA]"
            + "|\\\\u3000"
            + "|\\\\u[dD]800"
            + "|\\\\u[fF]8[fF]{2}"
            + "|\\\\u[fF][bB]50"
            + "|\\\\u[fF][dD][fF]{2}"
            + "|\\\\u[fF][eE]70"
            + "|\\\\u[fF][eE][fF]{2}"
            + "|\\\\u[fF]{2}0[eE]"
            + "|\\\\u[fF]{2}61"
            + "|\\\\u[fF]{2}[dD][cC]"
            + "|\\\\u[fF]{3}9"
            + "|\\\\u[fF]{3}[aA]"
            + "|\\\\u[fF]{3}[bB]"
            + "|\\\\u[fF]{4}");

    /** Pending literal to be checked. */
    private DetailAST pendingLiteral;

    /** Line number of the pending literal. */
    private int pendingLineNo;

    /** Whether the pending literal line has a trailing comment. */
    private boolean pendingLineHasComment;

    /** Allow use escapes for non-printable, control characters. */
    private boolean allowEscapesForControlCharacters;

    /** Allow use escapes if trail comment is present. */
    private boolean allowByTailComment;

    /** Allow if all characters in literal are escaped. */
    private boolean allowIfAllCharactersEscaped;

    /** Allow use escapes for non-printable, whitespace characters. */
    private boolean allowNonPrintableEscapes;

    /**
     * Setter to allow use escapes for non-printable, control characters.
     *
     * @param allow user's value.
     * @since 5.8
     */
    public final void setAllowEscapesForControlCharacters(boolean allow) {
        allowEscapesForControlCharacters = allow;
    }

    /**
     * Setter to allow use escapes if trail comment is present.
     *
     * @param allow user's value.
     * @since 5.8
     */
    public final void setAllowByTailComment(boolean allow) {
        allowByTailComment = allow;
    }

    /**
     * Setter to allow if all characters in literal are escaped.
     *
     * @param allow user's value.
     * @since 5.8
     */
    public final void setAllowIfAllCharactersEscaped(boolean allow) {
        allowIfAllCharactersEscaped = allow;
    }

    /**
     * Setter to allow use escapes for non-printable, whitespace characters.
     *
     * @param allow user's value.
     * @since 5.8
     */
    public final void setAllowNonPrintableEscapes(boolean allow) {
        allowNonPrintableEscapes = allow;
    }

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
        return new int[] {
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
            TokenTypes.TEXT_BLOCK_CONTENT,
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final int tokenType = ast.getType();

        if (tokenType == TokenTypes.STRING_LITERAL
            || tokenType == TokenTypes.CHAR_LITERAL
            || tokenType == TokenTypes.TEXT_BLOCK_CONTENT) {
            processPendingLiteral();
            pendingLiteral = ast;
            pendingLineNo = getLineNumberForLiteral(ast, tokenType);
            pendingLineHasComment = false;
        }
        else if ((tokenType == TokenTypes.SINGLE_LINE_COMMENT
            || tokenType == TokenTypes.BLOCK_COMMENT_BEGIN)
            && pendingLiteral != null
            && ast.getLineNo() == pendingLineNo) {

            final boolean isTrailing = isTrailingComment(ast, tokenType);
            if (isTrailing) {
                pendingLineHasComment = true;
            }
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        processPendingLiteral();
    }

    /**
     * Processes the pending literal if it exists.
     */
    private void processPendingLiteral() {
        if (pendingLiteral != null) {
            checkLiteral(pendingLiteral, pendingLineHasComment);
            pendingLiteral = null;
        }
    }

    /**
     * Gets the line number to check for trailing comments for a literal.
     *
     * @param ast the literal AST node
     * @param tokenType the token type of the literal
     * @return the line number to check for trailing comments
     */
    private static int getLineNumberForLiteral(DetailAST ast, int tokenType) {
        int lineNo = ast.getLineNo();
        if (tokenType == TokenTypes.TEXT_BLOCK_CONTENT) {
            final DetailAST textBlockEnd = ast.getNextSibling();
            if (textBlockEnd != null && textBlockEnd.getType()
                    == TokenTypes.TEXT_BLOCK_LITERAL_END) {
                lineNo = textBlockEnd.getLineNo();
            }
        }
        return lineNo;
    }

    /**
     * Checks if a comment is a trailing comment for the pending literal.
     *
     * @param ast the comment AST node
     * @param tokenType the token type of the comment
     * @return true if the comment is a trailing comment
     */
    private static boolean isTrailingComment(DetailAST ast, int tokenType) {
        boolean isTrailing = true;

        if (tokenType == TokenTypes.BLOCK_COMMENT_BEGIN) {
            final DetailAST commentEnd = ast.getLastChild();

            if (commentEnd != null && commentEnd.getType() == TokenTypes.BLOCK_COMMENT_END) {

                if (commentEnd.getLineNo() > ast.getLineNo()) {
                    isTrailing = true;
                }
                else {
                    final DetailAST nextToken = ast.getNextSibling();

                    if (nextToken != null
                        && nextToken.getLineNo() == ast.getLineNo()
                        && nextToken.getType() != TokenTypes.SINGLE_LINE_COMMENT
                        && nextToken.getType() != TokenTypes.BLOCK_COMMENT_BEGIN) {
                        isTrailing = false;
                    }
                }
            }
        }

        return isTrailing;
    }

    /**
     * Checks a literal for Unicode escape violations.
     *
     * @param ast the literal AST node
     * @param hasTrailing whether the literal has a trailing comment
     */
    private void checkLiteral(DetailAST ast, boolean hasTrailing) {
        final String literal = CheckUtil.stripIndentAndInitialNewLineFromTextBlock(ast.getText());
        if (hasUnicodeChar(literal) && !isAllowed(literal, hasTrailing)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Determines if a Unicode escape is allowed based on configuration.
     *
     * @param literal the literal text
     * @param hasTrailing whether the literal has a trailing comment
     * @return true if the Unicode escape is allowed
     */
    private boolean isAllowed(String literal, boolean hasTrailing) {
        return allowByTailComment && hasTrailing
            || isAllCharactersEscaped(literal)
            || allowEscapesForControlCharacters
            && isOnlyUnicodeValidChars(literal, UNICODE_CONTROL)
            || allowNonPrintableEscapes
            && isOnlyUnicodeValidChars(literal, NON_PRINTABLE_CHARS);
    }

    /**
     * Checks if literal has Unicode chars.
     *
     * @param literal String literal.
     * @return true if literal has Unicode chars.
     */
    private static boolean hasUnicodeChar(String literal) {
        final String literalWithoutEscapedBackslashes =
                ESCAPED_BACKSLASH.matcher(literal).replaceAll("");
        return UNICODE_REGEXP.matcher(literalWithoutEscapedBackslashes).find();
    }

    /**
     * Check if String literal contains Unicode control chars.
     *
     * @param literal String literal.
     * @param pattern RegExp for valid characters.
     * @return true, if String literal contains Unicode control chars.
     */
    private static boolean isOnlyUnicodeValidChars(String literal, Pattern pattern) {
        final int unicodeMatchesCounter =
                countMatches(UNICODE_REGEXP, literal);
        final int unicodeValidMatchesCounter =
                countMatches(pattern, literal);
        return unicodeMatchesCounter - unicodeValidMatchesCounter == 0;
    }

    /**
     * Count regexp matches into String literal.
     *
     * @param pattern pattern.
     * @param target String literal.
     * @return count of regexp matches.
     */
    private static int countMatches(Pattern pattern, String target) {
        int matcherCounter = 0;
        final Matcher matcher = pattern.matcher(target);
        while (matcher.find()) {
            matcherCounter++;
        }
        return matcherCounter;
    }

    /**
     * Checks if all characters in String literal is escaped.
     *
     * @param literal current literal.
     * @return true if all characters in String literal is escaped.
     */
    private boolean isAllCharactersEscaped(String literal) {
        return allowIfAllCharactersEscaped
                && ALL_ESCAPED_CHARS.matcher(literal).find();
    }

}

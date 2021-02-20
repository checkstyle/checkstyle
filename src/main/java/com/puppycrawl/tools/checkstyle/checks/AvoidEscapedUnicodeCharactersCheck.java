////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Restricts using
 * <a href = "https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.3">
 * Unicode escapes</a>
 * (such as &#92;u221e). It is possible to allow using escapes for
 * <a href="https://en.wiktionary.org/wiki/Appendix:Control_characters">
 * non-printable, control characters</a>.
 * Also, this check can be configured to allow using escapes
 * if trail comment is present. By the option it is possible to
 * allow using escapes if literal contains only them.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowEscapesForControlCharacters} - Allow use escapes for
 * non-printable, control characters.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowByTailComment} - Allow use escapes if trail comment is present.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowIfAllCharactersEscaped} - Allow if all characters in literal are escaped.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code allowNonPrintableEscapes} - Allow use escapes for
 * non-printable, whitespace characters.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"/&gt;
 * </pre>
 * <p>
 * Examples of using Unicode:</p>
 * <pre>
 * String unitAbbrev = "μs";     // OK, perfectly clear even without a comment.
 * String unitAbbrev = "&#92;u03bcs";// violation, the reader has no idea what this is.
 * return '&#92;ufeff' + content;    // OK, an example of non-printable,
 *                               // control characters (byte order mark).
 * </pre>
 * <p>
 * An example of how to configure the check to allow using escapes
 * for non-printable, control characters:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *   &lt;property name="allowEscapesForControlCharacters" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of using escapes for non-printable, control characters:
 * </p>
 * <pre>
 * String unitAbbrev = "μs";      // OK, a normal String
 * String unitAbbrev = "&#92;u03bcs"; // violation, "&#92;u03bcs" is a printable character.
 * return '&#92;ufeff' + content;     // OK, non-printable control character.
 * </pre>
 * <p>
 * An example of how to configure the check to allow using escapes
 * if trail comment is present:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *   &lt;property name="allowByTailComment" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example of using escapes if trail comment is present:
 * </p>
 * <pre>
 * String unitAbbrev = "μs";      // OK, a normal String
 * String unitAbbrev = "&#92;u03bcs"; // OK, Greek letter mu, "s"
 * return '&#92;ufeff' + content;
 * // -----^--------------------- violation, comment is not used within same line.
 * </pre>
 * <p>
 * An example of how to configure the check to allow if
 * all characters in literal are escaped.
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *   &lt;property name="allowIfAllCharactersEscaped" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example of using escapes if all characters in literal are escaped:</p>
 * <pre>
 * String unitAbbrev = "μs";      // OK, a normal String
 * String unitAbbrev = "&#92;u03bcs"; // violation, not all characters are escaped ('s').
 * String unitAbbrev = "&#92;u03bc&#92;u03bc&#92;u03bc"; // OK
 * String unitAbbrev = "&#92;u03bc&#92;u03bcs";// violation, not all characters are escaped ('s').
 * return '&#92;ufeff' + content;          // OK, all control characters are escaped
 * </pre>
 * <p>An example of how to configure the check to allow using escapes
 * for non-printable whitespace characters:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *   &lt;property name="allowNonPrintableEscapes" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example of using escapes for non-printable whitespace characters:</p>
 * <pre>
 * String unitAbbrev = "μs";       // OK, a normal String
 * String unitAbbrev1 = "&#92;u03bcs"; // violation, printable escape character.
 * String unitAbbrev2 = "&#92;u03bc&#92;u03bc&#92;u03bc"; // violation, printable escape character.
 * String unitAbbrev3 = "&#92;u03bc&#92;u03bcs";// violation, printable escape character.
 * return '&#92;ufeff' + content;           // OK, non-printable escape character.
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code forbid.escaped.unicode.char}
 * </li>
 * </ul>
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
    private static final Pattern UNICODE_REGEXP = Pattern.compile("\\\\u+[a-fA-F0-9]{4}");

    /**
     * Regular expression Unicode control characters.
     *
     * @see <a href="https://en.wiktionary.org/wiki/Appendix:Control_characters">
     *     Appendix:Control characters</a>
     */
    private static final Pattern UNICODE_CONTROL = Pattern.compile("\\\\u+"
            + "(00[0-1][0-9A-Fa-f]"
            + "|00[8-9][0-9A-Fa-f]"
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
     * See "EscapeSequence" at
     * https://docs.oracle.com/javase/specs/jls/se15/html/jls-3.html#jls-3.10.7
     */
    private static final Pattern ALL_ESCAPED_CHARS = Pattern.compile("^((\\\\u+)[a-fA-F0-9]{4}"
            + "|\""
            + "|'"
            + "|\\\\"
            + "|\\\\b"
            + "|\\\\f"
            + "|\\\\n"
            + "|\\\\r"
            + "|\\\\s"
            + "|\\\\t"
            + ")+$");

    /** Regular expression for escaped backslash. */
    private static final Pattern ESCAPED_BACKSLASH = Pattern.compile("\\\\\\\\");

    /** Regular expression for non-printable unicode chars. */
    private static final Pattern NON_PRINTABLE_CHARS = Pattern.compile("\\\\u+0000"
            + "|\\\\u+0009"
            + "|\\\\u+000[bB]"
            + "|\\\\u+000[cC]"
            + "|\\\\u+0020"
            + "|\\\\u+007[fF]"
            + "|\\\\u+0085"
            + "|\\\\u+009[fF]"
            + "|\\\\u+00[aA]0"
            + "|\\\\u+00[aA][dD]"
            + "|\\\\u+04[fF]9"
            + "|\\\\u+05[bB][eE]"
            + "|\\\\u+05[dD]0"
            + "|\\\\u+05[eE][aA]"
            + "|\\\\u+05[fF]3"
            + "|\\\\u+05[fF]4"
            + "|\\\\u+0600"
            + "|\\\\u+0604"
            + "|\\\\u+061[cC]"
            + "|\\\\u+06[dD]{2}"
            + "|\\\\u+06[fF]{2}"
            + "|\\\\u+070[fF]"
            + "|\\\\u+0750"
            + "|\\\\u+077[fF]"
            + "|\\\\u+0[eE]00"
            + "|\\\\u+0[eE]7[fF]"
            + "|\\\\u+1680"
            + "|\\\\u+180[eE]"
            + "|\\\\u+1[eE]00"
            + "|\\\\u+2000"
            + "|\\\\u+2001"
            + "|\\\\u+2002"
            + "|\\\\u+2003"
            + "|\\\\u+2004"
            + "|\\\\u+2005"
            + "|\\\\u+2006"
            + "|\\\\u+2007"
            + "|\\\\u+2008"
            + "|\\\\u+2009"
            + "|\\\\u+200[aA]"
            + "|\\\\u+200[fF]"
            + "|\\\\u+2025"
            + "|\\\\u+2028"
            + "|\\\\u+2029"
            + "|\\\\u+202[fF]"
            + "|\\\\u+205[fF]"
            + "|\\\\u+2064"
            + "|\\\\u+2066"
            + "|\\\\u+2067"
            + "|\\\\u+2068"
            + "|\\\\u+2069"
            + "|\\\\u+206[aA]"
            + "|\\\\u+206[fF]"
            + "|\\\\u+20[aA][fF]"
            + "|\\\\u+2100"
            + "|\\\\u+213[aA]"
            + "|\\\\u+3000"
            + "|\\\\u+[dD]800"
            + "|\\\\u+[fF]8[fF]{2}"
            + "|\\\\u+[fF][bB]50"
            + "|\\\\u+[fF][dD][fF]{2}"
            + "|\\\\u+[fF][eE]70"
            + "|\\\\u+[fF][eE][fF]{2}"
            + "|\\\\u+[fF]{2}0[eE]"
            + "|\\\\u+[fF]{2}61"
            + "|\\\\u+[fF]{2}[dD][cC]"
            + "|\\\\u+[fF]{3}9"
            + "|\\\\u+[fF]{3}[aA]"
            + "|\\\\u+[fF]{3}[bB]"
            + "|\\\\u+[fF]{4}");

    /** Cpp style comments. */
    private Map<Integer, TextBlock> singlelineComments;
    /** C style comments. */
    private Map<Integer, List<TextBlock>> blockComments;

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
     */
    public final void setAllowEscapesForControlCharacters(boolean allow) {
        allowEscapesForControlCharacters = allow;
    }

    /**
     * Setter to allow use escapes if trail comment is present.
     *
     * @param allow user's value.
     */
    public final void setAllowByTailComment(boolean allow) {
        allowByTailComment = allow;
    }

    /**
     * Setter to allow if all characters in literal are escaped.
     *
     * @param allow user's value.
     */
    public final void setAllowIfAllCharactersEscaped(boolean allow) {
        allowIfAllCharactersEscaped = allow;
    }

    /**
     * Setter to allow use escapes for non-printable, whitespace characters.
     *
     * @param allow user's value.
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
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        singlelineComments = getFileContents().getSingleLineComments();
        blockComments = getFileContents().getBlockComments();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String literal =
            CheckUtil.stripIndentAndInitialNewLineFromTextBlock(ast.getText());

        if (hasUnicodeChar(literal) && !(allowByTailComment && hasTrailComment(ast)
                || isAllCharactersEscaped(literal)
                || allowEscapesForControlCharacters
                        && isOnlyUnicodeValidChars(literal, UNICODE_CONTROL)
                || allowNonPrintableEscapes
                        && isOnlyUnicodeValidChars(literal, NON_PRINTABLE_CHARS))) {
            log(ast, MSG_KEY);
        }
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
     * Check if trail comment is present after ast token.
     *
     * @param ast current token.
     * @return true if trail comment is present after ast token.
     */
    private boolean hasTrailComment(DetailAST ast) {
        int lineNo = ast.getLineNo();

        // Since the trailing comment in the case of text blocks must follow the """ delimiter,
        // we need to look for it after TEXT_BLOCK_LITERAL_END.
        if (ast.getType() == TokenTypes.TEXT_BLOCK_CONTENT) {
            lineNo = ast.getNextSibling().getLineNo();
        }
        boolean result = false;
        if (singlelineComments.containsKey(lineNo)) {
            result = true;
        }
        else {
            final List<TextBlock> commentList = blockComments.get(lineNo);
            if (commentList != null) {
                final TextBlock comment = commentList.get(commentList.size() - 1);
                final String line = getLines()[lineNo - 1];
                result = isTrailingBlockComment(comment, line);
            }
        }
        return result;
    }

    /**
     * Whether the C style comment is trailing.
     *
     * @param comment the comment to check.
     * @param line the line where the comment starts.
     * @return true if the comment is trailing.
     */
    private static boolean isTrailingBlockComment(TextBlock comment, String line) {
        return comment.getText().length != 1
            || CommonUtil.isBlank(line.substring(comment.getEndColNo() + 1));
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

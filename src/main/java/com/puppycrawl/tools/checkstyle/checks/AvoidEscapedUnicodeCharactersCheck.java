////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Restrict using <a href =
 * "http://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.3">
 * Unicode escapes</a> (e.g. \u221e).
 * It is possible to allow using escapes for
 * <a href="https://en.wiktionary.org/wiki/Appendix:Control_characters">
 * non-printable(control) characters</a>.
 * Also, this check can be configured to allow using escapes
 * if trail comment is present. By the option it is possible to
 * allow using escapes if literal contains only them. By the option it
 * is possible to allow using escapes for space literals.
 * </p>
 * <p>
 * Examples of using Unicode:</p>
 * <pre>
 * String unitAbbrev = "μs"; //Best: perfectly clear even without a comment.
 * String unitAbbrev = "\u03bcs"; //Poor: the reader has no idea what this is.
 * </pre>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"/&gt;
 * </pre>
 * <p>
 * An example of non-printable(control) characters.
 * </p>
 * <pre>
 * return '\ufeff' + content; // byte order mark
 * </pre>
 * <p>
 * An example of how to configure the check to allow using escapes
 * for non-printable(control) characters:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *     &lt;property name="allowEscapesForControlCharacters" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example of using escapes with trail comment:
 * </p>
 * <pre>
 * String unitAbbrev = "\u03bcs"; // Greek letter mu, "s"
 * </pre>
 * <p>An example of how to configure the check to allow using escapes
 * if trail comment is present:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *     &lt;property name="allowByTailComment" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example of using escapes if literal contains only them:
 * </p>
 * <pre>
 * String unitAbbrev = "\u03bc\u03bc\u03bc";
 * </pre>
 * <p>An example of how to configure the check to allow escapes
 * if literal contains only them:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *    &lt;property name="allowIfAllCharactersEscaped" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>An example of how to configure the check to allow non-printable escapes:
 * </p>
 * <pre>
 * &lt;module name="AvoidEscapedUnicodeCharacters"&gt;
 *    &lt;property name="allowNonPrintableEscapes" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author maxvetrenko
 *
 */
public class AvoidEscapedUnicodeCharactersCheck
    extends AbstractCheck {
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "forbid.escaped.unicode.char";

    /** Regular expression for Unicode chars. */
    private static final Pattern UNICODE_REGEXP = Pattern.compile("\\\\u[a-fA-F0-9]{4}");

    /** Regular expression Unicode control characters. */
    private static final Pattern UNICODE_CONTROL = Pattern.compile("\\\\(u|U)"
            + "(00[0-1][0-1A-Fa-f]|00[8-9][0-9A-Fa-f]|034(f|F)|070(f|F)"
            + "|180(e|E)|200[b-fB-F]|202[b-eB-E]|206[0-4a-fA-F]"
            + "|[fF]{3}[9a-bA-B]|[fF][eE][fF]{2})");

    /** Regular expression for all escaped chars. */
    private static final Pattern ALL_ESCAPED_CHARS =
            Pattern.compile("^((\\\\u)[a-fA-F0-9]{4}"
                    + "||\\\\b|\\\\t|\\\\n|\\\\f|\\\\r|\\\\|\"|\')+$");

    /** Regular expression for non-printable unicode chars. */
    private static final Pattern NON_PRINTABLE_CHARS = Pattern.compile("\\\\u1680|\\\\u2028"
            + "|\\\\u2029|\\\\u205(f|F)|\\\\u3000|\\\\u2007|\\\\u2000|\\\\u200(a|A)"
            + "|\\\\u007(F|f)|\\\\u009(f|F)|\\\\u(f|F){4}|\\\\u007(F|f)|\\\\u00(a|A)(d|D)"
            + "|\\\\u0600|\\\\u061(c|C)|\\\\u06(d|D){2}|\\\\u070(f|F)|\\\\u1680|\\\\u180(e|E)"
            + "|\\\\u2000|\\\\u2028|\\\\u205(f|F)|\\\\u2066|\\\\u2067|\\\\u2068|\\\\u2069"
            + "|\\\\u206(a|A)|\\\\u(d|D)800|\\\\u(f|F)(e|E)(f|F){2}|\\\\u(f|F){3}9"
            + "|\\\\u(f|F){3}(a|A)|\\\\u0020|\\\\u00(a|A)0|\\\\u00(a|A)(d|D)|\\\\u0604"
            + "|\\\\u061(c|C)|\\\\u06(d|D){2}|\\\\u070(f|F)|\\\\u1680|\\\\u180(e|E)|\\\\u200(f|F)"
            + "|\\\\u202(f|F)|\\\\u2064|\\\\u2066|\\\\u2067|\\\\u2068|\\\\u2069|\\\\u206(f|F)"
            + "|\\\\u(f|F)8(f|F){2}|\\\\u(f|F)(e|E)(f|F){2}|\\\\u(f|F){3}9|\\\\u(f|F){3}(b|B)"
            + "|\\\\u05(d|D)0|\\\\u05(f|F)3|\\\\u0600|\\\\u0750|\\\\u0(e|E)00|\\\\u1(e|E)00"
            + "|\\\\u2100|\\\\u(f|F)(b|B)50|\\\\u(f|F)(e|E)70|\\\\u(F|f){2}61|\\\\u04(f|F)9"
            + "|\\\\u05(b|B)(e|E)|\\\\u05(e|E)(a|A)|\\\\u05(f|F)4|\\\\u06(f|F){2}"
            + "|\\\\u077(f|F)|\\\\u0(e|E)7(f|F)|\\\\u20(a|A)(f|F)|\\\\u213(a|A)|\\\\u0000"
            + "|\\\\u(f|F)(d|D)(f|F){2}|\\\\u(f|F)(e|E)(f|F){2}|\\\\u(f|F){2}(d|D)(c|C)"
            + "|\\\\u2002|\\\\u0085|\\\\u200(a|A)|\\\\u2005|\\\\u2000|\\\\u2029|\\\\u000(B|b)"
            + "|\\\\u2008|\\\\u2003|\\\\u205(f|F)|\\\\u1680|\\\\u0009|\\\\u0020|\\\\u2006"
            + "|\\\\u2001|\\\\u202(f|F)|\\\\u00(a|A)0|\\\\u000(c|C)|\\\\u2009|\\\\u2004|\\\\u2028"
            + "|\\\\u2028|\\\\u2007|\\\\u2004|\\\\u2028|\\\\u2007|\\\\u2025"
            + "|\\\\u(f|F){2}0(e|E)|\\\\u(f|F){2}61");

    /** Cpp style comments. */
    private Map<Integer, TextBlock> singlelineComments;
    /** C style comments. */
    private Map<Integer, List<TextBlock>> blockComments;

    /** Allow use escapes for non-printable(control) characters.  */
    private boolean allowEscapesForControlCharacters;

    /** Allow use escapes if trail comment is present. */
    private boolean allowByTailComment;

    /** Allow if all characters in literal are escaped. */
    private boolean allowIfAllCharactersEscaped;

    /** Allow escapes for space literals. */
    private boolean allowNonPrintableEscapes;

    /**
     * Set allowIfAllCharactersEscaped.
     * @param allow user's value.
     */
    public final void setAllowEscapesForControlCharacters(boolean allow) {
        allowEscapesForControlCharacters = allow;
    }

    /**
     * Set allowByTailComment.
     * @param allow user's value.
     */
    public final void setAllowByTailComment(boolean allow) {
        allowByTailComment = allow;
    }

    /**
     * Set allowIfAllCharactersEscaped.
     * @param allow user's value.
     */
    public final void setAllowIfAllCharactersEscaped(boolean allow) {
        allowIfAllCharactersEscaped = allow;
    }

    /**
     * Set allowSpaceEscapes.
     * @param allow user's value.
     */
    public final void setAllowNonPrintableEscapes(boolean allow) {
        allowNonPrintableEscapes = allow;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        singlelineComments = getFileContents().getCppComments();
        blockComments = getFileContents().getCComments();
    }

    @Override
    public void visitToken(DetailAST ast) {

        final String literal = ast.getText();

        if (hasUnicodeChar(literal) && !(allowByTailComment && hasTrailComment(ast)
                || isAllCharactersEscaped(literal)
                || allowEscapesForControlCharacters
                        && isOnlyUnicodeValidChars(literal, UNICODE_CONTROL)
                || allowNonPrintableEscapes
                        && isOnlyUnicodeValidChars(literal, NON_PRINTABLE_CHARS))) {
            log(ast.getLineNo(), MSG_KEY);
        }
    }

    /**
     * Checks if literal has Unicode chars.
     * @param literal String literal.
     * @return true if literal has Unicode chars.
     */
    private static boolean hasUnicodeChar(String literal) {
        return UNICODE_REGEXP.matcher(literal).find();
    }

    /**
     * Check if String literal contains Unicode control chars.
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
     * @param ast current token.
     * @return true if trail comment is present after ast token.
     */
    private boolean hasTrailComment(DetailAST ast) {
        boolean result = false;
        final int lineNo = ast.getLineNo();
        if (singlelineComments.containsKey(lineNo)) {
            result = true;
        }
        else {
            final String line = getLines()[lineNo - 1];
            final List<TextBlock> commentList = blockComments.get(lineNo);
            if (commentList != null) {
                final TextBlock comment = commentList.get(commentList.size() - 1);
                result = isTrailingCComent(comment, line);
            }
        }
        return result;
    }

    /**
     * Whether the C style comment is trailing.
     * @param comment the comment to check.
     * @param line the line where the comment starts.
     * @return true if the comment is trailing.
     */
    private static boolean isTrailingCComent(TextBlock comment, String line) {
        return comment.getText().length != 1
            || line.substring(comment.getEndColNo() + 1).trim().isEmpty();
    }

    /**
     * Count regexp matches into String literal.
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
     * @param literal current literal.
     * @return true if all characters in String literal is escaped.
     */
    private boolean isAllCharactersEscaped(String literal) {
        return allowIfAllCharactersEscaped
                && ALL_ESCAPED_CHARS.matcher(literal.substring(1,
                        literal.length() - 1)).find();
    }
}

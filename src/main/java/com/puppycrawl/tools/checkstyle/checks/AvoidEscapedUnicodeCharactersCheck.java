////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>
 * Restrict using <a href =
 * "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.3">
 * Unicode escapes</a> (e.g. \u221e).
 * It is possible to allow using escapes for
 * <a href="http://en.wiktionary.org/wiki/Appendix:Control_characters">
 * non-printable(control) characters</a>.
 * Also, this check can be configured to allow using escapes
 * if trail comment is present. By the option it is possible to
 * allow using escapes if literal contains only them.
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
 *
 * @author maxvetrenko
 *
 */
public class AvoidEscapedUnicodeCharactersCheck
    extends Check
{
     /** Regexp for Unicode chars */
    private static Pattern sUnicodeRegexp =
            Utils.getPattern("\\\\u[a-fA-F0-9]{4}");

    /** Regexp Unicode control characters */
    private static Pattern sUnicodeControl = Utils.getPattern("\\\\(u|U)"
            + "(00[0-1][0-1A-Fa-f]|00[8-9][0-1A-Fa-f]|034(f|F)|070(f|F)"
            + "|180(e|E)|200[b-fB-F]|202[b-eB-E]|206[0-4a-fA-F]"
            + "|[fF]{3}[9a-bA-B]|[fF][eE][fF]{2})");

    /** Regexp for trail comment */
    private static Pattern sCommentRegexp = Utils.getPattern(";[ ]*//+"
            + "[a-zA-Z0-9 ]*|;[ ]*/[*]{1}+[a-zA-Z0-9 ]*");

    /** Regexp for all escaped chars*/
    private static Pattern sAllEscapedChars =
            Utils.getPattern("^((\\\\u)[a-fA-F0-9]{4})+$");

    /** Allow use escapes for non-printable(control) characters.  */
    private boolean mAllowEscapesForControlCharacters;

    /** Allow use escapes if trail comment is present*/
    private boolean mAllowByTailComment;

    /** Allow if all characters in literal are excaped*/
    private boolean mAllowIfAllCharactersEscaped;

    /**
     * Set mAllowIfAllCharactersEscaped.
     * @param aAllow user's value.
     */
    public final void setAllowEscapesForControlCharacters(boolean aAllow)
    {
        mAllowEscapesForControlCharacters = aAllow;
    }

    /**
     * Set mAllowByTailComment.
     * @param aAllow user's value.
     */
    public final void setAllowByTailComment(boolean aAllow)
    {
        mAllowByTailComment = aAllow;
    }

    /**
     * Set mAllowIfAllCharactersEscaped.
     * @param aAllow user's value.
     */
    public final void setAllowIfAllCharactersEscaped(boolean aAllow)
    {
        mAllowIfAllCharactersEscaped = aAllow;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {

        final String literal = aAST.getText();

        if (hasUnicodeChar(literal)) {
            if (!(hasTrailComment(aAST)
                    || isAllCharactersEscaped(literal)
                    || isOnlyUnicodeControlChars(literal)))
            {
                log(aAST.getLineNo(), "forbid.escaped.unicode.char");
            }
        }
    }

    /**
     * Checks if literal has Unicode chars.
     * @param aLiteral String literal.
     * @return true if literal has Unicode chars.
     */
    private boolean hasUnicodeChar(String aLiteral)
    {
        return sUnicodeRegexp.matcher(aLiteral).find();
    }

    /**
     * Check if String literal contains Unicode control chars.
     * @param aLiteral String llteral.
     * @return true, if String literal contains Unicode control chars.
     */
    private boolean isOnlyUnicodeControlChars(String aLiteral)
    {
        return mAllowEscapesForControlCharacters
                && sUnicodeControl.matcher(aLiteral).find();
    }

    /**
     * Check if trail comment is present after aAst token.
     * @param aAst current token.
     * @return true if trail comment is present after aAst token.
     */
    private boolean hasTrailComment(DetailAST aAst)
    {
        boolean result = false;
        final DetailAST variableDef = getVariableDef(aAst);

        if (variableDef != null) {

            DetailAST comma = variableDef.getNextSibling();

            if (comma.getType() != TokenTypes.COMMA) {
                comma = variableDef.getLastChild();
            }

            final int lineNo = comma.getLineNo();
            final String currentLine = getLines()[lineNo - 1];

            if (sCommentRegexp.matcher(currentLine).find()) {
                result = true;
            }
        }
        return mAllowByTailComment && result;
    }

    /**
     * Get variable definition.
     * @param aAst current token.
     * @return variable definition.
     */
    private DetailAST getVariableDef(DetailAST aAst)
    {
        DetailAST result = aAst.getParent();
        while (result != null
                && result.getType() != TokenTypes.VARIABLE_DEF)
        {
            result = result.getParent();
        }
        return result;
    }

    /**
     * Checks if all characters in String literal is escaped.
     * @param aLiteral current literal.
     * @return true if all characters in String literal is escaped.
     */
    private boolean isAllCharactersEscaped(String aLiteral)
    {
        return mAllowIfAllCharactersEscaped
                && sAllEscapedChars.matcher(aLiteral.substring(1,
                        aLiteral.length() - 1)).find();
    }
}

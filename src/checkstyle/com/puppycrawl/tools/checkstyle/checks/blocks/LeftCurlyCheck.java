////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 *
 * <p>
 * Checks for the placement of left curly braces (
 * <span class="code">'{'</span>) for code blocks.
 * </p>
 *
 * <subsection name="Properties">
 * <table>
 *   <tr>
 *     <th>name</th>
 *     <th>description</th>
 *     <th>type</th>
 *     <th>default value</th>
 *   </tr>
 *   <tr>
 *     <td>option</td>
 *     <td>
 *       policy on placement of a left curly brace (
 *       <span class="code">'{'</span>)
 *     </td>
 *     <td>
 *       <a href="property_types.html#lcurly">left curly brace policy</a>
 *     </td>
 *     <td><span class="default">eol</span></td>
 *   </tr>
 *   <tr>
 *     <td>maxLineLength</td>
 *     <td>maximum number of characters in a line</td>
 *     <td><a href="property_types.html#integer">integer</a></td>
 *     <td><span class="default">80</span></td>
 *   </tr>
 *   <tr>
 *     <td>tokens</td>
 *     <td>blocks to check</td>
 *
 *     <td>
 *       subset of tokens
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">CLASS_DEF</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">CTOR_DEF</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">INTERFACE_DEF</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">LITERAL_CATCH</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">LITERAL_DO</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">LITERAL_ELSE</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">LITERAL_FINALLY</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">LITERAL_FOR</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">LITERAL_IF</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">LITERAL_SWITCH</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">LITERAL_SYNCHRONIZED</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">LITERAL_TRY</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">LITERAL_WHILE</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">METHOD_DEF</a>
 *     </td>
 *
 *     <td>all tokens</td>
 *   </tr>
 * </table>
 * </subsection>
 *
 * <subsection name="Examples">
 * <p> To configure the check: </p>
 *
 * <source>
 * &lt;module name=&quot;LeftCurly&quot;/&gt;
 * </source>
 *
 * <p>
 * To configure the check to apply the <span class="code">nl</span> policy to
 * type blocks:
 * </p>
 *
 * <source>
 * &lt;module name=&quot;LeftCurly&quot;&gt;
 *     &lt;property name=&quot;option&quot; value=&quot;nl&quot;/&gt;
 *     &lt;property name=&quot;tokens&quot; value=&quot;CLASS_DEF,INTERFACE_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </source>
 * </subsection>
 *
 * <subsection name="Package">
 *   <p> com.puppycrawl.tools.checkstyle.checks.blocks </p>
 * </subsection>
 *
 * <subsection name="Parent Module">
 *   <p> <a href="config.html#TreeWalker">TreeWalker</a> </p>
 * </subsection>
 *
 * @author Oliver Burn
 * @author lkuehne
 * @version 1.0
 */
public class LeftCurlyCheck
    extends AbstractOptionCheck
{
    /** default maximum line length */
    private static final int DEFAULT_MAX_LINE_LENGTH = 80;

    /** TODO: replace this ugly hack **/
    private int mMaxLineLength = DEFAULT_MAX_LINE_LENGTH;

    /**
     * Creates a default instance and sets the policy to EOL.
     */
    public LeftCurlyCheck()
    {
        super(LeftCurlyOption.EOL);
    }

    /**
     * Sets the maximum line length used in calculating the placement of the
     * left curly brace.
     * @param aMaxLineLength the max allowed line length
     */
    public void setMaxLineLength(int aMaxLineLength)
    {
        mMaxLineLength = aMaxLineLength;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            // TODO: need to handle....
            //TokenTypes.STATIC_INIT,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST startToken;
        final DetailAST brace;

        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF :
        case TokenTypes.METHOD_DEF :
            startToken = aAST;
            brace = aAST.findFirstToken(TokenTypes.SLIST);
            break;

        case TokenTypes.INTERFACE_DEF :
        case TokenTypes.CLASS_DEF :
        case TokenTypes.ANNOTATION_DEF :
        case TokenTypes.ENUM_DEF :
        case TokenTypes.ENUM_CONSTANT_DEF :
            startToken = (DetailAST) aAST.getFirstChild();
            brace = (DetailAST) aAST.findFirstToken(TokenTypes.OBJBLOCK)
                .getFirstChild();
            break;

        case TokenTypes.LITERAL_WHILE:
        case TokenTypes.LITERAL_CATCH:
        case TokenTypes.LITERAL_SYNCHRONIZED:
        case TokenTypes.LITERAL_FOR:
        case TokenTypes.LITERAL_TRY:
        case TokenTypes.LITERAL_FINALLY:
        case TokenTypes.LITERAL_DO:
        case TokenTypes.LITERAL_IF :
            startToken = aAST;
            brace = aAST.findFirstToken(TokenTypes.SLIST);
            break;

        case TokenTypes.LITERAL_ELSE :
            startToken = aAST;
            final DetailAST candidate = (DetailAST) aAST.getFirstChild();
            brace =
                (candidate.getType() == TokenTypes.SLIST)
                ? candidate
                : null; // silently ignore
            break;

        case TokenTypes.LITERAL_SWITCH :
            startToken = aAST;
            brace = aAST.findFirstToken(TokenTypes.LCURLY);
            break;

        default :
            startToken = null;
            brace = null;
        }

        if ((brace != null) && (startToken != null)) {
            verifyBrace(brace, startToken);
        }
    }

    /**
     * Verifies that a specified left curly brace is placed correctly
     * according to policy.
     * @param aBrace token for left curly brace
     * @param aStartToken token for start of expression
     */
    private void verifyBrace(final DetailAST aBrace,
                             final DetailAST aStartToken)
    {
        final String braceLine = getLines()[aBrace.getLineNo() - 1];

        // calculate the previous line length without trailing whitespace. Need
        // to handle the case where there is no previous line, cause the line
        // being check is the first line in the file.
        final int prevLineLen = (aBrace.getLineNo() == 1)
            ? mMaxLineLength
            : Utils.lengthMinusTrailingWhitespace(
                getLines()[aBrace.getLineNo() - 2]);

        // Check for being told to ignore, or have '{}' which is a special case
        if ((braceLine.length() > (aBrace.getColumnNo() + 1))
            && (braceLine.charAt(aBrace.getColumnNo() + 1) == '}'))
        {
            ; // ignore
        }
        else if (getAbstractOption() == LeftCurlyOption.NL) {
            if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.new", "{");
            }
        }
        else if (getAbstractOption() == LeftCurlyOption.EOL) {
            if (Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)
                && ((prevLineLen + 2) <= mMaxLineLength))
            {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.previous", "{");
            }
        }
        else if (getAbstractOption() == LeftCurlyOption.NLOW) {
            if (aStartToken.getLineNo() == aBrace.getLineNo()) {
                ; // all ok as on the same line
            }
            else if ((aStartToken.getLineNo() + 1) == aBrace.getLineNo()) {
                if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                    log(aBrace.getLineNo(), aBrace.getColumnNo(),
                        "line.new", "{");
                }
                else if ((prevLineLen + 2) <= mMaxLineLength) {
                    log(aBrace.getLineNo(), aBrace.getColumnNo(),
                        "line.previous", "{");
                }
            }
            else if (!Utils.whitespaceBefore(aBrace.getColumnNo(), braceLine)) {
                log(aBrace.getLineNo(), aBrace.getColumnNo(),
                    "line.new", "{");
            }
        }
    }
}

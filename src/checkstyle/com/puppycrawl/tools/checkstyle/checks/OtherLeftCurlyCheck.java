////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Checks the placement of left curly braces on other blocks apart from
 * methods and types.
 * </p>
 * <p> By default the check will check the following blocks:
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 * </p>
 * <p>
 * The policy to verify is specified using the {@link
 * LeftCurlyOption} class and defaults to {@link LeftCurlyOption#EOL}. Policies
 * {@link LeftCurlyOption#EOL} and {@link LeftCurlyOption#NLOW} take into
 * account property maxLineLength. The default value for maxLineLength is 80. 
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;config name="OtherLeftCurlyCheck"/&gt;
 * </pre> 
 * <p>
 * An example of how to configure the check with policy
 * {@link LeftCurlyOption#NLOW} and maxLineLength 120 is:
 * </p>
 * <pre>
 * &lt;config name="OtherLeftCurlyCheck"&gt;
 *     &lt;property name="option" value="nlow"/&gt;
 *     &lt;property name="maxLineLength" value="120"/&gt;
 * &lt;/config&gt;
 * </pre>
 * <p>
 * An example of how to configure the check with policy
 * {@link LeftCurlyOption#NL} for <code>if</code> and <code>else</code> blocks
 *  is:
 * </p>
 * <pre>
 * &lt;config name="OtherLeftCurlyCheck"&gt;
 *     &lt;property name="option" value="nl"/&gt;
 *     &lt;property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/&gt;
 * &lt;/config&gt;
 * </pre> 
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author lkuehne
 * @version 1.0
 */
public class OtherLeftCurlyCheck
    extends AbstractLeftCurlyCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_WHILE,
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
        final DetailAST startToken = aAST;
        final DetailAST brace;

        switch (aAST.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_SYNCHRONIZED:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_FINALLY:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_IF:
                brace = aAST.findFirstToken(TokenTypes.SLIST);
                break;
                
            case TokenTypes.LITERAL_ELSE:
                final DetailAST candidate = (DetailAST) aAST.getFirstChild();
                brace =
                    (candidate.getType() == TokenTypes.SLIST)
                        ? candidate
                        : null; // silently ignore
                break;
                
            case TokenTypes.LITERAL_SWITCH :
                brace = aAST.findFirstToken(TokenTypes.LCURLY);
                break;

            default:
                brace = null;
        }

        if (brace != null) {
            verifyBrace(brace, startToken);
        }
    }
}

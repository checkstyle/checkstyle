////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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


import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for braces around code blocks.
 * </p>
 * <p> By default the check will check the following blocks:
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"/&gt;
 * </pre>
 * <p> An example of how to configure the check for <code>if</code> and
 * <code>else</code> blocks is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"&gt;
 *     &lt;property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/&gt;
 * &lt;/module&gt;
 * </pre>
 * Check has an option <b>allowSingleLineIf</b> which allows one line
 * if-statements without braces, e.g.:
 * <p>
 * <code>
 * if (obj.isValid()) return true;
 * </code>
 * </p>
 * <br>
 *
 * @author Rick Giles
 * @author <a href="mailto:nesterenko-aleksey@list.ru">Aleksey Nesterenko</a>
 */
public class NeedBracesCheck extends Check
{
    /**
     * Check's option for skipping single-line if-statements.
     */
    private boolean allowSingleLineIf;

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_NEED_BRACES = "needBraces";

    /**
     * Setter.
     * @param allowSingleLineIf Check's option for skipping single-line if-statements
     */
    public void setAllowSingleLineIf(boolean allowSingleLineIf)
    {
        this.allowSingleLineIf = allowSingleLineIf;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_WHILE,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        final DetailAST slistAST = ast.findFirstToken(TokenTypes.SLIST);
        boolean isElseIf = false;
        if ((ast.getType() == TokenTypes.LITERAL_ELSE)
            && (ast.findFirstToken(TokenTypes.LITERAL_IF) != null))
        {
            isElseIf = true;
        }
        boolean skipStatement = false;
        if (ast.getType() == TokenTypes.LITERAL_IF) {
            skipStatement = isSkipIfBlock(ast);
        }
        if ((slistAST == null) && !isElseIf && !skipStatement) {
            log(ast.getLineNo(), MSG_KEY_NEED_BRACES, ast.getText());
        }
    }

    /**
     * Checks if current if-block can be skipped by "need braces" warning.
     * @param literalIf {@link TokenTypes#LITERAL_IF LITERAL_IF}
     * @return true if current if block can be skipped by Check
     */
    private boolean isSkipIfBlock(DetailAST literalIf)
    {
        return allowSingleLineIf && isSingleLineIf(literalIf);
    }

    /**
     * Checks if current if-statement is single-line statement, e.g.:
     * <p>
     * <code>
     * if (obj.isValid()) return true;
     * </code>
     * </p>
     * @param literalIf {@link TokenTypes#LITERAL_IF LITERAL_IF}
     * @return true if current if-statement is single-line statement
     */
    private static boolean isSingleLineIf(DetailAST literalIf)
    {
        boolean result = false;
        final DetailAST ifBlock = literalIf.getLastChild();
        final DetailAST lastElementInIfBlock = ifBlock.getLastChild();
        if (lastElementInIfBlock != null
            && lastElementInIfBlock.getFirstChild() == null
            && literalIf.getLineNo() == lastElementInIfBlock.getLineNo())
        {
            result = true;
        }
        return result;
    }
}

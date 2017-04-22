////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Checks that local, non-final variable names conform to a format specified
 * by the format property. A catch parameter is considered to be
 * a local variable. The format is a
 * {@link Pattern regular expression}
 * and defaults to
 * <strong>^[a-z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower
 * case letter, followed by letters, digits, and underscores is:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *    &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * An example of one character variable name in
 * initialization expression(like "i") in FOR loop:
 * </p>
 * <pre>
 * for(int i = 1; i &lt; 10; i++) {}
 * </pre>
 * <p>
 * An example of how to configure the check to allow one char variable name in
 * <a href="http://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
 * initialization expressions</a> in FOR loop:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *    &lt;property name="allowOneCharVarInForLoop" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Rick Giles
 * @author maxvetrenko
 */
public class LocalVariableNameCheck
    extends AbstractNameCheck {
    /** Regexp for one-char loop variables. */
    private static final Pattern SINGLE_CHAR = Pattern.compile("^[a-z]$");

    /**
     * Allow one character name for initialization expression in FOR loop.
     */
    private boolean allowOneCharVarInForLoop;

    /** Creates a new {@code LocalVariableNameCheck} instance. */
    public LocalVariableNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    /**
     * Sets whether to allow one character name in FOR loop or not.
     *
     * @param allow Flag for allowing or not one character name in FOR loop.
     */
    public final void setAllowOneCharVarInForLoop(boolean allow) {
        allowOneCharVarInForLoop = allow;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final boolean result;
        if (allowOneCharVarInForLoop && isForLoopVariable(ast)) {
            final String variableName = ast.findFirstToken(TokenTypes.IDENT).getText();
            result = !SINGLE_CHAR.matcher(variableName).find();
        }
        else {
            final DetailAST modifiersAST = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean isFinal = modifiersAST.branchContains(TokenTypes.FINAL);
            result = !isFinal && ScopeUtils.isLocalVariableDef(ast);
        }
        return result;
    }

    /**
     * Checks if a variable is the loop's one.
     * @param variableDef variable definition.
     * @return true if a variable is the loop's one.
     */
    private static boolean isForLoopVariable(DetailAST variableDef) {
        final int parentType = variableDef.getParent().getType();
        return parentType == TokenTypes.FOR_INIT
                || parentType == TokenTypes.FOR_EACH_CLAUSE;
    }
}

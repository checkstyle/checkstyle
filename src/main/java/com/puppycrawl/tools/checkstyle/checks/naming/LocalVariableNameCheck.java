///
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks that local, non-{@code final} variable names conform to a specified pattern.
 * A catch parameter is considered to be
 * a local variable.
 * </div>
 *
 * <p>
 * This check does not support pattern variables. Instead, use
 * <a href="https://checkstyle.org/checks/naming/patternvariablename.html#PatternVariableName">
 * PatternVariableName</a>.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowOneCharVarInForLoop} - Allow one character variable name in
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
 * initialization expressions</a>
 * in FOR loop if one char variable name is prohibited by {@code format} regexp.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code format} - Sets the pattern to match valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^([a-z][a-zA-Z0-9]*|_)$"}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
public class LocalVariableNameCheck
    extends AbstractNameCheck {

    /**
     * Allow one character variable name in
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
     * initialization expressions</a>
     * in FOR loop if one char variable name is prohibited by {@code format} regexp.
     */
    private boolean allowOneCharVarInForLoop;

    /** Creates a new {@code LocalVariableNameCheck} instance. */
    public LocalVariableNameCheck() {
        super("^([a-z][a-zA-Z0-9]*|_)$");
    }

    /**
     * Setter to allow one character variable name in
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
     * initialization expressions</a>
     * in FOR loop if one char variable name is prohibited by {@code format} regexp.
     *
     * @param allow Flag for allowing or not one character name in FOR loop.
     * @since 5.8
     */
    public final void setAllowOneCharVarInForLoop(boolean allow) {
        allowOneCharVarInForLoop = allow;
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
            TokenTypes.VARIABLE_DEF,
        };
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final boolean result;
        if (allowOneCharVarInForLoop && isForLoopVariable(ast)) {
            final String variableName = ast.findFirstToken(TokenTypes.IDENT).getText();
            result = variableName.length() != 1;
        }
        else {
            final DetailAST modifiersAST = ast.findFirstToken(TokenTypes.MODIFIERS);
            final boolean isFinal = modifiersAST.findFirstToken(TokenTypes.FINAL) != null;
            result = !isFinal && ScopeUtil.isLocalVariableDef(ast);
        }
        return result;
    }

    /**
     * Checks if a variable is the loop's one.
     *
     * @param variableDef variable definition.
     * @return true if a variable is the loop's one.
     */
    private static boolean isForLoopVariable(DetailAST variableDef) {
        final int parentType = variableDef.getParent().getType();
        return parentType == TokenTypes.FOR_INIT
                || parentType == TokenTypes.FOR_EACH_CLAUSE;
    }

}

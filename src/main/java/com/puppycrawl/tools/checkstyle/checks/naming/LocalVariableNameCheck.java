///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks that local, non-{@code final} variable names conform to a specified pattern.
 * A catch parameter is considered to be
 * a local variable.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * <li>
 * Property {@code allowOneCharVarInForLoop} - Allow one character variable name in
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
 * initialization expressions</a>
 * in FOR loop if one char variable name is prohibited by {@code format} regexp.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     for (int var = 1; var &lt; 10; var++) {} // OK
 *     for (int VAR = 1; VAR &lt; 10; VAR++) {} // violation, name 'VAR' must match
 *                                           // pattern '^[a-z][a-zA-Z0-9]*$'
 *     for (int i = 1; i &lt; 10; i++) {} // OK
 *     for (int var_1 = 0; var_1 &lt; 10; var_1++) {} // violation, name 'var_1' must match
 *                                                    // pattern '^[a-z][a-zA-Z0-9]*$'
 *   }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with a lower
 * case letter, followed by letters, digits, and underscores is:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *   &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     for (int var = 1; var &lt; 10; var++) {} // OK
 *     for (int VAR = 1; VAR &lt; 10; VAR++) {} // violation, name 'VAR' must match
 *                                              // pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 *     for (int i = 1; i &lt; 10; i++) {} // OK
 *     for (int var_1 = 0; var_1 &lt; 10; var_1++) {} // OK
 *   }
 * }
 * </pre>
 * <p>
 * An example of one character variable name in
 * initialization expression(like "i") in FOR loop:
 * </p>
 * <pre>
 * for(int i = 1; i &lt; 10; i++) {}
 * for(int K = 1; K &lt; 10; K++) {}
 * List list = new ArrayList();
 * for (Object o : list) {}
 * for (Object O : list) {}
 * </pre>
 * <p>
 * An example of how to configure the check to allow one character variable name in
 * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
 * initialization expressions</a> in FOR loop, where regexp allows 2 or more chars:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *   &lt;property name="format" value="^[a-z][_a-zA-Z0-9]+$"/&gt;
 *   &lt;property name="allowOneCharVarInForLoop" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     int good = 1;
 *     int g = 0; // violation
 *     for (int v = 1; v &lt; 10; v++) { // OK
 *         int a = 1; // violation
 *     }
 *     for (int V = 1; V &lt; 10; V++) { // OK
 *         int I = 1; // violation
 *     }
 *     List list = new ArrayList();
 *     for (Object o : list) { // OK
 *         String a = ""; // violation
 *     }
 *     for (Object O : list) { // OK
 *         String A = ""; // violation
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check to that all variables have 3 or more chars in name:
 * </p>
 * <pre>
 * &lt;module name="LocalVariableName"&gt;
 *   &lt;property name="format" value="^[a-z][_a-zA-Z0-9]{2,}$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   void MyMethod() {
 *     int goodName = 0;
 *     int i = 1; // violation
 *     for (int var = 1; var &lt; 10; var++) { //OK
 *       int j = 1; // violation
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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
        super("^[a-z][a-zA-Z0-9]*$");
    }

    /**
     * Setter to allow one character variable name in
     * <a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html">
     * initialization expressions</a>
     * in FOR loop if one char variable name is prohibited by {@code format} regexp.
     *
     * @param allow Flag for allowing or not one character name in FOR loop.
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

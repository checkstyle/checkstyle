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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for over-complicated boolean return statements.
 * For example the following code
 * </p>
 * <pre>
 * if (valid())
 *   return false;
 * else
 *   return true;
 * </pre>
 * <p>
 * could be written as
 * </p>
 * <pre>
 * return !valid();
 * </pre>
 * <p>
 * The idea for this Check has been shamelessly stolen from the equivalent
 * <a href="https://pmd.github.io/">PMD</a> rule.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;SimplifyBooleanReturn&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public class Test {
 *
 *  private boolean cond;
 *  private Foo a;
 *  private Foo b;
 *
 *  public boolean check1() {
 *   if (cond) { // violation, can be simplified
 *     return true;
 *   }
 *   else {
 *     return false;
 *   }
 *  }
 *
 *  // Ok, simplified version of check1()
 *  public boolean check2() {
 *   return cond;
 *  }
 *
 *  // violations, can be simplified
 *  public boolean check3() {
 *   if (cond == true) { // can be simplified to "if (cond)"
 *     return false;
 *   }
 *   else {
 *     return true; // can be simplified to "return !cond"
 *   }
 *  }
 *
 *  // Ok, can be simplified but doesn't return a Boolean
 *  public Foo choose1() {
 *   if (cond) {
 *     return a;
 *   }
 *   else {
 *     return b;
 *   }
 *  }
 *
 *  // Ok, simplified version of choose1()
 *  public Foo choose2() {
 *   return cond ? a: b;
 *  }
 *
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
 * {@code simplify.boolReturn}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class SimplifyBooleanReturnCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "simplify.boolReturn";

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_IF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        // LITERAL_IF has the following four or five children:
        // '('
        // condition
        // ')'
        // thenStatement
        // [ LITERAL_ELSE (with the elseStatement as a child) ]

        // don't bother if this is not if then else
        final DetailAST elseLiteral =
            ast.findFirstToken(TokenTypes.LITERAL_ELSE);
        if (elseLiteral != null) {
            final DetailAST elseStatement = elseLiteral.getFirstChild();

            // skip '(' and ')'
            final DetailAST condition = ast.getFirstChild().getNextSibling();
            final DetailAST thenStatement = condition.getNextSibling().getNextSibling();

            if (canReturnOnlyBooleanLiteral(thenStatement)
                && canReturnOnlyBooleanLiteral(elseStatement)) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Returns if an AST is a return statement with a boolean literal
     * or a compound statement that contains only such a return statement.
     *
     * <p>Returns {@code true} iff ast represents
     * <pre>
     * return true/false;
     * </pre>
     * or
     * <pre>
     * {
     *   return true/false;
     * }
     * </pre>
     *
     * @param ast the syntax tree to check
     * @return if ast is a return statement with a boolean literal.
     */
    private static boolean canReturnOnlyBooleanLiteral(DetailAST ast) {
        boolean result = true;
        if (!isBooleanLiteralReturnStatement(ast)) {
            final DetailAST firstStatement = ast.getFirstChild();
            result = isBooleanLiteralReturnStatement(firstStatement);
        }
        return result;
    }

    /**
     * Returns if an AST is a return statement with a boolean literal.
     *
     * <p>Returns {@code true} iff ast represents
     * <pre>
     * return true/false;
     * </pre>
     *
     * @param ast the syntax tree to check
     * @return if ast is a return statement with a boolean literal.
     */
    private static boolean isBooleanLiteralReturnStatement(DetailAST ast) {
        boolean booleanReturnStatement = false;

        if (ast != null && ast.getType() == TokenTypes.LITERAL_RETURN) {
            final DetailAST expr = ast.getFirstChild();

            if (expr.getType() != TokenTypes.SEMI) {
                final DetailAST value = expr.getFirstChild();
                booleanReturnStatement = TokenUtil.isBooleanLiteralType(value.getType());
            }
        }
        return booleanReturnStatement;
    }
}

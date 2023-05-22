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
 * Checks that switch statement has a {@code default} clause.
 * </p>
 * <p>
 * Rationale: It's usually a good idea to introduce a
 * default case in every switch statement. Even if
 * the developer is sure that all currently possible
 * cases are covered, this should be expressed in the
 * default branch, e.g. by using an assertion. This way
 * the code is protected against later changes, e.g.
 * introduction of new types in an enumeration type.
 * </p>
 * <p>
 * This check does not validate any switch expressions. Rationale:
 * The compiler requires switch expressions to be exhaustive. This means
 * that all possible inputs must be covered.
 * </p>
 * <p>
 * This check does not validate switch statements that use pattern or null
 * labels. Rationale: Switch statements that use pattern or null labels are
 * checked by the compiler for exhaustiveness. This means that all possible
 * inputs must be covered.
 * </p>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se17/html/jls-15.html#jls-15.28">
 *     Java Language Specification</a> for more information about switch statements
 *     and expressions.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MissingSwitchDefault&quot;/&gt;
 * </pre>
 * <p>Example of violation:</p>
 * <pre>
 * switch (i) {    // violation
 *  case 1:
 *    break;
 *  case 2:
 *    break;
 * }
 * </pre>
 * <p>Example of correct code:</p>
 * <pre>
 * switch (i) {
 *  case 1:
 *    break;
 *  case 2:
 *    break;
 *  default: // OK
 *    break;
 * }
 * switch (o) {
 *     case String s: // type pattern
 *         System.out.println(s);
 *         break;
 *     case Integer i: // type pattern
 *         System.out.println("Integer");
 *         break;
 *     default:    // will not compile without default label, thanks to type pattern label usage
 *         break;
 * }
 * </pre>
 * <p>Example of correct code which does not require default labels:</p>
 * <pre>
 *    sealed interface S permits A, B, C {}
 *    final class A implements S {}
 *    final class B implements S {}
 *    record C(int i) implements S {}  // Implicitly final
 *
 *    /**
 *     * The completeness of a switch statement can be
 *     * determined by the contents of the permits clause
 *     * of interface S. No default label or default case
 *     * label is allowed by the compiler in this situation, so
 *     * this check does not enforce a default label in such
 *     * statements.
 *     *&#47;
 *    static void showSealedCompleteness(S s) {
 *        switch (s) {
 *            case A a: System.out.println("A"); break;
 *            case B b: System.out.println("B"); break;
 *            case C c: System.out.println("C"); break;
 *        }
 *    }
 *
 *    /**
 *     * A total type pattern matches all possible inputs,
 *     * including null. A default label or
 *     * default case is not allowed by the compiler in this
 *     * situation. Accordingly, check does not enforce a
 *     * default label in this case.
 *     *&#47;
 *    static void showTotality(String s) {
 *        switch (s) {
 *            case Object o: // total type pattern
 *                System.out.println("o!");
 *        }
 *    }
 *
 *    enum Color { RED, GREEN, BLUE }
 *
 *    static int showSwitchExpressionExhaustiveness(Color color) {
 *        switch (color) {
 *            case RED: System.out.println("RED"); break;
 *            case BLUE: System.out.println("BLUE"); break;
 *            case GREEN: System.out.println("GREEN"); break;
 *            // Check will require default label below, compiler
 *            // does not enforce a switch statement with constants
 *            // to be complete.
 *            default: System.out.println("Something else");
 *        }
 *
 *        // Check will not require default label in switch
 *        // expression below, because code will not compile
 *        // if all possible values are not handled. If the
 *        // 'Color' enum is extended, code will fail to compile.
 *        return switch (color) {
 *            case RED:
 *                yield 1;
 *            case GREEN:
 *                yield 2;
 *            case BLUE:
 *                yield 3;
 *        };
 *    }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code missing.switch.default}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public class MissingSwitchDefaultCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.switch.default";

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
        return new int[] {TokenTypes.LITERAL_SWITCH};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!containsDefaultLabel(ast)
                && !containsPatternCaseLabelElement(ast)
                && !containsDefaultCaseLabelElement(ast)
                && !isSwitchExpression(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if the case group or its sibling contain the 'default' switch.
     *
     * @param detailAst first case group to check.
     * @return true if 'default' switch found.
     */
    private static boolean containsDefaultLabel(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst,
                ast -> ast.findFirstToken(TokenTypes.LITERAL_DEFAULT) != null
        ).isPresent();
    }

    /**
     * Checks if a switch block contains a case label with a pattern variable definition.
     * In this situation, the compiler enforces the given switch block to cover
     * all possible inputs, and we do not need a default label.
     *
     * @param detailAst first case group to check.
     * @return true if switch block contains a pattern case label element
     */
    private static boolean containsPatternCaseLabelElement(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst, ast -> {
            return ast.getFirstChild() != null
                    && ast.getFirstChild().findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF) != null;
        }).isPresent();
    }

    /**
     * Checks if a switch block contains a default case label.
     *
     * @param detailAst first case group to check.
     * @return true if switch block contains default case label
     */
    private static boolean containsDefaultCaseLabelElement(DetailAST detailAst) {
        return TokenUtil.findFirstTokenByPredicate(detailAst, ast -> {
            return ast.getFirstChild() != null
                    && ast.getFirstChild().findFirstToken(TokenTypes.LITERAL_DEFAULT) != null;
        }).isPresent();
    }

    /**
     * Checks if this LITERAL_SWITCH token is part of a switch expression.
     *
     * @param ast the switch statement we are checking
     * @return true if part of a switch expression
     */
    private static boolean isSwitchExpression(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.EXPR
                || ast.getParent().getParent().getType() == TokenTypes.EXPR;
    }
}

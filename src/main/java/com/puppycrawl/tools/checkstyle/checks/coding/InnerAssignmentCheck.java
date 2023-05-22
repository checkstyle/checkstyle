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

import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks for assignments in subexpressions, such as in
 * {@code String s = Integer.toString(i = 2);}.
 * </p>
 * <p>
 * Rationale: Except for the loop idioms,
 * all assignments should occur in their own top-level statement to increase readability.
 * With inner assignments like the one given above, it is difficult to see all places
 * where a variable is set.
 * </p>
 * <p>
 * Note: Check allows usage of the popular assignments in loops:
 * </p>
 * <pre>
 * String line;
 * while ((line = bufferedReader.readLine()) != null) { // OK
 *   // process the line
 * }
 *
 * for (;(line = bufferedReader.readLine()) != null;) { // OK
 *   // process the line
 * }
 *
 * do {
 *   // process the line
 * }
 * while ((line = bufferedReader.readLine()) != null); // OK
 * </pre>
 * <p>
 * Assignment inside a condition is not a problem here, as the assignment is surrounded
 * by an extra pair of parentheses. The comparison is {@code != null} and there is no chance that
 * intention was to write {@code line == reader.readLine()}.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;InnerAssignment"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass {
 *
 *   void foo() {
 *     int a, b;
 *     a = b = 5; // violation, assignment to each variable should be in a separate statement
 *     a = b += 5; // violation
 *
 *     a = 5; // OK
 *     b = 5; // OK
 *     a = 5; b = 5; // OK
 *
 *     double myDouble;
 *     double[] doubleArray = new double[] {myDouble = 4.5, 15.5}; // violation
 *
 *     String nameOne;
 *     List&lt;String&gt; myList = new ArrayList&lt;String&gt;();
 *     myList.add(nameOne = "tom"); // violation
 *     for (int k = 0; k &lt; 10; k = k + 2) { // OK
 *       // some code
 *     }
 *
 *     boolean someVal;
 *     if (someVal = true) { // violation
 *       // some code
 *     }
 *
 *     while (someVal = false) {} // violation
 *
 *     InputStream is = new FileInputStream("textFile.txt");
 *     while ((b = is.read()) != -1) { // OK, this is a common idiom
 *       // some code
 *     }
 *
 *   }
 *
 *   boolean testMethod() {
 *     boolean val;
 *     return val = true; // violation
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
 * {@code assignment.inner.avoid}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class InnerAssignmentCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "assignment.inner.avoid";

    /**
     * Allowed AST types from an assignment AST node
     * towards the root.
     */
    private static final int[][] ALLOWED_ASSIGNMENT_CONTEXT = {
        {TokenTypes.EXPR, TokenTypes.SLIST},
        {TokenTypes.VARIABLE_DEF},
        {TokenTypes.EXPR, TokenTypes.ELIST, TokenTypes.FOR_INIT},
        {TokenTypes.EXPR, TokenTypes.ELIST, TokenTypes.FOR_ITERATOR},
        {TokenTypes.ANNOTATION_MEMBER_VALUE_PAIR}, {
            TokenTypes.RESOURCE,
            TokenTypes.RESOURCES,
            TokenTypes.RESOURCE_SPECIFICATION,
        },
        {TokenTypes.EXPR, TokenTypes.LAMBDA},
    };

    /**
     * Allowed AST types from an assignment AST node
     * towards the root.
     */
    private static final int[][] CONTROL_CONTEXT = {
        {TokenTypes.EXPR, TokenTypes.LITERAL_DO},
        {TokenTypes.EXPR, TokenTypes.LITERAL_FOR},
        {TokenTypes.EXPR, TokenTypes.LITERAL_WHILE},
        {TokenTypes.EXPR, TokenTypes.LITERAL_IF},
        {TokenTypes.EXPR, TokenTypes.LITERAL_ELSE},
    };

    /**
     * Allowed AST types from a comparison node (above an assignment)
     * towards the root.
     */
    private static final int[][] ALLOWED_ASSIGNMENT_IN_COMPARISON_CONTEXT = {
        {TokenTypes.EXPR, TokenTypes.LITERAL_WHILE},
        {TokenTypes.EXPR, TokenTypes.FOR_CONDITION},
        {TokenTypes.EXPR, TokenTypes.LITERAL_DO},
    };

    /**
     * The token types that identify comparison operators.
     */
    private static final BitSet COMPARISON_TYPES = TokenUtil.asBitSet(
        TokenTypes.EQUAL,
        TokenTypes.GE,
        TokenTypes.GT,
        TokenTypes.LE,
        TokenTypes.LT,
        TokenTypes.NOT_EQUAL
    );

    /**
     * The token types that are ignored while checking "loop-idiom".
     */
    private static final BitSet LOOP_IDIOM_IGNORED_PARENTS = TokenUtil.asBitSet(
        TokenTypes.LAND,
        TokenTypes.LOR,
        TokenTypes.LNOT,
        TokenTypes.BOR,
        TokenTypes.BAND
    );

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
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      // "-="
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.BAND_ASSIGN,       // "&="
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!isInContext(ast, ALLOWED_ASSIGNMENT_CONTEXT, CommonUtil.EMPTY_BIT_SET)
                && !isInNoBraceControlStatement(ast)
                && !isInLoopIdiom(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Determines if ast is in the body of a flow control statement without
     * braces. An example of such a statement would be
     * <pre>
     * if (y &lt; 0)
     *     x = y;
     * </pre>
     * <p>
     * This leads to the following AST structure:
     * </p>
     * <pre>
     * LITERAL_IF
     *     LPAREN
     *     EXPR // test
     *     RPAREN
     *     EXPR // body
     *     SEMI
     * </pre>
     * <p>
     * We need to ensure that ast is in the body and not in the test.
     * </p>
     *
     * @param ast an assignment operator AST
     * @return whether ast is in the body of a flow control statement
     */
    private static boolean isInNoBraceControlStatement(DetailAST ast) {
        boolean result = false;
        if (isInContext(ast, CONTROL_CONTEXT, CommonUtil.EMPTY_BIT_SET)) {
            final DetailAST expr = ast.getParent();
            final DetailAST exprNext = expr.getNextSibling();
            result = exprNext.getType() == TokenTypes.SEMI;
        }
        return result;
    }

    /**
     * Tests whether the given AST is used in the "assignment in loop" idiom.
     * <pre>
     * String line;
     * while ((line = bufferedReader.readLine()) != null) {
     *   // process the line
     * }
     * for (;(line = bufferedReader.readLine()) != null;) {
     *   // process the line
     * }
     * do {
     *   // process the line
     * }
     * while ((line = bufferedReader.readLine()) != null);
     * </pre>
     * Assignment inside a condition is not a problem here, as the assignment is surrounded by an
     * extra pair of parentheses. The comparison is {@code != null} and there is no chance that
     * intention was to write {@code line == reader.readLine()}.
     *
     * @param ast assignment AST
     * @return whether the context of the assignment AST indicates the idiom
     */
    private static boolean isInLoopIdiom(DetailAST ast) {
        return isComparison(ast.getParent())
                    && isInContext(ast.getParent(),
                            ALLOWED_ASSIGNMENT_IN_COMPARISON_CONTEXT,
                            LOOP_IDIOM_IGNORED_PARENTS);
    }

    /**
     * Checks if an AST is a comparison operator.
     *
     * @param ast the AST to check
     * @return true iff ast is a comparison operator.
     */
    private static boolean isComparison(DetailAST ast) {
        final int astType = ast.getType();
        return COMPARISON_TYPES.get(astType);
    }

    /**
     * Tests whether the provided AST is in
     * one of the given contexts.
     *
     * @param ast the AST from which to start walking towards root
     * @param contextSet the contexts to test against.
     * @param skipTokens parent token types to ignore
     *
     * @return whether the parents nodes of ast match one of the allowed type paths.
     */
    private static boolean isInContext(DetailAST ast, int[][] contextSet, BitSet skipTokens) {
        boolean found = false;
        for (int[] element : contextSet) {
            DetailAST current = ast;
            for (int anElement : element) {
                current = getParent(current, skipTokens);
                if (current.getType() == anElement) {
                    found = true;
                }
                else {
                    found = false;
                    break;
                }
            }

            if (found) {
                break;
            }
        }
        return found;
    }

    /**
     * Get ast parent, ignoring token types from {@code skipTokens}.
     *
     * @param ast token to get parent
     * @param skipTokens token types to skip
     * @return first not ignored parent of ast
     */
    private static DetailAST getParent(DetailAST ast, BitSet skipTokens) {
        DetailAST result = ast.getParent();
        while (skipTokens.get(result.getType())) {
            result = result.getParent();
        }
        return result;
    }

}

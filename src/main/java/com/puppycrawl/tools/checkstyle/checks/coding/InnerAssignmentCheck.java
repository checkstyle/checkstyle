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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Arrays;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for assignments in subexpressions, such as in
 * <code>String s = Integer.toString(i = 2);</code>.
 * </p>
 * <p>
 * Rationale: With the exception of <code>for</code> iterators, all assignments
 * should occur in their own toplevel statement to increase readability.
 * With inner assignments like the above it is difficult to see all places
 * where a variable is set.
 * </p>
 *
 * @author lkuehne
 */
public class InnerAssignmentCheck
        extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "assignment.inner.avoid";

    /**
     * list of allowed AST types from an assignement AST node
     * towards the root.
     */
    private static final int[][] ALLOWED_ASSIGMENT_CONTEXT = {
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
     * list of allowed AST types from an assignement AST node
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
     * list of allowed AST types from a comparison node (above an assignement)
     * towards the root.
     */
    private static final int[][] ALLOWED_ASSIGMENT_IN_COMPARISON_CONTEXT = {
        {TokenTypes.EXPR, TokenTypes.LITERAL_WHILE, },
    };

    /**
     * The token types that identify comparison operators.
     */
    private static final int[] COMPARISON_TYPES = {
        TokenTypes.EQUAL,
        TokenTypes.GE,
        TokenTypes.GT,
        TokenTypes.LE,
        TokenTypes.LT,
        TokenTypes.NOT_EQUAL,
    };

    static {
        Arrays.sort(COMPARISON_TYPES);
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      //"-="
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
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ASSIGN,            // '='
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS_ASSIGN,      //"-="
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
        if (isInContext(ast, ALLOWED_ASSIGMENT_CONTEXT)) {
            return;
        }

        if (isInNoBraceControlStatement(ast)) {
            return;
        }

        if (isInWhileIdiom(ast)) {
            return;
        }

        log(ast.getLineNo(), ast.getColumnNo(), MSG_KEY);
    }

    /**
     * Determines if ast is in the body of a flow control statement without
     * braces. An example of such a statement would be
     * <p>
     * <pre>
     * if (y < 0)
     *     x = y;
     * </pre>
     * </p>
     * <p>
     * This leads to the following AST structure:
     * </p>
     * <p>
     * <pre>
     * LITERAL_IF
     *     LPAREN
     *     EXPR // test
     *     RPAREN
     *     EXPR // body
     *     SEMI
     * </pre>
     * </p>
     * <p>
     * We need to ensure that ast is in the body and not in the test.
     * </p>
     *
     * @param ast an assignment operator AST
     * @return whether ast is in the body of a flow control statement
     */
    private static boolean isInNoBraceControlStatement(DetailAST ast) {
        if (!isInContext(ast, CONTROL_CONTEXT)) {
            return false;
        }
        final DetailAST expr = ast.getParent();
        final AST exprNext = expr.getNextSibling();
        return exprNext.getType() == TokenTypes.SEMI;
    }

    /**
     * Tests whether the given AST is used in the "assignment in while test"
     * idiom.
     * <p>
     * <pre>
     * while ((b = is.read()) != -1) {
     *   // work with b
     * }
     * </pre>
     * </p>
     *
     * @param ast assignment AST
     * @return whether the context of the assignemt AST indicates the idiom
     */
    private static boolean isInWhileIdiom(DetailAST ast) {
        if (!isComparison(ast.getParent())) {
            return false;
        }
        return isInContext(
                ast.getParent(), ALLOWED_ASSIGMENT_IN_COMPARISON_CONTEXT);
    }

    /**
     * Checks if an AST is a comparison operator.
     * @param ast the AST to check
     * @return true iff ast is a comparison operator.
     */
    private static boolean isComparison(DetailAST ast) {
        final int astType = ast.getType();
        return Arrays.binarySearch(COMPARISON_TYPES, astType) >= 0;
    }

    /**
     * Tests whether the provided AST is in
     * one of the given contexts.
     *
     * @param ast the AST from which to start walking towards root
     * @param contextSet the contexts to test against.
     *
     * @return whether the parents nodes of ast match
     * one of the allowed type paths
     */
    private static boolean isInContext(DetailAST ast, int[]... contextSet) {
        boolean found = false;
        for (int[] element : contextSet) {
            DetailAST current = ast;
            for (int anElement : element) {
                current = current.getParent();
                final int expectedType = anElement;
                if (current.getType() != expectedType) {
                    found = false;
                    break;
                }
                else {
                    found = true;
                }
            }

            if (found) {
                break;
            }
        }
        return found;
    }
}

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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

/**
 * <p>
 * Ensures that local variables that never get their values changed,
 * must be declared final.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="FinalLocalVariable"&gt;
 *     &lt;property name="token" value="VARIABLE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * By default, this Check skip final validation on
 *  <a href = "http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.2">
 * Enhanced For-Loop</a>
 * </p>
 * <p>
 * Option 'validateEnhancedForLoopVariable' could be used to make Check to validate even variable
 *  from Enhanced For Loop.
 * </p>
 * <p>
 * An example of how to configure the check so that it also validates enhanced For Loop Variable is:
 * </p>
 * <pre>
 * &lt;module name="FinalLocalVariable"&gt;
 *     &lt;property name="token" value="VARIABLE_DEF"/&gt;
 *     &lt;property name="validateEnhancedForLoopVariable" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <p>
 * {@code
 * for (int number : myNumbers) { // violation
 *    System.out.println(number);
 * }
 * }
 * </p>
 * @author k_gibbs, r_auckenthaler
 */
public class FinalLocalVariableCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "final.variable";

    /**
     * Assign operators types.
     */
    private static final int[] ASSIGN_OPERATOR_TYPES = {
        TokenTypes.POST_INC,
        TokenTypes.POST_DEC,
        TokenTypes.ASSIGN,
        TokenTypes.PLUS_ASSIGN,
        TokenTypes.MINUS_ASSIGN,
        TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN,
        TokenTypes.MOD_ASSIGN,
        TokenTypes.SR_ASSIGN,
        TokenTypes.BSR_ASSIGN,
        TokenTypes.SL_ASSIGN,
        TokenTypes.BAND_ASSIGN,
        TokenTypes.BXOR_ASSIGN,
        TokenTypes.BOR_ASSIGN,
        TokenTypes.INC,
        TokenTypes.DEC,
    };

    /** Scope Stack. */
    private final Deque<Map<String, DetailAST>> scopeStack = new ArrayDeque<>();

    /** Controls whether to check enhanced for-loop variable. */
    private boolean validateEnhancedForLoopVariable;

    static {
        // Array sorting for binary search
        Arrays.sort(ASSIGN_OPERATOR_TYPES);
    }

    /**
     * Whether to check enhanced for-loop variable or not.
     * @param validateEnhancedForLoopVariable whether to check for-loop variable
     */
    public final void setValidateEnhancedForLoopVariable(boolean validateEnhancedForLoopVariable) {
        this.validateEnhancedForLoopVariable = validateEnhancedForLoopVariable;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_FOR,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_FOR,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
            TokenTypes.PARAMETER_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_FOR,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.SLIST:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.INSTANCE_INIT:
                scopeStack.push(new HashMap<String, DetailAST>());
                break;

            case TokenTypes.PARAMETER_DEF:
                if (!isInLambda(ast)
                        && !ast.branchContains(TokenTypes.FINAL)
                        && !isInAbstractOrNativeMethod(ast)
                        && !ScopeUtils.isInInterfaceBlock(ast)) {
                    insertVariable(ast);
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK
                        && !isVariableInForInit(ast)
                        && shouldCheckEnhancedForLoopVariable(ast)
                        && !ast.branchContains(TokenTypes.FINAL)) {
                    insertVariable(ast);
                }
                break;

            case TokenTypes.IDENT:
                final int parentType = ast.getParent().getType();
                if (isAssignOperator(parentType)
                        && ast.getParent().getFirstChild() == ast) {
                    removeVariable(ast);
                }
                break;

            default:
                throw new IllegalStateException("Incorrect token type");
        }
    }

    /**
     * Is Arithmetic operator.
     * @param parentType token AST
     * @return true is token type is in arithmetic operator
     */
    private static boolean isAssignOperator(int parentType) {
        return Arrays.binarySearch(ASSIGN_OPERATOR_TYPES, parentType) >= 0;
    }

    /**
     * Determines whether enhanced for-loop variable should be checked or not.
     * @param ast The ast to compare.
     * @return true if enhanced for-loop variable should be checked.
     */
    private boolean shouldCheckEnhancedForLoopVariable(DetailAST ast) {
        return validateEnhancedForLoopVariable
                || ast.getParent().getType() != TokenTypes.FOR_EACH_CLAUSE;
    }

    /**
     * Checks if current variable is defined in
     *  {@link TokenTypes#FOR_INIT for-loop init}, e.g.:
     * <p>
     * {@code
     * for (int i = 0, j = 0; i < j; i++) { . . . }
     * }
     * </p>
     * {@code i, j} are defined in {@link TokenTypes#FOR_INIT for-loop init}
     * @param variableDef variable definition node.
     * @return true if variable is defined in {@link TokenTypes#FOR_INIT for-loop init}
     */
    private static boolean isVariableInForInit(DetailAST variableDef) {
        return variableDef.getParent().getType() == TokenTypes.FOR_INIT;
    }

    /**
     * Determines whether an AST is a descendant of an abstract or native method.
     * @param ast the AST to check.
     * @return true if ast is a descendant of an abstract or native method.
     */
    private static boolean isInAbstractOrNativeMethod(DetailAST ast) {
        boolean abstractOrNative = false;
        DetailAST parent = ast.getParent();
        while (parent != null && !abstractOrNative) {
            if (parent.getType() == TokenTypes.METHOD_DEF) {
                final DetailAST modifiers =
                    parent.findFirstToken(TokenTypes.MODIFIERS);
                abstractOrNative = modifiers.branchContains(TokenTypes.ABSTRACT)
                        || modifiers.branchContains(TokenTypes.LITERAL_NATIVE);
            }
            parent = parent.getParent();
        }
        return abstractOrNative;
    }

    /**
     * Check if current param is lambda's param.
     * @param paramDef {@link TokenTypes#PARAMETER_DEF parameter def}.
     * @return true if current param is lambda's param.
     */
    private static boolean isInLambda(DetailAST paramDef) {
        return paramDef.getParent().getParent().getType() == TokenTypes.LAMBDA;
    }

    /**
     * Find the Class or Constructor or Method in which it is defined.
     * @param ast Variable for which we want to find the scope in which it is defined
     * @return ast The Class or Constructor or Method in which it is defined.
     */
    private static DetailAST findClassOrConstructorOrMethodInWhichItIsDefined(DetailAST ast) {
        DetailAST astTraverse = ast;
        while (!(astTraverse.getType() == TokenTypes.METHOD_DEF
                || astTraverse.getType() == TokenTypes.CLASS_DEF
                || astTraverse.getType() == TokenTypes.ENUM_DEF
                || astTraverse.getType() == TokenTypes.CTOR_DEF)) {
            astTraverse = astTraverse.getParent();
        }
        return astTraverse;
    }

    /**
     * Check if both the Variable are same.
     * @param ast1 Variable to compare
     * @param ast2 Variable to compare
     * @return true if both the variable are same, otherwise false
     */
    private static boolean isSameVariables(DetailAST ast1, DetailAST ast2) {
        final DetailAST classOrMethodOfAst1 =
            findClassOrConstructorOrMethodInWhichItIsDefined(ast1);
        final DetailAST classOrMethodOfAst2 =
            findClassOrConstructorOrMethodInWhichItIsDefined(ast2);

        final String identifierOfAst1 =
            classOrMethodOfAst1.findFirstToken(TokenTypes.IDENT).getText();
        final String identifierOfAst2 =
            classOrMethodOfAst2.findFirstToken(TokenTypes.IDENT).getText();

        return identifierOfAst1.equals(identifierOfAst2);
    }

    /**
     * Inserts a variable at the topmost scope stack.
     * @param ast the variable to insert
     */
    private void insertVariable(DetailAST ast) {
        final Map<String, DetailAST> state = scopeStack.peek();
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        state.put(astNode.getText(), astNode);
    }

    /**
     * Removes the variable from the Stacks.
     * @param ast Variable to remove
     */
    private void removeVariable(DetailAST ast) {
        final Iterator<Map<String, DetailAST>> iterator = scopeStack.descendingIterator();
        while (iterator.hasNext()) {
            final Map<String, DetailAST> state = iterator.next();
            final DetailAST storedVariable = state.get(ast.getText());
            if (storedVariable != null && isSameVariables(storedVariable, ast)) {
                state.remove(ast.getText());
                break;
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        super.leaveToken(ast);

        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.SLIST:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.METHOD_DEF:
                final Map<String, DetailAST> state = scopeStack.pop();
                for (DetailAST node : state.values()) {
                    log(node.getLineNo(), node.getColumnNo(), MSG_KEY, node
                        .getText());
                }
                break;

            default:
        }
    }
}

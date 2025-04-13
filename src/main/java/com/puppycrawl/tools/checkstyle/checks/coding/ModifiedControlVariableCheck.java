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

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that for loop control variables are not modified
 * inside the for block. An example is:
 * </div>
 * <pre>
 * for (int i = 0; i &lt; 1; i++) {
 *   i++; // violation
 * }
 * </pre>
 *
 * <p>
 * Rationale: If the control variable is modified inside the loop
 * body, the program flow becomes more difficult to follow.
 * See <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14">
 * FOR statement</a> specification for more details.
 * </p>
 *
 * <p>
 * Such loop would be suppressed:
 * </p>
 * <pre>
 * for (int i = 0; i &lt; 10;) {
 *   i++;
 * }
 * </pre>
 *
 * <p>
 * NOTE:The check works with only primitive type variables.
 * The check will not work for arrays used as control variable.An example is
 * </p>
 * <pre>
 * for (int a[]={0};a[0] &lt; 10;a[0]++) {
 *  a[0]++;   // it will skip this violation
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code skipEnhancedForLoopVariable} - Control whether to check
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
 * enhanced for-loop</a> variable.
 * Type is {@code boolean}.
 * Default value is {@code false}.
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
 * {@code modified.control.variable}
 * </li>
 * </ul>
 *
 * @since 3.5
 */
@FileStatefulCheck
public final class ModifiedControlVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "modified.control.variable";

    /**
     * Message thrown with IllegalStateException.
     */
    private static final String ILLEGAL_TYPE_OF_TOKEN = "Illegal type of token: ";

    /** Operations which can change control variable in update part of the loop. */
    private static final BitSet MUTATION_OPERATIONS = TokenUtil.asBitSet(
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.DEC,
            TokenTypes.INC,
            TokenTypes.ASSIGN);

    /** Stack of block parameters. */
    private final Deque<Deque<String>> variableStack = new ArrayDeque<>();

    /**
     * Control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     */
    private boolean skipEnhancedForLoopVariable;

    /**
     * Setter to control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     *
     * @param skipEnhancedForLoopVariable whether to skip enhanced for-loop variable
     * @since 6.8
     */
    public void setSkipEnhancedForLoopVariable(boolean skipEnhancedForLoopVariable) {
        this.skipEnhancedForLoopVariable = skipEnhancedForLoopVariable;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.FOR_ITERATOR,
            TokenTypes.FOR_EACH_CLAUSE,
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
            TokenTypes.POST_INC,
            TokenTypes.DEC,
            TokenTypes.POST_DEC,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // clear data
        variableStack.clear();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
                enterBlock();
                break;
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.FOR_ITERATOR:
            case TokenTypes.FOR_EACH_CLAUSE:
                // we need that Tokens only at leaveToken()
                break;
            case TokenTypes.ASSIGN:
            case TokenTypes.PLUS_ASSIGN:
            case TokenTypes.MINUS_ASSIGN:
            case TokenTypes.STAR_ASSIGN:
            case TokenTypes.DIV_ASSIGN:
            case TokenTypes.MOD_ASSIGN:
            case TokenTypes.SR_ASSIGN:
            case TokenTypes.BSR_ASSIGN:
            case TokenTypes.SL_ASSIGN:
            case TokenTypes.BAND_ASSIGN:
            case TokenTypes.BXOR_ASSIGN:
            case TokenTypes.BOR_ASSIGN:
            case TokenTypes.INC:
            case TokenTypes.POST_INC:
            case TokenTypes.DEC:
            case TokenTypes.POST_DEC:
                checkIdent(ast);
                break;
            default:
                throw new IllegalStateException(ILLEGAL_TYPE_OF_TOKEN + ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.FOR_ITERATOR:
                leaveForIter(ast.getParent());
                break;
            case TokenTypes.FOR_EACH_CLAUSE:
                if (!skipEnhancedForLoopVariable) {
                    final DetailAST paramDef = ast.findFirstToken(TokenTypes.VARIABLE_DEF);
                    leaveForEach(paramDef);
                }
                break;
            case TokenTypes.LITERAL_FOR:
                leaveForDef(ast);
                break;
            case TokenTypes.OBJBLOCK:
                exitBlock();
                break;
            case TokenTypes.ASSIGN:
            case TokenTypes.PLUS_ASSIGN:
            case TokenTypes.MINUS_ASSIGN:
            case TokenTypes.STAR_ASSIGN:
            case TokenTypes.DIV_ASSIGN:
            case TokenTypes.MOD_ASSIGN:
            case TokenTypes.SR_ASSIGN:
            case TokenTypes.BSR_ASSIGN:
            case TokenTypes.SL_ASSIGN:
            case TokenTypes.BAND_ASSIGN:
            case TokenTypes.BXOR_ASSIGN:
            case TokenTypes.BOR_ASSIGN:
            case TokenTypes.INC:
            case TokenTypes.POST_INC:
            case TokenTypes.DEC:
            case TokenTypes.POST_DEC:
                // we need that Tokens only at visitToken()
                break;
            default:
                throw new IllegalStateException(ILLEGAL_TYPE_OF_TOKEN + ast);
        }
    }

    /**
     * Enters an inner class, which requires a new variable set.
     */
    private void enterBlock() {
        variableStack.push(new ArrayDeque<>());
    }

    /**
     * Leave an inner class, so restore variable set.
     */
    private void exitBlock() {
        variableStack.pop();
    }

    /**
     * Get current variable stack.
     *
     * @return current variable stack
     */
    private Deque<String> getCurrentVariables() {
        return variableStack.peek();
    }

    /**
     * Check if ident is parameter.
     *
     * @param ast ident to check.
     */
    private void checkIdent(DetailAST ast) {
        final Deque<String> currentVariables = getCurrentVariables();
        final DetailAST identAST = ast.getFirstChild();

        if (identAST != null && identAST.getType() == TokenTypes.IDENT
            && currentVariables.contains(identAST.getText())) {
            log(ast, MSG_KEY, identAST.getText());
        }
    }

    /**
     * Push current variables to the stack.
     *
     * @param ast a for definition.
     */
    private void leaveForIter(DetailAST ast) {
        final Set<String> variablesToPutInScope = getVariablesManagedByForLoop(ast);
        for (String variableName : variablesToPutInScope) {
            getCurrentVariables().push(variableName);
        }
    }

    /**
     * Determines which variable are specific to for loop and should not be
     * change by inner loop body.
     *
     * @param ast For Loop
     * @return Set of Variable Name which are managed by for
     */
    private static Set<String> getVariablesManagedByForLoop(DetailAST ast) {
        final Set<String> initializedVariables = getForInitVariables(ast);
        final Set<String> iteratingVariables = getForIteratorVariables(ast);
        return initializedVariables.stream().filter(iteratingVariables::contains)
            .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Push current variables to the stack.
     *
     * @param paramDef a for-each clause variable
     */
    private void leaveForEach(DetailAST paramDef) {
        // When using record decomposition in enhanced for loops,
        // we are not able to declare a 'control variable'.
        final boolean isRecordPattern = paramDef == null;

        if (!isRecordPattern) {
            final DetailAST paramName = paramDef.findFirstToken(TokenTypes.IDENT);
            getCurrentVariables().push(paramName.getText());
        }
    }

    /**
     * Pops the variables from the stack.
     *
     * @param ast a for definition.
     */
    private void leaveForDef(DetailAST ast) {
        final DetailAST forInitAST = ast.findFirstToken(TokenTypes.FOR_INIT);
        if (forInitAST == null) {
            final Deque<String> currentVariables = getCurrentVariables();
            if (!skipEnhancedForLoopVariable && !currentVariables.isEmpty()) {
                // this is for-each loop, just pop variables
                currentVariables.pop();
            }
        }
        else {
            final Set<String> variablesManagedByForLoop = getVariablesManagedByForLoop(ast);
            popCurrentVariables(variablesManagedByForLoop.size());
        }
    }

    /**
     * Pops given number of variables from currentVariables.
     *
     * @param count Count of variables to be popped from currentVariables
     */
    private void popCurrentVariables(int count) {
        for (int i = 0; i < count; i++) {
            getCurrentVariables().pop();
        }
    }

    /**
     * Get all variables initialized In init part of for loop.
     *
     * @param ast for loop token
     * @return set of variables initialized in for loop
     */
    private static Set<String> getForInitVariables(DetailAST ast) {
        final Set<String> initializedVariables = new HashSet<>();
        final DetailAST forInitAST = ast.findFirstToken(TokenTypes.FOR_INIT);

        for (DetailAST parameterDefAST = forInitAST.findFirstToken(TokenTypes.VARIABLE_DEF);
             parameterDefAST != null;
             parameterDefAST = parameterDefAST.getNextSibling()) {
            if (parameterDefAST.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST param =
                        parameterDefAST.findFirstToken(TokenTypes.IDENT);

                initializedVariables.add(param.getText());
            }
        }
        return initializedVariables;
    }

    /**
     * Get all variables which for loop iterating part change in every loop.
     *
     * @param ast for loop literal(TokenTypes.LITERAL_FOR)
     * @return names of variables change in iterating part of for
     */
    private static Set<String> getForIteratorVariables(DetailAST ast) {
        final Set<String> iteratorVariables = new HashSet<>();
        final DetailAST forIteratorAST = ast.findFirstToken(TokenTypes.FOR_ITERATOR);
        final DetailAST forUpdateListAST = forIteratorAST.findFirstToken(TokenTypes.ELIST);

        findChildrenOfExpressionType(forUpdateListAST).stream()
            .filter(iteratingExpressionAST -> {
                return MUTATION_OPERATIONS.get(iteratingExpressionAST.getType());
            }).forEach(iteratingExpressionAST -> {
                final DetailAST oneVariableOperatorChild = iteratingExpressionAST.getFirstChild();
                iteratorVariables.add(oneVariableOperatorChild.getText());
            });

        return iteratorVariables;
    }

    /**
     * Find all child of given AST of type TokenType.EXPR.
     *
     * @param ast parent of expressions to find
     * @return all child of given ast
     */
    private static List<DetailAST> findChildrenOfExpressionType(DetailAST ast) {
        final List<DetailAST> foundExpressions = new LinkedList<>();
        if (ast != null) {
            for (DetailAST iteratingExpressionAST = ast.findFirstToken(TokenTypes.EXPR);
                 iteratingExpressionAST != null;
                 iteratingExpressionAST = iteratingExpressionAST.getNextSibling()) {
                if (iteratingExpressionAST.getType() == TokenTypes.EXPR) {
                    foundExpressions.add(iteratingExpressionAST.getFirstChild());
                }
            }
        }
        return foundExpressions;
    }

}

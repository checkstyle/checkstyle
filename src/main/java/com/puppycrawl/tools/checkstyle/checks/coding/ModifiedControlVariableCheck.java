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

import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * <p>
 * Check for ensuring that for loop control variables are not modified
 * inside the for block. An example is:
 * <p>
 * <pre>
 * <code>
 * for (int i = 0; i &lt; 1; i++) {
 *     i++;//violation
 * }
 * </code>
 * </pre>
 * </p>
 * Rationale: If the control variable is modified inside the loop
 * body, the program flow becomes more difficult to follow.<br/>
 * See <a href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14">
 * FOR statement</a> specification for more details.
 * </p>
 * Examples:
 * <p>
 * <pre>
 * &lt;module name=&quot;ModifiedControlVariable&quot;&gt;
 * &lt;/module&gt;
 * </pre>
 * </p>
 * Such loop would be supressed:
 * <p>
 * <pre>
 * <code>
 * for(int i=0;i < 10;) {
 *     i++;
 * }
 * </code>
 * </pre>
 * </p>
 * <p>
 * By default, This Check validates
 *  <a href = "http://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.2">
 * Enhanced For-Loop</a>.
 * </p>
 * <p>
 * Option 'skipEnhancedForLoopVariable' could be used to skip check of variable
 *  from Enhanced For Loop.
 * </p>
 * <p>
 * An example of how to configure the check so that it skips enhanced For Loop Variable is:
 * </p>
 * <pre>
 * &lt;module name="ModifiedControlVariable"&gt;
 *     &lt;property name="skipEnhancedForLoopVariable" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <p>
 * <pre>
 * <code>
 * for (String line: lines) {
 *     line = line.trim();   // it will skip this violation
 * }
 * </code>
 * </pre>
 * </p>
 *
 * @author Daniel Grenner
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public final class ModifiedControlVariableCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "modified.control.variable";

    /**
     * Message thrown with IllegalStateException
     */
    private static final String ILLEGAL_TYPE_OF_TOKEN = "Illegal type of token: ";

    /** Operations which can change control variable in update part of the loop*/
    private static final Set<Integer> MUTATION_OPERATIONS =
            Sets.newHashSet(TokenTypes.POST_INC, TokenTypes.POST_DEC, TokenTypes.DEC,
                    TokenTypes.INC, TokenTypes.ASSIGN);

    /** Stack of block parameters. */
    private final Deque<Deque<String>> variableStack = new ArrayDeque<>();

    /** Controls whether to skip enhanced for-loop variable. */
    private boolean skipEnhancedForLoopVariable;

    /**
     * Whether to skip enhanced for-loop variable or not.
     * @param skipEnhancedForLoopVariable whether to skip enhanced for-loop variable
     */
    public void setSkipEnhancedForLoopVariable(boolean skipEnhancedForLoopVariable) {
        this.skipEnhancedForLoopVariable = skipEnhancedForLoopVariable;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
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
    public void beginTree(DetailAST rootAST) {
        // clear data
        variableStack.clear();
        variableStack.push(new ArrayDeque<String>());
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
                //we need that Tokens only at leaveToken()
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
                throw new IllegalStateException(ILLEGAL_TYPE_OF_TOKEN + ast.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.FOR_ITERATOR:
                leaveForIter(ast.getParent());
                break;
            case TokenTypes.FOR_EACH_CLAUSE:
                final DetailAST paramDef =
                    ast.findFirstToken(TokenTypes.VARIABLE_DEF);
                if (shouldCheckEnhancedForLoopVariable(paramDef)) {
                    leaveForEach(paramDef);
                }
                break;
            case TokenTypes.LITERAL_FOR:
                if (!getCurrentVariables().isEmpty()) {
                    leaveForDef(ast);
                }
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
                //we need that Tokens only at visitToken()
                break;
            default:
                throw new IllegalStateException(ILLEGAL_TYPE_OF_TOKEN + ast.toString());
        }
    }

    /**
     * Enters an inner class, which requires a new variable set.
     */
    private void enterBlock() {
        variableStack.push(new ArrayDeque<String>());
    }
    /**
     * Leave an inner class, so restore variable set.
     */
    private void exitBlock() {
        variableStack.pop();
    }

    /**
     * Get current variable stack
     * @return current variable stack
     */
    private Deque<String> getCurrentVariables() {
        return variableStack.peek();
    }

    /**
     * Check if ident is parameter.
     * @param ast ident to check.
     */
    private void checkIdent(DetailAST ast) {
        if (!getCurrentVariables().isEmpty()) {
            final DetailAST identAST = ast.getFirstChild();

            if (identAST != null
                && identAST.getType() == TokenTypes.IDENT
                && getCurrentVariables().contains(identAST.getText())) {
                log(ast.getLineNo(), ast.getColumnNo(),
                    MSG_KEY, identAST.getText());
            }
        }
    }

    /**
     * Push current variables to the stack.
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
     * @param ast For Loop
     * @return Set of Variable Name which are managed by for
     */
    private Set<String> getVariablesManagedByForLoop(DetailAST ast) {
        final Set<String> initializedVariables = getForInitVariables(ast);
        final Set<String> iteratingVariables = getForIteratorVariables(ast);

        return Sets.intersection(initializedVariables, iteratingVariables);
    }

    /**
     * Determines whether enhanced for-loop variable should be checked or not.
     * @param ast The ast to compare.
     * @return true if enhanced for-loop variable should be checked.
     */
    private boolean shouldCheckEnhancedForLoopVariable(DetailAST ast) {
        return !skipEnhancedForLoopVariable
                || ast.getParent().getType() != TokenTypes.FOR_EACH_CLAUSE;
    }

    /**
     * Push current variables to the stack.
     * @param paramDef a for-each clause variable
     */
    private void leaveForEach(DetailAST paramDef) {
        final DetailAST paramName = paramDef.findFirstToken(TokenTypes.IDENT);
        getCurrentVariables().push(paramName.getText());
    }

    /**
     * Pops the variables from the stack.
     * @param ast a for definition.
     */
    private void leaveForDef(DetailAST ast) {
        final DetailAST forInitAST = ast.findFirstToken(TokenTypes.FOR_INIT);
        if (forInitAST != null) {
            final Set<String> variablesManagedByForLoop = getVariablesManagedByForLoop(ast);
            popCurrentVariables(variablesManagedByForLoop.size());
        }
        else {
            // this is for-each loop, just pop veriables
            getCurrentVariables().pop();
        }
    }

    /**
     * Pops given number of variables from currentVariables
     * @param count Count of variables to be popped from currentVariables
     */
    private void popCurrentVariables(int count) {
        for (int i = 0; i < count; i++) {
            getCurrentVariables().pop();
        }
    }

    /**
     * Get all variables initialized In init part of for loop.
     * @param ast for loop iteral
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
     * @param ast for loop literal(TokenTypes.LITERAL_FOR)
     * @return names of variables change in iterating part of for
     */
    private static Set<String> getForIteratorVariables(DetailAST ast) {
        final Set<String> iteratorVariables = new HashSet<>();
        final DetailAST forIteratorAST = ast.findFirstToken(TokenTypes.FOR_ITERATOR);
        final DetailAST forUpdateListAST = forIteratorAST.findFirstToken(TokenTypes.ELIST);

        for (DetailAST iteratingExpressionAST : findChildrenOfExpressionType(forUpdateListAST)) {

            if (MUTATION_OPERATIONS.contains(iteratingExpressionAST.getType())) {
                final DetailAST oneVariableOperatorChild = iteratingExpressionAST.getFirstChild();
                if (oneVariableOperatorChild.getType() == TokenTypes.IDENT) {
                    iteratorVariables.add(oneVariableOperatorChild.getText());
                }
            }
        }

        return iteratorVariables;
    }

    /**
     * Find all child of given AST of type TokenType.EXPR
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

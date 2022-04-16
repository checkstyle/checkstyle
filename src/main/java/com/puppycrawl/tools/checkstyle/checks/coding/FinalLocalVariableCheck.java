////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
import java.util.BitSet;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that local variables that never have their values changed are declared final.
 * The check can be configured to also check that unchanged parameters are declared final.
 * </p>
 * <p>
 * When configured to check parameters, the check ignores parameters of interface
 * methods and abstract methods.
 * </p>
 * <ul>
 * <li>
 * Property {@code validateEnhancedForLoopVariable} - Control whether to check
 * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
 * enhanced for-loop</a> variable.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#VARIABLE_DEF">
 * VARIABLE_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalLocalVariable&quot;/&gt;
 * </pre>
 * <p>
 * To configure the check so that it checks local variables and parameters:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalLocalVariable&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;VARIABLE_DEF,PARAMETER_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * By default, this Check skip final validation on
 *  <a href = "https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
 * Enhanced For-Loop</a>.
 * </p>
 * <p>
 * Option 'validateEnhancedForLoopVariable' could be used to make Check to validate even variable
 *  from Enhanced For Loop.
 * </p>
 * <p>
 * An example of how to configure the check so that it also validates enhanced For Loop Variable is:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalLocalVariable&quot;&gt;
 *   &lt;property name=&quot;tokens&quot; value=&quot;VARIABLE_DEF&quot;/&gt;
 *   &lt;property name=&quot;validateEnhancedForLoopVariable&quot; value=&quot;true&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * for (int number : myNumbers) { // violation
 *   System.out.println(number);
 * }
 * </pre>
 * <p>
 * An example of how to configure check on local variables and parameters
 * but do not validate loop variables:
 * </p>
 * <pre>
 * &lt;module name=&quot;FinalLocalVariable&quot;&gt;
 *    &lt;property name=&quot;tokens&quot; value=&quot;VARIABLE_DEF,PARAMETER_DEF&quot;/&gt;
 *    &lt;property name=&quot;validateEnhancedForLoopVariable&quot; value=&quot;false&quot;/&gt;
 *  &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * public class MyClass {
 *   static int foo(int x, int y) { //violations, parameters should be final
 *     return x+y;
 *   }
 *   public static void main (String []args) { //violation, parameters should be final
 *     for (String i : args) {
 *       System.out.println(i);
 *     }
 *     int result=foo(1,2); // violation
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
 * {@code final.variable}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class FinalLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "final.variable";

    /**
     * Assign operator types.
     */
    private static final BitSet ASSIGN_OPERATOR_TYPES = TokenUtil.asBitSet(
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
        TokenTypes.DEC
    );

    /**
     * Loop types.
     */
    private static final BitSet LOOP_TYPES = TokenUtil.asBitSet(
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO
    );

    /** Scope Deque. */
    private final Deque<ScopeData> scopeStack = new ArrayDeque<>();

    /** Uninitialized variables of previous scope. */
    private final Deque<Deque<DetailAST>> prevScopeUninitializedVariables =
            new ArrayDeque<>();

    /** Assigned variables of current scope. */
    private final Deque<Deque<DetailAST>> currentScopeAssignedVariables =
            new ArrayDeque<>();

    /**
     * Control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     */
    private boolean validateEnhancedForLoopVariable;

    /**
     * Setter to control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     *
     * @param validateEnhancedForLoopVariable whether to check for-loop variable
     */
    public final void setValidateEnhancedForLoopVariable(boolean validateEnhancedForLoopVariable) {
        this.validateEnhancedForLoopVariable = validateEnhancedForLoopVariable;
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.EXPR,
        };
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.EXPR,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.IDENT,
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.OBJBLOCK,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.EXPR,
        };
    }

    // -@cs[CyclomaticComplexity] The only optimization which can be done here is moving CASE-block
    // expressions to separate methods, but that will not increase readability.
    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.LITERAL_FOR:
                scopeStack.push(new ScopeData());
                break;
            case TokenTypes.SLIST:
                currentScopeAssignedVariables.push(new ArrayDeque<>());
                if (ast.getParent().getType() != TokenTypes.CASE_GROUP
                    || ast.getParent().getParent().findFirstToken(TokenTypes.CASE_GROUP)
                    == ast.getParent()) {
                    storePrevScopeUninitializedVariableData();
                    scopeStack.push(new ScopeData());
                }
                break;
            case TokenTypes.PARAMETER_DEF:
                if (!isInLambda(ast)
                        && ast.findFirstToken(TokenTypes.MODIFIERS)
                            .findFirstToken(TokenTypes.FINAL) == null
                        && !isInAbstractOrNativeMethod(ast)
                        && !ScopeUtil.isInInterfaceBlock(ast)
                        && !isMultipleTypeCatch(ast)
                        && !CheckUtil.isReceiverParameter(ast)) {
                    insertParameter(ast);
                }
                break;
            case TokenTypes.VARIABLE_DEF:
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK
                        && ast.findFirstToken(TokenTypes.MODIFIERS)
                            .findFirstToken(TokenTypes.FINAL) == null
                        && !isVariableInForInit(ast)
                        && shouldCheckEnhancedForLoopVariable(ast)) {
                    insertVariable(ast);
                }
                break;
            case TokenTypes.IDENT:
                final int parentType = ast.getParent().getType();
                if (isAssignOperator(parentType) && isFirstChild(ast)) {
                    final Optional<FinalVariableCandidate> candidate = getFinalCandidate(ast);
                    if (candidate.isPresent()) {
                        determineAssignmentConditions(ast, candidate.get());
                        currentScopeAssignedVariables.peek().add(ast);
                    }
                    removeFinalVariableCandidateFromStack(ast);
                }
                break;
            case TokenTypes.LITERAL_BREAK:
                scopeStack.peek().containsBreak = true;
                break;
            case TokenTypes.EXPR:
                // Switch labeled expression has no slist
                if (ast.getParent().getType() == TokenTypes.SWITCH_RULE
                    && ast.getParent().getParent().findFirstToken(TokenTypes.SWITCH_RULE)
                        == ast.getParent()) {
                    storePrevScopeUninitializedVariableData();
                }
                break;
            default:
                throw new IllegalStateException("Incorrect token type");
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        Map<String, FinalVariableCandidate> scope = null;
        final Deque<DetailAST> prevScopeUninitializedVariableData;
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.LITERAL_FOR:
                scope = scopeStack.pop().scope;
                break;
            case TokenTypes.EXPR:
                // Switch labeled expression has no slist
                if (ast.getParent().getType() == TokenTypes.SWITCH_RULE) {
                    prevScopeUninitializedVariableData = prevScopeUninitializedVariables.peek();
                    if (shouldUpdateUninitializedVariables(ast.getParent())) {
                        updateAllUninitializedVariables(prevScopeUninitializedVariableData);
                    }
                }
                break;
            case TokenTypes.SLIST:
                prevScopeUninitializedVariableData = prevScopeUninitializedVariables.peek();
                boolean containsBreak = false;
                if (ast.getParent().getType() != TokenTypes.CASE_GROUP
                    || findLastChildWhichContainsSpecifiedToken(ast.getParent().getParent(),
                            TokenTypes.CASE_GROUP, TokenTypes.SLIST) == ast.getParent()) {
                    containsBreak = scopeStack.peek().containsBreak;
                    scope = scopeStack.pop().scope;
                    prevScopeUninitializedVariables.pop();
                }
                final DetailAST parent = ast.getParent();
                if (containsBreak || shouldUpdateUninitializedVariables(parent)) {
                    updateAllUninitializedVariables(prevScopeUninitializedVariableData);
                }
                updateCurrentScopeAssignedVariables();
                break;
            default:
                // do nothing
        }
        if (scope != null) {
            for (FinalVariableCandidate candidate : scope.values()) {
                final DetailAST ident = candidate.variableIdent;
                log(ident, MSG_KEY, ident.getText());
            }
        }
    }

    /**
     * Update assigned variables in a temporary stack.
     */
    private void updateCurrentScopeAssignedVariables() {
        // -@cs[MoveVariableInsideIf] assignment value is a modification call, so it can't be moved
        final Deque<DetailAST> poppedScopeAssignedVariableData =
                currentScopeAssignedVariables.pop();
        final Deque<DetailAST> currentScopeAssignedVariableData =
                currentScopeAssignedVariables.peek();
        if (currentScopeAssignedVariableData != null) {
            currentScopeAssignedVariableData.addAll(poppedScopeAssignedVariableData);
        }
    }

    /**
     * Determines identifier assignment conditions (assigned or already assigned).
     *
     * @param ident identifier.
     * @param candidate final local variable candidate.
     */
    private static void determineAssignmentConditions(DetailAST ident,
                                                      FinalVariableCandidate candidate) {
        if (candidate.assigned) {
            final int[] blockTypes = {
                TokenTypes.LITERAL_ELSE,
                TokenTypes.CASE_GROUP,
                TokenTypes.SWITCH_RULE,
            };
            if (!isInSpecificCodeBlocks(ident, blockTypes)) {
                candidate.alreadyAssigned = true;
            }
        }
        else {
            candidate.assigned = true;
        }
    }

    /**
     * Checks whether the scope of a node is restricted to a specific code blocks.
     *
     * @param node node.
     * @param blockTypes int array of all block types to check.
     * @return true if the scope of a node is restricted to specific code block types.
     */
    private static boolean isInSpecificCodeBlocks(DetailAST node, int... blockTypes) {
        boolean returnValue = false;
        for (int blockType : blockTypes) {
            for (DetailAST token = node.getParent(); token != null; token = token.getParent()) {
                final int type = token.getType();
                if (type == blockType) {
                    returnValue = true;
                    break;
                }
            }
        }
        return returnValue;
    }

    /**
     * Gets final variable candidate for ast.
     *
     * @param ast ast.
     * @return Optional of {@link FinalVariableCandidate} for ast from scopeStack.
     */
    private Optional<FinalVariableCandidate> getFinalCandidate(DetailAST ast) {
        Optional<FinalVariableCandidate> result = Optional.empty();
        final Iterator<ScopeData> iterator = scopeStack.descendingIterator();
        while (iterator.hasNext() && result.isEmpty()) {
            final ScopeData scopeData = iterator.next();
            result = scopeData.findFinalVariableCandidateForAst(ast);
        }
        return result;
    }

    /**
     * Store un-initialized variables in a temporary stack for future use.
     */
    private void storePrevScopeUninitializedVariableData() {
        final ScopeData scopeData = scopeStack.peek();
        final Deque<DetailAST> prevScopeUninitializedVariableData =
                new ArrayDeque<>();
        scopeData.uninitializedVariables.forEach(prevScopeUninitializedVariableData::push);
        prevScopeUninitializedVariables.push(prevScopeUninitializedVariableData);
    }

    /**
     * Update current scope data uninitialized variable according to the whole scope data.
     *
     * @param prevScopeUninitializedVariableData variable for previous stack of uninitialized
     *     variables
     * @noinspection MethodParameterNamingConvention
     */
    private void updateAllUninitializedVariables(
            Deque<DetailAST> prevScopeUninitializedVariableData) {
        final boolean hasSomeScopes = !currentScopeAssignedVariables.isEmpty();
        if (hasSomeScopes) {
            // Check for only previous scope
            updateUninitializedVariables(prevScopeUninitializedVariableData);
            // Check for rest of the scope
            prevScopeUninitializedVariables.forEach(this::updateUninitializedVariables);
        }
    }

    /**
     * Update current scope data uninitialized variable according to the specific scope data.
     *
     * @param scopeUninitializedVariableData variable for specific stack of uninitialized variables
     */
    private void updateUninitializedVariables(Deque<DetailAST> scopeUninitializedVariableData) {
        final Iterator<DetailAST> iterator = currentScopeAssignedVariables.peek().iterator();
        while (iterator.hasNext()) {
            final DetailAST assignedVariable = iterator.next();
            boolean shouldRemove = false;
            for (DetailAST variable : scopeUninitializedVariableData) {
                for (ScopeData scopeData : scopeStack) {
                    final FinalVariableCandidate candidate =
                        scopeData.scope.get(variable.getText());
                    DetailAST storedVariable = null;
                    if (candidate != null) {
                        storedVariable = candidate.variableIdent;
                    }
                    if (storedVariable != null
                            && isSameVariables(storedVariable, variable)
                            && isSameVariables(assignedVariable, variable)) {
                        scopeData.uninitializedVariables.push(variable);
                        shouldRemove = true;
                    }
                }
            }
            if (shouldRemove) {
                iterator.remove();
            }
        }
    }

    /**
     * If token is LITERAL_IF and there is an {@code else} following or token is CASE_GROUP or
     * SWITCH_RULE and there is another {@code case} following, then update the
     * uninitialized variables.
     *
     * @param ast token to be checked
     * @return true if should be updated, else false
     */
    private static boolean shouldUpdateUninitializedVariables(DetailAST ast) {
        return isIfTokenWithAnElseFollowing(ast) || isCaseTokenWithAnotherCaseFollowing(ast);
    }

    /**
     * If token is LITERAL_IF and there is an {@code else} following.
     *
     * @param ast token to be checked
     * @return true if token is LITERAL_IF and there is an {@code else} following, else false
     */
    private static boolean isIfTokenWithAnElseFollowing(DetailAST ast) {
        return ast.getType() == TokenTypes.LITERAL_IF
                && ast.getLastChild().getType() == TokenTypes.LITERAL_ELSE;
    }

    /**
     * If token is CASE_GROUP or SWITCH_RULE and there is another {@code case} following.
     *
     * @param ast token to be checked
     * @return true if token is CASE_GROUP or SWITCH_RULE and there is another {@code case}
     *     following, else false
     */
    private static boolean isCaseTokenWithAnotherCaseFollowing(DetailAST ast) {
        boolean result = false;
        if (ast.getType() == TokenTypes.CASE_GROUP) {
            result = findLastChildWhichContainsSpecifiedToken(
                    ast.getParent(), TokenTypes.CASE_GROUP, TokenTypes.SLIST) != ast;
        }
        else if (ast.getType() == TokenTypes.SWITCH_RULE) {
            result = ast.getNextSibling().getType() == TokenTypes.SWITCH_RULE;
        }
        return result;
    }

    /**
     * Returns the last child token that makes a specified type and contains containType in
     * its branch.
     *
     * @param ast token to be tested
     * @param childType the token type to match
     * @param containType the token type which has to be present in the branch
     * @return the matching token, or null if no match
     */
    private static DetailAST findLastChildWhichContainsSpecifiedToken(DetailAST ast, int childType,
                                                              int containType) {
        DetailAST returnValue = null;
        for (DetailAST astIterator = ast.getFirstChild(); astIterator != null;
                astIterator = astIterator.getNextSibling()) {
            if (astIterator.getType() == childType
                    && astIterator.findFirstToken(containType) != null) {
                returnValue = astIterator;
            }
        }
        return returnValue;
    }

    /**
     * Determines whether enhanced for-loop variable should be checked or not.
     *
     * @param ast The ast to compare.
     * @return true if enhanced for-loop variable should be checked.
     */
    private boolean shouldCheckEnhancedForLoopVariable(DetailAST ast) {
        return validateEnhancedForLoopVariable
                || ast.getParent().getType() != TokenTypes.FOR_EACH_CLAUSE;
    }

    /**
     * Insert a parameter at the topmost scope stack.
     *
     * @param ast the variable to insert.
     */
    private void insertParameter(DetailAST ast) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        scope.put(astNode.getText(), new FinalVariableCandidate(astNode));
    }

    /**
     * Insert a variable at the topmost scope stack.
     *
     * @param ast the variable to insert.
     */
    private void insertVariable(DetailAST ast) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        final FinalVariableCandidate candidate = new FinalVariableCandidate(astNode);
        // for-each variables are implicitly assigned
        candidate.assigned = ast.getParent().getType() == TokenTypes.FOR_EACH_CLAUSE;
        scope.put(astNode.getText(), candidate);
        if (!isInitialized(astNode)) {
            scopeStack.peek().uninitializedVariables.add(astNode);
        }
    }

    /**
     * Check if VARIABLE_DEF is initialized or not.
     *
     * @param ast VARIABLE_DEF to be checked
     * @return true if initialized
     */
    private static boolean isInitialized(DetailAST ast) {
        return ast.getParent().getLastChild().getType() == TokenTypes.ASSIGN;
    }

    /**
     * Whether the ast is the first child of its parent.
     *
     * @param ast the ast to check.
     * @return true if the ast is the first child of its parent.
     */
    private static boolean isFirstChild(DetailAST ast) {
        return ast.getPreviousSibling() == null;
    }

    /**
     * Removes the final variable candidate from the Stack.
     *
     * @param ast variable to remove.
     */
    private void removeFinalVariableCandidateFromStack(DetailAST ast) {
        final Iterator<ScopeData> iterator = scopeStack.descendingIterator();
        while (iterator.hasNext()) {
            final ScopeData scopeData = iterator.next();
            final Map<String, FinalVariableCandidate> scope = scopeData.scope;
            final FinalVariableCandidate candidate = scope.get(ast.getText());
            DetailAST storedVariable = null;
            if (candidate != null) {
                storedVariable = candidate.variableIdent;
            }
            if (storedVariable != null && isSameVariables(storedVariable, ast)) {
                if (shouldRemoveFinalVariableCandidate(scopeData, ast)) {
                    scope.remove(ast.getText());
                }
                break;
            }
        }
    }

    /**
     * Check if given parameter definition is a multiple type catch.
     *
     * @param parameterDefAst parameter definition
     * @return true if it is a multiple type catch, false otherwise
     */
    private static boolean isMultipleTypeCatch(DetailAST parameterDefAst) {
        final DetailAST typeAst = parameterDefAst.findFirstToken(TokenTypes.TYPE);
        return typeAst.findFirstToken(TokenTypes.BOR) != null;
    }

    /**
     * Whether the final variable candidate should be removed from the list of final local variable
     * candidates.
     *
     * @param scopeData the scope data of the variable.
     * @param ast the variable ast.
     * @return true, if the variable should be removed.
     */
    private static boolean shouldRemoveFinalVariableCandidate(ScopeData scopeData, DetailAST ast) {
        boolean shouldRemove = true;
        for (DetailAST variable : scopeData.uninitializedVariables) {
            if (variable.getText().equals(ast.getText())) {
                // if the variable is declared outside the loop and initialized inside
                // the loop, then it cannot be declared final, as it can be initialized
                // more than once in this case
                if (isInTheSameLoop(variable, ast) || !isUseOfExternalVariableInsideLoop(ast)) {
                    final FinalVariableCandidate candidate = scopeData.scope.get(ast.getText());
                    shouldRemove = candidate.alreadyAssigned;
                }
                scopeData.uninitializedVariables.remove(variable);
                break;
            }
        }
        return shouldRemove;
    }

    /**
     * Checks whether a variable which is declared outside loop is used inside loop.
     * For example:
     * <p>
     * {@code
     * int x;
     * for (int i = 0, j = 0; i < j; i++) {
     *     x = 5;
     * }
     * }
     * </p>
     *
     * @param variable variable.
     * @return true if a variable which is declared outside loop is used inside loop.
     */
    private static boolean isUseOfExternalVariableInsideLoop(DetailAST variable) {
        DetailAST loop2 = variable.getParent();
        while (loop2 != null
            && !isLoopAst(loop2.getType())) {
            loop2 = loop2.getParent();
        }
        return loop2 != null;
    }

    /**
     * Is Arithmetic operator.
     *
     * @param parentType token AST
     * @return true is token type is in arithmetic operator
     */
    private static boolean isAssignOperator(int parentType) {
        return ASSIGN_OPERATOR_TYPES.get(parentType);
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
     *
     * @param variableDef variable definition node.
     * @return true if variable is defined in {@link TokenTypes#FOR_INIT for-loop init}
     */
    private static boolean isVariableInForInit(DetailAST variableDef) {
        return variableDef.getParent().getType() == TokenTypes.FOR_INIT;
    }

    /**
     * Determines whether an AST is a descendant of an abstract or native method.
     *
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
                abstractOrNative = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null
                        || modifiers.findFirstToken(TokenTypes.LITERAL_NATIVE) != null;
            }
            parent = parent.getParent();
        }
        return abstractOrNative;
    }

    /**
     * Check if current param is lambda's param.
     *
     * @param paramDef {@link TokenTypes#PARAMETER_DEF parameter def}.
     * @return true if current param is lambda's param.
     */
    private static boolean isInLambda(DetailAST paramDef) {
        return paramDef.getParent().getParent().getType() == TokenTypes.LAMBDA;
    }

    /**
     * Find the Class, Constructor, Enum, Method, or Field in which it is defined.
     *
     * @param ast Variable for which we want to find the scope in which it is defined
     * @return ast The Class or Constructor or Method in which it is defined.
     */
    private static DetailAST findFirstUpperNamedBlock(DetailAST ast) {
        DetailAST astTraverse = ast;
        while (!TokenUtil.isOfType(astTraverse, TokenTypes.METHOD_DEF, TokenTypes.CLASS_DEF,
                TokenTypes.ENUM_DEF, TokenTypes.CTOR_DEF, TokenTypes.COMPACT_CTOR_DEF)
                && !ScopeUtil.isClassFieldDef(astTraverse)) {
            astTraverse = astTraverse.getParent();
        }
        return astTraverse;
    }

    /**
     * Check if both the Variables are same.
     *
     * @param ast1 Variable to compare
     * @param ast2 Variable to compare
     * @return true if both the variables are same, otherwise false
     */
    private static boolean isSameVariables(DetailAST ast1, DetailAST ast2) {
        final DetailAST classOrMethodOfAst1 =
            findFirstUpperNamedBlock(ast1);
        final DetailAST classOrMethodOfAst2 =
            findFirstUpperNamedBlock(ast2);
        return classOrMethodOfAst1 == classOrMethodOfAst2 && ast1.getText().equals(ast2.getText());
    }

    /**
     * Check if both the variables are in the same loop.
     *
     * @param ast1 variable to compare.
     * @param ast2 variable to compare.
     * @return true if both the variables are in the same loop.
     */
    private static boolean isInTheSameLoop(DetailAST ast1, DetailAST ast2) {
        DetailAST loop1 = ast1.getParent();
        while (loop1 != null && !isLoopAst(loop1.getType())) {
            loop1 = loop1.getParent();
        }
        DetailAST loop2 = ast2.getParent();
        while (loop2 != null && !isLoopAst(loop2.getType())) {
            loop2 = loop2.getParent();
        }
        return loop1 != null && loop1 == loop2;
    }

    /**
     * Checks whether the ast is a loop.
     *
     * @param ast the ast to check.
     * @return true if the ast is a loop.
     */
    private static boolean isLoopAst(int ast) {
        return LOOP_TYPES.get(ast);
    }

    /**
     * Holder for the scope data.
     */
    private static class ScopeData {

        /** Contains variable definitions. */
        private final Map<String, FinalVariableCandidate> scope = new HashMap<>();

        /** Contains definitions of uninitialized variables. */
        private final Deque<DetailAST> uninitializedVariables = new ArrayDeque<>();

        /** Whether there is a {@code break} in the scope. */
        private boolean containsBreak;

        /**
         * Searches for final local variable candidate for ast in the scope.
         *
         * @param ast ast.
         * @return Optional of {@link FinalVariableCandidate}.
         */
        public Optional<FinalVariableCandidate> findFinalVariableCandidateForAst(DetailAST ast) {
            Optional<FinalVariableCandidate> result = Optional.empty();
            DetailAST storedVariable = null;
            final Optional<FinalVariableCandidate> candidate =
                Optional.ofNullable(scope.get(ast.getText()));
            if (candidate.isPresent()) {
                storedVariable = candidate.get().variableIdent;
            }
            if (storedVariable != null && isSameVariables(storedVariable, ast)) {
                result = candidate;
            }
            return result;
        }

    }

    /** Represents information about final local variable candidate. */
    private static class FinalVariableCandidate {

        /** Identifier token. */
        private final DetailAST variableIdent;
        /** Whether the variable is assigned. */
        private boolean assigned;
        /** Whether the variable is already assigned. */
        private boolean alreadyAssigned;

        /**
         * Creates new instance.
         *
         * @param variableIdent variable identifier.
         */
        /* package */ FinalVariableCandidate(DetailAST variableIdent) {
            this.variableIdent = variableIdent;
        }

    }

}

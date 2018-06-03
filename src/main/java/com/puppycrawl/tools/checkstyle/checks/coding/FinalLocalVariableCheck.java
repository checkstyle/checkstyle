////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Ensures that local variables that never get their values changed,
 * must be declared final.
 * </p>
 * <p>
 * An example of how to configure the check to validate variable definition is:
 * </p>
 * <pre>
 * &lt;module name="FinalLocalVariable"&gt;
 *     &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * By default, this Check skip final validation on
 *  <a href = "https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.14.2">
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
 *     &lt;property name="tokens" value="VARIABLE_DEF"/&gt;
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

    /**
     * Loop types.
     */
    private static final int[] LOOP_TYPES = {
        TokenTypes.LITERAL_FOR,
        TokenTypes.LITERAL_WHILE,
        TokenTypes.LITERAL_DO,
    };

    /** Scope Deque. */
    private final Deque<ScopeData> scopeStack = new ArrayDeque<>();

    /** Uninitialized variables of previous scope. */
    private final Deque<Deque<DetailAST>> prevScopeUninitializedVariables =
            new ArrayDeque<>();

    /** Assigned variables of current scope. */
    private final Deque<Deque<DetailAST>> currentScopeAssignedVariables =
            new ArrayDeque<>();

    /** Controls whether to check enhanced for-loop variable. */
    private boolean validateEnhancedForLoopVariable;

    static {
        // Array sorting for binary search
        Arrays.sort(ASSIGN_OPERATOR_TYPES);
        Arrays.sort(LOOP_TYPES);
    }

    /**
     * Whether to check enhanced for-loop variable or not.
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
            TokenTypes.VARIABLE_DEF,
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
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
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
                        && !isMultipleTypeCatch(ast)) {
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
            default:
                throw new IllegalStateException("Incorrect token type");
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        Map<String, FinalVariableCandidate> scope = null;
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
                scope = scopeStack.pop().scope;
                break;
            case TokenTypes.SLIST:
                // -@cs[MoveVariableInsideIf] assignment value is modified later so it can't be
                // moved
                final Deque<DetailAST> prevScopeUninitializedVariableData =
                    prevScopeUninitializedVariables.peek();
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
        // -@cs[MoveVariableInsideIf] assignment value is a modification call so it can't be moved
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
     * @param ident identifier.
     * @param candidate final local variable candidate.
     */
    private static void determineAssignmentConditions(DetailAST ident,
                                                      FinalVariableCandidate candidate) {
        if (candidate.assigned) {
            if (!isInSpecificCodeBlock(ident, TokenTypes.LITERAL_ELSE)
                    && !isInSpecificCodeBlock(ident, TokenTypes.CASE_GROUP)) {
                candidate.alreadyAssigned = true;
            }
        }
        else {
            candidate.assigned = true;
        }
    }

    /**
     * Checks whether the scope of a node is restricted to a specific code block.
     * @param node node.
     * @param blockType block type.
     * @return true if the scope of a node is restricted to a specific code block.
     */
    private static boolean isInSpecificCodeBlock(DetailAST node, int blockType) {
        boolean returnValue = false;
        for (DetailAST token = node.getParent(); token != null; token = token.getParent()) {
            final int type = token.getType();
            if (type == blockType) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    /**
     * Gets final variable candidate for ast.
     * @param ast ast.
     * @return Optional of {@link FinalVariableCandidate} for ast from scopeStack.
     */
    private Optional<FinalVariableCandidate> getFinalCandidate(DetailAST ast) {
        Optional<FinalVariableCandidate> result = Optional.empty();
        final Iterator<ScopeData> iterator = scopeStack.descendingIterator();
        while (iterator.hasNext() && !result.isPresent()) {
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
     * @param prevScopeUninitializedVariableData variable for previous stack of uninitialized
     *     variables
     * @noinspection MethodParameterNamingConvention
     */
    private void updateAllUninitializedVariables(
            Deque<DetailAST> prevScopeUninitializedVariableData) {
        // Check for only previous scope
        updateUninitializedVariables(prevScopeUninitializedVariableData);
        // Check for rest of the scope
        prevScopeUninitializedVariables.forEach(this::updateUninitializedVariables);
    }

    /**
     * Update current scope data uninitialized variable according to the specific scope data.
     * @param scopeUninitializedVariableData variable for specific stack of uninitialized variables
     */
    private void updateUninitializedVariables(Deque<DetailAST> scopeUninitializedVariableData) {
        final Iterator<DetailAST> iterator = currentScopeAssignedVariables.peek().iterator();
        while (iterator.hasNext()) {
            final DetailAST assignedVariable = iterator.next();
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
                        iterator.remove();
                    }
                }
            }
        }
    }

    /**
     * If token is LITERAL_IF and there is an {@code else} following or token is CASE_GROUP and
     * there is another {@code case} following, then update the uninitialized variables.
     * @param ast token to be checked
     * @return true if should be updated, else false
     */
    private static boolean shouldUpdateUninitializedVariables(DetailAST ast) {
        return isIfTokenWithAnElseFollowing(ast) || isCaseTokenWithAnotherCaseFollowing(ast);
    }

    /**
     * If token is LITERAL_IF and there is an {@code else} following.
     * @param ast token to be checked
     * @return true if token is LITERAL_IF and there is an {@code else} following, else false
     */
    private static boolean isIfTokenWithAnElseFollowing(DetailAST ast) {
        return ast.getType() == TokenTypes.LITERAL_IF
                && ast.getLastChild().getType() == TokenTypes.LITERAL_ELSE;
    }

    /**
     * If token is CASE_GROUP and there is another {@code case} following.
     * @param ast token to be checked
     * @return true if token is CASE_GROUP and there is another {@code case} following, else false
     */
    private static boolean isCaseTokenWithAnotherCaseFollowing(DetailAST ast) {
        return ast.getType() == TokenTypes.CASE_GROUP
                && findLastChildWhichContainsSpecifiedToken(
                        ast.getParent(), TokenTypes.CASE_GROUP, TokenTypes.SLIST) != ast;
    }

    /**
     * Returns the last child token that makes a specified type and contains containType in
     * its branch.
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
     * @param ast The ast to compare.
     * @return true if enhanced for-loop variable should be checked.
     */
    private boolean shouldCheckEnhancedForLoopVariable(DetailAST ast) {
        return validateEnhancedForLoopVariable
                || ast.getParent().getType() != TokenTypes.FOR_EACH_CLAUSE;
    }

    /**
     * Insert a parameter at the topmost scope stack.
     * @param ast the variable to insert.
     */
    private void insertParameter(DetailAST ast) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        scope.put(astNode.getText(), new FinalVariableCandidate(astNode));
    }

    /**
     * Insert a variable at the topmost scope stack.
     * @param ast the variable to insert.
     */
    private void insertVariable(DetailAST ast) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        scope.put(astNode.getText(), new FinalVariableCandidate(astNode));
        if (!isInitialized(astNode)) {
            scopeStack.peek().uninitializedVariables.add(astNode);
        }
    }

    /**
     * Check if VARIABLE_DEF is initialized or not.
     * @param ast VARIABLE_DEF to be checked
     * @return true if initialized
     */
    private static boolean isInitialized(DetailAST ast) {
        return ast.getParent().getLastChild().getType() == TokenTypes.ASSIGN;
    }

    /**
     * Whether the ast is the first child of its parent.
     * @param ast the ast to check.
     * @return true if the ast is the first child of its parent.
     */
    private static boolean isFirstChild(DetailAST ast) {
        return ast.getPreviousSibling() == null;
    }

    /**
     * Removes the final variable candidate from the Stack.
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
     * @param parameterDefAst parameter definition
     * @return true if it is a multiple type catch, false otherwise
     */
    private static boolean isMultipleTypeCatch(DetailAST parameterDefAst) {
        final DetailAST typeAst = parameterDefAst.findFirstToken(TokenTypes.TYPE);
        return typeAst.getFirstChild().getType() == TokenTypes.BOR;
    }

    /**
     * Whether the final variable candidate should be removed from the list of final local variable
     * candidates.
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
     * @param parentType token AST
     * @return true is token type is in arithmetic operator
     */
    private static boolean isAssignOperator(int parentType) {
        return Arrays.binarySearch(ASSIGN_OPERATOR_TYPES, parentType) >= 0;
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
                abstractOrNative = modifiers.findFirstToken(TokenTypes.ABSTRACT) != null
                        || modifiers.findFirstToken(TokenTypes.LITERAL_NATIVE) != null;
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
     * Find the Class, Constructor, Enum, Method, or Field in which it is defined.
     * @param ast Variable for which we want to find the scope in which it is defined
     * @return ast The Class or Constructor or Method in which it is defined.
     */
    private static DetailAST findFirstUpperNamedBlock(DetailAST ast) {
        DetailAST astTraverse = ast;
        while (astTraverse.getType() != TokenTypes.METHOD_DEF
                && astTraverse.getType() != TokenTypes.CLASS_DEF
                && astTraverse.getType() != TokenTypes.ENUM_DEF
                && astTraverse.getType() != TokenTypes.CTOR_DEF
                && !ScopeUtil.isClassFieldDef(astTraverse)) {
            astTraverse = astTraverse.getParent();
        }
        return astTraverse;
    }

    /**
     * Check if both the Variables are same.
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
     * @param ast the ast to check.
     * @return true if the ast is a loop.
     */
    private static boolean isLoopAst(int ast) {
        return Arrays.binarySearch(LOOP_TYPES, ast) >= 0;
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

    /**Represents information about final local variable candidate. */
    private static class FinalVariableCandidate {

        /** Identifier token. */
        private final DetailAST variableIdent;
        /** Whether the variable is assigned. */
        private boolean assigned;
        /** Whether the variable is already assigned. */
        private boolean alreadyAssigned;

        /**
         * Creates new instance.
         * @param variableIdent variable identifier.
         */
        FinalVariableCandidate(DetailAST variableIdent) {
            this.variableIdent = variableIdent;
        }

    }

}

///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks that local variables that never have their values changed are declared final.
 * The check can be configured to also check that unchanged parameters are declared final.
 * </div>
 *
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
 * Property {@code validateUnnamedVariables} - Control whether to check
 * <a href="https://docs.oracle.com/javase/specs/jls/se21/preview/specs/unnamed-jls.html">
 * unnamed variables</a>.
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

    /** Scope Deque. */
    private final Deque<ScopeData> scopeStack = new ArrayDeque<>();

    /** Assigned variables of current scope. */
    private final Deque<Deque<DetailAST>> currentScopeAssignedVariables = new ArrayDeque<>();

    /**
     * Control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     */
    private boolean validateEnhancedForLoopVariable;

    /**
     * Control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se21/preview/specs/unnamed-jls.html">
     * unnamed variables</a>.
     */
    private boolean validateUnnamedVariables;

    /**
     * Setter to control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-14.html#jls-14.14.2">
     * enhanced for-loop</a> variable.
     *
     * @param validateEnhancedForLoopVariable whether to check for-loop variable
     * @since 6.5
     */
    public final void setValidateEnhancedForLoopVariable(boolean validateEnhancedForLoopVariable) {
        this.validateEnhancedForLoopVariable = validateEnhancedForLoopVariable;
    }

    /**
     * Setter to control whether to check
     * <a href="https://docs.oracle.com/javase/specs/jls/se21/preview/specs/unnamed-jls.html">
     * unnamed variables</a>.
     *
     * @param validateUnnamedVariables whether to check unnamed variables
     * @since 10.18.0
     */
    public final void setValidateUnnamedVariables(boolean validateUnnamedVariables) {
        this.validateUnnamedVariables = validateUnnamedVariables;
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

    /**
     * Processes the AST node during visitation phase.
     *
     * @param ast The AST node being visited
     */
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
                processStatementList(ast);
                break;
            case TokenTypes.PARAMETER_DEF:
                processParameterDefinition(ast);
                break;
            case TokenTypes.VARIABLE_DEF:
                processVariableDefinition(ast);
                break;
            case TokenTypes.IDENT:
                processIdentifier(ast);
                break;
            case TokenTypes.LITERAL_BREAK:
                scopeStack.peek().containsBreak = true;
                break;
            case TokenTypes.EXPR:
                processExpression(ast);
                break;
            default:
                throw new IllegalStateException("Incorrect token type");
        }
    }

    /**
     * Processes expression nodes in switch rules.
     *
     * @param exprAst The expression AST node
     */
    private void processExpression(DetailAST exprAst) {
        // Switch labeled expression has no statement list
        if (exprAst.getParent().getType() == TokenTypes.SWITCH_RULE) {
            storePrevScopeUninitializedVariableData();
        }
    }

    /**
     * Processes identifier nodes that might be assignments.
     *
     * @param identAst The identifier AST node
     */
    private void processIdentifier(DetailAST identAst) {
        getFinalCandidate(identAst).map(candidate -> {
            if (FinalLocalVariableCheckUtil.isFirstChild(identAst)
                && FinalLocalVariableCheckUtil.isAssignOperator(identAst.getParent().getType())) {
                candidate.setAssignmentConditions(candidate, identAst);
                currentScopeAssignedVariables.peek().add(identAst);
                removeFinalVariableCandidateFromStack(identAst);
            }
            return null;
        });
    }

    /**
     * Processes variable definition nodes.
     *
     * @param varDefAst The variable definition AST node
     */
    private void processVariableDefinition(DetailAST varDefAst) {
        if (varDefAst.getParent().getType() != TokenTypes.OBJBLOCK
            && varDefAst.findFirstToken(TokenTypes.MODIFIERS)
            .findFirstToken(TokenTypes.FINAL) == null
            && !FinalLocalVariableCheckUtil.isVariableInForInit(varDefAst)
            && shouldCheckEnhancedForLoopVariable(varDefAst)
            && shouldCheckUnnamedVariable(varDefAst)) {
            insertVariable(varDefAst);
        }
    }

    /**
     * Processes parameter definition nodes.
     *
     * @param paramDefAst The parameter definition AST node
     */
    private void processParameterDefinition(DetailAST paramDefAst) {
        if (FinalLocalVariableCheckUtil.isValidParameterDefinition(paramDefAst)) {
            final DetailAST astNode = paramDefAst.findFirstToken(TokenTypes.IDENT);
            scopeStack.peek().scope.put(astNode.getText(), new FinalVariableCandidate(astNode));
        }
    }

    /**
     * Processes statement list nodes.
     *
     * @param stmtListAst The statement list AST node
     */
    private void processStatementList(DetailAST stmtListAst) {
        currentScopeAssignedVariables.push(new ArrayDeque<>());
        if (stmtListAst.getParent().getType() != TokenTypes.CASE_GROUP
            || stmtListAst.getParent().getParent().findFirstToken(TokenTypes.CASE_GROUP)
            == stmtListAst.getParent()) {
            storePrevScopeUninitializedVariableData();
            scopeStack.push(new ScopeData());
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        Map<String, FinalVariableCandidate> scope = null;
        final DetailAST parentAst = ast.getParent();
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK:
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.LITERAL_FOR:
                scope = scopeStack.pop().scope;
                break;
            case TokenTypes.EXPR:
                // Switch labeled expression has no slist
                if (parentAst.getType() == TokenTypes.SWITCH_RULE
                    && FinalLocalVariableCheckUtil.shouldUpdateUninitializedVariables(parentAst)) {
                    updateAllUninitializedVariables();
                }
                break;
            case TokenTypes.SLIST:
                boolean containsBreak = false;
                if (parentAst.getType() != TokenTypes.CASE_GROUP
                    || FinalLocalVariableCheckUtil
                    .findLastCaseGroupWhichContainsSlist(parentAst.getParent()) == parentAst) {
                    containsBreak = scopeStack.peek().containsBreak;
                    scope = scopeStack.pop().scope;
                }
                if (containsBreak
                    || FinalLocalVariableCheckUtil.shouldUpdateUninitializedVariables(parentAst)) {
                    updateAllUninitializedVariables();
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
        final Deque<DetailAST> pop = currentScopeAssignedVariables.pop();
        final Deque<DetailAST> peek = currentScopeAssignedVariables.peek();
        if (peek != null) {
            peek.addAll(pop);
        }
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
        final Deque<DetailAST> prevScopeUninitializedVariableData = new ArrayDeque<>();
        scopeData.uninitializedVariables.forEach(prevScopeUninitializedVariableData::push);
        scopeData.prevScopeUninitializedVariables = prevScopeUninitializedVariableData;
    }

    /**
     * Update current scope data uninitialized variable according to the whole scope data.
     */
    private void updateAllUninitializedVariables() {
        if (!currentScopeAssignedVariables.isEmpty()) {
            scopeStack.forEach(scopeData
                -> updateUninitializedVariables(scopeData.prevScopeUninitializedVariables));
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
                        && FinalLocalVariableCheckUtil.isEqual(assignedVariable, variable)) {
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
     * Determines whether unnamed variable should be checked or not.
     *
     * @param ast The ast to compare.
     * @return true if unnamed variable should be checked.
     */
    private boolean shouldCheckUnnamedVariable(DetailAST ast) {
        return validateUnnamedVariables
                 || !"_".equals(ast.findFirstToken(TokenTypes.IDENT).getText());
    }

    /**
     * Insert a variable at the topmost scope stack.
     *
     * @param ast the variable to insert.
     */
    private void insertVariable(DetailAST ast) {
        final DetailAST astNode = ast.findFirstToken(TokenTypes.IDENT);
        final FinalVariableCandidate candidate = new FinalVariableCandidate(astNode);
        // for-each variables are implicitly assigned
        candidate.assigned = ast.getParent().getType() == TokenTypes.FOR_EACH_CLAUSE;
        scopeStack.peek().scope.put(astNode.getText(), candidate);
        if (!FinalLocalVariableCheckUtil.isInitialized(astNode)) {
            scopeStack.peek().uninitializedVariables.add(astNode);
        }
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
            if (storedVariable != null
                && FinalLocalVariableCheckUtil.isEqual(storedVariable, ast)) {
                if (scopeData.isRemoveFinalVariableCandidate(ast)) {
                    scope.remove(ast.getText());
                }
                break;
            }
        }
    }

    /**
     * Holder for the scope data.
     */
    private static final class ScopeData {

        /**
         * Contains variable definitions.
         */
        private final Map<String, FinalVariableCandidate> scope = new HashMap<>();

        /**
         * Contains definitions of uninitialized variables.
         */
        private final Deque<DetailAST> uninitializedVariables = new ArrayDeque<>();

        /**
         * Contains definitions of previous scope uninitialized variables.
         */
        private Deque<DetailAST> prevScopeUninitializedVariables = new ArrayDeque<>();

        /**
         * Whether there is a {@code break} in the scope.
         */
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
                storedVariable = candidate.orElseThrow().variableIdent;
            }
            if (storedVariable != null
                && FinalLocalVariableCheckUtil.isEqual(storedVariable, ast)) {
                result = candidate;
            }
            return result;
        }

        /**
         * Whether the final variable candidate should be removed from the list of
         * final local variable candidates.
         *
         * @param ast the variable ast.
         * @return true, if the variable should be removed.
         */
        public boolean isRemoveFinalVariableCandidate(DetailAST ast) {
            boolean shouldRemove = true;
            for (DetailAST variable : uninitializedVariables) {
                if (variable.getText().equals(ast.getText())) {
                    // if the variable is declared outside the loop and initialized inside
                    // the loop, then it cannot be declared final, as it can be initialized
                    // more than once in this case
                    if (FinalLocalVariableCheckUtil.getParentLoop(ast)
                        == FinalLocalVariableCheckUtil.getParentLoop(variable)) {
                        shouldRemove = scope.get(ast.getText()).alreadyAssigned;
                    }
                    uninitializedVariables.remove(variable);
                    break;
                }
            }
            return shouldRemove;
        }

    }

    /**
     * Represents information about final local variable candidate.
     */
    public static final class FinalVariableCandidate {

        /**
         * Block types.
         */
        public static final int[] BLOCK_TYPES = {
            TokenTypes.CASE_GROUP,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.SWITCH_RULE,
        };

        /**
         * Identifier token.
         */
        private final DetailAST variableIdent;
        /**
         * Whether the variable is assigned.
         */
        private boolean assigned;
        /**
         * Whether the variable is already assigned.
         */
        private boolean alreadyAssigned;

        /**
         * Creates new instance.
         *
         * @param variableIdent variable identifier.
         */
        private FinalVariableCandidate(DetailAST variableIdent) {
            this.variableIdent = variableIdent;
        }

        /**
         * Determines identifier assignment conditions (assigned or already assigned).
         *
         * @param candidate final local variable candidate.
         * @param ident     identifier.
         */
        private void setAssignmentConditions(FinalVariableCandidate candidate, DetailAST ident) {
            candidate.alreadyAssigned = candidate.assigned
                && !FinalLocalVariableCheckUtil.isInSpecificCodeBlocks(ident, BLOCK_TYPES);
            candidate.assigned = true;
            // RV: skipping the else block works - assuming BUG or test blind-spot
            // as we have 100% test coverage so the else might not be needed
            //        else {
            //        }
        }

    }

}

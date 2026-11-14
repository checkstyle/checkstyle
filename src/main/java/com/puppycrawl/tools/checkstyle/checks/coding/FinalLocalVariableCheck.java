///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
 * <div>
 * Checks that local variables that never have their values changed are declared final.
 * The check can be configured to also check that unchanged parameters are declared final.
 * </div>
 *
 * <p>
 * Notes:
 * When configured to check parameters, the check ignores parameters of interface
 * methods and abstract methods.
 * </p>
 *
 * @since 3.2
 */
@FileStatefulCheck
public class FinalLocalVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
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

    /** Branch of a conditional expression: the condition itself. */
    private static final int QUESTION_CONDITION_BRANCH = 0;

    /** Branch of a conditional expression: the branch taken when the condition is true. */
    private static final int QUESTION_THEN_BRANCH = 1;

    /** Branch of a conditional expression: the branch taken when the condition is false. */
    private static final int QUESTION_ELSE_BRANCH = 2;

    /** Scope Deque. */
    private final Deque<ScopeData> scopeStack = new ArrayDeque<>();

    /** Assigned variables of current scope. */
    private final Deque<Deque<DetailAST>> currentScopeAssignedVariables =
            new ArrayDeque<>();

    /** Unassigned candidates on entry to conditional blocks that end with a throw. */
    private final Map<DetailAST, ThrowingBranch> throwingBranches =
            new HashMap<>();

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
     * Creates a new {@code FinalLocalVariableCheck} instance.
     */
    public FinalLocalVariableCheck() {
        // no code by default
    }

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
            TokenTypes.COMPACT_COMPILATION_UNIT,
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
            TokenTypes.COMPACT_COMPILATION_UNIT,
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
            TokenTypes.COMPACT_COMPILATION_UNIT,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_FOR,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.EXPR,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        scopeStack.clear();
        currentScopeAssignedVariables.clear();
        throwingBranches.clear();
    }

    // -@cs[CyclomaticComplexity] The only optimization which can be done here is moving CASE-block
    // expressions to separate methods, but that will not increase readability.
    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.COMPACT_COMPILATION_UNIT, TokenTypes.OBJBLOCK,
                 TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF, TokenTypes.LITERAL_FOR ->
                scopeStack.push(new ScopeData());

            case TokenTypes.SLIST -> {
                if (ThrowingBranch.isThrowingBranch(ast)) {
                    throwingBranches.put(ast, new ThrowingBranch(scopeStack));
                }
                currentScopeAssignedVariables.push(new ArrayDeque<>());
                if (ast.getParent().getType() != TokenTypes.CASE_GROUP
                    || ast.getParent().getParent()
                    .findFirstToken(TokenTypes.CASE_GROUP) == ast.getParent()) {
                    storePrevScopeUninitializedVariableData();
                    scopeStack.push(new ScopeData());
                }
            }

            case TokenTypes.PARAMETER_DEF -> {
                if (!isInLambda(ast)
                        && ast.findFirstToken(TokenTypes.MODIFIERS)
                            .findFirstToken(TokenTypes.FINAL) == null
                        && !isInMethodWithoutBody(ast)
                        && !isMultipleTypeCatch(ast)
                        && !CheckUtil.isReceiverParameter(ast)) {
                    insertParameter(ast);
                }
            }

            case TokenTypes.VARIABLE_DEF -> {
                if (ast.getParent().getType() != TokenTypes.OBJBLOCK
                        && ast.findFirstToken(TokenTypes.MODIFIERS)
                            .findFirstToken(TokenTypes.FINAL) == null
                        && !isVariableInForInit(ast)
                        && shouldCheckEnhancedForLoopVariable(ast)
                        && shouldCheckUnnamedVariable(ast)) {
                    insertVariable(ast);
                }
            }

            case TokenTypes.IDENT -> {
                final int parentType = ast.getParent().getType();
                if (isAssignOperator(parentType) && isFirstChild(ast)) {
                    final Optional<FinalVariableCandidate> candidate = getFinalCandidate(ast);
                    if (candidate.isPresent()) {
                        determineAssignmentConditions(ast, candidate.orElseThrow());
                        currentScopeAssignedVariables.peek().add(ast);
                    }
                    removeFinalVariableCandidateFromStack(ast);
                }
            }

            case TokenTypes.LITERAL_BREAK -> scopeStack.peek().containsBreak = true;

            case TokenTypes.EXPR -> {
                // Switch labeled expression has no slist
                if (ast.getParent().getType() == TokenTypes.SWITCH_RULE) {
                    storePrevScopeUninitializedVariableData();
                }
            }

            default -> throw new IllegalStateException("Incorrect token type");
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        Map<String, FinalVariableCandidate> scope = null;
        final DetailAST parentAst = ast.getParent();
        switch (ast.getType()) {
            case TokenTypes.OBJBLOCK, TokenTypes.CTOR_DEF, TokenTypes.METHOD_DEF,
                 TokenTypes.LITERAL_FOR ->
                scope = scopeStack.pop().scope;

            case TokenTypes.EXPR -> {
                // Switch labeled expression has no slist
                if (parentAst.getType() == TokenTypes.SWITCH_RULE
                    && shouldUpdateUninitializedVariables(parentAst)) {
                    updateAllUninitializedVariables();
                }
            }

            case TokenTypes.SLIST -> {
                boolean containsBreak = false;
                if (parentAst.getType() != TokenTypes.CASE_GROUP
                    || findLastCaseGroupWhichContainsSlist(parentAst.getParent())
                    == parentAst) {
                    containsBreak = scopeStack.peek().containsBreak;
                    scope = scopeStack.pop().scope;
                }
                if (containsBreak || shouldUpdateUninitializedVariables(parentAst)) {
                    updateAllUninitializedVariables();
                }
                final ThrowingBranch branch = throwingBranches.remove(ast);
                if (branch != null) {
                    branch.restore();
                }
                updateCurrentScopeAssignedVariables();
            }

            default -> {
                // do nothing
            }
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
    private void determineAssignmentConditions(DetailAST ident,
                                               FinalVariableCandidate candidate) {
        if (candidate.assigned) {
            final int[] blockTypes = {
                TokenTypes.LITERAL_ELSE,
                TokenTypes.CASE_GROUP,
                TokenTypes.SWITCH_RULE,
            };
            final boolean inOppositeBranch =
                    isInOppositeBranchOfSameQuestion(ident, candidate);
            if (!inOppositeBranch && !isInSpecificCodeBlocks(ident, blockTypes)) {
                candidate.alreadyAssigned = true;
            }
        }
        else {
            candidate.assigned = true;
        }
    }

    /**
     * Checks whether the identifier is assigned in one branch of a conditional
     * expression while a previous assignment of the same variable sits in the other
     * branch of the same conditional expression. The two assignments are mutually
     * exclusive, in the same way as assignments in the branches of an if-else
     * statement, so the variable is still assigned only once on every path. In
     * that case the variable is also put back into the uninitialized variables of
     * its scope, as there is no block boundary inside an expression that would
     * restore it the way leaving a branch block does.
     *
     * @param ident identifier.
     * @param candidate final local variable candidate.
     * @return true if the assignment is in the opposite branch of a conditional
     *     expression from a previous assignment of the same variable.
     */
    private boolean isInOppositeBranchOfSameQuestion(DetailAST ident,
                                                     FinalVariableCandidate candidate) {
        boolean result = false;
        final Deque<DetailAST> assignedVariables = currentScopeAssignedVariables.peek();
        if (assignedVariables != null) {
            final Map<DetailAST, Integer> branches = getQuestionBranches(ident);
            if (!branches.isEmpty()) {
                for (DetailAST assignedVariable : assignedVariables) {
                    if (isSameVariables(candidate.variableIdent, assignedVariable)
                            && isInOppositeBranches(branches, assignedVariable)) {
                        result = true;
                        break;
                    }
                }
            }
        }
        if (result) {
            restoreUninitializedVariable(candidate);
        }
        return result;
    }

    /**
     * Checks whether a previous assignment is in the opposite branch of one of the
     * conditional expressions the identifier is assigned in.
     *
     * @param branches conditional expressions containing the identifier, mapped to
     *     the branch the identifier is in.
     * @param assignedVariable a previous assignment of the same variable.
     * @return true if both assignments are in opposite branches of one conditional
     *     expression.
     */
    private static boolean isInOppositeBranches(Map<DetailAST, Integer> branches,
                                                DetailAST assignedVariable) {
        boolean result = false;
        for (DetailAST token = assignedVariable.getParent(); token != null && !result;
                token = token.getParent()) {
            if (token.getType() == TokenTypes.QUESTION) {
                final Integer branch = branches.get(token);
                if (branch != null) {
                    final int otherBranch = getQuestionBranch(token, assignedVariable);
                    result = isOppositeBranch(branch, otherBranch);
                }
            }
        }
        return result;
    }

    /**
     * Checks whether two branches of a conditional expression are the then branch
     * and the else branch, in any order.
     *
     * @param branch branch of one assignment.
     * @param otherBranch branch of the other assignment.
     * @return true if the branches are opposite.
     */
    private static boolean isOppositeBranch(int branch, int otherBranch) {
        final boolean result;
        switch (branch) {
            case QUESTION_THEN_BRANCH -> result = otherBranch == QUESTION_ELSE_BRANCH;
            case QUESTION_ELSE_BRANCH -> result = otherBranch == QUESTION_THEN_BRANCH;
            default -> result = false;
        }
        return result;
    }

    /**
     * Collects the conditional expressions a node is assigned in, mapped to the
     * branch of the expression the node sits in.
     *
     * @param node node.
     * @return map of QUESTION tokens to branch constants.
     */
    private static Map<DetailAST, Integer> getQuestionBranches(DetailAST node) {
        final Map<DetailAST, Integer> branches = new HashMap<>();
        for (DetailAST token = node.getParent(); token != null; token = token.getParent()) {
            if (token.getType() == TokenTypes.QUESTION) {
                branches.put(token, getQuestionBranch(token, node));
            }
        }
        return branches;
    }

    /**
     * Determines the branch of a conditional expression a node sits in. The direct
     * child of the QUESTION token that contains the node decides: a child behind
     * the COLON token is the else branch, a child that starts after the question
     * mark is the then branch, and anything before it is the condition.
     *
     * @param question QUESTION token.
     * @param node node inside the conditional expression.
     * @return the branch constant for the node.
     */
    private static int getQuestionBranch(DetailAST question, DetailAST node) {
        DetailAST child = node;
        while (child.getParent() != question) {
            child = child.getParent();
        }
        int branch = QUESTION_CONDITION_BRANCH;
        boolean afterColon = false;
        for (DetailAST current = question.getFirstChild(); current != null;
                current = current.getNextSibling()) {
            if (current.getType() == TokenTypes.COLON) {
                afterColon = true;
            }
            if (current == child) {
                if (afterColon) {
                    branch = QUESTION_ELSE_BRANCH;
                }
                else if (child.getLineNo() > question.getLineNo()
                        || child.getLineNo() == question.getLineNo()
                        && child.getColumnNo() > question.getColumnNo()) {
                    branch = QUESTION_THEN_BRANCH;
                }
                break;
            }
        }
        return branch;
    }

    /**
     * Puts the variable of the candidate back into the uninitialized variables of
     * the scope that holds the candidate, if it is not there already.
     *
     * @param candidate final local variable candidate.
     */
    private void restoreUninitializedVariable(FinalVariableCandidate candidate) {
        for (ScopeData scopeData : scopeStack) {
            if (scopeData.scope.get(candidate.variableIdent.getText()) == candidate
                    && !scopeData.uninitializedVariables.contains(candidate.variableIdent)) {
                scopeData.uninitializedVariables.add(candidate.variableIdent);
                break;
            }
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
            for (DetailAST token = node; token != null; token = token.getParent()) {
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
        scopeData.prevScopeUninitializedVariables = prevScopeUninitializedVariableData;
    }

    /**
     * Update current scope data uninitialized variable according to the whole scope data.
     */
    private void updateAllUninitializedVariables() {
        final boolean hasSomeScopes = !currentScopeAssignedVariables.isEmpty();
        if (hasSomeScopes) {
            scopeStack.forEach(scopeData -> {
                updateUninitializedVariables(scopeData.prevScopeUninitializedVariables);
            });
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
     * If there is an {@code else} following or token is CASE_GROUP or
     * SWITCH_RULE and there is another {@code case} following, then update the
     * uninitialized variables.
     *
     * @param ast token to be checked
     * @return true if should be updated, else false
     */
    private static boolean shouldUpdateUninitializedVariables(DetailAST ast) {
        return ast.getLastChild().getType() == TokenTypes.LITERAL_ELSE
            || isCaseTokenWithAnotherCaseFollowing(ast);
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
            result = findLastCaseGroupWhichContainsSlist(ast.getParent()) != ast;
        }
        else if (ast.getType() == TokenTypes.SWITCH_RULE) {
            result = ast.getNextSibling().getType() == TokenTypes.SWITCH_RULE;
        }
        return result;
    }

    /**
     * Returns the last token of type {@link TokenTypes#CASE_GROUP} which contains
     * {@link TokenTypes#SLIST}.
     *
     * @param literalSwitchAst ast node of type {@link TokenTypes#LITERAL_SWITCH}
     * @return the matching token, or null if no match
     */
    private static DetailAST findLastCaseGroupWhichContainsSlist(DetailAST literalSwitchAst) {
        DetailAST returnValue = null;
        for (DetailAST astIterator = literalSwitchAst.getFirstChild(); astIterator != null;
             astIterator = astIterator.getNextSibling()) {
            if (astIterator.findFirstToken(TokenTypes.SLIST) != null) {
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
     * Determines whether unnamed variable should be checked or not.
     *
     * @param ast The ast to compare.
     * @return true if unnamed variable should be checked.
     */
    private boolean shouldCheckUnnamedVariable(DetailAST ast) {
        return validateUnnamedVariables
                 || !"_".equals(TokenUtil.getIdent(ast).getText());
    }

    /**
     * Insert a parameter at the topmost scope stack.
     *
     * @param ast parameter definition AST, but not receiver parameter.
     */
    private void insertParameter(DetailAST ast) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = TokenUtil.getIdent(ast);
        scope.put(astNode.getText(), new FinalVariableCandidate(astNode));
    }

    /**
     * Insert a variable at the topmost scope stack.
     *
     * @param variableAst the variable to insert.
     */
    private void insertVariable(DetailAST variableAst) {
        final Map<String, FinalVariableCandidate> scope = scopeStack.peek().scope;
        final DetailAST astNode = TokenUtil.getIdent(variableAst);
        final FinalVariableCandidate candidate = new FinalVariableCandidate(astNode);
        // for-each variables are implicitly assigned
        candidate.assigned = variableAst.getParent().getType() == TokenTypes.FOR_EACH_CLAUSE;
        scope.put(astNode.getText(), candidate);
        if (!isInitialized(variableAst)) {
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
        return ast.getLastChild().getType() == TokenTypes.ASSIGN;
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
                final DetailAST currAstLoopAstParent = getParentLoop(ast);
                final DetailAST currVarLoopAstParent = getParentLoop(variable);
                if (currAstLoopAstParent == currVarLoopAstParent) {
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
     * Get the ast node of type {@link FinalVariableCandidate#LOOP_TYPES} that is the ancestor
     * of the current ast node, if there is no such node, null is returned.
     *
     * @param ast ast node
     * @return ast node of type {@code FinalVariableCandidate#LOOP_TYPES} that is the ancestor
     *         of the current ast node, null if no such node exists
     */
    private static DetailAST getParentLoop(DetailAST ast) {
        DetailAST parentLoop = ast;
        while (parentLoop != null
            && !LOOP_TYPES.get(parentLoop.getType())) {
            parentLoop = parentLoop.getParent();
        }
        return parentLoop;
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
     *
     * <p>
     * {@code
     * for (int i = 0, j = 0; i < j; i++) { . . . }
     * }
     * </p>
     * {@code i, j} are defined in {@code TokenTypes#FOR_INIT for-loop init}
     *
     * @param variableDef variable definition node.
     * @return true if variable is defined in {@code TokenTypes#FOR_INIT for-loop init}
     */
    private static boolean isVariableInForInit(DetailAST variableDef) {
        return variableDef.getParent().getType() == TokenTypes.FOR_INIT;
    }

    /**
     * Checks if a parameter is within a method that has no implementation body.
     *
     * @param parameterDefAst the AST node representing the parameter definition
     * @return true if the parameter is in a method without a body
     */
    private static boolean isInMethodWithoutBody(DetailAST parameterDefAst) {
        final DetailAST methodDefAst = parameterDefAst.getParent().getParent();
        return methodDefAst.findFirstToken(TokenTypes.SLIST) == null;
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
     * Find the Class, Constructor, Enum, Method, Record, or Field in which it is defined.
     *
     * @param ast Variable for which we want to find the scope in which it is defined
     * @return ast The Class or Constructor or Method or Record in which it is defined.
     */
    private static DetailAST findFirstUpperNamedBlock(DetailAST ast) {
        DetailAST astTraverse = ast;
        while (!TokenUtil.isOfType(astTraverse, TokenTypes.METHOD_DEF, TokenTypes.CLASS_DEF,
                TokenTypes.ENUM_DEF, TokenTypes.CTOR_DEF, TokenTypes.COMPACT_CTOR_DEF,
                TokenTypes.RECORD_DEF)
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

    /** Assignment state for a conditional branch that cannot complete normally. */
    private static final class ThrowingBranch {

        /** Candidates that were unassigned before entering the branch, with their scopes. */
        private final Map<FinalVariableCandidate, ScopeData> unassigned = new HashMap<>();

        /**
         * Captures the state before entering a throwing branch.
         *
         * @param scopes enclosing scopes
         */
        private ThrowingBranch(Deque<ScopeData> scopes) {
            for (ScopeData data : scopes) {
                for (FinalVariableCandidate candidate : data.scope.values()) {
                    if (!candidate.assigned
                            && data.uninitializedVariables.contains(candidate.variableIdent)) {
                        unassigned.put(candidate, data);
                    }
                }
            }
        }

        /**
         * Checks for a block ending in a throw outside exception handlers.
         * Enclosing try statements are excluded because a handler can resume execution.
         *
         * @param ast block to inspect
         * @return whether assignments in the block cannot reach following statements
         */
        private static boolean isThrowingBranch(DetailAST ast) {
            boolean result = TokenUtil.isOfType(ast.getLastChild().getPreviousSibling(),
                            TokenTypes.LITERAL_THROW);
            for (DetailAST parent = ast; result && parent != null;
                    parent = parent.getParent()) {
                result = parent.getType() != TokenTypes.LITERAL_TRY;
            }
            return result;
        }

        /**
         * Restores assignment state on the path that does not enter the branch.
         */
        private void restore() {
            unassigned.forEach((candidate, data) -> {
                candidate.assigned = false;
                if (!data.uninitializedVariables.contains(candidate.variableIdent)) {
                    data.uninitializedVariables.add(candidate.variableIdent);
                }
            });
        }

    }

    /**
     * Holder for the scope data.
     */
    private static final class ScopeData {

        /** Contains variable definitions. */
        private final Map<String, FinalVariableCandidate> scope = new HashMap<>();

        /** Contains definitions of uninitialized variables. */
        private final Deque<DetailAST> uninitializedVariables = new ArrayDeque<>();

        /** Contains definitions of previous scope uninitialized variables. */
        private Deque<DetailAST> prevScopeUninitializedVariables = new ArrayDeque<>();

        /** Whether there is a {@code break} in the scope. */
        private boolean containsBreak;

        /**
         * Creates a new {@code ScopeData} instance.
         */
        private ScopeData() {
            // no code by default
        }

        /**
         * Searches for final local variable candidate for ast in the scope.
         *
         * @param ast ast.
         * @return Optional of {@link FinalVariableCandidate}.
         */
        /* package */ Optional<FinalVariableCandidate>
            findFinalVariableCandidateForAst(DetailAST ast) {
            Optional<FinalVariableCandidate> result = Optional.empty();
            DetailAST storedVariable = null;
            final Optional<FinalVariableCandidate> candidate =
                Optional.ofNullable(scope.get(ast.getText()));
            if (candidate.isPresent()) {
                storedVariable = candidate.orElseThrow().variableIdent;
            }
            if (storedVariable != null && isSameVariables(storedVariable, ast)) {
                result = candidate;
            }
            return result;
        }

    }

    /** Represents information about final local variable candidate. */
    private static final class FinalVariableCandidate {

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
        private FinalVariableCandidate(DetailAST variableIdent) {
            this.variableIdent = variableIdent;
        }

    }

}

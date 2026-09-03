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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks that pattern variables that never have their values changed are declared final.
 * </div>
 *
 * <p>
 * Pattern variables are not implicitly final in Java, and they can be reassigned.
 * This check verifies that pattern variables are explicitly declared as final if they
 * are not reassigned within their flow scope.
 * </p>
 *
 * @since 14.2.0
 */
@StatelessCheck
public class FinalPatternVariableCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message in "messages.properties" file.
     */
    public static final String MSG_KEY = "final.pattern.variable";

    /**
     * The set of all valid types of ASSIGN token for this check.
     */
    private static final Set<Integer> ASSIGN_TOKEN_TYPES = Set.of(
        TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN,
        TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN,
        TokenTypes.SR_ASSIGN, TokenTypes.BSR_ASSIGN, TokenTypes.SL_ASSIGN,
        TokenTypes.BAND_ASSIGN, TokenTypes.BXOR_ASSIGN, TokenTypes.BOR_ASSIGN);

    /**
     * Creates a new {@code FinalPatternVariableCheck} instance.
     */
    public FinalPatternVariableCheck() {
        // no code by default
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_INSTANCEOF};
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(final DetailAST ast) {

        final List<DetailAST> patternVariableIdents =
                getPatternVariableIdents(ast);
        final List<DetailAST> reassignedVariableIdents =
                getReassignedVariableIdents(ast);

        for (DetailAST patternVariableIdent : patternVariableIdents) {
            checkIfShouldBeFinal(patternVariableIdent,
                    reassignedVariableIdents);
        }
    }

    /**
     * Gets the list of all pattern variable idents in instanceof expression.
     *
     * @param ast ast tree of instanceof to get the list from.
     * @return list of pattern variables.
     */
    private static List<DetailAST> getPatternVariableIdents(
            final DetailAST ast) {

        final DetailAST outermostPatternVariable =
            ast.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);

        final List<DetailAST> patternVariableIdentsArray = new ArrayList<>();

        if (outermostPatternVariable != null) {
            final DetailAST ident =
                outermostPatternVariable.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                patternVariableIdentsArray.add(ident);
            }
        }
        else {
            final DetailAST recordPatternDef;
            if (ast.getType() == TokenTypes.LITERAL_INSTANCEOF) {
                recordPatternDef =
                    ast.findFirstToken(TokenTypes.RECORD_PATTERN_DEF);
            }
            else {
                recordPatternDef = ast;
            }
            collectRecordPatternIdents(recordPatternDef, patternVariableIdentsArray);
        }
        return patternVariableIdentsArray;
    }

    /**
     * Collects pattern variable identifiers from a record pattern AST definition.
     *
     * @param recordPatternDef AST of record pattern definition
     * @param patternVariableIdentsArray collection to add pattern variable identifiers to
     */
    private static void collectRecordPatternIdents(
            @Nullable final DetailAST recordPatternDef,
            final Collection<DetailAST> patternVariableIdentsArray) {
        if (recordPatternDef != null) {
            final DetailAST recordPatternComponents = recordPatternDef
                .findFirstToken(TokenTypes.RECORD_PATTERN_COMPONENTS);

            if (recordPatternComponents != null) {
                for (DetailAST innerPatternVariable =
                         recordPatternComponents.getFirstChild();
                     innerPatternVariable != null;
                     innerPatternVariable =
                         innerPatternVariable.getNextSibling()) {

                    if (innerPatternVariable.getType()
                            == TokenTypes.PATTERN_VARIABLE_DEF) {
                        final DetailAST ident =
                            innerPatternVariable.findFirstToken(
                                TokenTypes.IDENT);
                        if (ident != null) {
                            patternVariableIdentsArray.add(ident);
                        }
                    }
                    else {
                        patternVariableIdentsArray.addAll(
                            getPatternVariableIdents(innerPatternVariable));
                    }
                }
            }
        }
    }

    /**
     * Gets the list of AST branches of reassigned variable identifiers.
     *
     * @param ast ast tree of checked instanceof statement
     * @return list of AST identifiers that represent reassigned variables
     */
    private static List<DetailAST> getReassignedVariableIdents(
            final DetailAST ast) {

        final List<DetailAST> reassignedVariableIdents = new ArrayList<>();
        final DetailAST scopeRoot = findReassignmentScopeRoot(ast);

        if (scopeRoot != null) {

            final List<DetailAST> branches =
                    expandReassignmentScopes(scopeRoot);

            for (DetailAST branch : branches) {
                for (DetailAST expressionBranch = branch;
                     expressionBranch != null;
                     expressionBranch = shiftToNextTraversedBranch(
                             expressionBranch, branch)) {

                    final DetailAST assignToken =
                            getMatchedAssignToken(expressionBranch);

                    if (assignToken != null) {
                        reassignedVariableIdents.add(
                                assignToken.getFirstChild());
                    }
                }
            }
        }

        return reassignedVariableIdents;
    }

    /**
     * Gets statements that follow conditional where pattern variable scope
     * extends. Only returns top-level statements that are siblings of the
     * conditional, excluding statements nested in control structures like
     * while loops.
     *
     * @param conditionalStatement The if statement.
     * @return List of statement nodes in the extended scope.
     */
    private static List<DetailAST> getStatementsInExtendedScope(
            final DetailAST conditionalStatement) {
        final List<DetailAST> statements = new ArrayList<>();

        DetailAST nextSibling = conditionalStatement.getNextSibling();

        while (nextSibling != null) {
            final int type = nextSibling.getType();
            if (type == TokenTypes.EXPR || type == TokenTypes.LITERAL_RETURN
                    || type == TokenTypes.LITERAL_IF) {
                statements.add(nextSibling);
            }
            else {
                if (type != TokenTypes.SEMI) {
                    break;
                }
            }
            nextSibling = nextSibling.getNextSibling();
        }

        return statements;
    }

    /**
     * Shifts once to the next possible branch within traverse trajectory.
     *
     * @param ast AST branch to shift from.
     * @param boundAst AST Branch that traverse cannot extend to.
     * @return next possible branch within traverse trajectory.
     */
    @Nullable
    private static DetailAST shiftToNextTraversedBranch(
            final DetailAST ast, final DetailAST boundAst) {
        DetailAST newAst = ast;

        if (ast.getFirstChild() != null) {
            newAst = ast.getFirstChild();
        }
        else {
            while (newAst.getNextSibling() == null
                    && !newAst.equals(boundAst)) {
                newAst = newAst.getParent();
            }
            if (newAst.equals(boundAst)) {
                newAst = null;
            }
            else {
                newAst = newAst.getNextSibling();
            }
        }

        return newAst;
    }

    /**
     * Gets the type of ASSIGN tokens that matches with what follows the
     * preceding branch.
     *
     * @param preAssignBranch branch preceding ASSIGN token types.
     * @return type of ASSIGN token.
     */
    @Nullable
    private static DetailAST getMatchedAssignToken(
            final DetailAST preAssignBranch) {
        DetailAST matchedAssignToken = null;

        for (int assignType : ASSIGN_TOKEN_TYPES) {
            matchedAssignToken = preAssignBranch.findFirstToken(assignType);
            if (matchedAssignToken != null) {
                break;
            }
        }

        return matchedAssignToken;
    }

    /**
     * Checks whether a pattern variable should be declared final, and logs
     * a violation if it is not declared final and never reassigned.
     *
     * @param patternVariableIdent AST ident of the pattern variable
     * @param reassignedVariableIdents list of AST idents of reassigned vars
     */
    private void checkIfShouldBeFinal(
            final DetailAST patternVariableIdent,
            final Iterable<DetailAST> reassignedVariableIdents) {

        final DetailAST patternVariableDef = patternVariableIdent.getParent();
        final DetailAST modifiers =
                patternVariableDef.findFirstToken(TokenTypes.MODIFIERS);
        final boolean isFinal = modifiers != null
                && modifiers.findFirstToken(TokenTypes.FINAL) != null;

        if (!isFinal) {
            boolean isReassigned = false;
            for (DetailAST assignTokenIdent : reassignedVariableIdents) {
                if (patternVariableIdent.getText().equals(
                        assignTokenIdent.getText())) {
                    isReassigned = true;
                    break;
                }
            }
            if (!isReassigned) {
                log(patternVariableIdent, MSG_KEY,
                        patternVariableIdent.getText());
            }
        }
    }

    /**
     * Finds the nearest AST node that defines the scope in which reassignment
     * of a pattern variable may occur.
     *
     * <p>
     * The scope is determined by locating the closest enclosing conditional
     * structure such as {@code if}, {@code else}, or ternary operator.
     * </p>
     *
     * @param ast the AST node to start searching from
     * @return the AST node representing the reassignment scope root,
     *         or {@code null} if none is found
     */
    @Nullable
    private static DetailAST findReassignmentScopeRoot(final DetailAST ast) {

        DetailAST result = null;

        for (DetailAST node = ast; node != null && result == null;
             node = node.getParent()) {

            final int type = node.getType();

            if (type == TokenTypes.LITERAL_IF
                    || type == TokenTypes.LITERAL_ELSE
                    || type == TokenTypes.QUESTION) {
                result = node;
            }
        }

        return result;
    }

    /**
     * Expands the reassignment scope root into concrete AST branches
     * that may contain reassigned pattern variables.
     *
     * <p>
     * For ternary expressions, the conditional expression itself is
     * treated as the reassignment scope. For {@code if} / {@code else}
     * statements, the method includes the statement list and any
     * subsequent statements where the pattern variable remains in scope.
     * </p>
     *
     * @param scopeRoot the root AST node of the reassignment scope
     * @return list of AST branches that may contain reassignments
     */
    private static List<DetailAST> expandReassignmentScopes(
            final DetailAST scopeRoot) {

        final List<DetailAST> branches = new ArrayList<>();

        addBodyBranch(branches, scopeRoot);
        branches.addAll(getStatementsInExtendedScope(scopeRoot));

        return branches;
    }

    /**
     * Adds the body branch of a conditional (if/else) to the list.
     * For braced blocks, adds the SLIST. For unbraced statements,
     * adds the single statement directly.
     *
     * @param branches collection to add the body branch to
     * @param scopeRoot the if/else AST node
     */
    private static void addBodyBranch(final Collection<DetailAST> branches,
            final DetailAST scopeRoot) {
        if (scopeRoot.getType() == TokenTypes.LITERAL_IF) {
            final DetailAST body = TokenUtil.findFirstTokenByPredicate(
                    scopeRoot, node -> node.getType() == TokenTypes.RPAREN)
                    .map(DetailAST::getNextSibling)
                    .orElse(scopeRoot);
            branches.add(body);
        }
        else {
            branches.add(scopeRoot);
        }
    }

}

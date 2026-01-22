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
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks for assignment of pattern variables.
 * </div>
 *
 * <p>
 * Pattern variable assignment is considered bad programming practice. The pattern variable
 * is meant to be a direct reference to the object being matched. Reassigning it can break this
 * connection and mislead readers.
 * </p>
 *
 * @since 10.26.0
 */
@StatelessCheck
public class PatternVariableAssignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message in "messages.properties" file.
     */
    public static final String MSG_KEY = "pattern.variable.assignment";

    /**
     * The set of all valid types of ASSIGN token for this check.
     */
    private static final Set<Integer> ASSIGN_TOKEN_TYPES = Set.of(
        TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.SR_ASSIGN, TokenTypes.BSR_ASSIGN,
        TokenTypes.SL_ASSIGN, TokenTypes.BAND_ASSIGN, TokenTypes.BXOR_ASSIGN,
        TokenTypes.BOR_ASSIGN);

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
    public void visitToken(DetailAST ast) {

        final List<DetailAST> patternVariableIdents = getPatternVariableIdents(ast);
        final List<DetailAST> reassignedVariableIdents = getReassignedVariableIdents(ast);

        for (DetailAST patternVariableIdent : patternVariableIdents) {
            for (DetailAST assignTokenIdent : reassignedVariableIdents) {
                if (patternVariableIdent.getText().equals(assignTokenIdent.getText())) {

                    log(assignTokenIdent, MSG_KEY, assignTokenIdent.getText());
                    break;
                }

            }
        }
    }

    /**
     * Gets the list of all pattern variable idents in instanceof expression.
     *
     * @param ast ast tree of instanceof to get the list from.
     * @return list of pattern variables.
     */
    private static List<DetailAST> getPatternVariableIdents(DetailAST ast) {

        final DetailAST outermostPatternVariable =
            ast.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF);

        final DetailAST recordPatternDef;
        if (ast.getType() == TokenTypes.LITERAL_INSTANCEOF) {
            recordPatternDef = ast.findFirstToken(TokenTypes.RECORD_PATTERN_DEF);
        }
        else {
            recordPatternDef = ast;
        }

        final List<DetailAST> patternVariableIdentsArray = new ArrayList<>();

        if (outermostPatternVariable != null) {
            patternVariableIdentsArray.add(
                outermostPatternVariable.findFirstToken(TokenTypes.IDENT));
        }
        else if (recordPatternDef != null) {
            final DetailAST recordPatternComponents = recordPatternDef
                .findFirstToken(TokenTypes.RECORD_PATTERN_COMPONENTS);

            if (recordPatternComponents != null) {
                for (DetailAST innerPatternVariable = recordPatternComponents.getFirstChild();
                     innerPatternVariable != null;
                     innerPatternVariable = innerPatternVariable.getNextSibling()) {

                    if (innerPatternVariable.getType() == TokenTypes.PATTERN_VARIABLE_DEF) {
                        patternVariableIdentsArray.add(
                            innerPatternVariable.findFirstToken(TokenTypes.IDENT));
                    }
                    else {
                        patternVariableIdentsArray.addAll(
                            getPatternVariableIdents(innerPatternVariable));
                    }

                }
            }

        }
        return patternVariableIdentsArray;
    }

    /**
     * Gets the array list made out of AST branches of reassigned variable idents.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return the list of AST branches of reassigned variable idents.
     */
    private static List<DetailAST> getReassignedVariableIdents(DetailAST ast) {

        final DetailAST branchLeadingToReassignedVar = getBranchLeadingToReassignedVars(ast);
        final List<DetailAST> reassignedVariableIdents = new ArrayList<>();

        for (DetailAST expressionBranch = branchLeadingToReassignedVar;
             expressionBranch != null;
             expressionBranch = traverseUntilNeededBranchType(expressionBranch,
                 branchLeadingToReassignedVar, TokenTypes.EXPR)) {

            final DetailAST assignToken = getMatchedAssignToken(expressionBranch);

            if (assignToken != null) {
                final DetailAST neededAssignIdent = getNeededAssignIdent(assignToken);
                if (neededAssignIdent.getPreviousSibling() == null) {
                    reassignedVariableIdents.add(getNeededAssignIdent(assignToken));
                }
            }
        }

        return reassignedVariableIdents;

    }

    /**
     * Gets the closest consistent AST branch that leads to reassigned variable's ident.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return the closest consistent AST branch that leads to reassigned variable's ident.
     */
    @Nullable
    private static DetailAST getBranchLeadingToReassignedVars(DetailAST ast) {
        DetailAST leadingToReassignedVarBranch = null;

        for (DetailAST conditionalStatement = ast;
             conditionalStatement != null && leadingToReassignedVarBranch == null;
             conditionalStatement = conditionalStatement.getParent()) {

            if (conditionalStatement.getType() == TokenTypes.LITERAL_IF
                || conditionalStatement.getType() == TokenTypes.LITERAL_ELSE) {

                leadingToReassignedVarBranch =
                    conditionalStatement.findFirstToken(TokenTypes.SLIST);

            }
            else if (conditionalStatement.getType() == TokenTypes.QUESTION) {
                leadingToReassignedVarBranch = conditionalStatement;
            }
        }

        return leadingToReassignedVarBranch;

    }

    /**
     * Traverses along the AST tree to locate the first branch of certain token type.
     *
     * @param startingBranch AST branch to start the traverse from, but not check.
     * @param bound AST Branch that the traverse cannot further extend to.
     * @param neededTokenType Token type whose first encountered branch is to look for.
     * @return the AST tree of first encountered branch of needed token type.
     */
    @Nullable
    private static DetailAST traverseUntilNeededBranchType(DetailAST startingBranch,
                              DetailAST bound, int neededTokenType) {

        DetailAST match = null;

        DetailAST iteratedBranch = shiftToNextTraversedBranch(startingBranch, bound);

        while (iteratedBranch != null) {
            if (iteratedBranch.getType() == neededTokenType) {
                match = iteratedBranch;
                break;
            }

            iteratedBranch = shiftToNextTraversedBranch(iteratedBranch, bound);
        }

        return match;
    }

    /**
     * Shifts once to the next possible branch within traverse trajectory.
     *
     * @param ast AST branch to shift from.
     * @param boundAst AST Branch that the traverse cannot further extend to.
     * @return the AST tree of next possible branch within traverse trajectory.
     */
    @Nullable
    private static DetailAST shiftToNextTraversedBranch(DetailAST ast, DetailAST boundAst) {
        DetailAST newAst = ast;

        if (ast.getFirstChild() != null) {
            newAst = ast.getFirstChild();
        }
        else {
            while (newAst.getNextSibling() == null && !newAst.equals(boundAst)) {
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
     * Gets the type of ASSIGN tokens that particularly matches with what follows the preceding
     * branch.
     *
     * @param preAssignBranch branch that precedes the branch of ASSIGN token types.
     * @return type of ASSIGN token.
     */
    @Nullable
    private static DetailAST getMatchedAssignToken(DetailAST preAssignBranch) {
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
     * Gets the needed AST Ident of reassigned variable for check to compare.
     *
     * @param assignToken The AST branch of reassigned variable's ASSIGN token.
     * @return needed AST Ident.
     */
    private static DetailAST getNeededAssignIdent(DetailAST assignToken) {
        DetailAST assignIdent = assignToken;

        while (traverseUntilNeededBranchType(
            assignIdent, assignToken.getFirstChild(), TokenTypes.IDENT) != null) {

            assignIdent =
                traverseUntilNeededBranchType(assignIdent, assignToken, TokenTypes.IDENT);
        }

        return assignIdent;
    }
}

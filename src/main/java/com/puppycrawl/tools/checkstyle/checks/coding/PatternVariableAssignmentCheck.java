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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code pattern.variable.assignment}
 * </li>
 * </ul>
 *
 * @since 10.25.0
 */
@StatelessCheck
public class PatternVariableAssignmentCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message in "messages.properties" file.
     */
    public static final String MSG_KEY = "pattern.variable.assignment";

    /**
     * The array list of all valid types of ASSIGN token for this check.
     */
    private static final List<Integer> ASSIGN_TOKEN_TYPES = new ArrayList<>(Arrays.asList(
        TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.MINUS_ASSIGN, TokenTypes.STAR_ASSIGN,
        TokenTypes.DIV_ASSIGN, TokenTypes.MOD_ASSIGN));

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

        final List<DetailAST> patternVariables = getArrayOfPatternVariables(ast);

        if (!patternVariables.isEmpty()) {
            final List<DetailAST> reassignedVariables = getArrayOfReassignedVariables(ast);

            if (!reassignedVariables.isEmpty()) {
                for (DetailAST patternVariable : patternVariables) {
                    final DetailAST patternVariableIdent = patternVariable.findFirstToken(
                        TokenTypes.IDENT);

                    for (int reassignedVarIndex = 0;
                         reassignedVarIndex < reassignedVariables.size(); reassignedVarIndex++) {

                        final DetailAST assignToken = reassignedVariables.get(reassignedVarIndex);

                        final DetailAST reassignedVariableIdent = getNeededAssignIdent(assignToken);

                        if (patternVariableIdent.getText().equals(reassignedVariableIdent
                            .getText())) {

                            log(reassignedVariableIdent, MSG_KEY, reassignedVariableIdent
                                .getText());
                            reassignedVariables.remove(reassignedVariableIdent);
                            break;
                        }

                    }
                }

            }

        }

    }

    /**
     * Gets the needed AST Ident of reassigned variable for check to compare.
     *
     * @param assignToken The AST branch of reassigned variable's ASSIGN token.
     * @return needed AST Ident.
     */
    private DetailAST getNeededAssignIdent(DetailAST assignToken) {
        DetailAST assignIdent = traverseUntilNeededBranchType(assignToken.getFirstChild(),
            assignToken, TokenTypes.IDENT, true);

        if (assignIdent.getParent().getType() == TokenTypes.DOT) {
            for (DetailAST iteratedBranch = traverseUntilNeededBranchType(
                    assignIdent, assignToken.getFirstChild(), TokenTypes.IDENT, false);
                 iteratedBranch != null;
                 iteratedBranch = traverseUntilNeededBranchType(iteratedBranch,
                     assignToken.getFirstChild(), TokenTypes.IDENT, false)) {

                assignIdent = iteratedBranch;
            }
        }

        return assignIdent;
    }

    /**
     * Gets the list of all pattern variables in instanceof expression.
     *
     * @param ast ast tree of instanceof to get the lsit from.
     * @return list of pattern variables.
     */
    private List<DetailAST> getArrayOfPatternVariables(DetailAST ast) {

        final DetailAST outermostPatternVariable = ast.findFirstToken(
            TokenTypes.PATTERN_VARIABLE_DEF);

        final DetailAST recordPatternDef;
        if (ast.getType() == TokenTypes.LITERAL_INSTANCEOF) {
            recordPatternDef = ast.findFirstToken(TokenTypes.RECORD_PATTERN_DEF);
        }
        else {
            recordPatternDef = ast;
        }

        final List<DetailAST> patternVariablesArray = new ArrayList<>();

        if (outermostPatternVariable != null) {
            patternVariablesArray.add(outermostPatternVariable);
        }
        else if (recordPatternDef != null) {
            final DetailAST recordPatternComponents = recordPatternDef
                .findFirstToken(TokenTypes.RECORD_PATTERN_COMPONENTS);

            if (recordPatternComponents != null) {
                for (DetailAST innerPatternVariable = recordPatternComponents.getFirstChild();
                     innerPatternVariable != null;
                     innerPatternVariable = innerPatternVariable.getNextSibling()) {

                    if (innerPatternVariable.getType() == TokenTypes.PATTERN_VARIABLE_DEF) {
                        patternVariablesArray.add(innerPatternVariable);
                    }
                    else if (innerPatternVariable.getType() == TokenTypes.RECORD_PATTERN_DEF) {
                        patternVariablesArray.addAll(getArrayOfPatternVariables(
                            innerPatternVariable));
                    }

                }
            }

        }
        return patternVariablesArray;
    }

    /**
     * Gets the array list made out of AST branches of reassigned variables.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return the list of AST branches of reassigned variables.
     */
    private List<DetailAST> getArrayOfReassignedVariables(DetailAST ast) {

        final DetailAST branchLeadingToReassignedVar = getBranchLeadingToReassignedVars(ast);
        final List<DetailAST> reassignedVariables = new ArrayList<>();

        if (branchLeadingToReassignedVar != null) {
            if (ASSIGN_TOKEN_TYPES.contains(branchLeadingToReassignedVar.getType())) {
                reassignedVariables.add(branchLeadingToReassignedVar);
            }

            for (DetailAST startingBranch = traverseUntilNeededBranchType(
                branchLeadingToReassignedVar.getFirstChild(), branchLeadingToReassignedVar,
                TokenTypes.EXPR, true);
                 startingBranch != null;
                 startingBranch = traverseUntilNeededBranchType(startingBranch,
                     branchLeadingToReassignedVar, TokenTypes.EXPR, false)) {

                final DetailAST assignToken = getMatchedAssignToken(startingBranch);

                if (assignToken != null) {
                    reassignedVariables.add(assignToken);
                }

            }

        }

        return reassignedVariables;

    }

    /**
     * Traverses along the AST tree to locate the first branch of certain token type.
     *
     * @param startingBranch AST branch to start the traverse from.
     * @param bound AST Branch that the traverse cannot further extend to.
     * @param neededTokenType Token type whose first encountered branch is to look for.
     * @param checkStartingBranch Whether to check the starting branch for needed token type.
     * @return the AST tree of first encountered branch of needed token type.
     */
    private static DetailAST traverseUntilNeededBranchType(DetailAST startingBranch,
                              DetailAST bound, int neededTokenType, boolean checkStartingBranch) {

        DetailAST match = null;

        if (checkStartingBranch) {
            if (startingBranch.getType() == neededTokenType) {
                match = startingBranch;
            }
        }

        DetailAST iteratedBranch = shiftToNextTraversedBranch(startingBranch, bound);

        while (match == null && iteratedBranch != null) {
            if (iteratedBranch.getType() == neededTokenType) {
                match = iteratedBranch;
                break;
            }
            else if (iteratedBranch.findFirstToken(neededTokenType) != null) {
                match = iteratedBranch.findFirstToken(neededTokenType);
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
    private static DetailAST shiftToNextTraversedBranch(DetailAST ast, DetailAST boundAst) {
        DetailAST newAst = ast;

        if (ast.getFirstChild() != null) {
            newAst = ast.getFirstChild();
        }
        else if (ast.getFirstChild() == null && ast.getNextSibling() != null) {
            newAst = ast.getNextSibling();
        }
        else {
            while (newAst.getNextSibling() == null && newAst != boundAst) {
                newAst = newAst.getParent();
            }
            if (newAst == boundAst) {
                newAst = null;
            }
            else {
                newAst = newAst.getNextSibling();
            }
        }

        return newAst;
    }

    /**
     * Gets the closest consistent AST branch that leads to reassigned variable's ident.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return the closest consistent AST branch that leads to reassigned variable's ident.
     */
    private static DetailAST getBranchLeadingToReassignedVars(DetailAST ast) {
        DetailAST conditionalStatement = ast.getParent();

        while (conditionalStatement != null
            && conditionalStatement.getType() != TokenTypes.LITERAL_IF
            && conditionalStatement.getType() != TokenTypes.QUESTION) {

            conditionalStatement = conditionalStatement.getParent();

            if (conditionalStatement != null && (conditionalStatement.getType() == TokenTypes.ASSIGN
                || conditionalStatement.getType() == TokenTypes.SLIST)) {
                conditionalStatement = null;
            }
        }

        DetailAST leadingToReassignedVarBranch = conditionalStatement;

        if (leadingToReassignedVarBranch != null) {
            if (conditionalStatement.getType() == TokenTypes.LITERAL_IF) {
                leadingToReassignedVarBranch = conditionalStatement.findFirstToken(
                    TokenTypes.SLIST);
            }
            else if (conditionalStatement.getType() == TokenTypes.QUESTION) {
                leadingToReassignedVarBranch = getMatchedAssignToken(leadingToReassignedVarBranch);
            }
        }

        return leadingToReassignedVarBranch;

    }

    /**
     * Gets the type of ASSIGN tokens that particularly matches with what follows the preceding
     * branch.
     *
     * @param preAssignBranch branch that precedes the branch of ASSIGN token types.
     * @return type of ASSIGN token.
     */
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

}

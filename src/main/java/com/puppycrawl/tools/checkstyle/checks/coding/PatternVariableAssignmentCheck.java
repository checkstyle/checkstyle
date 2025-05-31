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
            List<DetailAST> reassignedVariables = getArrayOfReassignedVariables(ast);

            if (!reassignedVariables.isEmpty()) {
                for (DetailAST patternVariable : patternVariables) {
                    DetailAST patternVariableIdent = patternVariable.findFirstToken(TokenTypes.IDENT);
                    for (int i = 0; i < reassignedVariables.size(); i++) {
                        DetailAST reassignedVariableIdent = reassignedVariables.get(i)
                            .findFirstToken(TokenTypes.IDENT);
                        if (patternVariableIdent.getText().equals(reassignedVariableIdent.getText())) {
                            log(reassignedVariableIdent, MSG_KEY, reassignedVariableIdent.getText());
                            reassignedVariables.remove(i);
                            break;
                        }
                    }
                }
            }

        }

    }

    /**
     * Gets the list of all pattern variables in instanceof expression.
     *
     * @param ast ast tree of instanceof to get the lsit from.
     * @return list of pattern variables.
     */
    private List<DetailAST> getArrayOfPatternVariables(DetailAST ast) {
        final List<DetailAST> patternVariablesArray = new ArrayList<>();

        final DetailAST outermostPatternVariable = ast.findFirstToken(
            TokenTypes.PATTERN_VARIABLE_DEF);

        final DetailAST recordPatternDef;
        if (ast.getType() == TokenTypes.LITERAL_INSTANCEOF) {
            recordPatternDef = ast.findFirstToken(TokenTypes.RECORD_PATTERN_DEF);
        }
        else {
            recordPatternDef = ast;
        }

        if (outermostPatternVariable != null) {
            patternVariablesArray.add(outermostPatternVariable);
        }
        else if (recordPatternDef != null) {
            final DetailAST recordPatternComponents = recordPatternDef
                .findFirstToken(TokenTypes.RECORD_PATTERN_COMPONENTS);

            for (DetailAST innerPatternVariable = recordPatternComponents.getFirstChild();
                 innerPatternVariable != null;
                 innerPatternVariable = innerPatternVariable.getNextSibling()) {

                if (innerPatternVariable.getType() == TokenTypes.PATTERN_VARIABLE_DEF) {
                    patternVariablesArray.add(innerPatternVariable);
                }
                else if (innerPatternVariable.getType() == TokenTypes.RECORD_PATTERN_DEF) {
                    patternVariablesArray.addAll(getArrayOfPatternVariables(innerPatternVariable));
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
    public List<DetailAST> getArrayOfReassignedVariables(DetailAST ast) {

        final DetailAST leadingToIdentBranch = getBranchLeadingToReassignedVarIdent(ast);
        List<DetailAST> reassignedVariables = new ArrayList<>();

        if (leadingToIdentBranch != null) {
            if (getTypesOfAssignTokens().contains(leadingToIdentBranch.getType())) {
                reassignedVariables.add(leadingToIdentBranch);
            }
            else if (leadingToIdentBranch.getType() == TokenTypes.SLIST) {
                DetailAST iteratedBranch = leadingToIdentBranch.getFirstChild();

                while (iteratedBranch != null && iteratedBranch.getLineNo() != leadingToIdentBranch.getLineNo()) {
                    if (iteratedBranch.getType() == TokenTypes.EXPR) {
                        final DetailAST assignToken = getMatchedAssignToken(iteratedBranch);

                        if (assignToken != null) {
                            reassignedVariables.add(assignToken);
                        }
                    }

                    if (iteratedBranch.getFirstChild() != null) {
                        iteratedBranch = iteratedBranch.getFirstChild();
                    }
                    else if (iteratedBranch.getFirstChild() == null && iteratedBranch.getNextSibling() != null) {
                        iteratedBranch = iteratedBranch.getNextSibling();
                    }
                    else {
                        while (iteratedBranch.getNextSibling() == null && iteratedBranch != leadingToIdentBranch) {
                            iteratedBranch = iteratedBranch.getParent();
                        }
                        if (iteratedBranch.getLineNo() != leadingToIdentBranch.getLineNo()) {
                            iteratedBranch = iteratedBranch.getNextSibling();
                        }
                    }

                }
            }

        }

        return reassignedVariables;

    }

    /**
     * Gets the closest consistent AST branch that leads to reassigned variable's ident.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return the closest consistent AST branch that leads to reassigned variable's ident.
     */
    public static DetailAST getBranchLeadingToReassignedVarIdent(DetailAST ast) {
        DetailAST conditionalStatement = ast.getParent();

        while (conditionalStatement != null
            && conditionalStatement.getType() != TokenTypes.LITERAL_IF
            && conditionalStatement.getType() != TokenTypes.QUESTION) {

            conditionalStatement = conditionalStatement.getParent();

            if (conditionalStatement.getType() == TokenTypes.SLIST) {
                conditionalStatement = null;
            }
        }

        DetailAST leadingToIdentBranch = conditionalStatement;

        if (leadingToIdentBranch != null) {
            if (conditionalStatement.getType() == TokenTypes.LITERAL_IF) {
                leadingToIdentBranch = conditionalStatement.findFirstToken(TokenTypes.SLIST);
            }
            else if (conditionalStatement.getType() == TokenTypes.QUESTION) {
                leadingToIdentBranch = getMatchedAssignToken(leadingToIdentBranch);
            }
        }

        return leadingToIdentBranch;

    }

    /**
     * Gets the type of ASSIGN tokens that particularly matches with what follows the preceding
     * branch.
     *
     * @param preAssignBranch branch that precedes the branch of ASSIGN token types.
     * @return type of ASSIGN token.
     */
    public static DetailAST getMatchedAssignToken(DetailAST preAssignBranch) {
        final List<Integer> assignTypes = getTypesOfAssignTokens();
        DetailAST matchedAssignToken = null;

        for (int assignType : assignTypes) {
            matchedAssignToken = preAssignBranch.findFirstToken(assignType);
            if (matchedAssignToken != null) {
                break;
            }
        }

        return matchedAssignToken;
    }

    /**
     * Gets the array list of all valid types of ASSIGN token for this check.
     *
     * @return array of valid ASSIGN token types.
     */
    public static List<Integer> getTypesOfAssignTokens() {
        return new ArrayList<>(Arrays.asList(TokenTypes.ASSIGN, TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN, TokenTypes.STAR_ASSIGN, TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN));
    }

}

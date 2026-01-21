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
     * Gets array list made out of AST branches of reassigned variable idents.
     *
     * @param ast ast tree of checked instanceof statement.
     * @return list of AST branches of reassigned variable idents.
     */
    private static List<DetailAST> getReassignedVariableIdents(DetailAST ast) {

        // Get pattern variables first
        final List<DetailAST> patternVariableIdents = getPatternVariableIdents(ast);
        final List<DetailAST> reassignedVariableIdents = new ArrayList<>();

        // Handle all assignment contexts, not just the first one found
        final List<DetailAST> allAssignments = findAllAssignments(ast);
        
        for (DetailAST assignment : allAssignments) {
            if (isPatternVariableAssignment(assignment, patternVariableIdents)) {
                reassignedVariableIdents.add(assignment.findFirstToken(TokenTypes.IDENT));
            }
        }

        return reassignedVariableIdents;
    }

    /**
     * Finds all assignment tokens within given AST scope.
     *
     * @param ast AST to search within
     * @return list of all assignment tokens found
     */
    private static List<DetailAST> findAllAssignments(DetailAST ast) {
        final List<DetailAST> assignments = new ArrayList<>();
        findAllAssignmentsRecursive(ast, ast, assignments);
        return assignments;
    }

    /**
     * Recursively finds all assignment tokens within given scope.
     *
     * @param current current AST node to check
     * @param boundary boundary AST node (usually the instanceof statement)
     * @param assignments list to collect found assignments
     */
    private static void findAllAssignmentsRecursive(DetailAST current, DetailAST boundary, 
                                                  List<DetailAST> assignments) {
        if (current == null || current.equals(boundary)) {
            return;
        }

        // Check if current node is an assignment
        if (isAssignmentToken(current.getType())) {
            assignments.add(current);
        }

        // Recursively check children
        for (DetailAST child = current.getFirstChild(); child != null; child = child.getNextSibling()) {
            findAllAssignmentsRecursive(child, boundary, assignments);
        }
    }

    /**
     * Checks if the given token type is an assignment operator.
     *
     * @param tokenType token type to check
     * @return true if it's an assignment token, false otherwise
     */
    private static boolean isAssignmentToken(int tokenType) {
        return ASSIGN_TOKEN_TYPES.contains(tokenType);
    }

    /**
     * Checks if the given assignment is for a pattern variable.
     *
     * @param assignment the assignment AST node
     * @param patternVariables list of pattern variable identifiers
     * @return true if assignment targets a pattern variable, false otherwise
     */
    private static boolean isPatternVariableAssignment(DetailAST assignment, 
                                               List<DetailAST> patternVariables) {
        final DetailAST assignIdent = assignment.findFirstToken(TokenTypes.IDENT);
        if (assignIdent == null) {
            return false;
        }
        
        final String assignIdentName = assignIdent.getText();
        return patternVariables.stream()
                .anyMatch(patternVar -> patternVar.getText().equals(assignIdentName));
    }
}

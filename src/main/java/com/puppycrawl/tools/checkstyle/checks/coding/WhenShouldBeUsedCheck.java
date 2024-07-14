///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Ensures that {@code when} is used instead of a single {@code if}
 * statement inside a case block.
 * </p>
 * <p>
 * Rationale: Java 21 has introduced enhancements for switch statements and expressions
 * that allow the use of patterns in case labels. The {@code when} keyword is used to specify
 * condition for a case label, also called as guarded case labels. This syntax is more readable
 * and concise than the single {@code if} statement inside the pattern match block.
 * </p>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-14.html#jls-Guard">
 * Java Language Specification</a> for more information about guarded case labels.
 * </p>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-14.html#jls-14.30">
 * Java Language Specification</a> for more information about patterns.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code when.should.be.used}
 * </li>
 * </ul>
 *
 * @since 10.18.0
 */

@StatelessCheck
public class WhenShouldBeUsedCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "when.should.be.used";

    /**
     *  An array of acceptable statements inside the case block.
     */
    private static final int[] ACCEPTABLE_STATEMENTS_INSIDE_CASE_BLOCK = {
        TokenTypes.LITERAL_IF,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.EMPTY_STAT,
        TokenTypes.RCURLY,
    };

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_CASE};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final boolean hasPatternLabel = hasPatternLabel(ast);
        final DetailAST statementList = getStatementList(ast);

        if (hasPatternLabel && statementList != null) {
            final List<DetailAST> blockStatements = getBlockStatements(statementList);

            final boolean hasAcceptableStatementsOnly = blockStatements.stream()
                .allMatch(WhenShouldBeUsedCheck::isAcceptableStatement);

            final boolean hasSingleIfWithNoElse = blockStatements.stream()
                    .filter(WhenShouldBeUsedCheck::isSingleIfWithNoElse)
                    .count() == 1;

            if (hasAcceptableStatementsOnly && hasSingleIfWithNoElse) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Get the statement list token of the case block.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return the AST node representing {@code SLIST} of the current case
     */
    private static DetailAST getStatementList(DetailAST caseAST) {
        final DetailAST caseParent = caseAST.getParent();
        final DetailAST statementList = caseParent.findFirstToken(TokenTypes.SLIST);

        return Optional.ofNullable(statementList)
               .map(ast -> ast.findFirstToken(TokenTypes.SLIST))
               .orElse(statementList);
    }

    /**
     * Get all statements inside the case block.
     *
     * @param statementList the AST node representing {@code SLIST} of the current case
     * @return statements inside the current case block
     */
    private static List<DetailAST> getBlockStatements(DetailAST statementList) {
        final List<DetailAST> blockStatements = new ArrayList<>();
        DetailAST ast = statementList.getFirstChild();
        while (ast != null) {
            blockStatements.add(ast);
            ast = ast.getNextSibling();
        }
        return Collections.unmodifiableList(blockStatements);
    }

    /**
     * Check if the statement is an acceptable statement inside the case block.
     *
     * @param ast the AST node representing the statement
     * @return true if the statement is an acceptable statement, false otherwise
     */
    private static boolean isAcceptableStatement(DetailAST ast) {
        return TokenUtil.isOfType(ast, ACCEPTABLE_STATEMENTS_INSIDE_CASE_BLOCK);
    }

    /**
     * Check if the case block has a pattern variable definition
     * or a record pattern definition.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if the case block has a pattern label, false otherwise
     */
    private static boolean hasPatternLabel(DetailAST caseAST) {
        return caseAST.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF) != null
                || caseAST.findFirstToken(TokenTypes.RECORD_PATTERN_DEF) != null
                || caseAST.findFirstToken(TokenTypes.PATTERN_DEF) != null;
    }

    /**
     * Check if the case block statement is a single if statement with no else branch.
     *
     * @param statement statement to check inside the current case block
     * @return true if the statement is a single if statement with no else branch, false otherwise
     */
    private static boolean isSingleIfWithNoElse(DetailAST statement) {
        return statement.getType() == TokenTypes.LITERAL_IF
                && statement.findFirstToken(TokenTypes.LITERAL_ELSE) == null;
    }

}

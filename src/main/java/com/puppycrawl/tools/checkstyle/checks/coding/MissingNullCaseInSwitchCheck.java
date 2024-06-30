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
 * Checks that a given switch statement or expression that use a reference type in its selector
 * expression has a {@code null} case label.
 * </p>
 * <p>
 * Rationale: switch statements and expressions in Java throw a
 * {@code NullPointerException} if the selector expression evaluates to {@code null}.
 * As of Java 21,
 * it is now possible to integrate a null check within the switch,
 * eliminating the risk of {@code NullPointerException} and simplifies the code
 * as there is no need for an external null check before entering the switch.
 * </p>
 * <p>
 * Limitations due to Checkstyle not being type-aware:
 * </p>
 * <ul>
 * <li>
 * This check does not support enums; syntactically, these are no different from other constants.
 * </li>
 * </ul>
 * <p>
 * See the <a href="https://docs.oracle.com/javase/specs/jls/se22/html/jls-15.html#jls-15.28">
 * Java Language Specification</a> for more information about switch statements and expressions.
 * </p>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code missing.switch.nullcase}
 * </li>
 * </ul>
 *
 * @since 10.19.0
 */

@StatelessCheck
public class MissingNullCaseInSwitchCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "missing.switch.nullcase";

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
        return new int[] {TokenTypes.LITERAL_SWITCH};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final List<DetailAST> caseLabels = getAllCaseLabels(ast);
        final boolean hasNullCaseLabel = caseLabels.stream()
                .anyMatch(MissingNullCaseInSwitchCheck::hasLiteralNull);
        if (!hasNullCaseLabel) {
            final boolean hasPatternCaseLabel = caseLabels.stream()
                .anyMatch(MissingNullCaseInSwitchCheck::hasPatternCaseLabel);
            final boolean hasStringCaseLabel = caseLabels.stream()
                .anyMatch(MissingNullCaseInSwitchCheck::hasStringCaseLabel);
            if (hasPatternCaseLabel || hasStringCaseLabel) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * Gets all case labels in the given switch AST node.
     *
     * @param switchAST the AST node representing {@code LITERAL_SWITCH}
     * @return a list of all case labels in the switch
     */
    private static List<DetailAST> getAllCaseLabels(DetailAST switchAST) {
        final List<DetailAST> caseLabels = new ArrayList<>();
        DetailAST ast = switchAST.getFirstChild();
        while (ast != null) {
            // case group token may have several LITERAL_CASE tokens
            TokenUtil.forEachChild(ast, TokenTypes.LITERAL_CASE, caseLabels::add);
            ast = ast.getNextSibling();
        }
        return Collections.unmodifiableList(caseLabels);
    }

    /**
     * Checks if the given case AST node has a null label.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if the case has {@code null} label, false otherwise
     */
    private static boolean hasLiteralNull(DetailAST caseAST) {
        return Optional.ofNullable(caseAST.findFirstToken(TokenTypes.EXPR))
                .map(exp -> exp.findFirstToken(TokenTypes.LITERAL_NULL))
                .isPresent();
    }

    /**
     * Checks if the given case AST node has a pattern variable declaration label
     * or record pattern definition label.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if case has a pattern in its label
     */
    private static boolean hasPatternCaseLabel(DetailAST caseAST) {
        return caseAST.findFirstToken(TokenTypes.RECORD_PATTERN_DEF) != null
               || caseAST.findFirstToken(TokenTypes.PATTERN_VARIABLE_DEF) != null
               || caseAST.findFirstToken(TokenTypes.PATTERN_DEF) != null;
    }

    /**
     * Checks if the given case contains a string in its label.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if switch block contains a string case label
     */
    private static boolean hasStringCaseLabel(DetailAST caseAST) {
        return hasStringLiteral(caseAST) || hasStringConcatenatedExpression(caseAST);
    }

    /**
     * Checks if the case label has a string literal.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if the case label has a string literal
     */
    private static boolean hasStringLiteral(DetailAST caseAST) {
        return Optional.ofNullable(caseAST.findFirstToken(TokenTypes.EXPR))
                .map(exp -> exp.findFirstToken(TokenTypes.STRING_LITERAL))
                .isPresent();
    }

    /**
     * Checks if the case label has a string in a concatenated expression.
     *
     * @param caseAST the AST node representing {@code LITERAL_CASE}
     * @return true if the case label has a string in a concatenated expression
     */
    private static boolean hasStringConcatenatedExpression(DetailAST caseAST) {
        DetailAST ast = caseAST.getFirstChild().findFirstToken(TokenTypes.PLUS);
        boolean hasStringInConcatenatedExpression = false;
        while (ast != null) {
            if (ast.findFirstToken(TokenTypes.STRING_LITERAL) != null) {
                hasStringInConcatenatedExpression = true;
                break;
            }
            if (ast.getFirstChild() != null
                    && ast.getFirstChild().getType() == TokenTypes.PLUS) {
                ast = ast.getFirstChild();
            }
            else {
                ast = ast.getNextSibling();
            }
        }
        return hasStringInConcatenatedExpression;
    }
}

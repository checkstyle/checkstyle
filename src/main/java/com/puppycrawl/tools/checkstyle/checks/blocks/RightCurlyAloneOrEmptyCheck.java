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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Arrays;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the placement of right curly braces (<code>'}'</code>) for code blocks.
 * The policy verified is that the right curly brace must be alone on a line,
 * or the block must be empty. A block is considered empty if it contains no
 * statements or comments, and the right curly brace is placed immediately after
 * the left curly brace or on a different line.
 * This check supports if-else, try-catch-finally blocks, switch statements, switch cases,
 * while-loops, for-loops, method definitions, class definitions, constructor definitions,
 * instance, static initialization blocks, annotation definitions and enum definitions.
 * This check follows the conventions of
 * <a href="https://google.github.io/styleguide/javaguide.html#s4.1-braces">
 * Google Java Style Guide</a>.
 * </div>
 *
 * @since 13.3.0
 */
@StatelessCheck
public class RightCurlyAloneOrEmptyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_ALONE_OR_EMPTY = "alone.or.empty";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.CLASS_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final Details details = Details.getDetails(ast);
        final DetailAST rcurly = details.rcurly();

        if (rcurly != null) {
            final String violation = validate(details);
            if (!violation.isEmpty()) {
                log(rcurly, violation, "}", rcurly.getColumnNo() + 1);
            }
        }
    }

    /**
     * Does general validation.
     *
     * @param details for validation.
     * @return violation message or empty string
     *     if there was no violation during validation.
     */
    private String validate(Details details) {
        String violation = "";
        if (!isAloneOnLine(details, getLine(details.rcurly.getLineNo() - 1))
            && !isBlockEmptyAndNothingAfterIt(details)) {
            violation = MSG_KEY_ALONE_OR_EMPTY;
        }
        return violation;
    }

    /**
     * Checks whether a block is empty and nothing is after it.
     *
     * @param details for validation.
     * @return true if a block is empty and nothing is after it.
     */
    private boolean isBlockEmptyAndNothingAfterIt(Details details) {
        boolean areOnSameLine = false;
        if (details.lcurly.getType() == TokenTypes.SLIST) {
            areOnSameLine = details.lcurly.getFirstChild() == details.lcurly.getLastChild();
        }
        else if (details.lcurly.getType() == TokenTypes.OBJBLOCK) {
            areOnSameLine = details.lcurly.getFirstChild() == details.rcurly.getPreviousSibling();
        }
        else {
            areOnSameLine = details.rcurly.getPreviousSibling() == details.lcurly;
        }

        final DetailAST nextToken = details.nextToken();
        return areOnSameLine && details.canBeEmpty
            && (nextToken == null || !TokenUtil.areOnSameLine(details.rcurly, nextToken));
    }

    /**
     * Checks whether right curly is alone on a line.
     *
     * @param details for validation.
     * @param targetSrcLine A string with contents of rcurly's line
     * @return true if right curly is alone on a line.
     */
    private static boolean isAloneOnLine(Details details, String targetSrcLine) {
        final DetailAST rcurly = details.rcurly();
        final DetailAST nextToken = details.nextToken();
        return (nextToken == null || !TokenUtil.areOnSameLine(rcurly, nextToken))
            && CommonUtil.hasWhitespaceBefore(details.rcurly().getColumnNo(),
               targetSrcLine);
    }

    /**
     * Structure that contains all details for validation.
     *
     * @param lcurly                the left curly token being analysed
     * @param rcurly                the matching right curly token
     * @param nextToken             the token following the right curly
     * @param canBeEmpty            flag that indicates if the block can be empty
     */
    private record Details(DetailAST lcurly, DetailAST rcurly,
                        DetailAST nextToken, boolean canBeEmpty) {

        /**
         * Token types that identify tokens that will never have SLIST in their AST.
         */
        private static final int[] TOKENS_WITH_NO_CHILD_SLIST = {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.RECORD_DEF,
        };

        /**
         * Collects validation Details.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetails(DetailAST ast) {
            return switch (ast.getType()) {
                case TokenTypes.LITERAL_TRY, TokenTypes.LITERAL_CATCH -> getDetailsForTryCatch(ast);
                case TokenTypes.LITERAL_IF -> getDetailsForIf(ast);
                case TokenTypes.LITERAL_DO -> getDetailsForDoLoops(ast);
                case TokenTypes.LITERAL_SWITCH -> getDetailsForSwitch(ast);
                case TokenTypes.LITERAL_CASE -> getDetailsForCase(ast);
                default -> getDetailsForOthers(ast);
            };
        }

        /**
         * Collects details about switch statements and expressions.
         *
         * @param switchNode switch statement or expression to gather details about
         * @return new Details about given switch statement or expression
         */
        private static Details getDetailsForSwitch(DetailAST switchNode) {
            final DetailAST lcurly = switchNode.findFirstToken(TokenTypes.LCURLY);
            final DetailAST rcurly;
            DetailAST nextToken = null;
            // skipping switch expression as check only handles statements
            if (isSwitchExpression(switchNode)) {
                rcurly = null;
            }
            else {
                rcurly = switchNode.getLastChild();
                nextToken = getNextToken(switchNode);
            }
            return new Details(lcurly, rcurly, nextToken, true);
        }

        /**
         * Collects details about case statements.
         *
         * @param caseNode case statement to gather details about
         * @return new Details about given case statement
         */
        private static Details getDetailsForCase(DetailAST caseNode) {
            final DetailAST caseParent = caseNode.getParent();
            final int parentType = caseParent.getType();
            final Optional<DetailAST> lcurly;
            final DetailAST statementList;

            if (parentType == TokenTypes.SWITCH_RULE) {
                statementList = caseParent.findFirstToken(TokenTypes.SLIST);
                lcurly = Optional.ofNullable(statementList);
            }
            else {
                statementList = caseNode.getNextSibling();
                lcurly = Optional.ofNullable(statementList)
                         .map(DetailAST::getFirstChild)
                         .filter(node -> node.getType() == TokenTypes.SLIST);
            }
            final DetailAST rcurly = lcurly.map(DetailAST::getLastChild)
                    .filter(child -> !isSwitchExpression(caseParent))
                    .orElse(null);
            final Optional<DetailAST> nextToken =
                    Optional.ofNullable(lcurly.map(DetailAST::getNextSibling)
                    .orElseGet(() -> getNextToken(caseParent)));

            return new Details(lcurly.orElse(null), rcurly, nextToken.orElse(null), true);
        }

        /**
         * Check whether switch is expression or not.
         *
         * @param switchNode switch statement or expression to provide detail
         * @return true if it is a switch expression
         */
        private static boolean isSwitchExpression(DetailAST switchNode) {
            DetailAST currentNode = switchNode;
            boolean ans = false;

            while (currentNode != null) {
                if (currentNode.getType() == TokenTypes.EXPR) {
                    ans = true;
                }
                currentNode = currentNode.getParent();
            }
            return ans;
        }

        /**
         * Collects validation details for LITERAL_DO loops' tokens.
         *
         * @param ast a {@code DetailAST} value
         * @return an object containing all details to make a validation
         */
        private static Details getDetailsForDoLoops(DetailAST ast) {
            final DetailAST lcurly = ast.findFirstToken(TokenTypes.SLIST);
            final DetailAST nextToken = ast.findFirstToken(TokenTypes.DO_WHILE);
            DetailAST rcurly = null;
            if (lcurly != null) {
                rcurly = lcurly.getLastChild();
            }
            return new Details(lcurly, rcurly, nextToken, true);
        }

        /**
         * Collects validation details for LITERAL_TRY, and LITERAL_CATCH.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForTryCatch(DetailAST ast) {
            final DetailAST lcurly;
            final DetailAST nextToken;
            final int tokenType = ast.getType();
            if (tokenType == TokenTypes.LITERAL_TRY) {
                if (ast.getFirstChild().getType() == TokenTypes.RESOURCE_SPECIFICATION) {
                    lcurly = ast.getFirstChild().getNextSibling();
                }
                else {
                    lcurly = ast.getFirstChild();
                }
                nextToken = lcurly.getNextSibling();
            }
            else {
                nextToken = getNextToken(ast);
                lcurly = ast.getLastChild();
            }

            final DetailAST rcurly = lcurly.getLastChild();
            return new Details(lcurly, rcurly, nextToken, false);
        }

        /**
         * Collects validation details for LITERAL_IF.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForIf(DetailAST ast) {
            final DetailAST lcurly;
            DetailAST nextToken = ast.findFirstToken(TokenTypes.LITERAL_ELSE);

            if (nextToken == null) {
                nextToken = getNextToken(ast);
                lcurly = ast.getLastChild();
            }
            else {
                lcurly = nextToken.getPreviousSibling();
            }

            DetailAST rcurly = null;
            if (lcurly.getType() == TokenTypes.SLIST) {
                rcurly = lcurly.getLastChild();
            }
            return new Details(lcurly, rcurly, nextToken, false);
        }

        /**
         * Collects validation details for CLASS_DEF, RECORD_DEF, METHOD DEF, CTOR_DEF,
         * STATIC_INIT, INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, LITERAL_CATCH,
         * LITERAL_FINALLY and COMPACT_CTOR_DEF.
         *
         * @param ast a {@code DetailAST} value
         * @return an object containing all details to make a validation
         */
        private static Details getDetailsForOthers(DetailAST ast) {
            boolean canBeEmpty = true;
            if (ast.getType() == TokenTypes.LITERAL_FINALLY
                    || ast.getType() == TokenTypes.LITERAL_ELSE) {
                canBeEmpty = false;
            }
            DetailAST rcurly = null;
            final DetailAST lcurly;
            final int tokenType = ast.getType();
            if (isTokenWithNoChildSlist(tokenType)) {
                final DetailAST child = ast.getLastChild();
                lcurly = child;
                rcurly = child.getLastChild();
            }
            else {
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    // SLIST could be absent if method is abstract
                    rcurly = lcurly.getLastChild();
                }
            }

            return new Details(lcurly, rcurly, getNextToken(ast), canBeEmpty);
        }

        /**
         * Tests whether the provided tokenType will never have a SLIST as child in its AST.
         * Like CLASS_DEF, ANNOTATION_DEF etc.
         *
         * @param tokenType the tokenType to test against.
         * @return weather provided tokenType is definition token.
         */
        private static boolean isTokenWithNoChildSlist(int tokenType) {
            return Arrays.stream(TOKENS_WITH_NO_CHILD_SLIST).anyMatch(token -> token == tokenType);
        }

        /**
         * Finds next token after the given one.
         *
         * @param ast the given node.
         * @return the token which represents next lexical item.
         */
        private static DetailAST getNextToken(DetailAST ast) {
            DetailAST next = null;
            DetailAST parent = ast;
            while (next == null && parent != null) {
                next = parent.getNextSibling();
                parent = parent.getParent();
            }
            return next;
        }
    }

}

////
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
///

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the placement of right curly braces (<code>'}'</code>) for code blocks. This check
 * supports if-else, try-catch-finally blocks, switch statements, switch cases, while-loops,
 *  for-loops, method definitions, class definitions, constructor definitions,
 * instance, static initialization blocks, annotation definitions and enum definitions.
 * For right curly brace of expression blocks of arrays, lambdas and class instances
 * please follow issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/5945">#5945</a>.
 * For right curly brace of enum constant please follow issue
 * <a href="https://github.com/checkstyle/checkstyle/issues/7519">#7519</a>.
 * </div>
 *
 * <ul>
 * <li>
 * Property {@code option} - Specify the policy on placement of a right curly brace
 * (<code>'}'</code>).
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption}.
 * Default value is {@code same}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">
 * LITERAL_TRY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">
 * LITERAL_FINALLY</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">
 * LITERAL_ELSE</a>.
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
 * {@code line.alone}
 * </li>
 * <li>
 * {@code line.break.before}
 * </li>
 * <li>
 * {@code line.same}
 * </li>
 * </ul>
 *
 * @since 3.0
 */
@StatelessCheck
public class RightCurlyCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_BREAK_BEFORE = "line.break.before";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_ALONE = "line.alone";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_SAME = "line.same";

    /**
     * Specify the policy on placement of a right curly brace (<code>'}'</code>).
     */
    private RightCurlyOption option = RightCurlyOption.SAME;

    /**
     * Setter to specify the policy on placement of a right curly brace (<code>'}'</code>).
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 3.0
     */
    public void setOption(String optionStr) {
        option = RightCurlyOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
    }

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
        final DetailAST rcurly = details.rcurly;

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
        if (shouldHaveLineBreakBefore(option, details)) {
            violation = MSG_KEY_LINE_BREAK_BEFORE;
        }
        else if (shouldBeOnSameLine(option, details)) {
            violation = MSG_KEY_LINE_SAME;
        }
        else if (shouldBeAloneOnLine(option, details, getLine(details.rcurly.getLineNo() - 1))) {
            violation = MSG_KEY_LINE_ALONE;
        }
        return violation;
    }

    /**
     * Checks whether a right curly should have a line break before.
     *
     * @param bracePolicy option for placing the right curly brace.
     * @param details details for validation.
     * @return true if a right curly should have a line break before.
     */
    private static boolean shouldHaveLineBreakBefore(RightCurlyOption bracePolicy,
                                                     Details details) {
        return bracePolicy == RightCurlyOption.SAME
            && !hasLineBreakBefore(details.rcurly)
            && !TokenUtil.areOnSameLine(details.lcurly, details.rcurly);
    }

    /**
     * Checks that a right curly should be on the same line as the next statement.
     *
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeOnSameLine(RightCurlyOption bracePolicy, Details details) {
        return bracePolicy == RightCurlyOption.SAME
            && !details.shouldCheckLastRcurly
            && !TokenUtil.areOnSameLine(details.rcurly, details.nextToken);
    }

    /**
     * Checks that a right curly should be alone on a line.
     *
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @param targetSrcLine A string with contents of rcurly's line
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeAloneOnLine(RightCurlyOption bracePolicy,
                                               Details details,
                                               String targetSrcLine) {
        return bracePolicy == RightCurlyOption.ALONE
            && shouldBeAloneOnLineWithAloneOption(details, targetSrcLine)
            || (bracePolicy == RightCurlyOption.ALONE_OR_SINGLELINE
            || details.shouldCheckLastRcurly)
            && shouldBeAloneOnLineWithNotAloneOption(details, targetSrcLine);
    }

    /**
     * Whether right curly should be alone on line when ALONE option is used.
     *
     * @param details details for validation.
     * @param targetSrcLine A string with contents of rcurly's line
     * @return true, if right curly should be alone on line when ALONE option is used.
     */
    private static boolean shouldBeAloneOnLineWithAloneOption(Details details,
                                                              String targetSrcLine) {
        return !isAloneOnLine(details, targetSrcLine);
    }

    /**
     * Whether right curly should be alone on line when ALONE_OR_SINGLELINE or SAME option is used.
     *
     * @param details details for validation.
     * @param targetSrcLine A string with contents of rcurly's line
     * @return true, if right curly should be alone on line
     *         when ALONE_OR_SINGLELINE or SAME option is used.
     */
    private static boolean shouldBeAloneOnLineWithNotAloneOption(Details details,
                                                                 String targetSrcLine) {
        return shouldBeAloneOnLineWithAloneOption(details, targetSrcLine)
            && !isBlockAloneOnSingleLine(details);
    }

    /**
     * Checks whether right curly is alone on a line.
     *
     * @param details for validation.
     * @param targetSrcLine A string with contents of rcurly's line
     * @return true if right curly is alone on a line.
     */
    private static boolean isAloneOnLine(Details details, String targetSrcLine) {
        final DetailAST rcurly = details.rcurly;
        final DetailAST nextToken = details.nextToken;
        return (nextToken == null || !TokenUtil.areOnSameLine(rcurly, nextToken)
            || skipDoubleBraceInstInit(details))
            && CommonUtil.hasWhitespaceBefore(details.rcurly.getColumnNo(),
            targetSrcLine);
    }

    /**
     * This method determines if the double brace initialization should be skipped over by the
     * check. Double brace initializations are treated differently. The corresponding inner
     * rcurly is treated as if it was alone on line even when it may be followed by another
     * rcurly and a semi, raising no violations.
     * <i>Please do note though that the line should not contain anything other than the following
     * right curly and the semi following it or else violations will be raised.</i>
     * Only the kind of double brace initializations shown in the following example code will be
     * skipped over:<br>
     * <pre>
     *     {@code Map<String, String> map = new LinkedHashMap<>() {{
     *           put("alpha", "man");
     *       }}; // no violation}
     * </pre>
     *
     * @param details {@link Details} object containing the details relevant to the rcurly
     * @return if the double brace initialization rcurly should be skipped over by the check
     */
    private static boolean skipDoubleBraceInstInit(Details details) {
        boolean skipDoubleBraceInstInit = false;
        final DetailAST tokenAfterNextToken = Details.getNextToken(details.nextToken);
        if (tokenAfterNextToken != null) {
            final DetailAST rcurly = details.rcurly;
            skipDoubleBraceInstInit = rcurly.getParent().getParent()
                .getType() == TokenTypes.INSTANCE_INIT
                && details.nextToken.getType() == TokenTypes.RCURLY
                && !TokenUtil.areOnSameLine(rcurly, Details.getNextToken(tokenAfterNextToken));
        }
        return skipDoubleBraceInstInit;
    }

    /**
     * Checks whether block has a single-line format and is alone on a line.
     *
     * @param details for validation.
     * @return true if block has single-line format and is alone on a line.
     */
    private static boolean isBlockAloneOnSingleLine(Details details) {
        DetailAST nextToken = details.nextToken;

        while (nextToken != null && nextToken.getType() == TokenTypes.LITERAL_ELSE) {
            nextToken = Details.getNextToken(nextToken);
        }

        // sibling tokens should be allowed on a single line
        final int[] tokensWithBlockSibling = {
            TokenTypes.DO_WHILE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_CATCH,
        };

        if (TokenUtil.isOfType(nextToken, tokensWithBlockSibling)) {
            final DetailAST parent = nextToken.getParent();
            nextToken = Details.getNextToken(parent);
        }

        return TokenUtil.areOnSameLine(details.lcurly, details.rcurly)
            && (nextToken == null || !TokenUtil.areOnSameLine(details.rcurly, nextToken)
            || isRightcurlyFollowedBySemicolon(details));
    }

    /**
     * Checks whether the right curly is followed by a semicolon.
     *
     * @param details details for validation.
     * @return true if the right curly is followed by a semicolon.
     */
    private static boolean isRightcurlyFollowedBySemicolon(Details details) {
        return details.nextToken.getType() == TokenTypes.SEMI;
    }

    /**
     * Checks if right curly has line break before.
     *
     * @param rightCurly right curly token.
     * @return true, if right curly has line break before.
     */
    private static boolean hasLineBreakBefore(DetailAST rightCurly) {
        DetailAST previousToken = rightCurly.getPreviousSibling();
        if (previousToken == null) {
            previousToken = rightCurly.getParent();
        }
        return !TokenUtil.areOnSameLine(rightCurly, previousToken);
    }

    /**
     * Structure that contains all details for validation.
     */
    private static final class Details {

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

        /** Right curly. */
        private final DetailAST rcurly;
        /** Left curly. */
        private final DetailAST lcurly;
        /** Next token. */
        private final DetailAST nextToken;
        /** Should check last right curly. */
        private final boolean shouldCheckLastRcurly;

        /**
         * Constructor.
         *
         * @param lcurly the lcurly of the token whose details are being collected
         * @param rcurly the rcurly of the token whose details are being collected
         * @param nextToken the token after the token whose details are being collected
         * @param shouldCheckLastRcurly boolean value to determine if to check last rcurly
         */
        private Details(DetailAST lcurly, DetailAST rcurly,
                        DetailAST nextToken, boolean shouldCheckLastRcurly) {
            this.lcurly = lcurly;
            this.rcurly = rcurly;
            this.nextToken = nextToken;
            this.shouldCheckLastRcurly = shouldCheckLastRcurly;
        }

        /**
         * Collects validation Details.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetails(DetailAST ast) {
            final Details details;
            switch (ast.getType()) {
                case TokenTypes.LITERAL_TRY:
                case TokenTypes.LITERAL_CATCH:
                    details = getDetailsForTryCatch(ast);
                    break;
                case TokenTypes.LITERAL_IF:
                    details = getDetailsForIf(ast);
                    break;
                case TokenTypes.LITERAL_DO:
                    details = getDetailsForDoLoops(ast);
                    break;
                case TokenTypes.LITERAL_SWITCH:
                    details = getDetailsForSwitch(ast);
                    break;
                case TokenTypes.LITERAL_CASE:
                    details = getDetailsForCase(ast);
                    break;
                default:
                    details = getDetailsForOthers(ast);
                    break;
            }
            return details;
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
         * Collects validation details for LITERAL_TRY, and LITERAL_CATCH.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForTryCatch(DetailAST ast) {
            final DetailAST lcurly;
            DetailAST nextToken;
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
                nextToken = ast.getNextSibling();
                lcurly = ast.getLastChild();
            }

            final boolean shouldCheckLastRcurly;
            if (nextToken == null) {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
            }
            else {
                shouldCheckLastRcurly = false;
            }

            final DetailAST rcurly = lcurly.getLastChild();
            return new Details(lcurly, rcurly, nextToken, shouldCheckLastRcurly);
        }

        /**
         * Collects validation details for LITERAL_IF.
         *
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForIf(DetailAST ast) {
            final boolean shouldCheckLastRcurly;
            final DetailAST lcurly;
            DetailAST nextToken = ast.findFirstToken(TokenTypes.LITERAL_ELSE);

            if (nextToken == null) {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
                lcurly = ast.getLastChild();
            }
            else {
                shouldCheckLastRcurly = false;
                lcurly = nextToken.getPreviousSibling();
            }

            DetailAST rcurly = null;
            if (lcurly.getType() == TokenTypes.SLIST) {
                rcurly = lcurly.getLastChild();
            }
            return new Details(lcurly, rcurly, nextToken, shouldCheckLastRcurly);
        }

        /**
         * Collects validation details for CLASS_DEF, RECORD_DEF, METHOD DEF, CTOR_DEF, STATIC_INIT,
         * INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, and COMPACT_CTOR_DEF.
         *
         * @param ast a {@code DetailAST} value
         * @return an object containing all details to make a validation
         */
        private static Details getDetailsForOthers(DetailAST ast) {
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
            return new Details(lcurly, rcurly, getNextToken(ast), true);
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
            return new Details(lcurly, rcurly, nextToken, false);
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

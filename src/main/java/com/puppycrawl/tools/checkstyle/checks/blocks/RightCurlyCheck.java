////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks the placement of right curly braces.
 * The policy to verify is specified using the {@link RightCurlyOption} class
 * and defaults to {@link RightCurlyOption#SAME}.
 * </p>
 * <p> By default the check will check the following tokens:
 *  {@link TokenTypes#LITERAL_TRY LITERAL_TRY},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_FINALLY LITERAL_FINALLY},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE}.
 * Other acceptable tokens are:
 *  {@link TokenTypes#CLASS_DEF CLASS_DEF},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF}.
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR}.
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO}.
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT}.
 *  {@link TokenTypes#INSTANCE_INIT INSTANCE_INIT}.
 * </p>
 * <p>
 * <b>shouldStartLine</b> - does the check need to check
 * if right curly starts line. Default value is <b>true</b>
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check with policy
 * {@link RightCurlyOption#ALONE} for {@code else} and
 * {@code {@link TokenTypes#METHOD_DEF METHOD_DEF}}tokens is:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"&gt;
 *     &lt;property name="tokens" value="LITERAL_ELSE"/&gt;
 *     &lt;property name="option" value="alone"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
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
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY_LINE_NEW = "line.new";

    /** Do we need to check if right curly starts line. */
    private boolean shouldStartLine = true;

    /** The policy to enforce. */
    private RightCurlyOption option = RightCurlyOption.SAME;

    /**
     * Sets the option to enforce.
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = RightCurlyOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("unable to parse " + optionStr, iae);
        }
    }

    /**
     * Does the check need to check if right curly starts line.
     * @param flag new value of this property.
     */
    public void setShouldStartLine(boolean flag) {
        shouldStartLine = flag;
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
     * @param details for validation.
     * @return violation message or empty string
     *     if there was not violation during validation.
     */
    private String validate(Details details) {
        String violation = "";
        if (shouldHaveLineBreakBefore(option, details)) {
            violation = MSG_KEY_LINE_BREAK_BEFORE;
        }
        else if (shouldBeOnSameLine(option, details)) {
            violation = MSG_KEY_LINE_SAME;
        }
        else if (shouldBeAloneOnLine(option, details)) {
            violation = MSG_KEY_LINE_ALONE;
        }
        else if (shouldStartLine) {
            final String targetSourceLine = getLines()[details.rcurly.getLineNo() - 1];
            if (!isOnStartOfLine(details, targetSourceLine)) {
                violation = MSG_KEY_LINE_NEW;
            }
        }
        return violation;
    }

    /**
     * Checks whether a right curly should have a line break before.
     * @param bracePolicy option for placing the right curly brace.
     * @param details details for validation.
     * @return true if a right curly should have a line break before.
     */
    private static boolean shouldHaveLineBreakBefore(RightCurlyOption bracePolicy,
                                                     Details details) {
        return bracePolicy == RightCurlyOption.SAME
                && !hasLineBreakBefore(details.rcurly)
                && details.lcurly.getLineNo() != details.rcurly.getLineNo();
    }

    /**
     * Checks that a right curly should be on the same line as the next statement.
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeOnSameLine(RightCurlyOption bracePolicy, Details details) {
        return bracePolicy == RightCurlyOption.SAME
                && !details.shouldCheckLastRcurly
                && details.rcurly.getLineNo() != details.nextToken.getLineNo();
    }

    /**
     * Checks that a right curly should be alone on a line.
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeAloneOnLine(RightCurlyOption bracePolicy, Details details) {
        return bracePolicy == RightCurlyOption.ALONE
                    && shouldBeAloneOnLineWithAloneOption(details)
                || bracePolicy == RightCurlyOption.ALONE_OR_SINGLELINE
                    && shouldBeAloneOnLineWithAloneOrSinglelineOption(details)
                || details.shouldCheckLastRcurly
                    && details.rcurly.getLineNo() == details.nextToken.getLineNo();
    }

    /**
     * Whether right curly should be alone on line when ALONE option is used.
     * @param details details for validation.
     * @return true, if right curly should be alone on line when ALONE option is used.
     */
    private static boolean shouldBeAloneOnLineWithAloneOption(Details details) {
        return !isAloneOnLine(details)
                && !isEmptyBody(details.lcurly);
    }

    /**
     * Whether right curly should be alone on line when ALONE_OR_SINGLELINE option is used.
     * @param details details for validation.
     * @return true, if right curly should be alone on line
     *         when ALONE_OR_SINGLELINE option is used.
     */
    private static boolean shouldBeAloneOnLineWithAloneOrSinglelineOption(Details details) {
        return !isAloneOnLine(details)
                && !isSingleLineBlock(details)
                && !isAnonInnerClassInit(details.lcurly)
                && !isEmptyBody(details.lcurly);
    }

    /**
     * Whether right curly brace starts target source line.
     * @param details Details of right curly brace for validation
     * @param targetSourceLine source line to check
     * @return true if right curly brace starts target source line.
     */
    private static boolean isOnStartOfLine(Details details, String targetSourceLine) {
        return CommonUtil.hasWhitespaceBefore(details.rcurly.getColumnNo(), targetSourceLine)
                || details.lcurly.getLineNo() == details.rcurly.getLineNo();
    }

    /**
     * Checks whether right curly is alone on a line.
     * @param details for validation.
     * @return true if right curly is alone on a line.
     */
    private static boolean isAloneOnLine(Details details) {
        final DetailAST rcurly = details.rcurly;
        final DetailAST lcurly = details.lcurly;
        final DetailAST nextToken = details.nextToken;
        return rcurly.getLineNo() != lcurly.getLineNo()
            && rcurly.getLineNo() != nextToken.getLineNo();
    }

    /**
     * Checks whether block has a single-line format.
     * @param details for validation.
     * @return true if block has single-line format.
     */
    private static boolean isSingleLineBlock(Details details) {
        final DetailAST rcurly = details.rcurly;
        final DetailAST lcurly = details.lcurly;
        final DetailAST nextToken = details.nextToken;
        return rcurly.getLineNo() == lcurly.getLineNo()
            && rcurly.getLineNo() != nextToken.getLineNo();
    }

    /**
     * Checks whether lcurly is in anonymous inner class initialization.
     * @param lcurly left curly token.
     * @return true if lcurly begins anonymous inner class initialization.
     */
    private static boolean isAnonInnerClassInit(DetailAST lcurly) {
        final Scope surroundingScope = ScopeUtil.getSurroundingScope(lcurly);
        return surroundingScope.ordinal() == Scope.ANONINNER.ordinal();
    }

    /**
     * Checks if definition body is empty.
     * @param lcurly left curly.
     * @return true if definition body is empty.
     */
    private static boolean isEmptyBody(DetailAST lcurly) {
        boolean result = false;
        if (lcurly.getParent().getType() == TokenTypes.OBJBLOCK) {
            if (lcurly.getNextSibling().getType() == TokenTypes.RCURLY) {
                result = true;
            }
        }
        else if (lcurly.getFirstChild().getType() == TokenTypes.RCURLY) {
            result = true;
        }
        return result;
    }

    /**
     * Checks if right curly has line break before.
     * @param rightCurly right curly token.
     * @return true, if right curly has line break before.
     */
    private static boolean hasLineBreakBefore(DetailAST rightCurly) {
        final DetailAST previousToken = rightCurly.getPreviousSibling();
        return previousToken == null
                || rightCurly.getLineNo() != previousToken.getLineNo();
    }

    /**
     * Structure that contains all details for validation.
     */
    private static final class Details {

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
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetails(DetailAST ast) {
            final Details details;
            switch (ast.getType()) {
                case TokenTypes.LITERAL_TRY:
                case TokenTypes.LITERAL_CATCH:
                case TokenTypes.LITERAL_FINALLY:
                    details = getDetailsForTryCatchFinally(ast);
                    break;
                case TokenTypes.LITERAL_IF:
                case TokenTypes.LITERAL_ELSE:
                    details = getDetailsForIfElse(ast);
                    break;
                case TokenTypes.LITERAL_DO:
                case TokenTypes.LITERAL_WHILE:
                case TokenTypes.LITERAL_FOR:
                    details = getDetailsForLoops(ast);
                    break;
                default:
                    details = getDetailsForOthers(ast);
                    break;
            }
            return details;
        }

        /**
         * Collects validation details for LITERAL_TRY, LITERAL_CATCH, and LITERAL_FINALLY.
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForTryCatchFinally(DetailAST ast) {
            boolean shouldCheckLastRcurly = false;
            final DetailAST rcurly;
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
                rcurly = lcurly.getLastChild();

                if (nextToken == null) {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                }
            }
            else if (tokenType == TokenTypes.LITERAL_CATCH) {
                nextToken = ast.getNextSibling();
                lcurly = ast.getLastChild();
                rcurly = lcurly.getLastChild();
                if (nextToken == null) {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                }
            }
            else {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
                lcurly = ast.getFirstChild();
                rcurly = lcurly.getLastChild();
            }
            return new Details(lcurly, rcurly, nextToken, shouldCheckLastRcurly);
        }

        /**
         * Collects validation details for LITERAL_IF and LITERAL_ELSE.
         * @param ast a {@code DetailAST} value
         * @return object containing all details to make a validation
         */
        private static Details getDetailsForIfElse(DetailAST ast) {
            boolean shouldCheckLastRcurly = false;
            final DetailAST lcurly;
            DetailAST nextToken;
            final int tokenType = ast.getType();
            if (tokenType == TokenTypes.LITERAL_IF) {
                nextToken = ast.findFirstToken(TokenTypes.LITERAL_ELSE);
                if (nextToken == null) {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                    lcurly = ast.getLastChild();
                }
                else {
                    lcurly = nextToken.getPreviousSibling();
                }
            }
            else {
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
                lcurly = ast.getFirstChild();
            }
            DetailAST rcurly = null;
            if (lcurly.getType() == TokenTypes.SLIST) {
                rcurly = lcurly.getLastChild();
            }
            return new Details(lcurly, rcurly, nextToken, shouldCheckLastRcurly);
        }

        /**
         * Collects validation details for CLASS_DEF, METHOD DEF, CTOR_DEF, STATIC_INIT, and
         * INSTANCE_INIT.
         * @param ast a {@code DetailAST} value
         * @return an object containing all details to make a validation
         */
        private static Details getDetailsForOthers(DetailAST ast) {
            DetailAST rcurly = null;
            final DetailAST lcurly;
            final DetailAST nextToken;
            final int tokenType = ast.getType();
            if (tokenType == TokenTypes.CLASS_DEF) {
                final DetailAST child = ast.getLastChild();
                lcurly = child.getFirstChild();
                rcurly = child.getLastChild();
                nextToken = ast;
            }
            else if (tokenType == TokenTypes.METHOD_DEF) {
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    // SLIST could be absent if method is abstract
                    rcurly = lcurly.getLastChild();
                }
                nextToken = getNextToken(ast);
            }
            else {
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                rcurly = lcurly.getLastChild();
                nextToken = getNextToken(ast);
            }
            return new Details(lcurly, rcurly, nextToken, false);
        }

        /**
         * Collects validation details for loops' tokens.
         * @param ast a {@code DetailAST} value
         * @return an object containing all details to make a validation
         */
        private static Details getDetailsForLoops(DetailAST ast) {
            DetailAST rcurly = null;
            final DetailAST lcurly;
            final DetailAST nextToken;
            final int tokenType = ast.getType();
            if (tokenType == TokenTypes.LITERAL_DO) {
                nextToken = ast.findFirstToken(TokenTypes.DO_WHILE);
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    rcurly = lcurly.getLastChild();
                }
            }
            else {
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    // SLIST could be absent in code like "while(true);"
                    rcurly = lcurly.getLastChild();
                }
                nextToken = getNextToken(ast);
            }
            return new Details(lcurly, rcurly, nextToken, false);
        }

        /**
         * Finds next token after the given one.
         * @param ast the given node.
         * @return the token which represents next lexical item.
         */
        private static DetailAST getNextToken(DetailAST ast) {
            DetailAST next = null;
            DetailAST parent = ast;
            while (next == null) {
                next = parent.getNextSibling();
                parent = parent.getParent();
            }
            return CheckUtil.getFirstNode(next);
        }

    }

}

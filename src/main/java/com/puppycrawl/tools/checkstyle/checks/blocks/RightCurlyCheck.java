////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import org.apache.commons.beanutils.ConversionException;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CheckUtils;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtils;

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
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LAMBDA LAMBDA}.
 * Other acceptable tokens are:
 *  {@link TokenTypes#CLASS_DEF CLASS_DEF},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF}.
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR}.
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO}.
 *  {@link TokenTypes#STATIC_INIT STATIC_INIT}.
 *  {@link TokenTypes#INSTANCE_INIT INSTANCE_INIT}.
 *  {@link TokenTypes#LAMBDA LAMBDA}.
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
 * @author Oliver Burn
 * @author lkuehne
 * @author o_sukhodolsky
 * @author maxvetrenko
 * @author Andrei Selkin
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
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
     * Set the option to enforce.
     * @param optionStr string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String optionStr) {
        try {
            option = RightCurlyOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
        }
        catch (IllegalArgumentException iae) {
            throw new ConversionException("unable to parse " + optionStr, iae);
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
            TokenTypes.LAMBDA,
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
            TokenTypes.LAMBDA,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final Details details = getDetails(ast);
        final DetailAST rcurly = details.rcurly;

        if (rcurly != null) {
            final String violation;
            if (shouldStartLine) {
                final String targetSourceLine = getLines()[rcurly.getLineNo() - 1];
                violation = validate(details, option, true, targetSourceLine);
            }
            else {
                violation = validate(details, option, false, "");
            }

            if (!violation.isEmpty()) {
                log(rcurly, violation, "}", rcurly.getColumnNo() + 1);
            }
        }
    }

    /**
     * Does general validation.
     * @param details for validation.
     * @param bracePolicy for placing the right curly brace.
     * @param shouldStartLine do we need to check if right curly starts line.
     * @param targetSourceLine line that we need to check if shouldStartLine is true.
     * @return violation message or empty string
     *     if there was not violation during validation.
     */
    private static String validate(Details details, RightCurlyOption bracePolicy,
                                   boolean shouldStartLine, String targetSourceLine) {
        final DetailAST rcurly = details.rcurly;
        final DetailAST nextToken = details.nextToken;
        final boolean shouldCheckLastRcurly = details.shouldCheckLastRcurly;
        String violation = "";

        if (shouldHaveBeLineBreakBefore(bracePolicy, details)) {
            violation = MSG_KEY_LINE_BREAK_BEFORE;
        }
        else if (shouldCheckLastRcurly) {
            if (rcurly.getLineNo() == nextToken.getLineNo()) {
                violation = MSG_KEY_LINE_ALONE;
            }
        }
        else if (shouldBeOnSameLine(bracePolicy, details)) {
            violation = MSG_KEY_LINE_SAME;
        }
        else if (shouldBeAloneOnLine(bracePolicy, details)) {
            violation = MSG_KEY_LINE_ALONE;
        }
        else if (shouldStartLine && !isOnStartOfLine(details, targetSourceLine)) {
            violation = MSG_KEY_LINE_NEW;
        }
        return violation;
    }

    /**
     * Checks that a right curly should be on the same line as the next statement.
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeOnSameLine(RightCurlyOption bracePolicy, Details details) {
        return bracePolicy == RightCurlyOption.SAME
                && details.rcurly.getLineNo() != details.nextToken.getLineNo();
    }

    /**
     * Checks that a right curly should be alone on a line.
     * @param bracePolicy option for placing the right curly brace
     * @param details Details for validation
     * @return true if a right curly should be alone on a line.
     */
    private static boolean shouldBeAloneOnLine(RightCurlyOption bracePolicy, Details details) {
        final int tokenLambda = details.lcurly.getParent().getType();
        return bracePolicy == RightCurlyOption.ALONE
                && tokenLambda != TokenTypes.LAMBDA
                && !isAloneOnLine(details)
                && !isEmptyBody(details.lcurly)
                || bracePolicy == RightCurlyOption.ALONE_OR_SINGLELINE
                && !isAloneOnLine(details)
                && !isSingleLineBlock(details)
                && !isAnonInnerClassInit(details.lcurly)
                && !isEmptyBody(details.lcurly)
                || bracePolicy == RightCurlyOption.ALONE
                && tokenLambda == TokenTypes.LAMBDA
                && !isAloneLambda(details);
    }

    /**
     * Whether right curly brace starts target source line.
     * @param details Details of right curly brace for validation
     * @param targetSourceLine source line to check
     * @return true if right curly brace starts target source line.
     */
    private static boolean isOnStartOfLine(Details details, String targetSourceLine) {
        return CommonUtils.hasWhitespaceBefore(details.rcurly.getColumnNo(), targetSourceLine)
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
     * Checks if right curly is alone on line in token the lambda.
     * @param details for validation.
     * @return true, if right curly is alone on line.
     */
    private static boolean isAloneLambda(Details details) {
        final DetailAST lcurly = details.lcurly;
        final DetailAST rcurly = details.rcurly;
        final int lcurlyNo = lcurly.getLineNo();
        final int rcurlyNo = rcurly.getLineNo();
        boolean result = false;
        if (lcurlyNo != rcurlyNo) {
            result = rcurly.getPreviousSibling() == null
                    || rcurlyNo != rcurly.getPreviousSibling().getLineNo();
        }
        return result;
    }

    /**
     * Checks whether lcurly is in anonymous inner class initialization.
     * @param lcurly left curly token.
     * @return true if lcurly begins anonymous inner class initialization.
     */
    private static boolean isAnonInnerClassInit(DetailAST lcurly) {
        final Scope surroundingScope = ScopeUtils.getSurroundingScope(lcurly);
        return surroundingScope.ordinal() == Scope.ANONINNER.ordinal();
    }

    /**
     * Collects validation details.
     * @param ast detail ast.
     * @return object that contain all details to make a validation.
     */
    // -@cs[JavaNCSS] getDetails() method is a huge SWITCH, it has to be monolithic
    // -@cs[ExecutableStatementCount] getDetails() method is a huge SWITCH, it has to be monolithic
    private static Details getDetails(DetailAST ast) {
        // Attempt to locate the tokens to do the check
        boolean shouldCheckLastRcurly = false;
        DetailAST rcurly = null;
        final DetailAST lcurly;
        DetailAST nextToken;

        switch (ast.getType()) {
            case TokenTypes.LITERAL_TRY:
                lcurly = ast.getFirstChild();
                nextToken = lcurly.getNextSibling();
                rcurly = lcurly.getLastChild();
                break;
            case TokenTypes.LITERAL_CATCH:
                nextToken = ast.getNextSibling();
                lcurly = ast.getLastChild();
                rcurly = lcurly.getLastChild();
                if (nextToken == null) {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                }
                break;
            case TokenTypes.LITERAL_IF:
                nextToken = ast.findFirstToken(TokenTypes.LITERAL_ELSE);
                if (nextToken == null) {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                    lcurly = ast.getLastChild();
                }
                else {
                    lcurly = nextToken.getPreviousSibling();
                }
                if (lcurly.getType() == TokenTypes.SLIST) {
                    rcurly = lcurly.getLastChild();
                }
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.LITERAL_FINALLY:
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
                lcurly = ast.getFirstChild();
                if (lcurly.getType() == TokenTypes.SLIST) {
                    rcurly = lcurly.getLastChild();
                }
                break;
            case TokenTypes.CLASS_DEF:
                final DetailAST child = ast.getLastChild();
                lcurly = child.getFirstChild();
                rcurly = child.getLastChild();
                nextToken = ast;
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.INSTANCE_INIT:
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                rcurly = lcurly.getLastChild();
                nextToken = getNextToken(ast);
                break;
            case TokenTypes.LITERAL_DO:
                nextToken = ast.findFirstToken(TokenTypes.DO_WHILE);
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    rcurly = lcurly.getLastChild();
                }
                break;
            case TokenTypes.LAMBDA:
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                nextToken = getNextToken(ast);
                if (lcurly != null) {
                    rcurly = lcurly.getLastChild();
                }
                break;
            default:
                // ATTENTION! We have default here, but we expect case TokenTypes.METHOD_DEF,
                // TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE only.
                // It has been done to improve coverage to 100%. I couldn't replace it with
                // if-else-if block because code was ugly and didn't pass pmd check.

                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    // SLIST could be absent if method is abstract,
                    // and code like "while(true);"
                    rcurly = lcurly.getLastChild();
                }
                nextToken = getNextToken(ast);
                break;
        }

        final Details details = new Details();
        details.rcurly = rcurly;
        details.lcurly = lcurly;
        details.nextToken = nextToken;
        details.shouldCheckLastRcurly = shouldCheckLastRcurly;

        return details;
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
        return CheckUtils.getFirstNode(next);
    }

    /**
     * Checks that before a right curly should be a linebreak.
     * @param bracePolicy options for placing the right curly brace.
     * @param details Details for validation
     * @return true if before a right curly should be a linebreak.
     */
    private static boolean shouldHaveBeLineBreakBefore(RightCurlyOption bracePolicy,
                                                       Details details) {
        final DetailAST rcurly = details.rcurly;
        final DetailAST lcurly = details.lcurly;
        return bracePolicy == RightCurlyOption.SAME
                && !hasLineBreakBefore(rcurly)
                && lcurly.getLineNo() != rcurly.getLineNo();
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
    private static class Details {
        /** Right curly. */
        private DetailAST rcurly;
        /** Left curly. */
        private DetailAST lcurly;
        /** Next token. */
        private DetailAST nextToken;
        /** Should check last right curly. */
        private boolean shouldCheckLastRcurly;
    }
}

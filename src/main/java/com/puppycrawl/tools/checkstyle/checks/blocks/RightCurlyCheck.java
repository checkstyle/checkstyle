////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

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
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="RightCurly"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check with policy
 * {@link RightCurlyOption#ALONE} for <code>else</code> and
 * <code>{@link TokenTypes#METHOD_DEF METHOD_DEF}</code>tokens is:
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
 */
public class RightCurlyCheck extends AbstractOptionCheck<RightCurlyOption> {
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

    /** Do we need to check if rculry starts line. */
    private boolean shouldStartLine = true;

    /**
     * Sets the right curly option to same.
     */
    public RightCurlyCheck() {
        super(RightCurlyOption.SAME, RightCurlyOption.class);
    }

    /**
     * Does the check need to check if rcurly starts line.
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
    public void visitToken(DetailAST ast) {
        final Details details = getDetails(ast);
        final DetailAST rcurly = details.rcurly;

        if (rcurly == null || rcurly.getType() != TokenTypes.RCURLY) {
            // we need to have both tokens to perform the check
            return;
        }

        final DetailAST lcurly = details.lcurly;

        validate(details, rcurly, lcurly);

        if (!shouldStartLine) {
            return;
        }
        final boolean startsLine =
                Utils.whitespaceBefore(rcurly.getColumnNo(),
                        getLines()[rcurly.getLineNo() - 1]);

        if (!startsLine && lcurly.getLineNo() != rcurly.getLineNo()) {
            log(rcurly, MSG_KEY_LINE_NEW, "}");
        }
    }

    /**
     * do general validation
     * @param details details
     * @param rcurly right curly token
     * @param lcurly left curly token
     */
    private void validate(Details details, DetailAST rcurly, DetailAST lcurly) {
        final DetailAST nextToken = details.nextToken;
        final boolean shouldCheckLastRcurly = details.shouldCheckLastRcurly;

        if (getAbstractOption() == RightCurlyOption.SAME && !hasLineBreakBefore(rcurly)) {
            log(rcurly, MSG_KEY_LINE_BREAK_BEFORE);
        }

        if (shouldCheckLastRcurly) {
            if (rcurly.getLineNo() == nextToken.getLineNo()) {
                log(rcurly, MSG_KEY_LINE_ALONE, "}");
            }
        }
        else if (getAbstractOption() == RightCurlyOption.SAME
                && rcurly.getLineNo() != nextToken.getLineNo()) {
            log(rcurly, MSG_KEY_LINE_SAME, "}");
        }
        else if (getAbstractOption() == RightCurlyOption.ALONE
                && rcurly.getLineNo() == nextToken.getLineNo()
                && !isEmptyBody(lcurly)) {
            log(rcurly, MSG_KEY_LINE_ALONE, "}");
        }
    }

    /**
     * Collect validation details
     * @param ast detail ast.
     * @return object that contain all details to make a validation.
     */
    private Details getDetails(DetailAST ast) {
        // Attempt to locate the tokens to do the check
        boolean shouldCheckLastRcurly = false;
        DetailAST rcurly = null;
        DetailAST lcurly = null;
        DetailAST nextToken = null;

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
                if (nextToken != null) {
                    lcurly = nextToken.getPreviousSibling();
                    rcurly = lcurly.getLastChild();
                }
                else {
                    shouldCheckLastRcurly = true;
                    nextToken = getNextToken(ast);
                    lcurly = ast.getLastChild();
                    rcurly = lcurly.getLastChild();
                }
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.LITERAL_FINALLY:
                shouldCheckLastRcurly = true;
                nextToken = getNextToken(ast);
                lcurly = ast.getFirstChild();
                rcurly = lcurly.getLastChild();
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
                nextToken = ast;
                break;
            case TokenTypes.METHOD_DEF:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
                lcurly = ast.findFirstToken(TokenTypes.SLIST);
                if (lcurly != null) {
                    //SLIST could be absent if method is abstract, and code like "while(true);"
                    rcurly = lcurly.getLastChild();
                }
                nextToken = lcurly;
                break;
            default:
                throw new IllegalStateException("Unexpected token type ("
                        + Utils.getTokenName(ast.getType()) + ")");
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
    private boolean isEmptyBody(DetailAST lcurly) {
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
    private DetailAST getNextToken(DetailAST ast) {
        DetailAST next = null;
        DetailAST parent = ast;
        while (parent != null && next == null) {
            next = parent.getNextSibling();
            parent = parent.getParent();
        }
        return CheckUtils.getFirstNode(next);
    }

    /**
     * Checks if right curly has line break before.
     * @param rightCurly
     *        Right curly token.
     * @return
     *        True, if right curly has line break before.
     */
    private boolean hasLineBreakBefore(DetailAST rightCurly) {
        if (rightCurly != null) {
            final DetailAST previousToken = rightCurly.getPreviousSibling();
            if (previousToken != null && rightCurly.getLineNo() == previousToken.getLineNo()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Structure that contains all details for validation
     */
    static class Details {
        /** right curly */
        private DetailAST rcurly;
        /** left curly */
        private DetailAST lcurly;
        /** next Token */
        private DetailAST nextToken;
        /** should Check Last Rcurly */
        private boolean shouldCheckLastRcurly;
    }
}

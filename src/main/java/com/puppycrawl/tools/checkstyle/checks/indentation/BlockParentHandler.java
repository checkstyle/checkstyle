////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for parents of blocks ('if', 'else', 'while', etc).
 * <P>
 * The "block" handler classes use a common superclass BlockParentHandler,
 * employing the Template Method pattern.
 * </P>
 *
 * <UL>
 *   <LI>template method to get the lcurly</LI>
 *   <LI>template method to get the rcurly</LI>
 *   <LI>if curlies aren't present, then template method to get expressions
 *       is called</LI>
 *   <LI>now all the repetitious code which checks for BOL, if curlies are on
 *       same line, etc. can be collapsed into the superclass</LI>
 * </UL>
 *
 *
 * @author jrichard
 */
public class BlockParentHandler extends AbstractExpressionHandler {
    /**
     * Children checked by parent handlers.
     */
    private static final int[] CHECKED_CHILDREN = {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.EXPR,
        TokenTypes.OBJBLOCK,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_THROW,
        TokenTypes.LITERAL_CONTINUE,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param name          the name of the handler
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public BlockParentHandler(IndentationCheck indentCheck,
        String name, DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, name, ast, parent);
    }

    /**
     * Returns array of token types which should be checked among children.
     * @return array of token types to check.
     */
    protected int[] getCheckedChildren() {
        return CHECKED_CHILDREN.clone();
    }

    /**
     * Get the top level expression being managed by this handler.
     *
     * @return the top level expression
     */
    protected DetailAST getTopLevelAst() {
        return getMainAst();
    }

    /**
     * Check the indent of the top level token.
     */
    protected void checkTopLevelToken() {
        final DetailAST topLevel = getTopLevelAst();

        if (topLevel != null
                && !getIndent().isAcceptable(expandedTabsColumnNo(topLevel))
                && !hasLabelBefore()
                && (shouldTopLevelStartLine() || isOnStartOfLine(topLevel))) {
            logError(topLevel, "", expandedTabsColumnNo(topLevel));
        }
    }

    /**
     * Check if the top level token has label before.
     * @return true if the top level token has label before.
     */
    protected boolean hasLabelBefore() {
        final DetailAST parent = getTopLevelAst().getParent();
        return parent.getType() == TokenTypes.LABELED_STAT
            && parent.getLineNo() == getTopLevelAst().getLineNo();
    }

    /**
     * Determines if the top level token must start the line.
     *
     * @return true
     */
    protected boolean shouldTopLevelStartLine() {
        return true;
    }

    /**
     * Determines if this block expression has curly braces.
     *
     * @return true if curly braces are present, false otherwise
     */
    protected boolean hasCurlies() {
        return getLCurly() != null && getRCurly() != null;
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLCurly() {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRCurly() {
        final DetailAST slist = getMainAst().findFirstToken(TokenTypes.SLIST);
        return slist.findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Check the indentation of the left curly brace.
     */
    protected void checkLCurly() {
        // the lcurly can either be at the correct indentation, or nested
        // with a previous expression
        final DetailAST lcurly = getLCurly();
        final int lcurlyPos = expandedTabsColumnNo(lcurly);

        if (!curlyIndent().isAcceptable(lcurlyPos) && isOnStartOfLine(lcurly)) {
            logError(lcurly, "lcurly", lcurlyPos, curlyIndent());
        }
    }

    /**
     * Get the expected indentation level for the curly braces.
     *
     * @return the curly brace indentation level
     */
    protected IndentLevel curlyIndent() {
        return new IndentLevel(getIndent(), getBraceAdjustment());
    }

    /**
     * Determines if the right curly brace must be at the start of the line.
     *
     * @return true
     */
    protected boolean shouldStartWithRCurly() {
        return true;
    }

    /**
     * Determines if child elements within the expression may be nested.
     *
     * @return false
     */
    protected boolean canChildrenBeNested() {
        return false;
    }

    /**
     * Check the indentation of the right curly brace.
     */
    protected void checkRCurly() {
        // the rcurly can either be at the correct indentation, or
        // on the same line as the lcurly
        final DetailAST lcurly = getLCurly();
        final DetailAST rcurly = getRCurly();
        final int rcurlyPos = expandedTabsColumnNo(rcurly);

        if (!curlyIndent().isAcceptable(rcurlyPos)
                && (shouldStartWithRCurly() || isOnStartOfLine(rcurly))
                && !areOnSameLine(rcurly, lcurly)) {
            logError(rcurly, "rcurly", rcurlyPos, curlyIndent());
        }
    }

    /**
     * Get the child element that is not a list of statements.
     *
     * @return the non-list child element
     */
    protected DetailAST getNonListChild() {
        return getMainAst().findFirstToken(TokenTypes.RPAREN).getNextSibling();
    }

    /**
     * Check the indentation level of a child that is not a list of statements.
     */
    private void checkNonListChild() {
        final DetailAST nonList = getNonListChild();
        if (nonList != null) {
            final IndentLevel expected = new IndentLevel(getIndent(), getBasicOffset());
            checkExpressionSubtree(nonList, expected, false, false);
        }
    }

    /**
     * Get the child element representing the list of statements.
     *
     * @return the statement list child
     */
    protected DetailAST getListChild() {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right parenthesis portion of the expression we are handling.
     *
     * @return the right parenthesis expression
     */
    protected DetailAST getRParen() {
        return getMainAst().findFirstToken(TokenTypes.RPAREN);
    }

    /**
     * Get the left parenthesis portion of the expression we are handling.
     *
     * @return the left parenthesis expression
     */
    protected DetailAST getLParen() {
        return getMainAst().findFirstToken(TokenTypes.LPAREN);
    }

    @Override
    public void checkIndentation() {
        checkTopLevelToken();
        // separate to allow for eventual configuration
        checkLParen(getLParen());
        checkRParen(getLParen(), getRParen());
        if (hasCurlies()) {
            checkLCurly();
            checkRCurly();
        }
        final DetailAST listChild = getListChild();
        if (listChild == null) {
            checkNonListChild();
        }
        else {
            // NOTE: switch statements usually don't have curlies
            if (!hasCurlies() || !areOnSameLine(getLCurly(), getRCurly())) {
                checkChildren(listChild,
                        getCheckedChildren(),
                        getChildrenExpectedIndent(),
                        true,
                        canChildrenBeNested());
            }
        }
    }

    /**
     * Gets indentation level expected for children.
     * @return indentation level expected for children
     */
    protected IndentLevel getChildrenExpectedIndent() {
        IndentLevel indentLevel = new IndentLevel(getIndent(), getBasicOffset());
        // if we have multileveled expected level then we should
        // try to suggest single level to children using curlies'
        // levels.
        if (getIndent().isMultiLevel() && hasCurlies()) {
            if (isOnStartOfLine(getLCurly())) {
                indentLevel = new IndentLevel(expandedTabsColumnNo(getLCurly()) + getBasicOffset());
            }
            else if (isOnStartOfLine(getRCurly())) {
                final IndentLevel level = new IndentLevel(curlyIndent(), getBasicOffset());
                level.addAcceptedIndent(level.getFirstIndentLevel() + getLineWrappingIndent());
                indentLevel = level;
            }
        }
        return indentLevel;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getChildrenExpectedIndent();
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndent() {
        return getIndentCheck().getLineWrappingIndentation();
    }
}

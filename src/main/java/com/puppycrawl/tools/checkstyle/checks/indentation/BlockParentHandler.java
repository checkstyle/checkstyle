///
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for parents of blocks ('if', 'else', 'while', etc).
 *
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
 */
public class BlockParentHandler extends AbstractExpressionHandler {

    /**
     * Children checked by parent handlers.
     */
    private static final int[] CHECKED_CHILDREN = {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.EXPR,
        TokenTypes.ANNOTATION,
        TokenTypes.OBJBLOCK,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_THROW,
        TokenTypes.LITERAL_CONTINUE,
        TokenTypes.CTOR_CALL,
        TokenTypes.SUPER_CTOR_CALL,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param name          the name of the handler
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public BlockParentHandler(IndentationCheck indentCheck,
        String name, DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, name, ast, parent);
    }

    /**
     * Returns array of token types which should be checked among children.
     *
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
                && isOnStartOfLine(topLevel)) {
            logError(topLevel, "", expandedTabsColumnNo(topLevel));
        }
    }

    /**
     * Determines if this block expression has curly braces.
     *
     * @return true if curly braces are present, false otherwise
     */
    private boolean hasCurlies() {
        return getLeftCurly() != null && getRightCurly() != null;
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRightCurly() {
        final DetailAST slist = getMainAst().findFirstToken(TokenTypes.SLIST);
        return slist.findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Check the indentation of the left curly brace.
     */
    private void checkLeftCurly() {
        // the lcurly can either be at the correct indentation, or nested
        // with a previous expression
        final DetailAST lcurly = getLeftCurly();
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
        final DetailAST lcurly = getLeftCurly();
        IndentLevel expIndentLevel = new IndentLevel(getIndent(), getBraceAdjustment());
        if (!isOnStartOfLine(lcurly)
            || lcurly.getParent().getType() == TokenTypes.INSTANCE_INIT) {
            expIndentLevel = new IndentLevel(getIndent(), 0);
        }

        return expIndentLevel;
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
    private void checkRightCurly() {
        final DetailAST rcurly = getRightCurly();
        final int rcurlyPos = expandedTabsColumnNo(rcurly);

        if (!curlyIndent().isAcceptable(rcurlyPos)
                && isOnStartOfLine(rcurly)) {
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

            final DetailAST nonListStartAst = getFirstAstNode(nonList);
            if (nonList != nonListStartAst) {
                checkExpressionSubtree(nonListStartAst, expected, false, false);
            }
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
    private DetailAST getRightParen() {
        return getMainAst().findFirstToken(TokenTypes.RPAREN);
    }

    /**
     * Get the left parenthesis portion of the expression we are handling.
     *
     * @return the left parenthesis expression
     */
    private DetailAST getLeftParen() {
        return getMainAst().findFirstToken(TokenTypes.LPAREN);
    }

    @Override
    public void checkIndentation() {
        checkTopLevelToken();
        // separate to allow for eventual configuration
        checkLeftParen(getLeftParen());
        checkRightParen(getLeftParen(), getRightParen());
        if (hasCurlies()) {
            checkLeftCurly();
            checkRightCurly();
        }
        final DetailAST listChild = getListChild();
        if (listChild == null) {
            checkNonListChild();
        }
        else {
            // NOTE: switch statements usually don't have curlies
            if (!hasCurlies() || !TokenUtil.areOnSameLine(getLeftCurly(), getRightCurly())) {
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
     *
     * @return indentation level expected for children
     */
    protected IndentLevel getChildrenExpectedIndent() {
        IndentLevel indentLevel = new IndentLevel(getIndent(), getBasicOffset());
        // if we have multileveled expected level then we should
        // try to suggest single level to children using curlies'
        // levels.
        if (getIndent().isMultiLevel() && hasCurlies()) {
            if (isOnStartOfLine(getLeftCurly())) {
                indentLevel = new IndentLevel(expandedTabsColumnNo(getLeftCurly())
                        + getBasicOffset());
            }
            else if (isOnStartOfLine(getRightCurly())) {
                final IndentLevel level = new IndentLevel(curlyIndent(), getBasicOffset());
                indentLevel = IndentLevel.addAcceptable(level, level.getFirstIndentLevel()
                        + getLineWrappingIndent());
            }
        }
        if (hasCurlies() && isOnStartOfLine(getLeftCurly())) {
            indentLevel = IndentLevel.addAcceptable(indentLevel,
                    curlyIndent().getFirstIndentLevel() + getBasicOffset());
        }
        return indentLevel;
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getChildrenExpectedIndent();
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     *
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndent() {
        return getIndentCheck().getLineWrappingIndentation();
    }

}

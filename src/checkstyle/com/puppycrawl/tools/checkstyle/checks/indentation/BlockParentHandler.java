////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
 * <P>
 * <UL>
 *   <LI>template method to get the lcurly</LI>
 *   <LI>template method to get the rcurly</LI>
 *   <LI>if curlys aren't present, then template method to get expressions
 *       is called</LI>
 *   <LI>now all the repetitous code which checks for BOL, if curlys are on
 *       same line, etc. can be collapsed into  the superclass</LI>
 * </UL>
 *
 * @author jrichard
 */
public class BlockParentHandler extends ExpressionHandler
{
    /**
     * Children checked by parent handlers.
     */
    private static final int[] CHECKED_CHILDREN = new int[] {
        TokenTypes.VARIABLE_DEF,
        TokenTypes.EXPR,
        TokenTypes.OBJBLOCK,
        TokenTypes.LITERAL_BREAK,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aName          the name of the handler
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public BlockParentHandler(IndentationCheck aIndentCheck,
        String aName, DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, aName, aAst, aParent);
    }

    /**
     * Get the top level expression being managed by this handler.
     *
     * @return the top level expression
     */
    protected DetailAST getToplevelAST()
    {
        return getMainAst();
    }

    /**
     * Check the indent of the top level token.
     */
    protected void checkToplevelToken()
    {
        final DetailAST toplevel = getToplevelAST();

        if (toplevel == null
            || getLevel().accept(expandedTabsColumnNo(toplevel)))
        {
            return;
        }
        if (!toplevelMustStartLine() && !startsLine(toplevel)) {
            return;
        }
        logError(toplevel, "", expandedTabsColumnNo(toplevel));
    }

    /**
     * Determines if the top level token must start the line.
     *
     * @return true
     */
    protected boolean toplevelMustStartLine()
    {
        return true;
    }

    /**
     * Determines if this block expression has curly braces.
     *
     * @return true if curly braces are present, false otherwise
     */
    protected boolean hasCurlys()
    {
        return (getLCurly() != null) && (getRCurly() != null);
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRCurly()
    {
        final DetailAST slist = getMainAst().findFirstToken(TokenTypes.SLIST);
        if (slist == null) {
            return null;
        }

        return slist.findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Check the indentation of the left curly brace.
     */
    protected void checkLCurly()
    {
        // the lcurly can either be at the correct indentation, or nested
        // with a previous expression
        final DetailAST lcurly = getLCurly();
        final int lcurlyPos = expandedTabsColumnNo(lcurly);

        if (lcurly == null
            || curlyLevel().accept(lcurlyPos)
            || !startsLine(lcurly))
        {
            return;
        }

        logError(lcurly, "lcurly", lcurlyPos);
    }

    /**
     * Get the expected indentation level for the curly braces.
     *
     * @return the curly brace indentation level
     */
    private IndentLevel curlyLevel()
    {
        return new IndentLevel(getLevel(), getBraceAdjustement());
    }

    /**
     * Determines if the right curly brace must be at the start of the line.
     *
     * @return true
     */
    protected boolean rcurlyMustStart()
    {
        return true;
    }

    /**
     * Determines if child elements within the expression may be nested.
     *
     * @return false
     */
    protected boolean childrenMayNest()
    {
        return false;
    }

    /**
     * Check the indentation of the right curly brace.
     */
    protected void checkRCurly()
    {
        // the rcurly can either be at the correct indentation, or
        // on the same line as the lcurly
        DetailAST lcurly = getLCurly();
        DetailAST rcurly = getRCurly();
        int rcurlyPos = expandedTabsColumnNo(rcurly);

        if (rcurly == null
            || curlyLevel().accept(rcurlyPos)
            || (!rcurlyMustStart() && !startsLine(rcurly))
            || areOnSameLine(rcurly, lcurly))
        {
            return;
        }
        logError(rcurly, "rcurly", rcurlyPos, curlyLevel());
    }

    /**
     * Get the child element that is not a list of statements.
     *
     * @return the non-list child element
     */
    protected DetailAST getNonlistChild()
    {
        return (DetailAST) getMainAst().findFirstToken(
            TokenTypes.RPAREN).getNextSibling();
    }

    /**
     * Check the indentation level of a child that is not a list of statements.
     */
    private void checkNonlistChild()
    {
        // TODO: look for SEMI and check for it here?
        DetailAST nonlist = getNonlistChild();
        if (nonlist == null) {
            return;
        }

        IndentLevel expected = new IndentLevel(getLevel(), getBasicOffset());

        checkExpressionSubtree(nonlist, expected, false, false);
    }

    /**
     * Get the child element representing the list of statements.
     *
     * @return the statement list child
     */
    protected DetailAST getListChild()
    {
        return getMainAst().findFirstToken(TokenTypes.SLIST);
    }

    /**
     * Get the right parenthesis portion of the expression we are handling.
     *
     * @return the right parenthis expression
     */
    protected DetailAST getRParen()
    {
        return getMainAst().findFirstToken(TokenTypes.RPAREN);
    }

    /**
     * Get the left parenthesis portion of the expression we are handling.
     *
     * @return the left parenthis expression
     */
    protected DetailAST getLParen()
    {
        return getMainAst().findFirstToken(TokenTypes.LPAREN);
    }

    /**
     * Check the indentation of the right parenthesis.
     */
    protected void checkRParen()
    {
        final DetailAST rparen = getRParen();

        // no paren - no check :)
        if (rparen == null) {
            return;
        }

        // the rcurly can either be at the correct indentation,
        // or not first on the line ...
        final int rparenLevel = expandedTabsColumnNo(rparen);
        if (getLevel().accept(rparenLevel) || !startsLine(rparen)) {
            return;
        }

        // or has <lparen level> + 1 indentation
        final DetailAST lparen = getLParen();
        final int lparenLevel = expandedTabsColumnNo(lparen);
        if (rparenLevel == (lparenLevel + 1)) {
            return;
        }

        logError(rparen, "rparen", expandedTabsColumnNo(rparen));
    }

    /**
     * Check the indentation of the left parenthesis.
     */
    protected void checkLParen()
    {
        // the rcurly can either be at the correct indentation, or on the
        // same line as the lcurly
        DetailAST lparen = getLParen();
        if (lparen == null
            || getLevel().accept(expandedTabsColumnNo(lparen))
            || !startsLine(lparen))
        {
            return;
        }
        logError(lparen, "lparen", expandedTabsColumnNo(lparen));
    }

    /**
     * Check the indentation of the expression we are handling.
     */
    public void checkIndentation()
    {
        checkToplevelToken();
        // seperate to allow for eventual configuration
        checkLParen();
        checkRParen();
        if (hasCurlys()) {
            checkLCurly();
            checkRCurly();
        }
        DetailAST listChild = getListChild();
        if (listChild != null) {
            // NOTE: switch statements usually don't have curlys
            if (!hasCurlys() || !areOnSameLine(getLCurly(), getRCurly())) {
                checkChildren(listChild,
                              CHECKED_CHILDREN,
                              getChildrenExpectedLevel(),
                              true,
                              childrenMayNest());
            }
        }
        else {
            checkNonlistChild();
        }
    }

    /**
     * @return indentation level expected for children
     */
    protected IndentLevel getChildrenExpectedLevel()
    {
        return new IndentLevel(getLevel(), getBasicOffset());
    }
}

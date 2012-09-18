////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
        TokenTypes.LITERAL_RETURN,
        TokenTypes.LITERAL_THROW,
        TokenTypes.LITERAL_CONTINUE,
    };

    /**
     * Returns array of token types which should be checked among childrens.
     * @return array of token types to check.
     */
    protected int[] getCheckedChildren()
    {
        return CHECKED_CHILDREN;
    }

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

        if ((toplevel == null)
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

        if ((lcurly == null)
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
        final DetailAST lcurly = getLCurly();
        final DetailAST rcurly = getRCurly();
        final int rcurlyPos = expandedTabsColumnNo(rcurly);

        if ((rcurly == null)
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
        return getMainAst().findFirstToken(
            TokenTypes.RPAREN).getNextSibling();
    }

    /**
     * Check the indentation level of a child that is not a list of statements.
     */
    private void checkNonlistChild()
    {
        // TODO: look for SEMI and check for it here?
        final DetailAST nonlist = getNonlistChild();
        if (nonlist == null) {
            return;
        }

        final IndentLevel expected =
            new IndentLevel(getLevel(), getBasicOffset());
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

    @Override
    public void checkIndentation()
    {
        checkToplevelToken();
        // seperate to allow for eventual configuration
        checkLParen(getLParen());
        checkRParen(getLParen(), getRParen());
        if (hasCurlys()) {
            checkLCurly();
            checkRCurly();
        }
        final DetailAST listChild = getListChild();
        if (listChild != null) {
            // NOTE: switch statements usually don't have curlys
            if (!hasCurlys() || !areOnSameLine(getLCurly(), getRCurly())) {
                checkChildren(listChild,
                              getCheckedChildren(),
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
        // if we have multileveled expected level then we should
        // try to suggest single level to children using curlies'
        // levels.
        if (getLevel().isMultiLevel() && hasCurlys()
            && !areOnSameLine(getLCurly(), getRCurly()))
        {
            if (startsLine(getLCurly())) {
                return new IndentLevel(expandedTabsColumnNo(getLCurly())
                                       + getBasicOffset());
            }
            else if (startsLine(getRCurly())) {
                return new IndentLevel(expandedTabsColumnNo(getRCurly())
                                       + getBasicOffset());
            }
        }
        return new IndentLevel(getLevel(), getBasicOffset());
    }

    @Override
    public IndentLevel suggestedChildLevel(ExpressionHandler aChild)
    {
        return getChildrenExpectedLevel();
    }

}

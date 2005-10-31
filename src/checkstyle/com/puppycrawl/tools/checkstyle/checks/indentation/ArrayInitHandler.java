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
 * Handler for array initialization blocks.
 *
 * @author jrichard
 */
public class ArrayInitHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ArrayInitHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "array initialization", aAst, aParent);
    }

    /**
     * Compute the indentation amount for this handler.
     *
     * @return the expected indentation amount
     */
    protected IndentLevel getLevelImpl()
    {
        final DetailAST parentAST = getMainAst().getParent();
        final int type = parentAST.getType();
        if (type == TokenTypes.LITERAL_NEW || type == TokenTypes.ASSIGN) {
            // note: assumes new or assignment is line to align with
            return new IndentLevel(getLineStart(parentAST));
        }
        else if (getParent() instanceof ArrayInitHandler) {
            return ((ArrayInitHandler) getParent()).getChildrenExpectedLevel();
        }
        else {
            return getParent().getLevel();
        }
    }

    /**
     * There is no top level expression for this handler.
     *
     * @return null
     */
    protected DetailAST getToplevelAST()
    {
        return null;
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLCurly()
    {
        return getMainAst();
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * Determines if the right curly brace must be at the start of the line.
     *
     * @return false
     */
    protected boolean rcurlyMustStart()
    {
        return false;
    }

    /**
     * Determines if child elements within the expression may be nested.
     *
     * @return true
     */
    protected boolean childrenMayNest()
    {
        return true;
    }

    /**
     * Get the child element representing the list of statements.
     *
     * @return the statement list child
     */
    protected DetailAST getListChild()
    {
        return getMainAst();
    }

    /** {@inheritDoc} */
    protected IndentLevel getChildrenExpectedLevel()
    {
        // now we accept
        // new int[] {1,
        //            2};
        // and
        // new int[] {1, 2,
        //     3};

        final IndentLevel expectedIndent = super.getChildrenExpectedLevel();

        final int firstLine = getFirstLine(Integer.MAX_VALUE, getListChild());
        if (hasCurlys() && firstLine == getLCurly().getLineNo()) {
            final int lcurlyPos = expandedTabsColumnNo(getLCurly());
            final int firstChildPos =
                getNextFirstNonblankOnLineAfter(firstLine, lcurlyPos);
            if (firstChildPos >= 0) {
                expectedIndent.addAcceptedIndent(firstChildPos);
            }
        }
        return expectedIndent;
    }

    /**
     * @param aLineNo   number of line on which we search
     * @param aColumnNo number of column after which we search
     *
     * @return column number of first non-blank char after
     *         specified column on specified line or -1 if
     *         such char doesn't exist.
     */
    private int getNextFirstNonblankOnLineAfter(int aLineNo, int aColumnNo)
    {
        int columnNo = aColumnNo + 1;
        final String line = getIndentCheck().getLines()[aLineNo - 1];
        final int lineLength = line.length();
        while (columnNo < lineLength
               && Character.isWhitespace(line.charAt(columnNo)))
        {
            columnNo++;
        }

        return (columnNo == lineLength) ? -1 : columnNo;
    }
}

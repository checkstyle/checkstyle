////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.Arrays;

/**
 * Abstract base class for all handlers.
 *
 * @author jrichard
 */
public abstract class ExpressionHandler
{
    /**
     * The instance of <code>IndentationCheck</code> using this handler.
     */
    private final IndentationCheck mIndentCheck;

    /** the AST which is handled by this handler */
    private final DetailAST mMainAst;

    /** name used during output to user */
    private final String mTypeName;

    /** containing AST handler */
    private final ExpressionHandler mParent;

    /** indentation amount for this handler */
    private IndentLevel mLevel;

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aTypeName      the name of the handler
     * @param aExpr          the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ExpressionHandler(IndentationCheck aIndentCheck,
            String aTypeName, DetailAST aExpr, ExpressionHandler aParent)
    {
        mIndentCheck = aIndentCheck;
        mTypeName = aTypeName;
        mMainAst = aExpr;
        mParent = aParent;
    }

    /**
     * Get the indentation amount for this handler. For performance reasons,
     * this value is cached. The first time this method is called, the
     * indentation amount is computed and stored. On further calls, the stored
     * value is returned.
     *
     * @return the expected indentation amount
     */
    public final IndentLevel getLevel()
    {
        if (mLevel == null) {
            mLevel = getLevelImpl();
        }
        return mLevel;
    }

    /**
     * Compute the indentation amount for this handler.
     *
     * @return the expected indentation amount
     */
    protected IndentLevel getLevelImpl()
    {
        return mParent.suggestedChildLevel(this);
    }

    /**
     * Indentation level suggested for a child element. Children don't have
     * to respect this, but most do.
     *
     * @param aChild  child AST (so suggestion level can differ based on child
     *                  type)
     *
     * @return suggested indentation for child
     */
    public IndentLevel suggestedChildLevel(ExpressionHandler aChild)
    {
        return new IndentLevel(getLevel(), getBasicOffset());
    }

    /**
     * Log an indentation error.
     *
     * @param aAst           the expression that caused the error
     * @param aSubtypeName   the type of the expression
     * @param aActualLevel    the actual indent level of the expression
     */
    protected final void logError(DetailAST aAst, String aSubtypeName,
                                  int aActualLevel)
    {
        logError(aAst, aSubtypeName, aActualLevel, getLevel());
    }

    /**
     * Log an indentation error.
     *
     * @param aAst           the expression that caused the error
     * @param aSubtypeName   the type of the expression
     * @param aActualLevel   the actual indent level of the expression
     * @param aExpectedLevel the expected indent level of the expression
     */
    protected final void logError(DetailAST aAst, String aSubtypeName,
                                  int aActualLevel, IndentLevel aExpectedLevel)
    {
        final String typeStr =
            ("".equals(aSubtypeName) ? "" : (" " + aSubtypeName));
        mIndentCheck.indentationLog(aAst.getLineNo(), "indentation.error",
                mTypeName + typeStr, aActualLevel, aExpectedLevel);
    }

    /**
     * Log child indentation error.
     *
     * @param aLine           the expression that caused the error
     * @param aActualLevel   the actual indent level of the expression
     * @param aExpectedLevel the expected indent level of the expression
     */
    private void logChildError(int aLine,
                               int aActualLevel,
                               IndentLevel aExpectedLevel)
    {
        mIndentCheck.indentationLog(aLine, "indentation.child.error",
                mTypeName, aActualLevel, aExpectedLevel);
    }

    /**
     * Determines if the given expression is at the start of a line.
     *
     * @param aAst   the expression to check
     *
     * @return true if it is, false otherwise
     */
    protected final boolean startsLine(DetailAST aAst)
    {
        return getLineStart(aAst) == expandedTabsColumnNo(aAst);
    }

    /**
     * Determines if two expressions are on the same line.
     *
     * @param aAst1   the first expression
     * @param aAst2   the second expression
     *
     * @return true if they are, false otherwise
     */
    static boolean areOnSameLine(DetailAST aAst1, DetailAST aAst2)
    {
        return (aAst1 != null) && (aAst2 != null)
            && (aAst1.getLineNo() == aAst2.getLineNo());
    }

    /**
     * Searchs in given sub-tree (including given node) for the token
     * which represents first symbol for this sub-tree in file.
     * @param aAST a root of sub-tree in which the search shoul be performed.
     * @return a token which occurs first in the file.
     */
    static DetailAST getFirstToken(DetailAST aAST)
    {
        DetailAST first = aAST;
        DetailAST child = aAST.getFirstChild();

        while (child != null) {
            final DetailAST toTest = getFirstToken(child);
            if ((toTest.getLineNo() < first.getLineNo())
                || ((toTest.getLineNo() == first.getLineNo())
                    && (toTest.getColumnNo() < first.getColumnNo())))
            {
                first = toTest;
            }
            child = child.getNextSibling();
        }

        return first;
    }

    /**
     * Get the start of the line for the given expression.
     *
     * @param aAst   the expression to find the start of the line for
     *
     * @return the start of the line for the given expression
     */
    protected final int getLineStart(DetailAST aAst)
    {
        final String line = mIndentCheck.getLines()[aAst.getLineNo() - 1];
        return getLineStart(line);
    }

    // TODO: this whole checking of consecuitive/expression line indents is
    // smelling pretty bad... and is in serious need of pruning.  But, I
    // want to finish the invalid tests before I start messing around with
    // it.

    /**
     * Check the indentation of consecutive lines for the expression we are
     * handling.
     *
     * @param aStartLine     the first line to check
     * @param aEndLine       the last line to check
     * @param aIndentLevel   the required indent level
     */
    protected final void checkLinesIndent(int aStartLine, int aEndLine,
        IndentLevel aIndentLevel)
    {
        // check first line
        checkSingleLine(aStartLine, aIndentLevel);

        // check following lines
        final IndentLevel offsetLevel =
            new IndentLevel(aIndentLevel, getBasicOffset());
        for (int i = aStartLine + 1; i <= aEndLine; i++) {
            checkSingleLine(i, offsetLevel);
        }
    }

    /**
     * @return true if indentation should be increased after
     *              fisrt line in checkLinesIndent()
     *         false otherwise
     */
    protected boolean shouldIncreaseIndent()
    {
        return true;
    }

    /**
     * Check the indentation for a set of lines.
     *
     * @param aLines              the set of lines to check
     * @param aIndentLevel        the indentation level
     * @param aFirstLineMatches   whether or not the first line has to match
     * @param aFirstLine          firstline of whole expression
     */
    private void checkLinesIndent(LineSet aLines,
                                  IndentLevel aIndentLevel,
                                  boolean aFirstLineMatches,
                                  int aFirstLine)
    {
        if (aLines.isEmpty()) {
            return;
        }

        // check first line
        final int startLine = aLines.firstLine();
        final int endLine = aLines.lastLine();
        final int startCol = aLines.firstLineCol();

        final int realStartCol =
            getLineStart(mIndentCheck.getLines()[startLine - 1]);

        if (realStartCol == startCol) {
            checkSingleLine(startLine, startCol, aIndentLevel,
                aFirstLineMatches);
        }

        // if first line starts the line, following lines are indented
        // one level; but if the first line of this expression is
        // nested with the previous expression (which is assumed if it
        // doesn't start the line) then don't indent more, the first
        // indentation is absorbed by the nesting

        // TODO: shouldIncreaseIndent() is a hack, should be removed
        //       after complete rewriting of checkExpressionSubtree()

        IndentLevel theLevel = aIndentLevel;
        if (aFirstLineMatches
            || ((aFirstLine > mMainAst.getLineNo()) && shouldIncreaseIndent()))
        {
            theLevel = new IndentLevel(aIndentLevel, getBasicOffset());
        }

        // check following lines
        for (int i = startLine + 1; i <= endLine; i++) {
            final Integer col = aLines.getStartColumn(i);
            // startCol could be null if this line didn't have an
            // expression that was required to be checked (it could be
            // checked by a child expression)

            if (col != null) {
                checkSingleLine(i, col.intValue(), theLevel, false);
            }
        }
    }

    /**
     * Check the indent level for a single line.
     *
     * @param aLineNum       the line number to check
     * @param aIndentLevel   the required indent level
     */
    private void checkSingleLine(int aLineNum, IndentLevel aIndentLevel)
    {
        final String line = mIndentCheck.getLines()[aLineNum - 1];
        final int start = getLineStart(line);
        if (aIndentLevel.gt(start)) {
            logChildError(aLineNum, start, aIndentLevel);
        }
    }

    /**
     * Check the indentation for a single line.
     *
     * @param aLineNum       the number of the line to check
     * @param aColNum        the column number we are starting at
     * @param aIndentLevel   the indentation level
     * @param aMustMatch     whether or not the indentation level must match
     */

    private void checkSingleLine(int aLineNum, int aColNum,
        IndentLevel aIndentLevel, boolean aMustMatch)
    {
        final String line = mIndentCheck.getLines()[aLineNum - 1];
        final int start = getLineStart(line);
        // if must match is set, it is an error if the line start is not
        // at the correct indention level; otherwise, it is an only an
        // error if this statement starts the line and it is less than
        // the correct indentation level
        if (aMustMatch ? !aIndentLevel.accept(start)
            : (aColNum == start) && aIndentLevel.gt(start))
        {
            logChildError(aLineNum, start, aIndentLevel);
        }
    }

    /**
     * Get the start of the specified line.
     *
     * @param aLine   the specified line number
     *
     * @return the start of the specified line
     */
    protected final int getLineStart(String aLine)
    {
        for (int start = 0; start < aLine.length(); start++) {
            final char c = aLine.charAt(start);

            if (!Character.isWhitespace(c)) {
                return Utils.lengthExpandedTabs(
                    aLine, start, mIndentCheck.getIndentationTabWidth());
            }
        }
        return 0;
    }

    // TODO: allowNesting either shouldn't be allowed with
    //  firstLineMatches, or I should change the firstLineMatches logic
    //  so it doesn't match if the first line is nested

    /**
     * Check the indent level of the children of the specified parent
     * expression.
     *
     * @param aParent             the parent whose children we are checking
     * @param aTokenTypes         the token types to check
     * @param aStartLevel         the starting indent level
     * @param aFirstLineMatches   whether or not the first line needs to match
     * @param aAllowNesting       whether or not nested children are allowed
     */
    protected final void checkChildren(DetailAST aParent,
                                       int[] aTokenTypes,
                                       IndentLevel aStartLevel,
                                       boolean aFirstLineMatches,
                                       boolean aAllowNesting)
    {
        Arrays.sort(aTokenTypes);
        for (DetailAST child = aParent.getFirstChild();
                child != null;
                child = child.getNextSibling())
        {
            if (Arrays.binarySearch(aTokenTypes, child.getType()) >= 0) {
                checkExpressionSubtree(child, aStartLevel,
                    aFirstLineMatches, aAllowNesting);
            }
        }
    }

    /**
     * Check the indentation level for an expression subtree.
     *
     * @param aTree               the expression subtree to check
     * @param aLevel              the indentation level
     * @param aFirstLineMatches   whether or not the first line has to match
     * @param aAllowNesting       whether or not subtree nesting is allowed
     */
    protected final void checkExpressionSubtree(
        DetailAST aTree,
        IndentLevel aLevel,
        boolean aFirstLineMatches,
        boolean aAllowNesting
    )
    {
        final LineSet subtreeLines = new LineSet();
        final int firstLine = getFirstLine(Integer.MAX_VALUE, aTree);
        if (aFirstLineMatches && !aAllowNesting) {
            subtreeLines.addLineAndCol(firstLine,
                getLineStart(mIndentCheck.getLines()[firstLine - 1]));
        }
        findSubtreeLines(subtreeLines, aTree, aAllowNesting);

        checkLinesIndent(subtreeLines, aLevel, aFirstLineMatches, firstLine);
    }

    /**
     * Get the first line for a given expression.
     *
     * @param aStartLine   the line we are starting from
     * @param aTree        the expression to find the first line for
     *
     * @return the first line of the expression
     */
    protected final int getFirstLine(int aStartLine, DetailAST aTree)
    {
        // find line for this node
        // TODO: getLineNo should probably not return < 0, but it is for
        // the interface methods... I should ask about this
        int realStart = aStartLine;
        final int currLine = aTree.getLineNo();
        if (currLine < realStart) {
            realStart = currLine;
        }

        // check children
        for (DetailAST node = aTree.getFirstChild();
            node != null;
            node = node.getNextSibling())
        {
            realStart = getFirstLine(realStart, node);
        }

        return realStart;
    }

    /**
     * Get the column number for the start of a given expression, expanding
     * tabs out into spaces in the process.
     *
     * @param aAST   the expression to find the start of
     *
     * @return the column number for the start of the expression
     */
    protected final int expandedTabsColumnNo(DetailAST aAST)
    {
        final String line =
            mIndentCheck.getLines()[aAST.getLineNo() - 1];

        return Utils.lengthExpandedTabs(line, aAST.getColumnNo(),
            mIndentCheck.getIndentationTabWidth());
    }

    /**
     * Find the set of lines for a given subtree.
     *
     * @param aLines          the set of lines to add to
     * @param aTree           the subtree to examine
     * @param aAllowNesting   whether or not to allow nested subtrees
     */
    protected final void findSubtreeLines(LineSet aLines, DetailAST aTree,
        boolean aAllowNesting)
    {
        // find line for this node
        // TODO: getLineNo should probably not return < 0, but it is for
        // the interface methods... I should ask about this

        if (getIndentCheck().getHandlerFactory().isHandledType(aTree.getType())
            || (aTree.getLineNo() < 0))
        {
            return;
        }

        // TODO: the problem with this is that not all tree tokens actually
        // have the right column number -- I should get a list of these
        // and verify that checking nesting this way won't cause problems
//          if (aAllowNesting && aTree.getColumnNo() != getLineStart(aTree)) {
//              return;
//          }

        final int lineNum = aTree.getLineNo();
        final Integer colNum = aLines.getStartColumn(lineNum);

        final int thisLineColumn = expandedTabsColumnNo(aTree);
        if ((colNum == null) || (thisLineColumn < colNum.intValue())) {
            aLines.addLineAndCol(lineNum, thisLineColumn);
        }

        // check children
        for (DetailAST node = aTree.getFirstChild();
            node != null;
            node = node.getNextSibling())
        {
            findSubtreeLines(aLines, node, aAllowNesting);
        }
    }

    /**
     * Check the indentation level of modifiers.
     */
    protected final void checkModifiers()
    {
        final DetailAST modifiers =
            mMainAst.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST modifier = modifiers.getFirstChild();
             modifier != null;
             modifier = modifier.getNextSibling())
        {
            /*
            if (!areOnSameLine(modifier, prevExpr)) {
                continue;
            }
            */
            if (startsLine(modifier)
                && !getLevel().accept(expandedTabsColumnNo(modifier)))
            {
                logError(modifier, "modifier",
                    expandedTabsColumnNo(modifier));
            }
        }
    }

    /**
     * Check the indentation of the expression we are handling.
     */
    public abstract void checkIndentation();

    /**
     * Accessor for the IndentCheck attribute.
     *
     * @return the IndentCheck attribute
     */
    protected final IndentationCheck getIndentCheck()
    {
        return mIndentCheck;
    }

    /**
     * Accessor for the MainAst attribute.
     *
     * @return the MainAst attribute
     */
    protected final DetailAST getMainAst()
    {
        return mMainAst;
    }

    /**
     * Accessor for the Parent attribute.
     *
     * @return the Parent attribute
     */
    protected final ExpressionHandler getParent()
    {
        return mParent;
    }

    /**
     * A shortcut for <code>IndentationCheck</code> property.
     * @return value of basicOffset property of <code>IndentationCheck</code>
     */
    protected final int getBasicOffset()
    {
        return getIndentCheck().getBasicOffset();
    }

    /**
     * A shortcut for <code>IndentationCheck</code> property.
     * @return value of braceAdjustment property
     *         of <code>IndentationCheck</code>
     */
    protected final int getBraceAdjustement()
    {
        return getIndentCheck().getBraceAdjustement();
    }

    /**
     * Check the indentation of the right parenthesis.
     * @param aRparen parenthesis to check
     * @param aLparen left parenthesis associated with aRparen
     */
    protected final void checkRParen(DetailAST aLparen, DetailAST aRparen)
    {
        // no paren - no check :)
        if (aRparen == null) {
            return;
        }

        // the rcurly can either be at the correct indentation,
        // or not first on the line ...
        final int rparenLevel = expandedTabsColumnNo(aRparen);
        if (getLevel().accept(rparenLevel) || !startsLine(aRparen)) {
            return;
        }

        // or has <lparen level> + 1 indentation
        final int lparenLevel = expandedTabsColumnNo(aLparen);
        if (rparenLevel == (lparenLevel + 1)) {
            return;
        }

        logError(aRparen, "rparen", rparenLevel);
    }

    /**
     * Check the indentation of the left parenthesis.
     * @param aLparen parenthesis to check
     */
    protected final void checkLParen(final DetailAST aLparen)
    {
        // the rcurly can either be at the correct indentation, or on the
        // same line as the lcurly
        if ((aLparen == null)
            || getLevel().accept(expandedTabsColumnNo(aLparen))
            || !startsLine(aLparen))
        {
            return;
        }
        logError(aLparen, "lparen", expandedTabsColumnNo(aLparen));
    }
}

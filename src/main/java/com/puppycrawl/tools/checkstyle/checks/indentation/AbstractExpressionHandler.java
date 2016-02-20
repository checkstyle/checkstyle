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

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Abstract base class for all handlers.
 *
 * @author jrichard
 */
public abstract class AbstractExpressionHandler {
    /**
     * The instance of {@code IndentationCheck} using this handler.
     */
    private final IndentationCheck indentCheck;

    /** The AST which is handled by this handler. */
    private final DetailAST mainAst;

    /** Name used during output to user. */
    private final String typeName;

    /** Containing AST handler. */
    private final AbstractExpressionHandler parent;

    /** Indentation amount for this handler. */
    private IndentLevel indent;

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param typeName      the name of the handler
     * @param expr          the abstract syntax tree
     * @param parent        the parent handler
     */
    protected AbstractExpressionHandler(IndentationCheck indentCheck, String typeName,
            DetailAST expr, AbstractExpressionHandler parent) {
        this.indentCheck = indentCheck;
        this.typeName = typeName;
        mainAst = expr;
        this.parent = parent;
    }

    /**
     * Check the indentation of the expression we are handling.
     */
    public abstract void checkIndentation();

    /**
     * Get the indentation amount for this handler. For performance reasons,
     * this value is cached. The first time this method is called, the
     * indentation amount is computed and stored. On further calls, the stored
     * value is returned.
     *
     * @return the expected indentation amount
     */
    public final IndentLevel getIndent() {
        if (indent == null) {
            indent = getIndentImpl();
        }
        return indent;
    }

    /**
     * Compute the indentation amount for this handler.
     *
     * @return the expected indentation amount
     */
    protected IndentLevel getIndentImpl() {
        return parent.getSuggestedChildIndent(this);
    }

    /**
     * Indentation level suggested for a child element. Children don't have
     * to respect this, but most do.
     *
     * @param child  child AST (so suggestion level can differ based on child
     *                  type)
     *
     * @return suggested indentation for child
     */
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return new IndentLevel(getIndent(), getBasicOffset());
    }

    /**
     * Log an indentation error.
     *
     * @param ast           the expression that caused the error
     * @param subtypeName   the type of the expression
     * @param actualIndent  the actual indent level of the expression
     */
    protected final void logError(DetailAST ast, String subtypeName,
                                  int actualIndent) {
        logError(ast, subtypeName, actualIndent, getIndent());
    }

    /**
     * Log an indentation error.
     *
     * @param ast            the expression that caused the error
     * @param subtypeName    the type of the expression
     * @param actualIndent   the actual indent level of the expression
     * @param expectedIndent the expected indent level of the expression
     */
    protected final void logError(DetailAST ast, String subtypeName,
                                  int actualIndent, IndentLevel expectedIndent) {
        final String typeStr;

        if (subtypeName.isEmpty()) {
            typeStr = "";
        }
        else {
            typeStr = " " + subtypeName;
        }
        String messageKey = IndentationCheck.MSG_ERROR;
        if (expectedIndent.isMultiLevel()) {
            messageKey = IndentationCheck.MSG_ERROR_MULTI;
        }
        indentCheck.indentationLog(ast.getLineNo(), messageKey,
            typeName + typeStr, actualIndent, expectedIndent);
    }

    /**
     * Log child indentation error.
     *
     * @param line           the expression that caused the error
     * @param actualIndent   the actual indent level of the expression
     * @param expectedIndent the expected indent level of the expression
     */
    private void logChildError(int line,
                               int actualIndent,
                               IndentLevel expectedIndent) {
        String messageKey = IndentationCheck.MSG_CHILD_ERROR;
        if (expectedIndent.isMultiLevel()) {
            messageKey = IndentationCheck.MSG_CHILD_ERROR_MULTI;
        }
        indentCheck.indentationLog(line, messageKey,
            typeName, actualIndent, expectedIndent);
    }

    /**
     * Determines if the given expression is at the start of a line.
     *
     * @param ast   the expression to check
     *
     * @return true if it is, false otherwise
     */
    protected final boolean isOnStartOfLine(DetailAST ast) {
        return getLineStart(ast) == expandedTabsColumnNo(ast);
    }

    /**
     * Determines if two expressions are on the same line.
     *
     * @param ast1   the first expression
     * @param ast2   the second expression
     *
     * @return true if they are, false otherwise
     */
    public static boolean areOnSameLine(DetailAST ast1, DetailAST ast2) {
        return ast1.getLineNo() == ast2.getLineNo();
    }

    /**
     * Searches in given sub-tree (including given node) for the token
     * which represents first symbol for this sub-tree in file.
     * @param ast a root of sub-tree in which the search should be performed.
     * @return a token which occurs first in the file.
     */
    public static DetailAST getFirstToken(DetailAST ast) {
        DetailAST first = ast;
        DetailAST child = ast.getFirstChild();

        while (child != null) {
            final DetailAST toTest = getFirstToken(child);
            if (toTest.getColumnNo() < first.getColumnNo()) {
                first = toTest;
            }
            child = child.getNextSibling();
        }

        return first;
    }

    /**
     * Get the start of the line for the given expression.
     *
     * @param ast   the expression to find the start of the line for
     *
     * @return the start of the line for the given expression
     */
    protected final int getLineStart(DetailAST ast) {
        final String line = indentCheck.getLine(ast.getLineNo() - 1);
        return getLineStart(line);
    }

    /**
     * Get the start of the specified line.
     *
     * @param line   the specified line number
     *
     * @return the start of the specified line
     */
    protected final int getLineStart(String line) {
        int index = 0;
        while (index < line.length() && Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return CommonUtils.lengthExpandedTabs(
            line, index, indentCheck.getIndentationTabWidth());
    }

    /**
     * Checks that indentation should be increased after first line in checkLinesIndent().
     * @return true if indentation should be increased after
     *              first line in checkLinesIndent()
     *         false otherwise
     */
    protected boolean shouldIncreaseIndent() {
        return true;
    }

    /**
     * Check the indentation of consecutive lines for the expression we are
     * handling.
     *
     * @param startLine     the first line to check
     * @param endLine       the last line to check
     * @param indentLevel   the required indent level
     */
    protected final void checkLinesIndent(int startLine, int endLine,
        IndentLevel indentLevel) {
        // check first line
        checkLineIndent(startLine, indentLevel);

        // check following lines
        final IndentLevel offsetLevel =
            new IndentLevel(indentLevel, getBasicOffset());
        for (int i = startLine + 1; i <= endLine; i++) {
            checkLineIndent(i, offsetLevel);
        }
    }

    /**
     * Check the indentation for a set of lines.
     *
     * @param lines              the set of lines to check
     * @param indentLevel        the indentation level
     * @param firstLineMatches   whether or not the first line has to match
     * @param firstLine          first line of whole expression
     */
    private void checkLinesIndent(LineSet lines,
                                  IndentLevel indentLevel,
                                  boolean firstLineMatches,
                                  int firstLine) {
        if (lines.isEmpty()) {
            return;
        }

        // check first line
        final int startLine = lines.firstLine();
        final int endLine = lines.lastLine();
        final int startCol = lines.firstLineCol();

        final int realStartCol =
            getLineStart(indentCheck.getLine(startLine - 1));

        if (realStartCol == startCol) {
            checkLineIndent(startLine, startCol, indentLevel,
                firstLineMatches);
        }

        // if first line starts the line, following lines are indented
        // one level; but if the first line of this expression is
        // nested with the previous expression (which is assumed if it
        // doesn't start the line) then don't indent more, the first
        // indentation is absorbed by the nesting

        IndentLevel theLevel = indentLevel;
        if (firstLineMatches
            || firstLine > mainAst.getLineNo() && shouldIncreaseIndent()) {
            theLevel = new IndentLevel(indentLevel, getBasicOffset());
        }

        // check following lines
        for (int i = startLine + 1; i <= endLine; i++) {
            final Integer col = lines.getStartColumn(i);
            // startCol could be null if this line didn't have an
            // expression that was required to be checked (it could be
            // checked by a child expression)

            if (col != null) {
                checkLineIndent(i, col, theLevel, false);
            }
        }
    }

    /**
     * Check the indent level for a single line.
     *
     * @param lineNum       the line number to check
     * @param indentLevel   the required indent level
     */
    private void checkLineIndent(int lineNum, IndentLevel indentLevel) {
        final String line = indentCheck.getLine(lineNum - 1);
        if (!line.isEmpty()) {
            final int start = getLineStart(line);
            if (indentLevel.isGreaterThan(start)) {
                logChildError(lineNum, start, indentLevel);
            }
        }
    }

    /**
     * Check the indentation for a single line.
     *
     * @param lineNum       the number of the line to check
     * @param colNum        the column number we are starting at
     * @param indentLevel   the indentation level
     * @param mustMatch     whether or not the indentation level must match
     */
    private void checkLineIndent(int lineNum, int colNum,
        IndentLevel indentLevel, boolean mustMatch) {
        final String line = indentCheck.getLine(lineNum - 1);
        final int start = getLineStart(line);
        // if must match is set, it is an error if the line start is not
        // at the correct indention level; otherwise, it is an only an
        // error if this statement starts the line and it is less than
        // the correct indentation level
        if (mustMatch && !indentLevel.isAcceptable(start)
                || !mustMatch && colNum == start && indentLevel.isGreaterThan(start)) {
            logChildError(lineNum, start, indentLevel);
        }
    }

    /**
     * Checks indentation on wrapped lines between and including
     * {@code firstNode} and {@code lastNode}.
     *
     * @param firstNode First node to start examining.
     * @param lastNode Last node to examine inclusively.
     */
    protected void checkWrappingIndentation(DetailAST firstNode, DetailAST lastNode) {
        indentCheck.getLineWrappingHandler().checkIndentation(firstNode, lastNode);
    }

    /**
     * Check the indent level of the children of the specified parent
     * expression.
     *
     * @param parentNode         the parent whose children we are checking
     * @param tokenTypes         the token types to check
     * @param startIndent        the starting indent level
     * @param firstLineMatches   whether or not the first line needs to match
     * @param allowNesting       whether or not nested children are allowed
     */
    protected final void checkChildren(DetailAST parentNode,
                                       int[] tokenTypes,
                                       IndentLevel startIndent,
                                       boolean firstLineMatches,
                                       boolean allowNesting) {
        Arrays.sort(tokenTypes);
        for (DetailAST child = parentNode.getFirstChild();
                child != null;
                child = child.getNextSibling()) {
            if (Arrays.binarySearch(tokenTypes, child.getType()) >= 0) {
                checkExpressionSubtree(child, startIndent,
                    firstLineMatches, allowNesting);
            }
        }
    }

    /**
     * Check the indentation level for an expression subtree.
     *
     * @param tree               the expression subtree to check
     * @param indentLevel        the indentation level
     * @param firstLineMatches   whether or not the first line has to match
     * @param allowNesting       whether or not subtree nesting is allowed
     */
    protected final void checkExpressionSubtree(
        DetailAST tree,
        IndentLevel indentLevel,
        boolean firstLineMatches,
        boolean allowNesting
    ) {
        final LineSet subtreeLines = new LineSet();
        final int firstLine = getFirstLine(Integer.MAX_VALUE, tree);
        if (firstLineMatches && !allowNesting) {
            subtreeLines.addLineAndCol(firstLine,
                getLineStart(indentCheck.getLine(firstLine - 1)));
        }
        findSubtreeLines(subtreeLines, tree, allowNesting);

        checkLinesIndent(subtreeLines, indentLevel, firstLineMatches, firstLine);
    }

    /**
     * Get the first line for a given expression.
     *
     * @param startLine   the line we are starting from
     * @param tree        the expression to find the first line for
     *
     * @return the first line of the expression
     */
    protected final int getFirstLine(int startLine, DetailAST tree) {
        int realStart = startLine;
        final int currLine = tree.getLineNo();
        if (currLine < realStart) {
            realStart = currLine;
        }

        // check children
        for (DetailAST node = tree.getFirstChild();
            node != null;
            node = node.getNextSibling()) {
            realStart = getFirstLine(realStart, node);
        }

        return realStart;
    }

    /**
     * Get the column number for the start of a given expression, expanding
     * tabs out into spaces in the process.
     *
     * @param ast   the expression to find the start of
     *
     * @return the column number for the start of the expression
     */
    protected final int expandedTabsColumnNo(DetailAST ast) {
        final String line =
            indentCheck.getLine(ast.getLineNo() - 1);

        return CommonUtils.lengthExpandedTabs(line, ast.getColumnNo(),
            indentCheck.getIndentationTabWidth());
    }

    /**
     * Find the set of lines for a given subtree.
     *
     * @param lines          the set of lines to add to
     * @param tree           the subtree to examine
     * @param allowNesting   whether or not to allow nested subtrees
     */
    protected final void findSubtreeLines(LineSet lines, DetailAST tree,
        boolean allowNesting) {
        if (indentCheck.getHandlerFactory().isHandledType(tree.getType())) {
            return;
        }

        final int lineNum = tree.getLineNo();
        final Integer colNum = lines.getStartColumn(lineNum);

        final int thisLineColumn = expandedTabsColumnNo(tree);
        if (colNum == null || thisLineColumn < colNum) {
            lines.addLineAndCol(lineNum, thisLineColumn);
        }

        // check children
        for (DetailAST node = tree.getFirstChild();
            node != null;
            node = node.getNextSibling()) {
            findSubtreeLines(lines, node, allowNesting);
        }
    }

    /**
     * Check the indentation level of modifiers.
     */
    protected void checkModifiers() {
        final DetailAST modifiers =
            mainAst.findFirstToken(TokenTypes.MODIFIERS);
        for (DetailAST modifier = modifiers.getFirstChild();
             modifier != null;
             modifier = modifier.getNextSibling()) {
            if (isOnStartOfLine(modifier)
                && !getIndent().isAcceptable(expandedTabsColumnNo(modifier))) {
                logError(modifier, "modifier",
                    expandedTabsColumnNo(modifier));
            }
        }
    }

    /**
     * Accessor for the IndentCheck attribute.
     *
     * @return the IndentCheck attribute
     */
    protected final IndentationCheck getIndentCheck() {
        return indentCheck;
    }

    /**
     * Accessor for the MainAst attribute.
     *
     * @return the MainAst attribute
     */
    protected final DetailAST getMainAst() {
        return mainAst;
    }

    /**
     * Accessor for the Parent attribute.
     *
     * @return the Parent attribute
     */
    protected final AbstractExpressionHandler getParent() {
        return parent;
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     * @return value of basicOffset property of {@code IndentationCheck}
     */
    protected final int getBasicOffset() {
        return indentCheck.getBasicOffset();
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     * @return value of braceAdjustment property
     *         of {@code IndentationCheck}
     */
    protected final int getBraceAdjustment() {
        return indentCheck.getBraceAdjustment();
    }

    /**
     * Check the indentation of the right parenthesis.
     * @param rparen parenthesis to check
     * @param lparen left parenthesis associated with aRparen
     */
    protected final void checkRParen(DetailAST lparen, DetailAST rparen) {
        if (rparen != null) {
            // the rcurly can either be at the correct indentation,
            // or not first on the line
            final int rparenLevel = expandedTabsColumnNo(rparen);
            // or has <lparen level> + 1 indentation
            final int lparenLevel = expandedTabsColumnNo(lparen);

            if (rparenLevel != lparenLevel + 1
                    && !getIndent().isAcceptable(rparenLevel)
                    && isOnStartOfLine(rparen)) {
                logError(rparen, "rparen", rparenLevel);
            }
        }
    }

    /**
     * Check the indentation of the left parenthesis.
     * @param lparen parenthesis to check
     */
    protected final void checkLParen(final DetailAST lparen) {
        // the rcurly can either be at the correct indentation, or on the
        // same line as the lcurly
        if (lparen == null
            || getIndent().isAcceptable(expandedTabsColumnNo(lparen))
            || !isOnStartOfLine(lparen)) {
            return;
        }
        logError(lparen, "lparen", expandedTabsColumnNo(lparen));
    }
}

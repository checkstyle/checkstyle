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

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * This class checks line-wrapping into definitions and expressions. The
 * line-wrapping indentation should be not less then value of the
 * lineWrappingIndentation parameter.
 *
 * @author maxvetrenko
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class LineWrappingHandler {

    /**
     * The current instance of {@code IndentationCheck} class using this
     * handler. This field used to get access to private fields of
     * IndentationCheck instance.
     */
    private final IndentationCheck indentCheck;

    /**
     * Sets values of class field, finds last node and calculates indentation level.
     *
     * @param instance
     *            instance of IndentationCheck.
     */
    public LineWrappingHandler(IndentationCheck instance) {
        indentCheck = instance;
    }

    /**
     * Checks line wrapping into expressions and definitions using property
     * 'lineWrappingIndentation'.
     *
     * @param firstNode First node to start examining.
     * @param lastNode Last node to examine inclusively.
     */
    public void checkIndentation(DetailAST firstNode, DetailAST lastNode) {
        checkIndentation(firstNode, lastNode, indentCheck.getLineWrappingIndentation());
    }

    /**
     * Checks line wrapping into expressions and definitions.
     *
     * @param firstNode First node to start examining.
     * @param lastNode Last node to examine inclusively.
     * @param indentLevel Indentation all wrapped lines should use.
     */
    public void checkIndentation(DetailAST firstNode, DetailAST lastNode, int indentLevel) {
        final NavigableMap<Integer, DetailAST> firstNodesOnLines = collectFirstNodes(firstNode,
                lastNode);

        final DetailAST firstLineNode = firstNodesOnLines.get(firstNodesOnLines.firstKey());
        if (firstLineNode.getType() == TokenTypes.AT) {
            checkAnnotationIndentation(firstLineNode, firstNodesOnLines, indentLevel);
        }

        // First node should be removed because it was already checked before.
        firstNodesOnLines.remove(firstNodesOnLines.firstKey());
        final int firstNodeIndent = getLineStart(firstLineNode);
        final int currentIndent = firstNodeIndent + indentLevel;

        for (DetailAST node : firstNodesOnLines.values()) {
            final int currentType = node.getType();

            if (currentType == TokenTypes.RPAREN) {
                logWarningMessage(node, firstNodeIndent);
            }
            else if (currentType != TokenTypes.RCURLY && currentType != TokenTypes.ARRAY_INIT) {
                logWarningMessage(node, currentIndent);
            }
        }
    }

    /**
     * Finds first nodes on line and puts them into Map.
     *
     * @param firstNode First node to start examining.
     * @param lastNode Last node to examine inclusively.
     * @return NavigableMap which contains lines numbers as a key and first
     *         nodes on lines as a values.
     */
    private NavigableMap<Integer, DetailAST> collectFirstNodes(DetailAST firstNode,
            DetailAST lastNode) {
        final NavigableMap<Integer, DetailAST> result = new TreeMap<>();

        result.put(firstNode.getLineNo(), firstNode);
        DetailAST curNode = firstNode.getFirstChild();

        while (curNode != lastNode) {

            if (curNode.getType() == TokenTypes.OBJBLOCK
                    || curNode.getType() == TokenTypes.SLIST) {
                curNode = curNode.getLastChild();
            }

            final DetailAST firstTokenOnLine = result.get(curNode.getLineNo());

            if (firstTokenOnLine == null
                || expandedTabsColumnNo(firstTokenOnLine) >= expandedTabsColumnNo(curNode)) {
                result.put(curNode.getLineNo(), curNode);
            }
            curNode = getNextCurNode(curNode);
        }
        return result;
    }

    /**
     * Returns next curNode node.
     *
     * @param curNode current node.
     * @return next curNode node.
     */
    private static DetailAST getNextCurNode(DetailAST curNode) {
        DetailAST nodeToVisit = curNode.getFirstChild();
        DetailAST currentNode = curNode;

        while (nodeToVisit == null) {
            nodeToVisit = currentNode.getNextSibling();
            if (nodeToVisit == null) {
                currentNode = currentNode.getParent();
            }
        }
        return nodeToVisit;
    }

    /**
     * Checks line wrapping into annotations.
     *
     * @param atNode at-clause node.
     * @param firstNodesOnLines map which contains
     *     first nodes as values and line numbers as keys.
     * @param indentLevel line wrapping indentation.
     */
    private void checkAnnotationIndentation(DetailAST atNode,
            NavigableMap<Integer, DetailAST> firstNodesOnLines, int indentLevel) {
        final int firstNodeIndent = getLineStart(atNode);
        final int currentIndent = firstNodeIndent + indentLevel;
        final Collection<DetailAST> values = firstNodesOnLines.values();
        final DetailAST lastAnnotationNode = getLastAnnotationNode(atNode);
        final int lastAnnotationLine = lastAnnotationNode.getLineNo();

        final Iterator<DetailAST> itr = values.iterator();
        while (firstNodesOnLines.size() > 1) {
            final DetailAST node = itr.next();

            if (node.getLineNo() <= lastAnnotationLine) {
                final DetailAST parentNode = node.getParent();
                final boolean isCurrentNodeCloseAnnotationAloneInLine =
                        node.getLineNo() == lastAnnotationLine
                        && node.equals(lastAnnotationNode);
                if (isCurrentNodeCloseAnnotationAloneInLine
                        || node.getType() == TokenTypes.AT
                        && parentNode.getParent().getType() == TokenTypes.MODIFIERS) {
                    logWarningMessage(node, firstNodeIndent);
                }
                else {
                    logWarningMessage(node, currentIndent);
                }
                itr.remove();
            }
            else {
                break;
            }
        }
    }

    /**
     * Get the column number for the start of a given expression, expanding
     * tabs out into spaces in the process.
     *
     * @param ast   the expression to find the start of
     *
     * @return the column number for the start of the expression
     */
    private int expandedTabsColumnNo(DetailAST ast) {
        final String line =
            indentCheck.getLine(ast.getLineNo() - 1);

        return CommonUtils.lengthExpandedTabs(line, ast.getColumnNo(),
            indentCheck.getIndentationTabWidth());
    }

    /**
     * Get the start of the line for the given expression.
     *
     * @param ast   the expression to find the start of the line for
     *
     * @return the start of the line for the given expression
     */
    private int getLineStart(DetailAST ast) {
        final String line = indentCheck.getLine(ast.getLineNo() - 1);
        return getLineStart(line);
    }

    /**
     * Get the start of the specified line.
     *
     * @param line the specified line number
     *
     * @return the start of the specified line
     */
    private int getLineStart(String line) {
        int index = 0;
        while (Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return CommonUtils.lengthExpandedTabs(line, index, indentCheck.getIndentationTabWidth());
    }

    /**
     * Finds and returns last annotation node.
     * @param atNode first at-clause node.
     * @return last annotation node.
     */
    private static DetailAST getLastAnnotationNode(DetailAST atNode) {
        DetailAST lastAnnotation = atNode.getParent();
        while (lastAnnotation.getNextSibling() != null
                && lastAnnotation.getNextSibling().getType() == TokenTypes.ANNOTATION) {
            lastAnnotation = lastAnnotation.getNextSibling();
        }
        return lastAnnotation.getLastChild();
    }

    /**
     * Logs warning message if indentation is incorrect.
     *
     * @param currentNode
     *            current node which probably invoked an error.
     * @param currentIndent
     *            correct indentation.
     */
    private void logWarningMessage(DetailAST currentNode, int currentIndent) {
        if (indentCheck.isForceStrictCondition()) {
            if (expandedTabsColumnNo(currentNode) != currentIndent) {
                indentCheck.indentationLog(currentNode.getLineNo(),
                        IndentationCheck.MSG_ERROR, currentNode.getText(),
                        expandedTabsColumnNo(currentNode), currentIndent);
            }
        }
        else {
            if (expandedTabsColumnNo(currentNode) < currentIndent) {
                indentCheck.indentationLog(currentNode.getLineNo(),
                        IndentationCheck.MSG_ERROR, currentNode.getText(),
                        expandedTabsColumnNo(currentNode), currentIndent);
            }
        }
    }
}

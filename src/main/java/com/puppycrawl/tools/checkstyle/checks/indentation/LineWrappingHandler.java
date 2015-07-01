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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This class checks line-wrapping into definitions and expressions. The
 * line-wrapping indentation should be not less then value of the
 * lineWrappingIndentation parameter.
 *
 * @author maxvetrenko
 *
 */
public class LineWrappingHandler {

    /**
     * The current instance of <code>IndentationCheck</code> class using this
     * handler. This field used to get access to private fields of
     * IndentationCheck instance.
     */
    private final IndentationCheck indentCheck;

    /**
     * Root node for current expression.
     */
    private DetailAST firstNode;

    /**
     * Last node for current expression.
     */
    private DetailAST lastNode;

    /**
     * User's value of line wrapping indentation.
     */
    private int indentLevel;

    /**
     * Force strict condition in line wrapping case.
     */
    private boolean forceStrictCondition;

    /**
     * Sets values of class field, finds last node and calculates indentation level.
     *
     * @param instance
     *            instance of IndentationCheck.
     * @param firstNode
     *            root node for current expression.
     * @param lastNode
     *            last node for current expression.
     */
    public LineWrappingHandler(IndentationCheck instance, DetailAST firstNode, DetailAST lastNode) {
        indentCheck = instance;
        this.firstNode = firstNode;
        this.lastNode = lastNode;
        indentLevel = indentCheck.getLineWrappingIndentation();
        forceStrictCondition = indentCheck.isForceStrictCondition();
    }

    /**
     *  Getter for lastNode field
     *  @return lastNode field
     */
    protected final DetailAST getLastNode() {
        return lastNode;
    }

    /**
     * Checks line wrapping into expressions and definitions.
     */
    public void checkIndentation() {
        final NavigableMap<Integer, DetailAST> firstNodesOnLines = collectFirstNodes();

        final DetailAST firstNode = firstNodesOnLines.get(firstNodesOnLines.firstKey());
        if (firstNode.getType() == TokenTypes.AT) {
            checkAnnotationIndentation(firstNode, firstNodesOnLines);
        }

        // First node should be removed because it was already checked before.
        firstNodesOnLines.remove(firstNodesOnLines.firstKey());
        final int firstNodeIndent = getFirstNodeIndent(firstNode);
        final int currentIndent = firstNodeIndent + indentLevel;

        for (DetailAST node : firstNodesOnLines.values()) {
            final int currentType = node.getType();

            if (currentType == TokenTypes.RCURLY
                    || currentType == TokenTypes.RPAREN
                    || currentType == TokenTypes.ARRAY_INIT) {
                logWarningMessage(node, firstNodeIndent);
            }
            else if (currentType == TokenTypes.LITERAL_IF) {
                final DetailAST parent = node.getParent();

                if (parent.getType() == TokenTypes.LITERAL_ELSE) {
                    logWarningMessage(parent, currentIndent);
                }
            }
            else {
                logWarningMessage(node, currentIndent);
            }
        }
    }

    /**
     * Calculates indentation of first node.
     *
     * @param node
     *            first node.
     * @return indentation of first node.
     */
    private int getFirstNodeIndent(DetailAST node) {
        int indentLevel = node.getColumnNo();

        if (node.getType() == TokenTypes.LITERAL_IF
                && node.getParent().getType() == TokenTypes.LITERAL_ELSE) {
            final DetailAST lcurly = node.getParent().getPreviousSibling();
            final DetailAST rcurly = lcurly.getLastChild();

            if (lcurly.getType() == TokenTypes.SLIST
                    && rcurly.getLineNo() == node.getLineNo()) {
                indentLevel = rcurly.getColumnNo();
            }
            else {
                indentLevel = node.getParent().getColumnNo();
            }
        }
        return indentLevel;
    }

    /**
     * Finds first nodes on line and puts them into Map.
     *
     * @return NavigableMap which contains lines numbers as a key and first
     *         nodes on lines as a values.
     */
    private NavigableMap<Integer, DetailAST> collectFirstNodes() {
        final NavigableMap<Integer, DetailAST> result = new TreeMap<>();

        result.put(firstNode.getLineNo(), firstNode);
        DetailAST curNode = firstNode.getFirstChild();

        while (curNode != null && curNode != lastNode) {

            if (curNode.getType() == TokenTypes.OBJBLOCK) {
                curNode = curNode.getNextSibling();
            }

            if (curNode != null) {
                final DetailAST firstTokenOnLine = result.get(curNode.getLineNo());

                if (firstTokenOnLine == null
                        || firstTokenOnLine != null
                        && firstTokenOnLine.getColumnNo() >= curNode.getColumnNo()) {
                    result.put(curNode.getLineNo(), curNode);
                }
                curNode = getNextCurNode(curNode);
            }
        }
        return result;
    }

    /**
     * Returns next curNode node.
     *
     * @param curNode current node.
     * @return next curNode node.
     */
    private DetailAST getNextCurNode(DetailAST curNode) {
        DetailAST nodeToVisit = curNode.getFirstChild();
        DetailAST currentNode = curNode;

        while (currentNode != null && nodeToVisit == null) {
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
     */
    private void checkAnnotationIndentation(DetailAST atNode,
            NavigableMap<Integer, DetailAST> firstNodesOnLines) {
        final int currentIndent = atNode.getColumnNo() + indentLevel;
        final int firstNodeIndent = atNode.getColumnNo();
        final Collection<DetailAST> values = firstNodesOnLines.values();
        final DetailAST lastAnnotationNode = getLastAnnotationNode(atNode);
        final int lastAnnotationLine = lastAnnotationNode.getLineNo();
        final int lastAnnotattionColumn = lastAnnotationNode.getColumnNo();

        final Iterator<DetailAST> itr = values.iterator();
        while (itr.hasNext() && firstNodesOnLines.size() > 1) {
            final DetailAST node = itr.next();

            if (node.getLineNo() < lastAnnotationLine
                    || node.getLineNo() == lastAnnotationLine
                    && node.getColumnNo() <= lastAnnotattionColumn) {
                final DetailAST parentNode = node.getParent();
                if (node.getType() == TokenTypes.AT
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
     * Finds and returns last annotation node.
     * @param atNode first at-clause node.
     * @return last annotation node.
     */
    private DetailAST getLastAnnotationNode(DetailAST atNode) {
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
        if (forceStrictCondition) {
            if (currentNode.getColumnNo() != currentIndent) {
                indentCheck.indentationLog(currentNode.getLineNo(),
                        "indentation.error", currentNode.getText(),
                        currentNode.getColumnNo(), currentIndent);
            }
        }
        else {
            if (currentNode.getColumnNo() < currentIndent) {
                indentCheck.indentationLog(currentNode.getLineNo(),
                        "indentation.error", currentNode.getText(),
                        currentNode.getColumnNo(), currentIndent);
            }
        }
    }
}

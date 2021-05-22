////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * This class checks line-wrapping into definitions and expressions. The
 * line-wrapping indentation should be not less than value of the
 * lineWrappingIndentation parameter.
 *
 */
public class LineWrappingHandler {

    /**
     * Enum to be used for test if first line's indentation should be checked or not.
     */
    public enum LineWrappingOptions {

        /**
         * First line's indentation should NOT be checked.
         */
        IGNORE_FIRST_LINE,
        /**
         * First line's indentation should be checked.
         */
        NONE;

        /**
         * Builds enum value from boolean.
         *
         * @param val value.
         * @return enum instance.
         *
         * @noinspection BooleanParameter
         */
        public static LineWrappingOptions ofBoolean(boolean val) {
            LineWrappingOptions option = NONE;
            if (val) {
                option = IGNORE_FIRST_LINE;
            }
            return option;
        }

    }

    /**
     * The list of ignored token types for being checked by lineWrapping indentation
     * inside {@code checkIndentation()} as these tokens are checked for lineWrapping
     * inside their dedicated handlers.
     *
     * @see NewHandler#getIndentImpl()
     * @see BlockParentHandler#curlyIndent()
     * @see ArrayInitHandler#getIndentImpl()
     */
    private static final int[] IGNORED_LIST = {
        TokenTypes.RCURLY,
        TokenTypes.LITERAL_NEW,
        TokenTypes.ARRAY_INIT,
    };

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
    private void checkIndentation(DetailAST firstNode, DetailAST lastNode, int indentLevel) {
        checkIndentation(firstNode, lastNode, indentLevel,
                -1, LineWrappingOptions.IGNORE_FIRST_LINE);
    }

    /**
     * Checks line wrapping into expressions and definitions.
     *
     * @param firstNode First node to start examining.
     * @param lastNode Last node to examine inclusively.
     * @param indentLevel Indentation all wrapped lines should use.
     * @param startIndent Indentation first line before wrapped lines used.
     * @param ignoreFirstLine Test if first line's indentation should be checked or not.
     */
    public void checkIndentation(DetailAST firstNode, DetailAST lastNode, int indentLevel,
            int startIndent, LineWrappingOptions ignoreFirstLine) {
        final NavigableMap<Integer, DetailAST> firstNodesOnLines = collectFirstNodes(firstNode,
                lastNode);

        final DetailAST firstLineNode = firstNodesOnLines.get(firstNodesOnLines.firstKey());
        if (firstLineNode.getType() == TokenTypes.AT) {
            checkForAnnotationIndentation(firstNodesOnLines, indentLevel);
        }

        if (ignoreFirstLine == LineWrappingOptions.IGNORE_FIRST_LINE) {
            // First node should be removed because it was already checked before.
            firstNodesOnLines.remove(firstNodesOnLines.firstKey());
        }

        final int firstNodeIndent;
        if (startIndent == -1) {
            firstNodeIndent = getLineStart(firstLineNode);
        }
        else {
            firstNodeIndent = startIndent;
        }
        final int currentIndent = firstNodeIndent + indentLevel;

        for (DetailAST node : firstNodesOnLines.values()) {
            final int currentType = node.getType();
            if (checkForNullParameterChild(node) || checkForMethodLparenNewLine(node)) {
                continue;
            }
            if (currentType == TokenTypes.RPAREN) {
                logWarningMessage(node, new IndentLevel(firstNodeIndent));
            }
            else if (!TokenUtil.isOfType(currentType, IGNORED_LIST)) {
                final int[] offsets =
                    additionalOffsets(firstNode, node, currentIndent);
                logWarningMessage(node, new IndentLevel(
                        new IndentLevel(currentIndent), offsets));
            }
        }
    }

    /**
     * Returns possible offsets in addition to baseIndent for {@code node}.
     *
     * @param firstNode Node on the highest line.
     * @param node Node to examine.
     * @param baseIndent Current indentation level.
     * @return Indentation offsets.
     */
    private int[] additionalOffsets(final DetailAST firstNode, DetailAST node, int baseIndent) {
        int[] result = {0};
        if (indentCheck.isForceStrictCondition()) {
            final int additionalOffset = expressionStartsBetween(firstNode, node)
                * indentCheck.getLineWrappingIndentation();
            final int previousOffset = getPreviousElementIndentation(node) - baseIndent;
            if (additionalOffset >= previousOffset) {
                result = new int[] {additionalOffset};
            }
            else {
                result = new int[] {additionalOffset, previousOffset};
            }
        }
        return result;
    }

    /**
     * Returns indentation level of previous element.
     *
     * @param node Node to start examining.
     * @return Indentation level of previous element or zero.
     */
    private static int getPreviousElementIndentation(DetailAST node) {
        int result = 0;
        if (node != null) {
            if (node.getType() == TokenTypes.PARAMETERS) {
                result = node.getPreviousSibling().getColumnNo() + 1;
            }
            else if (node.getType() == TokenTypes.ELIST) {
                if (node.getPreviousSibling() != null
                        && node.getPreviousSibling().getType() == TokenTypes.LPAREN) {
                    result = node.getPreviousSibling().getColumnNo() + 1;
                }
                else if (node.getParent().getType() == TokenTypes.METHOD_CALL) {
                    result = node.getParent().getColumnNo() + 1;
                }
                else {
                    result = 0;
                }
            }
            else if (node.getType() == TokenTypes.ARRAY_INIT) {
                result = node.getColumnNo() + 1;
            }
            else {
                result = getPreviousElementIndentation(node.getParent());
            }
        }
        return result;
    }

    /**
     * Counts the beginnings of expressions in previous lines between {@code firstNode} and
     * {@code node}.
     *
     * @param firstNode Node on the highest line.
     * @param node Node to start examining.
     * @return Number of expression starts (negative, if there are more expression ends than starts
     *            between {@code firstNode} and {@code node}.
     */
    public static int expressionStartsBetween(final DetailAST firstNode, DetailAST node) {
        final int result;
        if (node == firstNode) {
            result = 0;
        }
        else if (node.getType() == TokenTypes.LPAREN
                || node.getType() == TokenTypes.GENERIC_START) {
            // lparen / generic_start is in own line -> skip
            result = expressionStartsBetween(firstNode, node.getParent());
        }
        else {
            result = numberOfSingleLparenPreviousSiblings(node)
                + expressionStartsBetween(firstNode, node.getParent());
        }
        return result;
    }

    /**
     * Counts the number of previous siblings starting new expressions.
     *
     * @param node Node to start examining.
     * @return Number of previous siblings starting new expressions.
     */
    private static int numberOfSingleLparenPreviousSiblings(DetailAST node) {
        int previousSiblings = 0;
        if (node.getPreviousSibling() == null) {
            previousSiblings = firstNodeIsLparen(node);
        }
        else if (shouldExamineNodeType(node)) {
            if (node.getType() == TokenTypes.LPAREN) {
                previousSiblings =
                        numberOfSingleLparenPreviousSiblings(node.getPreviousSibling()) + 1;
            }
            else if (node.getType() == TokenTypes.RPAREN) {
                previousSiblings =
                        numberOfSingleLparenPreviousSiblings(node.getPreviousSibling()) - 1;
            }
            else {
                previousSiblings = numberOfSingleLparenPreviousSiblings(node.getPreviousSibling());
            }
        }
        else if (node.getType() == TokenTypes.ELIST
                && node.getParent().getType() == TokenTypes.METHOD_CALL
                && node.getLineNo() != node.getParent().getLineNo()) {
            previousSiblings = 1;
        }
        return previousSiblings;
    }

    /**
     * Decides if node should be examined in
     * {@link #numberOfSingleLparenPreviousSiblings(DetailAST)}.
     *
     * @param node Actual node.
     * @return true, if node should be examined.
     */
    private static boolean shouldExamineNodeType(DetailAST node) {
        return node.getType() != TokenTypes.FOR_INIT && node.getType() != TokenTypes.ELIST
            && (node.getType() != TokenTypes.PARAMETERS
                    || node.getParent().getType() == TokenTypes.LAMBDA);
    }

    /**
     * Checks if node is lparen or generic_start.
     *
     * @param node Node to examine.
     * @return 1 if node is lparen or generic_start, otherwise 0.
     */
    private static int firstNodeIsLparen(DetailAST node) {
        int previousSiblings = 0;
        if (node.getType() == TokenTypes.LPAREN || node.getType() == TokenTypes.GENERIC_START) {
            previousSiblings = 1;
        }
        return previousSiblings;
    }

    /**
     * Checks for annotation indentation.
     *
     * @param firstNodesOnLines the nodes which are present in the beginning of each line.
     * @param indentLevel line wrapping indentation.
     */
    public void checkForAnnotationIndentation(
            NavigableMap<Integer, DetailAST> firstNodesOnLines, int indentLevel) {
        final DetailAST firstLineNode = firstNodesOnLines.get(firstNodesOnLines.firstKey());
        DetailAST node = firstLineNode.getParent();
        while (node != null) {
            if (node.getType() == TokenTypes.ANNOTATION) {
                final DetailAST atNode = node.getFirstChild();
                final NavigableMap<Integer, DetailAST> annotationLines =
                        firstNodesOnLines.subMap(
                                node.getLineNo(),
                                true,
                                getNextNodeLine(firstNodesOnLines, node),
                                true
                        );
                checkAnnotationIndentation(atNode, annotationLines, indentLevel);
            }
            node = node.getNextSibling();
        }
    }

    /**
     * Checks whether parameter node has any child or not.
     *
     * @param node the node for which to check.
     * @return true if  parameter has no child.
     */
    public static boolean checkForNullParameterChild(DetailAST node) {
        return node.getFirstChild() == null && node.getType() == TokenTypes.PARAMETERS;
    }

    /**
     * Checks whether the method lparen starts from a new line or not.
     *
     * @param node the node for which to check.
     * @return true if method lparen starts from a new line.
     */
    public static boolean checkForMethodLparenNewLine(DetailAST node) {
        final int parentType = node.getParent().getType();
        return parentType == TokenTypes.METHOD_DEF && node.getType() == TokenTypes.LPAREN;
    }

    /**
     * Gets the next node line from the firstNodesOnLines map unless there is no next line, in
     * which case, it returns the last line.
     *
     * @param firstNodesOnLines NavigableMap of lines and their first nodes.
     * @param node the node for which to find the next node line
     * @return the line number of the next line in the map
     */
    private static Integer getNextNodeLine(
            NavigableMap<Integer, DetailAST> firstNodesOnLines, DetailAST node) {
        Integer nextNodeLine = firstNodesOnLines.higherKey(node.getLastChild().getLineNo());
        if (nextNodeLine == null) {
            nextNodeLine = firstNodesOnLines.lastKey();
        }
        return nextNodeLine;
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
     * @param atNode block tag node.
     * @param firstNodesOnLines map which contains
     *     first nodes as values and line numbers as keys.
     * @param indentLevel line wrapping indentation.
     */
    private void checkAnnotationIndentation(DetailAST atNode,
            NavigableMap<Integer, DetailAST> firstNodesOnLines, int indentLevel) {
        final int firstNodeIndent = getLineStart(atNode);
        final int currentIndent = firstNodeIndent + indentLevel;
        final Collection<DetailAST> values = firstNodesOnLines.values();
        final DetailAST lastAnnotationNode = atNode.getParent().getLastChild();
        final int lastAnnotationLine = lastAnnotationNode.getLineNo();

        final Iterator<DetailAST> itr = values.iterator();
        while (firstNodesOnLines.size() > 1) {
            final DetailAST node = itr.next();

            final DetailAST parentNode = node.getParent();
            final boolean isArrayInitPresentInAncestors =
                isParentContainsTokenType(node, TokenTypes.ANNOTATION_ARRAY_INIT);
            final boolean isCurrentNodeCloseAnnotationAloneInLine =
                node.getLineNo() == lastAnnotationLine
                    && isEndOfScope(lastAnnotationNode, node);
            if (!isArrayInitPresentInAncestors
                    && (isCurrentNodeCloseAnnotationAloneInLine
                        || node.getType() == TokenTypes.AT
                        && (parentNode.getParent().getType() == TokenTypes.MODIFIERS
                            || parentNode.getParent().getType() == TokenTypes.ANNOTATIONS)
                        || TokenUtil.areOnSameLine(node, atNode))) {
                logWarningMessage(node, new IndentLevel(firstNodeIndent));
            }
            else if (!isArrayInitPresentInAncestors) {
                logWarningMessage(node, new IndentLevel(currentIndent));
            }
            itr.remove();
        }
    }

    /**
     * Checks line for end of scope.  Handles occurrences of close braces and close parenthesis on
     * the same line.
     *
     * @param lastAnnotationNode the last node of the annotation
     * @param node the node indicating where to begin checking
     * @return true if all the nodes up to the last annotation node are end of scope nodes
     *         false otherwise
     */
    private static boolean isEndOfScope(final DetailAST lastAnnotationNode, final DetailAST node) {
        DetailAST checkNode = node;
        boolean endOfScope = true;
        while (endOfScope && !checkNode.equals(lastAnnotationNode)) {
            switch (checkNode.getType()) {
                case TokenTypes.RCURLY:
                case TokenTypes.RBRACK:
                    while (checkNode.getNextSibling() == null) {
                        checkNode = checkNode.getParent();
                    }
                    checkNode = checkNode.getNextSibling();
                    break;
                default:
                    endOfScope = false;
            }
        }
        return endOfScope;
    }

    /**
     * Checks that some parent of given node contains given token type.
     *
     * @param node node to check
     * @param type type to look for
     * @return true if there is a parent of given type
     */
    private static boolean isParentContainsTokenType(final DetailAST node, int type) {
        boolean returnValue = false;
        for (DetailAST ast = node.getParent(); ast != null; ast = ast.getParent()) {
            if (ast.getType() == type) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
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

        return CommonUtil.lengthExpandedTabs(line, ast.getColumnNo(),
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
     * @return the start of the specified line
     */
    private int getLineStart(String line) {
        int index = 0;
        while (Character.isWhitespace(line.charAt(index))) {
            index++;
        }
        return CommonUtil.lengthExpandedTabs(line, index, indentCheck.getIndentationTabWidth());
    }

    /**
     * Logs warning message if indentation is incorrect.
     *
     * @param currentNode
     *            current node which probably invoked a violation.
     * @param currentIndent
     *            correct indentation.
     */
    private void logWarningMessage(DetailAST currentNode, IndentLevel currentIndent) {
        if (indentCheck.isForceStrictCondition()) {
            if (!currentIndent.isAcceptable(expandedTabsColumnNo(currentNode))) {
                indentCheck.indentationLog(currentNode,
                        AbstractExpressionHandler.getIndentErrorMessage(currentIndent),
                        currentNode.getText(),
                        expandedTabsColumnNo(currentNode), currentIndent);
            }
        }
        else {
            if (currentIndent.isGreaterThan(expandedTabsColumnNo(currentNode))) {
                indentCheck.indentationLog(currentNode,
                        AbstractExpressionHandler.getIndentErrorMessage(currentIndent),
                        currentNode.getText(),
                        expandedTabsColumnNo(currentNode), currentIndent);
            }
        }
    }
}

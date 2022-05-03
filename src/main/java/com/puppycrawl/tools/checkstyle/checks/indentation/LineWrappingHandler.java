////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
     * @see CaseHandler#getIndentImpl()
     */
    private static final int[] IGNORED_LIST = {
        TokenTypes.RCURLY,
        TokenTypes.LITERAL_NEW,
        TokenTypes.ARRAY_INIT,
        TokenTypes.LITERAL_DEFAULT,
        TokenTypes.LITERAL_CASE,
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
        DetailAST previousLine = null;
        if (ignoreFirstLine == LineWrappingOptions.IGNORE_FIRST_LINE) {
            // First node should be removed because it was already checked before.
            firstNodesOnLines.remove(firstNodesOnLines.firstKey());
            previousLine = firstLineNode;
        }

        final int firstNodeIndent;
        if (startIndent == -1) {
            firstNodeIndent = getLineStart(firstLineNode);
        }
        else {
            firstNodeIndent = startIndent;
        }
        for (DetailAST node : firstNodesOnLines.values()) {
            if (checkForNullParameterChild(node) || checkForMethodLparenNewLine(node)
                    || isArrayInitElement(node)) {
                continue;
            }

            if (!lineStartsWithComment(node)) {
                checkFirstNodeOnLine(node, indentLevel, previousLine, firstNodeIndent);
            }

            if (noAnnotationParameter(node)) {
                previousLine = node;
            }
        }
    }

    /**
     * Checks if node is part of an annotation parameter.
     *
     * @param node Node to examine.
     * @return false, if node is part of an annotation parameter
     */
    private static boolean noAnnotationParameter(DetailAST node) {
        return onlyAnnotationIn(node)
                || !isParentContainsTokenType(node, TokenTypes.ANNOTATION);
    }

    /**
     * Examines, if {@code node} is part of an annotation array initialization.
     *
     * @param node Node to examine.
     * @return true, if node is part of an annotation array initialization.
     */
    private static boolean isArrayInitElement(DetailAST node) {
        return isParentContainsTokenType(node, TokenTypes.ANNOTATION_ARRAY_INIT);
    }

    /**
     * Checks indentation of {@code node}.
     *
     * @param node First node on line to examine.
     * @param indentLevel Indentation all wrapped lines should use.
     * @param previousLine Node in previous line which isn't an annotation parameter.
     * @param firstNodeIndent Indentation of first node in the examined sub tree.
     */
    private void checkFirstNodeOnLine(DetailAST node, int indentLevel, DetailAST previousLine,
            final int firstNodeIndent) {
        final int currentType = node.getType();

        if (currentType == TokenTypes.RPAREN) {
            logWarningMessage(node, firstNodeIndent);
        }
        else if (!TokenUtil.isOfType(currentType, IGNORED_LIST)) {
            if (previousLine == null) {
                logWarningMessage(node, firstNodeIndent);
            }
            else if (isAnnotationOrLineAfterAnnotation(node, previousLine)) {
                logStrictWarningMessage(node, firstNodeIndent);
            }
            else {
                logWarningMessage(node, firstNodeIndent + indentLevel);
            }
        }
    }

    /**
     * Checks if the line containing {@code node} starts with a block comment.
     *
     * @param node Node in line to examine.
     * @return true, if line starts with a block comment.
     */
    private boolean lineStartsWithComment(DetailAST node) {
        final boolean result;
        final String line = indentCheck.getLine(node.getLineNo() - 1);
        final int indexOfNonWhitespace = CommonUtil.indexOfNonWhitespace(line);
        if (line.length() > indexOfNonWhitespace + 1) {
            final String lineStart =
                line.substring(indexOfNonWhitespace, indexOfNonWhitespace + 2);
            result = "/*".equals(lineStart);
        }
        else {
            result = false;
        }
        return result;
    }

    /**
     * Examines if {@code node} is a non-parameter annotation or if the previous line of
     * {@code node} is an annotation. Annotations which are parameters of other annotations return
     * false.
     *
     * @param node Node to examine.
     * @param previousLineNode Node in previous line which isn't an annotation parameter.
     * @return true, if node is annotation or line after annotation.
     */
    private static boolean isAnnotationOrLineAfterAnnotation(DetailAST node,
            DetailAST previousLineNode) {
        final boolean result;
        if (node.getType() == TokenTypes.AT) {
            // node is annotation, check if annotation is parameter of other annotation
            result = !isParentContainsTokenType(node.getParent(), TokenTypes.ANNOTATION)
                && !isParentContainsTokenType(node.getParent(), TokenTypes.PARAMETER_DEF)
                && !isParentContainsTokenType(node.getParent(), TokenTypes.TYPECAST);
        }
        else if (isParentContainsTokenType(node, TokenTypes.ANNOTATION)) {
            // non-annotation node inside annotation
            result = false;
        }
        else {
            // non-annotation node outside an annotation
            result = onlyAnnotationIn(previousLineNode)
                    && !isParentContainsTokenType(node, TokenTypes.PARAMETER_DEF);
        }
        return result;
    }

    /**
     * Checks if line contains only annotation elements.
     *
     * @param previousLineNode node to examine.
     * @return true, if line contains only annotation elements.
     */
    private static boolean onlyAnnotationIn(DetailAST previousLineNode) {
        return previousLineNode.getType() == TokenTypes.AT
                && previousLineNode.getParent().getParent().getNextSibling() != null
                && previousLineNode.getParent().getParent().getNextSibling().getLineNo()
                    > previousLineNode.getLineNo();
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
    private void logWarningMessage(DetailAST currentNode, int currentIndent) {
        if (indentCheck.isForceStrictCondition()) {
            logStrictWarningMessage(currentNode, currentIndent);
        }
        else {
            if (expandedTabsColumnNo(currentNode) < currentIndent) {
                indentCheck.indentationLog(currentNode,
                        IndentationCheck.MSG_ERROR, currentNode.getText(),
                        expandedTabsColumnNo(currentNode), currentIndent);
            }
        }
    }

    /**
     * Logs warning message if indentation is incorrect following strict rules.
     *
     * @param currentNode current node which probably invoked a violation.
     * @param currentIndent correct indentation.
     */
    private void logStrictWarningMessage(DetailAST currentNode, int currentIndent) {
        if (expandedTabsColumnNo(currentNode) != currentIndent) {
            indentCheck.indentationLog(currentNode,
                    IndentationCheck.MSG_ERROR, currentNode.getText(),
                    expandedTabsColumnNo(currentNode), currentIndent);
        }
    }

}

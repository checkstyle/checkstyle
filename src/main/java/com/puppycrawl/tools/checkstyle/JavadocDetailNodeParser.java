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

package com.puppycrawl.tools.checkstyle;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.CaseFormat;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocLexer;
import com.puppycrawl.tools.checkstyle.grammars.javadoc.JavadocParser;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtils;

/**
 * Used for parsing Javadoc comment as DetailNode tree.
 * @author bizmailov
 *
 */
public class JavadocDetailNodeParser {

    /**
     * Message key of error message. Missed close HTML tag breaks structure
     * of parse tree, so parser stops parsing and generates such error
     * message. This case is special because parser prints error like
     * {@code "no viable alternative at input 'b \n *\n'"} and it is not
     * clear that error is about missed close HTML tag.
     */
    public static final String MSG_JAVADOC_MISSED_HTML_CLOSE = "javadoc.missed.html.close";

    /**
     * Message key of error message.
     */
    public static final String MSG_JAVADOC_WRONG_SINGLETON_TAG =
        "javadoc.wrong.singleton.html.tag";

    /**
     * Parse error while rule recognition.
     */
    public static final String MSG_JAVADOC_PARSE_RULE_ERROR = "javadoc.parse.rule.error";

    /**
     * Error message key for common javadoc errors.
     */
    public static final String MSG_KEY_PARSE_ERROR = "javadoc.parse.error";

    /**
     * Unrecognized error from antlr parser.
     */
    public static final String MSG_KEY_UNRECOGNIZED_ANTLR_ERROR =
            "javadoc.unrecognized.antlr.error";

    /**
     * Line number of the Block comment AST that is being parsed.
     */
    private int blockCommentLineNumber;

    /**
     * Custom error listener.
     */
    private DescriptiveErrorListener errorListener;

    /**
     * Parses Javadoc comment as DetailNode tree.
     * @param javadocCommentAst
     *        DetailAST of Javadoc comment
     * @return DetailNode tree of Javadoc comment
     */
    public ParseStatus parseJavadocAsDetailNode(DetailAST javadocCommentAst) {
        blockCommentLineNumber = javadocCommentAst.getLineNo();
        final String javadocComment = JavadocUtils.getJavadocCommentContent(javadocCommentAst);

        // Use a new error listener each time to be able to use
        // one check instance for multiple files to be checked
        // without getting side effects.
        errorListener = new DescriptiveErrorListener();

        // Log messages should have line number in scope of file,
        // not in scope of Javadoc comment.
        // Offset is line number of beginning of Javadoc comment.
        errorListener.setOffset(javadocCommentAst.getLineNo() - 1);

        final ParseStatus result = new ParseStatus();

        try {
            final ParseTree parseTree = parseJavadocAsParseTree(javadocComment);

            final DetailNode tree = convertParseTreeToDetailNode(parseTree);
            result.setTree(tree);
        }
        catch (ParseCancellationException ex) {
            // If syntax error occurs then message is printed by error listener
            // and parser throws this runtime exception to stop parsing.
            // Just stop processing current Javadoc comment.
            ParseErrorMessage parseErrorMessage = errorListener.getErrorMessage();

            // There are cases when antlr error listener does not handle syntax error
            if (parseErrorMessage == null) {
                parseErrorMessage = new ParseErrorMessage(javadocCommentAst.getLineNo(),
                        MSG_KEY_UNRECOGNIZED_ANTLR_ERROR,
                        javadocCommentAst.getColumnNo(), ex.getMessage());
            }

            result.setParseErrorMessage(parseErrorMessage);
        }

        return result;
    }

    /**
     * Parses block comment content as javadoc comment.
     * @param blockComment
     *        block comment content.
     * @return parse tree
     */
    private ParseTree parseJavadocAsParseTree(String blockComment) {
        final ANTLRInputStream input = new ANTLRInputStream(blockComment);

        final JavadocLexer lexer = new JavadocLexer(input);

        // remove default error listeners
        lexer.removeErrorListeners();

        // add custom error listener that logs parsing errors
        lexer.addErrorListener(errorListener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final JavadocParser parser = new JavadocParser(tokens);

        // remove default error listeners
        parser.removeErrorListeners();

        // add custom error listener that logs syntax errors
        parser.addErrorListener(errorListener);

        // This strategy stops parsing when parser error occurs.
        // By default it uses Error Recover Strategy which is slow and useless.
        parser.setErrorHandler(new BailErrorStrategy());

        return parser.javadoc();
    }

    /**
     * Converts ParseTree (that is generated by ANTLRv4) to DetailNode tree.
     *
     * @param parseTreeNode root node of ParseTree
     * @return root of DetailNode tree
     */
    private DetailNode convertParseTreeToDetailNode(ParseTree parseTreeNode) {
        final JavadocNodeImpl rootJavadocNode = createRootJavadocNode(parseTreeNode);

        JavadocNodeImpl currentJavadocParent = rootJavadocNode;
        ParseTree parseTreeParent = parseTreeNode;

        while (currentJavadocParent != null) {
            final JavadocNodeImpl[] children =
                    (JavadocNodeImpl[]) currentJavadocParent.getChildren();

            insertChildrenNodes(children, parseTreeParent);

            if (children.length > 0) {
                currentJavadocParent = children[0];
                parseTreeParent = parseTreeParent.getChild(0);
            }
            else {
                JavadocNodeImpl nextJavadocSibling = (JavadocNodeImpl) JavadocUtils
                        .getNextSibling(currentJavadocParent);

                ParseTree nextParseTreeSibling = getNextSibling(parseTreeParent);

                if (nextJavadocSibling == null) {
                    JavadocNodeImpl tempJavadocParent =
                            (JavadocNodeImpl) currentJavadocParent.getParent();

                    ParseTree tempParseTreeParent = parseTreeParent.getParent();

                    while (nextJavadocSibling == null && tempJavadocParent != null) {

                        nextJavadocSibling = (JavadocNodeImpl) JavadocUtils
                                .getNextSibling(tempJavadocParent);

                        nextParseTreeSibling = getNextSibling(tempParseTreeParent);

                        tempJavadocParent = (JavadocNodeImpl) tempJavadocParent.getParent();
                        tempParseTreeParent = tempParseTreeParent.getParent();
                    }
                }
                currentJavadocParent = nextJavadocSibling;
                parseTreeParent = nextParseTreeSibling;
            }
        }

        return rootJavadocNode;
    }

    /**
     * Creates child nodes for each node from 'nodes' array.
     * @param parseTreeParent original ParseTree parent node
     * @param nodes array of JavadocNodeImpl nodes
     */
    private void insertChildrenNodes(final JavadocNodeImpl[] nodes, ParseTree parseTreeParent) {
        for (int i = 0; i < nodes.length; i++) {
            final JavadocNodeImpl currentJavadocNode = nodes[i];
            final ParseTree currentParseTreeNodeChild = parseTreeParent.getChild(i);
            final JavadocNodeImpl[] subChildren =
                    createChildrenNodes(currentJavadocNode, currentParseTreeNodeChild);
            currentJavadocNode.setChildren(subChildren);
        }
    }

    /**
     * Creates children Javadoc nodes base on ParseTree node's children.
     * @param parentJavadocNode node that will be parent for created children
     * @param parseTreeNode original ParseTree node
     * @return array of Javadoc nodes
     */
    private JavadocNodeImpl[]
            createChildrenNodes(JavadocNodeImpl parentJavadocNode, ParseTree parseTreeNode) {
        final JavadocNodeImpl[] children =
                new JavadocNodeImpl[parseTreeNode.getChildCount()];

        for (int j = 0; j < children.length; j++) {
            final JavadocNodeImpl child =
                    createJavadocNode(parseTreeNode.getChild(j), parentJavadocNode, j);

            children[j] = child;
        }
        return children;
    }

    /**
     * Creates root JavadocNodeImpl node base on ParseTree root node.
     * @param parseTreeNode ParseTree root node
     * @return root Javadoc node
     */
    private JavadocNodeImpl createRootJavadocNode(ParseTree parseTreeNode) {
        final JavadocNodeImpl rootJavadocNode = createJavadocNode(parseTreeNode, null, -1);

        final int childCount = parseTreeNode.getChildCount();
        final JavadocNodeImpl[] children = new JavadocNodeImpl[childCount];

        for (int i = 0; i < childCount; i++) {
            final JavadocNodeImpl child = createJavadocNode(parseTreeNode.getChild(i),
                    rootJavadocNode, i);
            children[i] = child;
        }
        rootJavadocNode.setChildren(children);
        return rootJavadocNode;
    }

    /**
     * Creates JavadocNodeImpl node on base of ParseTree node.
     *
     * @param parseTree ParseTree node
     * @param parent DetailNode that will be parent of new node
     * @param index child index that has new node
     * @return JavadocNodeImpl node on base of ParseTree node.
     */
    private JavadocNodeImpl createJavadocNode(ParseTree parseTree, DetailNode parent, int index) {
        final JavadocNodeImpl node = new JavadocNodeImpl();
        node.setText(parseTree.getText());
        node.setColumnNumber(getColumn(parseTree));
        node.setLineNumber(getLine(parseTree) + blockCommentLineNumber);
        node.setIndex(index);
        node.setType(getTokenType(parseTree));
        node.setParent(parent);
        node.setChildren(new JavadocNodeImpl[parseTree.getChildCount()]);
        return node;
    }

    /**
     * Gets line number from ParseTree node.
     * @param tree
     *        ParseTree node
     * @return line number
     */
    private static int getLine(ParseTree tree) {
        if (tree instanceof TerminalNode) {
            return ((TerminalNode) tree).getSymbol().getLine() - 1;
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) tree;
            return rule.start.getLine() - 1;
        }
    }

    /**
     * Gets column number from ParseTree node.
     * @param tree
     *        ParseTree node
     * @return column number
     */
    private static int getColumn(ParseTree tree) {
        if (tree instanceof TerminalNode) {
            return ((TerminalNode) tree).getSymbol().getCharPositionInLine();
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) tree;
            return rule.start.getCharPositionInLine();
        }
    }

    /**
     * Gets next sibling of ParseTree node.
     * @param node ParseTree node
     * @return next sibling of ParseTree node.
     */
    private static ParseTree getNextSibling(ParseTree node) {
        ParseTree nextSibling = null;

        if (node.getParent() != null) {
            final ParseTree parent = node.getParent();
            final int childCount = parent.getChildCount();

            int index = 0;
            while (true) {
                final ParseTree currentNode = parent.getChild(index);
                if (currentNode.equals(node)) {
                    if (index != childCount - 1) {
                        nextSibling = parent.getChild(index + 1);
                    }
                    break;
                }
                index++;
            }
        }
        return nextSibling;
    }

    /**
     * Gets token type of ParseTree node from JavadocTokenTypes class.
     * @param node ParseTree node.
     * @return token type from JavadocTokenTypes
     */
    private static int getTokenType(ParseTree node) {
        final int tokenType;

        if (node.getChildCount() == 0) {
            tokenType = ((TerminalNode) node).getSymbol().getType();
        }
        else {
            final String className = getNodeClassNameWithoutContext(node);
            final String typeName =
                    CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, className);
            tokenType = JavadocUtils.getTokenId(typeName);
        }

        return tokenType;
    }

    /**
     * Gets class name of ParseTree node and removes 'Context' postfix at the
     * end.
     * @param node
     *        ParseTree node.
     * @return class name without 'Context'
     */
    private static String getNodeClassNameWithoutContext(ParseTree node) {
        final String className = node.getClass().getSimpleName();
        // remove 'Context' at the end
        final int contextLength = 7;
        return className.substring(0, className.length() - contextLength);
    }

    /**
     * Custom error listener for JavadocParser that prints user readable errors.
     */
    private static class DescriptiveErrorListener extends BaseErrorListener {

        /**
         * Offset is line number of beginning of the Javadoc comment. Log
         * messages should have line number in scope of file, not in scope of
         * Javadoc comment.
         */
        private int offset;

        /**
         * Error message that appeared while parsing.
         */
        private ParseErrorMessage errorMessage;

        /**
         * Getter for error message during parsing.
         * @return Error message during parsing.
         */
        private ParseErrorMessage getErrorMessage() {
            return errorMessage;
        }

        /**
         * Sets offset. Offset is line number of beginning of the Javadoc
         * comment. Log messages should have line number in scope of file, not
         * in scope of Javadoc comment.
         * @param offset
         *        offset line number
         */
        public void setOffset(int offset) {
            this.offset = offset;
        }

        /**
         * Logs parser errors in Checkstyle manner. Parser can generate error
         * messages. There is special error that parser can generate. It is
         * missed close HTML tag. This case is special because parser prints
         * error like {@code "no viable alternative at input 'b \n *\n'"} and it
         * is not clear that error is about missed close HTML tag. Other error
         * messages are not special and logged simply as "Parse Error...".
         *
         * <p>{@inheritDoc}
         */
        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer, Object offendingSymbol,
                int line, int charPositionInLine,
                String msg, RecognitionException ex) {
            final int lineNumber = offset + line;
            final Token token = (Token) offendingSymbol;

            if (MSG_JAVADOC_MISSED_HTML_CLOSE.equals(msg)) {
                errorMessage = new ParseErrorMessage(lineNumber,
                        MSG_JAVADOC_MISSED_HTML_CLOSE, charPositionInLine, token.getText());

                throw new ParseCancellationException(msg);
            }
            else if (MSG_JAVADOC_WRONG_SINGLETON_TAG.equals(msg)) {
                errorMessage = new ParseErrorMessage(lineNumber,
                        MSG_JAVADOC_WRONG_SINGLETON_TAG, charPositionInLine, token.getText());

                throw new ParseCancellationException(msg);
            }
            else {
                final int ruleIndex = ex.getCtx().getRuleIndex();
                final String ruleName = recognizer.getRuleNames()[ruleIndex];
                final String upperCaseRuleName = CaseFormat.UPPER_CAMEL.to(
                        CaseFormat.UPPER_UNDERSCORE, ruleName);

                errorMessage = new ParseErrorMessage(lineNumber,
                        MSG_JAVADOC_PARSE_RULE_ERROR, charPositionInLine, msg, upperCaseRuleName);
            }
        }
    }

    /**
     * Contains result of parsing javadoc comment: DetailNode tree and parse
     * error message.
     */
    public static class ParseStatus {
        /**
         * DetailNode tree (is null if parsing fails).
         */
        private DetailNode tree;

        /**
         * Parse error message (is null if parsing is successful).
         */
        private ParseErrorMessage parseErrorMessage;

        /**
         * Getter for DetailNode tree.
         * @return DetailNode tree if parsing was successful, null otherwise.
         */
        public DetailNode getTree() {
            return tree;
        }

        /**
         * Sets DetailNode tree.
         * @param tree DetailNode tree.
         */
        public void setTree(DetailNode tree) {
            this.tree = tree;
        }

        /**
         * Getter for error message during parsing.
         * @return Error message if parsing was unsuccessful, null otherwise.
         */
        public ParseErrorMessage getParseErrorMessage() {
            return parseErrorMessage;
        }

        /**
         * Sets parse error message.
         * @param parseErrorMessage Parse error message.
         */
        public void setParseErrorMessage(ParseErrorMessage parseErrorMessage) {
            this.parseErrorMessage = parseErrorMessage;
        }

    }

    /**
     * Contains information about parse error message.
     */
    public static class ParseErrorMessage {
        /**
         * Line number where parse error occurred.
         */
        private final int lineNumber;

        /**
         * Key for error message.
         */
        private final String messageKey;

        /**
         * Error message arguments.
         */
        private final Object[] messageArguments;

        /**
         * Initializes parse error message.
         *
         * @param lineNumber line number
         * @param messageKey message key
         * @param messageArguments message arguments
         */
        ParseErrorMessage(int lineNumber, String messageKey, Object ... messageArguments) {
            this.lineNumber = lineNumber;
            this.messageKey = messageKey;
            this.messageArguments = messageArguments.clone();
        }

        /**
         * Getter for line number where parse error occurred.
         * @return Line number where parse error occurred.
         */
        public int getLineNumber() {
            return lineNumber;
        }

        /**
         * Getter for key for error message.
         * @return Key for error message.
         */
        public String getMessageKey() {
            return messageKey;
        }

        /**
         * Getter for error message arguments.
         * @return Array of error message arguments.
         */
        public Object[] getMessageArguments() {
            return messageArguments.clone();
        }
    }

}

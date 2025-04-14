///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Objects;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocNodeImpl;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocLexer;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocParser;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Used for parsing Javadoc comment as DetailNode tree.
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
     * Message property key for the Unclosed HTML message.
     */
    public static final String MSG_UNCLOSED_HTML_TAG = "javadoc.unclosedHtml";

    /** Symbols with which javadoc starts. */
    private static final String JAVADOC_START = "/**";

    /**
     * Line number of the Block comment AST that is being parsed.
     */
    private int blockCommentLineNumber;

    /**
     * Parses Javadoc comment as DetailNode tree.
     *
     * @param javadocCommentAst
     *        DetailAST of Javadoc comment
     * @return DetailNode tree of Javadoc comment
     */
    public ParseStatus parseJavadocAsDetailNode(DetailAST javadocCommentAst) {
        blockCommentLineNumber = javadocCommentAst.getLineNo();

        final String javadocComment = JavadocUtil.getJavadocCommentContent(javadocCommentAst);

        // Use a new error listener each time to be able to use
        // one check instance for multiple files to be checked
        // without getting side effects.
        final DescriptiveErrorListener errorListener = new DescriptiveErrorListener();

        // Log messages should have line number in scope of file,
        // not in scope of Javadoc comment.
        // Offset is line number of beginning of Javadoc comment.
        errorListener.setOffset(javadocCommentAst.getLineNo() - 1);

        final ParseStatus result = new ParseStatus();

        try {
            final JavadocParser javadocParser = createJavadocParser(javadocComment, errorListener);

            final ParseTree javadocParseTree = javadocParser.javadoc();

            final DetailNode tree = convertParseTreeToDetailNode(javadocParseTree);
            // adjust first line to indent of /**
            adjustFirstLineToJavadocIndent(tree,
                        javadocCommentAst.getColumnNo()
                                + JAVADOC_START.length());
            result.setTree(tree);
            result.firstNonTightHtmlTag = getFirstNonTightHtmlTag(javadocParser,
                    errorListener.offset);
        }
        catch (ParseCancellationException | IllegalArgumentException ex) {
            ParseErrorMessage parseErrorMessage = null;

            if (ex.getCause() instanceof FailedPredicateException
                    || ex.getCause() instanceof NoViableAltException) {
                final RecognitionException recognitionEx = (RecognitionException) ex.getCause();
                if (recognitionEx.getCtx() instanceof JavadocParser.HtmlTagContext) {
                    final Token htmlTagNameStart = getMissedHtmlTag(recognitionEx);
                    parseErrorMessage = new ParseErrorMessage(
                            errorListener.offset + htmlTagNameStart.getLine(),
                            MSG_JAVADOC_MISSED_HTML_CLOSE,
                            htmlTagNameStart.getCharPositionInLine(),
                            htmlTagNameStart.getText());
                }
            }

            if (parseErrorMessage == null) {
                // If syntax error occurs then message is printed by error listener
                // and parser throws this runtime exception to stop parsing.
                // Just stop processing current Javadoc comment.
                parseErrorMessage = errorListener.getErrorMessage();
            }

            result.setParseErrorMessage(parseErrorMessage);
        }

        return Objects.requireNonNull(result);
    }

    /**
     * Parses block comment content as javadoc comment.
     *
     * @param blockComment
     *        block comment content.
     * @param errorListener custom error listener
     * @return parse tree
     */
    private static JavadocParser createJavadocParser(String blockComment,
            DescriptiveErrorListener errorListener) {
        final JavadocLexer lexer = new JavadocLexer(CharStreams.fromString(blockComment), true);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);

        final JavadocParser parser = new JavadocParser(tokens);

        // set prediction mode to SLL to speed up parsing
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

        // remove default error listeners
        parser.removeErrorListeners();

        // add custom error listener that logs syntax errors
        parser.addErrorListener(errorListener);

        // JavadocParserErrorStrategy stops parsing on first parse error encountered unlike the
        // DefaultErrorStrategy used by ANTLR which rather attempts error recovery.
        parser.setErrorHandler(new CheckstyleParserErrorStrategy());

        return parser;
    }

    /**
     * Converts ParseTree (that is generated by ANTLRv4) to DetailNode tree.
     *
     * @param parseTreeNode root node of ParseTree
     * @return root of DetailNode tree
     * @noinspection SuspiciousArrayCast
     * @noinspectionreason SuspiciousArrayCast - design of parser forces us to
     *      use mutable node
     */
    private DetailNode convertParseTreeToDetailNode(ParseTree parseTreeNode) {
        final JavadocNodeImpl rootJavadocNode = createRootJavadocNode(parseTreeNode);

        JavadocNodeImpl currentJavadocParent = rootJavadocNode;
        ParseTree parseTreeParent = parseTreeNode;

        while (currentJavadocParent != null) {
            // remove unnecessary children tokens
            if (currentJavadocParent.getType() == JavadocTokenTypes.TEXT) {
                currentJavadocParent.setChildren(JavadocNodeImpl.EMPTY_DETAIL_NODE_ARRAY);
            }

            final JavadocNodeImpl[] children =
                    (JavadocNodeImpl[]) currentJavadocParent.getChildren();

            insertChildrenNodes(children, parseTreeParent);

            if (children.length > 0) {
                currentJavadocParent = children[0];
                parseTreeParent = parseTreeParent.getChild(0);
            }
            else {
                JavadocNodeImpl nextJavadocSibling = (JavadocNodeImpl) JavadocUtil
                        .getNextSibling(currentJavadocParent);

                ParseTree nextParseTreeSibling = getNextSibling(parseTreeParent);

                while (nextJavadocSibling == null) {
                    currentJavadocParent =
                            (JavadocNodeImpl) currentJavadocParent.getParent();

                    parseTreeParent = parseTreeParent.getParent();

                    if (currentJavadocParent == null) {
                        break;
                    }

                    nextJavadocSibling = (JavadocNodeImpl) JavadocUtil
                            .getNextSibling(currentJavadocParent);

                    nextParseTreeSibling = getNextSibling(parseTreeParent);
                }
                currentJavadocParent = nextJavadocSibling;
                parseTreeParent = nextParseTreeSibling;
            }
        }

        return rootJavadocNode;
    }

    /**
     * Creates child nodes for each node from 'nodes' array.
     *
     * @param nodes array of JavadocNodeImpl nodes
     * @param parseTreeParent original ParseTree parent node
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
     *
     * @param parentJavadocNode node that will be parent for created children
     * @param parseTreeNode original ParseTree node
     * @return array of Javadoc nodes
     */
    private JavadocNodeImpl[]
            createChildrenNodes(DetailNode parentJavadocNode, ParseTree parseTreeNode) {
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
     *
     * @param parseTreeNode ParseTree root node
     * @return root Javadoc node
     */
    private JavadocNodeImpl createRootJavadocNode(ParseTree parseTreeNode) {
        final JavadocNodeImpl rootJavadocNode = createJavadocNode(parseTreeNode, null, -1);

        final int childCount = parseTreeNode.getChildCount();
        final DetailNode[] children = rootJavadocNode.getChildren();

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
        if (parseTree.getChildCount() == 0
                || "Text".equals(getNodeClassNameWithoutContext(parseTree))) {
            node.setText(parseTree.getText());
        }
        else {
            node.setText(getFormattedNodeClassNameWithoutContext(parseTree));
        }
        node.setColumnNumber(getColumn(parseTree));
        node.setLineNumber(getLine(parseTree) + blockCommentLineNumber);
        node.setIndex(index);
        node.setType(getTokenType(parseTree));
        node.setParent(parent);
        node.setChildren(new JavadocNodeImpl[parseTree.getChildCount()]);
        return node;
    }

    /**
     * Adjust first line nodes to javadoc indent.
     *
     * @param tree DetailNode tree root
     * @param javadocColumnNumber javadoc indent
     */
    private void adjustFirstLineToJavadocIndent(DetailNode tree, int javadocColumnNumber) {
        if (tree.getLineNumber() == blockCommentLineNumber) {
            ((JavadocNodeImpl) tree).setColumnNumber(tree.getColumnNumber() + javadocColumnNumber);
            final DetailNode[] children = tree.getChildren();
            for (DetailNode child : children) {
                adjustFirstLineToJavadocIndent(child, javadocColumnNumber);
            }
        }
    }

    /**
     * Gets line number from ParseTree node.
     *
     * @param tree
     *        ParseTree node
     * @return line number
     */
    private static int getLine(ParseTree tree) {
        final int line;
        if (tree instanceof TerminalNode) {
            line = ((TerminalNode) tree).getSymbol().getLine() - 1;
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) tree;
            line = rule.start.getLine() - 1;
        }
        return line;
    }

    /**
     * Gets column number from ParseTree node.
     *
     * @param tree
     *        ParseTree node
     * @return column number
     */
    private static int getColumn(ParseTree tree) {
        final int column;
        if (tree instanceof TerminalNode) {
            column = ((TerminalNode) tree).getSymbol().getCharPositionInLine();
        }
        else {
            final ParserRuleContext rule = (ParserRuleContext) tree;
            column = rule.start.getCharPositionInLine();
        }
        return column;
    }

    /**
     * Gets next sibling of ParseTree node.
     *
     * @param node ParseTree node
     * @return next sibling of ParseTree node.
     */
    private static ParseTree getNextSibling(ParseTree node) {
        ParseTree nextSibling = null;

        if (node.getParent() != null) {
            final ParseTree parent = node.getParent();
            int index = 0;
            while (true) {
                final ParseTree currentNode = parent.getChild(index);
                if (currentNode.equals(node)) {
                    nextSibling = parent.getChild(index + 1);
                    break;
                }
                index++;
            }
        }
        return nextSibling;
    }

    /**
     * Gets token type of ParseTree node from JavadocTokenTypes class.
     *
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
            tokenType = JavadocUtil.getTokenId(convertUpperCamelToUpperUnderscore(className));
        }

        return tokenType;
    }

    /**
     * Gets class name of ParseTree node and removes 'Context' postfix at the
     * end and formats it.
     *
     * @param node {@code ParseTree} node whose class name is to be formatted and returned
     * @return uppercased class name without the word 'Context' and with appropriately
     *     inserted underscores
     */
    private static String getFormattedNodeClassNameWithoutContext(ParseTree node) {
        final String classNameWithoutContext = getNodeClassNameWithoutContext(node);
        return convertUpperCamelToUpperUnderscore(classNameWithoutContext);
    }

    /**
     * Gets class name of ParseTree node and removes 'Context' postfix at the
     * end.
     *
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
     * Method to get the missed HTML tag to generate more informative error message for the user.
     * This method doesn't concern itself with
     * <a href="https://www.w3.org/TR/html51/syntax.html#void-elements">void elements</a>
     * since it is forbidden to close them.
     * Missed HTML tags for the following tags will <i>not</i> generate an error message from ANTLR:
     * {@code
     * <p>
     * <li>
     * <tr>
     * <td>
     * <th>
     * <body>
     * <colgroup>
     * <dd>
     * <dt>
     * <head>
     * <html>
     * <option>
     * <tbody>
     * <thead>
     * <tfoot>
     * }
     *
     * @param exception {@code NoViableAltException} object catched while parsing javadoc
     * @return returns appropriate {@link Token} if a HTML close tag is missed;
     *     null otherwise
     */
    private static Token getMissedHtmlTag(RecognitionException exception) {
        Token htmlTagNameStart = null;
        final Interval sourceInterval = exception.getCtx().getSourceInterval();
        final List<Token> tokenList = ((BufferedTokenStream) exception.getInputStream())
                .getTokens(sourceInterval.a, sourceInterval.b);
        final Deque<Token> stack = new ArrayDeque<>();
        int prevTokenType = JavadocTokenTypes.EOF;
        for (final Token token : tokenList) {
            final int tokenType = token.getType();
            if (tokenType == JavadocTokenTypes.HTML_TAG_NAME
                    && prevTokenType == JavadocTokenTypes.START) {
                stack.push(token);
            }
            else if (tokenType == JavadocTokenTypes.HTML_TAG_NAME && !stack.isEmpty()) {
                if (stack.peek().getText().equals(token.getText())) {
                    stack.pop();
                }
                else {
                    htmlTagNameStart = stack.pop();
                }
            }
            prevTokenType = tokenType;
        }
        if (htmlTagNameStart == null) {
            htmlTagNameStart = stack.pop();
        }
        return htmlTagNameStart;
    }

    /**
     * This method is used to get the first non-tight HTML tag encountered while parsing javadoc.
     * This shall eventually be reflected by the {@link ParseStatus} object returned by
     * {@link #parseJavadocAsDetailNode(DetailAST)} method via the instance member
     * {@link ParseStatus#firstNonTightHtmlTag}, and checks not supposed to process non-tight HTML
     * or the ones which are supposed to log violation for non-tight javadocs can utilize that.
     *
     * @param javadocParser The ANTLR recognizer instance which has been used to parse the javadoc
     * @param javadocLineOffset The line number of beginning of the Javadoc comment
     * @return First non-tight HTML tag if one exists; null otherwise
     */
    private static Token getFirstNonTightHtmlTag(JavadocParser javadocParser,
            int javadocLineOffset) {
        final CommonToken offendingToken;
        final ParserRuleContext nonTightTagStartContext = javadocParser.nonTightTagStartContext;
        if (nonTightTagStartContext == null) {
            offendingToken = null;
        }
        else {
            final Token token = ((TerminalNode) nonTightTagStartContext.getChild(1))
                    .getSymbol();
            offendingToken = new CommonToken(token);
            offendingToken.setLine(offendingToken.getLine() + javadocLineOffset);
        }
        return offendingToken;
    }

    /**
     * Converts the given {@code text} from camel case to all upper case with
     * underscores separating each word.
     *
     * @param text The string to convert.
     * @return The result of the conversion.
     */
    private static String convertUpperCamelToUpperUnderscore(String text) {
        final StringBuilder result = new StringBuilder(20);
        boolean first = true;
        for (char letter : text.toCharArray()) {
            if (!first && Character.isUpperCase(letter)) {
                result.append('_');
            }
            result.append(Character.toUpperCase(letter));
            first = false;
        }
        return result.toString();
    }

    /**
     * Custom error listener for JavadocParser that prints user readable errors.
     */
    private static final class DescriptiveErrorListener extends BaseErrorListener {

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
         *
         * @return Error message during parsing.
         */
        private ParseErrorMessage getErrorMessage() {
            return errorMessage;
        }

        /**
         * Sets offset. Offset is line number of beginning of the Javadoc
         * comment. Log messages should have line number in scope of file, not
         * in scope of Javadoc comment.
         *
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

            if (MSG_JAVADOC_WRONG_SINGLETON_TAG.equals(msg)) {
                errorMessage = new ParseErrorMessage(lineNumber,
                        MSG_JAVADOC_WRONG_SINGLETON_TAG, charPositionInLine,
                        ((Token) offendingSymbol).getText());

                throw new IllegalArgumentException(msg);
            }

            final int ruleIndex = ex.getCtx().getRuleIndex();
            final String ruleName = recognizer.getRuleNames()[ruleIndex];
            final String upperCaseRuleName = convertUpperCamelToUpperUnderscore(ruleName);

            errorMessage = new ParseErrorMessage(lineNumber,
                    MSG_JAVADOC_PARSE_RULE_ERROR, charPositionInLine, msg, upperCaseRuleName);

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
         * Stores the first non-tight HTML tag encountered while parsing javadoc.
         *
         * @see <a
         *     href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
         *     Tight HTML rules</a>
         */
        private Token firstNonTightHtmlTag;

        /**
         * Getter for DetailNode tree.
         *
         * @return DetailNode tree if parsing was successful, null otherwise.
         */
        public DetailNode getTree() {
            return tree;
        }

        /**
         * Sets DetailNode tree.
         *
         * @param tree DetailNode tree.
         */
        public void setTree(DetailNode tree) {
            this.tree = tree;
        }

        /**
         * Getter for error message during parsing.
         *
         * @return Error message if parsing was unsuccessful, null otherwise.
         */
        public ParseErrorMessage getParseErrorMessage() {
            return parseErrorMessage;
        }

        /**
         * Sets parse error message.
         *
         * @param parseErrorMessage Parse error message.
         */
        public void setParseErrorMessage(ParseErrorMessage parseErrorMessage) {
            this.parseErrorMessage = parseErrorMessage;
        }

        /**
         * This method is used to check if the javadoc parsed has non-tight HTML tags.
         *
         * @return returns true if the javadoc has at least one non-tight HTML tag; false otherwise
         * @see <a
         *     href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
         *     Tight HTML rules</a>
         */
        public boolean isNonTight() {
            return firstNonTightHtmlTag != null;
        }

        /**
         * Getter for the first non-tight HTML tag encountered while parsing javadoc.
         *
         * @return the first non-tight HTML tag that is encountered while parsing Javadoc,
         *     if one exists
         * @see <a href="https://checkstyle.org/writingjavadocchecks.html#Tight-HTML_rules">
         *     Tight HTML rules</a>
         */
        public Token getFirstNonTightHtmlTag() {
            return firstNonTightHtmlTag;
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
        /* package */ ParseErrorMessage(int lineNumber, String messageKey,
                Object... messageArguments) {
            this.lineNumber = lineNumber;
            this.messageKey = messageKey;
            this.messageArguments = messageArguments.clone();
        }

        /**
         * Getter for line number where parse error occurred.
         *
         * @return Line number where parse error occurred.
         */
        public int getLineNumber() {
            return lineNumber;
        }

        /**
         * Getter for key for error message.
         *
         * @return Key for error message.
         */
        public String getMessageKey() {
            return messageKey;
        }

        /**
         * Getter for error message arguments.
         *
         * @return Array of error message arguments.
         */
        public Object[] getMessageArguments() {
            return messageArguments.clone();
        }

    }
}

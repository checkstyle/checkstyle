///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import java.util.Set;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.grammar.SimpleToken;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsLexer;
import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParser;
import com.puppycrawl.tools.checkstyle.utils.JavadocUtil;

/**
 * Used for parsing Javadoc comment as DetailNode tree.
 *
 */
public class JavadocDetailNodeParser {

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
     * Parses the given Javadoc comment AST into a {@link ParseStatus} object.
     *
     * <p>
     * This method extracts the raw Javadoc comment text from the supplied
     * {@link DetailAST}, creates a new lexer and parser for the Javadoc grammar,
     * and attempts to parse it into an AST of {@link DetailNode}s.
     * The parser uses {@link PredictionMode#SLL} for
     * faster performance and stops parsing on the first error encountered by
     * using {@link CheckstyleParserErrorStrategy}.
     * </p>
     *
     * @param javadocCommentAst
     *        the {@link DetailAST} node representing the Javadoc comment in the
     *        source file
     * @return a {@link ParseStatus} containing the root of the parsed Javadoc
     *        tree (if successful), the first non-tight HTML tag (if any), and
     *        the error message (if parsing failed)
     */
    public ParseStatus parseJavadocComment(DetailAST javadocCommentAst) {
        final int blockCommentLineNumber = javadocCommentAst.getLineNo();

        final String javadocComment = JavadocUtil.getJavadocCommentContent(javadocCommentAst);
        final ParseStatus result = new ParseStatus();

        // Use a new error listener each time to be able to use
        // one check instance for multiple files to be checked
        // without getting side effects.
        final DescriptiveErrorListener errorListener = new DescriptiveErrorListener();

        // Log messages should have line number in scope of file,
        // not in scope of Javadoc comment.
        // Offset is line number of beginning of Javadoc comment.
        errorListener.setOffset(javadocCommentAst.getLineNo() - 1);

        final JavadocCommentsLexer lexer =
                        new JavadocCommentsLexer(CharStreams.fromString(javadocComment), true);

        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill();

        final Set<SimpleToken> unclosedTags = lexer.getUnclosedTagNameTokens();
        final JavadocCommentsParser parser = new JavadocCommentsParser(tokens, unclosedTags);

        // set prediction mode to SLL to speed up parsing
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);

        // remove default error listeners
        parser.removeErrorListeners();

        parser.addErrorListener(errorListener);

        // JavadocParserErrorStrategy stops parsing on first parse error encountered unlike the
        // DefaultErrorStrategy used by ANTLR which rather attempts error recovery.
        parser.setErrorHandler(new CheckstyleParserErrorStrategy());

        try {
            final JavadocCommentsParser.JavadocContext javadoc = parser.javadoc();
            final int javadocColumnNumber = javadocCommentAst.getColumnNo()
                            + JAVADOC_START.length();

            final JavadocCommentsAstVisitor visitor = new JavadocCommentsAstVisitor(
                    tokens, blockCommentLineNumber, javadocColumnNumber);
            final DetailNode tree = visitor.visit(javadoc);

            result.setTree(tree);

            result.firstNonTightHtmlTag = visitor.getFirstNonTightHtmlTag();

            result.setParseErrorMessage(errorListener.getErrorMessage());
        }
        catch (ParseCancellationException | IllegalArgumentException exc) {
            result.setParseErrorMessage(errorListener.getErrorMessage());
        }

        return result;
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
        /* package */ void setOffset(int offset) {
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

            final String target;
            if (recognizer instanceof JavadocCommentsLexer lexer) {
                target = lexer.getPreviousToken().getText();
            }
            else {
                final int ruleIndex = ex.getCtx().getRuleIndex();
                final String ruleName = recognizer.getRuleNames()[ruleIndex];
                target = convertUpperCamelToUpperUnderscore(ruleName);
            }

            errorMessage = new ParseErrorMessage(lineNumber,
                    MSG_JAVADOC_PARSE_RULE_ERROR, charPositionInLine, msg, target);

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
            for (int i = 0; i < text.length(); i++) {
                final char letter = text.charAt(i);
                if (Character.isUpperCase(letter)) {
                    result.append('_');
                }
                result.append(Character.toUpperCase(letter));
            }
            return result.toString();
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
        private DetailNode firstNonTightHtmlTag;

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
        public DetailNode getFirstNonTightHtmlTag() {
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

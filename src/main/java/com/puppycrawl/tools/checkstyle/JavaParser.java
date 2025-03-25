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

import com.puppycrawl.tools.checkstyle.api.*;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser;
import com.puppycrawl.tools.checkstyle.utils.ParserUtil;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.PredictionMode;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

/**
 * Helper methods to parse java source files.
 */
// -@cs[ClassDataAbstractionCoupling] No way to split up class usage.
public final class JavaParser {

    /**
     * Enum to be used for test if comments should be used.
     */
    public enum Options {

        /**
         * Comments nodes should be processed.
         */
        WITH_COMMENTS,

        /**
         * Comments nodes should be ignored.
         */
        WITHOUT_COMMENTS,

    }

    /**
     * Stop instances being created.
     **/
    private JavaParser() {
    }

    /**
     * Static helper method to parses a Java source file.
     *
     * @param contents contains the contents of the file
     * @return the root of the AST
     * @throws CheckstyleException if the contents is not a valid Java source
     */
    public static DetailAST parse(FileContents contents)
            throws CheckstyleException {
        final String fullText = contents.getText().getFullText().toString();
        final CharStream codePointCharStream = CharStreams.fromString(fullText);
        final JavaLanguageLexer lexer = new JavaLanguageLexer(codePointCharStream, true);
        lexer.setCommentListener(contents);

        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        final JavaLanguageParser parser =
                new JavaLanguageParser(tokenStream, JavaLanguageParser.CLEAR_DFA_LIMIT);
        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        parser.removeErrorListeners();
        parser.setErrorHandler(new BailErrorStrategy());

        JavaLanguageParser.CompilationUnitContext compilationUnit;

        try {
            compilationUnit = parser.compilationUnit();
        }
        catch (ParseCancellationException e) {
            // if we get here, it means that the input is not parsable by SLL
            // we need to retry with LL
            tokenStream.seek(0);
            parser.setErrorHandler(new CheckstyleParserErrorStrategy());
            parser.addErrorListener(new CheckstyleErrorListener());
            parser.getInterpreter().setPredictionMode(PredictionMode.LL);

            try {
                compilationUnit = parser.compilationUnit();
            }
            catch (IllegalStateException ex) {
                final String exceptionMsg = String.format(Locale.ROOT,
                        "%s occurred while parsing file %s.",
                        ex.getClass().getSimpleName(), contents.getFileName());
                throw new CheckstyleException(exceptionMsg, ex);
            }
        }

        return new JavaAstVisitor(tokenStream).visit(compilationUnit);
    }

    /**
     * Parse a text and return the parse tree.
     *
     * @param text    the text to parse
     * @param options {@link Options} to control inclusion of comment nodes
     * @return the root node of the parse tree
     * @throws CheckstyleException if the text is not a valid Java source
     */
    public static DetailAST parseFileText(FileText text, Options options)
            throws CheckstyleException {
        final FileContents contents = new FileContents(text);
        final DetailAST ast = parse(contents);
        if (options == Options.WITH_COMMENTS) {
            appendHiddenCommentNodes(ast);
        }
        return ast;
    }

    /**
     * Parses Java source file.
     *
     * @param file    the file to parse
     * @param options {@link Options} to control inclusion of comment nodes
     * @return DetailAST tree
     * @throws IOException         if the file could not be read
     * @throws CheckstyleException if the file is not a valid Java source file
     */
    public static DetailAST parseFile(File file, Options options)
            throws IOException, CheckstyleException {
        final FileText text = new FileText(file,
                StandardCharsets.UTF_8.name());
        return parseFileText(text, options);
    }

    /**
     * Appends comment nodes to existing AST.
     * It traverses each node in AST, looks for hidden comment tokens
     * and appends found comment tokens as nodes in AST.
     *
     * @param root of AST
     * @return root of AST with comment nodes
     */
    public static DetailAST appendHiddenCommentNodes(DetailAST root) {
        DetailAST curNode = root;
        DetailAST lastNode = root;

        while (curNode != null) {
            lastNode = curNode;

            final List<Token> hiddenBefore = ((DetailAstImpl) curNode).getHiddenBefore();
            if (hiddenBefore != null) {
                DetailAST currentSibling = curNode;

                final ListIterator<Token> reverseCommentsIterator =
                        hiddenBefore.listIterator(hiddenBefore.size());

                while (reverseCommentsIterator.hasPrevious()) {
                    final DetailAST newCommentNode =
                            createCommentAstFromToken((CommonToken)
                                    reverseCommentsIterator.previous());
                    ((DetailAstImpl) currentSibling).addPreviousSibling(newCommentNode);

                    currentSibling = newCommentNode;
                }
            }

            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }
            curNode = toVisit;
        }
        if (lastNode != null) {
            final List<Token> hiddenAfter = ((DetailAstImpl) lastNode).getHiddenAfter();
            if (hiddenAfter != null) {
                DetailAST currentSibling = lastNode;
                for (Token token : hiddenAfter) {
                    final DetailAST newCommentNode =
                            createCommentAstFromToken((CommonToken) token);

                    ((DetailAstImpl) currentSibling).addNextSibling(newCommentNode);

                    currentSibling = newCommentNode;
                }
            }
        }
        return root;
    }

    /**
     * Create comment AST from token. Depending on token type
     * SINGLE_LINE_COMMENT or BLOCK_COMMENT_BEGIN is created.
     *
     * @param token to create the AST
     * @return DetailAST of comment node
     */
    private static DetailAST createCommentAstFromToken(CommonToken token) {
        final DetailAST commentAst;
        if (token.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            commentAst = createSlCommentNode(token);
        } else {
            commentAst = ParserUtil.createBlockCommentNode(token);
        }
        return commentAst;
    }

    /**
     * Create single-line comment from token.
     *
     * @param token to create the AST
     * @return DetailAST with SINGLE_LINE_COMMENT type
     */
    private static DetailAST createSlCommentNode(Token token) {
        final DetailAstImpl slComment = new DetailAstImpl();
        slComment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        slComment.setText("//");

        slComment.setColumnNo(token.getCharPositionInLine());
        slComment.setLineNo(token.getLine());

        final DetailAstImpl slCommentContent = new DetailAstImpl();
        slCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // plus length of '//'
        slCommentContent.setColumnNo(token.getCharPositionInLine() + 2);
        slCommentContent.setLineNo(token.getLine());
        slCommentContent.setText(token.getText());

        slComment.addChild(slCommentContent);
        return slComment;
    }

    /**
     * Custom error listener to provide detailed exception message.
     */
    private static final class CheckstyleErrorListener extends BaseErrorListener {

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException ex) {
            final String message = line + ":" + charPositionInLine + ": " + msg;
            throw new IllegalStateException(message, ex);
        }
    }
}

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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import javax.annotation.Nullable;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser;
import com.puppycrawl.tools.checkstyle.utils.ParserUtil;

/**
 * Helper methods to parse java source files.
 *
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

    /** Stop instances being created. */
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
        parser.setErrorHandler(new CheckstyleParserErrorStrategy());
        parser.removeErrorListeners();
        parser.addErrorListener(new CheckstyleErrorListener());

        final JavaLanguageParser.CompilationUnitContext compilationUnit;
        try {
            compilationUnit = parser.compilationUnit();
        }
        catch (IllegalStateException exc) {
            final String exceptionMsg = String.format(Locale.ROOT,
                "%s occurred while parsing file %s.",
                exc.getClass().getSimpleName(), contents.getFileName());
            throw new CheckstyleException(exceptionMsg, exc);
        }

        return new JavaAstVisitor(tokenStream).visit(compilationUnit);
    }

    /**
     * Parse a text and return the parse tree.
     *
     * @param text the text to parse
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
     * @param file the file to parse
     * @param options {@link Options} to control inclusion of comment nodes
     * @return DetailAST tree
     * @throws CheckstyleException if the file is not a valid Java source file
     * @throws IOException if the file could not be read
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
        DetailAST lastRcurly = null;

        while (curNode != null) {
            lastNode = curNode;

            final List<Token> hiddenBefore = ((DetailAstImpl) curNode).getHiddenBefore();
            if (hiddenBefore != null) {
                appendCommentsBefore(curNode, hiddenBefore, lastRcurly);
            }

            if (curNode.getType() == TokenTypes.RCURLY) {
                lastRcurly = curNode;
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
     * Appends the hidden comment tokens found before the node as comment nodes.
     * A comment that trails a closing brace on the same line and belongs to a class member
     * is placed before the whole member instead of inside the member's first child.
     *
     * @param curNode the node the comment tokens are hidden before
     * @param hiddenBefore the hidden comment tokens before the node
     * @param lastRcurly last closing brace node visited, may be null
     */
    private static void appendCommentsBefore(DetailAST curNode, List<Token> hiddenBefore,
                                             @Nullable DetailAST lastRcurly) {
        DetailAST currentSibling = curNode;
        final DetailAST leftmostAncestor = getLeftmostAncestor(curNode);

        final ListIterator<Token> reverseCommentsIterator =
                hiddenBefore.listIterator(hiddenBefore.size());

        while (reverseCommentsIterator.hasPrevious()) {
            final Token token = reverseCommentsIterator.previous();
            final DetailAST newCommentNode =
                    createCommentAstFromToken((CommonToken) token);
            if (!reverseCommentsIterator.hasPrevious()
                    && isClassMember(leftmostAncestor)
                    && isTrailingCommentAfterRcurly(lastRcurly, token)) {
                ((DetailAstImpl) leftmostAncestor).addPreviousSibling(newCommentNode);
            }
            else {
                ((DetailAstImpl) currentSibling).addPreviousSibling(newCommentNode);

                currentSibling = newCommentNode;
            }
        }
    }

    /**
     * Checks whether the comment token trails the given closing brace on the same line.
     *
     * @param rcurly last closing brace node visited, may be null
     * @param token comment token
     * @return true if the comment follows the closing brace directly on its line
     */
    private static boolean isTrailingCommentAfterRcurly(@Nullable DetailAST rcurly,
                                                        Token token) {
        boolean result = false;
        if (rcurly != null && rcurly.getLineNo() == token.getLine()) {
            final List<Token> hiddenAfter = ((DetailAstImpl) rcurly).getHiddenAfter();
            result = hiddenAfter != null && hiddenAfter.contains(token);
        }
        return result;
    }

    /**
     * Checks that the node is a member of a type body or of a compact source file.
     *
     * @param node the node to check
     * @return true if the parent of the node is an {@code OBJBLOCK}
     *     or a {@code COMPACT_COMPILATION_UNIT}
     */
    private static boolean isClassMember(DetailAST node) {
        final DetailAST parent = node.getParent();
        return parent != null
                && (parent.getType() == TokenTypes.OBJBLOCK
                    || parent.getType() == TokenTypes.COMPACT_COMPILATION_UNIT);
    }

    /**
     * Finds the outermost ancestor (or the node itself) that starts with the given node.
     * A childless {@code MODIFIERS} node in front of the node is ignored, so that members
     * without modifiers are treated the same way as members with modifiers.
     *
     * @param node the node to start from
     * @return the outermost node whose first descendant leaf is the given node
     */
    private static DetailAST getLeftmostAncestor(DetailAST node) {
        DetailAST result = node;
        while (result.getParent() != null && isFirstSkippingEmptyModifiers(result)) {
            result = result.getParent();
        }
        return result;
    }

    /**
     * Checks that the node is the first child of its parent, not counting
     * an empty {@code MODIFIERS} node.
     *
     * @param node the node to check, it must have a parent
     * @return true if only an empty {@code MODIFIERS} node may precede the node
     */
    private static boolean isFirstSkippingEmptyModifiers(DetailAST node) {
        DetailAST previous = node.getPreviousSibling();
        while (previous != null
                && previous.getType() == TokenTypes.MODIFIERS
                && !previous.hasChildren()) {
            previous = previous.getPreviousSibling();
        }
        return previous == null;
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
        }
        else {
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

        /**
         * Creates a new {@code CheckstyleErrorListener} instance.
         */
        private CheckstyleErrorListener() {
            // no code by default
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg, RecognitionException ex) {
            final String message = line + ":" + charPositionInLine + ": " + msg;
            throw new IllegalStateException(message, ex);
        }

    }

}

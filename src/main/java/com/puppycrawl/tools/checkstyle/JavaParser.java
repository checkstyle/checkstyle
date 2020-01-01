////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import antlr.CommonASTWithHiddenTokens;
import antlr.CommonHiddenStreamToken;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenStreamException;
import antlr.TokenStreamHiddenTokenFilter;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammar.GeneratedJavaLexer;
import com.puppycrawl.tools.checkstyle.grammar.GeneratedJavaRecognizer;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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

    /** Stop instances being created. **/
    private JavaParser() {
    }

    /**
     * Static helper method to parses a Java source file.
     * @param contents contains the contents of the file
     * @return the root of the AST
     * @throws CheckstyleException if the contents is not a valid Java source
     */
    public static DetailAST parse(FileContents contents)
            throws CheckstyleException {
        final String fullText = contents.getText().getFullText().toString();
        final Reader reader = new StringReader(fullText);
        final GeneratedJavaLexer lexer = new GeneratedJavaLexer(reader);
        lexer.setCommentListener(contents);
        lexer.setTokenObjectClass("antlr.CommonHiddenStreamToken");

        final TokenStreamHiddenTokenFilter filter = new TokenStreamHiddenTokenFilter(lexer);
        filter.hide(TokenTypes.SINGLE_LINE_COMMENT);
        filter.hide(TokenTypes.BLOCK_COMMENT_BEGIN);

        final GeneratedJavaRecognizer parser = new GeneratedJavaRecognizer(filter) {
            @Override
            public void reportError(RecognitionException ex) {
                throw new IllegalStateException(ex);
            }
        };
        parser.setFilename(contents.getFileName());
        parser.setASTNodeClass(DetailAstImpl.class.getName());
        try {
            parser.compilationUnit();
        }
        catch (RecognitionException | TokenStreamException | IllegalStateException ex) {
            final String exceptionMsg = String.format(Locale.ROOT,
                "%s occurred while parsing file %s.",
                ex.getClass().getSimpleName(), contents.getFileName());
            throw new CheckstyleException(exceptionMsg, ex);
        }

        return (DetailAST) parser.getAST();
    }

    /**
     * Parse a text and return the parse tree.
     * @param text the text to parse
     * @param options {@link Options} to control inclusion of comment nodes
     * @return the root node of the parse tree
     * @throws CheckstyleException if the text is not a valid Java source
     */
    public static DetailAST parseFileText(FileText text, Options options)
            throws CheckstyleException {
        final FileContents contents = new FileContents(text);
        DetailAST ast = parse(contents);
        if (options == Options.WITH_COMMENTS) {
            ast = appendHiddenCommentNodes(ast);
        }
        return ast;
    }

    /**
     * Parses Java source file.
     * @param file the file to parse
     * @param options {@link Options} to control inclusion of comment nodes
     * @return DetailAST tree
     * @throws IOException if the file could not be read
     * @throws CheckstyleException if the file is not a valid Java source file
     */
    public static DetailAST parseFile(File file, Options options)
            throws IOException, CheckstyleException {
        final FileText text = new FileText(file.getAbsoluteFile(),
            System.getProperty("file.encoding", StandardCharsets.UTF_8.name()));
        return parseFileText(text, options);
    }

    /**
     * Appends comment nodes to existing AST.
     * It traverses each node in AST, looks for hidden comment tokens
     * and appends found comment tokens as nodes in AST.
     * @param root of AST
     * @return root of AST with comment nodes
     */
    public static DetailAST appendHiddenCommentNodes(DetailAST root) {
        DetailAST result = root;
        DetailAST curNode = root;
        DetailAST lastNode = root;

        while (curNode != null) {
            lastNode = curNode;

            CommonHiddenStreamToken tokenBefore = ((CommonASTWithHiddenTokens) curNode)
                    .getHiddenBefore();
            DetailAST currentSibling = curNode;
            while (tokenBefore != null) {
                final DetailAST newCommentNode =
                         createCommentAstFromToken(tokenBefore);

                ((DetailAstImpl) currentSibling).addPreviousSibling(newCommentNode);

                if (currentSibling == result) {
                    result = newCommentNode;
                }

                currentSibling = newCommentNode;
                tokenBefore = tokenBefore.getHiddenBefore();
            }

            DetailAST toVisit = curNode.getFirstChild();
            while (curNode != null && toVisit == null) {
                toVisit = curNode.getNextSibling();
                curNode = curNode.getParent();
            }
            curNode = toVisit;
        }
        if (lastNode != null) {
            CommonHiddenStreamToken tokenAfter = ((CommonASTWithHiddenTokens) lastNode)
                    .getHiddenAfter();
            DetailAST currentSibling = lastNode;
            while (tokenAfter != null) {
                final DetailAST newCommentNode =
                        createCommentAstFromToken(tokenAfter);

                ((DetailAstImpl) currentSibling).addNextSibling(newCommentNode);

                currentSibling = newCommentNode;
                tokenAfter = tokenAfter.getHiddenAfter();
            }
        }
        return result;
    }

    /**
     * Create comment AST from token. Depending on token type
     * SINGLE_LINE_COMMENT or BLOCK_COMMENT_BEGIN is created.
     * @param token to create the AST
     * @return DetailAST of comment node
     */
    private static DetailAST createCommentAstFromToken(Token token) {
        final DetailAST commentAst;
        if (token.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
            commentAst = createSlCommentNode(token);
        }
        else {
            commentAst = CommonUtil.createBlockCommentNode(token);
        }
        return commentAst;
    }

    /**
     * Create single-line comment from token.
     * @param token to create the AST
     * @return DetailAST with SINGLE_LINE_COMMENT type
     */
    private static DetailAST createSlCommentNode(Token token) {
        final DetailAstImpl slComment = new DetailAstImpl();
        slComment.setType(TokenTypes.SINGLE_LINE_COMMENT);
        slComment.setText("//");

        // column counting begins from 0
        slComment.setColumnNo(token.getColumn() - 1);
        slComment.setLineNo(token.getLine());

        final DetailAstImpl slCommentContent = new DetailAstImpl();
        slCommentContent.setType(TokenTypes.COMMENT_CONTENT);

        // column counting begins from 0
        // plus length of '//'
        slCommentContent.setColumnNo(token.getColumn() - 1 + 2);
        slCommentContent.setLineNo(token.getLine());
        slCommentContent.setText(token.getText());

        slComment.addChild(slCommentContent);
        return slComment;
    }

}

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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.dfa.DFA;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageLexer;
import com.puppycrawl.tools.checkstyle.grammar.java.JavaLanguageParser;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class JavaParserTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javaparser";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(TestUtil.isUtilsClassHasPrivateConstructor(JavaParser.class, false))
                .isTrue();
    }

    @Test
    public void testNullRootWithComments() {
        assertNull(JavaParser.appendHiddenCommentNodes(null), "Invalid return root");
    }

    @Test
    public void testAppendHiddenBlockCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> blockComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN);

        assertWithMessage("Block comment should be present")
                .that(blockComment.isPresent())
                .isTrue();

        final DetailAST comment = blockComment.get();

        assertEquals(3, comment.getLineNo(), "Unexpected line number");
        assertEquals(0, comment.getColumnNo(), "Unexpected column number");
        assertEquals("/*", comment.getText(), "Unexpected comment content");

        final DetailAST commentContent = comment.getFirstChild();
        final DetailAST commentEnd = comment.getLastChild();

        assertEquals(3, commentContent.getLineNo(), "Unexpected line number");
        assertEquals(2, commentContent.getColumnNo(), "Unexpected column number");
        assertEquals(9, commentEnd.getLineNo(), "Unexpected line number");
        assertEquals(1, commentEnd.getColumnNo(), "Unexpected column number");
    }

    @Test
    public void testAppendHiddenSingleLineCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertWithMessage("Single line comment should be present")
                .that(singleLineComment.isPresent())
                .isTrue();

        final DetailAST comment = singleLineComment.get();

        assertEquals(13, comment.getLineNo(), "Unexpected line number");
        assertEquals(0, comment.getColumnNo(), "Unexpected column number");
        assertEquals("//", comment.getText(), "Unexpected comment content");

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals(TokenTypes.COMMENT_CONTENT, commentContent.getType(), "Unexpected token type");
        assertEquals(13, commentContent.getLineNo(), "Unexpected line number");
        assertEquals(2, commentContent.getColumnNo(), "Unexpected column number");
        assertWithMessage("Unexpected comment content")
                .that(commentContent.getText().startsWith(" inline comment"))
                .isTrue();
    }

    @Test
    public void testAppendHiddenSingleLineCommentNodes2() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments2.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertWithMessage("Single line comment should be present")
                .that(singleLineComment.isPresent())
                .isTrue();

        final DetailAST comment = singleLineComment.get();

        assertEquals(1, comment.getLineNo(), "Unexpected line number");
        assertEquals(4, comment.getColumnNo(), "Unexpected column number");
        assertEquals("//", comment.getText(), "Unexpected comment content");

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals(TokenTypes.COMMENT_CONTENT, commentContent.getType(), "Unexpected token type");
        assertEquals(1, commentContent.getLineNo(), "Unexpected line number");
        assertEquals(6, commentContent.getColumnNo(), "Unexpected column number");
        assertWithMessage("Unexpected comment content")
                .that(commentContent.getText().startsWith(" indented comment"))
                .isTrue();
    }

    @Test
    public void testDontAppendCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertWithMessage("Single line comment should be present")
                .that(singleLineComment.isPresent())
                .isFalse();
    }

    @Test
    public void testParseException() throws Exception {
        final File input = new File(getNonCompilablePath("InputJavaParser.java"));
        try {
            JavaParser.parseFile(input, JavaParser.Options.WITH_COMMENTS);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertEquals(
                    CheckstyleException.class.getName()
                            + ": IllegalStateException occurred while parsing file "
                            + input.getAbsolutePath() + ".",
                    ex.toString(), "Invalid exception message");
            assertSame(IllegalStateException.class, ex.getCause().getClass(), "Invalid class");
            assertEquals(IllegalStateException.class.getName()
                            + ": 2:0: no viable alternative at input 'classD'",
                    ex.getCause().toString(), "Invalid exception message");
        }
    }

    @Test
    public void testComments() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments3.java")),
                JavaParser.Options.WITH_COMMENTS);
        final CountComments counter = new CountComments(root);

        assertArrayEquals(
                Arrays.asList("1,4", "6,4", "9,0").toArray(),
                counter.lineComments.toArray(), "Invalid line comments");
        assertArrayEquals(
                Arrays.asList("5,4", "8,0").toArray(),
                counter.blockComments.toArray(), "Invalid block comments");
    }

    @Test
    public void testJava14TextBlocks() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(
                    getNonCompilablePath("InputJavaParserTextBlocks.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> textBlockContent = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.TEXT_BLOCK_CONTENT);

        assertWithMessage("Text block content should be present")
                .that(textBlockContent.isPresent())
                .isTrue();

        final DetailAST content = textBlockContent.get();
        final String expectedContents = "\n                 string";

        assertEquals(5, content.getLineNo(), "Unexpected line number");
        assertEquals(32, content.getColumnNo(), "Unexpected column number");
        assertEquals(expectedContents, content.getText(), "Unexpected text block content");
    }

    @Test
    public void testNoStackOverflowOnDeepStringConcat() throws Exception {
        final File file =
                new File(getPath("InputJavaParserNoStackOverflowOnDeepStringConcat.java"));

        DetailAST ast = null;
        try {
            ast = TestUtil.getResultWithLimitedResources(() -> {
                return JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS);
            });
        }
        catch (Exception exception) {
            exception.printStackTrace(System.out);
        }

        assertWithMessage("File parsing should complete successfully.")
                .that(ast)
                .isNotNull();
    }

    @Test
    public void testNoFreezeOnDeeplyNestedLambdas() throws Exception {
        final File file =
                new File(getNonCompilablePath("InputJavaParserNoFreezeOnDeeplyNestedLambdas.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    public void testFullJavaIdentifierSupport() throws Exception {
        final File file =
                new File(getNonCompilablePath("InputJavaParserFullJavaIdentifierSupport.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    /**
     * In order to test successful DFA clearing in lexer and parser, we take advantage
     * of the fact that all instances of {@link JavaLanguageLexer} share one
     * {@link org.antlr.v4.runtime.atn.LexerATNSimulator#decisionToDFA} and
     * all instances of {@link JavaLanguageParser} share one
     * {@link org.antlr.v4.runtime.atn.ParserATNSimulator#decisionToDFA}.
     *
     * @throws Exception if file doesn't exist
     */
    @Test
    public void testClearAntlrDfa() throws Exception {
        final File inputFile = new File(getPath("InputJavaParserClearAntlrDFA.java"));
        final FileText fileText = new FileText(inputFile, StandardCharsets.UTF_8.name());
        final FileContents fileContents = new FileContents(fileText);

        // Instantiate antlr classes as we do in JavaParser,
        // this allows us to have an instance of the lexer and parser
        // so that we can access the DFA states.
        final String fullText = fileContents.getText().getFullText().toString();
        final CharStream codePointCharStream = CharStreams.fromString(fullText);
        final JavaLanguageLexer lexer = new JavaLanguageLexer(codePointCharStream, true);
        final CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        final JavaLanguageParser parser = new JavaLanguageParser(tokenStream);

        // Get initial DFA states, this is all we need to verify that
        // DFA states are cleared in JavaParser#parse.
        final DFA initialLexerDfa = lexer.getInterpreter().decisionToDFA[0];
        final DFA initialParserDfa = parser.getInterpreter().decisionToDFA[0];

        // Parse file, and clear DFA
        JavaParser.parse(fileContents);

        // Get final DFA states after parsing to make assertions about
        final DFA finalLexerDfa = lexer.getInterpreter().decisionToDFA[0];
        final DFA finalParserDfa = parser.getInterpreter().decisionToDFA[0];

        assertWithMessage("DFA states should not be equal after invocation of 'getParser'")
                .that(initialParserDfa)
                .isNotEqualTo(finalParserDfa);
    }

    private static final class CountComments {
        private final List<String> lineComments = new ArrayList<>();
        private final List<String> blockComments = new ArrayList<>();

        /* package */ CountComments(DetailAST root) {
            forEachChild(root);
        }

        private void forEachChild(DetailAST root) {
            for (DetailAST ast = root; ast != null; ast = ast.getNextSibling()) {
                if (ast.getType() == TokenTypes.SINGLE_LINE_COMMENT) {
                    lineComments.add(ast.getLineNo() + "," + ast.getColumnNo());
                }
                else if (ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
                    blockComments.add(ast.getLineNo() + "," + ast.getColumnNo());
                }

                forEachChild(ast.getFirstChild());
            }
        }
    }
}

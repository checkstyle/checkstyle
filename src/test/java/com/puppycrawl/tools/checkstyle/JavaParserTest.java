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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck.MSG_JAVADOC_CONTENT_SECOND_LINE;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class JavaParserTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javaparser";
    }

    @Test
    void isProperUtilsClass() throws Exception {
        assertWithMessage("Constructor is not private")
                .that(TestUtil.isUtilsClassHasPrivateConstructor(JavaParser.class))
                .isTrue();
    }

    @Test
    void nullRootWithComments() {
        assertWithMessage("Invalid return root")
            .that(JavaParser.appendHiddenCommentNodes(null))
            .isNull();
    }

    @Test
    void appendHiddenBlockCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> blockComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN);

        assertWithMessage("Block comment should be present")
                .that(blockComment.isPresent())
                .isTrue();

        final DetailAST comment = blockComment.orElseThrow();

        assertWithMessage("Unexpected line number")
            .that(comment.getLineNo())
            .isEqualTo(3);
        assertWithMessage("Unexpected column number")
            .that(comment.getColumnNo())
            .isEqualTo(0);
        assertWithMessage("Unexpected comment content")
            .that(comment.getText())
            .isEqualTo("/*");

        final DetailAST commentContent = comment.getFirstChild();
        final DetailAST commentEnd = comment.getLastChild();

        assertWithMessage("Unexpected line number")
            .that(commentContent.getLineNo())
            .isEqualTo(3);
        assertWithMessage("Unexpected column number")
            .that(commentContent.getColumnNo())
            .isEqualTo(2);
        assertWithMessage("Unexpected line number")
            .that(commentEnd.getLineNo())
            .isEqualTo(9);
        assertWithMessage("Unexpected column number")
            .that(commentEnd.getColumnNo())
            .isEqualTo(1);
    }

    @Test
    void appendHiddenSingleLineCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertWithMessage("Single line comment should be present")
            .that(singleLineComment.isPresent())
            .isTrue();

        final DetailAST comment = singleLineComment.orElseThrow();

        assertWithMessage("Unexpected line number")
            .that(comment.getLineNo())
            .isEqualTo(13);
        assertWithMessage("Unexpected column number")
            .that(comment.getColumnNo())
            .isEqualTo(0);
        assertWithMessage("Unexpected comment content")
            .that(comment.getText())
            .isEqualTo("//");

        final DetailAST commentContent = comment.getFirstChild();

        assertWithMessage("Unexpected token type")
            .that(commentContent.getType())
            .isEqualTo(TokenTypes.COMMENT_CONTENT);
        assertWithMessage("Unexpected line number")
            .that(commentContent.getLineNo())
            .isEqualTo(13);
        assertWithMessage("Unexpected column number")
            .that(commentContent.getColumnNo())
            .isEqualTo(2);
        assertWithMessage("Unexpected comment content")
                .that(commentContent.getText())
                .startsWith(" inline comment");
    }

    @Test
    void appendHiddenSingleLineCommentNodes2() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments2.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertWithMessage("Single line comment should be present")
                .that(singleLineComment.isPresent())
                .isTrue();

        final DetailAST comment = singleLineComment.orElseThrow();

        assertWithMessage("Unexpected line number")
            .that(comment.getLineNo())
            .isEqualTo(1);
        assertWithMessage("Unexpected column number")
            .that(comment.getColumnNo())
            .isEqualTo(4);
        assertWithMessage("Unexpected comment content")
            .that(comment.getText())
            .isEqualTo("//");

        final DetailAST commentContent = comment.getFirstChild();

        assertWithMessage("Unexpected token type")
            .that(commentContent.getType())
            .isEqualTo(TokenTypes.COMMENT_CONTENT);
        assertWithMessage("Unexpected line number")
            .that(commentContent.getLineNo())
            .isEqualTo(1);
        assertWithMessage("Unexpected column number")
            .that(commentContent.getColumnNo())
            .isEqualTo(6);
        assertWithMessage("Unexpected comment content")
                .that(commentContent.getText())
                .startsWith(" indented comment");
    }

    @Test
    void dontAppendCommentNodes() throws Exception {
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
    void parseException() throws Exception {
        final File input = new File(getNonCompilablePath("InputJavaParser.java"));
        try {
            JavaParser.parseFile(input, JavaParser.Options.WITH_COMMENTS);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.toString())
                .isEqualTo(CheckstyleException.class.getName()
                            + ": IllegalStateException occurred while parsing file "
                            + input.getAbsolutePath() + ".");
            assertWithMessage("Invalid class")
                .that(exc.getCause())
                .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Invalid exception message")
                .that(exc.getCause().toString())
                .isEqualTo(IllegalStateException.class.getName()
                            + ": 2:0: no viable alternative at input 'classD'");
        }
    }

    @Test
    void comments() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments3.java")),
                JavaParser.Options.WITH_COMMENTS);
        final CountComments counter = new CountComments(root);

        assertWithMessage("Invalid line comments")
            .that(counter.lineComments)
            .isEqualTo(Arrays.asList("1,4", "6,4", "9,0"));
        assertWithMessage("Invalid block comments")
            .that(counter.blockComments)
            .isEqualTo(Arrays.asList("5,4", "8,0"));
    }

    @Test
    void java14TextBlocks() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(
                    getPath("InputJavaParserTextBlocks.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> textBlockContent = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.TEXT_BLOCK_CONTENT);

        assertWithMessage("Text block content should be present")
                .that(textBlockContent.isPresent())
                .isTrue();

        final DetailAST content = textBlockContent.orElseThrow();
        final String expectedContents = "\n                 string";

        assertWithMessage("Unexpected line number")
            .that(content.getLineNo())
            .isEqualTo(5);
        assertWithMessage("Unexpected column number")
            .that(content.getColumnNo())
            .isEqualTo(32);
        assertWithMessage("Unexpected text block content")
            .that(content.getText())
            .isEqualTo(expectedContents);
    }

    @Test
    void noFreezeOnDeeplyNestedLambdas() throws Exception {
        final File file =
                new File(getNonCompilablePath("InputJavaParserNoFreezeOnDeeplyNestedLambdas.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport1() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport1.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport2() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport2.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport3() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport3.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport4() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport4.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport5() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport5.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport6() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport6.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport7() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport7.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport8() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport8.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport9() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport9.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport10() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport10.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport11() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport11.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport12() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport12.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport13() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport13.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport14() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport14.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport15() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport15.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void fullJavaIdentifierSupport16() throws Exception {
        final File file =
                new File(getPath("InputJavaParserFullJavaIdentifierSupport16.java"));
        assertWithMessage("File parsing should complete successfully.")
                .that(JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS))
                .isNotNull();
    }

    @Test
    void returnValueOfAppendHiddenCommentNodes()
            throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(JavadocContentLocationCheck.class,
                    MSG_JAVADOC_CONTENT_SECOND_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaParserHiddenComments4.java"), expected);
    }

    private static final class CountComments {
        private final List<String> lineComments = new ArrayList<>();
        private final List<String> blockComments = new ArrayList<>();

        private CountComments(DetailAST root) {
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

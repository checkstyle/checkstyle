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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import antlr.NoViableAltException;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class JavaParserTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javaparser";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(TestUtil.isUtilsClassHasPrivateConstructor(
            JavaParser.class, false), "Constructor is not private");
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

        assertTrue(blockComment.isPresent(), "Block comment should be present");

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
        assertTrue(singleLineComment.isPresent(), "Single line comment should be present");

        final DetailAST comment = singleLineComment.get();

        assertEquals(13, comment.getLineNo(), "Unexpected line number");
        assertEquals(0, comment.getColumnNo(), "Unexpected column number");
        assertEquals("//", comment.getText(), "Unexpected comment content");

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals(TokenTypes.COMMENT_CONTENT, commentContent.getType(), "Unexpected token type");
        assertEquals(13, commentContent.getLineNo(), "Unexpected line number");
        assertEquals(2, commentContent.getColumnNo(), "Unexpected column number");
        assertTrue(commentContent.getText().startsWith(" inline comment"),
                "Unexpected comment content");
    }

    @Test
    public void testAppendHiddenSingleLineCommentNodes2() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments2.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertTrue(singleLineComment.isPresent(), "Single line comment should be present");

        final DetailAST comment = singleLineComment.get();

        assertEquals(1, comment.getLineNo(), "Unexpected line number");
        assertEquals(4, comment.getColumnNo(), "Unexpected column number");
        assertEquals("//", comment.getText(), "Unexpected comment content");

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals(TokenTypes.COMMENT_CONTENT, commentContent.getType(), "Unexpected token type");
        assertEquals(1, commentContent.getLineNo(), "Unexpected line number");
        assertEquals(6, commentContent.getColumnNo(), "Unexpected column number");
        assertTrue(commentContent.getText().startsWith(" indented comment"),
                "Unexpected comment content");
    }

    @Test
    public void testDontAppendCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertFalse(singleLineComment.isPresent(), "Single line comment should be present");
    }

    @Test
    public void testParseException() throws Exception {
        final File input = new File(getNonCompilablePath("InputJavaParser.java"));
        try {
            JavaParser.parseFile(input, JavaParser.Options.WITH_COMMENTS);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertEquals(
                    CheckstyleException.class.getName()
                            + ": NoViableAltException occurred while parsing file "
                            + input.getAbsolutePath() + ".",
                    ex.toString(), "Invalid exception message");
            assertSame(NoViableAltException.class, ex.getCause().getClass(), "Invalid class");
            assertEquals(input.getAbsolutePath() + ":2:1: unexpected token: classD",
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

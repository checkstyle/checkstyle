////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

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
        assertTrue("Constructor is not private", TestUtil.isUtilsClassHasPrivateConstructor(
            JavaParser.class, false));
    }

    @Test
    public void testAppendHiddenBlockCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> blockComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.BLOCK_COMMENT_BEGIN);

        assertTrue("Block comment should be present", blockComment.isPresent());

        final DetailAST comment = blockComment.get();

        assertEquals("Unexpected line number", 3, comment.getLineNo());
        assertEquals("Unexpected column number", 0, comment.getColumnNo());
        assertEquals("Unexpected comment content", "/*", comment.getText());

        final DetailAST commentContent = comment.getFirstChild();
        final DetailAST commentEnd = comment.getLastChild();

        assertEquals("Unexpected line number", 3, commentContent.getLineNo());
        assertEquals("Unexpected column number", 2, commentContent.getColumnNo());
        assertEquals("Unexpected line number", 9, commentEnd.getLineNo());
        assertEquals("Unexpected column number", 1, commentEnd.getColumnNo());
    }

    @Test
    public void testAppendHiddenSingleLineCommentNodes() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertTrue("Single line comment should be present", singleLineComment.isPresent());

        final DetailAST comment = singleLineComment.get();

        assertEquals("Unexpected line number", 13, comment.getLineNo());
        assertEquals("Unexpected column number", 0, comment.getColumnNo());
        assertEquals("Unexpected comment content", "//", comment.getText());

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals("Unexpected token type", TokenTypes.COMMENT_CONTENT, commentContent.getType());
        assertEquals("Unexpected line number", 13, commentContent.getLineNo());
        assertEquals("Unexpected column number", 2, commentContent.getColumnNo());
        assertTrue("Unexpected comment content",
            commentContent.getText().startsWith(" inline comment"));
    }

    @Test
    public void testAppendHiddenSingleLineCommentNodes2() throws Exception {
        final DetailAST root =
            JavaParser.parseFile(new File(getPath("InputJavaParserHiddenComments2.java")),
                JavaParser.Options.WITH_COMMENTS);

        final Optional<DetailAST> singleLineComment = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.SINGLE_LINE_COMMENT);
        assertTrue("Single line comment should be present", singleLineComment.isPresent());

        final DetailAST comment = singleLineComment.get();

        assertEquals("Unexpected line number", 1, comment.getLineNo());
        assertEquals("Unexpected column number", 4, comment.getColumnNo());
        assertEquals("Unexpected comment content", "//", comment.getText());

        final DetailAST commentContent = comment.getFirstChild();

        assertEquals("Unexpected token type", TokenTypes.COMMENT_CONTENT, commentContent.getType());
        assertEquals("Unexpected line number", 1, commentContent.getLineNo());
        assertEquals("Unexpected column number", 6, commentContent.getColumnNo());
        assertTrue("Unexpected comment content",
            commentContent.getText().startsWith(" indented comment"));
    }

    /**
     * Could not find proper test case to test pitest mutations functionally.
     * Should be rewritten during grammar update.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testIsPositionGreater() throws Exception {
        final DetailAST ast1 = createAst(1, 3);
        final DetailAST ast2 = createAst(1, 2);
        final DetailAST ast3 = createAst(2, 2);

        final TreeWalker treeWalker = new TreeWalker();
        final Method isPositionGreater = Whitebox.getMethod(JavaParser.class,
                "isPositionGreater", DetailAST.class, DetailAST.class);

        assertTrue("Should return true when lines are equal and column is greater",
                (boolean) isPositionGreater.invoke(treeWalker, ast1, ast2));
        assertFalse("Should return false when lines are equal columns are equal",
                (boolean) isPositionGreater.invoke(treeWalker, ast1, ast1));
        assertTrue("Should return true when line is greater",
                (boolean) isPositionGreater.invoke(treeWalker, ast3, ast1));
    }

    @Test
    public void testParseException() throws Exception {
        final File input = new File(getNonCompilablePath("InputJavaParser.java"));
        try {
            JavaParser.parseFile(input, JavaParser.Options.WITH_COMMENTS);
            Assert.fail("exception expected");
        }
        catch (CheckstyleException ex) {
            Assert.assertSame("Invalid class",
                    NoViableAltException.class, ex.getCause().getClass());
            assertEquals("Invalid exception message",
                    input.getAbsolutePath() + ":2:1: unexpected token: classD",
                    ex.getCause().toString());
        }
    }

    private static DetailAST createAst(int line, int column) {
        final DetailAST ast = new DetailAST();
        ast.setLineNo(line);
        ast.setColumnNo(column);
        return ast;
    }

}

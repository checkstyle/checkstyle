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

import org.junit.Test;
import org.powermock.reflect.Whitebox;

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

        final DetailAST commentContent = blockComment.get().getFirstChild();
        final DetailAST commentEnd = blockComment.get().getLastChild();

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

        final DetailAST commentContent = singleLineComment.get().getFirstChild();

        assertEquals("Unexpected token type", TokenTypes.COMMENT_CONTENT, commentContent.getType());
        assertEquals("Unexpected line number", 13, commentContent.getLineNo());
        assertEquals("Unexpected column number", 2, commentContent.getColumnNo());
        assertTrue("Unexpected comment content",
            commentContent.getText().startsWith(" inline comment"));
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

    private static DetailAST createAst(int line, int column) {
        final DetailAST ast = new DetailAST();
        ast.setLineNo(line);
        ast.setColumnNo(column);
        return ast;
    }

}

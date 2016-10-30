////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.gui;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import antlr.collections.AST;

import com.puppycrawl.tools.checkstyle.TreeWalker;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;

public class ParseTreeTablePModelTest {

    private DetailAST tree;

    public static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/gui/" + filename;
    }

    public static DetailAST parseFile(File file) throws Exception {
        final FileContents contents = new FileContents(
                new FileText(file.getAbsoluteFile(), System.getProperty("file.encoding", "UTF-8")));
        return TreeWalker.parseWithComments(contents);
    }

    @Before
    public void loadTree() throws Exception {
        tree = parseFile(
                new File(getPath("InputJavadocAttributesAndMethods.java")));
    }

    @Test
    public void testRoot() {
        final Object root = new ParseTreeTablePModel(tree).getRoot();
        final int childCount = new ParseTreeTablePModel(null).getChildCount(root);
        Assert.assertEquals(1, childCount);
    }

    @Test
    public void testChildCount() {
        final int childCount = new ParseTreeTablePModel(null).getChildCount(tree);
        Assert.assertEquals(5, childCount);
    }

    @Test
    public void testChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int childCount = parseTree.getChildCount(tree);
        Assert.assertEquals(5, childCount);
    }

    @Test
    public void testChild() {
        final Object child = new ParseTreeTablePModel(null).getChild(tree, 1);
        Assert.assertTrue(child instanceof DetailAST);
        Assert.assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, ((AST) child).getType());
    }

    @Test
    public void testChildInJavaAndJavadocMode() {
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(tree, 1);
        Assert.assertTrue(child instanceof DetailAST);
        Assert.assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, ((AST) child).getType());
    }

    @Test
    public void testCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals(0, javadocCommentChildCount);
    }

    @Test
    public void testCommentChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals(0, commentChildCount);
    }

    @Test
    public void testCommentChildInJavaAndJavadocMode() {
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final Object commentChild = parseTree.getChild(commentContentNode, 0);
        Assert.assertNull(commentChild);
    }

    @Test
    public void testJavadocCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals(0, commentChildCount);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals(1, javadocCommentChildCount);
    }

    @Test
    public void testJavadocCommentChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue(child instanceof DetailNode);
        Assert.assertEquals(JavadocTokenTypes.JAVADOC, ((DetailNode) child).getType());
    }

    @Test
    public void testJavadocChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue(javadoc instanceof DetailNode);
        Assert.assertEquals(JavadocTokenTypes.JAVADOC, ((DetailNode) javadoc).getType());
        final int javadocChildCount = parseTree.getChildCount(javadoc);
        Assert.assertEquals(5, javadocChildCount);
    }

    @Test
    public void testJavadocChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePModel parseTree = new ParseTreeTablePModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue(javadoc instanceof DetailNode);
        Assert.assertEquals(JavadocTokenTypes.JAVADOC, ((DetailNode) javadoc).getType());
        final Object javadocChild = parseTree.getChild(javadoc, 2);
        Assert.assertTrue(javadoc instanceof DetailNode);
        Assert.assertEquals(JavadocTokenTypes.TEXT, ((DetailNode) javadocChild).getType());
    }

}

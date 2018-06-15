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

package com.puppycrawl.tools.checkstyle.gui;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import antlr.collections.AST;
import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class ParseTreeTablePresentationTest extends AbstractPathTestSupport {

    private DetailAST tree;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/parsetreetablepresentation";
    }

    @Before
    public void loadTree() throws Exception {
        tree = JavaParser.parseFile(new File(getPath("InputParseTreeTablePresentation.java")),
            JavaParser.Options.WITH_COMMENTS).getNextSibling();
    }

    @Test
    public void testRoot() {
        final Object root = new ParseTreeTablePresentation(tree).getRoot();
        final int childCount = new ParseTreeTablePresentation(null).getChildCount(root);
        Assert.assertEquals("Invalid child count", 1, childCount);
    }

    @Test
    public void testChildCount() {
        final int childCount = new ParseTreeTablePresentation(null).getChildCount(tree);
        Assert.assertEquals("Invalid child count", 5, childCount);
    }

    @Test
    public void testChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int childCount = parseTree.getChildCount(tree);
        Assert.assertEquals("Invalid child count", 5, childCount);
    }

    @Test
    public void testChild() {
        final Object child = new ParseTreeTablePresentation(null).getChild(tree, 1);
        Assert.assertTrue("Invalid child type", child instanceof DetailAST);
        Assert.assertEquals("Invalid child token type",
                TokenTypes.BLOCK_COMMENT_BEGIN, ((AST) child).getType());
    }

    @Test
    public void testChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(tree, 1);
        Assert.assertTrue("Invalid child type", child instanceof DetailAST);
        Assert.assertEquals("Invalid child token type",
                TokenTypes.BLOCK_COMMENT_BEGIN, ((AST) child).getType());
    }

    @Test
    public void testCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals("Invalid child count", 0, javadocCommentChildCount);
    }

    @Test
    public void testCommentChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals("Invalid child count", 0, commentChildCount);
    }

    @Test
    public void testCommentChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final Object commentChild = parseTree.getChild(commentContentNode, 0);
        Assert.assertNull("Child must be null", commentChild);
    }

    @Test
    public void testJavadocCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals("Invalid child count", 0, commentChildCount);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        Assert.assertEquals("Invalid child count", 1, javadocCommentChildCount);
    }

    @Test
    public void testJavadocCommentChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue("Invalid child type", child instanceof DetailNode);
        Assert.assertEquals("Invalid child token type",
                JavadocTokenTypes.JAVADOC, ((DetailNode) child).getType());
        // get Child one more time to test cache of PModel
        final Object childSame = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue("Invalid child type", childSame instanceof DetailNode);
        Assert.assertEquals("Invalid child token type",
                JavadocTokenTypes.JAVADOC, ((DetailNode) childSame).getType());
    }

    @Test
    public void testJavadocChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue("Invalid child type", javadoc instanceof DetailNode);
        Assert.assertEquals("Invalid child token type",
                JavadocTokenTypes.JAVADOC, ((DetailNode) javadoc).getType());
        final int javadocChildCount = parseTree.getChildCount(javadoc);
        Assert.assertEquals("Invalid child count", 5, javadocChildCount);
    }

    @Test
    public void testJavadocChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        Assert.assertTrue("Invalid child type", javadoc instanceof DetailNode);
        Assert.assertEquals("Invalid child token type",
                JavadocTokenTypes.JAVADOC, ((DetailNode) javadoc).getType());
        final Object javadocChild = parseTree.getChild(javadoc, 2);
        Assert.assertTrue("Invalid child type", javadocChild instanceof DetailNode);
        Assert.assertEquals("Invalid child token type",
                JavadocTokenTypes.TEXT, ((DetailNode) javadocChild).getType());
    }

    @Test
    public void testGetIndexOfChild() {
        DetailAST ithChild = tree.getFirstChild();
        Assert.assertNotNull("Child must not be null", ithChild);
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        int index = 0;
        while (ithChild != null) {
            Assert.assertEquals("Invalid child index",
                    index, parseTree.getIndexOfChild(tree, ithChild));
            ithChild = ithChild.getNextSibling();
            index++;
        }

        Assert.assertEquals("Invalid child index",
                -1, parseTree.getIndexOfChild(tree, new DetailAST()));
    }

    /**
     * The path to class name in InputJavadocAttributesAndMethods.java.
     * <pre>
     * CLASS_DEF
     *  - MODIFIERS
     *  - Comment node
     *  - LITERAL_CLASS
     *  - IDENT -> this is the node that holds the class name
     *  Line number 4 - first three lines are taken by javadoc
     *  Column 6 - first five columns taken by 'class '
     *  </pre>
     */
    @Test
    public void testGetValueAt() {
        final DetailAST node = tree.getFirstChild()
            .getNextSibling()
            .getNextSibling()
            .getNextSibling();

        Assert.assertNotNull("Expected a non-null identifier node here", node);
        Assert.assertEquals("Expected identifier token",
            TokenTypes.IDENT, node.getType());

        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final Object treeModel = parseTree.getValueAt(node, 0);
        final String type = (String) parseTree.getValueAt(node, 1);
        final int line = (int) parseTree.getValueAt(node, 2);
        final int column = (int) parseTree.getValueAt(node, 3);
        final String text = (String) parseTree.getValueAt(node, 4);

        Assert.assertEquals("Node should be an Identifier", "IDENT", type);
        Assert.assertEquals("Class identifier should start on line 6", 6, line);
        Assert.assertEquals("Class name should start from column 6", 6, column);
        Assert.assertEquals("Wrong class name", "InputParseTreeTablePresentation", text);
        Assert.assertNull("Root node should have null value", treeModel);

        try {
            parseTree.getValueAt(node, parseTree.getColumnCount());
            Assert.fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            Assert.assertEquals("Invalid error message", "Unknown column", ex.getMessage());
        }
    }

    @Test
    public void testGetValueAtDetailNode() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        Assert.assertNotNull("Comment node cannot be null", commentContentNode);
        final int nodeType = commentContentNode.getType();
        Assert.assertTrue("Comment node should be a comment type",
            TokenUtil.isCommentType(nodeType));
        Assert.assertEquals("This should be a javadoc comment",
            "/*", commentContentNode.getParent().getText());
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);

        Assert.assertFalse("Child has not to be leaf", parseTree.isLeaf(child));
        Assert.assertTrue("Child has to be leaf", parseTree.isLeaf(tree.getFirstChild()));

        final Object treeModel = parseTree.getValueAt(child, 0);
        final String type = (String) parseTree.getValueAt(child, 1);
        final int line = (int) parseTree.getValueAt(child, 2);
        final int column = (int) parseTree.getValueAt(child, 3);
        final String text = (String) parseTree.getValueAt(child, 4);
        final String expectedText = "JAVADOC";

        Assert.assertNull("Tree model must be null", treeModel);
        Assert.assertEquals("Invalid type", "JAVADOC", type);
        Assert.assertEquals("Invalid line", 3, line);
        Assert.assertEquals("Invalid column", 3, column);
        Assert.assertEquals("Invalid text", expectedText, text);

        try {
            parseTree.getValueAt(child, parseTree.getColumnCount());
            Assert.fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            Assert.assertEquals("Invalid error message", "Unknown column", ex.getMessage());
        }
    }

    @Test
    public void testColumnMethods() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        Assert.assertSame("Invalid type", ParseTreeTableModel.class, parseTree.getColumnClass(0));
        Assert.assertSame("Invalid type", String.class, parseTree.getColumnClass(1));
        Assert.assertSame("Invalid type", Integer.class, parseTree.getColumnClass(2));
        Assert.assertSame("Invalid type", Integer.class, parseTree.getColumnClass(3));
        Assert.assertSame("Invalid type", String.class, parseTree.getColumnClass(4));

        try {
            parseTree.getColumnClass(parseTree.getColumnCount());
            Assert.fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            Assert.assertEquals("Invalid error message", "Unknown column", ex.getMessage());
        }

        Assert.assertFalse("Invalid cell editable status", parseTree.isCellEditable(1));

        Assert.assertEquals("Invalid column count", 5, parseTree.getColumnCount());
        Assert.assertEquals("Invalid column name", "Tree", parseTree.getColumnName(0));
        Assert.assertEquals("Invalid column name", "Type", parseTree.getColumnName(1));
        Assert.assertEquals("Invalid column name", "Line", parseTree.getColumnName(2));
        Assert.assertEquals("Invalid column name", "Column", parseTree.getColumnName(3));
        Assert.assertEquals("Invalid column name", "Text", parseTree.getColumnName(4));
    }

}

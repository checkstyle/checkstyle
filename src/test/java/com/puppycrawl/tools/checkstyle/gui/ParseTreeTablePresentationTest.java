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

package com.puppycrawl.tools.checkstyle.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
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

    @BeforeEach
    public void loadTree() throws Exception {
        tree = JavaParser.parseFile(new File(getPath("InputParseTreeTablePresentation.java")),
            JavaParser.Options.WITH_COMMENTS).getNextSibling();
    }

    @Test
    public void testRoot() {
        final Object root = new ParseTreeTablePresentation(tree).getRoot();
        final int childCount = new ParseTreeTablePresentation(null).getChildCount(root);
        assertEquals(1, childCount, "Invalid child count");
    }

    @Test
    public void testChildCount() {
        final int childCount = new ParseTreeTablePresentation(null).getChildCount(tree);
        assertEquals(5, childCount, "Invalid child count");
    }

    @Test
    public void testChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int childCount = parseTree.getChildCount(tree);
        assertEquals(5, childCount, "Invalid child count");
    }

    @Test
    public void testChild() {
        final Object child = new ParseTreeTablePresentation(null).getChild(tree, 1);
        assertTrue(child instanceof DetailAST, "Invalid child type");
        final int type = ((DetailAST) child).getType();
        assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, type, "Invalid child token type");
    }

    @Test
    public void testChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(tree, 1);
        assertTrue(child instanceof DetailAST, "Invalid child type");
        final int type = ((DetailAST) child).getType();
        assertEquals(TokenTypes.BLOCK_COMMENT_BEGIN, type, "Invalid child token type");
    }

    @Test
    public void testCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        assertEquals(0, javadocCommentChildCount, "Invalid child count");
    }

    @Test
    public void testCommentChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        assertEquals(0, commentChildCount, "Invalid child count");
    }

    @Test
    public void testCommentChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final Object commentChild = parseTree.getChild(commentContentNode, 0);
        assertNull(commentChild, "Child must be null");
    }

    @Test
    public void testJavadocCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        assertEquals(0, commentChildCount, "Invalid child count");
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        assertEquals(1, javadocCommentChildCount, "Invalid child count");
    }

    @Test
    public void testJavadocCommentChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);
        assertTrue(child instanceof DetailNode, "Invalid child type");
        final int type = ((DetailNode) child).getType();
        assertEquals(JavadocTokenTypes.JAVADOC, type, "Invalid child token type");
        // get Child one more time to test cache of PModel
        final Object childSame = parseTree.getChild(commentContentNode, 0);
        assertTrue(childSame instanceof DetailNode, "Invalid child type");
        final int sameType = ((DetailNode) childSame).getType();
        assertEquals(JavadocTokenTypes.JAVADOC, sameType, "Invalid child token type");
    }

    @Test
    public void testJavadocChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertTrue(javadoc instanceof DetailNode, "Invalid child type");
        final int type = ((DetailNode) javadoc).getType();
        assertEquals(JavadocTokenTypes.JAVADOC, type, "Invalid child token type");
        final int javadocChildCount = parseTree.getChildCount(javadoc);
        assertEquals(5, javadocChildCount, "Invalid child count");
    }

    @Test
    public void testJavadocChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertTrue(javadoc instanceof DetailNode, "Invalid child type");
        final int type = ((DetailNode) javadoc).getType();
        assertEquals(JavadocTokenTypes.JAVADOC, type, "Invalid child token type");
        final Object javadocChild = parseTree.getChild(javadoc, 2);
        assertTrue(javadocChild instanceof DetailNode, "Invalid child type");
        final int childType = ((DetailNode) javadocChild).getType();
        assertEquals(JavadocTokenTypes.TEXT, childType, "Invalid child token type");
    }

    @Test
    public void testGetIndexOfChild() {
        DetailAST ithChild = tree.getFirstChild();
        assertNotNull(ithChild, "Child must not be null");
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        int index = 0;
        while (ithChild != null) {
            final int indexOfChild = parseTree.getIndexOfChild(tree, ithChild);
            assertEquals(index, indexOfChild, "Invalid child index");
            ithChild = ithChild.getNextSibling();
            index++;
        }

        final int indexOfChild = parseTree.getIndexOfChild(tree, new DetailAstImpl());
        assertEquals(-1, indexOfChild, "Invalid child index");
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

        assertNotNull(node, "Expected a non-null identifier node here");
        assertEquals(TokenTypes.IDENT, node.getType(), "Expected identifier token");

        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final Object treeModel = parseTree.getValueAt(node, 0);
        final String type = (String) parseTree.getValueAt(node, 1);
        final int line = (int) parseTree.getValueAt(node, 2);
        final int column = (int) parseTree.getValueAt(node, 3);
        final String text = (String) parseTree.getValueAt(node, 4);

        assertEquals("IDENT", type, "Node should be an Identifier");
        assertEquals(6, line, "Class identifier should start on line 6");
        assertEquals(6, column, "Class name should start from column 6");
        assertEquals("InputParseTreeTablePresentation", text, "Wrong class name");
        assertNull(treeModel, "Root node should have null value");

        try {
            parseTree.getValueAt(node, parseTree.getColumnCount());
            fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Unknown column", ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testGetValueAtDetailNode() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        assertNotNull(commentContentNode, "Comment node cannot be null");
        final int nodeType = commentContentNode.getType();
        assertTrue(TokenUtil.isCommentType(nodeType), "Comment node should be a comment type");
        assertEquals("/*", commentContentNode.getParent().getText(),
                "This should be a javadoc comment");
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);

        assertFalse(parseTree.isLeaf(child), "Child has not to be leaf");
        assertTrue(parseTree.isLeaf(tree.getFirstChild()), "Child has to be leaf");

        final Object treeModel = parseTree.getValueAt(child, 0);
        final String type = (String) parseTree.getValueAt(child, 1);
        final int line = (int) parseTree.getValueAt(child, 2);
        final int column = (int) parseTree.getValueAt(child, 3);
        final String text = (String) parseTree.getValueAt(child, 4);
        final String expectedText = "JAVADOC";

        assertNull(treeModel, "Tree model must be null");
        assertEquals("JAVADOC", type, "Invalid type");
        assertEquals(3, line, "Invalid line");
        assertEquals(3, column, "Invalid column");
        assertEquals(expectedText, text, "Invalid text");

        try {
            parseTree.getValueAt(child, parseTree.getColumnCount());
            fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Unknown column", ex.getMessage(), "Invalid error message");
        }
    }

    @Test
    public void testColumnMethods() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        assertSame(ParseTreeTableModel.class, parseTree.getColumnClass(0), "Invalid type");
        assertSame(String.class, parseTree.getColumnClass(1), "Invalid type");
        assertSame(Integer.class, parseTree.getColumnClass(2), "Invalid type");
        assertSame(Integer.class, parseTree.getColumnClass(3), "Invalid type");
        assertSame(String.class, parseTree.getColumnClass(4), "Invalid type");

        try {
            parseTree.getColumnClass(parseTree.getColumnCount());
            fail("IllegalStateException expected");
        }
        catch (IllegalStateException ex) {
            assertEquals("Unknown column", ex.getMessage(), "Invalid error message");
        }

        assertFalse(parseTree.isCellEditable(1), "Invalid cell editable status");
    }

    @Test
    public void testColumnNames() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        assertEquals(5, parseTree.getColumnCount(), "Invalid column count");
        assertEquals("Tree", parseTree.getColumnName(0), "Invalid column name");
        assertEquals("Type", parseTree.getColumnName(1), "Invalid column name");
        assertEquals("Line", parseTree.getColumnName(2), "Invalid column name");
        assertEquals("Column", parseTree.getColumnName(3), "Invalid column name");
        assertEquals("Text", parseTree.getColumnName(4), "Invalid column name");
    }

}

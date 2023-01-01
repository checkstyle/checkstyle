///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;

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
            JavaParser.Options.WITH_COMMENTS).getFirstChild().getNextSibling();
    }

    @Test
    public void testRoot() throws Exception {
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputParseTreeTablePresentation.java")),
                JavaParser.Options.WITH_COMMENTS);
        assertWithMessage("Root node should have 2 children")
                .that(root.getChildCount())
                .isEqualTo(2);
    }

    @Test
    public void testChildCount() {
        final int childCount = new ParseTreeTablePresentation(null).getChildCount(tree);
        assertWithMessage("Invalid child count")
            .that(childCount)
            .isEqualTo(5);
    }

    @Test
    public void testChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int childCount = parseTree.getChildCount(tree);
        assertWithMessage("Invalid child count")
            .that(childCount)
            .isEqualTo(5);
    }

    @Test
    public void testChild() {
        final Object child = new ParseTreeTablePresentation(null).getChild(tree, 1);
        assertWithMessage("Invalid child type")
                .that(child instanceof DetailAST)
                .isTrue();
        final int type = ((DetailAST) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(TokenTypes.BLOCK_COMMENT_BEGIN);
    }

    @Test
    public void testChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(tree, 1);
        assertWithMessage("Invalid child type")
                .that(child instanceof DetailAST)
                .isTrue();
        final int type = ((DetailAST) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(TokenTypes.BLOCK_COMMENT_BEGIN);
    }

    @Test
    public void testCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(javadocCommentChildCount)
            .isEqualTo(0);
    }

    @Test
    public void testCommentChildCountInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(commentChildCount)
            .isEqualTo(0);
    }

    @Test
    public void testCommentChildInJavaAndJavadocMode() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final DetailAST commentContentNode = tree.getLastChild().getLastChild()
                .getPreviousSibling().getLastChild().getFirstChild().getFirstChild();
        final Object commentChild = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Child must be null")
            .that(commentChild)
            .isNull();
    }

    @Test
    public void testJavadocCommentChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(commentChildCount)
            .isEqualTo(0);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(javadocCommentChildCount)
            .isEqualTo(1);
    }

    @Test
    public void testJavadocCommentChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(child instanceof DetailNode)
                .isTrue();
        final int type = ((DetailNode) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocTokenTypes.JAVADOC);
        // get Child one more time to test cache of PModel
        final Object childSame = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(childSame instanceof DetailNode)
                .isTrue();
        final int sameType = ((DetailNode) childSame).getType();
        assertWithMessage("Invalid child token type")
            .that(sameType)
            .isEqualTo(JavadocTokenTypes.JAVADOC);
    }

    @Test
    public void testJavadocChildCount() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(javadoc instanceof DetailNode)
                .isTrue();
        final int type = ((DetailNode) javadoc).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocTokenTypes.JAVADOC);
        final int javadocChildCount = parseTree.getChildCount(javadoc);
        assertWithMessage("Invalid child count")
            .that(javadocChildCount)
            .isEqualTo(5);
    }

    @Test
    public void testJavadocChild() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(javadoc instanceof DetailNode)
                .isTrue();
        final int type = ((DetailNode) javadoc).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocTokenTypes.JAVADOC);
        final Object javadocChild = parseTree.getChild(javadoc, 2);
        assertWithMessage("Invalid child type")
                .that(javadocChild instanceof DetailNode)
                .isTrue();
        final int childType = ((DetailNode) javadocChild).getType();
        assertWithMessage("Invalid child token type")
            .that(childType)
            .isEqualTo(JavadocTokenTypes.TEXT);
    }

    @Test
    public void testGetIndexOfChild() {
        DetailAST ithChild = tree.getFirstChild();
        assertWithMessage("Child must not be null")
            .that(ithChild)
            .isNotNull();
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        int index = 0;
        while (ithChild != null) {
            final int indexOfChild = parseTree.getIndexOfChild(tree, ithChild);
            assertWithMessage("Invalid child index")
                .that(indexOfChild)
                .isEqualTo(index);
            ithChild = ithChild.getNextSibling();
            index++;
        }

        final int indexOfChild = parseTree.getIndexOfChild(tree, new DetailAstImpl());
        assertWithMessage("Invalid child index")
            .that(indexOfChild)
            .isEqualTo(-1);
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

        assertWithMessage("Expected a non-null identifier node here")
            .that(node)
            .isNotNull();
        assertWithMessage("Expected identifier token")
            .that(node.getType())
            .isEqualTo(TokenTypes.IDENT);

        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        final Object treeModel = parseTree.getValueAt(node, 0);
        final String type = (String) parseTree.getValueAt(node, 1);
        final int line = (int) parseTree.getValueAt(node, 2);
        final int column = (int) parseTree.getValueAt(node, 3);
        final String text = (String) parseTree.getValueAt(node, 4);

        assertWithMessage("Node should be an Identifier")
            .that(type)
            .isEqualTo("IDENT");
        assertWithMessage("Class identifier should start on line 6")
            .that(line)
            .isEqualTo(6);
        assertWithMessage("Class name should start from column 6")
            .that(column)
            .isEqualTo(6);
        assertWithMessage("Wrong class name")
            .that(text)
            .isEqualTo("InputParseTreeTablePresentation");
        assertWithMessage("Root node should have null value")
            .that(treeModel)
            .isNull();

        try {
            parseTree.getValueAt(node, parseTree.getColumnCount());
            assertWithMessage("IllegalStateException expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unknown column");
        }
    }

    @Test
    public void testGetValueAtDetailNode() {
        final DetailAST commentContentNode = tree.getFirstChild().getNextSibling().getFirstChild();
        assertWithMessage("Comment node cannot be null")
            .that(commentContentNode)
            .isNotNull();
        final int nodeType = commentContentNode.getType();
        assertWithMessage("Comment node should be a comment type")
                .that(TokenUtil.isCommentType(nodeType))
                .isTrue();
        assertWithMessage("This should be a javadoc comment")
            .that(commentContentNode.getParent().getText())
            .isEqualTo("/*");
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);

        assertWithMessage("Child has not to be leaf")
                .that(parseTree.isLeaf(child))
                .isFalse();
        assertWithMessage("Child has to be leaf")
                .that(parseTree.isLeaf(tree.getFirstChild()))
                .isTrue();

        final Object treeModel = parseTree.getValueAt(child, 0);
        final String type = (String) parseTree.getValueAt(child, 1);
        final int line = (int) parseTree.getValueAt(child, 2);
        final int column = (int) parseTree.getValueAt(child, 3);
        final String text = (String) parseTree.getValueAt(child, 4);
        final String expectedText = "JAVADOC";

        assertWithMessage("Tree model must be null")
            .that(treeModel)
            .isNull();
        assertWithMessage("Invalid type")
            .that(type)
            .isEqualTo("JAVADOC");
        assertWithMessage("Invalid line")
            .that(line)
            .isEqualTo(3);
        assertWithMessage("Invalid column")
            .that(column)
            .isEqualTo(3);
        assertWithMessage("Invalid text")
            .that(text)
            .isEqualTo(expectedText);

        try {
            parseTree.getValueAt(child, parseTree.getColumnCount());
            assertWithMessage("IllegalStateException expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unknown column");
        }
    }

    @Test
    public void testColumnMethods() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        assertWithMessage("Invalid type")
            .that(parseTree.getColumnClass(0))
            .isEqualTo(ParseTreeTableModel.class);
        assertWithMessage("Invalid type")
            .that(parseTree.getColumnClass(1))
            .isEqualTo(String.class);
        assertWithMessage("Invalid type")
            .that(parseTree.getColumnClass(2))
            .isEqualTo(Integer.class);
        assertWithMessage("Invalid type")
            .that(parseTree.getColumnClass(3))
            .isEqualTo(Integer.class);
        assertWithMessage("Invalid type")
            .that(parseTree.getColumnClass(4))
            .isEqualTo(String.class);

        try {
            parseTree.getColumnClass(parseTree.getColumnCount());
            assertWithMessage("IllegalStateException expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid error message")
                .that(ex.getMessage())
                .isEqualTo("Unknown column");
        }

        assertWithMessage("Invalid cell editable status")
                .that(parseTree.isCellEditable(1))
                .isFalse();
    }

    @Test
    public void testColumnNames() {
        final ParseTreeTablePresentation parseTree = new ParseTreeTablePresentation(null);
        assertWithMessage("Invalid column count")
            .that(parseTree.getColumnCount())
            .isEqualTo(5);
        assertWithMessage("Invalid column name")
            .that(parseTree.getColumnName(0))
            .isEqualTo("Tree");
        assertWithMessage("Invalid column name")
            .that(parseTree.getColumnName(1))
            .isEqualTo("Type");
        assertWithMessage("Invalid column name")
            .that(parseTree.getColumnName(2))
            .isEqualTo("Line");
        assertWithMessage("Invalid column name")
            .that(parseTree.getColumnName(3))
            .isEqualTo("Column");
        assertWithMessage("Invalid column name")
            .that(parseTree.getColumnName(4))
            .isEqualTo("Text");
    }

}

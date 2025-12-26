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

package com.puppycrawl.tools.checkstyle.gui;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.BLOCK_COMMENT_BEGIN;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.CLASS_DEF;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.COMMENT_CONTENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.IDENT;
import static com.puppycrawl.tools.checkstyle.api.TokenTypes.MODIFIERS;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.DetailNode;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.gui.MainFrameModel.ParseMode;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class ParseTreeTableModelTest extends AbstractPathTestSupport {

    private DetailAST classDef;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/gui/parsetreetablepresentation";
    }

    @BeforeEach
    void loadTree() throws Exception {
        classDef = JavaParser.parseFile(new File(getPath("InputParseTreeTablePresentation.java")),
            JavaParser.Options.WITH_COMMENTS).findFirstToken(CLASS_DEF);
    }

    @Test
    void childCount() {
        final int childCount = new ParseTreeTableModel(null).getChildCount(classDef);
        assertWithMessage("Invalid child count")
            .that(childCount)
            .isEqualTo(5);
    }

    @Test
    void child() {
        final Object child = new ParseTreeTableModel(null).getChild(classDef, 1);
        assertWithMessage("Invalid child type")
                .that(child)
                .isInstanceOf(DetailAST.class);
        final int type = ((DetailAST) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(BLOCK_COMMENT_BEGIN);
    }

    @Test
    void commentChildCount() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_COMMENTS);
        final int javadocCommentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(javadocCommentChildCount)
            .isEqualTo(0);
    }

    @Test
    void childCountInJavaAndJavadocMode() {
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int childCount = parseTree.getChildCount(classDef);
        assertWithMessage("Invalid child count")
            .that(childCount)
            .isEqualTo(5);
    }

    @Test
    void childInJavaAndJavadocMode() {
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(classDef, 1);
        assertWithMessage("Invalid child type")
                .that(child)
                .isInstanceOf(DetailAST.class);
        final int type = ((DetailAST) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(BLOCK_COMMENT_BEGIN);
    }

    @Test
    void commentChildCountInJavaAndJavadocMode() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final int commentChildCount = parseTree.getChildCount(commentContentNode);
        assertWithMessage("Invalid child count")
            .that(commentChildCount)
            .isEqualTo(1);
    }

    @Test
    void commentChildInJavaAndJavadocMode() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object commentChild = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Child is not null")
            .that(commentChild)
            .isNotNull();
    }

    @Test
    void javadocCommentChildCount() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
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
    void javadocCommentChild() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(child)
                .isInstanceOf(DetailNode.class);
        final int type = ((DetailNode) child).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocCommentsTokenTypes.JAVADOC_CONTENT);
        final Object childSame = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(childSame)
                .isInstanceOf(DetailNode.class);
        final int sameType = ((DetailNode) childSame).getType();
        assertWithMessage("Invalid child token type")
            .that(sameType)
            .isEqualTo(JavadocCommentsTokenTypes.JAVADOC_CONTENT);
    }

    @Test
    void javadocChildCount() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(javadoc)
                .isInstanceOf(DetailNode.class);
        final int type = ((DetailNode) javadoc).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocCommentsTokenTypes.JAVADOC_CONTENT);
        final int javadocChildCount = parseTree.getChildCount(javadoc);
        assertWithMessage("Invalid child count")
            .that(javadocChildCount)
            .isEqualTo(4);
    }

    @Test
    void javadocChild() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object javadoc = parseTree.getChild(commentContentNode, 0);
        assertWithMessage("Invalid child type")
                .that(javadoc)
                .isInstanceOf(DetailNode.class);
        final int type = ((DetailNode) javadoc).getType();
        assertWithMessage("Invalid child token type")
            .that(type)
            .isEqualTo(JavadocCommentsTokenTypes.JAVADOC_CONTENT);
        final Object javadocChild = parseTree.getChild(javadoc, 2);
        assertWithMessage("Invalid child type")
                .that(javadocChild)
                .isInstanceOf(DetailNode.class);
        final int childType = ((DetailNode) javadocChild).getType();
        assertWithMessage("Invalid child token type")
            .that(childType)
            .isEqualTo(JavadocCommentsTokenTypes.TEXT);
    }

    @Test
    void getIndexOfChild() {
        DetailAST child = classDef.findFirstToken(MODIFIERS);
        assertWithMessage("Child must not be null")
            .that(child)
            .isNotNull();
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        int index = 0;
        while (child != null) {
            final int indexOfChild = parseTree.getIndexOfChild(classDef, child);
            assertWithMessage("Invalid child index")
                .that(indexOfChild)
                .isEqualTo(index);
            child = child.getNextSibling();
            index++;
        }
        final int indexOfChild = parseTree.getIndexOfChild(classDef, new DetailAstImpl());
        assertWithMessage("Invalid child index")
            .that(indexOfChild)
            .isEqualTo(-1);
    }

    @Test
    void getValueAt() {
        final DetailAST classIdentNode = classDef.findFirstToken(IDENT);
        assertWithMessage("Expected a non-null identifier classDef here")
            .that(classIdentNode)
            .isNotNull();

        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        final Object treeModel = parseTree.getValueAt(classIdentNode, 0);
        final String type = (String) parseTree.getValueAt(classIdentNode, 1);
        final int line = (int) parseTree.getValueAt(classIdentNode, 2);
        final int column = (int) parseTree.getValueAt(classIdentNode, 3);
        final String text = (String) parseTree.getValueAt(classIdentNode, 4);

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
        assertWithMessage("Root classDef should have null value")
            .that(treeModel)
            .isNull();

        try {
            parseTree.getValueAt(classIdentNode, parseTree.getColumnCount());
            assertWithMessage("IllegalStateException expected").fail();
        }
        catch (IllegalStateException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unknown column");
        }
    }

    @Test
    void getValueAtDetailNode() {
        final DetailAST commentContentNode = classDef
                .findFirstToken(BLOCK_COMMENT_BEGIN).findFirstToken(COMMENT_CONTENT);
        assertWithMessage("Comment classDef cannot be null")
            .that(commentContentNode)
            .isNotNull();
        final int nodeType = commentContentNode.getType();
        assertWithMessage("Comment classDef should be a comment type")
                .that(TokenUtil.isCommentType(nodeType))
                .isTrue();
        assertWithMessage("This should be a javadoc comment")
            .that(commentContentNode.getParent().getText())
            .isEqualTo("/*");
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
        parseTree.setParseMode(ParseMode.JAVA_WITH_JAVADOC_AND_COMMENTS);
        final Object child = parseTree.getChild(commentContentNode, 0);

        assertWithMessage("Child has not to be leaf")
                .that(parseTree.isLeaf(child))
                .isFalse();
        assertWithMessage("Child has to be leaf")
                .that(parseTree.isLeaf(classDef.getFirstChild()))
                .isTrue();

        final Object treeModel = parseTree.getValueAt(child, 0);
        final String type = (String) parseTree.getValueAt(child, 1);
        final int line = (int) parseTree.getValueAt(child, 2);
        final int column = (int) parseTree.getValueAt(child, 3);
        final String text = (String) parseTree.getValueAt(child, 4);
        final String expectedText = "JAVADOC_CONTENT";

        assertWithMessage("Tree model must be null")
            .that(treeModel)
            .isNull();
        assertWithMessage("Invalid type")
            .that(type)
            .isEqualTo("JAVADOC_CONTENT");
        assertWithMessage("Invalid line")
            .that(line)
            .isEqualTo(3);
        assertWithMessage("Invalid column")
            .that(column)
            .isEqualTo(3);
        assertWithMessage("Invalid text")
            .that(text)
            .isEqualTo(expectedText);
    }

    @Test
    void columnMethods() {
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
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
        catch (IllegalStateException exc) {
            assertWithMessage("Invalid error message")
                .that(exc.getMessage())
                .isEqualTo("Unknown column");
        }

        assertWithMessage("Invalid cell editable status")
                .that(parseTree.isCellEditable(1))
                .isFalse();
    }

    @Test
    void columnNames() {
        final ParseTreeTableModel parseTree = new ParseTreeTableModel(null);
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

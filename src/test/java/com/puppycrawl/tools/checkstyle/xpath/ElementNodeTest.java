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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.puppycrawl.tools.checkstyle.internal.utils.XpathUtil.getXpathItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
import net.sf.saxon.tree.iter.ArrayIterator;
import net.sf.saxon.tree.iter.AxisIterator;
import net.sf.saxon.tree.iter.EmptyIterator;
import net.sf.saxon.tree.util.Navigator;

public class ElementNodeTest extends AbstractPathTestSupport {

    private static RootNode rootNode;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @BeforeEach
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathMapperAst.java"));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testParentChildOrdering() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.VARIABLE_DEF);

        final DetailAstImpl parentAST = new DetailAstImpl();
        parentAST.setFirstChild(detailAST);
        parentAST.setType(TokenTypes.METHOD_DEF);

        final AbstractNode parentNode = new ElementNode(rootNode, rootNode, parentAST, 1, 0);
        final AbstractNode childNode = new ElementNode(rootNode, parentNode, detailAST, 2, 0);
        assertEquals(-1, parentNode.compareOrder(childNode), "Incorrect ordering value");
        assertEquals(1, childNode.compareOrder(parentNode), "Incorrect ordering value");
    }

    @Test
    public void testSiblingsOrdering() {
        final DetailAstImpl detailAst1 = new DetailAstImpl();
        detailAst1.setType(TokenTypes.VARIABLE_DEF);

        final DetailAstImpl detailAst2 = new DetailAstImpl();
        detailAst2.setType(TokenTypes.NUM_INT);

        final DetailAstImpl parentAST = new DetailAstImpl();
        parentAST.setType(TokenTypes.METHOD_DEF);
        parentAST.addChild(detailAst1);
        parentAST.addChild(detailAst2);

        final AbstractNode parentNode = new ElementNode(rootNode, rootNode, parentAST, 1, 0);
        final List<AbstractNode> children = parentNode.getChildren();

        assertEquals(-1, children.get(0).compareOrder(children.get(1)), "Incorrect ordering value");
        assertEquals(1, children.get(1).compareOrder(children.get(0)), "Incorrect ordering value");
    }

    @Test
    public void testCompareOrderWrongInstance() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final int result = nodes.get(0).compareOrder(null);
        assertEquals(0, result, "Expected result wrong");
    }

    @Test
    public void testGetParent() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertEquals(1, nodes.size(), "Invalid number of nodes");
        final AbstractNode parent = (AbstractNode) nodes.get(0).getParent();
        assertEquals(TokenTypes.CLASS_DEF, parent.getTokenType(), "Invalid token type");
    }

    @Test
    public void testRootOfElementNode() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertEquals(1, nodes.size(), "Invalid number of nodes");
        final AbstractNode root = (AbstractNode) nodes.get(0).getRoot();
        assertEquals(TokenTypes.EOF, root.getTokenType(), "Invalid token type");
        assertTrue(root instanceof RootNode,
                "Should return true, because selected node is RootNode");
    }

    @Test
    public void testGetNodeByValueNumInt() throws Exception {
        final String xPath = "//NUM_INT[@text = 123]";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals(1, nodes.size(), "Invalid number of nodes");
        final int tokenType = ((AbstractNode) nodes.get(0)).getTokenType();
        assertEquals(TokenTypes.NUM_INT, tokenType, "Invalid token type");
    }

    @Test
    public void testGetNodeByValueStringLiteral() throws Exception {
        final String xPath = "//STRING_LITERAL[@text = 'HelloWorld']";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals(2, nodes.size(), "Invalid number of nodes");
        final int tokenType = ((AbstractNode) nodes.get(0)).getTokenType();
        assertEquals(TokenTypes.STRING_LITERAL, tokenType, "Invalid token type");
    }

    @Test
    public void testGetNodeByValueWithSameTokenText() throws Exception {
        final String xPath = "//MODIFIERS[@text = 'MODIFIERS']";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals(0, nodes.size(), "Invalid number of nodes");
    }

    @Test
    public void testGetAttributeValue() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.IDENT);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);

        assertEquals("HelloWorld", elementNode.getAttributeValue(null, "text"),
                "Invalid text attribute");
    }

    @Test
    public void testGetAttributeCached() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.IDENT);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);
        try (AxisIterator first = elementNode.iterateAxis(AxisInfo.ATTRIBUTE);
             AxisIterator second = elementNode.iterateAxis(AxisInfo.ATTRIBUTE)) {
            assertSame(first.next(), second.next(), "Expected same attribute node");
        }
    }

    @Test
    public void testGetAttributeValueNoAttribute() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.CLASS_DEF);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);

        assertNull(elementNode.getAttributeValue(null, "text"), "Must be null");
    }

    @Test
    public void testGetAttributeValueWrongAttribute() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.IDENT);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);

        assertNull(elementNode.getAttributeValue(null, "somename"), "Must be null");
    }

    @Test
    public void testIterateAxisEmptyChildren() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.METHOD_DEF);
        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.CHILD)) {
            assertTrue(iterator instanceof EmptyIterator, "Invalid iterator");
        }
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertTrue(iterator instanceof EmptyIterator, "Invalid iterator");
        }
    }

    @Test
    public void testIterateAxisWithChildren() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.METHOD_DEF);
        final DetailAstImpl childAst = new DetailAstImpl();
        childAst.setType(TokenTypes.VARIABLE_DEF);
        detailAST.addChild(childAst);
        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST, 1, 0);
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.CHILD)) {
            assertTrue(iterator instanceof ArrayIterator, "Invalid iterator");
        }
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertTrue(iterator instanceof Navigator.DescendantEnumeration, "Invalid iterator");
        }
    }

    @Test
    public void testIterateAxisWithNoSiblings() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.VARIABLE_DEF);

        final DetailAstImpl parentAST = new DetailAstImpl();
        parentAST.setFirstChild(detailAST);
        parentAST.setType(TokenTypes.METHOD_DEF);
        final AbstractNode parentNode = new ElementNode(rootNode, rootNode, parentAST, 1, 0);

        final AbstractNode elementNode = parentNode.getChildren().get(0);
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.FOLLOWING_SIBLING)) {
            assertTrue(iterator instanceof EmptyIterator, "Invalid iterator");
        }
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.PRECEDING_SIBLING)) {
            assertTrue(iterator instanceof EmptyIterator, "Invalid iterator");
        }
    }
}

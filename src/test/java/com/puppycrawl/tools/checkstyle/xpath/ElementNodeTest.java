////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void init() throws Exception {
        final File file = new File(getPath("InputXpathMapperAst.java"));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        rootNode = new RootNode(rootAst);
    }

    @Test
    public void testGetParent() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        final AbstractNode parent = (AbstractNode) nodes.get(0).getParent();
        assertEquals("Invalid token type", TokenTypes.CLASS_DEF, parent.getTokenType());
    }

    @Test
    public void testRootOfElementNode() throws Exception {
        final String xpath = "//OBJBLOCK";
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        final AbstractNode root = (AbstractNode) nodes.get(0).getRoot();
        assertEquals("Invalid token type", TokenTypes.EOF, root.getTokenType());
        assertTrue("Should return true, because selected node is RootNode",
                root instanceof RootNode);
    }

    @Test
    public void testGetNodeByValueNumInt() throws Exception {
        final String xPath = "//NUM_INT[@text = 123]";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        assertEquals("Invalid token type", TokenTypes.NUM_INT,
                ((AbstractNode) nodes.get(0)).getTokenType());
    }

    @Test
    public void testGetNodeByValueStringLiteral() throws Exception {
        final String xPath = "//STRING_LITERAL[@text = 'HelloWorld']";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals("Invalid number of nodes", 2, nodes.size());
        assertEquals("Invalid token type", TokenTypes.STRING_LITERAL,
                ((AbstractNode) nodes.get(0)).getTokenType());
    }

    @Test
    public void testGetNodeByValueWithSameTokenText() throws Exception {
        final String xPath = "//MODIFIERS[@text = 'MODIFIERS']";
        final List<NodeInfo> nodes = getXpathItems(xPath, rootNode);
        assertEquals("Invalid number of nodes", 0, nodes.size());
    }

    @Test
    public void testGetAttributeValue() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.IDENT);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST);

        assertEquals("Invalid text attribute", "HelloWorld",
                elementNode.getAttributeValue(null, "text"));
    }

    @Test
    public void testGetAttributeValueNoAttribute() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.CLASS_DEF);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST);

        assertNull("Must be null", elementNode.getAttributeValue(null, "text"));
    }

    @Test
    public void testGetAttributeValueWrongAttribute() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.IDENT);
        detailAST.setText("HelloWorld");

        final ElementNode elementNode = new ElementNode(rootNode, rootNode, detailAST);

        assertNull("Must be null", elementNode.getAttributeValue(null, "somename"));
    }

    @Test
    public void testIterateAxisEmptyChildren() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.METHOD_DEF);
        final ElementNode elementNode = new ElementNode(rootNode, null, detailAST);
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.CHILD)) {
            assertTrue("Invalid iterator", iterator instanceof EmptyIterator);
        }
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertTrue("Invalid iterator", iterator instanceof EmptyIterator);
        }
    }

    @Test
    public void testIterateAxisWithChildren() {
        final DetailAstImpl detailAST = new DetailAstImpl();
        detailAST.setType(TokenTypes.METHOD_DEF);
        final DetailAstImpl childAst = new DetailAstImpl();
        childAst.setType(TokenTypes.VARIABLE_DEF);
        detailAST.addChild(childAst);
        final ElementNode elementNode = new ElementNode(rootNode, null, detailAST);
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.CHILD)) {
            assertTrue("Invalid iterator", iterator instanceof ArrayIterator);
        }
        try (AxisIterator iterator = elementNode.iterateAxis(AxisInfo.DESCENDANT)) {
            assertTrue("Invalid iterator", iterator instanceof Navigator.DescendantEnumeration);
        }
    }
}

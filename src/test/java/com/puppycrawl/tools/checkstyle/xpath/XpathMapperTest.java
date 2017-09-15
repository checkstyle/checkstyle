////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.Item;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.tree.iter.EmptyIterator;

public class XpathMapperTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @Test
    public void testFullPath() throws Exception {
        final String xpath = "/CLASS_DEF/OBJBLOCK/METHOD_DEF[1]/SLIST/VARIABLE_DEF[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testParent() throws Exception {
        final String xpath = "(//VARIABLE_DEF)[1]/..";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testCurlyBrackets() throws Exception {
        final String xpath = "(//RCURLY)[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.RCURLY);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testOr() throws Exception {
        final String xpath = "//CLASS_DEF | //METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode1 = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST expectedMethodDefNode2 = expectedMethodDefNode1.getNextSibling();
        final DetailAST[] expected = {expectedClassDefNode, expectedMethodDefNode1,
            expectedMethodDefNode2};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testAttributeOr() throws Exception {
        final String xpath = "//METHOD_DEF[@text='getSomeMethod' or @text='nonExistingMethod']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testAttributeAnd() throws Exception {
        final String xpath = "//METHOD_DEF[@text='callSomeMethod' and "
                + "../..[@text='InputXpathMapperAst']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryAllElementsWithAttribute() throws Exception {
        final String xpath = "//*[@text]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 18, nodes.size());
    }

    @Test
    public void testQueryElementByIndex() throws Exception {
        final String xpath = "(//VARIABLE_DEF)[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertEquals("Invalid number of nodes", 1, actual.length);
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryAllVariableDefinitionsWithAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[@*]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 4, nodes.size());
    }

    @Test
    public void testQueryAllVariableDefWrongAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[@qwe]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 0, nodes.size());
    }

    @Test
    public void testQueryAllMethodDefinitionsInContext() throws Exception {
        final String objectXpath = "/CLASS_DEF[@text='InputXpathMapperAst']//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> objectNodes = getXpathItems(objectXpath, rootNode);
        assertEquals("Invalid number of nodes", 1, objectNodes.size());
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "METHOD_DEF";
        final List<Item> methodsNodes = getXpathItems(methodsXpath, objNode);
        assertEquals("Invalid number of nodes", 2, methodsNodes.size());
        final DetailAST[] actual = convertToArray(methodsNodes);
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryAllClassDefinitions() throws Exception {
        final String xpath = "CLASS_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
        final AbstractNode classDefNode = (AbstractNode) nodes.get(0);
        assertEquals("Invalid number of nodes", 3, classDefNode.getLineNumber());
        assertEquals("Invalid number of nodes", 0, classDefNode.getColumnNumber());
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryByMethodName() throws Exception {
        final String xpath = "//METHOD_DEF[@text='getSomeMethod']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryMethodDefinitionsByClassName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputXpathMapperAst']//OBJBLOCK//METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryByClassNameAndMethodName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputXpathMapperAst']//OBJBLOCK"
                + "//METHOD_DEF[@text='getSomeMethod']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryClassDefinitionByClassName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputXpathMapperAst']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        assertEquals("Invalid number of nodes", "CLASS_DEF", classDefNode.getStringValue());
        assertEquals("Invalid number of nodes", "InputXpathMapperAst",
                classDefNode.getAttributeValue("", "text"));
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryWrongClassName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='WrongName']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertTrue("Should return true, because no item matches xpath", nodes.isEmpty());
    }

    @Test
    public void testQueryWrongXpath() throws Exception {
        final String xpath = "/WRONG_XPATH";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertTrue("Should return true, because no item matches xpath", nodes.isEmpty());
    }

    @Test
    public void testQueryAncestor() throws Exception {
        final String xpath = "//VARIABLE_DEF[@text='another']/ancestor::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryAncestorOrSelf() throws Exception {
        final String xpath = "//VARIABLE_DEF[@text='another']/ancestor-or-self::VARIABLE_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryDescendant() throws Exception {
        final String xpath = "//METHOD_DEF/descendant::EXPR";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 6, nodes.size());
    }

    @Test
    public void testQueryDescendantOrSelf() throws Exception {
        final String xpath = "//METHOD_DEF/descendant-or-self::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryNoChild() throws Exception {
        final String xpath = "//RCURLY/METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertTrue("Should return true, because no item matches xpath", nodes.isEmpty());
    }

    @Test
    public void testQueryNoDescendant() throws Exception {
        final String xpath = "//RCURLY/descendant::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertTrue("Should return true, because no item matches xpath", nodes.isEmpty());
    }

    @Test
    public void testQueryRootNotImplementedAxis() throws Exception {
        final String xpath = "//following-sibling::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            fail("Exception is excepted");

        }
        catch (XPathException ex) {
            assertEquals("Invalid number of nodes", "Operation is not supported", ex.getMessage());
        }
    }

    @Test
    public void testQueryElementNotImplementedAxis() throws Exception {
        final String xpath = "/CLASS_DEF//following-sibling::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            fail("Exception is excepted");
        }
        catch (XPathException ex) {
            assertEquals("Invalid number of nodes", "Operation is not supported", ex.getMessage());
        }
    }

    @Test
    public void testQuerySelf() throws Exception {
        final String objectXpath = "/CLASS_DEF[@text='InputXpathMapperAst']//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> objectNodes = getXpathItems(objectXpath, rootNode);
        assertEquals("Invalid number of nodes", 1, objectNodes.size());
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "self::OBJBLOCK";
        final DetailAST[] actual = convertToArray(getXpathItems(methodsXpath, objNode));
        final DetailAST expectedObjBlockNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST[] expected = {expectedObjBlockNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testRootWithNullDetailAst() {
        final RootNode emptyRootNode = new RootNode(null);
        assertFalse("Empty node should not have children", emptyRootNode.hasChildNodes());
        assertEquals("Invalid number of nodes", EmptyIterator.OfNodes.THE_INSTANCE,
                emptyRootNode.iterateAxis(
                AxisInfo.DESCENDANT));
        assertEquals("Invalid number of nodes", EmptyIterator.OfNodes.THE_INSTANCE,
                emptyRootNode.iterateAxis(AxisInfo.CHILD));
    }

    @Test
    public void testQueryNonExistingAttribute() throws Exception {
        final String xpath = "/CLASS_DEF[@text='InputXpathMapperAst']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        try {
            classDefNode.getAttributeValue("", "noneExistingAttribute");
            fail("Exception is excepted");

        }
        catch (UnsupportedOperationException ex) {
            assertEquals("Invalid number of nodes", "Operation is not supported", ex.getMessage());
        }
    }

    @Test
    public void testQueryRootSelf() throws Exception {
        final String xpath = "self::node()";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 1, nodes.size());
    }

    @Test
    public void testQueryAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[@text='SuppressWarnings']";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAnnotationNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.MODIFIERS)
                .findFirstToken(TokenTypes.ANNOTATION);
        final DetailAST[] expected = {expectedAnnotationNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryNonExistingAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[@text='SpringBootApplication']";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final List<Item> nodes = getXpathItems(xpath, rootNode);
        assertEquals("Invalid number of nodes", 0, nodes.size());
    }

    @Test
    public void testQueryEnumDef() throws Exception {
        final String xpath = "/ENUM_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, enumRootNode));
        final DetailAST expectedEnumDefNode = getSiblingByType(enumRootNode.getUnderlyingNode(),
                TokenTypes.ENUM_DEF);
        final DetailAST[] expected = {expectedEnumDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryEnumElementsNumber() throws Exception {
        final String xpath = "/ENUM_DEF/OBJBLOCK/ENUM_CONSTANT_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final List<Item> nodes = getXpathItems(xpath, enumRootNode);
        assertEquals("Invalid number of nodes", 3, nodes.size());
    }

    @Test
    public void testQueryEnumElementByName() throws Exception {
        final String xpath = "//*[@text='TWO']";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, enumRootNode));
        final DetailAST expectedEnumConstantDefNode = getSiblingByType(
                enumRootNode.getUnderlyingNode(),
                TokenTypes.ENUM_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.ENUM_CONSTANT_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedEnumConstantDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryInterfaceDef() throws Exception {
        final String xpath = "/INTERFACE_DEF";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, interfaceRootNode));
        final DetailAST expectedInterfaceDefNode = getSiblingByType(
                interfaceRootNode.getUnderlyingNode(),
                TokenTypes.INTERFACE_DEF);
        final DetailAST[] expected = {expectedInterfaceDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    @Test
    public void testQueryInterfaceMethodDefNumber() throws Exception {
        final String xpath = "/INTERFACE_DEF/OBJBLOCK/METHOD_DEF";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final List<Item> nodes = getXpathItems(xpath, interfaceRootNode);
        assertEquals("Invalid number of nodes", 4, nodes.size());
    }

    @Test
    public void testQueryInterfaceParameterDef() throws Exception {
        final String xpath = "//PARAMETER_DEF[@text='someVariable']/../..";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, interfaceRootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(
                interfaceRootNode.getUnderlyingNode(),
                TokenTypes.INTERFACE_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertArrayEquals("Result nodes differ from expected", expected, actual);
    }

    private RootNode getRootNode(String fileName) throws Exception {
        final File file = new File(getPath(fileName));
        final DetailAST rootAst = TestUtil.parseFile(file);
        return new RootNode(rootAst);
    }

    private static DetailAST[] convertToArray(List<Item> nodes) {
        final DetailAST[] result = new DetailAST[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            final AbstractNode abstractNode = (AbstractNode) nodes.get(i);
            result[i] = abstractNode.getUnderlyingNode();
        }
        return result;
    }

    private static DetailAST getSiblingByType(DetailAST node, int type) {
        DetailAST returnValue = null;
        for (DetailAST ast = node; ast != null; ast = ast.getNextSibling()) {
            if (ast.getType() == type) {
                returnValue = ast;
                break;
            }
        }
        return returnValue;
    }
}

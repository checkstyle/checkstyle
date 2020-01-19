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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.puppycrawl.tools.checkstyle.internal.utils.XpathUtil.getXpathItems;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.om.AxisInfo;
import net.sf.saxon.om.NodeInfo;
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testCurlyBrackets() throws Exception {
        final String xpath = "(//RCURLY)[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedCurlyNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.RCURLY);
        final DetailAST[] expected = {expectedCurlyNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testComplexQueryOne() throws Exception {
        final String xpath = "/CLASS_DEF | /CLASS_DEF/OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedObjblockNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST[] expected = {expectedClassDefNode, expectedObjblockNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testComplexQueryTwo() throws Exception {
        final String xpath = "/PACKAGE_DEF | /PACKAGE_DEF/ANNOTATIONS";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedPackageDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.PACKAGE_DEF);
        final DetailAST expectedAnnotationsNode = expectedPackageDefNode
                .findFirstToken(TokenTypes.ANNOTATIONS);
        final DetailAST[] expected = {expectedAnnotationsNode, expectedPackageDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testComplexQueryThree() throws Exception {
        final String xpath = "//CLASS_DEF | //CLASS_DEF//METHOD_DEF | /CLASS_DEF/OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedObjblockNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST expectedMethodDefNode = expectedObjblockNode
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST expectedMethodDefNode2 = expectedObjblockNode
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedClassDefNode, expectedMethodDefNode,
            expectedMethodDefNode2, expectedObjblockNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testAttributeOr() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='getSomeMethod'] "
                + "or ./IDENT[@text='nonExistentMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testAttributeAnd() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='callSomeMethod'] and "
                + "../..[./IDENT[@text='InputXpathMapperAst']]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryAllElementsWithAttribute() throws Exception {
        final String xpath = "//*[./IDENT[@text]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(18));
    }

    @Test
    public void testQueryElementByIndex() throws Exception {
        final String xpath = "(//VARIABLE_DEF)[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertThat("Invalid number of nodes", actual.length, equalTo(1));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryAllVariableDefinitionsWithAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@*]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(4));
    }

    @Test
    public void testQueryAllVariableDefWrongAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[@qwe]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(0));
    }

    @Test
    public void testQueryAllMethodDefinitionsInContext() throws Exception {
        final String objectXpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> objectNodes = getXpathItems(objectXpath, rootNode);
        assertThat("Invalid number of nodes", objectNodes.size(), equalTo(1));
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "METHOD_DEF";
        final List<NodeInfo> methodsNodes = getXpathItems(methodsXpath, objNode);
        assertThat("Invalid number of nodes", methodsNodes.size(), equalTo(2));
        final DetailAST[] actual = convertToArray(methodsNodes);
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
        assertThat("Invalid token type", actual[0].getType(), equalTo(TokenTypes.METHOD_DEF));
        assertThat("Invalid token type", actual[1].getType(), equalTo(TokenTypes.METHOD_DEF));
    }

    @Test
    public void testQueryAllClassDefinitions() throws Exception {
        final String xpath = "CLASS_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(1));
        final AbstractNode classDefNode = (AbstractNode) nodes.get(0);
        assertThat("Invalid line number", classDefNode.getLineNumber(), equalTo(3));
        assertThat("Invalid column number", classDefNode.getColumnNumber(), equalTo(0));
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryByMethodName() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='getSomeMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryMethodDefinitionsByClassName() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]"
                + "//OBJBLOCK//METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
        assertThat("Invalid token type", actual[0].getType(), equalTo(TokenTypes.METHOD_DEF));
        assertThat("Invalid token type", actual[1].getType(), equalTo(TokenTypes.METHOD_DEF));
    }

    @Test
    public void testQueryByClassNameAndMethodName() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK"
                + "//METHOD_DEF[./IDENT[@text='getSomeMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryClassDefinitionByClassName() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        assertThat("Invalid node name", classDefNode.getLocalPart(), equalTo("CLASS_DEF"));
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryWrongClassName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='WrongName']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Should return true, because no item matches xpath", nodes.isEmpty(),
                equalTo(true));
    }

    @Test
    public void testQueryWrongXpath() throws Exception {
        final String xpath = "/WRONG_XPATH";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Should return true, because no item matches xpath", nodes.isEmpty(),
                equalTo(true));
    }

    @Test
    public void testQueryAncestor() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='another']]/ancestor::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryAncestorOrSelf() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ancestor-or-self::VARIABLE_DEF";
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryDescendant() throws Exception {
        final String xpath = "//METHOD_DEF/descendant::EXPR";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(6));
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
        assertThat("Invalid token type", actual[0].getType(), equalTo(TokenTypes.METHOD_DEF));
        assertThat("Invalid token type", actual[1].getType(), equalTo(TokenTypes.METHOD_DEF));
    }

    @Test
    public void testQueryNoChild() throws Exception {
        final String xpath = "//RCURLY/METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Should return true, because no item matches xpath", nodes.isEmpty(),
                equalTo(true));
    }

    @Test
    public void testQueryNoDescendant() throws Exception {
        final String xpath = "//RCURLY/descendant::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Should return true, because no item matches xpath", nodes.isEmpty(),
                equalTo(true));
    }

    @Test
    public void testQueryRootNotImplementedAxis() throws Exception {
        final String xpath = "//namespace::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertThat("Invalid exception", ex.getMessage(), equalTo("Operation is not supported"));
        }
    }

    @Test
    public void testQueryElementNotImplementedAxis() throws Exception {
        final String xpath = "/CLASS_DEF//namespace::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            fail("Exception is excepted");
        }
        catch (UnsupportedOperationException ex) {
            assertThat("Invalid exception", ex.getMessage(), equalTo("Operation is not supported"));
        }
    }

    @Test
    public void testQuerySelf() throws Exception {
        final String objectXpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> objectNodes = getXpathItems(objectXpath, rootNode);
        assertThat("Invalid number of nodes", objectNodes.size(), equalTo(1));
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "self::OBJBLOCK";
        final DetailAST[] actual = convertToArray(getXpathItems(methodsXpath, objNode));
        final DetailAST expectedObjBlockNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST[] expected = {expectedObjBlockNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testRootWithNullDetailAst() {
        final RootNode emptyRootNode = new RootNode(null);
        assertThat("Empty node should not have children", emptyRootNode.hasChildNodes(),
                equalTo(false));
        assertThat("Invalid number of nodes", emptyRootNode.iterateAxis(AxisInfo.DESCENDANT),
                equalTo(EmptyIterator.OfNodes.THE_INSTANCE));
        assertThat("Invalid number of nodes", emptyRootNode.iterateAxis(AxisInfo.CHILD),
                equalTo(EmptyIterator.OfNodes.THE_INSTANCE));
    }

    @Test
    public void testQueryNonExistentAttribute() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        assertThat("Not existing attribute should have null value",
                classDefNode.getAttributeValue("", "noneExistingAttribute"), nullValue());
    }

    @Test
    public void testQueryRootSelf() throws Exception {
        final String xpath = "self::node()";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(1));
    }

    @Test
    public void testQueryAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[./IDENT[@text='Deprecated']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAnnotationNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.MODIFIERS)
                .findFirstToken(TokenTypes.ANNOTATION);
        final DetailAST[] expected = {expectedAnnotationNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryNonExistentAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[@text='SpringBootApplication']";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(0));
    }

    @Test
    public void testQueryEnumDef() throws Exception {
        final String xpath = "/ENUM_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, enumRootNode));
        final DetailAST expectedEnumDefNode = getSiblingByType(enumRootNode.getUnderlyingNode(),
                TokenTypes.ENUM_DEF);
        final DetailAST[] expected = {expectedEnumDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryEnumElementsNumber() throws Exception {
        final String xpath = "/ENUM_DEF/OBJBLOCK/ENUM_CONSTANT_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, enumRootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(3));
    }

    @Test
    public void testQueryEnumElementByName() throws Exception {
        final String xpath = "//*[./IDENT[@text='TWO']]";
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryInterfaceMethodDefNumber() throws Exception {
        final String xpath = "/INTERFACE_DEF/OBJBLOCK/METHOD_DEF";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, interfaceRootNode);
        assertThat("Invalid number of nodes", nodes.size(), equalTo(4));
    }

    @Test
    public void testQueryInterfaceParameterDef() throws Exception {
        final String xpath = "//PARAMETER_DEF[./IDENT[@text='someVariable']]/../..";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, interfaceRootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(
                interfaceRootNode.getUnderlyingNode(),
                TokenTypes.INTERFACE_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testIdent() throws Exception {
        final String xpath = "/CLASS_DEF/IDENT[@text='InputXpathMapperAst']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedIdentNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.IDENT);

        final DetailAST[] expected = {expectedIdentNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testIdentByText() throws Exception {
        final String xpath = "//IDENT[@text='puppycrawl']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.PACKAGE_DEF)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.IDENT)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testNumVariableByItsValue() throws Exception {
        final String xpath = "//VARIABLE_DEF[.//NUM_INT[@text=123]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testStringVariableByItsValue() throws Exception {
        final String xpath = "//VARIABLE_DEF[./ASSIGN/EXPR"
                + "/STRING_LITERAL[@text='HelloWorld']]";
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
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testSameNodesByNameAndByText() throws Exception {
        final String xpath1 = "//VARIABLE_DEF[./IDENT[@text='another']]/ASSIGN/EXPR/STRING_LITERAL";
        final String xpath2 = "//VARIABLE_DEF/ASSIGN/EXPR/STRING_LITERAL[@text='HelloWorld']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual1 = convertToArray(getXpathItems(xpath1, rootNode));
        final DetailAST[] actual2 = convertToArray(getXpathItems(xpath2, rootNode));
        assertThat("Result nodes differ from expected", actual2, equalTo(actual1));
    }

    @Test
    public void testMethodDefByAnnotationValue() throws Exception {
        final String xpath = "//METHOD_DEF[.//ANNOTATION[./IDENT[@text='SuppressWarnings']"
                + " and .//*[@text='good']]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAnnotationNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedAnnotationNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testFirstImport() throws Exception {
        final String xpath = "/IMPORT[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.IMPORT);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testSecondImport() throws Exception {
        final String xpath = "/IMPORT[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.IMPORT).getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testThirdImport() throws Exception {
        final String xpath = "/IMPORT[3]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.IMPORT).getNextSibling().getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testLastImport() throws Exception {
        final String xpath = "/IMPORT[9]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.IMPORT)
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testFirstCaseGroup() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testSecondCaseGroup() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testThirdCaseGroup() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[3]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testFourthCaseGroup() throws Exception {
        final String xpath = "/CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[4]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling()
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementFollowingSibling() throws Exception {
        final String xpath = "//METHOD_DEF/following-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode,
                expectedMethodDefNode.getNextSibling()};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
        assertThat("Invalid token type", actual[0].getType(), equalTo(TokenTypes.METHOD_DEF));
        assertThat("Invalid token type", actual[1].getType(), equalTo(TokenTypes.RCURLY));
    }

    @Test
    public void testQueryElementNoFollowingSibling() throws Exception {
        final String xpath = "//CLASS_DEF/following-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertThat("Invalid number of nodes", actual.length, equalTo(0));
    }

    @Test
    public void testQueryElementFollowingSiblingRcurly() throws Exception {
        final String xpath = "//METHOD_DEF/following-sibling::RCURLY";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedRightCurlyNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling().getNextSibling();
        final DetailAST[] expected = {expectedRightCurlyNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementFollowing() throws Exception {
        final String xpath = "//IDENT[@text='variable']/following::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAssignNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .findFirstToken(TokenTypes.MODIFIERS)
                .getNextSibling()
                .getNextSibling()
                .getNextSibling();
        final DetailAST expectedExprNode = expectedAssignNode.getFirstChild();
        final DetailAST expectedNumIntNode = expectedExprNode.getFirstChild();
        final DetailAST[] expected = {expectedAssignNode, expectedExprNode, expectedNumIntNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementFollowingMethodDef() throws Exception {
        final String xpath = "//PACKAGE_DEF/following::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode.getNextSibling(),
            expectedMethodDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
        assertThat("Invalid token type", actual[0].getType(), equalTo(TokenTypes.METHOD_DEF));
        assertThat("Invalid token type", actual[1].getType(), equalTo(TokenTypes.METHOD_DEF));
    }

    @Test
    public void testQueryElementNoFollowing() throws Exception {
        final String xpath = "//CLASS_DEF/following::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertThat("Invalid number of nodes", actual.length, equalTo(0));
    }

    @Test
    public void testQueryElementPrecedingSibling() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode1 = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST expectedSemiNode1 = expectedVariableDefNode1.getNextSibling();
        final DetailAST expectedVariableDefNode2 = expectedSemiNode1.getNextSibling();
        final DetailAST expectedSemiNode2 = expectedVariableDefNode2.getNextSibling();
        final DetailAST[] expected = {expectedSemiNode2, expectedSemiNode1,
            expectedVariableDefNode2, expectedVariableDefNode1};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementPrecedingSiblingVariableDef() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::"
                + "VARIABLE_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode1 = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST expectedVariableDefNode2 = expectedVariableDefNode1.getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode2, expectedVariableDefNode1};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementPrecedingSiblingArray() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::*[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementPrecedingOne() throws Exception {
        final String xpath = "//LITERAL_CLASS/preceding::*";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertThat("Invalid number of nodes", actual.length, equalTo(17));
    }

    @Test
    public void testQueryElementPrecedingTwo() throws Exception {
        final String xpath = "/PACKAGE_DEF/DOT/preceding::*";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedPackageDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.PACKAGE_DEF);
        final DetailAST expectedAnnotationsNode = expectedPackageDefNode.getFirstChild();
        final DetailAST[] expected = {expectedAnnotationsNode, expectedPackageDefNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    @Test
    public void testQueryElementPrecedingLiteralPublic() throws Exception {
        final String xpath = "//LITERAL_CLASS/preceding::LITERAL_PUBLIC";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedLiteralPublicNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.CLASS_DEF).getFirstChild().getFirstChild();
        final DetailAST[] expected = {expectedLiteralPublicNode};
        assertThat("Result nodes differ from expected", actual, equalTo(expected));
    }

    private RootNode getRootNode(String fileName) throws Exception {
        final File file = new File(getPath(fileName));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        return new RootNode(rootAst);
    }

    private static DetailAST[] convertToArray(List<NodeInfo> nodes) {
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

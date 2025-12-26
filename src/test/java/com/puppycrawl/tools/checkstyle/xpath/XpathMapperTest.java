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

package com.puppycrawl.tools.checkstyle.xpath;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.utils.XpathUtil.getXpathItems;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import net.sf.saxon.om.NodeInfo;

public class XpathMapperTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/xpath/xpathmapper";
    }

    @Test
    void nodeOrdering() throws Exception {
        final String xpath = "//METHOD_DEF/SLIST/*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        for (int i = 1; i < nodes.size(); i++) {
            final NodeInfo curr = nodes.get(i);
            final NodeInfo prev = nodes.get(i - 1);

            if (curr.getLineNumber() == prev.getLineNumber()) {
                assertWithMessage("Column number is not in document order")
                    .that(curr.getColumnNumber())
                    .isGreaterThan(prev.getColumnNumber());
            }
            else {
                assertWithMessage("Line number is not in document order")
                    .that(curr.getLineNumber())
                    .isGreaterThan(prev.getLineNumber());
            }
        }
    }

    @Test
    void fullPath() throws Exception {
        final String xpath = "/COMPILATION_UNIT/CLASS_DEF/OBJBLOCK"
                + "/METHOD_DEF[1]/SLIST/VARIABLE_DEF[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void parent() throws Exception {
        final String xpath = "(//VARIABLE_DEF)[1]/..";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void curlyBrackets() throws Exception {
        final String xpath = "(//RCURLY)[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedCurlyNode = getSiblingByType(rootNode.getUnderlyingNode(),
                        TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.RCURLY);
        final DetailAST[] expected = {expectedCurlyNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void or() throws Exception {
        final String xpath = "//CLASS_DEF | //METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode1 = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST expectedMethodDefNode2 = expectedMethodDefNode1.getNextSibling();
        final DetailAST[] expected = {expectedClassDefNode, expectedMethodDefNode1,
            expectedMethodDefNode2};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void complexQueryOne() throws Exception {
        final String xpath = "//CLASS_DEF | //CLASS_DEF/OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST expectedObjblockNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST[] expected = {expectedClassDefNode, expectedObjblockNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void complexQueryTwo() throws Exception {
        final String xpath = "//PACKAGE_DEF | //PACKAGE_DEF/ANNOTATIONS";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedPackageDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.PACKAGE_DEF);
        final DetailAST expectedAnnotationsNode = expectedPackageDefNode
                .findFirstToken(TokenTypes.ANNOTATIONS);
        final DetailAST[] expected = {expectedPackageDefNode, expectedAnnotationsNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void complexQueryThree() throws Exception {
        final String xpath = "//CLASS_DEF | //CLASS_DEF//METHOD_DEF |"
                + " /COMPILATION_UNIT/CLASS_DEF/OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST expectedObjblockNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST expectedMethodDefNode = expectedObjblockNode
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST expectedMethodDefNode2 = expectedObjblockNode
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedClassDefNode, expectedObjblockNode,
            expectedMethodDefNode, expectedMethodDefNode2};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void attributeOr() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='getSomeMethod'] "
                + "or ./IDENT[@text='nonExistentMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void attributeAnd() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='callSomeMethod'] and "
                + "../..[./IDENT[@text='InputXpathMapperAst']]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST expectedMethodDefNode = expectedClassDefNode
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryAllElementsWithAttribute() throws Exception {
        final String xpath = "//*[./IDENT[@text]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(18);
    }

    @Test
    void queryElementByIndex() throws Exception {
        final String xpath = "(//VARIABLE_DEF)[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertWithMessage("Invalid number of nodes")
                .that(actual)
                .hasLength(1);
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryAllVariableDefinitionsWithAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@*]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(4);
    }

    @Test
    void queryAllVariableDefWrongAttribute() throws Exception {
        final String xpath = "//VARIABLE_DEF[@qwe]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(0);
    }

    @Test
    void queryAllMethodDefinitionsInContext() throws Exception {
        final String objectXpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> objectNodes = getXpathItems(objectXpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(objectNodes)
                .hasSize(1);
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "METHOD_DEF";
        final List<NodeInfo> methodsNodes = getXpathItems(methodsXpath, objNode);
        assertWithMessage("Invalid number of nodes")
                .that(methodsNodes)
                .hasSize(2);
        final DetailAST[] actual = convertToArray(methodsNodes);
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
        assertWithMessage("Invalid token type")
                .that(actual[0].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
        assertWithMessage("Invalid token type")
                .that(actual[1].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
    }

    @Test
    void queryAllClassDefinitions() throws Exception {
        final String xpath = "/COMPILATION_UNIT/CLASS_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(1);
        final AbstractNode classDefNode = (AbstractNode) nodes.get(0);
        assertWithMessage("Invalid line number")
                .that(classDefNode.getLineNumber())
                .isEqualTo(3);
        assertWithMessage("Invalid column number")
                .that(classDefNode.getColumnNumber())
                .isEqualTo(0);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryByMethodName() throws Exception {
        final String xpath = "//METHOD_DEF[./IDENT[@text='getSomeMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryMethodDefinitionsByClassName() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]"
                + "//OBJBLOCK//METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
        assertWithMessage("Invalid token type")
                .that(actual[0].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
        assertWithMessage("Invalid token type")
                .that(actual[1].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
    }

    @Test
    void queryByClassNameAndMethodName() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK"
                + "//METHOD_DEF[./IDENT[@text='getSomeMethod']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryClassDefinitionByClassName() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedClassDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF);
        final DetailAST[] expected = {expectedClassDefNode};
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        assertWithMessage("Invalid node name")
                .that(classDefNode.getLocalPart())
                .isEqualTo("CLASS_DEF");
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryWrongClassName() throws Exception {
        final String xpath = "/CLASS_DEF[@text='WrongName']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Should return true, because no item matches xpath")
                .that(nodes)
                .isEmpty();
    }

    @Test
    void queryWrongXpath() throws Exception {
        final String xpath = "/WRONG_XPATH";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Should return true, because no item matches xpath")
                .that(nodes)
                .isEmpty();
    }

    @Test
    void queryAncestor() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='another']]/ancestor::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryAncestorOrSelf() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='another']]"
                + "/ancestor-or-self::VARIABLE_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryDescendant() throws Exception {
        final String xpath = "//METHOD_DEF/descendant::EXPR";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(6);
    }

    @Test
    void queryDescendantOrSelf() throws Exception {
        final String xpath = "//METHOD_DEF/descendant-or-self::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
        assertWithMessage("Invalid token type")
                .that(actual[0].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
        assertWithMessage("Invalid token type")
                .that(actual[1].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
    }

    @Test
    void queryNoChild() throws Exception {
        final String xpath = "//RCURLY/METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Should return true, because no item matches xpath")
                .that(nodes)
                .isEmpty();
    }

    @Test
    void queryNoDescendant() throws Exception {
        final String xpath = "//RCURLY/descendant::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Should return true, because no item matches xpath")
                .that(nodes)
                .isEmpty();
    }

    @Test
    void queryRootNotImplementedAxis() throws Exception {
        final String xpath = "//namespace::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception")
                    .that(exc.getMessage())
                    .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void queryElementNotImplementedAxis() throws Exception {
        final String xpath = "/COMPILATION_UNIT/CLASS_DEF//namespace::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        try {
            getXpathItems(xpath, rootNode);
            assertWithMessage("Exception is excepted").fail();
        }
        catch (UnsupportedOperationException exc) {
            assertWithMessage("Invalid exception")
                    .that(exc.getMessage())
                    .isEqualTo("Operation is not supported");
        }
    }

    @Test
    void querySelf() throws Exception {
        final String objectXpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]//OBJBLOCK";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> objectNodes = getXpathItems(objectXpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(objectNodes)
                .hasSize(1);
        final AbstractNode objNode = (AbstractNode) objectNodes.get(0);
        final String methodsXpath = "self::OBJBLOCK";
        final DetailAST[] actual = convertToArray(getXpathItems(methodsXpath, objNode));
        final DetailAST expectedObjBlockNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST[] expected = {expectedObjBlockNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryNonExistentAttribute() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperAst']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final ElementNode classDefNode = (ElementNode) nodes.get(0);
        assertWithMessage("Not existing attribute should have null value")
                .that(classDefNode.getAttributeValue("", "noneExistingAttribute"))
                .isNull();
    }

    @Test
    void queryRootSelf() throws Exception {
        final String xpath = "self::node()";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(1);
    }

    @Test
    void queryAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[./IDENT[@text='Deprecated']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAnnotationNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.MODIFIERS)
                .findFirstToken(TokenTypes.ANNOTATION);
        final DetailAST[] expected = {expectedAnnotationNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryNonExistentAnnotation() throws Exception {
        final String xpath = "//ANNOTATION[@text='SpringBootApplication']";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(0);
    }

    @Test
    void queryEnumDef() throws Exception {
        final String xpath = "/COMPILATION_UNIT/ENUM_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, enumRootNode));
        final DetailAST expectedEnumDefNode = getSiblingByType(enumRootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.ENUM_DEF);
        final DetailAST[] expected = {expectedEnumDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryEnumElementsNumber() throws Exception {
        final String xpath = "/COMPILATION_UNIT/ENUM_DEF/OBJBLOCK/ENUM_CONSTANT_DEF";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, enumRootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(3);
    }

    @Test
    void queryEnumElementByName() throws Exception {
        final String xpath = "//*[./IDENT[@text='TWO']]";
        final RootNode enumRootNode = getRootNode("InputXpathMapperEnum.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, enumRootNode));
        final DetailAST expectedEnumConstantDefNode = getSiblingByType(
                enumRootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.ENUM_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.ENUM_CONSTANT_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedEnumConstantDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryInterfaceDef() throws Exception {
        final String xpath = "//INTERFACE_DEF";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, interfaceRootNode));
        final DetailAST expectedInterfaceDefNode = getSiblingByType(
                interfaceRootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.INTERFACE_DEF);
        final DetailAST[] expected = {expectedInterfaceDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryInterfaceMethodDefNumber() throws Exception {
        final String xpath = "//INTERFACE_DEF/OBJBLOCK/METHOD_DEF";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, interfaceRootNode);
        assertWithMessage("Invalid number of nodes")
                .that(nodes)
                .hasSize(4);
    }

    @Test
    void queryInterfaceParameterDef() throws Exception {
        final String xpath = "//PARAMETER_DEF[./IDENT[@text='someVariable']]/../..";
        final RootNode interfaceRootNode = getRootNode("InputXpathMapperInterface.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, interfaceRootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(
                interfaceRootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.INTERFACE_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void ident() throws Exception {
        final String xpath = "//CLASS_DEF/IDENT[@text='InputXpathMapperAst']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> nodes = getXpathItems(xpath, rootNode);
        final DetailAST[] actual = convertToArray(nodes);
        final DetailAST expectedIdentNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.IDENT);

        final DetailAST[] expected = {expectedIdentNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void identByText() throws Exception {
        final String xpath = "//IDENT[@text='puppycrawl']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.PACKAGE_DEF)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.DOT)
                .findFirstToken(TokenTypes.IDENT)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void numVariableByItsValue() throws Exception {
        final String xpath = "//VARIABLE_DEF[.//NUM_INT[@text=123]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void stringVariableByItsValue() throws Exception {
        final String xpath = "//VARIABLE_DEF[./ASSIGN/EXPR"
                + "/STRING_LITERAL[@text='HelloWorld']]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void sameNodesByNameAndByText() throws Exception {
        final String xpath1 = "//VARIABLE_DEF[./IDENT[@text='another']]/ASSIGN/EXPR/STRING_LITERAL";
        final String xpath2 = "//VARIABLE_DEF/ASSIGN/EXPR/STRING_LITERAL[@text='HelloWorld']";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual1 = convertToArray(getXpathItems(xpath1, rootNode));
        final DetailAST[] actual2 = convertToArray(getXpathItems(xpath2, rootNode));
        assertWithMessage("Result nodes differ from expected")
                .that(actual2)
                .isEqualTo(actual1);
    }

    @Test
    void methodDefByAnnotationValue() throws Exception {
        final String xpath = "//METHOD_DEF[.//ANNOTATION[./IDENT[@text='SuppressWarnings']"
                + " and .//*[@text='good']]]";
        final RootNode rootNode = getRootNode("InputXpathMapperAnnotation.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedAnnotationNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedAnnotationNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void firstImport() throws Exception {
        final String xpath = "/COMPILATION_UNIT/IMPORT[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.IMPORT);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void secondImport() throws Exception {
        final String xpath = "/COMPILATION_UNIT/IMPORT[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.IMPORT)
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void thirdImport() throws Exception {
        final String xpath = "/COMPILATION_UNIT/IMPORT[3]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.IMPORT)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void lastImport() throws Exception {
        final String xpath = "/COMPILATION_UNIT/IMPORT[9]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.IMPORT)
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void firstCaseGroup() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void secondCaseGroup() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[2]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void thirdCaseGroup() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[3]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void fourthCaseGroup() throws Exception {
        final String xpath = "//CLASS_DEF[./IDENT[@text='InputXpathMapperPositions']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='switchMethod']]"
                + "/SLIST/LITERAL_SWITCH/CASE_GROUP[4]";
        final RootNode rootNode = getRootNode("InputXpathMapperPositions.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_SWITCH)
                .findFirstToken(TokenTypes.CASE_GROUP)
                .getNextSibling()
                .getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementFollowingSibling() throws Exception {
        final String xpath = "//METHOD_DEF/following-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST[] expected = {expectedMethodDefNode,
                expectedMethodDefNode.getNextSibling()};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
        assertWithMessage("Invalid token type")
                .that(actual[0].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
        assertWithMessage("Invalid token type")
                .that(actual[1].getType())
                .isEqualTo(TokenTypes.RCURLY);
    }

    @Test
    void queryElementNoFollowingSibling() throws Exception {
        final String xpath = "//CLASS_DEF/following-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertWithMessage("Invalid number of nodes")
                .that(actual)
                .isEmpty();
    }

    @Test
    void queryElementFollowingSiblingRcurly() throws Exception {
        final String xpath = "//METHOD_DEF/following-sibling::RCURLY";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedRightCurlyNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling().getNextSibling();
        final DetailAST[] expected = {expectedRightCurlyNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementFollowing() throws Exception {
        final String xpath = "//IDENT[@text='variable']/following::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final List<NodeInfo> actual = getXpathItems(xpath, rootNode);
        assertWithMessage("Number of result nodes differ from expected")
                .that(actual.size())
                .isEqualTo(60);
    }

    @Test
    void queryElementFollowingTwo() throws Exception {
        final String xpath = "//LITERAL_RETURN[.//STRING_LITERAL[@text='HelloWorld']]/following::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .getNextSibling();
        final DetailAST expectedRcurlyNodeOne = expectedMethodDefNode.getNextSibling();
        final DetailAST expectedRcurlyNodeTwo = expectedMethodDefNode
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.LITERAL_RETURN)
                .getNextSibling();
        final DetailAST[] expected = {expectedRcurlyNodeOne, expectedRcurlyNodeTwo};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementFollowingMethodDef() throws Exception {
        final String xpath = "//PACKAGE_DEF/following::METHOD_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedMethodDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST[] expected = {expectedMethodDefNode,
            expectedMethodDefNode.getNextSibling()};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
        assertWithMessage("Invalid token type")
                .that(actual[0].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
        assertWithMessage("Invalid token type")
                .that(actual[1].getType())
                .isEqualTo(TokenTypes.METHOD_DEF);
    }

    @Test
    void queryElementNoFollowing() throws Exception {
        final String xpath = "//CLASS_DEF/following::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        assertWithMessage("Invalid number of nodes")
                .that(actual)
                .isEmpty();
    }

    @Test
    void queryElementPrecedingSibling() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::*";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode1 = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST expectedSemiNode1 = expectedVariableDefNode1.getNextSibling();
        final DetailAST expectedVariableDefNode2 = expectedSemiNode1.getNextSibling();
        final DetailAST expectedSemiNode2 = expectedVariableDefNode2.getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode1, expectedSemiNode1,
            expectedVariableDefNode2, expectedSemiNode2};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementPrecedingSiblingVariableDef() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::"
                + "VARIABLE_DEF";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode1 = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        final DetailAST expectedVariableDefNode2 = expectedVariableDefNode1.getNextSibling()
                .getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode1, expectedVariableDefNode2};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementPrecedingSiblingArray() throws Exception {
        final String xpath = "//VARIABLE_DEF[./IDENT[@text='array']]/preceding-sibling::*[1]";
        final RootNode rootNode = getRootNode("InputXpathMapperAst.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.METHOD_DEF)
                .findFirstToken(TokenTypes.SLIST)
                .findFirstToken(TokenTypes.SEMI)
                .getNextSibling().getNextSibling();
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementPrecedingOne() throws Exception {
        final String xpath = "//LITERAL_CLASS/preceding::*";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath,
                rootNode.createChildren().get(0)));
        assertWithMessage("Invalid number of nodes")
                .that(actual)
                .hasLength(18);
    }

    @Test
    void queryElementPrecedingTwo() throws Exception {
        final String xpath = "//PACKAGE_DEF/DOT/preceding::*";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedPackageDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.PACKAGE_DEF);
        final DetailAST expectedAnnotationsNode = expectedPackageDefNode.getFirstChild();
        final DetailAST[] expected = {
            rootNode.getUnderlyingNode(),
            expectedPackageDefNode,
            expectedAnnotationsNode,
        };
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void queryElementPrecedingLiteralPublic() throws Exception {
        final String xpath = "//LITERAL_CLASS/preceding::LITERAL_PUBLIC";
        final RootNode rootNode = getRootNode("InputXpathMapperSingleTopClass.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedLiteralPublicNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .getFirstChild()
                .getFirstChild();
        final DetailAST[] expected = {expectedLiteralPublicNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void textBlockByItsValue() throws Exception {
        final String xpath = "//TEXT_BLOCK_LITERAL_BEGIN[./TEXT_BLOCK_CONTENT"
                + "[@text='\\n        &1line\\n        >2line\\n        <3line\\n        ']]";
        final RootNode rootNode = getRootNode("InputXpathMapperTextBlock.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.VARIABLE_DEF)
                .findFirstToken(TokenTypes.ASSIGN)
                .findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.TEXT_BLOCK_LITERAL_BEGIN);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void querySingleLineCommentByCommentContent() throws Exception {
        final String xpath = "//SINGLE_LINE_COMMENT[./COMMENT_CONTENT[@text=' some comment\\n']]";
        final RootNode rootNode = getRootNodeWithComments("InputXpathMapperSingleLineComment.java");
        final DetailAST[] actual = convertToArray(getXpathItems(xpath, rootNode));
        final DetailAST expectedVariableDefNode = getSiblingByType(rootNode.getUnderlyingNode(),
                TokenTypes.COMPILATION_UNIT)
                .findFirstToken(TokenTypes.CLASS_DEF)
                .findFirstToken(TokenTypes.OBJBLOCK)
                .findFirstToken(TokenTypes.SINGLE_LINE_COMMENT);
        final DetailAST[] expected = {expectedVariableDefNode};
        assertWithMessage("Result nodes differ from expected")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    void manyNestedNodes() throws Exception {
        final String xpath = "//STRING_LITERAL";
        final RootNode rootNode = getRootNode("InputXpathMapperStringConcat.java");
        final List<NodeInfo> actual = getXpathItems(xpath, rootNode);
        assertWithMessage("Number of result nodes differ from expected")
                .that(actual.size())
                .isEqualTo(39299);
    }

    private RootNode getRootNode(String fileName) throws Exception {
        final File file = new File(getPath(fileName));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITHOUT_COMMENTS);
        return new RootNode(rootAst);
    }

    private RootNode getRootNodeWithComments(String fileName) throws Exception {
        final File file = new File(getPath(fileName));
        final DetailAST rootAst = JavaParser.parseFile(file, JavaParser.Options.WITH_COMMENTS);
        return new RootNode(rootAst);
    }

    private static DetailAST[] convertToArray(List<NodeInfo> nodes) {
        final DetailAST[] result = new DetailAST[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            final ElementNode abstractNode = (ElementNode) nodes.get(i);
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

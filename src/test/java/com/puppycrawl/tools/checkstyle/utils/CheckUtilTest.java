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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.findTokenInAstByPredicate;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;

public class CheckUtilTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/checkutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(CheckUtil.class))
                .isTrue();
    }

    @Test
    public void testParseDoubleWithIncorrectToken() {
        final double parsedDouble = CheckUtil.parseDouble("1_02", TokenTypes.ASSIGN);
        assertWithMessage("Invalid parse result")
            .that(parsedDouble)
            .isEqualTo(Double.NaN);
    }

    @Test
    public void testElseWithCurly() {
        final DetailAstImpl ast = new DetailAstImpl();
        ast.setType(TokenTypes.ASSIGN);
        ast.setText("ASSIGN");
        assertWithMessage("Invalid elseIf check result 'ASSIGN' is not 'else if'")
                .that(CheckUtil.isElseIf(ast))
                .isFalse();

        final DetailAstImpl parentAst = new DetailAstImpl();
        parentAst.setType(TokenTypes.LCURLY);
        parentAst.setText("LCURLY");

        final DetailAstImpl ifAst = new DetailAstImpl();
        ifAst.setType(TokenTypes.LITERAL_IF);
        ifAst.setText("IF");
        parentAst.addChild(ifAst);

        assertWithMessage("Invalid elseIf check result: 'IF' is not 'else if'")
                .that(CheckUtil.isElseIf(ifAst))
                .isFalse();

        final DetailAstImpl parentAst2 = new DetailAstImpl();
        parentAst2.setType(TokenTypes.SLIST);
        parentAst2.setText("SLIST");

        parentAst2.addChild(ifAst);

        assertWithMessage("Invalid elseIf check result: 'SLIST' is not 'else if'")
                .that(CheckUtil.isElseIf(ifAst))
                .isFalse();

        final DetailAstImpl elseAst = new DetailAstImpl();
        elseAst.setType(TokenTypes.LITERAL_ELSE);

        elseAst.setFirstChild(ifAst);
        assertWithMessage("Invalid elseIf check result")
                .that(CheckUtil.isElseIf(ifAst))
                .isTrue();
    }

    @Test
    public void testEquals() {
        final DetailAstImpl litStatic = new DetailAstImpl();
        litStatic.setType(TokenTypes.LITERAL_STATIC);

        final DetailAstImpl modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.MODIFIERS);
        modifiers.addChild(litStatic);

        final DetailAstImpl metDef = new DetailAstImpl();
        metDef.setType(TokenTypes.METHOD_DEF);
        metDef.addChild(modifiers);

        assertWithMessage("Invalid result: ast is not equals method")
                .that(CheckUtil.isEqualsMethod(metDef))
                .isFalse();

        metDef.removeChildren();

        final DetailAstImpl metName = new DetailAstImpl();
        metName.setType(TokenTypes.IDENT);
        metName.setText("equals");
        metDef.addChild(metName);

        final DetailAstImpl modifiers2 = new DetailAstImpl();
        modifiers2.setType(TokenTypes.MODIFIERS);
        metDef.addChild(modifiers2);

        final DetailAstImpl parameter1 = new DetailAstImpl();
        final DetailAstImpl parameter2 = new DetailAstImpl();

        final DetailAstImpl parameters = new DetailAstImpl();
        parameters.setType(TokenTypes.PARAMETERS);

        parameters.addChild(parameter2);

        parameters.addChild(parameter1);
        metDef.addChild(parameters);

        assertWithMessage("Invalid result: ast is not equals method")
                .that(CheckUtil.isEqualsMethod(metDef))
                .isFalse();
    }

    @Test
    public void testGetAccessModifierFromModifiersTokenWrongTokenType() {
        final DetailAstImpl modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.METHOD_DEF);

        try {
            CheckUtil.getAccessModifierFromModifiersToken(modifiers);
            assertWithMessage("%s was expected.", IllegalArgumentException.class.getSimpleName())
                .fail();
        }
        catch (IllegalArgumentException exc) {
            final String expectedExceptionMsg = "expected non-null AST-token with type 'MODIFIERS'";
            final String actualExceptionMsg = exc.getMessage();
            assertWithMessage("Invalid exception message")
                .that(actualExceptionMsg)
                .isEqualTo(expectedExceptionMsg);
        }
    }

    @Test
    public void testGetTypeParameterNames() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final List<String> expected = Arrays.asList("V", "C");
        final List<String> actual = CheckUtil.getTypeParameterNames(parameterizedClassNode);

        assertWithMessage("Invalid type parameters")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetTypeParameters() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final DetailAST firstTypeParameter =
                getNode(parameterizedClassNode, TokenTypes.TYPE_PARAMETER);
        final List<DetailAST> expected = Arrays.asList(firstTypeParameter,
                firstTypeParameter.getNextSibling().getNextSibling());
        final List<DetailAST> actual = CheckUtil.getTypeParameters(parameterizedClassNode);

        assertWithMessage("Invalid type parameters")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testIsEqualsMethod() throws Exception {
        final DetailAST equalsMethodNode = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST someOtherMethod = equalsMethodNode.getNextSibling();

        assertWithMessage("Invalid result: AST provided is not equals method")
                .that(CheckUtil.isEqualsMethod(equalsMethodNode))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is equals method")
                .that(CheckUtil.isEqualsMethod(someOtherMethod))
                .isFalse();
    }

    @Test
    public void testIsElseIf() throws Exception {
        final DetailAST targetMethodNode = getNodeFromFile(TokenTypes.METHOD_DEF).getNextSibling();
        final DetailAST firstElseNode = getNode(targetMethodNode, TokenTypes.LITERAL_ELSE);
        final DetailAST ifElseWithCurlyBraces = firstElseNode.getFirstChild().getFirstChild();
        final DetailAST ifElse = getNode(firstElseNode.getParent().getNextSibling(),
                TokenTypes.LITERAL_ELSE).getFirstChild();
        final DetailAST ifWithoutElse =
                firstElseNode.getParent().getNextSibling().getNextSibling();

        assertWithMessage("Invalid result: AST provided is not else if with curly")
                .that(CheckUtil.isElseIf(ifElseWithCurlyBraces))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is not else if with curly")
                .that(CheckUtil.isElseIf(ifElse))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is else if with curly")
                .that(CheckUtil.isElseIf(ifWithoutElse))
                .isFalse();
    }

    @Test
    public void testIsNonVoidMethod() throws Exception {
        final DetailAST nonVoidMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST voidMethod = nonVoidMethod.getNextSibling();

        assertWithMessage("Invalid result: AST provided is void method")
                .that(CheckUtil.isNonVoidMethod(nonVoidMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is non void method")
                .that(CheckUtil.isNonVoidMethod(voidMethod))
                .isFalse();
    }

    @Test
    public void testIsGetterMethod() throws Exception {
        final DetailAST notGetterMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST getterMethod = notGetterMethod.getNextSibling().getNextSibling();

        assertWithMessage("Invalid result: AST provided is getter method")
                .that(CheckUtil.isGetterMethod(getterMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is not getter method")
                .that(CheckUtil.isGetterMethod(notGetterMethod))
                .isFalse();
    }

    @Test
    public void testIsSetterMethod() throws Exception {
        final DetailAST firstClassMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST setterMethod =
                firstClassMethod.getNextSibling().getNextSibling().getNextSibling();
        final DetailAST notSetterMethod = setterMethod.getNextSibling();

        assertWithMessage("Invalid result: AST provided is setter method")
                .that(CheckUtil.isSetterMethod(setterMethod))
                .isTrue();
        assertWithMessage("Invalid result: AST provided is not setter method")
                .that(CheckUtil.isSetterMethod(notSetterMethod))
                .isFalse();
    }

    @Test
    public void testGetAccessModifierFromModifiersToken() throws Exception {
        final DetailAST interfaceDef = getNodeFromFile(TokenTypes.INTERFACE_DEF);
        final AccessModifierOption modifierInterface = CheckUtil
                .getAccessModifierFromModifiersToken(interfaceDef
                        .findFirstToken(TokenTypes.OBJBLOCK)
                        .findFirstToken(TokenTypes.METHOD_DEF));
        assertWithMessage("Invalid access modifier")
            .that(modifierInterface)
            .isEqualTo(AccessModifierOption.PUBLIC);

        final DetailAST privateVariable = getNodeFromFile(TokenTypes.VARIABLE_DEF);
        final AccessModifierOption modifierPrivate =
                CheckUtil.getAccessModifierFromModifiersToken(privateVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPrivate)
            .isEqualTo(AccessModifierOption.PRIVATE);

        final DetailAST protectedVariable = privateVariable.getNextSibling();
        final AccessModifierOption modifierProtected =
                CheckUtil.getAccessModifierFromModifiersToken(protectedVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierProtected)
            .isEqualTo(AccessModifierOption.PROTECTED);

        final DetailAST publicVariable = protectedVariable.getNextSibling();
        final AccessModifierOption modifierPublic =
                CheckUtil.getAccessModifierFromModifiersToken(publicVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPublic)
            .isEqualTo(AccessModifierOption.PUBLIC);

        final DetailAST packageVariable = publicVariable.getNextSibling();
        final AccessModifierOption modifierPackage =
                CheckUtil.getAccessModifierFromModifiersToken(packageVariable);
        assertWithMessage("Invalid access modifier")
            .that(modifierPackage)
            .isEqualTo(AccessModifierOption.PACKAGE);
    }

    @Test
    public void testGetFirstNode() throws Exception {
        final DetailAST classDef = getNodeFromFile(TokenTypes.CLASS_DEF);

        final DetailAST firstChild = classDef.getFirstChild().getFirstChild();
        final DetailAST firstNode = CheckUtil.getFirstNode(classDef);
        assertWithMessage("Invalid first node")
            .that(firstNode)
            .isEqualTo(firstChild);
    }

    @Test
    public void testGetFirstNode1() {
        final DetailAstImpl child = new DetailAstImpl();
        child.setLineNo(5);
        child.setColumnNo(6);

        final DetailAstImpl root = new DetailAstImpl();
        root.setLineNo(5);
        root.setColumnNo(6);

        root.addChild(child);

        final DetailAST firstNode = CheckUtil.getFirstNode(root);
        assertWithMessage("Unexpected node")
            .that(firstNode)
            .isEqualTo(root);
    }

    @Test
    public void testGetFirstNode2() {
        final DetailAstImpl child = new DetailAstImpl();
        child.setLineNo(6);
        child.setColumnNo(5);

        final DetailAstImpl root = new DetailAstImpl();
        root.setLineNo(5);
        root.setColumnNo(6);

        root.addChild(child);

        final DetailAST firstNode = CheckUtil.getFirstNode(root);
        assertWithMessage("Unexpected node")
            .that(firstNode)
            .isEqualTo(root);
    }

    @Test
    public void testIsReceiverParameter() throws Exception {
        final DetailAST objBlock = getNodeFromFile(TokenTypes.OBJBLOCK);
        final DetailAST methodWithReceiverParameter = objBlock.getLastChild().getPreviousSibling()
                .getPreviousSibling();
        final DetailAST receiverParameter =
                getNode(methodWithReceiverParameter, TokenTypes.PARAMETER_DEF);
        final DetailAST simpleParameter =
                receiverParameter.getNextSibling().getNextSibling();

        assertWithMessage("Invalid result: parameter provided is receiver parameter")
                .that(CheckUtil.isReceiverParameter(receiverParameter))
                .isTrue();
        assertWithMessage("Invalid result: parameter provided is not receiver parameter")
                .that(CheckUtil.isReceiverParameter(simpleParameter))
                .isFalse();
    }

    @Test
    public void testParseDoubleFloatingPointValues() {
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-0.05f", TokenTypes.NUM_FLOAT))
            .isEqualTo(-0.05);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("10.0", TokenTypes.NUM_DOUBLE))
            .isEqualTo(10.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("1.23e3", TokenTypes.NUM_DOUBLE))
            .isEqualTo(1230);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-3.21E2", TokenTypes.NUM_DOUBLE))
            .isEqualTo(-321);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-0.0", TokenTypes.NUM_DOUBLE))
            .isEqualTo(-0.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("NaN", TokenTypes.NUM_DOUBLE))
            .isEqualTo(Double.NaN);
    }

    @Test
    public void testParseDoubleIntegerValues() {
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0L", TokenTypes.NUM_LONG))
            .isEqualTo(0.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0B101", TokenTypes.NUM_INT))
            .isEqualTo(0b101);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0b10001010001011010000101000101L", TokenTypes.NUM_LONG))
            .isEqualTo(289_775_941);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("1", TokenTypes.NUM_INT))
            .isEqualTo(1.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("8L", TokenTypes.NUM_LONG))
            .isEqualTo(8.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-21474836480", TokenTypes.NUM_LONG))
            .isEqualTo(-2.147_483_648E10);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("-2", TokenTypes.NUM_INT))
            .isEqualTo(-2);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0xffffffff", TokenTypes.NUM_INT))
            .isEqualTo(-1);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("0x0B63", TokenTypes.NUM_INT))
            .isEqualTo(2915.0);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("21474836470", TokenTypes.NUM_LONG))
            .isEqualTo(2.147_483_647E10);
        assertWithMessage("Invalid parse result")
            .that(CheckUtil.parseDouble("073l", TokenTypes.NUM_LONG))
            .isEqualTo(59.0);
    }

    @Test
    public void testParseClassNames() {
        final Set<String> actual = CheckUtil.parseClassNames(
                "I.am.class.name.with.dot.in.the.end.", "ClassOnly", "my.Class");
        final Set<String> expected = new HashSet<>();
        expected.add("I.am.class.name.with.dot.in.the.end.");
        expected.add("ClassOnly");
        expected.add("my.Class");
        expected.add("Class");
        assertWithMessage("Result is not expected")
            .that(actual)
            .isEqualTo(expected);
    }

    private DetailAST getNodeFromFile(int type) throws Exception {
        return getNode(JavaParser.parseFile(new File(getPath("InputCheckUtilTest.java")),
            JavaParser.Options.WITH_COMMENTS), type);
    }

    private static DetailAST getNode(DetailAST root, int type) {
        final Optional<DetailAST> node = findTokenInAstByPredicate(root,
            ast -> ast.getType() == type);

        assertWithMessage("Cannot find node of specified type: %s", type)
            .that(node.isPresent())
            .isTrue();

        return node.get();
    }

}

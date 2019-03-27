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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.findTokenInAstByPredicate;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifier;

public class CheckUtilTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/checkutil";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(CheckUtil.class, true));
    }

    @Test
    public void testParseDoubleWithIncorrectToken() {
        final double parsedDouble = CheckUtil.parseDouble("1_02", TokenTypes.ASSIGN);
        assertEquals("Invalid parse result", Double.NaN, parsedDouble, 0.0);
    }

    @Test
    public void testElseWithCurly() {
        final DetailAST ast = new DetailAstImpl();
        ast.setType(TokenTypes.ASSIGN);
        ast.setText("ASSIGN");
        assertFalse("Invalid elseIf check result 'ASSIGN' is not 'else if'",
                CheckUtil.isElseIf(ast));

        final DetailAstImpl parentAst = new DetailAstImpl();
        parentAst.setType(TokenTypes.LCURLY);
        parentAst.setText("LCURLY");

        final DetailAstImpl ifAst = new DetailAstImpl();
        ifAst.setType(TokenTypes.LITERAL_IF);
        ifAst.setText("IF");
        parentAst.addChild(ifAst);

        assertFalse("Invalid elseIf check result: 'IF' is not 'else if'",
                CheckUtil.isElseIf(ifAst));

        final DetailAstImpl parentAst2 = new DetailAstImpl();
        parentAst2.setType(TokenTypes.SLIST);
        parentAst2.setText("SLIST");

        parentAst2.addChild(ifAst);

        assertFalse("Invalid elseIf check result: 'SLIST' is not 'else if'",
                CheckUtil.isElseIf(ifAst));

        final DetailAstImpl elseAst = new DetailAstImpl();
        elseAst.setType(TokenTypes.LITERAL_ELSE);

        elseAst.setFirstChild(ifAst);
        assertTrue("Invalid elseIf check result", CheckUtil.isElseIf(ifAst));
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

        assertFalse("Invalid result: ast is not equals method",
                CheckUtil.isEqualsMethod(metDef));

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

        assertFalse("Invalid result: ast is not equals method",
                CheckUtil.isEqualsMethod(metDef));
    }

    @Test
    public void testGetAccessModifierFromModifiersTokenWrongTokenType() {
        final DetailAST modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.METHOD_DEF);

        try {
            CheckUtil.getAccessModifierFromModifiersToken(modifiers);
            fail(IllegalArgumentException.class.getSimpleName() + " was expected.");
        }
        catch (IllegalArgumentException exc) {
            final String expectedExceptionMsg = "expected non-null AST-token with type 'MODIFIERS'";
            final String actualExceptionMsg = exc.getMessage();
            assertEquals("Invalid exception message", expectedExceptionMsg, actualExceptionMsg);
        }
    }

    @Test
    public void testGetAccessModifierFromModifiersTokenWithNullParameter() {
        try {
            CheckUtil.getAccessModifierFromModifiersToken(null);
            fail(IllegalArgumentException.class.getSimpleName() + " was expected.");
        }
        catch (IllegalArgumentException exc) {
            final String expectedExceptionMsg = "expected non-null AST-token with type 'MODIFIERS'";
            final String actualExceptionMsg = exc.getMessage();
            assertEquals("Invalid exception message", expectedExceptionMsg, actualExceptionMsg);
        }
    }

    @Test
    public void testCreateFullType() throws Exception {
        final DetailAST typeNode = getNodeFromFile(TokenTypes.TYPE);

        assertEquals("Invalid full type", "Map[13x12]",
                CheckUtil.createFullType(typeNode).toString());
    }

    @Test
    public void testCreateFullTypeOfArray() throws Exception {
        final DetailAST arrayTypeNode = getNodeFromFile(TokenTypes.VARIABLE_DEF)
                .getNextSibling().getFirstChild().getNextSibling();

        assertEquals("Invalid full type", "int[14x14]",
                CheckUtil.createFullType(arrayTypeNode).toString());
    }

    @Test
    public void testGetTypeParameterNames() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final List<String> expected = Arrays.asList("V", "C");

        assertEquals("Invalid type parameters",
                expected, CheckUtil.getTypeParameterNames(parameterizedClassNode));
    }

    @Test
    public void testGetTypeParameters() throws Exception {
        final DetailAST parameterizedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final DetailAST firstTypeParameter =
                getNode(parameterizedClassNode, TokenTypes.TYPE_PARAMETER);
        final List<DetailAST> expected = Arrays.asList(firstTypeParameter,
                firstTypeParameter.getNextSibling().getNextSibling());

        assertEquals("Invalid type parameters", expected,
                CheckUtil.getTypeParameters(parameterizedClassNode));
    }

    @Test
    public void testIsEqualsMethod() throws Exception {
        final DetailAST equalsMethodNode = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST someOtherMethod = equalsMethodNode.getNextSibling();

        assertTrue("Invalid result: AST provided is not equals method",
                CheckUtil.isEqualsMethod(equalsMethodNode));
        assertFalse("Invalid result: AST provided is equals method",
                CheckUtil.isEqualsMethod(someOtherMethod));
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

        assertTrue("Invalid result: AST provided is not else if with curly",
                CheckUtil.isElseIf(ifElseWithCurlyBraces));
        assertTrue("Invalid result: AST provided is not else if with curly",
                CheckUtil.isElseIf(ifElse));
        assertFalse("Invalid result: AST provided is else if with curly",
                CheckUtil.isElseIf(ifWithoutElse));
    }

    @Test
    public void testIsNonVoidMethod() throws Exception {
        final DetailAST nonVoidMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST voidMethod = nonVoidMethod.getNextSibling();

        assertTrue("Invalid result: AST provided is void method",
                CheckUtil.isNonVoidMethod(nonVoidMethod));
        assertFalse("Invalid result: AST provided is non void method",
                CheckUtil.isNonVoidMethod(voidMethod));
    }

    @Test
    public void testIsGetterMethod() throws Exception {
        final DetailAST notGetterMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST getterMethod = notGetterMethod.getNextSibling().getNextSibling();

        assertTrue("Invalid result: AST provided is getter method",
                CheckUtil.isGetterMethod(getterMethod));
        assertFalse("Invalid result: AST provided is not getter method",
                CheckUtil.isGetterMethod(notGetterMethod));
    }

    @Test
    public void testIsSetterMethod() throws Exception {
        final DetailAST firstClassMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST setterMethod =
                firstClassMethod.getNextSibling().getNextSibling().getNextSibling();
        final DetailAST notSetterMethod = setterMethod.getNextSibling();

        assertTrue("Invalid result: AST provided is setter method",
                CheckUtil.isSetterMethod(setterMethod));
        assertFalse("Invalid result: AST provided is not setter method",
                CheckUtil.isSetterMethod(notSetterMethod));
    }

    @Test
    public void testGetAccessModifierFromModifiersToken() throws Exception {
        final DetailAST privateVariable = getNodeFromFile(TokenTypes.VARIABLE_DEF);
        final DetailAST protectedVariable = privateVariable.getNextSibling();
        final DetailAST publicVariable = protectedVariable.getNextSibling();
        final DetailAST packageVariable = publicVariable.getNextSibling();

        assertEquals("Invalid access modifier", AccessModifier.PRIVATE,
                CheckUtil.getAccessModifierFromModifiersToken(privateVariable.getFirstChild()));
        assertEquals("Invalid access modifier", AccessModifier.PROTECTED,
                CheckUtil.getAccessModifierFromModifiersToken(protectedVariable.getFirstChild()));
        assertEquals("Invalid access modifier", AccessModifier.PUBLIC,
                CheckUtil.getAccessModifierFromModifiersToken(publicVariable.getFirstChild()));
        assertEquals("Invalid access modifier", AccessModifier.PACKAGE,
                CheckUtil.getAccessModifierFromModifiersToken(packageVariable.getFirstChild()));
    }

    @Test
    public void testGetFirstNode() throws Exception {
        final DetailAST classDef = getNodeFromFile(TokenTypes.CLASS_DEF);

        assertEquals("Invalid first node", classDef.getFirstChild().getFirstChild(),
                CheckUtil.getFirstNode(classDef));
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

        assertEquals("Unexpected node", root, CheckUtil.getFirstNode(root));
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

        assertEquals("Unexpected node", root, CheckUtil.getFirstNode(root));
    }

    @Test
    public void testIsReceiverParameter() throws Exception {
        final DetailAST objBlock = getNodeFromFile(TokenTypes.OBJBLOCK);
        final DetailAST methodWithReceiverParameter = objBlock.getLastChild().getPreviousSibling();
        final DetailAST receiverParameter =
                getNode(methodWithReceiverParameter, TokenTypes.PARAMETER_DEF);
        final DetailAST simpleParameter =
                receiverParameter.getNextSibling().getNextSibling();

        assertTrue("Invalid result: parameter provided is receiver parameter",
                CheckUtil.isReceiverParameter(receiverParameter));
        assertFalse("Invalid result: parameter provided is not receiver parameter",
                CheckUtil.isReceiverParameter(simpleParameter));
    }

    @Test
    public void testParseDoubleFloatingPointValues() {
        assertEquals("Invalid parse result", -0.05,
                CheckUtil.parseDouble("-0.05f", TokenTypes.NUM_FLOAT), 0);
        assertEquals("Invalid parse result", 10.0,
                CheckUtil.parseDouble("10.0", TokenTypes.NUM_DOUBLE), 0);
        assertEquals("Invalid parse result", 1230,
                CheckUtil.parseDouble("1.23e3", TokenTypes.NUM_DOUBLE), 0);
        assertEquals("Invalid parse result", -321,
                CheckUtil.parseDouble("-3.21E2", TokenTypes.NUM_DOUBLE), 0);
        assertEquals("Invalid parse result", -0.0,
                CheckUtil.parseDouble("-0.0", TokenTypes.NUM_DOUBLE), 0);
        assertEquals("Invalid parse result", Double.NaN,
                CheckUtil.parseDouble("NaN", TokenTypes.NUM_DOUBLE), 0);
    }

    @Test
    public void testParseDoubleIntegerValues() {
        assertEquals("Invalid parse result", 0.0,
                CheckUtil.parseDouble("0L", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", 0b101,
                CheckUtil.parseDouble("0B101", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", 289_775_941,
                CheckUtil.parseDouble("0b10001010001011010000101000101L", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", 1.0,
                CheckUtil.parseDouble("1", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", 8.0,
                CheckUtil.parseDouble("8L", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", -2.147_483_648E10,
                CheckUtil.parseDouble("-21474836480", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", -2,
                CheckUtil.parseDouble("-2", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", -1,
                CheckUtil.parseDouble("0xffffffff", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", 2915.0,
                CheckUtil.parseDouble("0x0B63", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", 2.147_483_647E10,
                CheckUtil.parseDouble("21474836470", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", 59.0,
                CheckUtil.parseDouble("073l", TokenTypes.NUM_LONG), 0);
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
        assertEquals("Result is not expected", expected, actual);
    }

    private DetailAST getNodeFromFile(int type) throws Exception {
        return getNode(JavaParser.parseFile(new File(getPath("InputCheckUtilTest.java")),
            JavaParser.Options.WITH_COMMENTS), type);
    }

    private static DetailAST getNode(DetailAST root, int type) {
        final Optional<DetailAST> node = findTokenInAstByPredicate(root,
            ast -> ast.getType() == type);

        if (!node.isPresent()) {
            fail("Cannot find node of specified type: " + type);
        }

        return node.get();
    }

}

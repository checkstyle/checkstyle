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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static com.puppycrawl.tools.checkstyle.internal.TestUtils.findTokenInAstByPredicate;
import static com.puppycrawl.tools.checkstyle.internal.TestUtils.parseFile;
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
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifier;

public class CheckUtilsTest extends AbstractPathTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/checkutils";
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(CheckUtils.class, true);
    }

    @Test
    public void testParseDoubleWithIncorrectToken() {
        final double parsedDouble = CheckUtils.parseDouble("1_02", TokenTypes.ASSIGN);
        assertEquals("Invalid parse result", 0.0, parsedDouble, 0.0);
    }

    @Test
    public void testElseWithCurly() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ASSIGN);
        ast.setText("ASSIGN");
        assertFalse("Invalid elseIf check result 'ASSIGN' is not 'else if'",
                CheckUtils.isElseIf(ast));

        final DetailAST parentAst = new DetailAST();
        parentAst.setType(TokenTypes.LCURLY);
        parentAst.setText("LCURLY");

        final DetailAST ifAst = new DetailAST();
        ifAst.setType(TokenTypes.LITERAL_IF);
        ifAst.setText("IF");
        parentAst.addChild(ifAst);

        assertFalse("Invalid elseIf check result: 'IF' is not 'else if'",
                CheckUtils.isElseIf(ifAst));

        final DetailAST parentAst2 = new DetailAST();
        parentAst2.setType(TokenTypes.SLIST);
        parentAst2.setText("SLIST");

        parentAst2.addChild(ifAst);

        assertFalse("Invalid elseIf check result: 'SLIST' is not 'else if'",
                CheckUtils.isElseIf(ifAst));

        final DetailAST elseAst = new DetailAST();
        elseAst.setType(TokenTypes.LITERAL_ELSE);

        elseAst.setFirstChild(ifAst);
        assertTrue("Invalid elseIf check result", CheckUtils.isElseIf(ifAst));
    }

    @Test
    public void testEquals() {
        final DetailAST litStatic = new DetailAST();
        litStatic.setType(TokenTypes.LITERAL_STATIC);

        final DetailAST modifiers = new DetailAST();
        modifiers.setType(TokenTypes.MODIFIERS);
        modifiers.addChild(litStatic);

        final DetailAST metDef = new DetailAST();
        metDef.setType(TokenTypes.METHOD_DEF);
        metDef.addChild(modifiers);

        assertFalse("Invalid result: ast is not equals method",
                CheckUtils.isEqualsMethod(metDef));

        metDef.removeChildren();

        final DetailAST metName = new DetailAST();
        metName.setType(TokenTypes.IDENT);
        metName.setText("equals");
        metDef.addChild(metName);

        final DetailAST modifiers2 = new DetailAST();
        modifiers2.setType(TokenTypes.MODIFIERS);
        metDef.addChild(modifiers2);

        final DetailAST parameter1 = new DetailAST();
        final DetailAST parameter2 = new DetailAST();

        final DetailAST parameters = new DetailAST();
        parameters.setType(TokenTypes.PARAMETERS);

        parameters.addChild(parameter2);

        parameters.addChild(parameter1);
        metDef.addChild(parameters);

        assertFalse("Invalid result: ast is not equals method",
                CheckUtils.isEqualsMethod(metDef));
    }

    @Test
    public void testGetAccessModifierFromModifiersTokenWrongTokenType() {
        final DetailAST modifiers = new DetailAST();
        modifiers.setType(TokenTypes.METHOD_DEF);

        try {
            CheckUtils.getAccessModifierFromModifiersToken(modifiers);
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
            CheckUtils.getAccessModifierFromModifiersToken(null);
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
                CheckUtils.createFullType(typeNode).toString());
    }

    @Test
    public void testCreateFullTypeOfArray() throws Exception {
        final DetailAST arrayTypeNode = getNodeFromFile(TokenTypes.VARIABLE_DEF)
                .getNextSibling().getFirstChild().getNextSibling();

        assertEquals("Invalid full type", "int[14x14]",
                CheckUtils.createFullType(arrayTypeNode).toString());
    }

    @Test
    public void testGetTypeParameterNames() throws Exception {
        final DetailAST parameterisedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final List<String> expected = Arrays.asList("V", "C");

        assertEquals("Invalid type parameters",
                expected, CheckUtils.getTypeParameterNames(parameterisedClassNode));
    }

    @Test
    public void testGetTypeParameters() throws Exception {
        final DetailAST parameterisedClassNode = getNodeFromFile(TokenTypes.CLASS_DEF);
        final DetailAST firstTypeParameter =
                getNode(parameterisedClassNode, TokenTypes.TYPE_PARAMETER);
        final List<DetailAST> expected = Arrays.asList(firstTypeParameter,
                firstTypeParameter.getNextSibling().getNextSibling());

        assertEquals("Invalid type parameters", expected,
                CheckUtils.getTypeParameters(parameterisedClassNode));
    }

    @Test
    public void testIsEqualsMethod() throws Exception {
        final DetailAST equalsMethodNode = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST someOtherMethod = equalsMethodNode.getNextSibling();

        assertTrue("Invalid result: AST provided is not equals method",
                CheckUtils.isEqualsMethod(equalsMethodNode));
        assertFalse("Invalid result: AST provided is equals method",
                CheckUtils.isEqualsMethod(someOtherMethod));
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
                CheckUtils.isElseIf(ifElseWithCurlyBraces));
        assertTrue("Invalid result: AST provided is not else if with curly",
                CheckUtils.isElseIf(ifElse));
        assertFalse("Invalid result: AST provided is else if with curly",
                CheckUtils.isElseIf(ifWithoutElse));
    }

    @Test
    public void testIsNonViodMethod() throws Exception {
        final DetailAST nonVoidMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST voidMethod = nonVoidMethod.getNextSibling();

        assertTrue("Invalid result: AST provided is void method",
                CheckUtils.isNonVoidMethod(nonVoidMethod));
        assertFalse("Invalid result: AST provided is non void method",
                CheckUtils.isNonVoidMethod(voidMethod));
    }

    @Test
    public void testIsGetterMethod() throws Exception {
        final DetailAST notGetterMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST getterMethod = notGetterMethod.getNextSibling().getNextSibling();

        assertTrue("Invalid result: AST provided is getter method",
                CheckUtils.isGetterMethod(getterMethod));
        assertFalse("Invalid result: AST provided is not getter method",
                CheckUtils.isGetterMethod(notGetterMethod));
    }

    @Test
    public void testIsSetterMethod() throws Exception {
        final DetailAST firstClassMethod = getNodeFromFile(TokenTypes.METHOD_DEF);
        final DetailAST setterMethod =
                firstClassMethod.getNextSibling().getNextSibling().getNextSibling();
        final DetailAST notSetterMethod = setterMethod.getNextSibling();

        assertTrue("Invalid result: AST provided is setter method",
                CheckUtils.isSetterMethod(setterMethod));
        assertFalse("Invalid result: AST provided is not setter method",
                CheckUtils.isSetterMethod(notSetterMethod));
    }

    @Test
    public void testGetAccessModifierFromModifiersToken() throws Exception {
        final DetailAST privateVariable = getNodeFromFile(TokenTypes.VARIABLE_DEF);
        final DetailAST protectedVariable = privateVariable.getNextSibling();
        final DetailAST publicVariable = protectedVariable.getNextSibling();
        final DetailAST packageVariable = publicVariable.getNextSibling();

        assertEquals("Invalid access modofier", AccessModifier.PRIVATE,
                CheckUtils.getAccessModifierFromModifiersToken(privateVariable.getFirstChild()));
        assertEquals("Invalid access modofier", AccessModifier.PROTECTED,
                CheckUtils.getAccessModifierFromModifiersToken(protectedVariable.getFirstChild()));
        assertEquals("Invalid access modofier", AccessModifier.PUBLIC,
                CheckUtils.getAccessModifierFromModifiersToken(publicVariable.getFirstChild()));
        assertEquals("Invalid access modofier", AccessModifier.PACKAGE,
                CheckUtils.getAccessModifierFromModifiersToken(packageVariable.getFirstChild()));
    }

    @Test
    public void testGetFirstNode() throws Exception {
        final DetailAST classDef = getNodeFromFile(TokenTypes.CLASS_DEF);

        assertEquals("Invalid first node", classDef.getFirstChild().getFirstChild(),
                CheckUtils.getFirstNode(classDef));
    }

    @Test
    public void testGetFirstNode1() throws Exception {
        final DetailAST child = new DetailAST();
        child.setLineNo(5);
        child.setColumnNo(6);

        final DetailAST root = new DetailAST();
        root.setLineNo(5);
        root.setColumnNo(6);

        root.addChild(child);

        assertEquals("Unexpected node", root, CheckUtils.getFirstNode(root));
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
                CheckUtils.isReceiverParameter(receiverParameter));
        assertFalse("Invalid result: parameter provided is not receiver parameter",
                CheckUtils.isReceiverParameter(simpleParameter));
    }

    @Test
    public void testParseDouble() throws Exception {
        assertEquals("Invalid parse result", 1.0,
                CheckUtils.parseDouble("1", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", -0.05,
                CheckUtils.parseDouble("-0.05f", TokenTypes.NUM_FLOAT), 0);
        assertEquals("Invalid parse result", 8.0,
                CheckUtils.parseDouble("8L", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", 0.0,
                CheckUtils.parseDouble("0.0", TokenTypes.NUM_DOUBLE), 0);
        assertEquals("Invalid parse result", 2915.0,
                CheckUtils.parseDouble("0x0B63", TokenTypes.NUM_INT), 0);
        assertEquals("Invalid parse result", 2.147_483_647E10,
                CheckUtils.parseDouble("21474836470", TokenTypes.NUM_LONG), 0);
        assertEquals("Invalid parse result", 59.0,
                CheckUtils.parseDouble("073L", TokenTypes.NUM_LONG), 0);
    }

    @Test
    public void testParseClassNames() {
        final String className = "I.am.class.name.with.dot.in.the.end.";
        final Set<String> result = CheckUtils.parseClassNames(className);
        final Set<String> expected = new HashSet<>();
        expected.add(className);
        assertEquals("Result is not expected", expected, result);
    }

    private DetailAST getNodeFromFile(int type) throws Exception {
        return getNode(parseFile(new File(getPath("InputCheckUtilsTest.java"))), type);
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

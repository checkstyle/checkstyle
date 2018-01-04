////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ScopeUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(ScopeUtils.class, true));
    }

    @Test
    public void testInEnumBlock() {
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(new DetailAST()));
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.LITERAL_NEW,
                        TokenTypes.MODIFIERS)));
        assertTrue("Should return true when passed is enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.OBJBLOCK, TokenTypes.ENUM_DEF,
                        TokenTypes.MODIFIERS)));
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF,
                        TokenTypes.MODIFIERS)));
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.ANNOTATION_DEF,
                        TokenTypes.MODIFIERS)));
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.CLASS_DEF,
                        TokenTypes.MODIFIERS)));
        assertFalse("Should return false when passed is not enum",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.LITERAL_NEW,
                        TokenTypes.IDENT)));
        assertFalse("Should return false when passed is not expected",
                ScopeUtils.isInEnumBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)));
    }

    @Test
    public void testIsInCodeBlock() {
        assertFalse("invalid result", ScopeUtils.isInCodeBlock(getNode(TokenTypes.CLASS_DEF)));
        assertFalse("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.ASSIGN, TokenTypes.VARIABLE_DEF)));
        assertTrue("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.METHOD_DEF, TokenTypes.OBJBLOCK)));
        assertTrue("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.CTOR_DEF, TokenTypes.OBJBLOCK)));
        assertTrue("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.INSTANCE_INIT, TokenTypes.OBJBLOCK)));
        assertTrue("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.STATIC_INIT, TokenTypes.OBJBLOCK)));
        assertTrue("invalid result",
                ScopeUtils.isInCodeBlock(getNode(TokenTypes.LAMBDA, TokenTypes.ASSIGN)));
    }

    @Test
    public void testIsOuterMostTypeInterface() {
        assertFalse("Should return false when passed is not outer most type",
                ScopeUtils.isOuterMostType(getNode(TokenTypes.INTERFACE_DEF,
                        TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeAnnotation() {
        assertFalse("Should return false when passed is not outer most type",
                ScopeUtils.isOuterMostType(getNode(TokenTypes.ANNOTATION_DEF,
                        TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeEnum() {
        assertFalse("Should return false when passed is not outer most type",
                ScopeUtils.isOuterMostType(getNode(TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeClass() {
        assertFalse("Should return false when passed is not outer most type",
                ScopeUtils.isOuterMostType(getNode(TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypePackageDef() {
        assertTrue("Should return false when passed is not outer most type",
                ScopeUtils.isOuterMostType(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)));
    }

    @Test
    public void testIsLocalVariableDefCatch() {
        assertTrue("Should return true when passed is variable def",
                ScopeUtils.isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH,
                        TokenTypes.PARAMETER_DEF)));
    }

    @Test
    public void testIsLocalVariableDefUnexpected() {
        assertFalse("Should return false when passed is not variable def",
                ScopeUtils.isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH)));
        assertFalse("Should return false when passed is not variable def",
                ScopeUtils.isLocalVariableDef(getNode(TokenTypes.COMMA, TokenTypes.PARAMETER_DEF)));
    }

    @Test
    public void testIsLocalVariableDefResource() {
        assertTrue("invalid result",
                ScopeUtils.isLocalVariableDef(getNode(TokenTypes.RESOURCE)));
    }

    @Test
    public void testIsLocalVariableDefVariable() {
        assertTrue("invalid result",
                ScopeUtils.isLocalVariableDef(getNode(TokenTypes.SLIST, TokenTypes.VARIABLE_DEF)));
        assertTrue("invalid result", ScopeUtils.isLocalVariableDef(getNode(TokenTypes.FOR_INIT,
                TokenTypes.VARIABLE_DEF)));
        assertTrue("invalid result", ScopeUtils.isLocalVariableDef(getNode(
                TokenTypes.FOR_EACH_CLAUSE, TokenTypes.VARIABLE_DEF)));
        assertFalse("invalid result", ScopeUtils.isLocalVariableDef(getNode(TokenTypes.CLASS_DEF,
                TokenTypes.VARIABLE_DEF)));
    }

    @Test
    public void testIsClassFieldDef() {
        assertTrue("Should return true when passed is class field def",
                ScopeUtils.isClassFieldDef(getNode(TokenTypes.CLASS_DEF,
                        TokenTypes.OBJBLOCK, TokenTypes.VARIABLE_DEF)));
        assertFalse("Should return false when passed is unexpected",
                ScopeUtils.isClassFieldDef(getNode(TokenTypes.CLASS_DEF)));
        assertFalse("Should return false when passed is method variable def",
                ScopeUtils.isClassFieldDef(getNode(TokenTypes.METHOD_DEF,
                        TokenTypes.SLIST, TokenTypes.VARIABLE_DEF)));
    }

    @Test
    public void testSurroundingScope() {
        assertEquals("Invalid surrounding scope",
                Scope.PUBLIC, ScopeUtils.getSurroundingScope(getNodeWithParentScope(
            TokenTypes.LITERAL_PUBLIC, "public", TokenTypes.ANNOTATION_DEF)));
        assertEquals("Invalid surrounding scope",
                Scope.PROTECTED, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED, "protected",
            TokenTypes.INTERFACE_DEF)));
        assertEquals("Invalid surrounding scope",
                Scope.PRIVATE, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_PRIVATE, "private", TokenTypes.ENUM_DEF)));
        assertEquals("Invalid surrounding scope",
                Scope.PACKAGE, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_STATIC, "static", TokenTypes.CLASS_DEF)));
    }

    @Test
    public void testIsInScope() {
        assertTrue("Should return true when node is in valid scope",
                ScopeUtils.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PUBLIC,
                "public", TokenTypes.ANNOTATION_DEF), Scope.PUBLIC));
        assertFalse("Should return false when node is in invalid scope",
                ScopeUtils.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED,
                "protected", TokenTypes.INTERFACE_DEF), Scope.PRIVATE));
    }

    @Test
    public void testSurroundingScopeOfNodeChildOfLiteralNewIsAnoninner() {
        assertEquals("Invalid surrounding scope",
                Scope.ANONINNER, ScopeUtils.getSurroundingScope(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)));
    }

    @Test
    public void testIsInInterfaceBlock() {
        final DetailAST ast = getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS);

        assertTrue("Should return true when node is interface block",
                ScopeUtils.isInInterfaceBlock(ast.getParent()));
        assertFalse("Should return false when node is not interface block",
                ScopeUtils.isInInterfaceBlock(ast));
    }

    @Test
    public void testIsInAnnotationBlock() {
        final DetailAST ast = getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS);

        assertTrue("Should return true when node is annotation block",
                ScopeUtils.isInAnnotationBlock(ast.getParent()));
        assertFalse("Should return false when node is not annotation block",
                ScopeUtils.isInAnnotationBlock(ast));
    }

    @Test
    public void testisInInterfaceOrAnnotationBlock() {
        assertTrue("Should return true when node is in interface or annotation block",
                ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK)));
        assertTrue("Should return true when node is in interface or annotation block",
                ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK)));
        assertFalse("Should return false when node is not in interface or annotation block",
                ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK)));
        assertFalse("Should return false when node is not in interface or annotation block",
                ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)));
        assertFalse("Should return false when node is not in interface or annotation block",
                ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.ENUM_DEF, TokenTypes.OBJBLOCK)));
    }

    private static DetailAST getNode(int... nodeTypes) {
        DetailAST ast = new DetailAST();
        ast.setType(nodeTypes[0]);
        for (int i = 1; i < nodeTypes.length; i++) {
            final DetailAST astChild = new DetailAST();
            astChild.setType(nodeTypes[i]);
            ast.addChild(astChild);
            ast = astChild;
        }
        return ast;
    }

    private static DetailAST getNodeWithParentScope(int literal, String scope,
                                                    int parentTokenType) {
        final DetailAST ast = getNode(parentTokenType, TokenTypes.MODIFIERS, literal);
        ast.setText(scope);
        final DetailAST ast2 = getNode(TokenTypes.OBJBLOCK);
        ast.getParent().getParent().addChild(ast2);
        return ast;
    }

}

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
        assertUtilsClassHasPrivateConstructor(ScopeUtils.class);
    }

    @Test
    public void testInEnumOnRoot() {
        assertFalse(ScopeUtils.isInEnumBlock(new DetailAST()));
    }

    @Test
    public void testInEnumBlockInNew() {
        assertFalse(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testInEnumBlockWithEnum() {
        assertTrue(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.OBJBLOCK, TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testInEnumBlockInInterface() {
        assertFalse(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testInEnumBlockInAnnotation() {
        assertFalse(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testInEnumBlockInClass() {
        assertFalse(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testInEnumBlockInLiteralNew() {
        assertFalse(ScopeUtils.isInEnumBlock(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)));
    }

    @Test
    public void testIsOuterMostTypeInterface() {
        assertFalse(ScopeUtils.isOuterMostType(
                getNode(TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeAnnotation() {
        assertFalse(ScopeUtils.isOuterMostType(
                getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeEnum() {
        assertFalse(ScopeUtils.isOuterMostType(
                getNode(TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsOuterMostTypeClass() {
        assertFalse(ScopeUtils.isOuterMostType(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)));
    }

    @Test
    public void testIsLocalVariableDefCatch() {
        assertTrue(ScopeUtils.isLocalVariableDef(
                getNode(TokenTypes.LITERAL_CATCH, TokenTypes.PARAMETER_DEF)));
    }

    @Test
    public void testIsLocalVariableDefUnexpected() {
        assertFalse(ScopeUtils.isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH)));
    }

    @Test
    public void testIsClassFieldDef() {
        assertTrue(ScopeUtils.isClassFieldDef(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK, TokenTypes.VARIABLE_DEF)));
    }

    @Test
    public void testSurroundingScope() {
        assertEquals(Scope.PUBLIC, ScopeUtils.getSurroundingScope(getNodeWithParentScope(
            TokenTypes.LITERAL_PUBLIC, "public", TokenTypes.ANNOTATION_DEF)));
        assertEquals(Scope.PROTECTED, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED, "protected",
            TokenTypes.INTERFACE_DEF)));
        assertEquals(Scope.PRIVATE, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_PRIVATE, "private", TokenTypes.ENUM_DEF)));
        assertEquals(Scope.PACKAGE, ScopeUtils.getSurroundingScope(
            getNodeWithParentScope(TokenTypes.LITERAL_STATIC, "static", TokenTypes.CLASS_DEF)));
    }

    @Test
    public void testIsInScope() {
        assertTrue(ScopeUtils.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PUBLIC,
                "public", TokenTypes.ANNOTATION_DEF), Scope.PUBLIC));
        assertFalse(ScopeUtils.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED,
                "protected", TokenTypes.INTERFACE_DEF), Scope.PRIVATE));
    }

    @Test
    public void testSurroundingScopeOfNodeChildOfLiteralNewIsAnoninner() {
        assertEquals(Scope.ANONINNER, ScopeUtils.getSurroundingScope(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)));
    }

    @Test
    public void testIsInInterfaceBlock() {
        final DetailAST ast = getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS);

        assertTrue(ScopeUtils.isInInterfaceBlock(ast.getParent()));
        assertFalse(ScopeUtils.isInInterfaceBlock(ast));
    }

    @Test
    public void testIsInAnnotationBlock() {
        final DetailAST ast = getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS);

        assertTrue(ScopeUtils.isInAnnotationBlock(ast.getParent()));
        assertFalse(ScopeUtils.isInAnnotationBlock(ast));
    }

    @Test
    public void testisInInterfaceOrAnnotationBlock() {
        assertTrue(ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK)));
        assertTrue(ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK)));
        assertFalse(ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK)));
        assertFalse(ScopeUtils.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)));
        assertFalse(ScopeUtils.isInInterfaceOrAnnotationBlock(
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

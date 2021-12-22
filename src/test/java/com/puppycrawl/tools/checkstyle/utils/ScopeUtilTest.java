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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ScopeUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(ScopeUtil.class, true))
                .isTrue();
    }

    @Test
    public void testInClassBlock() {
        assertFalse(ScopeUtil.isInClassBlock(new DetailAstImpl()),
                "Should return false when passed is not class");
        assertFalse(ScopeUtil.isInClassBlock(getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)),
                "Should return false when passed is not class");
        assertWithMessage("Should return true when passed is class")
                .that(ScopeUtil.isInClassBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertFalse(ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not class");
        assertFalse(
                ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF, TokenTypes.ANNOTATION_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not class");
        assertFalse(ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not class");
        assertFalse(ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF, TokenTypes.LITERAL_NEW,
                TokenTypes.IDENT)), "Should return false when passed is not class");
        assertFalse(ScopeUtil.isInClassBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)),
                "Should return false when passed is not expected");
    }

    @Test
    public void testInEnumBlock() {
        assertFalse(ScopeUtil.isInEnumBlock(new DetailAstImpl()),
                "Should return false when passed is not enum");
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)),
                "Should return false when passed is not enum");
        assertWithMessage("Should return true when passed is enum")
                .that(ScopeUtil.isInEnumBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not enum");
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.ANNOTATION_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not enum");
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.CLASS_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not enum");
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.LITERAL_NEW,
                TokenTypes.IDENT)), "Should return false when passed is not enum");
        assertFalse(ScopeUtil.isInEnumBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)),
                "Should return false when passed is not expected");
    }

    @Test
    public void testIsInCodeBlock() {
        assertFalse(ScopeUtil.isInCodeBlock(getNode(TokenTypes.CLASS_DEF)), "invalid result");
        assertFalse(ScopeUtil.isInCodeBlock(getNode(TokenTypes.ASSIGN, TokenTypes.VARIABLE_DEF)),
                "invalid result");
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.METHOD_DEF, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.CTOR_DEF, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertWithMessage("invalid result")
                .that(ScopeUtil
                        .isInCodeBlock(getNode(TokenTypes.INSTANCE_INIT, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.STATIC_INIT, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.LAMBDA, TokenTypes.ASSIGN)))
                .isTrue();
    }

    @Test
    public void testInRecordBlock() {
        assertFalse(ScopeUtil.isInRecordBlock(new DetailAstImpl()),
                "Should return false when passed is not record");
        assertFalse(ScopeUtil.isInRecordBlock(getNode(TokenTypes.LITERAL_NEW,
                TokenTypes.MODIFIERS)), "Should return false when passed is not record");
        assertWithMessage("Should return true when passed is record")
                .that(ScopeUtil.isInRecordBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.RECORD_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertFalse(ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF,
                TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)),
                "Should return false when passed is not record");
        assertFalse(
                ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF, TokenTypes.ANNOTATION_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not record");
        assertFalse(ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF, TokenTypes.ENUM_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not record");
        assertFalse(ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF, TokenTypes.LITERAL_NEW,
                TokenTypes.IDENT)), "Should return false when passed is not record");
        assertFalse(ScopeUtil.isInRecordBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)),
                "Should return false when passed is not expected");
    }

    @Test
    public void testIsOuterMostTypeInterface() {
        assertFalse(ScopeUtil.isOuterMostType(getNode(TokenTypes.INTERFACE_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not outer most type");
    }

    @Test
    public void testIsOuterMostTypeAnnotation() {
        assertFalse(ScopeUtil.isOuterMostType(getNode(TokenTypes.ANNOTATION_DEF,
                TokenTypes.MODIFIERS)), "Should return false when passed is not outer most type");
    }

    @Test
    public void testIsOuterMostTypeEnum() {
        assertFalse(ScopeUtil.isOuterMostType(getNode(TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)),
                "Should return false when passed is not outer most type");
    }

    @Test
    public void testIsOuterMostTypeClass() {
        assertFalse(ScopeUtil.isOuterMostType(getNode(TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)),
                "Should return false when passed is not outer most type");
    }

    @Test
    public void testIsOuterMostTypePackageDef() {
        assertWithMessage("Should return false when passed is not outer most type")
                .that(ScopeUtil.isOuterMostType(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)))
                .isTrue();
    }

    @Test
    public void testIsLocalVariableDefCatch() {
        assertWithMessage("Should return true when passed is variable def")
            .that(ScopeUtil
                .isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH, TokenTypes.PARAMETER_DEF)))
                .isTrue();
    }

    @Test
    public void testIsLocalVariableDefUnexpected() {
        assertFalse(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH)),
                "Should return false when passed is not variable def");
        assertFalse(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.COMMA,
                TokenTypes.PARAMETER_DEF)), "Should return false when passed is not variable def");
    }

    @Test
    public void testIsLocalVariableDefResource() {
        final DetailAstImpl node = getNode(TokenTypes.RESOURCE);
        final DetailAstImpl modifiers = new DetailAstImpl();
        modifiers.setType(TokenTypes.MODIFIERS);
        node.addChild(modifiers);
        final DetailAstImpl ident = new DetailAstImpl();
        ident.setType(TokenTypes.IDENT);
        node.addChild(ident);
        assertWithMessage("invalid result")
                .that(ScopeUtil.isLocalVariableDef(node))
                .isTrue();
        final DetailAstImpl resourceWithIdent = getNode(TokenTypes.RESOURCE);
        resourceWithIdent.addChild(ident);
        assertFalse(ScopeUtil.isLocalVariableDef(resourceWithIdent), "invalid result");
        assertFalse(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.RESOURCE)), "invalid result");
    }

    @Test
    public void testIsLocalVariableDefVariable() {
        assertWithMessage("invalid result")
                .that(ScopeUtil
                        .isLocalVariableDef(getNode(TokenTypes.SLIST, TokenTypes.VARIABLE_DEF)))
                .isTrue();
        assertWithMessage("invalid result")
                .that(ScopeUtil
                        .isLocalVariableDef(getNode(TokenTypes.FOR_INIT, TokenTypes.VARIABLE_DEF)))
                .isTrue();
        assertWithMessage("invalid result")
            .that(ScopeUtil
                .isLocalVariableDef(getNode(TokenTypes.FOR_EACH_CLAUSE, TokenTypes.VARIABLE_DEF)))
                .isTrue();
        assertFalse(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.CLASS_DEF,
                TokenTypes.VARIABLE_DEF)), "invalid result");
    }

    @Test
    public void testIsClassFieldDef() {
        assertWithMessage("Should return true when passed is class field def")
                .that(ScopeUtil.isClassFieldDef(getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK,
                        TokenTypes.VARIABLE_DEF)))
                .isTrue();
        assertFalse(ScopeUtil.isClassFieldDef(getNode(TokenTypes.CLASS_DEF)),
            "Should return false when passed is unexpected");
        assertFalse(ScopeUtil.isClassFieldDef(getNode(TokenTypes.METHOD_DEF, TokenTypes.SLIST,
            TokenTypes.VARIABLE_DEF)), "Should return false when passed is method variable def");
    }

    @Test
    public void testSurroundingScope() {
        final Scope publicScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PUBLIC, "public", TokenTypes.ANNOTATION_DEF));
        assertEquals(Scope.PUBLIC, publicScope,
                "Invalid surrounding scope");
        final Scope protectedScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PROTECTED, "protected", TokenTypes.INTERFACE_DEF));
        assertEquals(Scope.PROTECTED, protectedScope,
                "Invalid surrounding scope");
        final Scope privateScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PRIVATE, "private", TokenTypes.ENUM_DEF));
        assertEquals(Scope.PRIVATE, privateScope,
                "Invalid surrounding scope");
        final Scope staticScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_STATIC, "static", TokenTypes.CLASS_DEF));
        assertEquals(Scope.PACKAGE, staticScope, "Invalid surrounding scope");
    }

    @Test
    public void testIsInScope() {
        assertWithMessage("Should return true when node is in valid scope")
                .that(ScopeUtil.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PUBLIC,
                        "public", TokenTypes.ANNOTATION_DEF), Scope.PUBLIC))
                .isTrue();
        assertFalse(ScopeUtil.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED,
                "protected", TokenTypes.INTERFACE_DEF), Scope.PRIVATE),
                "Should return false when node is in invalid scope");
    }

    @Test
    public void testSurroundingScopeOfNodeChildOfLiteralNewIsAnoninner() {
        final Scope scope =
                ScopeUtil.getSurroundingScope(getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT));
        assertEquals(Scope.ANONINNER, scope, "Invalid surrounding scope");
    }

    @Test
    public void testIsInInterfaceBlock() {
        final DetailAST ast = getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS);

        assertWithMessage("Should return true when node is interface block")
                .that(ScopeUtil.isInInterfaceBlock(ast.getParent()))
                .isTrue();
        assertFalse(ScopeUtil.isInInterfaceBlock(ast),
                "Should return false when node is not interface block");
    }

    @Test
    public void testIsInAnnotationBlock() {
        final DetailAST ast = getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS);

        assertWithMessage("Should return true when node is annotation block")
                .that(ScopeUtil.isInAnnotationBlock(ast.getParent()))
                .isTrue();
        assertFalse(ScopeUtil.isInAnnotationBlock(ast),
                "Should return false when node is not annotation block");
    }

    @Test
    public void testisInInterfaceOrAnnotationBlock() {
        assertWithMessage("Should return true when node is in interface or annotation block")
                .that(ScopeUtil.isInInterfaceOrAnnotationBlock(
                        getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertWithMessage("Should return true when node is in interface or annotation block")
                .that(ScopeUtil.isInInterfaceOrAnnotationBlock(
                        getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK)))
                .isTrue();
        assertFalse(ScopeUtil.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK)),
                "Should return false when node is not in interface or annotation block");
        assertFalse(ScopeUtil.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)),
                "Should return false when node is not in interface or annotation block");
        assertFalse(ScopeUtil.isInInterfaceOrAnnotationBlock(
                getNode(TokenTypes.ENUM_DEF, TokenTypes.OBJBLOCK)),
                "Should return false when node is not in interface or annotation block");
    }

    private static DetailAstImpl getNode(int... nodeTypes) {
        DetailAstImpl ast = new DetailAstImpl();
        ast.setType(nodeTypes[0]);
        for (int i = 1; i < nodeTypes.length; i++) {
            final DetailAstImpl astChild = new DetailAstImpl();
            astChild.setType(nodeTypes[i]);
            ast.addChild(astChild);
            ast = astChild;
        }
        return ast;
    }

    private static DetailAST getNodeWithParentScope(int literal, String scope,
                                                    int parentTokenType) {
        final DetailAstImpl ast = getNode(parentTokenType, TokenTypes.MODIFIERS, literal);
        ast.setText(scope);
        final DetailAstImpl ast2 = getNode(TokenTypes.OBJBLOCK);
        ((DetailAstImpl) ast.getParent().getParent()).addChild(ast2);
        return ast;
    }

}

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

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ScopeUtilTest {

    @Test
    public void testIsInBlockOf() {
        final DetailAstImpl blockNode = new DetailAstImpl();
        blockNode.setType(TokenTypes.SLIST);

        final DetailAstImpl parentNode = new DetailAstImpl();
        parentNode.setType(TokenTypes.LITERAL_IF);
        parentNode.addChild(blockNode);

        final boolean firstResult = ScopeUtil.isInBlockOf(blockNode, TokenTypes.LITERAL_IF);

        assertWithMessage("Node should be detected inside the specified block")
                .that(firstResult)
                .isTrue();

        final DetailAstImpl unrelatedParent = new DetailAstImpl();
        unrelatedParent.setType(TokenTypes.CLASS_DEF);
        unrelatedParent.addChild(blockNode);

        final boolean secondResult = ScopeUtil.isInBlockOf(blockNode, TokenTypes.LITERAL_IF);

        assertWithMessage("Node should not be detected inside the specified block")
                .that(secondResult)
                .isFalse();
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(ScopeUtil.class))
                .isTrue();
    }

    @Test
    public void testInClassBlock() {
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil.isInClassBlock(new DetailAstImpl()))
                .isFalse();
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil
                        .isInClassBlock(getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return true when passed is class")
                .that(ScopeUtil.isInClassBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF,
                        TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil.isInClassBlock(getNode(TokenTypes.CLASS_DEF,
                        TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil.isInClassBlock(
                        getNode(TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not class")
                .that(ScopeUtil.isInClassBlock(
                        getNode(TokenTypes.CLASS_DEF, TokenTypes.LITERAL_NEW, TokenTypes.IDENT)))
                .isFalse();
        assertWithMessage("Should return false when passed is not expected")
                .that(ScopeUtil.isInClassBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)))
                .isFalse();
    }

    @Test
    public void testInEnumBlock() {
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil.isInEnumBlock(new DetailAstImpl()))
                .isFalse();
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil
                        .isInEnumBlock(getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return true when passed is enum")
                .that(ScopeUtil.isInEnumBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF, TokenTypes.INTERFACE_DEF,
                        TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil.isInEnumBlock(getNode(TokenTypes.ENUM_DEF,
                        TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil.isInEnumBlock(
                        getNode(TokenTypes.ENUM_DEF, TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not enum")
                .that(ScopeUtil.isInEnumBlock(
                        getNode(TokenTypes.ENUM_DEF, TokenTypes.LITERAL_NEW, TokenTypes.IDENT)))
                .isFalse();
        assertWithMessage("Should return false when passed is not expected")
                .that(ScopeUtil.isInEnumBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)))
                .isFalse();
    }

    @Test
    public void testIsInCodeBlock() {
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.CLASS_DEF)))
                .isFalse();
        assertWithMessage("invalid result")
                .that(ScopeUtil.isInCodeBlock(getNode(TokenTypes.ASSIGN, TokenTypes.VARIABLE_DEF)))
                .isFalse();
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
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil.isInRecordBlock(new DetailAstImpl()))
                .isFalse();
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil
                        .isInRecordBlock(getNode(TokenTypes.LITERAL_NEW, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return true when passed is record")
                .that(ScopeUtil.isInRecordBlock(
                        getNode(TokenTypes.OBJBLOCK, TokenTypes.RECORD_DEF, TokenTypes.MODIFIERS)))
                .isTrue();
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF,
                        TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil.isInRecordBlock(getNode(TokenTypes.RECORD_DEF,
                        TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil.isInRecordBlock(
                        getNode(TokenTypes.RECORD_DEF, TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
        assertWithMessage("Should return false when passed is not record")
                .that(ScopeUtil.isInRecordBlock(
                        getNode(TokenTypes.RECORD_DEF, TokenTypes.LITERAL_NEW, TokenTypes.IDENT)))
                .isFalse();
        assertWithMessage("Should return false when passed is not expected")
                .that(ScopeUtil.isInRecordBlock(getNode(TokenTypes.PACKAGE_DEF, TokenTypes.DOT)))
                .isFalse();
    }

    @Test
    public void testIsOuterMostTypeInterface() {
        assertWithMessage("Should return false when passed is not outer most type")
                .that(ScopeUtil
                        .isOuterMostType(getNode(TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
    }

    @Test
    public void testIsOuterMostTypeAnnotation() {
        assertWithMessage("Should return false when passed is not outer most type")
                .that(ScopeUtil
                        .isOuterMostType(getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
    }

    @Test
    public void testIsOuterMostTypeEnum() {
        assertWithMessage("Should return false when passed is not outer most type")
                .that(ScopeUtil.isOuterMostType(getNode(TokenTypes.ENUM_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
    }

    @Test
    public void testIsOuterMostTypeClass() {
        assertWithMessage("Should return false when passed is not outer most type")
                .that(ScopeUtil
                        .isOuterMostType(getNode(TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS)))
                .isFalse();
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
        assertWithMessage("Should return false when passed is not variable def")
                .that(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.LITERAL_CATCH)))
                .isFalse();
        assertWithMessage("Should return false when passed is not variable def")
                .that(ScopeUtil
                        .isLocalVariableDef(getNode(TokenTypes.COMMA, TokenTypes.PARAMETER_DEF)))
                .isFalse();
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
        assertWithMessage("invalid result")
                .that(ScopeUtil.isLocalVariableDef(resourceWithIdent))
                .isFalse();
        assertWithMessage("invalid result")
                .that(ScopeUtil.isLocalVariableDef(getNode(TokenTypes.RESOURCE)))
                .isFalse();
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
        assertWithMessage("invalid result")
                .that(ScopeUtil
                        .isLocalVariableDef(getNode(TokenTypes.CLASS_DEF, TokenTypes.VARIABLE_DEF)))
                .isFalse();
    }

    @Test
    public void testIsClassFieldDef() {
        assertWithMessage("Should return true when passed is class field def")
                .that(ScopeUtil.isClassFieldDef(getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK,
                        TokenTypes.VARIABLE_DEF)))
                .isTrue();
        assertWithMessage("Should return false when passed is unexpected")
                .that(ScopeUtil.isClassFieldDef(getNode(TokenTypes.CLASS_DEF)))
                .isFalse();
        assertWithMessage("Should return false when passed is method variable def")
                .that(ScopeUtil.isClassFieldDef(
                        getNode(TokenTypes.METHOD_DEF, TokenTypes.SLIST, TokenTypes.VARIABLE_DEF)))
                .isFalse();
    }

    @Test
    public void testSurroundingScope() {
        final Scope publicScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PUBLIC, "public", TokenTypes.ANNOTATION_DEF));
        assertWithMessage("Invalid surrounding scope")
            .that(publicScope)
            .isEqualTo(Scope.PUBLIC);
        final Scope protectedScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PROTECTED, "protected", TokenTypes.INTERFACE_DEF));
        assertWithMessage("Invalid surrounding scope")
            .that(protectedScope)
            .isEqualTo(Scope.PROTECTED);
        final Scope privateScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_PRIVATE, "private", TokenTypes.ENUM_DEF));
        assertWithMessage("Invalid surrounding scope")
            .that(privateScope)
            .isEqualTo(Scope.PRIVATE);
        final Scope staticScope = ScopeUtil.getSurroundingScope(getNodeWithParentScope(
                TokenTypes.LITERAL_STATIC, "static", TokenTypes.CLASS_DEF));
        assertWithMessage("Invalid surrounding scope")
            .that(staticScope)
            .isEqualTo(Scope.PACKAGE);
    }

    @Test
    public void testIsInScope() {
        assertWithMessage("Should return true when node is in valid scope")
                .that(ScopeUtil.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PUBLIC,
                        "public", TokenTypes.ANNOTATION_DEF), Scope.PUBLIC))
                .isTrue();
        assertWithMessage("Should return false when node is in invalid scope")
                .that(ScopeUtil.isInScope(getNodeWithParentScope(TokenTypes.LITERAL_PROTECTED,
                        "protected", TokenTypes.INTERFACE_DEF), Scope.PRIVATE))
                .isFalse();
    }

    @Test
    public void testSurroundingScopeOfNodeChildOfLiteralNewIsAnoninner() {
        final Scope scope =
                ScopeUtil.getSurroundingScope(getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT));
        assertWithMessage("Invalid surrounding scope")
            .that(scope)
            .isEqualTo(Scope.ANONINNER);
    }

    @Test
    public void testIsInInterfaceBlock() {
        final DetailAST ast = getNode(TokenTypes.INTERFACE_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.CLASS_DEF, TokenTypes.MODIFIERS);

        assertWithMessage("Should return true when node is interface block")
                .that(ScopeUtil.isInInterfaceBlock(ast.getParent()))
                .isTrue();
        assertWithMessage("Should return false when node is not interface block")
                .that(ScopeUtil.isInInterfaceBlock(ast))
                .isFalse();
    }

    @Test
    public void testIsInAnnotationBlock() {
        final DetailAST ast = getNode(TokenTypes.ANNOTATION_DEF, TokenTypes.OBJBLOCK,
                TokenTypes.INTERFACE_DEF, TokenTypes.MODIFIERS);

        assertWithMessage("Should return true when node is annotation block")
                .that(ScopeUtil.isInAnnotationBlock(ast.getParent()))
                .isTrue();
        assertWithMessage("Should return false when node is not annotation block")
                .that(ScopeUtil.isInAnnotationBlock(ast))
                .isFalse();
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
        assertWithMessage("Should return false when node is not in interface or annotation block")
                .that(ScopeUtil.isInInterfaceOrAnnotationBlock(
                        getNode(TokenTypes.CLASS_DEF, TokenTypes.OBJBLOCK)))
                .isFalse();
        assertWithMessage("Should return false when node is not in interface or annotation block")
                .that(ScopeUtil.isInInterfaceOrAnnotationBlock(
                        getNode(TokenTypes.LITERAL_NEW, TokenTypes.IDENT)))
                .isFalse();
        assertWithMessage("Should return false when node is not in interface or annotation block")
                .that(ScopeUtil.isInInterfaceOrAnnotationBlock(
                        getNode(TokenTypes.ENUM_DEF, TokenTypes.OBJBLOCK)))
                .isFalse();
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

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ScopeUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(ScopeUtils.class);
    }

    @Test
    public void testInEnumOnRoot() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        Assert.assertFalse(ScopeUtils.inEnumBlock(ast));
    }

    @Test
    public void testInEnumBlockInNew() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_NEW);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.inEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInInterface() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.INTERFACE_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.inEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInAnnotation() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ANNOTATION_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.inEnumBlock(ast2));
    }

    @Test
    public void testInEnumBlockInClass() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.CLASS_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.inEnumBlock(ast2));
    }

    @Test
    public void testIsOuterMostTypeInterface() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.INTERFACE_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsOuterMostTypeAnnotation() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ANNOTATION_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsOuterMostTypeEnum() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ENUM_DEF);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.MODIFIERS);
        ast.addChild(ast2);

        Assert.assertFalse(ScopeUtils.isOuterMostType(ast2));
    }

    @Test
    public void testIsLocalVariableDefCatch() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_CATCH);
        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.PARAMETER_DEF);
        ast.addChild(ast2);

        Assert.assertTrue(ScopeUtils.isLocalVariableDef(ast2));
    }

    @Test
    public void testIsLocalVariableDefUnexpected() throws ReflectiveOperationException {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_CATCH);

        Assert.assertFalse(ScopeUtils.isLocalVariableDef(ast));
    }
}

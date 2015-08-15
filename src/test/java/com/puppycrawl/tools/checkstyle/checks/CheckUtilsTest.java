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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.TestUtils.assertUtilsClassHasPrivateConstructor;

import java.lang.reflect.InvocationTargetException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CheckUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        try {
            assertUtilsClassHasPrivateConstructor(CheckUtils.class);
            Assert.fail();
        }
        catch (InvocationTargetException ex) {
            Assert.assertTrue(ex.getCause() instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void testParseDouble() throws Exception {
        CheckUtils.parseDouble("1_02", TokenTypes.ASSIGN);
    }

    @Test
    public void testElseWithCurly() throws Exception {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ASSIGN);
        ast.setText("ASSIGN");
        Assert.assertFalse(CheckUtils.isElseIf(ast));

        DetailAST parentAst = new DetailAST();
        parentAst.setType(TokenTypes.LCURLY);
        parentAst.setText("LCURLY");

        DetailAST ifAst = new DetailAST();
        ifAst.setType(TokenTypes.LITERAL_IF);
        ifAst.setText("IF");
        parentAst.addChild(ifAst);

        Assert.assertFalse(CheckUtils.isElseIf(ifAst));

        DetailAST parentAst2 = new DetailAST();
        parentAst2.setType(TokenTypes.SLIST);
        parentAst2.setText("SLIST");

        parentAst2.addChild(ifAst);

        Assert.assertFalse(CheckUtils.isElseIf(ifAst));

        DetailAST elseAst = new DetailAST();
        elseAst.setType(TokenTypes.LITERAL_ELSE);

        elseAst.setFirstChild(ifAst);
        Assert.assertTrue(CheckUtils.isElseIf(ifAst));
    }

    @Test
    public void testEquals() throws Exception {
        DetailAST litStatic = new DetailAST();
        litStatic.setType(TokenTypes.LITERAL_STATIC);

        DetailAST modifiers = new DetailAST();
        modifiers.setType(TokenTypes.MODIFIERS);
        modifiers.addChild(litStatic);

        DetailAST metDef = new DetailAST();
        metDef.setType(TokenTypes.METHOD_DEF);
        metDef.addChild(modifiers);

        Assert.assertFalse(CheckUtils.isEqualsMethod(metDef));

        metDef.removeChildren();

        DetailAST metName = new DetailAST();
        metName.setType(TokenTypes.IDENT);
        metName.setText("equals");
        metDef.addChild(metName);

        DetailAST modifiers2 = new DetailAST();
        modifiers2.setType(TokenTypes.MODIFIERS);
        metDef.addChild(modifiers2);

        DetailAST parameter1 = new DetailAST();
        DetailAST parameter2 = new DetailAST();

        DetailAST parameters = new DetailAST();
        parameters.setType(TokenTypes.PARAMETERS);

        parameters.addChild(parameter2);

        parameters.addChild(parameter1);
        metDef.addChild(parameters);

        Assert.assertFalse(CheckUtils.isEqualsMethod(metDef));

    }

}

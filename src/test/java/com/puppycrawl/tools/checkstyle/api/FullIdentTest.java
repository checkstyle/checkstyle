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

package com.puppycrawl.tools.checkstyle.api;

import org.junit.Assert;
import org.junit.Test;

public class FullIdentTest {

    @Test
    public void testToString() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_NEW);
        ast.setColumnNo(14);
        ast.setLineNo(15);
        ast.setText("MyTest");

        final FullIdent indent = FullIdent.createFullIdent(ast);
        Assert.assertEquals("Invalid full indent", "MyTest[15x14]", indent.toString());
    }

    @Test
    public void testNonValidCoordinatesWithNegative() {
        final FullIdent fullIdent = prepareFullIdentWithCoordinates(14, 15);
        Assert.assertEquals("Invalid full indent", "MyTest.MyTestik[15x14]", fullIdent.toString());
    }

    @Test
    public void testNonValidCoordinatesWithZero() {
        final FullIdent fullIdent = prepareFullIdentWithCoordinates(0, 0);
        Assert.assertEquals("Invalid full indent", "MyTest.MyTestik[15x14]", fullIdent.toString());
    }

    @Test
    public void testWithArrayCreateFullIdentWithArrayDeclare() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.ARRAY_DECLARATOR);
        ast.setColumnNo(18);
        ast.setLineNo(15);
        ast.setText("[");

        final DetailAST ast1 = new DetailAST();
        ast1.setType(TokenTypes.LITERAL_INT);
        ast1.setColumnNo(14);
        ast1.setLineNo(15);
        ast1.setText("int");

        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.RBRACK);
        ast2.setText("]");

        final DetailAST ast3 = new DetailAST();
        ast3.setType(TokenTypes.ARRAY_DECLARATOR);
        ast3.setText("[");

        final DetailAST ast4 = new DetailAST();
        ast4.setType(TokenTypes.RBRACK);
        ast4.setText("]");

        final DetailAST ast5 = new DetailAST();
        ast5.setType(TokenTypes.TYPE);
        ast5.setText("TYPE");

        ast5.addChild(ast3);
        ast.addChild(ast1);
        ast3.addChild(ast);
        ast1.setNextSibling(ast2);
        ast.setNextSibling(ast4);
        final FullIdent indent = FullIdent.createFullIdentWithArrayDeclare(ast1);
        Assert.assertEquals("Invalid full indent", "int[][][15x14]", indent.toString());
    }

    @Test
    public void testWithOutArrayCreateFullIdentWithArrayDeclare() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_INT);
        ast.setColumnNo(14);
        ast.setLineNo(15);
        ast.setText("int");

        final FullIdent indent = FullIdent.createFullIdentWithArrayDeclare(ast);
        Assert.assertEquals("Invalid full indent", "int[15x14]", indent.toString());
    }

    private static FullIdent prepareFullIdentWithCoordinates(int columnNo, int lineNo) {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.DOT);
        ast.setColumnNo(1);
        ast.setLineNo(2);
        ast.setText("Root");

        final DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.LE);
        ast2.setColumnNo(columnNo);
        ast2.setLineNo(lineNo);
        ast2.setText("MyTestik");

        final DetailAST ast1 = new DetailAST();
        ast1.setType(TokenTypes.LITERAL_NEW);
        ast1.setColumnNo(14);
        ast1.setLineNo(15);
        ast1.setText("MyTest");

        ast.addChild(ast1);
        ast.addChild(ast2);

        return FullIdent.createFullIdent(ast);
    }
}

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

package com.puppycrawl.tools.checkstyle.api;

import org.junit.Assert;
import org.junit.Test;

public class FullIdentTest {

    public void testToString() {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.LITERAL_NEW);
        ast.setColumnNo(14);
        ast.setLineNo(15);
        ast.setText("MyTest");

        final FullIdent indent = FullIdent.createFullIdent(ast);
        Assert.assertEquals("MyTest[15x14]", indent.toString());
    }

    @Test
    public void testNonValidCoordinates() {
        DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.DOT);
        ast.setColumnNo(1);
        ast.setLineNo(2);
        ast.setText("Root");

        DetailAST ast2 = new DetailAST();
        ast2.setType(TokenTypes.LE);
        ast2.setColumnNo(-14);
        ast2.setLineNo(-15);
        ast2.setText("MyTestik");

        DetailAST ast1 = new DetailAST();
        ast1.setType(TokenTypes.LITERAL_NEW);
        ast1.setColumnNo(14);
        ast1.setLineNo(15);
        ast1.setText("MyTest");

        ast.addChild(ast1);
        ast.addChild(ast2);

        final FullIdent indent = FullIdent.createFullIdent(ast);
        Assert.assertEquals("MyTest.MyTestik[15x14]", indent.toString());
    }
}

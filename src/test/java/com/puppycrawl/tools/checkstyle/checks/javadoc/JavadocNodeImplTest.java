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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class JavadocNodeImplTest {

    @Test
    public void testToString() {
        JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setType(JavadocTokenTypes.CODE_LITERAL);
        javadocNode.setLineNumber(1);
        javadocNode.setColumnNumber(2);

        String result = javadocNode.toString();

        assertEquals("CODE_LITERAL[1x2]", result);
    }

    @Test
    public void testGetColumnNumber() {
        JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setColumnNumber(1);

        int result = javadocNode.getColumnNumber();

        assertEquals(1, result);
    }
}

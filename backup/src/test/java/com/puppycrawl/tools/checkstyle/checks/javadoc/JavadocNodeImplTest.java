///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;

public class JavadocNodeImplTest {

    @Test
    public void testToString() {
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setType(JavadocTokenTypes.CODE_LITERAL);
        javadocNode.setLineNumber(1);
        javadocNode.setColumnNumber(2);

        final String result = javadocNode.toString();

        assertWithMessage("Invalid toString result")
            .that(result)
            .isEqualTo("JavadocNodeImpl[index=0, type=CODE_LITERAL, text='null', lineNumber=1,"
                + " columnNumber=2, children=0, parent=null]");
    }

    @Test
    public void testGetColumnNumber() {
        final JavadocNodeImpl javadocNode = new JavadocNodeImpl();
        javadocNode.setColumnNumber(1);

        final int result = javadocNode.getColumnNumber();

        assertWithMessage("Invalid column number")
            .that(result)
            .isEqualTo(1);
    }

}

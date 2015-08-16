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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class JavadocTagInfoTest {

    @Test
    public void testAuthor() {
        final DetailAST ast = new DetailAST();

        int[] validTypes = new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.AUTHOR.isValidOn(ast));
        }

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.AUTHOR.isValidOn(ast));
    }

    @Test
    public void testOthers() {
        JavadocTagInfo[] tags = new JavadocTagInfo[] {
            JavadocTagInfo.CODE,
            JavadocTagInfo.DOC_ROOT,
            JavadocTagInfo.LINK,
            JavadocTagInfo.LINKPLAIN,
            JavadocTagInfo.LITERAL,
            JavadocTagInfo.SEE,
            JavadocTagInfo.SINCE,
            JavadocTagInfo.VALUE,
        };
        for (JavadocTagInfo tagInfo : tags) {
            DetailAST astParent = new DetailAST();
            astParent.setType(TokenTypes.LITERAL_CATCH);

            final DetailAST ast = new DetailAST();
            ast.setParent(astParent);

            int[] validTypes = new int[] {
                TokenTypes.PACKAGE_DEF,
                TokenTypes.CLASS_DEF,
                TokenTypes.INTERFACE_DEF,
                TokenTypes.ENUM_DEF,
                TokenTypes.ANNOTATION_DEF,
                TokenTypes.METHOD_DEF,
                TokenTypes.CTOR_DEF,
                TokenTypes.VARIABLE_DEF,
            };
            for (int type: validTypes) {
                ast.setType(type);
                assertTrue(tagInfo.isValidOn(ast));
            }

            astParent.setType(TokenTypes.SLIST);
            ast.setType(TokenTypes.VARIABLE_DEF);
            assertFalse(tagInfo.isValidOn(ast));

            ast.setType(TokenTypes.PARAMETER_DEF);
            assertFalse(tagInfo.isValidOn(ast));
        }
    }

    @Test
    public void testDeprecated() {
        final DetailAST ast = new DetailAST();
        DetailAST astParent = new DetailAST();
        astParent.setType(TokenTypes.LITERAL_CATCH);
        ast.setParent(astParent);

        int[] validTypes = new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.VARIABLE_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.DEPRECATED.isValidOn(ast));
        }

        astParent.setType(TokenTypes.SLIST);
        ast.setType(TokenTypes.VARIABLE_DEF);
        assertFalse(JavadocTagInfo.DEPRECATED.isValidOn(ast));

        ast.setType(TokenTypes.PARAMETER_DEF);
        assertFalse(JavadocTagInfo.DEPRECATED.isValidOn(ast));
    }

    @Test
    public void testSerial() {
        final DetailAST ast = new DetailAST();
        DetailAST astParent = new DetailAST();
        astParent.setType(TokenTypes.LITERAL_CATCH);
        ast.setParent(astParent);

        int[] validTypes = new int[] {
            TokenTypes.VARIABLE_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.SERIAL.isValidOn(ast));
        }

        astParent.setType(TokenTypes.SLIST);
        ast.setType(TokenTypes.VARIABLE_DEF);
        assertFalse(JavadocTagInfo.SERIAL.isValidOn(ast));

        ast.setType(TokenTypes.PARAMETER_DEF);
        assertFalse(JavadocTagInfo.SERIAL.isValidOn(ast));
    }

    @Test
    public void testException() {
        final DetailAST ast = new DetailAST();

        int[] validTypes = new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.EXCEPTION.isValidOn(ast));
        }

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.EXCEPTION.isValidOn(ast));
    }

    @Test
    public void testThrows() {
        final DetailAST ast = new DetailAST();

        int[] validTypes = new int[] {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.THROWS.isValidOn(ast));
        }

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.THROWS.isValidOn(ast));
    }

    @Test
    public void testVersions() {
        final DetailAST ast = new DetailAST();

        int[] validTypes = new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.VERSION.isValidOn(ast));
        }

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.VERSION.isValidOn(ast));
    }

    @Test
    public void testParam() {
        final DetailAST ast = new DetailAST();

        int[] validTypes = new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.PARAM.isValidOn(ast));
        }

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.PARAM.isValidOn(ast));
    }

    @Test
    public void testReturn() {
        final DetailAST ast = new DetailAST();
        final DetailAST astChild = new DetailAST();
        astChild.setType(TokenTypes.TYPE);
        ast.setFirstChild(astChild);
        final DetailAST astChild2 = new DetailAST();
        astChild2.setType(TokenTypes.LITERAL_INT);
        astChild.setFirstChild(astChild2);

        int[] validTypes = new int[] {
            TokenTypes.METHOD_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.RETURN.isValidOn(ast));
        }

        astChild2.setType(TokenTypes.LITERAL_VOID);
        assertFalse(JavadocTagInfo.RETURN.isValidOn(ast));

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.RETURN.isValidOn(ast));
    }

    @Test
    public void testSerialField() {
        final DetailAST ast = new DetailAST();
        final DetailAST astChild = new DetailAST();
        astChild.setType(TokenTypes.TYPE);
        ast.setFirstChild(astChild);
        final DetailAST astChild2 = new DetailAST();
        astChild2.setType(TokenTypes.ARRAY_DECLARATOR);
        astChild2.setText("ObjectStreafield");
        astChild.setFirstChild(astChild2);

        int[] validTypes = new int[] {
            TokenTypes.VARIABLE_DEF,
        };
        for (int type: validTypes) {
            ast.setType(type);
            assertTrue(JavadocTagInfo.SERIAL_FIELD.isValidOn(ast));
        }

        astChild2.setText("1111");
        assertFalse(JavadocTagInfo.SERIAL_FIELD.isValidOn(ast));

        astChild2.setType(TokenTypes.LITERAL_VOID);
        assertFalse(JavadocTagInfo.SERIAL_FIELD.isValidOn(ast));

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.SERIAL_FIELD.isValidOn(ast));
    }

    @Test
    public void testSerialData() {
        final DetailAST ast = new DetailAST();
        ast.setType(TokenTypes.METHOD_DEF);
        final DetailAST astChild = new DetailAST();
        astChild.setType(TokenTypes.IDENT);
        astChild.setText("writeObject");
        ast.setFirstChild(astChild);

        String[] validNames = new String[] {
            "writeObject",
            "readObject",
            "writeExternal",
            "readExternal",
            "writeReplace",
            "readResolve",
        };
        for (String name: validNames) {
            astChild.setText(name);
            assertTrue(JavadocTagInfo.SERIAL_DATA.isValidOn(ast));
        }

        astChild.setText("1111");
        assertFalse(JavadocTagInfo.SERIAL_DATA.isValidOn(ast));

        ast.setType(TokenTypes.LAMBDA);
        assertFalse(JavadocTagInfo.SERIAL_DATA.isValidOn(ast));
    }

    @Test
    public void testCoverage() {
        assertEquals(JavadocTagInfo.Type.BLOCK, JavadocTagInfo.VERSION.getType());

        assertEquals("text [@version] name [version] type [BLOCK]", JavadocTagInfo.VERSION.toString());

        try {
            JavadocTagInfo.fromName(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the name is null", ex.getMessage());
        }

        try {
            JavadocTagInfo.fromName("myname");
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the name [myname] is not a valid Javadoc tag name", ex.getMessage());
        }

        try {
            JavadocTagInfo.fromText(null);
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the text is null", ex.getMessage());
        }

        try {
            JavadocTagInfo.fromText("myname");
            fail();
        }
        catch (IllegalArgumentException ex) {
            assertEquals("the text [myname] is not a valid Javadoc tag text", ex.getMessage());
        }

        assertEquals(JavadocTagInfo.VERSION, JavadocTagInfo.fromText("@version"));
    }
}

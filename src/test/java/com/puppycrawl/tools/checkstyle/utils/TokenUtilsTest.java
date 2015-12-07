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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TokenUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(TokenUtils.class);
    }

    @Test
    public void testGetIntFromAccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getField("MAX_VALUE");

        assertEquals(Integer.MAX_VALUE, TokenUtils.getIntFromField(field, 0));
    }

    @Test
    public void testGetIntFromInaccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getDeclaredField("value");

        try {
            TokenUtils.getIntFromField(field, 0);
            fail();
        }
        catch (IllegalStateException expected) {
            assertTrue(expected.getMessage().startsWith(
                "java.lang.IllegalAccessException: Class"
                + " com.puppycrawl.tools.checkstyle.utils.TokenUtils"
                + " can not access a member of class java.lang.Integer with modifiers "));
        }
    }

    @Test
    public void testTokenValueIncorrect() {
        final Integer id = Integer.MAX_VALUE - 1;
        try {
            TokenUtils.getTokenName(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given id " + id, expected.getMessage());
        }
    }

    @Test
    public void testTokenValueIncorrect2() throws Exception {
        final Integer id = 0;
        String[] originalValue = null;
        Field fieldToken = null;
        try {
            // overwrite static field with new value
            final Field[] fields = TokenUtils.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if ("TOKEN_VALUE_TO_NAME".equals(field.getName())) {
                    fieldToken = field;
                    final Field modifiersField = Field.class.getDeclaredField("modifiers");
                    modifiersField.setAccessible(true);
                    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                    originalValue = (String[]) field.get(null);
                    field.set(null, new String[] {null});
                }
            }

            TokenUtils.getTokenName(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            // restoring original value, to let other tests pass
            fieldToken.set(null, originalValue);

            assertEquals("given id " + id, expected.getMessage());

        }
        catch (IllegalAccessException | NoSuchFieldException ex) {
            fail();
        }
    }

    @Test
    public void testTokenIdIncorrect() {
        final String id = "NON_EXISTING_VALUE";
        try {
            TokenUtils.getTokenId(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testShortDescriptionIncorrect() {
        final String id = "NON_EXISTING_VALUE";
        try {
            TokenUtils.getShortDescription(id);
            fail();
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testIsCommentType() {
        assertTrue(TokenUtils.isCommentType(TokenTypes.SINGLE_LINE_COMMENT));
        assertTrue(TokenUtils.isCommentType(TokenTypes.BLOCK_COMMENT_BEGIN));
        assertTrue(TokenUtils.isCommentType(TokenTypes.BLOCK_COMMENT_END));
        assertTrue(TokenUtils.isCommentType(TokenTypes.COMMENT_CONTENT));
    }

}

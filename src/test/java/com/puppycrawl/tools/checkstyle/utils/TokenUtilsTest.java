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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TokenUtilsTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private",
                isUtilsClassHasPrivateConstructor(TokenUtils.class, true));
    }

    @Test
    public void testGetIntFromAccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getField("MAX_VALUE");

        assertEquals("Invalid getIntFromField result",
                Integer.MAX_VALUE, TokenUtils.getIntFromField(field, 0));
    }

    @Test
    public void testGetIntFromInaccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getDeclaredField("value");

        try {
            TokenUtils.getIntFromField(field, 0);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException expected) {
            assertTrue("Invalid exception message: " + expected.getMessage(),
                    expected.getMessage().startsWith("java.lang.IllegalAccessException: Class"
                + " com.puppycrawl.tools.checkstyle.utils.TokenUtils"
                + " can not access a member of class java.lang.Integer with modifiers "));
        }
    }

    @Test
    public void testTokenValueIncorrect() throws IllegalAccessException {
        int maxId = 0;
        final Field[] fields = TokenTypes.class.getDeclaredFields();
        for (final Field field : fields) {
            // Only process the int declarations.
            if (field.getType() != Integer.TYPE) {
                continue;
            }

            final String name = field.getName();
            final int id = field.getInt(name);
            if (id > maxId) {
                maxId = id;
            }
        }

        final int nextAfterMaxId = maxId + 1;
        try {
            TokenUtils.getTokenName(nextAfterMaxId);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("Invalid exception message",
                    "given id " + nextAfterMaxId, expected.getMessage());
        }
    }

    @Test
    public void testTokenValueCorrect() throws IllegalAccessException {
        final Field[] fields = TokenTypes.class.getDeclaredFields();
        for (final Field field : fields) {
            // Only process the int declarations.
            if (field.getType() != Integer.TYPE) {
                continue;
            }

            final String name = field.getName();
            final int id = field.getInt(name);

            assertEquals("Invalid token name", name, TokenUtils.getTokenName(id));
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
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            // restoring original value, to let other tests pass
            fieldToken.set(null, originalValue);

            assertEquals("Invalid exception message",
                    "given id " + id, expected.getMessage());

        }
    }

    @Test
    public void testTokenIdIncorrect() {
        final String id = "NON_EXISTING_VALUE";
        try {
            TokenUtils.getTokenId(id);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("Invalid exception message",
                    "given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testShortDescriptionIncorrect() {
        final String id = "NON_EXISTING_VALUE";
        try {
            TokenUtils.getShortDescription(id);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("Invalid exception message",
                    "given name " + id, expected.getMessage());
        }
    }

    @Test
    public void testIsCommentType() {
        assertTrue("Should return true when valid type passed",
                TokenUtils.isCommentType(TokenTypes.SINGLE_LINE_COMMENT));
        assertTrue("Should return true when valid type passed",
                TokenUtils.isCommentType(TokenTypes.BLOCK_COMMENT_BEGIN));
        assertTrue("Should return true when valid type passed",
                TokenUtils.isCommentType(TokenTypes.BLOCK_COMMENT_END));
        assertTrue("Should return true when valid type passed",
                TokenUtils.isCommentType(TokenTypes.COMMENT_CONTENT));
        assertTrue("Should return true when valid type passed",
                TokenUtils.isCommentType("COMMENT_CONTENT"));
    }

    @Test
    public void tetsGetTokenTypesTotalNumber() {
        final int tokenTypesTotalNumber = TokenUtils.getTokenTypesTotalNumber();

        assertEquals("Invalid token total number", 169, tokenTypesTotalNumber);
    }

    @Test
    public void testGetAllTokenIds() {
        final int[] allTokenIds = TokenUtils.getAllTokenIds();
        final int sum = Arrays.stream(allTokenIds).sum();

        assertEquals("Invalid token length", 169, allTokenIds.length);
        assertEquals("invalid sum", 15662, sum);
    }

    @Test
    public void testGetTokenNameWithGreatestPossibleId() {
        final Integer id = TokenTypes.COMMENT_CONTENT;
        final String tokenName = TokenUtils.getTokenName(id);

        assertEquals("Invalid token name", "COMMENT_CONTENT", tokenName);
    }

    @Test
    public void testCorrectBehaviourOfGetTokenId() {
        final String id = "EOF";

        assertEquals("Invalid token id", TokenTypes.EOF, TokenUtils.getTokenId(id));

    }

    @Test
    public void testCorrectBehaviourOfShortDescription() {
        final String id = "EOF";
        final String shortDescription = TokenUtils.getShortDescription(id);

        assertEquals("Invalid short description", "The end of file token.", shortDescription);
    }

    @Test
    public void testFindFirstTokenByPredicate() {
        final DetailAST astForTest = new DetailAST();
        final DetailAST child = new DetailAST();
        final DetailAST firstSibling = new DetailAST();
        final DetailAST secondSibling = new DetailAST();
        final DetailAST thirdSibling = new DetailAST();
        firstSibling.setText("first");
        secondSibling.setText("second");
        thirdSibling.setText("third");
        secondSibling.setNextSibling(thirdSibling);
        firstSibling.setNextSibling(secondSibling);
        child.setNextSibling(firstSibling);
        astForTest.setFirstChild(child);
        final Optional<DetailAST> result = TokenUtils.findFirstTokenByPredicate(astForTest,
            ast -> "second".equals(ast.getText()));

        assertEquals("Invalid second sibling", secondSibling, result.get());
    }
}

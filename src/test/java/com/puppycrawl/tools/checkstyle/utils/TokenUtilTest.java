////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class TokenUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(TokenUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testGetIntFromAccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getField("MAX_VALUE");
        final int maxValue = TokenUtil.getIntFromField(field, 0);

        assertEquals(Integer.MAX_VALUE, maxValue, "Invalid getIntFromField result");
    }

    @Test
    public void testGetIntFromInaccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getDeclaredField("value");

        try {
            TokenUtil.getIntFromField(field, 0);
            fail("IllegalStateException is expected");
        }
        catch (IllegalStateException expected) {
            // The exception message may vary depending on the version of the JDK.
            // It will definitely contain the TokenUtil class name and the Integer class name.
            final String message = expected.getMessage();
            assertTrue(message.startsWith("java.lang.IllegalAccessException: ")
                        && message.contains("com.puppycrawl.tools.checkstyle.utils.TokenUtil")
                        && message.contains("access a member of class java.lang.Integer"),
                    "Invalid exception message: " + message);
        }
    }

    @Test
    public void testNameToValueMapFromPublicIntFields() {
        final Map<String, Integer> actualMap =
            TokenUtil.nameToValueMapFromPublicIntFields(Integer.class);
        final Map<String, Integer> expectedMap = new TreeMap<>();
        expectedMap.put("BYTES", Integer.BYTES);
        expectedMap.put("SIZE", Integer.SIZE);
        expectedMap.put("MAX_VALUE", Integer.MAX_VALUE);
        expectedMap.put("MIN_VALUE", Integer.MIN_VALUE);

        assertEquals(expectedMap, actualMap, "Unexpected name to value map");
    }

    @Test
    public void testValueToNameArrayFromNameToValueMap() {
        final Map<String, Integer> map = new TreeMap<>();
        map.put("ZERO", 0);
        map.put("ONE", 1);
        map.put("TWO", 2);
        map.put("NEGATIVE", -1);

        final String[] actualArray =
            TokenUtil.valueToNameArrayFromNameToValueMap(map);
        final String[] expectedArray = {"ZERO", "ONE", "TWO"};

        assertArrayEquals(expectedArray, actualArray, "Unexpected value to name array");
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
            TokenUtil.getTokenName(nextAfterMaxId);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expectedException) {
            assertEquals("given id " + nextAfterMaxId, expectedException.getMessage(),
                    "Invalid exception message");
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

            assertEquals(name, TokenUtil.getTokenName(id), "Invalid token name");
        }
    }

    @Test
    public void testTokenValueIncorrect2() {
        final int id = 0;
        try {
            TokenUtil.getTokenName(id);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given id " + id, expected.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testTokenIdIncorrect() {
        final String id = "NON_EXISTENT_VALUE";
        try {
            TokenUtil.getTokenId(id);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testShortDescriptionIncorrect() {
        final String id = "NON_EXISTENT_VALUE";
        try {
            TokenUtil.getShortDescription(id);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException expected) {
            assertEquals("given name " + id, expected.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testIsCommentType() {
        assertTrue(TokenUtil.isCommentType(TokenTypes.SINGLE_LINE_COMMENT),
                "Should return true when valid type passed");
        assertTrue(TokenUtil.isCommentType(TokenTypes.BLOCK_COMMENT_BEGIN),
                "Should return true when valid type passed");
        assertTrue(TokenUtil.isCommentType(TokenTypes.BLOCK_COMMENT_END),
                "Should return true when valid type passed");
        assertTrue(TokenUtil.isCommentType(TokenTypes.COMMENT_CONTENT),
                "Should return true when valid type passed");
        assertTrue(TokenUtil.isCommentType("COMMENT_CONTENT"),
                "Should return true when valid type passed");
    }

    @Test
    public void testGetTokenTypesTotalNumber() {
        final int tokenTypesTotalNumber = TokenUtil.getTokenTypesTotalNumber();

        assertEquals(169, tokenTypesTotalNumber, "Invalid token total number");
    }

    @Test
    public void testGetAllTokenIds() {
        final int[] allTokenIds = TokenUtil.getAllTokenIds();
        final int sum = Arrays.stream(allTokenIds).sum();

        assertEquals(169, allTokenIds.length, "Invalid token length");
        assertEquals(15662, sum, "invalid sum");
    }

    @Test
    public void testGetTokenNameWithGreatestPossibleId() {
        final int id = TokenTypes.COMMENT_CONTENT;
        final String tokenName = TokenUtil.getTokenName(id);

        assertEquals("COMMENT_CONTENT", tokenName, "Invalid token name");
    }

    @Test
    public void testCorrectBehaviourOfGetTokenId() {
        final String id = "EOF";

        assertEquals(TokenTypes.EOF, TokenUtil.getTokenId(id), "Invalid token id");
    }

    @Test
    public void testCorrectBehaviourOfShortDescription() {
        final String id = "EOF";
        final String shortDescription = TokenUtil.getShortDescription(id);

        assertEquals("The end of file token.", shortDescription, "Invalid short description");
    }

    @Test
    public void testFindFirstTokenByPredicate() {
        final DetailAstImpl astForTest = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl firstSibling = new DetailAstImpl();
        final DetailAstImpl secondSibling = new DetailAstImpl();
        final DetailAstImpl thirdSibling = new DetailAstImpl();
        firstSibling.setText("first");
        secondSibling.setText("second");
        thirdSibling.setText("third");
        secondSibling.setNextSibling(thirdSibling);
        firstSibling.setNextSibling(secondSibling);
        child.setNextSibling(firstSibling);
        astForTest.setFirstChild(child);
        final Optional<DetailAST> result = TokenUtil.findFirstTokenByPredicate(astForTest,
            ast -> "second".equals(ast.getText()));

        assertEquals(secondSibling, result.orElse(null), "Invalid second sibling");
    }

    @Test
    public void testForEachChild() {
        final DetailAstImpl astForTest = new DetailAstImpl();
        final DetailAstImpl child = new DetailAstImpl();
        final DetailAstImpl firstSibling = new DetailAstImpl();
        final DetailAstImpl secondSibling = new DetailAstImpl();
        final DetailAstImpl thirdSibling = new DetailAstImpl();
        firstSibling.setType(TokenTypes.DOT);
        secondSibling.setType(TokenTypes.CLASS_DEF);
        thirdSibling.setType(TokenTypes.IDENT);
        secondSibling.setNextSibling(thirdSibling);
        firstSibling.setNextSibling(secondSibling);
        child.setNextSibling(firstSibling);
        astForTest.setFirstChild(child);
        final List<DetailAST> children = new ArrayList<>();
        TokenUtil.forEachChild(astForTest, TokenTypes.CLASS_DEF, children::add);
        final DetailAST firstChild = children.get(0);

        assertEquals(1, children.size(), "Must be one match");
        assertEquals(secondSibling, firstChild, "Mismatched child node");
    }

}

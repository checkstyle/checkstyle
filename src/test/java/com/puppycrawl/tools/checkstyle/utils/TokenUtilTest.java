///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.utils;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

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
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(TokenUtil.class))
                .isTrue();
    }

    @Test
    public void testGetIntFromAccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getField("MAX_VALUE");
        final int maxValue = TokenUtil.getIntFromField(field, 0);

        assertWithMessage("Invalid getIntFromField result")
            .that(maxValue)
            .isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void testGetIntFromInaccessibleField() throws NoSuchFieldException {
        final Field field = Integer.class.getDeclaredField("value");

        try {
            TokenUtil.getIntFromField(field, 0);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException expected) {
            // The exception message may vary depending on the version of the JDK.
            // It will definitely contain the TokenUtil class name and the Integer class name.
            final String message = expected.getMessage();
            assertWithMessage("Invalid exception message: " + message)
                    .that(message.startsWith("java.lang.IllegalAccessException: ")
                            && message.contains("com.puppycrawl.tools.checkstyle.utils.TokenUtil")
                            && message.contains("access a member of class java.lang.Integer"))
                    .isTrue();
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

        assertWithMessage("Unexpected name to value map")
            .that(actualMap)
            .isEqualTo(expectedMap);
    }

    @Test
    public void testInvertMap() {
        final Map<String, Integer> map = new TreeMap<>();
        map.put("ZERO", 0);
        map.put("ONE", 1);
        map.put("TWO", 2);
        map.put("NEGATIVE", -1);

        final Map<Integer, String> invertedMap = TokenUtil.invertMap(map);

        assertWithMessage("Key set of 'map' and values of 'invertedMap' should be the same.")
                .that(invertedMap.values())
                .containsExactlyElementsIn(map.keySet());
        assertWithMessage("Values of 'map' and key set of 'invertedMap' should be the same.")
                .that(invertedMap.keySet())
                .containsExactlyElementsIn(map.values());
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
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException expectedException) {
            assertWithMessage("Invalid exception message")
                .that(expectedException.getMessage())
                .isEqualTo("unknown TokenTypes id '" + nextAfterMaxId + "'");
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

            assertWithMessage("Invalid token name")
                .that(TokenUtil.getTokenName(id))
                .isEqualTo(name);
        }
    }

    @Test
    public void testTokenValueIncorrect2() {
        final int id = 0;
        try {
            TokenUtil.getTokenName(id);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException expected) {
            assertWithMessage("Invalid exception message")
                .that(expected.getMessage())
                .isEqualTo("unknown TokenTypes id '" + id + "'");
        }
    }

    @Test
    public void testTokenIdIncorrect() {
        final String id = "NON_EXISTENT_VALUE";
        try {
            TokenUtil.getTokenId(id);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException expected) {
            assertWithMessage("Invalid exception message")
                .that(expected.getMessage())
                .isEqualTo("unknown TokenTypes value '" + id + "'");
        }
    }

    @Test
    public void testShortDescriptionIncorrect() {
        final String id = "NON_EXISTENT_VALUE";
        try {
            TokenUtil.getShortDescription(id);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException expected) {
            assertWithMessage("Invalid exception message")
                .that(expected.getMessage())
                .isEqualTo("unknown TokenTypes value '" + id + "'");
        }
    }

    @Test
    public void testIsCommentType() {
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isCommentType(TokenTypes.SINGLE_LINE_COMMENT))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isCommentType(TokenTypes.BLOCK_COMMENT_BEGIN))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isCommentType(TokenTypes.BLOCK_COMMENT_END))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isCommentType(TokenTypes.COMMENT_CONTENT))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isCommentType("COMMENT_CONTENT"))
                .isTrue();
        assertWithMessage("Should return false when invalid type passed")
                .that(TokenUtil.isCommentType(TokenTypes.CLASS_DEF))
                .isFalse();
        assertWithMessage("Should return false when invalid type passed")
                .that(TokenUtil.isCommentType("CLASS_DEF"))
                .isFalse();
    }

    @Test
    public void testGetTokenTypesTotalNumber() {
        final int tokenTypesTotalNumber = TokenUtil.getTokenTypesTotalNumber();

        assertWithMessage("Invalid token total number")
            .that(tokenTypesTotalNumber)
            .isEqualTo(189);
    }

    @Test
    public void testGetAllTokenIds() {
        final int[] allTokenIds = TokenUtil.getAllTokenIds();
        final int sum = Arrays.stream(allTokenIds).sum();

        assertWithMessage("Invalid token length")
            .that(allTokenIds.length)
            .isEqualTo(189);
        assertWithMessage("invalid sum")
            .that(sum)
            .isEqualTo(19820);
    }

    @Test
    public void testGetTokenNameWithGreatestPossibleId() {
        final int id = TokenTypes.COMMENT_CONTENT;
        final String tokenName = TokenUtil.getTokenName(id);

        assertWithMessage("Invalid token name")
            .that(tokenName)
            .isEqualTo("COMMENT_CONTENT");
    }

    @Test
    public void testCorrectBehaviourOfGetTokenId() {
        final String id = "COMPILATION_UNIT";

        assertWithMessage("Invalid token id")
            .that(TokenUtil.getTokenId(id))
            .isEqualTo(TokenTypes.COMPILATION_UNIT);
    }

    @Test
    public void testCorrectBehaviourOfShortDescription() {
        final String id = "COMPILATION_UNIT";
        final String shortDescription = TokenUtil.getShortDescription(id);

        assertWithMessage("Invalid short description")
            .that(shortDescription)
            .isEqualTo("This is the root node for the source file.");
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

        assertWithMessage("Invalid second sibling")
            .that(result.orElse(null))
            .isEqualTo(secondSibling);
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

        assertWithMessage("Must be one match")
            .that(children)
            .hasSize(1);
        assertWithMessage("Mismatched child node")
            .that(firstChild)
            .isEqualTo(secondSibling);
    }

    @Test
    public void testIsTypeDeclaration() {
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isTypeDeclaration(TokenTypes.CLASS_DEF))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isTypeDeclaration(TokenTypes.INTERFACE_DEF))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isTypeDeclaration(TokenTypes.ANNOTATION_DEF))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isTypeDeclaration(TokenTypes.ENUM_DEF))
                .isTrue();
        assertWithMessage("Should return true when valid type passed")
                .that(TokenUtil.isTypeDeclaration(TokenTypes.RECORD_DEF))
                .isTrue();
    }

    @Test
    public void testIsOfTypeTrue() {
        final int type = TokenTypes.LITERAL_CATCH;
        final DetailAstImpl astForTest = new DetailAstImpl();
        astForTest.setType(type);
        final boolean result1 = TokenUtil.isOfType(type, TokenTypes.LITERAL_FOR,
                                TokenTypes.LITERAL_IF, TokenTypes.LITERAL_CATCH);
        final boolean result2 = TokenUtil.isOfType(astForTest, TokenTypes.LITERAL_FOR,
                                TokenTypes.LITERAL_IF, TokenTypes.LITERAL_CATCH);

        assertWithMessage("Token type did not match")
                .that(result1)
                .isTrue();
        assertWithMessage("Token type did not match")
                .that(result2)
                .isTrue();
    }

    @Test
    public void testIsOfTypeFalse() {
        final int type = TokenTypes.LITERAL_CATCH;
        final DetailAstImpl astForTest1 = new DetailAstImpl();
        final DetailAstImpl astForTest2 = null;
        astForTest1.setType(type);
        final boolean result1 = TokenUtil.isOfType(type, TokenTypes.LITERAL_FOR,
                                TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE);
        final boolean result2 = TokenUtil.isOfType(astForTest1, TokenTypes.LITERAL_FOR,
                                TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE);
        final boolean result3 = TokenUtil.isOfType(astForTest2, TokenTypes.LITERAL_FOR,
                                TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE);

        assertWithMessage("Token type should not match")
                .that(result1)
                .isFalse();
        assertWithMessage("Token type should not match")
                .that(result2)
                .isFalse();
        assertWithMessage("Token type should not match")
                .that(result3)
                .isFalse();
    }

    @Test
    public void testIsBooleanLiteralType() {
        assertWithMessage("Result is not expected")
                .that(TokenUtil.isBooleanLiteralType(TokenTypes.LITERAL_TRUE))
                .isTrue();
        assertWithMessage("Result is not expected")
                .that(TokenUtil.isBooleanLiteralType(TokenTypes.LITERAL_FALSE))
                .isTrue();
        assertWithMessage("Result is not expected")
                .that(TokenUtil.isBooleanLiteralType(TokenTypes.LOR))
                .isFalse();
    }

}

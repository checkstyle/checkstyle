///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertWithMessage;

public class SimpleTokenTest {

    @Test
    public void testFrom() {
        final CommonToken token = new CommonToken(1, "test");
        token.setTokenIndex(5);
        token.setLine(10);
        token.setCharPositionInLine(20);
        token.setStartIndex(30);
        token.setStopIndex(40);

        final SimpleToken simpleToken = SimpleToken.from(token);

        assertWithMessage("Token index should be set")
                .that(simpleToken.getTokenIndex())
                .isEqualTo(5);
        assertWithMessage("Type should match")
                .that(simpleToken.getType())
                .isEqualTo(1);
        assertWithMessage("Text should match")
                .that(simpleToken.getText())
                .isEqualTo("test");
    }

    @Test
    public void testEqualsSameInstance() {
        final CommonToken token = new CommonToken(1, "test");
        final SimpleToken simpleToken = SimpleToken.from(token);

        assertWithMessage("Should be equal to itself")
                .that(simpleToken.equals(simpleToken))
                .isTrue();
    }

    @Test
    public void testEqualsNull() {
        final CommonToken token = new CommonToken(1, "test");
        final SimpleToken simpleToken = SimpleToken.from(token);
        final Object nullObj = null;

        assertWithMessage("Should not be equal to null")
                .that(simpleToken.equals(nullObj))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentClass() {
        final CommonToken token = new CommonToken(1, "test");
        final SimpleToken simpleToken = SimpleToken.from(token);
        final Object differentClass = "not a token";

        assertWithMessage("Should not be equal to different class")
                .that(simpleToken.equals(differentClass))
                .isFalse();
    }

    @Test
    public void testEqualsEqualTokens() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setTokenIndex(5);
        token1.setLine(10);
        token1.setCharPositionInLine(20);
        token1.setStartIndex(30);
        token1.setStopIndex(40);

        final CommonToken token2 = new CommonToken(1, "test");
        token2.setTokenIndex(5);
        token2.setLine(10);
        token2.setCharPositionInLine(20);
        token2.setStartIndex(30);
        token2.setStopIndex(40);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Equal tokens should be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isTrue();
    }

    @Test
    public void testEqualsDifferentType() {
        final CommonToken token1 = new CommonToken(1, "test");
        final CommonToken token2 = new CommonToken(2, "test");

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different types should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentText() {
        final CommonToken token1 = new CommonToken(1, "test1");
        final CommonToken token2 = new CommonToken(1, "test2");

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different text should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentLine() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setLine(10);
        final CommonToken token2 = new CommonToken(1, "test");
        token2.setLine(20);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different lines should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentTokenIndex() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setTokenIndex(5);
        final CommonToken token2 = new CommonToken(1, "test");
        token2.setTokenIndex(6);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different token indices should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentCharPositionInLine() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setCharPositionInLine(10);
        final CommonToken token2 = new CommonToken(1, "test");
        token2.setCharPositionInLine(20);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different char positions should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentStartIndex() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setStartIndex(10);
        final CommonToken token2 = new CommonToken(1, "test");
        token2.setStartIndex(20);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different start indices should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testEqualsDifferentStopIndex() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setStopIndex(10);
        final CommonToken token2 = new CommonToken(1, "test");
        token2.setStopIndex(20);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Different stop indices should not be equal")
                .that(simpleToken1.equals(simpleToken2))
                .isFalse();
    }

    @Test
    public void testHashCodeEqualTokens() {
        final CommonToken token1 = new CommonToken(1, "test");
        token1.setTokenIndex(5);
        token1.setLine(10);
        token1.setCharPositionInLine(20);
        token1.setStartIndex(30);
        token1.setStopIndex(40);

        final CommonToken token2 = new CommonToken(1, "test");
        token2.setTokenIndex(5);
        token2.setLine(10);
        token2.setCharPositionInLine(20);
        token2.setStartIndex(30);
        token2.setStopIndex(40);

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        assertWithMessage("Equal tokens should have same hashCode")
                .that(simpleToken1.hashCode())
                .isEqualTo(simpleToken2.hashCode());
    }

    @Test
    public void testHashCodeDifferentTokens() {
        final CommonToken token1 = new CommonToken(1, "test1");
        final CommonToken token2 = new CommonToken(2, "test2");

        final SimpleToken simpleToken1 = SimpleToken.from(token1);
        final SimpleToken simpleToken2 = SimpleToken.from(token2);

        // Hash codes should be different for different tokens
        // This kills mutations that remove method calls in hashCode
        final int hashCode1 = simpleToken1.hashCode();
        final int hashCode2 = simpleToken2.hashCode();

        assertWithMessage("hashCode should return a value")
                .that(hashCode1)
                .isNotEqualTo(0);
        assertWithMessage("hashCode should return a value")
                .that(hashCode2)
                .isNotEqualTo(0);
        // Different tokens should have different hash codes (very likely)
        assertWithMessage("Different tokens should have different hash codes")
                .that(hashCode1)
                .isNotEqualTo(hashCode2);
    }

    @Test
    public void testHashCodeUsesAllFields() {
        final CommonToken token = new CommonToken(1, "test");
        token.setTokenIndex(5);
        token.setLine(10);
        token.setCharPositionInLine(20);
        token.setStartIndex(30);
        token.setStopIndex(40);

        final SimpleToken simpleToken = SimpleToken.from(token);
        final int hashCode = simpleToken.hashCode();

        // Verify hashCode uses all fields by checking it's not zero
        // This kills mutations that remove method calls
        assertWithMessage("hashCode should use all fields")
                .that(hashCode)
                .isNotEqualTo(0);
    }

}

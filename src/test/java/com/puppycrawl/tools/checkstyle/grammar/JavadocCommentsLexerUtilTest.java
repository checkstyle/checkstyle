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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;

public class JavadocCommentsLexerUtilTest {

    @Test
    public void testIsProperUtilsClass() {
        try {
            assertWithMessage("Constructor is not private")
                    .that(isUtilsClassHasPrivateConstructor(JavadocCommentsLexerUtil.class))
                    .isTrue();
        }
        catch (ReflectiveOperationException exc) {
            // The constructor throws IllegalStateException which is wrapped in InvocationTargetException
            // This is expected behavior for utility classes
            assertWithMessage("Exception should be IllegalStateException wrapped in InvocationTargetException")
                    .that(exc.getCause())
                    .isInstanceOf(IllegalStateException.class);
            assertWithMessage("Constructor is not private")
                    .that(exc.getCause().getMessage())
                    .isEqualTo("Utility class");
        }
    }

    @Test
    public void testIsOpenTagNameWithNull() {
        final boolean result = JavadocCommentsLexerUtil.isOpenTagName(null);

        assertWithMessage("Null token should be considered open tag name")
                .that(result)
                .isTrue();
    }

    @Test
    public void testIsOpenTagNameWithNonSlashToken() {
        final CommonToken token = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        final boolean result = JavadocCommentsLexerUtil.isOpenTagName(token);

        assertWithMessage("Non-slash token should be considered open tag name")
                .that(result)
                .isTrue();
    }

    @Test
    public void testIsOpenTagNameWithSlashToken() {
        final CommonToken token = new CommonToken(JavadocCommentsTokenTypes.TAG_SLASH, "/");
        final boolean result = JavadocCommentsLexerUtil.isOpenTagName(token);

        assertWithMessage("Slash token should not be considered open tag name")
                .that(result)
                .isFalse();
    }

    @Test
    public void testGetUnclosedTagNameTokensWithEmptyDeques() {
        final Deque<Token> openTags = new ArrayDeque<>();
        final Deque<Token> closeTags = new ArrayDeque<>();

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Empty deques should return empty set")
                .that(result)
                .isEmpty();
    }

    @Test
    public void testGetUnclosedTagNameTokensWithUnclosedOpenTag() {
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag.setTokenIndex(1);
        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Unclosed open tag should be in result")
                .that(result)
                .hasSize(1);
        assertWithMessage("Result should contain the unclosed tag")
                .that(result.iterator().next().getText())
                .isEqualTo("p");
    }

    @Test
    public void testGetUnclosedTagNameTokensWithMatchingTags() {
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag.setTokenIndex(1);
        final CommonToken closeTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        closeTag.setTokenIndex(2);

        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();
        closeTags.add(closeTag);

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Matching tags should result in empty set")
                .that(result)
                .isEmpty();
    }

    @Test
    public void testGetUnclosedTagNameTokensWithCaseInsensitiveMatching() {
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "P");
        openTag.setTokenIndex(1);
        final CommonToken closeTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        closeTag.setTokenIndex(2);

        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();
        closeTags.add(closeTag);

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Case-insensitive matching tags should result in empty set")
                .that(result)
                .isEmpty();
    }

    @Test
    public void testGetUnclosedTagNameTokensWithNonMatchingTags() {
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag.setTokenIndex(1);
        final CommonToken closeTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "div");
        closeTag.setTokenIndex(2);

        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();
        closeTags.add(closeTag);

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Non-matching tags should leave unclosed tag")
                .that(result)
                .hasSize(1);
        assertWithMessage("Result should contain the unclosed tag")
                .that(result.iterator().next().getText())
                .isEqualTo("p");
    }

    @Test
    public void testGetUnclosedTagNameTokensWithBoundaryCondition() {
        // Test the boundary condition: openingTag.getTokenIndex() < closingTag.getTokenIndex()
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag.setTokenIndex(1);
        final CommonToken closeTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        closeTag.setTokenIndex(1); // Same index - should not match

        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();
        closeTags.add(closeTag);

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Tags with same index should not match")
                .that(result)
                .hasSize(1);
    }

    @Test
    public void testGetUnclosedTagNameTokensWithMultipleTags() {
        final CommonToken openTag1 = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag1.setTokenIndex(1);
        final CommonToken openTag2 = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "div");
        openTag2.setTokenIndex(2);
        final CommonToken closeTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        closeTag.setTokenIndex(3);

        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag1);
        openTags.add(openTag2);
        final Deque<Token> closeTags = new ArrayDeque<>();
        closeTags.add(closeTag);

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        assertWithMessage("Should have one unclosed tag")
                .that(result)
                .hasSize(1);
        assertWithMessage("Unclosed tag should be div")
                .that(result.iterator().next().getText())
                .isEqualTo("div");
    }

    @Test
    public void testGetUnclosedTagNameTokensWithEmptyCheck() {
        // Test that isEmpty() is called - kills NonVoidMethodCallMutator mutation
        final CommonToken openTag = new CommonToken(JavadocCommentsTokenTypes.TAG_NAME, "p");
        openTag.setTokenIndex(1);
        final Deque<Token> openTags = new ArrayDeque<>();
        openTags.add(openTag);
        final Deque<Token> closeTags = new ArrayDeque<>();

        final Set<SimpleToken> result =
                JavadocCommentsLexerUtil.getUnclosedTagNameTokens(openTags, closeTags);

        // This test ensures isEmpty() is called in the while loop
        assertWithMessage("Result should not be empty")
                .that(result)
                .isNotEmpty();
    }

}


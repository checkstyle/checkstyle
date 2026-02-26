///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.Token;

import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;

/**
 * Utility class for Javadoc comments lexer operations.
 */
public final class JavadocCommentsLexerUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException if this constructor is called, indicating that
     */
    private JavadocCommentsLexerUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Finds unclosed tag name tokens by comparing open and close tag name tokens.
     *
     * <p>
     * This method attempts to match each closing tag with the most recent
     * unmatched opening tag of the same name, considering only tags that appear
     * before it in the token stream. Any remaining unmatched opening tags are
     * considered unclosed and returned.
     * </p>
     *
     * <p>
     * <b>Note:</b> This method must be called after lexing is complete to ensure
     * that all tokens have their index values set.
     * </p>
     *
     * @param openTagNameTokens  a deque of {@link Token} instances representing open tag names
     * @param closeTagNameTokens a deque of {@link Token} instances representing close tag names
     * @return a set of {@link SimpleToken} instances representing unclosed tag names
     */
    public static Set<SimpleToken> getUnclosedTagNameTokens(
            Deque<Token> openTagNameTokens, Deque<Token> closeTagNameTokens) {

        final Deque<Token> unmatchedOpen = new ArrayDeque<>(openTagNameTokens);

        for (Token closingTag : closeTagNameTokens) {
            final Deque<Token> tempStack = new ArrayDeque<>();
            while (!unmatchedOpen.isEmpty()) {
                final Token openingTag = unmatchedOpen.pop();
                if (openingTag.getText().equalsIgnoreCase(closingTag.getText())
                        && openingTag.getTokenIndex() < closingTag.getTokenIndex()) {
                    break;
                }
                tempStack.push(openingTag);
            }

            // Put unmatched tags back
            while (!tempStack.isEmpty()) {
                unmatchedOpen.push(tempStack.pop());
            }

        }

        // We cannot map to SimpleToken until lexing has completed, otherwise
        // the token index will not be set.
        return unmatchedOpen.stream()
                .map(SimpleToken::from)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Checks if the previous token is an open tag name.
     *
     * @param previousToken the previous token to check
     * @return true if the previous token is null or not a closing tag, false otherwise
     */
    public static boolean isOpenTagName(Token previousToken) {
        return previousToken == null
                || previousToken.getType() != JavadocCommentsTokenTypes.TAG_SLASH;
    }

}

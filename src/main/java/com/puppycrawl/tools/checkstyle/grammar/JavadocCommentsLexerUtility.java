package com.puppycrawl.tools.checkstyle.grammar;

import com.puppycrawl.tools.checkstyle.grammar.javadoc.JavadocCommentsParser;
import org.antlr.v4.runtime.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for Javadoc comments lexer operations.
 */
public class JavadocCommentsLexerUtility {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavadocCommentsLexerUtility() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * Finds unclosed tag name tokens by comparing open and close tag name tokens.
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
            boolean matched = false;
            while (!unmatchedOpen.isEmpty()) {
                Token openingTag = unmatchedOpen.pop();
                if (openingTag.getText().equalsIgnoreCase(closingTag.getText())
                        && openingTag.getTokenIndex() < closingTag.getTokenIndex()) {
                    matched = true;
                    break;
                } else {
                    tempStack.push(openingTag);
                }
            }

            // Put unmatched tags back
            while (!tempStack.isEmpty()) {
                unmatchedOpen.push(tempStack.pop());
            }

            if (!matched) {
                // might need this, let's see how this works out
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
        return previousToken == null || previousToken.getType() != JavadocCommentsParser.TAG_SLASH;
    }

}

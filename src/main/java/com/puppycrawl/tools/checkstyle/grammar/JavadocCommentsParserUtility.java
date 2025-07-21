package com.puppycrawl.tools.checkstyle.grammar;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;

import java.util.Set;


/**
* Utility class for Javadoc comments parser operations.
*/
public class JavadocCommentsParserUtility {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JavadocCommentsParserUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks if the next token in the input stream is a non-tight tag.
     *
     * @param _input the input token stream
     * @param unclosedTagNameTokens a set of unclosed tag name tokens
     * @return true if the next token is a non-tight tag, false otherwise
     */
    public static boolean isNonTightTag(TokenStream _input, Set<SimpleToken> unclosedTagNameTokens) {
        final Token lookahead = SimpleToken.from(_input.LT(2));
        return unclosedTagNameTokens.contains(lookahead);
    }
}

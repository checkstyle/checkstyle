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

import java.util.Set;

import org.antlr.v4.runtime.TokenStream;

/**
 * Utility class for Javadoc comments parser operations.
 */
public final class JavadocCommentsParserUtil {

    /**
     * Private constructor to prevent instantiation of this utility class.
     *
     * @throws IllegalStateException if this constructor is called
     */
    private JavadocCommentsParserUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Checks if the next token in the input stream is a non-tight tag.
     *
     * @param input the input token stream
     * @param unclosedTagNameTokens a set of unclosed tag name tokens
     * @return true if the next token is a non-tight tag, false otherwise
     */
    public static boolean isNonTightTag(
            TokenStream input, Set<SimpleToken> unclosedTagNameTokens) {
        final SimpleToken lookahead = SimpleToken.from(input.LT(2));
        return unclosedTagNameTokens.contains(lookahead);
    }
}

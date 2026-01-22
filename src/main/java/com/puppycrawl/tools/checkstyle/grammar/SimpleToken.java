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

import java.io.Serial;
import java.util.Objects;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

/**
 * A simple wrapper for ANTLR {@link Token} that provides a proper {@link #equals(Object)}
 * and {@link #hashCode()} implementation based on the token's {@code type} and {@code text}.
 *
 * <p>This is useful because ANTLR's default {@code CommonToken} does not override
 * {@code equals}, It compares references.
 */
public final class SimpleToken extends CommonToken {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new instance from an existing ANTLR {@link Token}.
     *
     * @param token the ANTLR token to wrap
     */
    private SimpleToken(
            Token token) {
        super(token);
        setTokenIndex(token.getTokenIndex());
    }

    /**
     * Creates a new instance from an existing ANTLR {@link Token}.
     *
     * @param token the ANTLR token to wrap
     * @return a new instance of SimpleToken wrapping the provided token
     */
    public static SimpleToken from(Token token) {
        return new SimpleToken(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SimpleToken other = (SimpleToken) obj;
        return getType() == other.getType()
                && getText().equals(other.getText())
                && getLine() == other.getLine()
                && getTokenIndex() == other.getTokenIndex()
                && getCharPositionInLine() == other.getCharPositionInLine()
                && getStartIndex() == other.getStartIndex()
                && getStopIndex() == other.getStopIndex();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getType(),
                getText(),
                getLine(),
                getTokenIndex(),
                getCharPositionInLine(),
                getStartIndex(),
                getStopIndex()
        );
    }
}

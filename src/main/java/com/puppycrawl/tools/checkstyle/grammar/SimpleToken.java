package com.puppycrawl.tools.checkstyle.grammar;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;


/**
 * A simple wrapper for ANTLR {@link Token} that provides a proper {@link #equals(Object)}
 * and {@link #hashCode()} implementation based on the token's {@code type} and {@code text}.
 *
 * <p>This is useful because ANTLR's default {@code CommonToken} does not override
 * {@code equals}, It compares references.
 *
 */
public class SimpleToken extends CommonToken {

    /**
     * Constructs a new instance of {@link SimpleToken} from an existing ANTLR {@link Token}.
     *
     * @param token the ANTLR token to wrap
     */
    private SimpleToken(
            Token token) {
        super(token);
        this.index = token.getTokenIndex();
    }

    /**
     * Creates a new instance of {@link SimpleToken} from an existing ANTLR {@link Token}.
     * @param token the ANTLR token to wrap
     * @return a new instance of {@link SimpleToken} wrapping the provided token
     */
    public static SimpleToken from(Token token) {
        return new SimpleToken(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final SimpleToken other = (SimpleToken) obj;
        return this.getType() == other.getType()
                && this.getText().equals(other.getText())
                && this.getLine() == other.getLine()
                && this.getTokenIndex() == other.getTokenIndex()
                && this.getCharPositionInLine() == other.getCharPositionInLine()
                && this.start == other.getStartIndex()
                && this.stop == other.getStopIndex();
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(getType());
        result = 31 * result + getText().hashCode();
        result = 31 * result + Integer.hashCode(getLine());
        result = 31 * result + Integer.hashCode(getTokenIndex());
        result = 31 * result + Integer.hashCode(getCharPositionInLine());
        result = 31 * result + Integer.hashCode(start);
        result = 31 * result + Integer.hashCode(stop);
        return result;
    }
}

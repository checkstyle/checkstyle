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

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

/**
 * Extends the LexerATNSimulator class in order to override
 * the 'consume()' method so that we can handle '\r' line
 * endings (pre-OSX macOS line endings) correctly in the ANTLR lexer.
 */
public class CrAwareLexerSimulator extends LexerATNSimulator {

    /**
     * Constructs a CrAwareLexerSimulator to interpret the input from the lexer.
     *
     * @param lexer the current lexer
     * @param augmented the augmented transition network
     * @param decisionToDfa the DFA to store our states in
     * @param sharedContextCache cache of PredictionContext objects
     */
    public CrAwareLexerSimulator(Lexer lexer, ATN augmented,
                                 DFA[] decisionToDfa,
                                 PredictionContextCache sharedContextCache) {
        super(lexer, augmented, decisionToDfa, sharedContextCache);
    }

    /**
     * Overrides the 'consume()' method to add support for '\r' (carriage return) line endings.
     * A line break which is translated from a Unicode escape does not start a new line,
     * lines are counted in the original text.
     *
     * @param input the Character stream of the file we are parsing
     */
    @Override
    public void consume(CharStream input) {
        final int currentChar = input.LA(1);
        final int width = columnWidth(input, 0);
        if (width == 1 && currentChar == '\n') {
            line++;
            charPositionInLine = 0;
        }
        else if (width == 1 && currentChar == '\r') {
            final boolean crlf = input.LA(2) == '\n' && columnWidth(input, 1) == 1;
            if (!crlf) {
                line++;
                charPositionInLine = 0;
            }
        }
        else {
            charPositionInLine += width;
        }
        input.consume();
    }

    /**
     * Number of columns the character at the given offset from the current position occupies:
     * the width of its original spelling when Unicode escapes are translated, otherwise one.
     *
     * @param input the Character stream of the file we are parsing
     * @param offset offset from the current position
     * @return the number of columns
     */
    private static int columnWidth(CharStream input, int offset) {
        int width = 1;
        if (input instanceof final UnicodeEscapeDecodingCharStream decoding) {
            width = decoding.getRawWidth(decoding.index() + offset);
        }
        return width;
    }

}

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

import static com.google.common.truth.Truth.assertWithMessage;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.junit.jupiter.api.Test;

public class CrAwareLexerTest {

    @Test
    public void testConsumeCarriageReturnZeroCharPositionInLine() {
        final String text = "\r";
        final CharStream charStream = CharStreams.fromString(text);
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);

        assertWithMessage("Carriage return should reset column number.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(0);
    }

    @Test
    public void testConsumeCarriageReturnNewline() {
        final String text = "\r";
        final CharStream charStream = CharStreams.fromString(text);
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);

        assertWithMessage("Carriage return should increment line number.")
                .that(lexer.getLine())
                .isEqualTo(2);
    }

    @Test
    public void testConsumeWindowsNewlineZeroCharPositionInLine() {
        final String text = "\r\n";
        final CharStream charStream = CharStreams.fromString(text);
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);
        lexer.consume(charStream);

        assertWithMessage("Carriage return should reset column number.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(0);
    }

    @Test
    public void testConsumeWindowsNewline() {
        final String text = "\r\n";
        final CharStream charStream = CharStreams.fromString(text);
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);
        lexer.consume(charStream);

        assertWithMessage("Carriage return should increment line number.")
                .that(lexer.getLine())
                .isEqualTo(2);
    }

    @Test
    public void testConsumeColumnWidthDecodingStream() {
        final CharStream charStream = new UnicodeEscapeDecodingCharStream("\\u0041b");
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);

        assertWithMessage("Translated escape should occupy the width of its spelling.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(6);

        lexer.consume(charStream);

        assertWithMessage("Plain character after an escape should occupy one column.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(7);
    }

    @Test
    public void testConsumeEscapedLineBreaksDoNotStartNewLine() {
        final CharStream charStream =
                new UnicodeEscapeDecodingCharStream("\\u000D\\u000A\\u000Ab");
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        for (int index = 0; index < 3; index++) {
            lexer.consume(charStream);
        }

        assertWithMessage("Escaped line breaks should not increment line number.")
                .that(lexer.getLine())
                .isEqualTo(1);
        assertWithMessage("Escaped line breaks should advance the column by their width.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(18);
    }

    @Test
    public void testConsumeRawCarriageReturnBeforeEscapedLineFeed() {
        final CharStream charStream = new UnicodeEscapeDecodingCharStream("\r\\u000A");
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);

        assertWithMessage("Raw carriage return should increment line number.")
                .that(lexer.getLine())
                .isEqualTo(2);
    }

    @Test
    public void testConsumeColumnWidthPlainStream() {
        final CharStream charStream = CharStreams.fromString("\\u0041");
        final CrAwareLexerSimulator lexer =
                new CrAwareLexerSimulator(null, null, null, null);
        lexer.consume(charStream);

        assertWithMessage("Plain stream should occupy one column per character.")
                .that(lexer.getCharPositionInLine())
                .isEqualTo(1);
    }

}

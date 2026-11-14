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
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.misc.Interval;
import org.junit.jupiter.api.Test;

public class UnicodeEscapeDecodingCharStreamTest {

    private static String chars(int... codePoints) {
        return new String(codePoints, 0, codePoints.length);
    }

    private static String decode(String text) {
        final UnicodeEscapeDecodingCharStream stream = new UnicodeEscapeDecodingCharStream(text);
        return stream.getDecodedText(Interval.of(0, stream.size() - 1));
    }

    private static void assertDecoded(String text, String expected) {
        assertWithMessage("Decoded text of '%s'", text)
                .that(decode(text))
                .isEqualTo(expected);
    }

    @Test
    public void testSimpleEscape() {
        assertDecoded("\\u0061", "a");
        assertDecoded("x\\u0061y", "xay");
        assertDecoded("\\u00e9\\u00E9", chars(0xE9, 0xE9));
    }

    @Test
    public void testHexDigitBoundaries() {
        assertDecoded("\\u0000", "\u0000");
        assertDecoded("\\u0009", "\t");
        assertDecoded("\\u004a", "J");
        assertDecoded("\\u004A", "J");
        assertDecoded("\\u00fF", chars(0xFF));
        assertDecoded("\\u00aA", chars(0xAA));
    }

    @Test
    public void testExtendedEscape() {
        assertDecoded("\\uuu0061", "a");
        assertDecoded("\\uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu0061", "a");
    }

    @Test
    public void testEscapedBackslashIsNotEscape() {
        assertDecoded("\\\\u0061", "\\\\u0061");
        assertDecoded("\\\\\\\\u0061", "\\\\\\\\u0061");
    }

    @Test
    public void testBackslashAfterEvenBackslashesIsEscape() {
        assertDecoded("\\\\\\u0061", "\\\\a");
        assertDecoded("\\\\\\\\\\u0061", "\\\\\\\\a");
    }

    @Test
    public void testBackslashBeforeNonEscape() {
        assertDecoded("\\n\\u0061", "\\na");
        assertDecoded("\\u", "\\u");
        assertDecoded("\\", "\\");
        assertDecoded("\\0061", "\\0061");
        assertDecoded("\\1234\\u0061", "\\1234a");
    }

    @Test
    public void testLetterAfterNoBackslashIsNotEscape() {
        assertDecoded("xu0061", "xu0061");
        assertDecoded("uu0061", "uu0061");
        assertDecoded("a\\u0062u0063", "abu0063");
    }

    @Test
    public void testProducedBackslashIsNotReprocessed() {
        assertDecoded("\\u005cu0061", "\\u0061");
        assertDecoded("\\u005c\\u005c", "\\\\");
        assertDecoded("\\u005c\\u0061", "\\a");
        assertDecoded("\\u005c\\\\u0061", "\\\\\\u0061");
    }

    @Test
    public void testSurrogatePairEscapes() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("\\ud83d\\ude00x");
        assertWithMessage("Pair of escapes is one code point")
                .that(stream.size())
                .isEqualTo(2);
        assertWithMessage("Code point")
                .that(stream.LA(1))
                .isEqualTo(0x1_F600);
        assertWithMessage("Width of the pair")
                .that(stream.getRawWidth(0))
                .isEqualTo(12);
        assertWithMessage("Raw text of the pair")
                .that(stream.getText(Interval.of(0, 0)))
                .isEqualTo("\\ud83d\\ude00");
        assertWithMessage("Next character")
                .that(stream.LA(2))
                .isEqualTo('x');
    }

    @Test
    public void testSurrogatePairWithExtendedEscapes() {
        assertDecoded("\\uuD83D\\uuuDE00", "\uD83D\uDE00");
    }

    @Test
    public void testLoneSurrogates() {
        final UnicodeEscapeDecodingCharStream high = new UnicodeEscapeDecodingCharStream("\\ud800");
        assertWithMessage("Lone high surrogate is kept")
                .that(high.LA(1))
                .isEqualTo(0xD800);
        assertWithMessage("Lone high surrogate is one character")
                .that(high.size())
                .isEqualTo(1);
        final UnicodeEscapeDecodingCharStream low = new UnicodeEscapeDecodingCharStream("\\udc00");
        assertWithMessage("Lone low surrogate is kept")
                .that(low.LA(1))
                .isEqualTo(0xDC00);
        assertDecoded("\\ud800a", chars(0xD800, 0x61));
        assertDecoded("\\ud800\\u0061", chars(0xD800, 0x61));
        assertDecoded("\\ud800\\ud800", chars(0xD800, 0xD800));
        assertDecoded("\\u0061\\udc00", chars(0x61, 0xDC00));
        assertDecoded("\\ud800\\" + "u00G1", (char) 0xD800 + "\\" + "u00G1");
    }

    @Test
    public void testMalformedEscape() {
        assertDecoded("\\u00G1", "\\u00G1");
        assertDecoded("\\u00g1", "\\u00g1");
        assertDecoded("\\u00 1", "\\u00 1");
        final String arabicDigits = chars(0x661, 0x661);
        assertDecoded("\\u00" + arabicDigits, "\\u00" + arabicDigits);
        assertDecoded("\\u00G1\\u0061", "\\u00G1a");
        assertDecoded("\\u:000", "\\u:000");
        assertDecoded("\\u/000", "\\u/000");
        assertDecoded("\\u@000", "\\u@000");
        assertDecoded("\\u`000", "\\u`000");
        assertDecoded("\\u{000", "\\u{000");
        final String delete = chars(0x7F);
        assertDecoded("\\u" + delete + "000", "\\u" + delete + "000");
    }

    @Test
    public void testEscapeCutByEndOfInput() {
        assertDecoded("\\u006", "\\u006");
        assertDecoded("\\u00", "\\u00");
        assertDecoded("a\\uuu", "a\\uuu");
        assertDecoded("a\\u006", "a\\u006");
    }

    @Test
    public void testEscapeAtEndOfInput() {
        assertDecoded("a\\u0061", "aa");
    }

    @Test
    public void testLineTerminators() {
        assertDecoded("a\r\nb", "a\r\nb");
        assertDecoded("a\rb", "a\rb");
        assertDecoded("a\\u000d\\u000ab", "a\r\nb");
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("a\r\n\\u000ab");
        assertWithMessage("Plain CR is one character")
                .that(stream.getRawWidth(1))
                .isEqualTo(1);
        assertWithMessage("Plain LF is one character")
                .that(stream.getRawWidth(2))
                .isEqualTo(1);
        assertWithMessage("Escaped LF is six characters")
                .that(stream.getRawWidth(3))
                .isEqualTo(6);
    }

    @Test
    public void testWithoutEscapes() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("abc");
        assertWithMessage("Size")
                .that(stream.size())
                .isEqualTo(3);
        assertWithMessage("Raw text is the same")
                .that(stream.getText(Interval.of(0, 2)))
                .isEqualTo("abc");
        assertWithMessage("Width")
                .that(stream.getRawWidth(2))
                .isEqualTo(1);
    }

    @Test
    public void testEmptyText() {
        final UnicodeEscapeDecodingCharStream stream = new UnicodeEscapeDecodingCharStream("");
        assertWithMessage("Size")
                .that(stream.size())
                .isEqualTo(0);
        assertWithMessage("EOF")
                .that(stream.LA(1))
                .isEqualTo(IntStream.EOF);
        assertWithMessage("Text")
                .that(stream.toString())
                .isEmpty();
    }

    @Test
    public void testSupplementaryCharactersAreOneCodePoint() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("\uD83D\uDE00\\u0061\uD83D\uDE00");
        assertWithMessage("Size in code points")
                .that(stream.size())
                .isEqualTo(3);
        assertWithMessage("Width of a supplementary character")
                .that(stream.getRawWidth(0))
                .isEqualTo(1);
        assertWithMessage("Raw text after an escape")
                .that(stream.getText(Interval.of(2, 2)))
                .isEqualTo("\uD83D\uDE00");
        assertWithMessage("Raw text of everything")
                .that(stream.getText(Interval.of(0, 2)))
                .isEqualTo("\uD83D\uDE00\\u0061\uD83D\uDE00");
    }

    @Test
    public void testRawTextAroundEscapes() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("ab\\u0063d\\uu0065f");
        assertWithMessage("Decoded")
                .that(stream.toString())
                .isEqualTo("abcdef");
        assertWithMessage("Before the escape")
                .that(stream.getText(Interval.of(0, 1)))
                .isEqualTo("ab");
        assertWithMessage("The escape")
                .that(stream.getText(Interval.of(2, 2)))
                .isEqualTo("\\u0063");
        assertWithMessage("After the escape")
                .that(stream.getText(Interval.of(3, 3)))
                .isEqualTo("d");
        assertWithMessage("From plain through escape")
                .that(stream.getText(Interval.of(1, 4)))
                .isEqualTo("b\\u0063d\\uu0065");
        assertWithMessage("The second escape")
                .that(stream.getText(Interval.of(4, 4)))
                .isEqualTo("\\uu0065");
        assertWithMessage("The tail")
                .that(stream.getText(Interval.of(5, 5)))
                .isEqualTo("f");
        assertWithMessage("Width of the second escape")
                .that(stream.getRawWidth(4))
                .isEqualTo(7);
        assertWithMessage("Width of the tail")
                .that(stream.getRawWidth(5))
                .isEqualTo(1);
    }

    @Test
    public void testIntervalIsClamped() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("a\\u0062");
        assertWithMessage("Raw start is clamped")
                .that(stream.getText(Interval.of(-5, 0)))
                .isEqualTo("a");
        assertWithMessage("Raw stop is clamped")
                .that(stream.getText(Interval.of(1, 10)))
                .isEqualTo("\\u0062");
        assertWithMessage("Raw empty interval")
                .that(stream.getText(Interval.of(1, 0)))
                .isEmpty();
        assertWithMessage("Raw interval after the end")
                .that(stream.getText(Interval.of(2, 3)))
                .isEmpty();
        assertWithMessage("Decoded start is clamped")
                .that(stream.getDecodedText(Interval.of(-5, 0)))
                .isEqualTo("a");
        assertWithMessage("Decoded stop is clamped")
                .that(stream.getDecodedText(Interval.of(1, 10)))
                .isEqualTo("b");
        assertWithMessage("Decoded empty interval")
                .that(stream.getDecodedText(Interval.of(1, 0)))
                .isEmpty();
        assertWithMessage("Raw interval with start far after stop")
                .that(stream.getText(Interval.of(5, 0)))
                .isEmpty();
        assertWithMessage("Decoded interval with start far after stop")
                .that(stream.getDecodedText(Interval.of(5, 0)))
                .isEmpty();
    }

    @Test
    public void testLookAhead() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("a\\u0062");
        assertWithMessage("LA(0)")
                .that(stream.LA(0))
                .isEqualTo(0);
        assertWithMessage("LA(1)")
                .that(stream.LA(1))
                .isEqualTo('a');
        assertWithMessage("LA(2)")
                .that(stream.LA(2))
                .isEqualTo('b');
        assertWithMessage("LA(3)")
                .that(stream.LA(3))
                .isEqualTo(IntStream.EOF);
        assertWithMessage("LA(-1) at the start")
                .that(stream.LA(-1))
                .isEqualTo(IntStream.EOF);
        stream.consume();
        assertWithMessage("LA(-1)")
                .that(stream.LA(-1))
                .isEqualTo('a');
        assertWithMessage("LA(-2) before the start")
                .that(stream.LA(-2))
                .isEqualTo(IntStream.EOF);
    }

    @Test
    public void testConsumeSeekAndIndex() {
        final UnicodeEscapeDecodingCharStream stream =
                new UnicodeEscapeDecodingCharStream("a\\u0062");
        assertWithMessage("Start index")
                .that(stream.index())
                .isEqualTo(0);
        stream.consume();
        stream.consume();
        assertWithMessage("Index at the end")
                .that(stream.index())
                .isEqualTo(2);
        assertWithMessage("LA at the end")
                .that(stream.LA(1))
                .isEqualTo(IntStream.EOF);
        getExpectedThrowable(IllegalStateException.class, stream::consume);
        stream.seek(1);
        assertWithMessage("Index after seek")
                .that(stream.index())
                .isEqualTo(1);
        assertWithMessage("LA after seek")
                .that(stream.LA(1))
                .isEqualTo('b');
    }

    @Test
    public void testMarkAndMetadata() {
        final UnicodeEscapeDecodingCharStream stream = new UnicodeEscapeDecodingCharStream("a");
        final int marker = stream.mark();
        assertWithMessage("Marker")
                .that(marker)
                .isEqualTo(-1);
        stream.release(marker);
        assertWithMessage("Source name")
                .that(stream.getSourceName())
                .isEqualTo(IntStream.UNKNOWN_SOURCE_NAME);
        assertWithMessage("toString")
                .that(stream.toString())
                .isEqualTo("a");
    }

}

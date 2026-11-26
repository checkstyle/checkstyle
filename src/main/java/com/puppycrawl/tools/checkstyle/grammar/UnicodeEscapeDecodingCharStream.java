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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.IntStream;
import org.antlr.v4.runtime.misc.Interval;

/**
 * A {@link CharStream} that translates Unicode escapes ({@code \}, one or more {@code u}
 * and four hexadecimal digits) before the lexer sees the characters, as described in
 * <a href="https://docs.oracle.com/javase/specs/jls/se21/html/jls-3.html#jls-3.3">JLS 3.3</a>.
 *
 * <p>
 * The stream is indexed by code points of the <b>translated</b> text. The original
 * spelling is kept, so that callers can ask for the raw text of any interval
 * ({@link #getText(Interval)}), the translated text ({@link #getDecodedText(Interval)})
 * and the number of original characters that a translated character occupies
 * ({@link #getRawWidth(int)}).
 * Only the escapes are recorded (sparse, sorted by position), not every character.
 * </p>
 *
 * <p>
 * Rules implemented:
 * </p>
 * <ul>
 *   <li>a backslash starts an escape only if it is preceded by an even number of
 *   contiguous backslashes;</li>
 *   <li>a character produced by an escape never takes part in another escape, so a produced
 *   backslash neither starts an escape nor counts as a preceding backslash;</li>
 *   <li>two consecutive escapes that form a surrogate pair give one code point;</li>
 *   <li>a malformed escape, or an escape cut by the end of input, is left as is.</li>
 * </ul>
 *
 * @noinspection FieldNotUsedInToString
 * @noinspectionreason FieldNotUsedInToString - The string form is the translated text only,
 *      as the other fields are internal bookkeeping for position mapping.
 */
public final class UnicodeEscapeDecodingCharStream implements CharStream {

    /** Marker for "no escape here". */
    private static final int NOT_ESCAPE = -1;

    /** Number of hexadecimal digits in an escape. */
    private static final int HEX_DIGITS = 4;

    /** Radix of the digits of an escape. */
    private static final int HEX_RADIX = 16;

    /** Position of the original length in an escape entry. */
    private static final int ESCAPE_LENGTH = 2;

    /** Position of the translated code point in an escape entry. */
    private static final int ESCAPE_VALUE = 3;

    /** Original text, as code points. */
    private final int[] rawCodePoints;

    /** Translated text, as code points. */
    private final int[] decoded;

    /** Translated index of each escape, ascending. */
    private final int[] escapeDecodedIndexes;

    /** Index in the original text of each escape. */
    private final int[] escapeRawStarts;

    /** Length in the original text of each escape. */
    private final int[] escapeRawLengths;

    /** Current index in the translated text. */
    private int position;

    /**
     * Translates the escapes of the given text.
     *
     * @param text the original text
     */
    public UnicodeEscapeDecodingCharStream(String text) {
        rawCodePoints = text.codePoints().toArray();
        final int[] buffer = new int[rawCodePoints.length];
        final List<int[]> escapes = new ArrayList<>();
        final int size = decode(buffer, escapes);
        decoded = Arrays.copyOf(buffer, size);
        escapeDecodedIndexes = escapes.stream().mapToInt(escape -> escape[0]).toArray();
        escapeRawStarts = escapes.stream().mapToInt(escape -> escape[1]).toArray();
        escapeRawLengths = escapes.stream().mapToInt(escape -> escape[2]).toArray();
    }

    /**
     * Translates the original text into the buffer and collects the escapes found.
     *
     * @param buffer receives the translated code points
     * @param escapes receives one entry per escape: translated index, original index,
     *     original length and the translated code point
     * @return number of translated code points
     */
    private int decode(int[] buffer, List<int[]> escapes) {
        int size = 0;
        int rawIndex = 0;
        boolean eligible = true;
        while (rawIndex < rawCodePoints.length) {
            final int end = eligibleEscapeEnd(rawIndex, eligible);
            if (end == NOT_ESCAPE) {
                final int current = rawCodePoints[rawIndex];
                buffer[size] = current;
                eligible = current != '\\' || !eligible;
                rawIndex++;
            }
            else {
                final int[] escape = readEscape(size, rawIndex, end);
                escapes.add(escape);
                final int value = escape[ESCAPE_VALUE];
                buffer[size] = value;
                rawIndex += escape[ESCAPE_LENGTH];
                eligible = true;
            }
            size++;
        }
        return size;
    }

    /**
     * Reads the escape which ends at the given index, together with the escape that
     * follows it when the two form a surrogate pair.
     *
     * @param decodedIndex index of the escape in the translated text
     * @param start index of the escape in the original text
     * @param end index after the escape in the original text
     * @return translated index, original index, original length and translated code point
     * @noinspection NumericCastThatLosesPrecision
     * @noinspectionreason NumericCastThatLosesPrecision - escape values are UTF-16 code units
     *      (at most 0xFFFF), so the casts to char never lose data.
     */
    private int[] readEscape(int decodedIndex, int start, int end) {
        int value = escapeValue(end);
        int stop = end;
        final int next = escapeEnd(end);
        if (next != NOT_ESCAPE && Character.isHighSurrogate((char) value)
                && Character.isLowSurrogate((char) escapeValue(next))) {
            value = Character.toCodePoint((char) value, (char) escapeValue(next));
            stop = next;
        }
        return new int[] {decodedIndex, start, stop - start, value};
    }

    /**
     * Finds an escape which starts at the given index of the original text.
     *
     * @param start index in the original text
     * @param eligible whether a backslash at this index is preceded by an even number
     *     of backslashes
     * @return index after the escape, or {@link #NOT_ESCAPE}
     */
    private int eligibleEscapeEnd(int start, boolean eligible) {
        int result = NOT_ESCAPE;
        if (eligible) {
            result = escapeEnd(start);
        }
        return result;
    }

    /**
     * Returns the number of original characters (code points) which the translated
     * character at the given index occupies: 1 for a plain character, the length of
     * the escape (at least 6) for a translated one.
     *
     * @param index index in the translated text, from 0 to {@code size() - 1}
     * @return the width in the original text
     */
    public int getRawWidth(int index) {
        final int escape = Arrays.binarySearch(escapeDecodedIndexes, index);
        final int result;
        if (escape < 0) {
            result = 1;
        }
        else {
            result = escapeRawLengths[escape];
        }
        return result;
    }

    /**
     * Returns the translated text of the interval.
     *
     * @param interval interval in the translated text, inclusive
     * @return the translated text, the interval is clamped to the stream
     */
    public String getDecodedText(Interval interval) {
        final int start = Math.max(interval.a, 0);
        final int stop = Math.min(interval.b, decoded.length - 1);
        final String result;
        if (start > stop) {
            result = "";
        }
        else {
            result = new String(decoded, start, stop - start + 1);
        }
        return result;
    }

    /**
     * Returns the original text of the interval, escapes as they were written.
     *
     * @param interval interval in the translated text, inclusive
     * @return the original text, the interval is clamped to the stream
     */
    @Override
    public String getText(Interval interval) {
        final int start = Math.max(interval.a, 0);
        final int stop = Math.min(interval.b, decoded.length - 1);
        final String result;
        if (start > stop) {
            result = "";
        }
        else {
            final int rawStart = rawIndexOf(start);
            result = new String(rawCodePoints, rawStart, rawIndexOf(stop + 1) - rawStart);
        }
        return result;
    }

    @Override
    public void consume() {
        if (position >= decoded.length) {
            throw new IllegalStateException("cannot consume EOF");
        }
        position++;
    }

    @Override
    public int LA(int offset) {
        return switch (Integer.signum(offset)) {
            case -1 -> codePointAt(position + offset);
            case 1 -> codePointAt(position + offset - 1);
            default -> 0;
        };
    }

    /**
     * Translated code point at the given index.
     *
     * @param index index in the translated text
     * @return the code point, or {@link IntStream#EOF} if the index is outside the text
     */
    private int codePointAt(int index) {
        final int result;
        if (index < 0 || index >= decoded.length) {
            result = EOF;
        }
        else {
            result = decoded[index];
        }
        return result;
    }

    @Override
    public int mark() {
        return -1;
    }

    @Override
    public void release(int marker) {
        // nothing to release
    }

    @Override
    public int index() {
        return position;
    }

    @Override
    public void seek(int index) {
        position = index;
    }

    @Override
    public int size() {
        return decoded.length;
    }

    @Override
    public String getSourceName() {
        return UNKNOWN_SOURCE_NAME;
    }

    @Override
    public String toString() {
        return new String(decoded, 0, decoded.length);
    }

    /**
     * Index in the original text of the translated character at the given index.
     * The index equal to the size gives the length of the original text.
     *
     * @param index index in the translated text
     * @return index in the original text
     */
    private int rawIndexOf(int index) {
        final int before = lastEscapeBefore(index);
        final int result;
        if (before < 0) {
            result = index;
        }
        else {
            result = escapeRawStarts[before] + escapeRawLengths[before]
                    + index - escapeDecodedIndexes[before] - 1;
        }
        return result;
    }

    /**
     * Finds the last escape which starts before the given index of the translated text.
     *
     * @param index index in the translated text
     * @return position of the escape in the escape arrays, or -1 if there is none
     */
    private int lastEscapeBefore(int index) {
        final int found = Arrays.binarySearch(escapeDecodedIndexes, index);
        // found is the escape at the index, or -(insertion point) - 1 when there is none
        return Math.max(found, -found - 1) - 1;
    }

    /**
     * Finds an escape which starts at the given index of the original text.
     * The caller is responsible for the backslash being eligible.
     *
     * @param start index in the original text
     * @return index after the escape, or {@link #NOT_ESCAPE}
     */
    private int escapeEnd(int start) {
        int result = NOT_ESCAPE;
        if (start < rawCodePoints.length && rawCodePoints[start] == '\\') {
            int digits = start + 1;
            while (digits < rawCodePoints.length && rawCodePoints[digits] == 'u') {
                digits++;
            }
            if (digits > start + 1 && hasHexDigits(digits)) {
                result = digits + HEX_DIGITS;
            }
        }
        return result;
    }

    /**
     * Checks that four ASCII hexadecimal digits are at the given index.
     *
     * @param start index in the original text
     * @return true if they are
     */
    private boolean hasHexDigits(int start) {
        boolean result = start + HEX_DIGITS <= rawCodePoints.length;
        for (int index = start; result && index < start + HEX_DIGITS; index++) {
            result = isHexDigit(rawCodePoints[index]);
        }
        return result;
    }

    /**
     * Checks for an ASCII hexadecimal digit, the other digits accepted by
     * {@link Character#digit(int, int)}, like Arabic-Indic ones, are not allowed in an escape.
     *
     * @param codePoint character to check
     * @return true if it is {@code 0-9}, {@code a-f} or {@code A-F}
     */
    private static boolean isHexDigit(int codePoint) {
        return codePoint >= '0' && codePoint <= '9'
                || codePoint >= 'a' && codePoint <= 'f'
                || codePoint >= 'A' && codePoint <= 'F';
    }

    /**
     * Value of the escape that ends at the given index.
     *
     * @param end index after the escape in the original text
     * @return the UTF-16 code unit
     */
    private int escapeValue(int end) {
        int result = 0;
        for (int index = end - HEX_DIGITS; index < end; index++) {
            result = result * HEX_RADIX + Character.digit(rawCodePoints[index], HEX_RADIX);
        }
        return result;
    }

}

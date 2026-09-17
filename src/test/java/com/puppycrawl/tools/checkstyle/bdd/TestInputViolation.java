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

package com.puppycrawl.tools.checkstyle.bdd;

import java.util.Objects;

/**
 * Represents a test input violation with line number and message.
 *
 * @param lineNo parsed violation line number
 * @param message parsed violation message
 */
public record TestInputViolation(int lineNo, String message)
        implements Comparable<TestInputViolation> {

    /** Legacy getter for line number (backward compatibility). */
    public int getLineNo() {
        return lineNo;
    }

    /** Legacy getter for message (backward compatibility). */
    public String getMessage() {
        return message;
    }

    /**
     * Creates regex string to match the violation message format.
     *
     * @return the regex string
     */
    public String toRegex() {
        String regex = lineNo + ":(?:\\d+:)?\\s.*";
        if (message != null) {
            regex += escapeSegment(message) + ".*";
        }
        return regex;
    }

    /**
     * Escapes standard BDD violation special characters in a message segment.
     *
     * @param segment the segment to escape
     * @return the escaped segment
     */
    private static String escapeSegment(String segment) {
        final StringBuilder result = new StringBuilder(segment.length());
        boolean inQuote = false;
        int index = 0;
        while (index < segment.length()) {
            if (isQuoteStart(segment, index)) {
                inQuote = true;
                result.append("\\Q");
                index += 2;
            }
            else if (isQuoteEnd(segment, index)) {
                inQuote = false;
                result.append("\\E");
                index += 2;
            }
            else {
                final char character = segment.charAt(index);
                if (!inQuote && isSpecialChar(character)) {
                    result.append('\\');
                }
                result.append(character);
                index++;
            }
        }
        return result.toString();
    }

    /**
     * Checks if the segment at the given index is the start of a quote block.
     *
     * @param segment the segment
     * @param index the index
     * @return true if it is the start of a quote block
     */
    private static boolean isQuoteStart(String segment, int index) {
        return index < segment.length() - 1
                && segment.charAt(index) == '\\'
                && segment.charAt(index + 1) == 'Q';
    }

    /**
     * Checks if the segment at the given index is the end of a quote block.
     *
     * @param segment the segment
     * @param index the index
     * @return true if it is the end of a quote block
     */
    private static boolean isQuoteEnd(String segment, int index) {
        return index < segment.length() - 1
                && segment.charAt(index) == '\\'
                && segment.charAt(index + 1) == 'E';
    }

    /**
     * Checks if the character is a special regex character that needs escaping.
     *
     * @param character the character
     * @return true if the character is a special character
     */
    private static boolean isSpecialChar(char character) {
        return character == '{'
                || character == '('
                || character == ')'
                || character == '['
                || character == ']';
    }

    @Override
    public int compareTo(TestInputViolation other) {
        final int result;
        if (message != null && lineNo == other.lineNo) {
            result = message.compareTo(other.message);
        }
        else {
            result = Integer.compare(lineNo, other.lineNo);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNo);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof TestInputViolation violation
            && compareTo(violation) == 0;
    }

}

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
import java.util.regex.Pattern;

/**
 * Represents a test input violation with line number and message.
 *
 * @param lineNo parsed violation line number
 * @param message parsed violation message
 */
public record TestInputViolation(int lineNo, String message)
        implements Comparable<TestInputViolation> {

    /** Pattern to match the symbol: "{". */
    private static final Pattern OPEN_CURLY_PATTERN = Pattern.compile("\\{");

    /** Pattern to match the symbol: "(". */
    private static final Pattern OPEN_PAREN_PATTERN = Pattern.compile("\\(");

    /** Pattern to match the symbol: ")". */
    private static final Pattern CLOSE_PAREN_PATTERN = Pattern.compile("\\)");

    /** Pattern to match the symbol: "[". */
    private static final Pattern OPEN_BRACKET_PATTERN = Pattern.compile("\\[");

    /** Pattern to match the symbol: "]". */
    private static final Pattern CLOSE_BRACKET_PATTERN = Pattern.compile("\\]");

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
            final String rawMessage = message;
            final String[] segments = rawMessage.split("\\\\Q|\\\\E", -1);
            final StringBuilder builder = new StringBuilder();
            for (int index = 0; index < segments.length; index++) {
                if (index > 0) {
                    if (index % 2 == 1) {
                        builder.append("\\Q");
                    }
                    else {
                        builder.append("\\E");
                    }
                }
                if (index % 2 == 0) {
                    builder.append(escapeSegment(segments[index]));
                }
                else {
                    builder.append(segments[index]);
                }
            }
            regex += builder.toString() + ".*";
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
        String result = segment;
        result = OPEN_CURLY_PATTERN.matcher(result).replaceAll("\\\\{");
        result = OPEN_PAREN_PATTERN.matcher(result).replaceAll("\\\\(");
        result = CLOSE_PAREN_PATTERN.matcher(result).replaceAll("\\\\)");
        result = OPEN_BRACKET_PATTERN.matcher(result).replaceAll("\\\\[");
        result = CLOSE_BRACKET_PATTERN.matcher(result).replaceAll("\\\\]");
        return result;
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

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
        final StringBuilder result = new StringBuilder();
        int index = 0;
        while (index < segment.length()) {
            final int qStart = segment.indexOf("\\Q", index);
            if (qStart == -1) {
                result.append(escapeOutside(segment.substring(index)));
                index = segment.length();
            }
            else {
                result.append(escapeOutside(segment.substring(index, qStart)))
                        .append("\\Q");
                final int qEnd = segment.indexOf("\\E", qStart + 2);
                if (qEnd == -1) {
                    result.append(segment.substring(qStart + 2));
                    index = segment.length();
                }
                else {
                    result.append(segment.substring(qStart + 2, qEnd))
                            .append("\\E");
                    index = qEnd + 2;
                }
            }
        }
        return result.toString();
    }

    /**
     * Escapes standard BDD violation special characters in a message segment outside of Q/E blocks.
     *
     * @param part the part of the segment to escape
     * @return the escaped part
     */
    private static String escapeOutside(String part) {
        return part.replace("{", "\\{")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("[", "\\[")
                .replace("]", "\\]");
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

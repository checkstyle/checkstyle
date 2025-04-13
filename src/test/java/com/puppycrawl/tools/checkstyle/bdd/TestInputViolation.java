///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.bdd;

import java.util.Objects;
import java.util.regex.Pattern;

public final class TestInputViolation implements Comparable<TestInputViolation> {

    /** Pattern to match the symbol: "{". */
    private static final Pattern OPEN_CURLY_PATTERN = Pattern.compile("\\{");

    /** Pattern to match the symbol: "(". */
    private static final Pattern OPEN_PAREN_PATTERN = Pattern.compile("\\(");

    /** Pattern to match the symbol: ")". */
    private static final Pattern CLOSE_PAREN_PATTERN = Pattern.compile("\\)");

    /** Parsed violation line number. */
    private final int lineNo;

    /** Parsed violation message. */
    private final String message;

    /**
     * Creates a new {@code TestInputViolation} instance.
     *
     * @param lineNo the violation line number.
     * @param message the violation message.
     */
    public TestInputViolation(int lineNo, String message) {
        this.lineNo = lineNo;
        this.message = message;
    }

    /**
     * Gets the violation line number.
     *
     * @return the violation line number.
     */
    public int getLineNo() {
        return lineNo;
    }

    /**
     * Gets the violation message.
     *
     * @return the violation message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Creates regex string to match the violation message format.
     *
     * @return the regex string.
     */
    public String toRegex() {
        String regex = lineNo + ":(?:\\d+:)?\\s.*";
        if (message != null) {
            String rawMessage = message;
            rawMessage = OPEN_CURLY_PATTERN.matcher(rawMessage).replaceAll("\\\\{");
            rawMessage = OPEN_PAREN_PATTERN.matcher(rawMessage).replaceAll("\\\\(");
            rawMessage = CLOSE_PAREN_PATTERN.matcher(rawMessage).replaceAll("\\\\)");
            regex += rawMessage + ".*";
        }
        return regex;
    }

    @Override
    public int compareTo(TestInputViolation testInputViolation) {
        final int result;
        if (message != null && lineNo == testInputViolation.lineNo) {
            result = testInputViolation.message.compareTo(message);
        }
        else {
            result = Integer.compare(lineNo, testInputViolation.lineNo);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNo);
    }

    @Override
    public boolean equals(Object object) {
        return getClass() == object.getClass() && compareTo((TestInputViolation) object) == 0;
    }
}

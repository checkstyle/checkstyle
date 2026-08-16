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

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TestInputViolation}.
 */
public class TestInputViolationTest {

    @Test
    public void testToRegexNullMessage() {
        final TestInputViolation violation = new TestInputViolation(10, null);
        assertWithMessage("Regex with null message should match line pattern only")
                .that(violation.toRegex())
                .isEqualTo("10:(?:\\d+:)?\\s.*");
    }

    @Test
    public void testToRegexNoSpecialCharacters() {
        final TestInputViolation violation = new TestInputViolation(5, "simple message");
        assertWithMessage("Regex should match simple message")
                .that(violation.toRegex())
                .isEqualTo("5:(?:\\d+:)?\\s.*simple message.*");
    }

    @Test
    public void testToRegexWithSpecialCharactersOutsideQuotes() {
        final TestInputViolation violation = new TestInputViolation(12,
                "message (with) {special} [brackets]");
        assertWithMessage("Regex should escape special characters outside quotes")
                .that(violation.toRegex())
                .isEqualTo("12:(?:\\d+:)?\\s.*message \\(with\\) \\{special} \\[brackets\\].*");
    }

    @Test
    public void testToRegexWithQuotedSpecialCharacters() {
        final TestInputViolation violation = new TestInputViolation(1, "\\Q^[a-z][a-zA-Z0-9]*$\\E");
        assertWithMessage("Regex should not escape special characters inside \\Q...\\E block")
                .that(violation.toRegex())
                .isEqualTo("1:(?:\\d+:)?\\s.*\\Q^[a-z][a-zA-Z0-9]*$\\E.*");
    }

    @Test
    public void testToRegexWithMultipleQuotedBlocks() {
        final TestInputViolation violation = new TestInputViolation(2,
                "before \\Q^[a-z]\\E middle \\Q(test)\\E after (parenthesis)");
        assertWithMessage(
                "Regex should escape characters outside multiple \\Q...\\E blocks but not inside")
                .that(violation.toRegex())
                .isEqualTo("2:(?:\\d+:)?\\s.*before \\Q^[a-z]\\E middle \\Q(test)\\E after "
                        + "\\(parenthesis\\).*");
    }

    @Test
    public void testToRegexWithUnmatchedQuotes() {
        final TestInputViolation violation1 = new TestInputViolation(3, "unmatched \\Q^[a-z][A-Z]");
        assertWithMessage("Regex should not escape characters after unmatched \\Q")
                .that(violation1.toRegex())
                .isEqualTo("3:(?:\\d+:)?\\s.*unmatched \\Q^[a-z][A-Z].*");

        final TestInputViolation violation2 = new TestInputViolation(4,
                "unmatched \\E(parenthesis)");
        assertWithMessage("Regex should escape characters outside unmatched \\E")
                .that(violation2.toRegex())
                .isEqualTo("4:(?:\\d+:)?\\s.*unmatched \\E\\(parenthesis\\).*");
    }

}

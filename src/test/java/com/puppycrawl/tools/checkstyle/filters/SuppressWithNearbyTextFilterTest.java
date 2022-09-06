///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;

public class SuppressWithNearbyTextFilterTest
    extends AbstractModuleTestSupport {

    private static final String REGEXP_SINGLELINE_CHECK_FORMAT = "this should not appear";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter";
    }

    @Test
    public void testDefaultConfig() throws Exception {
        final int expectedLineLength = 90;
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] violationMessages = {
            "29: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "31: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "44:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant", pattern),
            "47:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant1", pattern),
        };

        final String[] suppressedMessages = {
            "31: " + getLineLengthCheckMessage(expectedLineLength, 97),
            "41: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "47:22: " + getCheckMessage(ConstantNameCheck.class,
                    MSG_INVALID_PATTERN, "badConstant1", pattern),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterDefaultConfig.java"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testNearbyTextPattern() throws Exception {
        final int expectedLineLength = 90;

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "33: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "33: " + getRegexpSinglelineCheckMessage(),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "44: " + getRegexpSinglelineCheckMessage(),
            "49: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "54: " + getRegexpSinglelineCheckMessage(),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        final String[] suppressedMessages = {
            "33: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "33: " + getRegexpSinglelineCheckMessage(),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 91),
            "49: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "54: " + getRegexpSinglelineCheckMessage(),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 95),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterNearbyTextPattern.css"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testCheckPattern() throws Exception {
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "29: " + getRegexpSinglelineCheckMessage(),
            "35: " + getLineLengthCheckMessage(expectedLineLength, 87),
        };

        final String[] suppressedMessages = {
            "28: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "35: " + getLineLengthCheckMessage(expectedLineLength, 87),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterCheckPattern.bash"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testMessagePattern() throws Exception {
        final int expectedLineLength = 90;

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "33: " + getRegexpSinglelineCheckMessage(),
            "38: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "42: " + getLineLengthCheckMessage(expectedLineLength, 96),
        };

        final String[] suppressedMessages = {
            "38: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "42: " + getLineLengthCheckMessage(expectedLineLength, 96),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterMessagePattern.xml"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testIdPattern() throws Exception {
        final int expectedLineLength = 80;

        final String[] violationMessages = {
            "16: " + getRegexpSinglelineCheckMessage(),
            "29: " + getRegexpSinglelineCheckMessage(),
            "34: " + getLineLengthCheckMessage(expectedLineLength, 83),
            "38: " + getLineLengthCheckMessage(expectedLineLength, 84),
        };

        final String[] suppressedMessages = {
            "34: " + getLineLengthCheckMessage(expectedLineLength, 83),
            "38: " + getLineLengthCheckMessage(expectedLineLength, 84),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterIdPattern.html"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testLineRangePositive3() throws Exception {
        final int expectedLineLength = 92;

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "29: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "33: " + getRegexpSinglelineCheckMessage(),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
        };

        final String[] suppressedMessages = {
            "27: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "29: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "33: " + getRegexpSinglelineCheckMessage(),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterLineRangePositive3.sql"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testLineRangeNegative2() throws Exception {
        final int expectedLineLength = 91;

        final String[] violationMessages = {
            "15: " + getRegexpSinglelineCheckMessage(),
            "27: " + getLineLengthCheckMessage(expectedLineLength, 96),
            "28: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "29: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "33: " + getRegexpSinglelineCheckMessage(),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
        };

        final String[] suppressedMessages = {
            "28: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "29: " + getLineLengthCheckMessage(expectedLineLength, 94),
            "30: " + getLineLengthCheckMessage(expectedLineLength, 98),
            "34: " + getRegexpSinglelineCheckMessage(),
            "35: " + getRegexpSinglelineCheckMessage(),
            "36: " + getRegexpSinglelineCheckMessage(),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterLineRangeNegative2.txt"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testVariableCheckPatternAndLineRange() throws Exception {
        final int expectedLineLength = 85;

        final String[] violationMessages = {
            "19: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "20: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "21: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "22: " + getLineLengthCheckMessage(expectedLineLength, 87),
            "24: " + getLineLengthCheckMessage(expectedLineLength, 87),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 89),
        };

        final String[] suppressedMessages = {
            "19: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "20: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "21: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "25: " + getLineLengthCheckMessage(expectedLineLength, 89),
            "26: " + getLineLengthCheckMessage(expectedLineLength, 89),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilter"
                    + "VariableNearbyTextPatternAndLineRange.xml"),
            violationMessages, removeSuppressed(violationMessages, suppressedMessages)
        );
    }

    @Test
    public void testInvalidCheckPattern() throws Exception {
        final String[] violationAndSuppressedMessages = {
            "18: " + getLineLengthCheckMessage(80, 93),
        };

        try {
            verifyFilterWithInlineConfigParser(
                getPath("InputSuppressWithNearbyTextFilterInvalidCheckPattern.txt"),
                violationAndSuppressedMessages
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment a![b");
        }
    }

    @Test
    public void testInvalidIdPattern() throws Exception {
        final String[] violationAndSuppressedMessages = {
            "18: " + getLineLengthCheckMessage(80, 93),
        };

        try {
            verifyFilterWithInlineConfigParser(
                getPath("InputSuppressWithNearbyTextFilterInvalidIdPattern.txt"),
                violationAndSuppressedMessages
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment a![b");
        }
    }

    @Test
    public void testInvalidMessagePattern() throws Exception {
        final String[] violationAndSuppressedMessages = {
            "18: " + getLineLengthCheckMessage(80, 93),
        };

        try {
            verifyFilterWithInlineConfigParser(
                getPath("InputSuppressWithNearbyTextFilterInvalidMessagePattern.txt"),
                violationAndSuppressedMessages
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            final IllegalArgumentException cause = (IllegalArgumentException) ex.getCause();
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo("unable to parse expanded comment a![b");
        }
    }

    @Test
    public void testInvalidLineRange() throws Exception {
        final String[] violationAndSuppressedMessages = {
            "18: " + getLineLengthCheckMessage(80, 93),
        };

        try {
            verifyFilterWithInlineConfigParser(
                getPath("InputSuppressWithNearbyTextFilterInvalidLineRange.txt"),
                violationAndSuppressedMessages
            );
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("unable to parse line range"
                        + " from 'SUPPRESS CHECKSTYLE LineLengthCheck' using a!b");
        }
    }

    @Test
    public void testAcceptNullViolation() {
        final SuppressWithNearbyTextFilter filter =
                new SuppressWithNearbyTextFilter();
        final AuditEvent auditEvent = new AuditEvent(this);
        assertWithMessage("Filter should accept audit event")
                .that(filter.accept(auditEvent))
                .isTrue();
        assertWithMessage("File name should not be null")
            .that(auditEvent.getFileName())
            .isNull();
    }

    @Test
    public void testThrowsIllegalStateExceptionWhenFileNotFound() {
        final Violation message = new Violation(1, 1, 1, TokenTypes.CLASS_DEF,
            "messages.properties", "key", null, SeverityLevel.ERROR, null, getClass(), null);
        final String fileName = "nonexisting_file";
        final AuditEvent auditEvent = new AuditEvent(this, fileName, message);

        final SuppressWithNearbyTextFilter filter =
                new SuppressWithNearbyTextFilter();

        try {
            filter.accept(auditEvent);
            assertWithMessage(IllegalStateException.class.getSimpleName() + " is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Cannot read source file: " + fileName);

            final Throwable cause = ex.getCause();
            assertWithMessage("Exception cause has invalid type")
                    .that(cause)
                    .isInstanceOf(FileNotFoundException.class);
            assertWithMessage("Invalid exception message")
                .that(cause)
                .hasMessageThat()
                .isEqualTo(fileName + " (No such file or directory)");
        }
    }

    @Test
    public void testFilterWithDirectory() throws IOException {
        final SuppressWithNearbyTextFilter filter =
                new SuppressWithNearbyTextFilter();
        final AuditEvent event = new AuditEvent(this, getPath(""), new Violation(1, 1,
                "bundle", "key", null, SeverityLevel.ERROR, "moduleId", getClass(),
                "customMessage"));

        assertWithMessage("filter should accept directory")
                .that(filter.accept(event))
                .isTrue();
    }

    private static String getLineLengthCheckMessage(int expectedLength, int actualLength) {
        return getCheckMessage(LineLengthCheck.class, MSG_KEY, expectedLength, actualLength);
    }

    private static String getRegexpSinglelineCheckMessage() {
        final String msgRegexExceeded = "regexp.exceeded";
        return getCheckMessage(RegexpSinglelineCheck.class,
                msgRegexExceeded, REGEXP_SINGLELINE_CHECK_FORMAT);
    }
}

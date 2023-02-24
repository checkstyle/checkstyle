///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.TestInputViolation;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import de.thetaphi.forbiddenapis.SuppressForbidden;

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
            "33: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "33: " + getRegexpSinglelineCheckMessage(),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "44: " + getRegexpSinglelineCheckMessage(),
            "49: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "54: " + getRegexpSinglelineCheckMessage(),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 97),
        };

        final String[] suppressedMessages = {
            "33: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "33: " + getRegexpSinglelineCheckMessage(),
            "39: " + getLineLengthCheckMessage(expectedLineLength, 93),
            "49: " + getLineLengthCheckMessage(expectedLineLength, 95),
            "54: " + getRegexpSinglelineCheckMessage(),
            "58: " + getLineLengthCheckMessage(expectedLineLength, 97),
        };

        verifyFilterWithInlineConfigParserSortedActualViolations(
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
    public void testNearbyTextPatternAny() throws Exception {
        final int expectedLineLength = 76;

        final String[] violationMessages = {
            "18: " + getLineLengthCheckMessage(expectedLineLength, 80),
        };

        final String[] suppressedMessages = {
            "18: " + getLineLengthCheckMessage(expectedLineLength, 80),
        };

        verifyFilterWithInlineConfigParser(
            getPath("InputSuppressWithNearbyTextFilterNearbyTextPatternAny.txt"),
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

    @Test
    public void testSuppressionsAreClearedEachRun() throws IOException {
        final SuppressWithNearbyTextFilter filter = new SuppressWithNearbyTextFilter();

        final List<?> suppressions1 = getSuppressionsAfterExecution(filter,
                getPath("InputSuppressWithNearbyTextFilterDefaultConfig.java"));
        assertWithMessage("Invalid suppressions size")
                .that(suppressions1)
                .hasSize(3);

        final List<?> suppressions2 = getSuppressionsAfterExecution(filter,
                getPath("InputSuppressWithNearbyTextFilterOneLineText.txt"));
        assertWithMessage("Invalid suppressions size")
                .that(suppressions2)
                .isEmpty();
    }

    @Test
    public void testCurrentFileAbsolutePathHasChangedEachRun() throws IOException {
        final SuppressWithNearbyTextFilter filter = new SuppressWithNearbyTextFilter();

        final String currentFileAbsolutePath1 = getCurrentFileAbsolutePathAfterExecution(filter,
                getPath("InputSuppressWithNearbyTextFilterDefaultConfig.java"));
        final String currentFileAbsolutePath2 = getCurrentFileAbsolutePathAfterExecution(filter,
                getPath("InputSuppressWithNearbyTextFilterOneLineText.txt"));

        assertWithMessage("currentFileAbsolutePath has not changed")
                .that(currentFileAbsolutePath1)
                .isNotEqualTo(currentFileAbsolutePath2);
    }

    /**
     * Calls the filter with a real input file and returns a list of
     * {@link SuppressWithNearbyTextFilter} internal type {@code Suppression}.
     *
     * @return {@code Suppression} list
     */
    private static List<?> getSuppressionsAfterExecution(SuppressWithNearbyTextFilter filter,
                                                         String filename) {
        final AuditEvent dummyEvent = buildDummyAuditEvent(filename);
        filter.accept(dummyEvent);
        return TestUtil.getInternalState(filter, "suppressions");
    }

    private static String getCurrentFileAbsolutePathAfterExecution(SuppressWithNearbyTextFilter
                                                                           filter,
                                                                   String filename) {
        final AuditEvent dummyEvent = buildDummyAuditEvent(filename);
        filter.accept(dummyEvent);
        return TestUtil.getInternalState(filter, "currentFileAbsolutePath");
    }

    private static AuditEvent buildDummyAuditEvent(String filename) {
        final Violation violation = new Violation(1, null, null,
                null, null, Object.class, null);
        return new AuditEvent("", filename, violation);
    }

    private static String getLineLengthCheckMessage(int expectedLength, int actualLength) {
        return getCheckMessage(LineLengthCheck.class, MSG_KEY, expectedLength, actualLength);
    }

    private static String getRegexpSinglelineCheckMessage() {
        final String msgRegexExceeded = "regexp.exceeded";
        return getCheckMessage(RegexpSinglelineCheck.class,
                msgRegexExceeded, REGEXP_SINGLELINE_CHECK_FORMAT);
    }

    /**
     * Performs verification of the file with the given file path using specified configuration
     * and the array of expected messages. Also performs verification of the config with filters
     * specified in the input file.
     * This method is an exact copy of
     * {@link AbstractModuleTestSupport#verifyWithInlineConfigParser} and the purpose of it is
     * to be able to call {@link #verifyViolationsSortedActualViolations}. This is a temporary fix
     * until sorting is implemented in {@link AbstractModuleTestSupport#verifyViolations}.
     *
     * @param filePath file path to verify.
     * @param expectedUnfiltered an array of expected unfiltered config.
     * @param expectedFiltered an array of expected config with filters.
     * @throws Exception if exception occurs during verification process.
     * @noinspection JavadocReference
     * @noinspectionreason This method is temporary.
     */
    @SuppressForbidden
    private void verifyFilterWithInlineConfigParserSortedActualViolations(String filePath,
                                                            String[] expectedUnfiltered,
                                                            String... expectedFiltered)
            throws Exception {
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parseWithFilteredViolations(filePath);
        final DefaultConfiguration configWithoutFilters =
                testInputConfiguration.createConfigurationWithoutFilters();
        final List<TestInputViolation> violationsWithoutFilters =
                new ArrayList<>(testInputConfiguration.getViolations());
        violationsWithoutFilters.addAll(testInputConfiguration.getFilteredViolations());
        Collections.sort(violationsWithoutFilters);
        verifyViolationsSortedActualViolations(configWithoutFilters,
                filePath, violationsWithoutFilters);
        verify(configWithoutFilters, filePath, expectedUnfiltered);
        final DefaultConfiguration configWithFilters =
                testInputConfiguration.createConfiguration();
        verifyViolationsSortedActualViolations(configWithFilters,
                filePath, testInputConfiguration.getViolations());
        verify(configWithFilters, filePath, expectedFiltered);
    }

    /**
     * Performs verification of violation lines.
     * This method is a copy of {@link AbstractModuleTestSupport#verifyViolations}
     * apart from the sorting of the actual violations.
     *
     * @param config parsed config.
     * @param file file path.
     * @param testInputViolations List of TestInputViolation objects.
     * @throws Exception if exception occurs during verification process.
     * @noinspection JavadocReference
     * @noinspectionreason This method is temporary.
     */
    public void verifyViolationsSortedActualViolations(Configuration config,
                                                       String file,
                                                       List<TestInputViolation> testInputViolations)
            throws Exception {
        final List<String> actualViolations = TestUtil.invokeMethod(this,
                "getActualViolationsForFile",
                config, file);
        Collections.sort(actualViolations);
        final List<Integer> actualViolationLines = actualViolations.stream()
                .map(violation -> violation.substring(0, violation.indexOf(':')))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        final List<Integer> expectedViolationLines = testInputViolations.stream()
                .map(TestInputViolation::getLineNo)
                .collect(Collectors.toList());
        assertWithMessage("Violation lines for %s differ.", file)
                .that(actualViolationLines)
                .isEqualTo(expectedViolationLines);
        for (int index = 0; index < actualViolations.size(); index++) {
            assertWithMessage("Actual and expected violations differ.")
                    .that(actualViolations.get(index))
                    .matches(testInputViolations.get(index).toRegex());
        }
    }
}

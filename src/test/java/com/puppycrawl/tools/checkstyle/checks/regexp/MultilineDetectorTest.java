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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestLoggingReporter;

/** Unit tests for {@link MultilineDetector}. */
public class MultilineDetectorTest {

    private static final int LARGE_XY_SIZE = 100_000;

    @Test
    public void testFormatNullLogsEmpty() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("line1"));
        detector.processLines(text);

        assertWithMessage("Expected one log when format is null")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testFormatEmptyLogsEmpty() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("")
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("line1"));
        detector.processLines(text);

        assertWithMessage("Expected one log when format is empty")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testExceededWithReportGroupAndEmptyMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("(CONTEXT\\n)(PRINT\\()")
                .maximum(0)
                .reportGroup(2)
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"),
                Arrays.asList("CONTEXT", "PRINT("));
        detector.processLines(text);

        assertWithMessage("Expected one log when exceeded with reportGroup and empty message")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testExceededWithoutReportGroupAndCustomMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("(TEST)")
                .maximum(0)
                .message("custom")
                .reportGroup(5)
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("TEST"));
        detector.processLines(text);

        assertWithMessage("Expected one log when exceeded without valid reportGroup")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testExceededWithReportGroupAndCustomMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("(CONTEXT\\n)(PRINT\\()")
                .maximum(0)
                .reportGroup(2)
                .message("Console print after specific context")
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"),
                Arrays.asList("CONTEXT", "PRINT("));
        detector.processLines(text);

        assertWithMessage("Expected one log when exceeded with reportGroup and custom message")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testExceededWithoutReportGroupAndEmptyMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("(TEST)")
                .maximum(0)
                .reportGroup(5)
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("TEST"));
        detector.processLines(text);

        assertWithMessage("Expected one log when exceeded without reportGroup and empty message")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testStackOverflowErrorLogsMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("(x|y)*")
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final String content = "xy".repeat(LARGE_XY_SIZE / 2);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList(content));
        detector.processLines(text);

        assertWithMessage("Expected one log when StackOverflowError is caught")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testMinimumMessageEmpty() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("NO_MATCH")
                .minimum(1)
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("line"));
        detector.processLines(text);

        assertWithMessage("Expected one log when minimum not met and message empty")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

    @Test
    public void testMinimumCustomMessage() {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions options = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("NO_MATCH")
                .minimum(1)
                .message("some message")
                .build();

        final MultilineDetector detector = new MultilineDetector(options);
        final FileText text = new FileText(new File("Test.java"), Arrays.asList("line"));
        detector.processLines(text);

        assertWithMessage("Expected one log when minimum not met and custom message")
                .that(reporter.getLogCount())
                .isEqualTo(1);
    }

}

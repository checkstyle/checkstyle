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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector.MSG_REGEXP_MINIMUM;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestLoggingReporter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpSinglelineCheckTest extends AbstractModuleTestSupport {

    private static final String[] EMPTY = {};

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final String[] expected = {
            "78: Bad line :(",
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic2.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {

        final String[] expected = {
            "78: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic3.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic4.java"), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_MINIMUM, "500", "\\r"),
        };

        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic5.java"), expected);
    }

    @Test
    public void testSetMessage() throws Exception {
        final String[] expected = {
            "1: someMessage",
        };

        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic6.java"), expected);
    }

    @Test
    public void testMaximum() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineSemantic7.java"), EMPTY);
    }

    /**
     * Done as a UT cause new instance of Detector is created each time 'verify' executed.
     *
     * @throws Exception some Exception
     */
    @Test
    public void testStateIsBeingReset() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions detectorOptions = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format(illegal)
                .maximum(1)
                .build();

        final SinglelineDetector detector =
                new SinglelineDetector(detectorOptions);
        final File file = new File(getPath("InputRegexpSinglelineSemantic8.java"));

        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        assertWithMessage("Logged unexpected amount of issues")
                .that(reporter.getLogCount())
                .isEqualTo(0);
    }

}

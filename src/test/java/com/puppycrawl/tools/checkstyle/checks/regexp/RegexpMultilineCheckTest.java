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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_STACKOVERFLOW;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.internal.testmodules.TestLoggingReporter;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpMultilineCheckTest extends AbstractModuleTestSupport {

    @TempDir
    public File temporaryFolder;

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final String[] expected = {
            "79: " + "Bad line :(",
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic2.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String[] expected = {
            "79: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic3.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic4.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
            "17: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
            "18: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic5.java"), expected);
    }

    @Test
    public void testCarriageReturn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addProperty("format", "\\r");
        checkConfig.addProperty("maximum", "0");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "\\r"),
            "3: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "\\r"),
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(),
            "first line \r\n second line \n\r third line".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testMaximum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addProperty("format", "\\r");
        checkConfig.addProperty("maximum", "1");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "\\r"),
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(),
                "first line \r\n second line \n\r third line".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    /**
     * Done as a UT cause new instance of Detector is created each time 'verify' executed.
     *
     * @throws Exception some Exception
     */
    @Test
    public void testStateIsBeingReset() throws Exception {
        final TestLoggingReporter reporter = new TestLoggingReporter();
        final DetectorOptions detectorOptions = DetectorOptions.newBuilder()
                .reporter(reporter)
                .format("\\r")
                .maximum(1)
                .build();

        final MultilineDetector detector =
                new MultilineDetector(detectorOptions);
        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(),
                "first line \r\n second line \n\r third line".getBytes(StandardCharsets.UTF_8));

        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        assertWithMessage("Logged unexpected amount of issues")
                .that(reporter.getLogCount())
                .isEqualTo(2);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic6.java"), expected);
    }

    @Test
    public void testNullFormat() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic7.java"), expected);
    }

    @Test
    public void testEmptyFormat() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_EMPTY),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic8.java"), expected);
    }

    @Test
    public void testNoStackOverflowError() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        // http://madbean.com/2004/mb2004-20/
        checkConfig.addProperty("format", "(x|y)*");

        final String[] expected = {
            "1: " + getCheckMessage(MSG_STACKOVERFLOW, "(x|y)*"),
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(), makeLargeXyString().toString().getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addProperty("format", "\\r");
        checkConfig.addProperty("minimum", "5");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_MINIMUM, "5", "\\r"),
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(), "".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testMinimumWithCustomMessage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addProperty("format", "\\r");
        checkConfig.addProperty("minimum", "5");
        checkConfig.addProperty("message", "some message");
        final String[] expected = {
            "1: some message",
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(), "".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    private static CharSequence makeLargeXyString() {
        // now needs 10'000 or 100'000, as just 1000 is no longer enough today to provoke the
        // StackOverflowError
        final int size = 100_000;
        return "xy".repeat(size / 2);
    }

    @Test
    public void testGoodLimit() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineSemantic9.java"), expected);
    }

    @Test
    public void testMultilineSupport() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "(a)bc.*def"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineMultilineSupport.java"), expected);
    }

    @Test
    public void testMultilineSupportNotGreedy() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "(a)bc.*?def"),
            "24: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "(a)bc.*?def"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpMultilineMultilineSupport2.java"), expected);
    }

}

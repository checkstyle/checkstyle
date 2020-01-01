////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_STACKOVERFLOW;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        checkConfig.addAttribute("message", "Bad line :(");
        final String[] expected = {
            "69: " + "Bad line :(",
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "^import");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
            "8: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
            "9: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "^import"),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testCarriageReturn() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "\\r");
        checkConfig.addAttribute("maximum", "0");
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
        checkConfig.addAttribute("format", "\\r");
        checkConfig.addAttribute("maximum", "1");
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
        assertEquals(2, reporter.getLogCount(), "Logged unexpected amount of issues");
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testNullFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", null);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_EMPTY),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testEmptyFormat() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_EMPTY),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testNoStackOverflowError() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        // http://madbean.com/2004/mb2004-20/
        checkConfig.addAttribute("format", "(x|y)*");

        final String[] expected = {
            "1: " + getCheckMessage(MSG_STACKOVERFLOW),
        };

        final File file = File.createTempFile("junit", null, temporaryFolder);
        Files.write(file.toPath(), makeLargeXyString().toString().getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "\\r");
        checkConfig.addAttribute("minimum", "5");
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
        checkConfig.addAttribute("format", "\\r");
        checkConfig.addAttribute("minimum", "5");
        checkConfig.addAttribute("message", "some message");
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
        final int size = 100000;
        final StringBuilder largeString = new StringBuilder(size);
        for (int i = 0; i < size / 2; i++) {
            largeString.append("xy");
        }
        return largeString;
    }

    @Test
    public void testGoodLimit() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "^import");
        checkConfig.addAttribute("maximum", "5000");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testMultilineSupport() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "abc.*def");
        checkConfig.addAttribute("matchAcrossLines", "true");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "abc.*def"),
        };
        verify(checkConfig, getPath("InputRegexpMultilineMultilineSupport.java"), expected);
    }

    @Test
    public void testMultilineSupportNotGreedy() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpMultilineCheck.class);
        checkConfig.addAttribute("format", "abc.*?def");
        checkConfig.addAttribute("matchAcrossLines", "true");
        final String[] expected = {
            "9: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "abc.*?def"),
            "11: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "abc.*?def"),
        };
        verify(checkConfig, getPath("InputRegexpMultilineMultilineSupport2.java"), expected);
    }

}

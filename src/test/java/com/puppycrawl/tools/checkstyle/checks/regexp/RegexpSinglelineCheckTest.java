////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.SinglelineDetector.MSG_REGEXP_MINIMUM;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.TestLoggingReporter;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RegexpSinglelineCheckTest extends AbstractModuleTestSupport {
    private static final String[] EMPTY = {};
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createModuleConfig(RegexpSinglelineCheck.class);
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline";
    }

    @Test
    public void testIt() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String message = "Bad line :(";
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        checkConfig.addAttribute("maximum", "0");

        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "500");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REGEXP_MINIMUM, "500", illegal),
        };

        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testSetMessage() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "500");
        checkConfig.addAttribute("message", "someMessage");
        final String[] expected = {
            "0: someMessage",
        };

        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), expected);
    }

    @Test
    public void testMaximum() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("maximum", "1");
        verify(checkConfig, getPath("InputRegexpSinglelineSemantic.java"), EMPTY);
    }

    /**
     * Done as a UT cause new instance of Detector is created each time 'verify' executed.
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
        final File file = new File(getPath("InputRegexpSinglelineSemantic.java"));

        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        detector.processLines(new FileText(file, StandardCharsets.UTF_8.name()));
        Assert.assertEquals("Logged unexpected amount of issues",
                0, reporter.getLogCount());
    }
}

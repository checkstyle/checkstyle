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

import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_STACKOVERFLOW;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RegexpMultilineCheckTest extends AbstractModuleTestSupport {
    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createModuleConfig(RegexpMultilineCheck.class);
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline";
    }

    @Test
    public void testIt() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
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
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final String illegal = "^import";
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
            "8: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
            "9: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testCarriageReturn() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("maximum", "0");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
            "3: " + getCheckMessage(MSG_REGEXP_EXCEEDED, illegal),
        };

        final File file = temporaryFolder.newFile();
        Files.write(file.toPath(),
            "first line \r\n second line \n\r third line".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testNullFormat() throws Exception {
        checkConfig.addAttribute("format", null);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_EMPTY),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testEmptyFormat() throws Exception {
        checkConfig.addAttribute("format", "");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_EMPTY),
        };
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testNoStackOverflowError() throws Exception {
        // http://madbean.com/2004/mb2004-20/
        checkConfig.addAttribute("format", "(x|y)*");

        final String[] expected = {
            "0: " + getCheckMessage(MSG_STACKOVERFLOW),
        };

        final File file = temporaryFolder.newFile();
        Files.write(file.toPath(), makeLargeXyString().toString().getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    @Test
    public void testMinimum() throws Exception {
        final String illegal = "\\r";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "5");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REGEXP_MINIMUM, "5", illegal),
        };

        final File file = temporaryFolder.newFile();
        Files.write(file.toPath(), "".getBytes(StandardCharsets.UTF_8));

        verify(checkConfig, file.getPath(), expected);
    }

    private static CharSequence makeLargeXyString() {
        // now needs 10'000 or 100'000, as just 1000 is no longer enough today to provoke the
        // StackOverflowError
        final int size = 100000;
        final StringBuffer largeString = new StringBuffer(size);
        for (int i = 0; i < size / 2; i++) {
            largeString.append("xy");
        }
        return largeString;
    }

    @Test
    public void testSetMessage() throws Exception {
        final String illegal = "\\n";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("minimum", "500");
        checkConfig.addAttribute("message", "someMessage");

        final String[] expected = new String[223];
        for (int i = 0; i < 223; i++) {
            expected[i] = i + ": someMessage";
        }

        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }

    @Test
    public void testGoodLimit() throws Exception {
        final String illegal = "^import";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("maximum", "5000");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpMultilineSemantic.java"), expected);
    }
}

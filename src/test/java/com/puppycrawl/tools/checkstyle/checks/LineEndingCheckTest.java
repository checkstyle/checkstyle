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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_UNABLE_OPEN;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.CR;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.CRLF;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingOption.LF;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class LineEndingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/lineending";
    }

    @Test
    public void testInputLineEndingLfExpected1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputLineEndingLfExpected1.java"),
                expected);
    }

    @Test
    public void testInputLineEndingLfExpected2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);

        verify(checkConfig,
                getPath("InputLineEndingLfExpected2.java"),
                expected);
    }

    @Test
    public void testInputLineEndingLfExpected3() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);

        verify(checkConfig,
                getPath("InputLineEndingLfExpected3.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrlfExpected1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputLineEndingCrlfExpected1.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrlfExpected2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");

        verify(checkConfig,
                getPath("InputLineEndingCrlfExpected2.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrlfExpected3() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");

        verify(checkConfig,
                getPath("InputLineEndingCrlfExpected3.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrExpected1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputLineEndingCrExpected1.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrExpected2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");

        verify(checkConfig,
                getPath("InputLineEndingCrExpected2.java"),
                expected);
    }

    @Test
    public void testInputLineEndingCrExpected3() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");

        verify(checkConfig,
                getPath("InputLineEndingCrExpected3.java"),
                expected);
    }

    @Test
    public void testInputLineEndingEmptyFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);

        verify(checkConfig,
                getPath("InputLineEndingEmptyFile.txt"), expected);
    }

    @Test
    public void testInputLineEndingEmptyFile2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");

        verify(checkConfig,
                getPath("InputLineEndingEmptyFile.txt"), expected);
    }

    @Test
    public void testInputLineEndingEmptyFile3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");

        verify(checkConfig,
                getPath("InputLineEndingEmptyFile.txt"), expected);
    }

    @Test
    public void testInputLineEndingOneLineLf1() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");

        verify(checkConfig,
                getPath("InputLineEndingOneLineLf.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineLf2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, LF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");

        verify(checkConfig,
                getPath("InputLineEndingOneLineLf.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineCrlf() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CRLF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);

        verify(checkConfig,
                getPath("InputLineEndingOneLineCrlf.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineCrlf2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CR, CRLF),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "cr");

        verify(checkConfig,
                getPath("InputLineEndingOneLineCrlf.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineCr() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, LF, CR),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "lf");

        verify(checkConfig,
                getPath("InputLineEndingOneLineCr.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineCr2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, CR),
        };

        DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");

        verify(checkConfig,
                getPath("InputLineEndingOneLineCr.txt"),
                expected);
    }

    @Test
    public void testInputLineEndingOneLineLf3() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING, CRLF, LF),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "  crlf  ");

        verify(checkConfig,
                getPath("InputLineEndingOneLineLf.txt"),
                expected);
    }

    @Test
    public void testWrongFileWithPath() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(LineEndingCheck.class);
        final LineEndingCheck check = new LineEndingCheck();
        check.configure(checkConfig);

        final List<String> lines = new ArrayList<>(1);
        lines.add("txt");

        final File impossibleFile =
                new File("this/file/does/not/exist.txt");

        final FileText fileText = new FileText(impossibleFile, lines);
        final Set<Violation> violations =
                check.process(impossibleFile, fileText);

        assertWithMessage("Amount of violations is unexpected")
                .that(violations)
                .hasSize(1);

        final Violation violation = violations.iterator().next();

        assertWithMessage("Violation line number differs from expected")
                .that(violation.getLineNo())
                .isEqualTo(0);

        assertWithMessage("Violation message differs from expected")
            .that(violation.getViolation())
            .isEqualTo(
                getCheckMessage(
                    MSG_KEY_UNABLE_OPEN,
                    impossibleFile.getPath()
                )
            );
    }

}

///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_UNABLE_OPEN;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING_CRLF;
import static com.puppycrawl.tools.checkstyle.checks.LineEndingCheck.MSG_KEY_WRONG_ENDING_LF;

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
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/lineending";
    }

    @Test
    public void testInputLineEndingLf() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputLineEndingLF.java"),
                expected
        );
    }

    @Test
    public void testInputLineEndingCrlf() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputLineEndingCRLF.java"),
                expected
        );
    }

    @Test
    public void testInputLineEndingLf2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "crlf");
        checkConfig.addProperty("fileExtensions", "java");

        verify(checkConfig,
                getPath("InputLineEndingLF2.java"), expected
        );
    }

    @Test
    public void testInputLineEndingCrlf2() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "2: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "3: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "4: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "5: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "6: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
            "7: " + getCheckMessage(MSG_KEY_WRONG_ENDING_LF),
        };

        final DefaultConfiguration checkConfig = createModuleConfig(LineEndingCheck.class);
        checkConfig.addProperty("lineEnding", "lf");
        checkConfig.addProperty("fileExtensions", "java");

        verify(checkConfig,
                getPath("InputLineEndingCRLF2.java"), expected
        );
    }

    @Test
    public void testInputLineEndingTxt() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputLineEndingLF3.txt"),
                expected
        );
    }

    @Test
    public void testLineEndingDefaultProperties() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputLineEndingDefaultProperties.java"),
                expected
        );
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

    @Test
    public void testInputEmptyLines() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING_CRLF),
        };

        final DefaultConfiguration configuration = createModuleConfig(LineEndingCheck.class);
        configuration.addProperty("lineEnding", "  crlf  ");

        verify(configuration, getPath("InputLineEndingEmptyLines.txt"), expected);
    }

    @Test
    public void testInputEmptyLines2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final DefaultConfiguration configuration = createModuleConfig(LineEndingCheck.class);
        verify(configuration, getPath("InputLineEndingEmptyLines.txt"), expected);
    }
}

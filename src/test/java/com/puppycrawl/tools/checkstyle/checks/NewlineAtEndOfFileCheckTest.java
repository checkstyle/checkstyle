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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_UNABLE_OPEN;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_WRONG_ENDING;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NewlineAtEndOfFileCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/newlineatendoffile";
    }

    @Test
    public void testNewlineLfAtEndOfFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileLf.java"),
            expected);
    }

    @Test
    public void testNewlineLfAtEndOfFileLfNotOverlapWithCrLf() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_WRONG_ENDING),
        };
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileCrlf.java"),
            expected);
    }

    @Test
    public void testNewlineCrlfAtEndOfFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileCrlf3.java"),
            expected);
    }

    @Test
    public void testNewlineCrAtEndOfFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileCr.java"),
            expected);
    }

    @Test
    public void testAnyNewlineAtEndOfFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileCrlf2.java"),
            expected);
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileLf2.java"),
            expected);
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileCr2.java"),
            expected);
    }

    @Test
    public void testNoNewlineLfAtEndOfFile() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileNoNewline.java"),
            expected);
    }

    @Test
    public void testNoNewlineAtEndOfFile() throws Exception {
        final String msgKeyNoNewlineEof = "File does not end with a newline :)";
        final String[] expected = {
            "1: " + msgKeyNoNewlineEof,
        };
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileNoNewline2.java"),
            expected);
    }

    @Test
    public void testSetLineSeparatorFailure()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addProperty("lineSeparator", "ct");
        try {
            createChecker(checkConfig);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Error message is unexpected")
                    .that(ex.getMessage())
                    .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle."
                            + "checks.NewlineAtEndOfFileCheck - "
                            + "Cannot set property 'lineSeparator' to 'ct'");
        }
    }

    @Test
    public void testEmptyFileFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addProperty("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            checkConfig,
            getPath("InputNewlineAtEndOfFileEmptyFile.txt"),
            expected);
    }

    @Test
    public void testFileWithEmptyLineOnly() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addProperty("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
                checkConfig,
                getPath("InputNewlineAtEndOfFileNewlineAtEnd.txt"),
                expected);
    }

    @Test
    public void testFileWithEmptyLineOnlyWithLfCrCrlf() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addProperty("lineSeparator", LineSeparatorOption.LF_CR_CRLF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
                checkConfig,
                getPath("InputNewlineAtEndOfFileNewlineAtEndLf.txt"),
                expected);
    }

    @Test
    public void testWrongFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NewlineAtEndOfFileCheck.class);
        final NewlineAtEndOfFileCheck check = new NewlineAtEndOfFileCheck();
        check.configure(checkConfig);
        final List<String> lines = new ArrayList<>(1);
        lines.add("txt");
        final File impossibleFile = new File("");
        final FileText fileText = new FileText(impossibleFile, lines);
        final Set<Violation> violations = check.process(impossibleFile, fileText);
        assertWithMessage("Amount of violations is unexpected")
                .that(violations)
                .hasSize(1);
        final Iterator<Violation> iterator = violations.iterator();
        assertWithMessage("Violation message differs from expected")
                .that(iterator.next().getViolation())
                .isEqualTo(getCheckMessage(MSG_KEY_UNABLE_OPEN, ""));
    }

    @Test
    public void testWrongSeparatorLength() throws Exception {
        try (RandomAccessFile file =
                     new ReadZeroRandomAccessFile(getPath("InputNewlineAtEndOfFileLf.java"), "r")) {
            TestUtil.invokeMethod(new NewlineAtEndOfFileCheck(), "endsWithNewline", file,
                LineSeparatorOption.LF);
            assertWithMessage("ReflectiveOperationException is expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex)
                    .hasCauseThat()
                        .hasMessageThat()
                        .isEqualTo("Unable to read 1 bytes, got 0");
        }
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNewlineAtEndOfFileTestTrimProperty.java"),
                expected);
    }

    private static final class ReadZeroRandomAccessFile extends RandomAccessFile {

        private ReadZeroRandomAccessFile(String name, String mode)
                throws FileNotFoundException {
            super(name, mode);
        }

        @Override
        public int read(byte[] bytes) {
            return 0;
        }
    }

}

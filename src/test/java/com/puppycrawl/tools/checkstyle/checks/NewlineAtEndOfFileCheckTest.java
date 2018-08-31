////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;
import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_UNABLE_OPEN;
import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NewlineAtEndOfFileCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/newlineatendoffile";
    }

    @Test
    public void testNewlineLfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileLf.java"),
            expected);
    }

    @Test
    public void testNewlineCrlfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.CRLF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileCrlf.java"),
            expected);
    }

    @Test
    public void testNewlineCrAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.CR.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileCr.java"),
            expected);
    }

    @Test
    public void testAnyNewlineAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF_CR_CRLF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileCrlf.java"),
            expected);
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileLf.java"),
            expected);
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileCr.java"),
            expected);
    }

    @Test
    public void testNoNewlineLfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileNoNewline.java"),
            expected);
    }

    @Test
    public void testNoNewlineAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF_CR_CRLF.toString());
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileNoNewline.java"),
            expected);
    }

    @Test
    public void testSetLineSeparatorFailure()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", "ct");
        try {
            createChecker(checkConfig);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue("Error message is unexpected",
                    ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle."
                            + "checks.NewlineAtEndOfFileCheck - "
                            + "Cannot set property 'lineSeparator' to 'ct' in module"));
        }
    }

    @Test
    public void testEmptyFileFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineAtEndOfFileEmptyFile.txt"),
            expected);
    }

    @Test
    public void testFileWithEmptyLineOnly() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(
                createChecker(checkConfig),
                getPath("InputNewlineAtEndOfFileNewlineAtEnd.txt"),
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
        final Set<LocalizedMessage> messages = check.process(impossibleFile, fileText);
        assertEquals("Amount of messages is unexpected",
                1, messages.size());
        final Iterator<LocalizedMessage> iterator = messages.iterator();
        assertEquals("Violation message differs from expected",
                getCheckMessage(MSG_KEY_UNABLE_OPEN, ""), iterator.next().getMessage());
    }

    @Test
    public void testWrongSeparatorLength() throws Exception {
        final NewlineAtEndOfFileCheck check = new NewlineAtEndOfFileCheck();
        final DefaultConfiguration checkConfig = createModuleConfig(NewlineAtEndOfFileCheck.class);
        check.configure(checkConfig);

        final Method method = NewlineAtEndOfFileCheck.class
                .getDeclaredMethod("endsWithNewline", RandomAccessFile.class);
        method.setAccessible(true);
        final RandomAccessFile file = mock(RandomAccessFile.class);
        when(file.length()).thenReturn(2_000_000L);
        try {
            method.invoke(new NewlineAtEndOfFileCheck(), file);
            fail("Exception is expected");
        }
        catch (InvocationTargetException ex) {
            assertTrue("Error type is unexpected",
                    ex.getCause() instanceof IOException);
            if (System.getProperty("os.name").toLowerCase(ENGLISH).startsWith("windows")) {
                assertEquals("Error message is unexpected",
                        "Unable to read 2 bytes, got 0", ex.getCause().getMessage());
            }
            else {
                assertEquals("Error message is unexpected",
                        "Unable to read 1 bytes, got 0", ex.getCause().getMessage());
            }
        }
    }

}

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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class NewlineAtEndOfFileCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration config) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(config);
        return dc;
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + filename);
    }

    @Test
    public void testNewlineLfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineLfAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testNewlineCrlfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.CRLF.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineCrlfAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testNewlineCrAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.CR.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineCrAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testAnyNewlineAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF_CR_CRLF.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineCrlfAtEndOfFile.java"),
            expected);
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineLfAtEndOfFile.java"),
            expected);
        verify(
            createChecker(checkConfig),
            getPath("InputNewlineCrAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testNoNewlineLfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNoNewlineAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testNoNewlineAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF_CR_CRLF.toString());
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputNoNewlineAtEndOfFile.java"),
            expected);
    }

    @Test
    public void testSetLineSeparatorFailure()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", "ct");
        try {
            createChecker(checkConfig);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertTrue(ex.getMessage().startsWith(
                    "cannot initialize module com.puppycrawl.tools.checkstyle."
                            + "checks.NewlineAtEndOfFileCheck - "
                            + "Cannot set property 'lineSeparator' to 'ct' in module"));
        }
    }

    @Test
    public void testEmptyFileFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY_NO_NEWLINE_EOF),
        };
        verify(
            createChecker(checkConfig),
            getPath("InputEmptyFile.txt"),
            expected);
    }

    @Test
    public void testWrongFile() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(NewlineAtEndOfFileCheck.class);
        final NewlineAtEndOfFileCheck check = new NewlineAtEndOfFileCheck();
        check.configure(checkConfig);
        final List<String> lines = new ArrayList<>(1);
        lines.add("txt");
        final File impossibleFile = new File("");
        final Set<LocalizedMessage> messages = check.process(impossibleFile, lines);
        assertEquals(1, messages.size());
        final Iterator<LocalizedMessage> iterator = messages.iterator();
        assertEquals(getCheckMessage(MSG_KEY_UNABLE_OPEN, ""), iterator.next().getMessage());
    }

    @Test
    public void testWrongSeparatorLength() throws Exception {
        final NewlineAtEndOfFileCheck check = new NewlineAtEndOfFileCheck();
        final DefaultConfiguration checkConfig = createCheckConfig(NewlineAtEndOfFileCheck.class);
        check.configure(checkConfig);

        final Method method = NewlineAtEndOfFileCheck.class
                .getDeclaredMethod("endsWithNewline", RandomAccessFile.class);
        method.setAccessible(true);
        final RandomAccessFile file = mock(RandomAccessFile.class);
        when(file.length()).thenReturn(2_000_000L);
        try {
            method.invoke(new NewlineAtEndOfFileCheck(), file);
        }
        catch (InvocationTargetException ex) {
            assertTrue(ex.getCause() instanceof IOException);
            if (System.getProperty("os.name").toLowerCase(ENGLISH).startsWith("windows")) {
                assertEquals("Unable to read 2 bytes, got 0", ex.getCause().getMessage());
            }
            else {
                assertEquals("Unable to read 1 bytes, got 0", ex.getCause().getMessage());
            }
        }
    }
}

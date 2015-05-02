////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.NewlineAtEndOfFileCheck.MSG_KEY_NO_NEWLINE_EOF;

public class NewlineAtEndOfFileCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration checkConfig) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(checkConfig);
        return dc;
    }

    @Test
    public void testNewlineLfAtEndOfFile() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", LineSeparatorOption.LF.toString());
        final String[] expected = {};
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
        final String[] expected = {};
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
        final String[] expected = {};
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
        final String[] expected = {};
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

    @Test(expected = CheckstyleException.class)
    public void testSetLineSeparatorFailure()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NewlineAtEndOfFileCheck.class);
        checkConfig.addAttribute("lineSeparator", "ct");
        createChecker(checkConfig);
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
}

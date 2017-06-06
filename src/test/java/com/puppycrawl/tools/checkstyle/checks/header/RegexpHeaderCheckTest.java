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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck.MSG_HEADER_MISSING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.io.Closeables;
import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Unit test for RegexpHeaderCheck.
 * @author richter
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Closeables.class)
public class RegexpHeaderCheckTest extends BaseFileSetCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "header" + File.separator + "regexpheader" + File.separator + filename);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderNull() {
        // check null passes
        final RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // recreate for each test because multiple invocations fail
        final String header = null;
        instance.setHeader(header);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderEmpty() {
        // check null passes
        final RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // check empty string passes
        final String header = "";
        instance.setHeader(header);
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeaderSimple() {
        //check if reader finally closed
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(Reader.class));

        final RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // check valid header passes
        final String header = "abc.*";
        instance.setHeader(header);

        verifyStatic(times(1));
        Closeables.closeQuietly(any(Reader.class));
    }

    /**
     * Test of setHeader method, of class RegexpHeaderCheck.
     */
    @Test
    public void testSetHeader() {
        // check invalid header passes
        final RegexpHeaderCheck instance = new RegexpHeaderCheck();
        try {
            final String header = "^/**\\n * Licensed to the Apache Software Foundation (ASF)";
            instance.setHeader(header);
            fail(String.format(Locale.ROOT, "%s should have been thrown",
                    IllegalArgumentException.class));
        }
        catch (IllegalArgumentException ex) {
            assertEquals("Unable to parse format: ^/**\\n *"
                    + " Licensed to the Apache Software Foundation (ASF)",
                    ex.getMessage());
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        createChecker(checkConfig);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", "");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with invalid headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'headerFile' to '' in"
                    + " module com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck",
                    ex.getMessage());
        }
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        final URI uri = new File(getPath("InputRegexpHeader.header")).toURI();
        checkConfig.addAttribute("headerFile", uri.toString());
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testInlineRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testFailureForMultilineRegexp() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^(.*\\n.*)");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed when regexp spans multiple lines");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'header' to '^(.*\\n.*)' in module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck",
                    ex.getMessage());
        }
    }

    @Test
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader1.header"));
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti1() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti2() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti2.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti3() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 7");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti4() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 5, 6, 7");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti4.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti5() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti6() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2_4.header"));
        checkConfig.addAttribute("multiLines", "8974382");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti6.java"), expected);
    }

    @Test
    public void testRegexpHeaderSmallHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderSmallHeader.java"), expected);
    }

    @Test
    public void testEmptyMultiline()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeaderSmallHeader.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti52()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader3.header"));
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testIgnoreLinesSorted() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader5.header"));
        checkConfig.addAttribute("multiLines", "7,5,3");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnoreLinesSorted.java"), expected);
    }

    @Test
    public void testHeaderWithInvalidRegexp() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader.invalid.header"));
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        try {
            verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("line 1 in header specification is not a regular expression",
                    ex.getMessage());
        }
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader4.header"));
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testNoHeaderMissingErrorInCaseHeaderSizeEqualToFileSize() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader3.header"));
        checkConfig.addAttribute("multiLines", "1");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testReaderClosedAfterHeaderRead() throws Exception {
        mockStatic(Closeables.class);
        doNothing().when(Closeables.class);
        Closeables.closeQuietly(any(InputStreamReader.class));

        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader.header"));
        createChecker(checkConfig);

        //check if reader finally closed
        verifyStatic(times(1));
        Closeables.closeQuietly(any(InputStreamReader.class));
    }
}

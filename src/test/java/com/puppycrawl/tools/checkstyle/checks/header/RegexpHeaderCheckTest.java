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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * Unit test for RegexpHeaderCheck.
 * @author richter
 */
public class RegexpHeaderCheckTest extends BaseFileSetCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "header" + File.separator + filename);
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
        final RegexpHeaderCheck instance = new RegexpHeaderCheck();
        // check valid header passes
        final String header = "abc.*";
        instance.setHeader(header);
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
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
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
        checkConfig.addAttribute("headerFile", getPath("regexp.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeader7.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        final URI uri = new File(getPath("regexp.header")).toURI();
        checkConfig.addAttribute("headerFile", uri.toString());
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeader7.java"), expected);
    }

    @Test
    public void testInlineRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeader7.java"), expected);
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
        checkConfig.addAttribute("headerFile", getPath("regexp.header1"));
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader7.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti1() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti2() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader2.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti3() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 7");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader1.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti4() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 5, 6, 7");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader3.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti5() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeader4.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti6() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp2.header4"));
        checkConfig.addAttribute("multiLines", "8974382");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeader6.java"), expected);
    }

    @Test
    public void testRegexpHeaderSmallHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSmallHeader.java"), expected);
    }

    @Test
    public void testEmptyMultiline()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header2"));
        checkConfig.addAttribute("multiLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpSmallHeader.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti52()
            throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("regexp.header3"));
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeader5.java"), expected);
    }
}

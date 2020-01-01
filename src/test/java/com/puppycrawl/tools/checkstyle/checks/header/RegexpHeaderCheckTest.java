////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Unit test for RegexpHeaderCheck.
 */
public class RegexpHeaderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/header/regexpheader";
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
        final List<Pattern> headerRegexps = Whitebox.getInternalState(instance, "headerRegexps");

        assertTrue(headerRegexps.isEmpty(), "When header is null regexps should not be set");
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
        final List<Pattern> headerRegexps = Whitebox.getInternalState(instance, "headerRegexps");

        assertTrue(headerRegexps.isEmpty(), "When header is empty regexps should not be set");
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
        final List<Pattern> headerRegexps = Whitebox.getInternalState(instance, "headerRegexps");
        assertEquals(1, headerRegexps.size(), "Expected one pattern");
        assertEquals(header, headerRegexps.get(0).pattern(), "Invalid header regexp");
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
                + " Licensed to the Apache Software Foundation (ASF)", ex.getMessage(),
                    "Invalid exception message");
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", "");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed with invalid headerFile");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'headerFile' to ''",
                    ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getUriString("InputRegexpHeader.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testInlineRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testFailureForMultilineRegexp() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^(.*\\n.*)");
        try {
            createChecker(checkConfig);
            fail("Checker creation should not succeed when regexp spans multiple lines");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'header' to '^(.*\\n.*)'",
                    ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlines() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("header", "^/*$\\n// .*\\n\\n// Created: 2017\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getUriString("InputRegexpHeaderNewLines.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader1.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti2.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti3() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 7");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti4() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 5, 6, 7");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti4.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti5() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
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
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2_4.header"));
        checkConfig.addAttribute("multiLines", "8974382");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti6.java"), expected);
    }

    @Test
    public void testRegexpHeaderSmallHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addAttribute("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderSmallHeader.java"), expected);
    }

    @Test
    public void testEmptyMultiline()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
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
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader3.header"));
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testIgnoreLinesSorted() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader5.header"));
        checkConfig.addAttribute("multiLines", "7,5,3");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderIgnoreLinesSorted.java"), expected);
    }

    @Test
    public void testHeaderWithInvalidRegexp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader.invalid.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        try {
            verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
            fail("IllegalArgumentException is expected");
        }
        catch (IllegalArgumentException ex) {
            assertEquals("line 1 in header specification is not a regular expression",
                    ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader4.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testNoHeaderMissingErrorInCaseHeaderSizeEqualToFileSize() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addAttribute("headerFile", getPath("InputRegexpHeader3.header"));
        checkConfig.addAttribute("multiLines", "1");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

}

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

package com.puppycrawl.tools.checkstyle.checks.header;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck.MSG_HEADER_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck.MSG_HEADER_MISSING;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
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
        final List<Pattern> headerRegexps = TestUtil.getInternalState(instance, "headerRegexps");

        assertWithMessage("When header is null regexps should not be set")
                .that(headerRegexps)
                .isEmpty();
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
        final List<Pattern> headerRegexps = TestUtil.getInternalState(instance, "headerRegexps");

        assertWithMessage("When header is empty regexps should not be set")
                .that(headerRegexps)
                .isEmpty();
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
        final List<Pattern> headerRegexps = TestUtil.getInternalState(instance, "headerRegexps");
        assertWithMessage("Expected one pattern")
            .that(headerRegexps.size())
            .isEqualTo(1);
        assertWithMessage("Invalid header regexp")
            .that(headerRegexps.get(0).pattern())
            .isEqualTo(header);
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
            assertWithMessage(String.format(Locale.ROOT, "%s should have been thrown",
                    IllegalArgumentException.class)).fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("Unable to parse format: ^/**\\n *"
                    + " Licensed to the Apache Software Foundation (ASF)");
        }
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        createChecker(checkConfig);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testEmptyFilename() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", "");
        try {
            createChecker(checkConfig);
            assertWithMessage("Checker creation should not succeed with invalid headerFile").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'headerFile' to ''");
        }
    }

    @Test
    public void testRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testNonMatchingRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("header",
                                "\\/\\/ Nth Line of Header\\n\\/\\/ Nth Line of Header\\n");
        checkConfig.addProperty("multiLines", "1");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISMATCH, "\\/\\/ Nth Line of Header"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderNonMatching.java"), expected);
    }

    @Test
    public void testRegexpHeaderUrl() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getUriString("InputRegexpHeader.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testInlineRegexpHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("header", "^/*$\\n// .*\\n// Created: 2002\\n^//.*\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// Created: 2002"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testFailureForMultilineRegexp() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("header", "^(.*\\n.*)");
        try {
            createChecker(checkConfig);
            assertWithMessage(
                    "Checker creation should not succeed when regexp spans multiple lines").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module"
                    + " com.puppycrawl.tools.checkstyle.checks.header.RegexpHeaderCheck"
                    + " - Cannot set property 'header' to '^(.*\\n.*)'");
        }
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlines() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("header", "^/*$\\n// .*\\n\\n// Created: 2017\\n^//.*");
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testInlineRegexpHeaderConsecutiveNewlinesThroughConfigFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getUriString("InputRegexpHeaderNewLines.header"));
        final String[] expected = {
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderConsecutiveNewLines.java"), expected);
    }

    @Test
    public void testRegexpHeaderIgnore() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader1.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderIgnore.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti1() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti2() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti2.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti3() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3, 7");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderDefaultConfig.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti4() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3, 5, 6, 7");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti4.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti5() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti6() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2_4.header"));
        checkConfig.addProperty("multiLines", "8974382");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti6.java"), expected);
    }

    @Test
    public void testRegexpHeaderSmallHeader() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "3, 6");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderSmallHeader.java"), expected);
    }

    @Test
    public void testEmptyMultiline()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader2.header"));
        checkConfig.addProperty("multiLines", "");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderSmallHeader.java"), expected);
    }

    @Test
    public void testRegexpHeaderMulti52()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader3.header"));
        final String[] expected = {
            "1: " + getCheckMessage(MSG_HEADER_MISSING),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testIgnoreLinesSorted() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader5.header"));
        checkConfig.addProperty("multiLines", "7,5,3");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderIgnoreLinesSorted.java"), expected);
    }

    @Test
    public void testHeaderWithInvalidRegexp() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader.invalid.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String path = getPath("InputRegexpHeaderMulti52.java");
        try {
            // Content header is conflicting with Input inline header
            verify(checkConfig, path, expected);
            assertWithMessage("IllegalArgumentException is expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("line 3 in header specification is not a regular expression");
        }
    }

    @Test
    public void testNoWarningIfSingleLinedLeft() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader4.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti5.java"), expected);
    }

    @Test
    public void testNoHeaderMissingErrorInCaseHeaderSizeEqualToFileSize() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader3.header"));
        checkConfig.addProperty("multiLines", "1");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_HEADER_MISMATCH, "^$"),
        };
        // Content header is conflicting with Input inline header
        verify(checkConfig, getPath("InputRegexpHeaderMulti52.java"), expected);
    }

    @Test
    public void testCharsetProperty1() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader7.header"));
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String path = getPath("InputRegexpHeader4.java");
        // Content header is conflicting with Input inline header
        verify(checkConfig, path, expected);
    }

    @Test
    public void testCharsetProperty2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("charset", "US-ASCII");
        checkConfig.addProperty("headerFile", getPath("InputRegexpHeader7.header"));
        final String[] expected = {
            // -@cs[RegexpSinglelineJava] need for testing
            "3: " + getCheckMessage(MSG_HEADER_MISMATCH, "// some.class.��������.passed"),
        };
        final String path = getPath("InputRegexpHeader4.java");
        // Content header is conflicting with Input inline header
        verify(checkConfig, path, expected);

    }

    @Test
    public void testCharsetProperty3() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpHeaderCheck.class);
        checkConfig.addProperty("headerFile",
                getPath("InputRegexpHeader7.header"));
        checkConfig.addProperty("charset", "US-ASCII");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final String path = getPath("InputRegexpHeader3.java");
        // Content header is conflicting with Input inline header
        verify(checkConfig, path, expected);
    }
}

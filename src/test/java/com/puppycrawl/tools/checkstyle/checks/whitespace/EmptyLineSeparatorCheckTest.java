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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_INSIDE;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class EmptyLineSeparatorCheckTest
    extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator";
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyLineSeparatorCheck checkObj = new EmptyLineSeparatorCheck();
        assertArrayEquals(
            "EmptyLineSeparatorCheck#getRequiredTockens should return empty array by default",
            CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);

        final String[] expected = {
            "21: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "38: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "41: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "42: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "46: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "60: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "65: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "82: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "113: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparator.java"), expected);
    }

    @Test
    public void testAllowNoEmptyLineBetweenFields() throws Exception {

        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowNoEmptyLineBetweenFields", "true");

        final String[] expected = {
            "21: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "38: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "42: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "46: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "60: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "65: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "82: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "113: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparator.java"), expected);
    }

    @Test
    public void testHeader() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "19: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparatorHeader.java"), expected);
    }

    @Test
    public void testMultipleEmptyLinesBetweenClassMembers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = {
            "21: " + getCheckMessage(MSG_MULTIPLE_LINES, "package"),
            "24: " + getCheckMessage(MSG_MULTIPLE_LINES, "import"),
            "29: " + getCheckMessage(MSG_MULTIPLE_LINES, "CLASS_DEF"),
            "33: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "38: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "43: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
            "45: " + getCheckMessage(MSG_MULTIPLE_LINES_AFTER, "METHOD_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparatorMultipleEmptyLines.java"), expected);
    }

    @Test
    public void testFormerArrayIndexOutOfBounds() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEmptyLineSeparatorFormerException.java"), expected);
    }

    @Test
    public void testAllowMultipleFieldInClass() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        checkConfig.addAttribute("allowNoEmptyLineBetweenFields", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEmptyLineSeparatorMultipleFieldsInClass.java"), expected);
    }

    @Test
    public void testAllowMultipleImportSeparatedFromPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparatorMultipleImportEmptyClass.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyLineSeparatorCheck emptyLineSeparatorCheckObj = new EmptyLineSeparatorCheck();
        final int[] actual = emptyLineSeparatorCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };
        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testPrePreviousLineEmptiness() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputEmptyLineSeparatorPrePreviousLineEmptiness.java"), expected);
    }

    @Test
    public void testDisAllowMultipleEmptyLinesInsideClassMembers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLinesInsideClassMembers", "false");
        final String[] expected = {
            "27: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "39: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "45: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "50: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "55: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
            "56: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };
        verify(checkConfig,
                getPath("InputEmptyLineSeparatorMultipleEmptyLinesInside.java"),
                expected);
    }

    @Test
    public void testAllowMultipleEmptyLinesInsideClassMembers() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputEmptyLineSeparatorMultipleEmptyLinesInside.java"),
                expected);
    }

    @Test
    public void testAllowPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("package-info/test1/package-info.java"),
                expected);
    }

    @Test
    public void testAllowJavadocBeforePackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("package-info/test2/package-info.java"),
                expected);
    }

    @Test
    public void testDisAllowBlockCommentBeforePackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verify(checkConfig,
                getPath("package-info/test3/package-info.java"),
                expected);
    }

    @Test
    public void testAllowSingleLineCommentPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verify(checkConfig,
                getPath("package-info/test4/package-info.java"),
                expected);
    }
}

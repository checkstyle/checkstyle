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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class EmptyLineSeparatorCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyLineSeparatorCheck checkObj = new EmptyLineSeparatorCheck();
        assertArrayEquals(ArrayUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);

        final String[] expected = {
            "21: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "35: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "38: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "39: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "43: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "57: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "62: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "79: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "110: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparator.java"), expected);
    }

    @Test
    public void testAllowNoEmptyLineBetweenFields() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowNoEmptyLineBetweenFields", "true");

        final String[] expected = {
            "21: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "35: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "39: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "STATIC_INIT"),
            "43: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INSTANCE_INIT"),
            "57: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CTOR_DEF"),
            "62: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "79: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "110: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "INTERFACE_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparator.java"), expected);
    }

    @Test
    public void testHeader() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        final String[] expected = {
            "19: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparatorHeader.java"), expected);
    }

    @Test
    public void testMultipleEmptyLinesBetweenClassMembers() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = {
            "21: " + getCheckMessage(MSG_MULTIPLE_LINES, "package"),
            "24: " + getCheckMessage(MSG_MULTIPLE_LINES, "import"),
            "33: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "38: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "43: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
        };
        verify(checkConfig, getPath("InputEmptyLineSeparatorMultipleEmptyLines.java"), expected);
    }

    @Test
    public void testFormerArrayIndexOutOfBounds() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEmptyLineSeparatorFormerException.java"), expected);
    }

    @Test
    public void testAllowMultipleFieldInClass() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        checkConfig.addAttribute("allowNoEmptyLineBetweenFields", "true");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputEmptyLineSeparatorMultipleFieldsInClass.java"), expected);
    }

    @Test
    public void testAllowMultipleImportSeparatedFromPackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
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
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testPrePreviousLineEmptiness() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(EmptyLineSeparatorCheck.class);
        checkConfig.addAttribute("allowMultipleEmptyLines", "false");
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputPrePreviousLineEmptiness.java"), expected);
    }
}

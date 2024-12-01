///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalImportCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/illegalimport";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.io.*"),
            "16:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
            "21:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Date"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.List"),
            "19:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Enumeration"),
            "20:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Arrays"),
            "21:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Date"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
            "19:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.List"),
            "21:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Arrays"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }
}

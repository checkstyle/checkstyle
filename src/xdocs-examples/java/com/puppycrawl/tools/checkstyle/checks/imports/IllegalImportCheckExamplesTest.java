///
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
///

package com.puppycrawl.tools.checkstyle.checks.imports;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class IllegalImportCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/illegalimport";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "sun.applet.*"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.io.*"),
            "17:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
            "22:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Date"),
            "23:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "sun.applet.*"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.List"),
            "20:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Enumeration"),
            "21:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Arrays"),
            "22:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Date"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.sql.Connection"),
            "20:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.List"),
            "22:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "java.util.Arrays"),
            "24:1: " + getCheckMessage(
                    IllegalImportCheck.MSG_KEY, "sun.applet.*"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example5.java"), expected);
    }
}

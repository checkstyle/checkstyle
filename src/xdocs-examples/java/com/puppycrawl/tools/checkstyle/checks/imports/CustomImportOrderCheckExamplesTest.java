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

import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LEX;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_LINE_SEPARATOR;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_NONGROUP_IMPORT;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck.MSG_SEPARATED_IN_GROUP;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class CustomImportOrderCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/customimportorder";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_ORDER,
                    "STATIC", "THIRD_PARTY_PACKAGE", "java.io.File.separator"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_LINE_SEPARATOR, "javax.net.*"),
            "21:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP,
                    "org.apache.commons.io.FileUtils"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21:1: " + getCheckMessage(MSG_NONGROUP_IMPORT,
                    "org.apache.commons.io.FileUtils"),
            "22:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "24:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED, "SPECIAL_IMPORTS",
                    "org.apache.commons.io.FileUtils"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "org.apache.commons.io.FileUtils"),
            "24:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_LEX,
                    "java.io.File.separator", "java.util.Collections.*"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_LEX,
                    "java.io.File.separator",
                    "java.util.Collections.*"),
            "23:1: " + getCheckMessage(MSG_LINE_SEPARATOR,
                    "org.apache.commons.io.FileUtils"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "25:1: " + getCheckMessage(MSG_ORDER,
                    "THIRD_PARTY_PACKAGE",
                    "SPECIAL_IMPORTS",
                    "com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"),
            "26:1: " + getCheckMessage(MSG_ORDER, "THIRD_PARTY_PACKAGE",
                    "SPECIAL_IMPORTS",
                    "com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED,
                    "STANDARD_JAVA_PACKAGE", "java.time.*"),
            "24:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED,
                    "SPECIAL_IMPORTS", "javax.net.*"),
            "26:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED,
                    "THIRD_PARTY_PACKAGE", "org.apache.commons.io.FileUtils"),
            "28:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED,
                    "THIRD_PARTY_PACKAGE",
                    "com.puppycrawl.tools.checkstyle.checks.imports.CustomImportOrderCheck"),
            "29:1: " + getCheckMessage(MSG_NONGROUP_EXPECTED,
                    "THIRD_PARTY_PACKAGE",
                    "com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_SEPARATED_IN_GROUP, "org.apache.commons.io.FileUtils"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "19:1: " + getCheckMessage(MSG_ORDER,
                    "STANDARD_JAVA_PACKAGE", "SAME_PACKAGE", "java.time.*"),
            "20:1: " + getCheckMessage(MSG_ORDER,
                    "STANDARD_JAVA_PACKAGE", "SAME_PACKAGE", "javax.net.*"),
        };

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }

    @Test
    public void testExample13() throws Exception {
        final String[] expected = {

        };

        verifyWithInlineConfigParser(getPath("Example13.java"), expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_LEX,
                    "java.awt.Frame", "java.awt.color.ColorSpace"),
        };

        verifyWithInlineConfigParser(getPath("Example14.java"), expected);
    }

    @Test
    public void testExample15() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_ORDER,
                    "THIRD_PARTY_PACKAGE",
                    "SPECIAL_IMPORTS",
                    "com.google.common.annotations.GwtCompatible"),
        };

        verifyWithInlineConfigParser(getPath("Example15.java"), expected);
    }
}

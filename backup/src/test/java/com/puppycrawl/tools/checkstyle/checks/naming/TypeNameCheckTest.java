///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck.DEFAULT_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class TypeNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/typename";
    }

    @Test
    public void testSpecified()
            throws Exception {
        final String[] expected = {
            "25:14: " + getCheckMessage(MSG_INVALID_PATTERN,
                        "InputTypeName", "^inputHe"),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "15:7: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderClass2", DEFAULT_PATTERN),
            "17:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderInterface", DEFAULT_PATTERN),
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderEnum", DEFAULT_PATTERN),
            "21:23: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderAnnotation", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName2.java"), expected);
    }

    @Test
    public void testClassSpecific()
            throws Exception {
        final String[] expected = {
            "15:7: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderClass3", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName3.java"), expected);
    }

    @Test
    public void testInterfaceSpecific()
            throws Exception {
        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderInterface", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName4.java"), expected);
    }

    @Test
    public void testEnumSpecific()
            throws Exception {
        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "inputHeaderEnum", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName5.java"), expected);
    }

    @Test
    public void testAnnotationSpecific()
            throws Exception {
        final String[] expected = {
            "21:23: " + getCheckMessage(MSG_INVALID_PATTERN,
                "inputHeaderAnnotation", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeName6.java"), expected);
    }

    @Test
    public void testTypeNameRecords() throws Exception {

        final String[] expected = {
            "23:10: " + getCheckMessage(MSG_INVALID_PATTERN, "Third_Name", DEFAULT_PATTERN),
            "25:11: " + getCheckMessage(MSG_INVALID_PATTERN, "FourthName_", DEFAULT_PATTERN),
            "28:12: " + getCheckMessage(MSG_INVALID_PATTERN, "My_Record", DEFAULT_PATTERN),
            "29:16: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner__Record", DEFAULT_PATTERN),
            "34:12: " + getCheckMessage(MSG_INVALID_PATTERN, "MyRecord__", DEFAULT_PATTERN),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputTypeNameRecords.java"), expected);
    }

}

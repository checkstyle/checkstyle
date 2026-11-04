///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class StaticVariableNameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/staticvariablename";
    }

    @Test
    public void testGetRequiredTokens() {
        final StaticVariableNameCheck checkObj = new StaticVariableNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testSpecifiedA()
            throws Exception {

        final String pattern = "^s[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "36:24: " + getCheckMessage(MSG_INVALID_PATTERN, "badStatic", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName1A.java"), expected);
    }

    @Test
    public void testSpecifiedB()
            throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName1B.java"), expected);
    }

    @Test
    public void testSpecifiedC()
            throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName1C.java"), expected);
    }

    @Test
    public void testAccessTuningA()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName2A.java"), expected);
    }

    @Test
    public void testAccessTuningB()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName2B.java"), expected);
    }

    @Test
    public void testAccessTuningC()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName2C.java"), expected);
    }

    @Test
    public void testInterfaceOrAnnotationBlock()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputStaticVariableName.java"), expected);
    }

    @Test
    public void testCompactSourceFileDefault() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "15:12: " + getCheckMessage(MSG_INVALID_PATTERN, "Bad", pattern),
            "16:19: " + getCheckMessage(MSG_INVALID_PATTERN, "Public_", pattern),
            "17:22: " + getCheckMessage(MSG_INVALID_PATTERN, "Protected_", pattern),
            "18:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Private_", pattern),
            "27:16: " + getCheckMessage(MSG_INVALID_PATTERN, "InnerBad", pattern),
            "37:16: " + getCheckMessage(MSG_INVALID_PATTERN, "EnumStatic_", pattern),
            "41:16: " + getCheckMessage(MSG_INVALID_PATTERN, "RecordStatic_", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputStaticVariableNameCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileProperties1() throws Exception {
        final String pattern = "^s[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "15:12: " + getCheckMessage(MSG_INVALID_PATTERN, "bad", pattern),
            "17:22: " + getCheckMessage(MSG_INVALID_PATTERN, "protectedBad", pattern),
            "25:16: " + getCheckMessage(MSG_INVALID_PATTERN, "innerBad", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputStaticVariableNameCompactSourceFileProperties1.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileProperties2() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_INVALID_PATTERN, "Public_", pattern),
            "17:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Private_", pattern),
            "25:23: " + getCheckMessage(MSG_INVALID_PATTERN, "InnerPublic", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputStaticVariableNameCompactSourceFileProperties2.java"),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final StaticVariableNameCheck staticVariableNameCheckObj = new StaticVariableNameCheck();
        final int[] actual = staticVariableNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

}

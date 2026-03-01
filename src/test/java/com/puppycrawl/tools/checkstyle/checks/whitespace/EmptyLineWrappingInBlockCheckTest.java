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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_NO;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineWrappingInBlockCheck.MSG_EMPTY_LINE_WRAPPING_TOP_ONE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EmptyLineWrappingInBlockCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptylinewrappinginblock";
    }

    @Test
    public void testGetRequiredTokens() {
        final EmptyLineWrappingInBlockCheck checkObj = new EmptyLineWrappingInBlockCheck();
        assertWithMessage(
                "EmptyLineWrappingInBlockCheck#getRequiredTokens should return empty array")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final EmptyLineWrappingInBlockCheck checkObj = new EmptyLineWrappingInBlockCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.ANNOTATION_ARRAY_INIT,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ARRAY_INIT,
            TokenTypes.CASE_GROUP,
            TokenTypes.CLASS_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_DEF,
            TokenTypes.SLIST,
            TokenTypes.STATIC_INIT,
        };
        assertWithMessage(
                "Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final EmptyLineWrappingInBlockCheck checkObj = new EmptyLineWrappingInBlockCheck();
        final int[] actual = checkObj.getDefaultTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
        };
        assertWithMessage(
                "Invalid default tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultRequiresOneEmptyLineTopAndBottom() throws Exception {
        final String[] expected = {
            "12:44: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "18:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "20:43: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "26:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlock.java"), expected);
    }

    @Test
    public void testNoEmptyLineForbidsTopAndBottom() throws Exception {
        final String[] expected = {
            "11:55: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_NO, "{"),
            "19:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockNoEmptyLine.java"), expected);
    }

    @Test
    public void testEmptyBlockExpectsTopAndBottomOne() throws Exception {
        final String[] expected = {
            "11:54: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "11:56: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockEmptyBlock.java"), expected);
    }

    @Test
    public void testEmptyLineAllowedNoViolations() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockAllowed.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testLiteralsDefaultConfig() throws Exception {
        // Mix of violation (no empty lines) and no violation (with empty lines) cases
        final String[] expected = {
            "15:19: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "17:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "28:26: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "30:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "40:20: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "42:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "42:16: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "47:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "50:20: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "52:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "52:26: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "57:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "64:32: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "66:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockLiterals.java"), expected);
    }

    @Test
    public void testModuleConfigNoEmptyLine() throws Exception {
        final String[] expected = {
            "11:55: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_NO, "{"),
            "19:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockNoEmptyLine.java"), expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        // Inline config has topSeparator/bottomSeparator with surrounding spaces; trim() must run
        // or valueOf() would throw (kills trim mutation)
        final String[] expected = {
            "11:59: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_NO, "{"),
            "19:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_NO, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockNoEmptyLineTrim.java"), expected);
    }

    @Test
    public void testNestedSlist() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "16:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "18:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "20:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockNestedSlist.java"), expected);
    }

    @Test
    public void testCaseGroup() throws Exception {
        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "19:13: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockCaseGroup.java"), expected);
    }

    @Test
    public void testSwitchCase() throws Exception {
        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "19:13: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "25:22: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "27:13: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockSwitchCase.java"), expected);
    }

    @Test
    public void testArrayInit() throws Exception {
        final String[] expected = {
            "16:44: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "19:5: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "26:16: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "29:5: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockArrayInit.java"), expected);
    }

    @Test
    public void testNoEmptyLineSameLineBlock() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockNoEmptyLineSameLine.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testSameLineBlockWithEmptyLineConfig() throws Exception {
        final String[] expected = {
            "11:69: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockSameLineEmptyLineConfig.java"), expected);
    }

    @Test
    public void testObjBlockNull() throws Exception {
        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "18:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "23:37: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "27:9: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockObjBlockNull.java"), expected);
    }

    @Test
    public void testInterfaceAnnotationEnumDef() throws Exception {
        final String[] expected = {
            "11:71: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "13:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "15:70: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "17:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
            "19:63: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "22:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockInterfaceAnnotationEnum.java"), expected);
    }

    @Test
    public void testTwoEmptyLines() throws Exception {
        // Exactly one empty line required;
        // Wwo consecutive empty lines should violate TOP_ONE and BOTTOM_ONE
        final String[] expected = {
            "11:57: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_TOP_ONE, "{"),
            "17:1: " + getCheckMessage(MSG_EMPTY_LINE_WRAPPING_BOTTOM_ONE, "}"),
        };
        verifyWithInlineConfigParser(
                getPath("InputEmptyLineWrappingInBlockTwoEmptyLines.java"), expected);
    }

}

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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_AFTER_LCURLY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_BEFORE_RCURLY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class TypeBodyPaddingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/typebodypadding";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "48:20: " + getCheckMessage(MSG_AFTER_LCURLY),
            "56:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "58:19: " + getCheckMessage(MSG_AFTER_LCURLY),
            "60:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "62:19: " + getCheckMessage(MSG_AFTER_LCURLY),
            "72:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "81:32: " + getCheckMessage(MSG_AFTER_LCURLY),
            "83:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "93:22: " + getCheckMessage(MSG_AFTER_LCURLY),
            "96:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "105:33: " + getCheckMessage(MSG_AFTER_LCURLY),
            "107:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "116:34: " + getCheckMessage(MSG_AFTER_LCURLY),
            "118:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingDefault.java"), expected);

        final String[] expected2 = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingDefault2.java"), expected2);
    }

    @Test
    public void testAllowEmptyFalse() throws Exception {
        final String[] expected = {
            "15:38: " + getCheckMessage(MSG_AFTER_LCURLY),
            "15:39: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "19:24: " + getCheckMessage(MSG_AFTER_LCURLY),
            "20:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "23:1: " + getCheckMessage(MSG_AFTER_LCURLY),
            "23:2: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "27:1: " + getCheckMessage(MSG_AFTER_LCURLY),
            "28:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "43:64: " + getCheckMessage(MSG_AFTER_LCURLY),
            "43:65: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingAllowEmpty.java"), expected);
    }

    @Test
    public void testStartingEndingFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingFalse.java"), expected);
    }

    @Test
    public void testSkipInnerFalse() throws Exception {
        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_AFTER_LCURLY),
            "19:5: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "22:30: " + getCheckMessage(MSG_AFTER_LCURLY),
            "24:5: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "27:20: " + getCheckMessage(MSG_AFTER_LCURLY),
            "29:5: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "32:31: " + getCheckMessage(MSG_AFTER_LCURLY),
            "34:5: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingSkipInnerFalse.java"), expected);
    }

    @Test
    public void testSkipLocalFalse() throws Exception {
        final String[] expected = {
            "35:26: " + getCheckMessage(MSG_AFTER_LCURLY),
            "37:9: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "42:32: " + getCheckMessage(MSG_AFTER_LCURLY),
            "44:9: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "49:38: " + getCheckMessage(MSG_AFTER_LCURLY),
            "51:9: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "56:40: " + getCheckMessage(MSG_AFTER_LCURLY),
            "58:9: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "63:34: " + getCheckMessage(MSG_AFTER_LCURLY),
            "65:9: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(
                getPath("InputTypeBodyPaddingSkipLocalFalse.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final TypeBodyPaddingCheck check = new TypeBodyPaddingCheck();
        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final TypeBodyPaddingCheck check = new TypeBodyPaddingCheck();
        final int[] actual = check.getDefaultTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        assertWithMessage("Invalid default tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final TypeBodyPaddingCheck check = new TypeBodyPaddingCheck();
        final int[] actual = check.getRequiredTokens();
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

}

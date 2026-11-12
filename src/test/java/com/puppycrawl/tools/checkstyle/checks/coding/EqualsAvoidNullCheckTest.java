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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class EqualsAvoidNullCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/equalsavoidnull";
    }

    @Test
    public void testEqualsWithDefaultFlag() throws Exception {

        final String[] expected = {
            "44:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "46:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "48:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "50:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "52:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "54:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "76:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "78:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "80:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "82:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "84:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "86:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullFlag.java"), expected);
    }

    @Test
    public void testEqualsWithDefaultNoFlag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlag.java"), expected);
    }

    @Test
    public void testEqualsWithDefaultNoFlagIgnoreCase() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlagIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsWithDefaultNewTest() throws Exception {
        final String[] expected = {
            "16:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "23:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "25:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "27:52: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "46:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "47:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "48:60: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "50:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "51:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "52:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNewTest.java"), expected);
    }

    @Test
    public void testEqualsWithDefaultNewTestBlocks() throws Exception {
        final String[] expected = {
            "17:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "23:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "34:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "35:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "37:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "38:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "39:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "43:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "44:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "45:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "46:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "60:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "62:62: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "63:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "64:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "65:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "69:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "71:70: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "72:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "73:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "85:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "96:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNewTestBlocks.java"), expected);
    }

    @Test
    public void testEqualsWithDefaultAnonymousAndConcat() throws Exception {
        final String[] expected = {
            "13:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "43:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "44:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "45:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "49:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullAnonymousAndConcat.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception {

        final String[] expected = {
            "80:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsOnTheSameLine() throws Exception {

        final String[] expected = {
            "14:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "21:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullOnTheSameLine.java"), expected);
    }

    @Test
    public void testEqualsNested() throws Exception {

        final String[] expected = {
            "25:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "26:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "27:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "33:34: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "36:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "39:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "42:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "45:39: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNested.java"), expected);
    }

    @Test
    public void testEqualsSuperClass() throws Exception {

        final String[] expected = {
            "23:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullSuperClass.java"), expected);
    }

    @Test
    public void testInputEqualsAvoidNullEnhancedInstanceof() throws Exception {

        final String[] expected = {
            "15:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "18:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:50: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "27:38: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:38: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullEnhancedInstanceof.java"),
                expected);
    }

    @Test
    public void testMisc() throws Exception {

        final String[] expected = {
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullMisc.java"), expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {

        final String[] expected = {
            "15:23: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "34:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "41:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "49:33: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testEqualsAvoidNullTextBlocks() throws Exception {

        final String[] expected = {
            "13:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "15:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:19: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "32:31: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullTextBlocks.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final EqualsAvoidNullCheck check = new EqualsAvoidNullCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                .isNotNull();
        assertWithMessage("Default tokens should not be null")
                .that(check.getDefaultTokens())
                .isNotNull();
        assertWithMessage("Required tokens should not be null")
                .that(check.getRequiredTokens())
                .isNotNull();
    }

    @Test
    public void testEqualAvoidNull() throws Exception {
        final String[] expected = {
            "13:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "14:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "15:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "18:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };

        verifyWithInlineConfigParser(getPath("InputEqualsAvoidNull2.java"),
                expected);
    }

    @Test
    public void testEqualAvoidNullRecordPattern() throws Exception {
        final String[] expected = {
            "22:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "30:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputEqualsAvoidNullRecordPattern.java"),
                expected);
    }

    @Test
    public void testEqualsAvoidNullCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "14:26: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "25:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:27: " + getCheckMessage(MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputEqualsAvoidNullCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testEqualsAvoidNullCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputEqualsAvoidNullCompactSourceFileProperties.java"),
                expected);
    }

}

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
    public void testEqualsWithDefault() throws Exception {

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
                getPath("InputEqualsAvoidNull.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullNoFlag() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlag.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullNoFlagForBoth() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlagForBoth.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullFieldReferences() throws Exception {

        final String[] expected = {
            "16:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "22:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "23:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "25:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "27:60: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "46:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "47:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "49:68: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "51:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "52:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "53:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "78:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "80:64: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "81:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "82:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "83:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "87:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "89:72: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "90:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "91:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullFieldReferences.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullBlocks() throws Exception {

        final String[] expected = {
            "15:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "21:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "32:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "34:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "35:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "36:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "40:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "41:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "42:43: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "43:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "58:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "69:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullBlocks.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullConcatenations() throws Exception {

        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "15:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "16:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "64:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullConcatenations.java"), expected);
    }

    @Test
    public void testEqualsWithoutEqualsIgnoreCase() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullNoFlagIgnoreCase() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlagIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullNoFlagForBothIgnoreCase() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullNoFlagForBothIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullFieldReferencesIgnoreCase() throws Exception {

        final String[] expected = {
            "17:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "23:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "24:21: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "26:24: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "27:27: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "29:70: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "30:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "49:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "50:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "52:78: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "54:40: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "55:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "56:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "81:28: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "83:74: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "84:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "85:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "86:37: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "90:36: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "92:82: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "93:42: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "94:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullFieldReferencesIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullBlocksIgnoreCase() throws Exception {

        final String[] expected = {
            "15:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "20:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "21:29: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "28:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "31:25: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "32:34: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "34:32: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "35:45: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "36:33: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "40:66: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "41:51: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "42:53: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "43:55: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "58:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "69:30: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullBlocksIgnoreCase.java"), expected);
    }

    @Test
    public void testEqualsAvoidNullConcatenationsIgnoreCase() throws Exception {

        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "15:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "16:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "21:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "64:35: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };
        verifyWithInlineConfigParser(
                getPath("InputEqualsAvoidNullConcatenationsIgnoreCase.java"), expected);
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

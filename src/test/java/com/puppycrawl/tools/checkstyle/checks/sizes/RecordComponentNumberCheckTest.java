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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RecordComponentNumberCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/recordcomponentnumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final RecordComponentNumberCheck checkObj = new RecordComponentNumberCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);

    }

    @Test
    public void testGetAcceptableTokens() {
        final RecordComponentNumberCheck checkObj = new RecordComponentNumberCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultOne() throws Exception {

        final int max = 8;

        final String[] expected = {
            "49:5: " + getCheckMessage(MSG_KEY, 14, max),
            "61:9: " + getCheckMessage(MSG_KEY, 14, max),
            "68:13: " + getCheckMessage(MSG_KEY, 14, max),
            "75:17: " + getCheckMessage(MSG_KEY, 11, max),
            "93:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberOne.java"), expected);
    }

    @Test
    public void testDefaultTwo() throws Exception {

        final int max = 8;

        final String[] expected = {
            "39:3: " + getCheckMessage(MSG_KEY, 15, max),
            "50:3: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberTwo.java"), expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel1() throws Exception {

        final int max = 8;

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberTopLevel1.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel2() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberTopLevel2.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberMax1() throws Exception {

        final int max = 1;

        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 2, max),
            "28:5: " + getCheckMessage(MSG_KEY, 3, max),
            "31:5: " + getCheckMessage(MSG_KEY, 5, max),
            "46:5: " + getCheckMessage(MSG_KEY, 7, max),
            "52:5: " + getCheckMessage(MSG_KEY, 14, max),
            "60:9: " + getCheckMessage(MSG_KEY, 3, max),
            "64:9: " + getCheckMessage(MSG_KEY, 14, max),
            "69:13: " + getCheckMessage(MSG_KEY, 14, max),
            "74:17: " + getCheckMessage(MSG_KEY, 6, max),
            "88:5: " + getCheckMessage(MSG_KEY, 4, max),
            "91:5: " + getCheckMessage(MSG_KEY, 15, max),
            "102:5: " + getCheckMessage(MSG_KEY, 3, max),
            "107:5: " + getCheckMessage(MSG_KEY, 6, max),
            "119:5: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberMax1.java"), expected);
    }

    @Test
    public void testRecordComponentNumberMax20() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberMax20.java"), expected);
    }

    @Test
    public void testRecordComponentNumberPrivateModifierOne() throws Exception {

        final int max = 8;

        final String[] expected = {
            "71:9: " + getCheckMessage(MSG_KEY, 14, max),
            "78:13: " + getCheckMessage(MSG_KEY, 14, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberPrivateModifierOne.java"), expected);
    }

    @Test
    public void testRecordComponentNumberPrivateModifierTwo() throws Exception {

        final int max = 8;

        final String[] expected = {
            "49:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getPath("InputRecordComponentNumberPrivateModifierTwo.java"), expected);
    }

    /**
     * Checks that the check when given an array, creates it's own instance of
     * the array and is not re-using and possible overwriting the one given to
     * it. Without this, pitest says {@code Arrays.copyOf} is not needed, but it
     * is needed for other style checks.
     */
    @Test
    public void testCloneInAccessModifiersProperty() {
        final AccessModifierOption[] input = {
            AccessModifierOption.PACKAGE,
        };
        final RecordComponentNumberCheck check = new RecordComponentNumberCheck();
        check.setAccessModifiers(input);

        assertWithMessage("check creates its own instance of access modifier array")
            .that(System.identityHashCode(
                    TestUtil.getInternalState(
                            check, "accessModifiers", AccessModifierOption[].class)))
            .isNotEqualTo(System.identityHashCode(input));
    }

}

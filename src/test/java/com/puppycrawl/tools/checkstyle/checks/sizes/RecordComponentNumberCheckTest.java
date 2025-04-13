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
    protected String getPackageLocation() {
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
    public void testDefault() throws Exception {

        final int max = 8;

        final String[] expected = {
            "57:5: " + getCheckMessage(MSG_KEY, 14, max),
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "82:17: " + getCheckMessage(MSG_KEY, 11, max),
            "101:5: " + getCheckMessage(MSG_KEY, 15, max),
            "122:5: " + getCheckMessage(MSG_KEY, 15, max),
            "132:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumber.java"), expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel1() throws Exception {

        final int max = 8;

        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumberTopLevel1.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberTopLevel2() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumberTopLevel2.java"),
                expected);
    }

    @Test
    public void testRecordComponentNumberMax1() throws Exception {

        final int max = 1;

        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY, 2, max),
            "32:5: " + getCheckMessage(MSG_KEY, 3, max),
            "36:5: " + getCheckMessage(MSG_KEY, 5, max),
            "52:5: " + getCheckMessage(MSG_KEY, 7, max),
            "57:5: " + getCheckMessage(MSG_KEY, 14, max),
            "66:9: " + getCheckMessage(MSG_KEY, 3, max),
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "82:17: " + getCheckMessage(MSG_KEY, 6, max),
            "96:5: " + getCheckMessage(MSG_KEY, 4, max),
            "100:5: " + getCheckMessage(MSG_KEY, 15, max),
            "110:5: " + getCheckMessage(MSG_KEY, 3, max),
            "114:5: " + getCheckMessage(MSG_KEY, 6, max),
            "125:5: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumberMax1.java"), expected);
    }

    @Test
    public void testRecordComponentNumberMax20() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumberMax20.java"), expected);
    }

    @Test
    public void testRecordComponentNumberPrivateModifier() throws Exception {

        final int max = 8;

        final String[] expected = {
            "70:9: " + getCheckMessage(MSG_KEY, 14, max),
            "76:13: " + getCheckMessage(MSG_KEY, 14, max),
            "122:5: " + getCheckMessage(MSG_KEY, 15, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNumberPrivateModifier.java"), expected);
    }

    /**
     * Checks that the check when given an array, creates it's own instance of
     * the array and is not re-using and possible overwriting the one given to
     * it. Without this, pitest says {@code Arrays.copyOf} is not needed, but it
     * is needed for other style checks.
     *
     * @throws Exception if an error occurs.
     */
    @Test
    public void testCloneInAccessModifiersProperty() throws Exception {
        final AccessModifierOption[] input = {
            AccessModifierOption.PACKAGE,
        };
        final RecordComponentNumberCheck check = new RecordComponentNumberCheck();
        check.setAccessModifiers(input);

        assertWithMessage("check creates its own instance of access modifier array")
            .that(System.identityHashCode(
                TestUtil.getClassDeclaredField(RecordComponentNumberCheck.class, "accessModifiers")
                        .get(check)))
            .isNotEqualTo(System.identityHashCode(input));
    }

}

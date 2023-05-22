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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MethodLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/methodlength";
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodLengthCheck checkObj = new MethodLengthCheck();
        assertWithMessage("MethodLengthCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodLengthCheck methodLengthCheckObj =
            new MethodLengthCheck();
        final int[] actual = methodLengthCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "76:5: " + getCheckMessage(MSG_KEY, 20, 19, "longMethod"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodLengthSimple.java"), expected);
    }

    @Test
    public void testCountEmptyIsFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMethodLengthCountEmptyIsFalse.java"), expected);
    }

    @Test
    public void testWithComments() throws Exception {
        final String[] expected = {
            "34:5: " + getCheckMessage(MSG_KEY, 8, 7, "visit"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodLengthComments.java"), expected);
    }

    @Test
    public void testCountEmpty() throws Exception {
        final int max = 2;
        final String[] expected = {
            "24:5: " + getCheckMessage(MSG_KEY, 3, max, "AA"),
            "41:5: " + getCheckMessage(MSG_KEY, 3, max, "threeLines"),
            "45:5: " + getCheckMessage(MSG_KEY, 3, max, "threeLinesAndComments"),
            "53:5: " + getCheckMessage(MSG_KEY, 3, max, "threeLinesWrap"),
            "63:5: " + getCheckMessage(MSG_KEY, 10, max, "m2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodLengthCountEmptySmallSize.java"), expected);
    }

    @Test
    public void testAbstract() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMethodLengthModifier.java"), expected);
    }

    @Test
    public void testTextBlocks() throws Exception {
        final int max = 2;

        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY, 21, max, "longEmptyTextBlock"),
            "43:5: " + getCheckMessage(MSG_KEY, 10, max, "textBlock2"),
            "57:5: " + getCheckMessage(MSG_KEY, 8, max, "textBlockWithIndent"),
            "66:5: " + getCheckMessage(MSG_KEY, 12, max, "textBlockNoIndent"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodLengthTextBlocksCountEmpty.java"),
                expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {

        final int max = 2;

        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, 6, max, "MyTestRecord2"),
            "34:9: " + getCheckMessage(MSG_KEY, 5, max, "foo"),
            "42:9: " + getCheckMessage(MSG_KEY, 7, max, "MyTestRecord4"),
            "63:9: " + getCheckMessage(MSG_KEY, 15, max, "m"),
            "66:17: " + getCheckMessage(MSG_KEY, 8, max, "R76"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodLengthRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testRecordsAndCompactCtorsCountEmpty() throws Exception {
        final int max = 2;

        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, 3, max, "MyTestRecord2"),
            "32:9: " + getCheckMessage(MSG_KEY, 3, max, "foo"),
            "38:9: " + getCheckMessage(MSG_KEY, 3, max, "MyTestRecord4"),
            "55:9: " + getCheckMessage(MSG_KEY, 13, max, "m"),
            "58:17: " + getCheckMessage(MSG_KEY, 8, max, "R76"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMethodLengthCompactCtorsCountEmpty.java"),
                expected);
    }

}

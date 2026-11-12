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
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH;
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH_LAST;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class FallThroughCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/fallthrough";
    }

    @Test
    public void testDefaultJumpsAndIf() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "102:11: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultJumpsAndIf.java"),
                expected);
    }

    @Test
    public void testDefaultLoops() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "26:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultLoops.java"),
                expected);
    }

    @Test
    public void testDefaultTryCatchFinally() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "37:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultTryCatchFinally.java"),
                expected);
    }

    @Test
    public void testDefaultNestedSwitch() throws Exception {
        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "93:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "101:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultNestedSwitch.java"),
                expected);
    }

    @Test
    public void testDefaultReliefComments() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultReliefComments.java"),
                expected);
    }

    @Test
    public void testDefaultCustomReliefComment() throws Exception {
        final String[] expected = {
            "20:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "23:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "25:27: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultCustomReliefComment.java"),
                expected);
    }

    @Test
    public void testDefaultSynchronizedAndOneLineCases() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "29:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "65:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "66:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "67:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultSynchronizedAndOneLineCases.java"),
                expected);
    }

    @Test
    public void testDefaultReliefWithDash() throws Exception {
        final String[] expected = {
            "43:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "45:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "49:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "51:15: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefaultReliefWithDash.java"),
                expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough.java"),
               expected);
    }

    @Test
    public void testTryCatchInSwitch() throws Exception {
        final String[] expected = {
            "37:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(getPath("InputFallThroughTryCatchInSwitch.java"), expected);
    }

    @Test
    public void testStringSwitch() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(getPath("InputFallThroughStringSwitch.java"), expected);
    }

    @Test
    public void testCharacterSwitch() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "30:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "44:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "48:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "63:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(getPath("InputFallThroughCharacterSwitch.java"), expected);
    }

    @Test
    public void testLastCaseGroupJumpsAndIf() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "102:11: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupJumpsAndIf.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupLoops() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "26:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupLoops.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupTryCatchFinally() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "37:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupTryCatchFinally.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupNestedSwitch() throws Exception {
        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "34:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "96:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "104:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupNestedSwitch.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupReliefComments() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupReliefComments.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupCustomReliefComment() throws Exception {
        final String[] expected = {
            "20:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "23:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "25:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "27:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupCustomReliefComment.java"),
                expected);
    }

    @Test
    public void testLastCaseGroupSynchronizedAndOneLineCases() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "29:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "66:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "67:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastCaseGroupSynchronizedAndOneLineCases.java"),
                expected);
    }

    @Test
    public void testOwnPatternJumpsAndIf() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "68:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "93:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "102:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "109:11: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternJumpsAndIf.java"),
                expected);
    }

    @Test
    public void testOwnPatternLoops() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "26:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "60:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:11: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternLoops.java"),
                expected);
    }

    @Test
    public void testOwnPatternTryCatchFinally() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "37:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternTryCatchFinally.java"),
                expected);
    }

    @Test
    public void testOwnPatternNestedSwitch() throws Exception {
        final String[] expected = {
            "34:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "56:26: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "93:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "101:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternNestedSwitch.java"),
                expected);
    }

    @Test
    public void testOwnPatternReliefComments() throws Exception {
        final String[] expected = {
            "20:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "23:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "27:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "29:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "45:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "48:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "50:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "66:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "69:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "71:23: " + getCheckMessage(MSG_FALL_THROUGH),
            "87:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "90:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "92:30: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternReliefComments.java"),
                expected);
    }

    @Test
    public void testOwnPatternCustomReliefComment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternCustomReliefComment.java"),
                expected);
    }

    @Test
    public void testOwnPatternSynchronizedAndOneLineCases() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "29:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "56:12: " + getCheckMessage(MSG_FALL_THROUGH),
            "65:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "66:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "67:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughOwnPatternSynchronizedAndOneLineCases.java"),
                expected);
    }

    @Test
    public void testOwnPatternTryWithResources() throws Exception {

        final String[] expected = {
            "54:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "58:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "64:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "70:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "77:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough2.java"),
               expected);
    }

    @Test
    public void testWithEmoji() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "25:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "49:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "52:17: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughWithEmoji.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final FallThroughCheck check = new FallThroughCheck();
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
    public void testFallThroughNoElse() throws Exception {
        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "43:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "54:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "68:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "75:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "88:21: " + getCheckMessage(MSG_FALL_THROUGH),
            "94:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "96:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough2.java"),
            expected);
    }

    @Test
    public void testYield() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough9.java"),
                expected);
    }

    @Test
    public void testLastCase() throws Exception {
        final String[] expected = {
            "48:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "83:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough4.java"),
                expected);
    }

    @Test
    public void testIfElse() throws Exception {
        final String[] expected = {
            "94:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough5.java"),
                expected);
    }

    @Test
    public void testFallThrough() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "27:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "52:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "65:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "80:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "94:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "109:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough6.java"),
                expected);
    }

    @Test
    public void testFallThroughNonCompilable4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThrough10.java"),
                expected);
    }

    @Test
    public void testFallThroughComment() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "43:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughFallThroughLotsOfComment.java"),
                expected);
    }

    @Test
    public void testFallThroughComment2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThroughFallThroughLotsOfComment2.java"),
                expected);
    }

    @Test
    public void testFallThrough7() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "36:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "50:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "50:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "67:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "114:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough7.java"),
                expected);
    }

    @Test
    public void testLastLine() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "99:39: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastLineCommentCheck.java"),
                expected);
    }

    @Test
    public void testLastLine2() throws Exception {
        final String[] expected = {
            "24:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "31:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "38:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough11.java"),
                expected);
    }

    @Test
    public void testReliefCommentBetweenMultipleComment() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputFallThrough8.java"),
                expected);
    }

    @Test
    public void testLabeledBreak() throws Exception {
        final String[] expected = {
            "51:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "69:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:17: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLabeledBreak.java"),
                expected);

    }

    @Test
    public void testSwitchLabeledRules() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getPath("InputFallThroughSwitchRules.java"),
                expected);
    }

    @Test
    public void testInlineSingleCase() throws Exception {
        final String[] expected = {
            "12:17: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };

        verifyWithInlineConfigParser(
                getPath("InputFallThroughInlineSingleCase.java"),
                expected);
    }

    @Test
    public void testInlineMultipleComment() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getPath("InputFallThroughMultipleReliefPatterns.java"),
                expected);
    }

    @Test
    public void testFallThroughWithoutReliefPattern() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "45:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "54:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "60:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "77:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "94:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughWithoutReliefPattern.java"),
                expected);
    }

    @Test
    public void testFallThroughWithPatternMatching() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "31:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "35:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "44:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThroughWithPatternMatching.java"),
                expected);
    }

    @Test
    public void testFallThroughWithPatternMatchingCheckLastCase() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "33:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "36:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "36:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "48:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThroughWithPatternMatchingCheckLastCase.java"),
                expected);
    }

    @Test
    public void testFallThroughInfiniteLoop() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputFallThroughInfiniteLoop.java"), expected);
    }

    @Test
    public void testFallThroughFiniteLoop() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "25:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "27:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "33:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "39:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "47:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "69:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "71:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "82:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "86:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "93:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "112:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "116:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };

        verifyWithInlineConfigParser(
                getPath("InputFallThroughFiniteLoop.java"), expected);
    }

    @Test
    public void testFallThroughCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputFallThroughCompactSourceFileDefault.java"),
                expected);
    }

    @Test
    public void testFallThroughCompactSourceFileProperties() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "20:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "20:9: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputFallThroughCompactSourceFileProperties.java"),
                expected);
    }

}

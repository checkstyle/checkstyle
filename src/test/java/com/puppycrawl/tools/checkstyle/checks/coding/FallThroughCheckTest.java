///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH;
import static com.puppycrawl.tools.checkstyle.checks.coding.FallThroughCheck.MSG_FALL_THROUGH_LAST;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class FallThroughCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/fallthrough";
    }

    @Test
    void testDefault() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "377:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "380:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "382:27: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "529:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "531:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "533:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "535:15: " + getCheckMessage(MSG_FALL_THROUGH),
            "537:15: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughDefault.java"),
               expected);
    }

    @Test
    void tryWithResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough.java"),
               expected);
    }

    @Test
    void tryCatchInSwitch() throws Exception {
        final String[] expected = {
            "37:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(getPath("InputFallThroughTryCatchInSwitch.java"), expected);
    }

    @Test
    void stringSwitch() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(getPath("InputFallThroughStringSwitch.java"), expected);
    }

    @Test
    void characterSwitch() throws Exception {
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
    void lastCaseGroup() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "377:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "380:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "382:40: " + getCheckMessage(MSG_FALL_THROUGH),
            "384:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough.java"),
               expected);
    }

    @Test
    void ownPattern() throws Exception {

        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "46:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "61:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "95:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "131:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "153:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "178:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "187:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "194:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "212:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "230:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "260:26: " + getCheckMessage(MSG_FALL_THROUGH),
            "274:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "289:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "292:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "296:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "298:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "314:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "317:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "319:25: " + getCheckMessage(MSG_FALL_THROUGH),
            "335:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "338:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "340:23: " + getCheckMessage(MSG_FALL_THROUGH),
            "356:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "359:11: " + getCheckMessage(MSG_FALL_THROUGH),
            "361:30: " + getCheckMessage(MSG_FALL_THROUGH),
            "424:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "432:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "444:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "454:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "481:12: " + getCheckMessage(MSG_FALL_THROUGH),
            "490:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "491:9: " + getCheckMessage(MSG_FALL_THROUGH),
            "492:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough3.java"),
               expected);
    }

    @Test
    void ownPatternTryWithResources() throws Exception {

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
    void withEmoji() throws Exception {
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
    void tokensNotNull() {
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
    void fallThroughNoElse() throws Exception {
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
    void yield() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough3.java"),
                expected);
    }

    @Test
    void lastCase() throws Exception {
        final String[] expected = {
            "48:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "83:11: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough4.java"),
                expected);
    }

    @Test
    void ifElse() throws Exception {
        final String[] expected = {
            "94:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough5.java"),
                expected);
    }

    @Test
    void fallThrough() throws Exception {
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
    void fallThroughNonCompilable4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough4.java"),
                expected);
    }

    @Test
    void fallThroughComment() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "43:13: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughFallThroughLotsOfComment.java"),
                expected);
    }

    @Test
    void fallThroughComment2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputFallThroughFallThroughLotsOfComment2.java"),
                expected);
    }

    @Test
    void fallThrough7() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "36:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "50:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "50:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "64:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "111:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThrough7.java"),
                expected);
    }

    @Test
    void lastLine() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_FALL_THROUGH),
            "99:39: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLastLineCommentCheck.java"),
                expected);
    }

    @Test
    void lastLine2() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
            "22:13: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThrough5.java"),
                expected);
    }

    @Test
    void reliefCommentBetweenMultipleComment() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputFallThrough8.java"),
                expected);
    }

    @Test
    void labeledBreak() throws Exception {
        final String[] expected = {
            "51:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "55:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "69:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "78:17: " + getCheckMessage(MSG_FALL_THROUGH),
            "88:17: " + getCheckMessage(MSG_FALL_THROUGH),
        };
        verifyWithInlineConfigParser(
                getPath("InputFallThroughLabeledBreak.java"),
                expected);

    }

    @Test
    void switchLabeledRules() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputFallThroughSwitchRules.java"),
                expected);
    }

    @Test
    void inlineSingleCase() throws Exception {
        final String[] expected = {
            "12:17: " + getCheckMessage(MSG_FALL_THROUGH_LAST),
        };

        verifyWithInlineConfigParser(
                getPath("InputFallThroughInlineSingleCase.java"),
                expected);
    }

    @Test
    void inlineMultipleComment() throws Exception {
        final String[] expected = {};

        verifyWithInlineConfigParser(
                getPath("InputFallThroughMultipleReliefPatterns.java"),
                expected);
    }

    @Test
    void fallThroughWithoutReliefPattern() throws Exception {
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
    void fallThroughWithPatternMatching() throws Exception {
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
    void fallThroughWithPatternMatchingCheckLastCase() throws Exception {
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
}

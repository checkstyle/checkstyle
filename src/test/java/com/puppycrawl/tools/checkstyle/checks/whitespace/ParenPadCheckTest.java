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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck.MSG_WS_PRECEDED;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ParenPadCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testDefaultExpressionOwners() throws Exception {
        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionOwners.java"), expected);
    }

    @Test
    public void testSelectedExpressionOwners() throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "12:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "19:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadSelectedOwners.java"), expected);
    }

    @Test
    public void testExpressionBoundary() throws Exception {
        final String[] expected = {
            "23:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "23:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionBoundary.java"), expected);
    }

    @Test
    public void testExpressionBoundarySpace() throws Exception {
        final String[] expected = {
            "23:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadExpressionBoundarySpace.java"), expected);
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "70:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "89:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "89:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "250:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "259:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "259:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "298:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "298:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final String[] expected = {
            "41:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "52:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "52:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "59:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "59:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "97:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "97:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "121:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "121:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "125:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "125:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "180:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "180:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "186:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "186:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "196:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "196:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "201:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "204:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "217:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "217:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "267:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "277:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "277:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "297:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "297:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "320:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "320:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "325:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "325:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "329:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "329:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "343:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "343:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
            throws Exception {
        final String[] expected = {
            "29:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "32:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "58:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "66:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
            throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "29:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "35:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "38:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "50:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace2.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithSpace.java"),
               expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadToCheckTrimFunctionInOptionProperty.java"), expected);
    }

    @Test
    public void testNospaceWithComplexInput() throws Exception {
        final String[] expected = {
            "60:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "64:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "67:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "74:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "75:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "83:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "84:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "85:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "91:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "92:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "93:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "95:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "108:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "108:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "112:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "116:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "116:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "122:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "123:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "124:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "124:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "124:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "130:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "131:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "132:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "133:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "134:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "134:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "134:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "139:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "140:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "141:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "143:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "156:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "157:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "157:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "157:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "162:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "162:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "162:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "162:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "168:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "168:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "168:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "168:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "168:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "178:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "178:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "185:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "185:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "191:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "191:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "198:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "198:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "219:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "219:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "230:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "231:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "232:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "232:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "239:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "239:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "243:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "243:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "243:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "243:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "251:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "251:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "251:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "251:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "259:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "260:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "260:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "269:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "270:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "271:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "271:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "284:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "284:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "288:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "288:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "295:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "295:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "299:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "299:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "303:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "303:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "312:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "312:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "316:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "317:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "318:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "318:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "324:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "324:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "324:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "329:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "329:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "335:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "335:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "335:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "335:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "341:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "343:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "343:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "343:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "352:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "352:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "357:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1.java"), expected);
    }

    @Test
    public void testConfigureTokens() throws Exception {
        final String[] expected = {
            "98:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "121:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "123:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "154:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "154:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "165:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "166:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "167:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "167:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "178:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "178:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "178:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "178:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "221:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "222:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "223:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "223:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "229:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "233:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "233:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "233:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "233:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "239:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "241:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "241:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "241:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace2.java"), expected);
    }

    @Test
    public void testInvalidOption() {

        final CheckstyleException exc =
            getExpectedThrowable(CheckstyleException.class, () -> {
                final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

                verifyWithInlineConfigParser(
                        getPath("InputParenPadLeftRightAndNoSpace3.java"), expected);
            });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                + "whitespace.ParenPadCheck");
    }

    @Test
    public void testLambdaAssignment() throws Exception {
        final String[] expected = {
            "25:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "41:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "46:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final String[] expected = {
            "25:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "25:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "30:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "49:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "49:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabled() throws Exception {
        final String[] expected = {
            "27:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "33:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabledWithSpace() throws Exception {
        final String[] expected = {
            "30:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithSpaceAndDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckOnly() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "33:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnly.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "22:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "25:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "28:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "28:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "33:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnlyWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace1() throws Exception {
        final String[] expected = {
            "21:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = {
            "25:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadTryWithResources.java"), expected);
    }

    @Test
    public void testTryWithResourcesAndSuppression() throws Exception {
        final String[] expectedFiltered = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedUnfiltered = {
            "32:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyFilterWithInlineConfigParser(
                getPath("InputParenPadTryWithResourcesAndSuppression.java"),
                expectedUnfiltered,
                expectedFiltered);
    }

    @Test
    public void testNoStackoverflowError()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithLimitedResources(getPath("InputParenPadNoStackoverflowError.java"),
                expected);
    }

    @Test
    public void testParenPadCheckRecords() throws Exception {

        final String[] expected = {
            "25:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "45:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "67:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "68:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecords.java"), expected);
    }

    @Test
    public void testParenPadCheckRecordsWithSpace() throws Exception {

        final String[] expected = {
            "30:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "39:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "48:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "50:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "50:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "62:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "64:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "65:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "67:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecordsSpace.java"), expected);
    }

    @Test
    public void testParenPadCheckEmoji() throws Exception {

        final String[] expected = {
            "30:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "38:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "42:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckEmoji.java"), expected);
    }

    @Test
    public void testParenPadForSynchronized() throws Exception {

        final String[] expected = {
            "18:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForSynchronized.java"), expected);
    }

    @Test
    public void testParenPadForEnum() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadForEnum.java"), expected);
    }

    @Test
    public void testParenPadWithWhenExpression() throws Exception {
        final String[] expected = {
            "26:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "32:41: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "34:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckWhenExpression.java"), expected);
    }

    @Test
    public void testParenPadForRecordPattern() throws Exception {
        final String[] expected = {
            "23:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:73: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:74: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "65:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "70:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "70:64: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadForRecordPattern.java"), expected);
    }

    @Test
    public void testParenPadForRecordPatternWithSpaceOption() throws Exception {
        final String[] expected = {
            "14:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "14:58: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "18:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:46: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:59: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:61: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "29:72: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "33:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:48: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "45:37: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadForRecordPatternWithSpaceOption.java"),
                expected);
    }

}

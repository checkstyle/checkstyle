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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class ParenPadCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "65:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "81:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "239:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "248:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "248:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "284:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "284:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final String[] expected = {
            "36:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "44:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "44:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "48:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "48:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "83:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "83:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "104:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "104:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "105:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "105:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "157:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "157:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "160:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "160:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "167:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "167:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "169:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "172:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "185:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "185:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "232:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "242:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "242:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "259:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "259:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "280:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "280:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "282:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "282:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "283:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "283:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "294:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "294:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
            throws Exception {
        final String[] expected = {
            "24:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "50:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "55:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "58:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
            throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "18:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "21:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "21:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "24:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "27:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "30:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "34:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "39:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
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
            "55:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "55:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "56:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "59:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "63:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "64:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "68:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "69:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "72:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "73:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "74:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "76:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "89:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "89:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "90:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "93:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "94:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "97:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "98:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "99:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "99:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "99:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "101:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "102:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "103:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "104:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "105:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "105:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "105:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "106:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "107:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "108:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "110:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "123:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "124:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "124:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "124:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "125:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "125:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "125:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "125:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "126:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "126:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "130:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "130:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "134:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "134:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "137:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "137:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "141:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "141:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "150:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "150:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "156:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "156:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "164:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "165:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "166:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "166:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "170:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "170:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "171:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "171:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "171:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "171:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "174:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "174:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "174:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "174:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "177:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "178:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "178:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "184:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "185:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "186:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "186:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "196:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "196:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "197:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "197:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "201:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "201:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "202:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "202:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "203:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "203:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "211:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "212:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "213:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "214:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "214:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "217:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "217:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "217:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "218:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "218:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "221:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "221:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "221:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "221:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "222:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "223:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "223:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "223:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "227:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "227:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "229:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "153:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "153:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "161:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "162:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "171:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "171:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "171:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "171:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "209:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "211:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "214:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "218:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "218:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "218:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "218:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "219:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "220:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "220:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "220:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace2.java"), expected);
    }

    @Test
    public void testInvalidOption() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputParenPadLeftRightAndNoSpace3.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.ParenPadCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void testLambdaAssignment() throws Exception {
        final String[] expected = {
            "20:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "24:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "28:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "33:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final String[] expected = {
            "20:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "20:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "22:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "24:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "26:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "26:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "28:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "28:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "33:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabled() throws Exception {
        final String[] expected = {
            "27:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "19:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "21:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "23:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "23:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnly.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace() throws Exception {
        final String[] expected = {
            "17:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "19:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "21:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "23:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "25:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "25:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaOnlyWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace1() throws Exception {
        final String[] expected = {
            "16:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = {
            "20:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "21:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadTryWithResources.java"), expected);
    }

    @Test
    public void testTryWithResourcesAndSuppression() throws Exception {
        final String[] expectedFiltered = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedUnfiltered = {
            "23:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyFilterWithInlineConfigParser(
                getPath("InputParenPadTryWithResourcesAndSuppression.java"), expectedUnfiltered,
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
            "20:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "34:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "51:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadCheckRecords.java"), expected);
    }

    @Test
    public void testParenPadCheckRecordsWithSpace() throws Exception {

        final String[] expected = {
            "25:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "31:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "34:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "40:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "42:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "42:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "51:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "53:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "54:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "55:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadCheckRecordsSpace.java"), expected);
    }

    @Test
    public void testParenPadCheckEmoji() throws Exception {

        final String[] expected = {
            "25:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "33:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "37:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "43:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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

    /**
     * Pitest requires us to specify more concrete lower bound for condition for
     * ParenPadCheck#isAcceptableToken as nodes of first several types like CTOR_DEF,
     * METHOD_DEF will never reach this method. It is hard to recreate conditions for
     * all tokens to go through this method. We do not want to change main code to have
     * this set ok tokens more exact, because it will not be ease to understand.
     * So we have to use reflection to be sure all
     * acceptable tokens pass that check.
     */
    @Test
    public void testIsAcceptableToken() throws Exception {
        final ParenPadCheck check = new ParenPadCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        final String message = "Expected that all acceptable tokens will pass isAcceptableToken "
            + "method, but some token don't: ";

        for (int token : check.getAcceptableTokens()) {
            ast.setType(token);
            assertWithMessage(message + TokenUtil.getTokenName(token))
                    .that(TestUtil.<Boolean>invokeMethod(check, "isAcceptableToken", ast))
                    .isTrue();
        }
    }

    @Test
    public void testParenPadWithWhenExpression() throws Exception {
        final String[] expected = {
            "21:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:41: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "29:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputParenPadCheckWhenExpression.java"), expected);
    }

    @Test
    public void testParenPadForRecordPattern() throws Exception {
        final String[] expected = {
            "18:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "30:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "30:73: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "35:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "35:74: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "56:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "56:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "60:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "65:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "65:64: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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

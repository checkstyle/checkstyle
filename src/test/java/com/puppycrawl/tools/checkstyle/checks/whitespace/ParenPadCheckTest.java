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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "65:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "84:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "84:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "245:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "254:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "254:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "293:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "293:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void space()
            throws Exception {
        final String[] expected = {
            "36:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "47:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "47:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "54:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "54:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "92:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "92:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "116:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "116:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "120:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "120:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "175:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "175:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "181:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "181:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "191:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "191:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "196:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "199:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "212:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "212:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "262:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "272:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "272:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "292:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "292:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "315:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "315:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "320:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "320:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "324:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "324:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "338:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "338:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWhitespace2.java"), expected);
    }

    @Test
    public void defaultForIterator()
            throws Exception {
        final String[] expected = {
            "24:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "27:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "53:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void spaceEmptyForIterator()
            throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "18:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "24:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "24:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "30:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "33:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "40:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
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
    public void trimOptionProperty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputParenPadToCheckTrimFunctionInOptionProperty.java"), expected);
    }

    @Test
    public void nospaceWithComplexInput() throws Exception {
        final String[] expected = {
            "55:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "55:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "59:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "69:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "70:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "71:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "78:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "79:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "80:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "81:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "86:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "87:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "88:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "90:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "103:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "103:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "107:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "110:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "111:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "111:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "117:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "118:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "119:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "119:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "119:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "125:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "127:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "128:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "129:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "129:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "129:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "134:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "135:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "136:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "138:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "151:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "152:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "152:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "152:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "157:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "157:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "157:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "157:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "173:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "173:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "180:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "180:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "186:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "186:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "193:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "193:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "205:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "205:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "214:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "214:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "225:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "226:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "227:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "227:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "234:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "234:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "238:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "238:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "238:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "238:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "246:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "246:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "246:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "246:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "254:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "255:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "255:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "264:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "265:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "266:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "266:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "279:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "279:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "283:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "283:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "290:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "290:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "294:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "294:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "298:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "298:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "307:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "307:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "311:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "312:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "313:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "313:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "319:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "319:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "319:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "324:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "324:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "330:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "330:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "330:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "330:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "336:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "338:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "338:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "338:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "347:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "347:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "352:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLeftRightAndNoSpace1.java"), expected);
    }

    @Test
    public void configureTokens() throws Exception {
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
    public void invalidOption() throws Exception {

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputParenPadLeftRightAndNoSpace3.java"), expected);
            assertWithMessage("exception expected").fail();
        }
        catch (CheckstyleException exc) {
            assertWithMessage("Invalid exception message")
                .that(exc.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.ParenPadCheck - "
                    + "Cannot set property 'option' to 'invalid_option'");
        }
    }

    @Test
    public void lambdaAssignment() throws Exception {
        final String[] expected = {
            "20:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "28:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "36:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "36:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "41:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "41:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "47:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "47:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void lambdaAssignmentWithSpace() throws Exception {
        final String[] expected = {
            "20:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "20:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "25:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "28:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "31:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "31:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "36:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "36:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "44:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "44:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void lambdaCheckDisabled() throws Exception {
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
    public void lambdaCheckDisabledWithSpace() throws Exception {
        final String[] expected = {
            "30:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "30:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadWithSpaceAndDisabledLambda.java"), expected);
    }

    @Test
    public void lambdaCheckOnly() throws Exception {
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
    public void lambdaCheckOnlyWithSpace() throws Exception {
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
    public void lambdaCheckOnlyWithSpace1() throws Exception {
        final String[] expected = {
            "16:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void tryWithResources() throws Exception {
        final String[] expected = {
            "20:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "21:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "23:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadTryWithResources.java"), expected);
    }

    @Test
    public void tryWithResourcesAndSuppression() throws Exception {
        final String[] expectedFiltered = CommonUtil.EMPTY_STRING_ARRAY;
        final String[] expectedUnfiltered = {
            "23:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
        };
        verifyFilterWithInlineConfigParser(
                getPath("InputParenPadTryWithResourcesAndSuppression.java"),
                expectedUnfiltered,
                expectedFiltered);
    }

    @Test
    public void noStackoverflowError()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithLimitedResources(getPath("InputParenPadNoStackoverflowError.java"),
                expected);
    }

    @Test
    public void parenPadCheckRecords() throws Exception {

        final String[] expected = {
            "20:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "20:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "25:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "37:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "46:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "57:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "62:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecords.java"), expected);
    }

    @Test
    public void parenPadCheckRecordsWithSpace() throws Exception {

        final String[] expected = {
            "25:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "31:19: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "34:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "35:25: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "43:22: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "45:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "45:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "57:31: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "59:38: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "60:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "62:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckRecordsSpace.java"), expected);
    }

    @Test
    public void parenPadCheckEmoji() throws Exception {

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
    public void parenPadForSynchronized() throws Exception {

        final String[] expected = {
            "18:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForSynchronized.java"), expected);
    }

    @Test
    public void parenPadForEnum() throws Exception {

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
    public void isAcceptableToken() throws Exception {
        final ParenPadCheck check = new ParenPadCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        final String message = "Expected that all acceptable tokens will pass isAcceptableToken "
            + "method, but some token don't: ";

        for (int token : check.getAcceptableTokens()) {
            ast.setType(token);
            assertWithMessage("%s%s", message, TokenUtil.getTokenName(token))
                    .that(TestUtil.invokeMethod(check, "isAcceptableToken", Boolean.class, ast))
                    .isTrue();
        }
    }

    @Test
    public void parenPadWithWhenExpression() throws Exception {
        final String[] expected = {
            "21:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "25:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "27:41: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "29:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "29:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadCheckWhenExpression.java"), expected);
    }

    @Test
    public void parenPadForRecordPattern() throws Exception {
        final String[] expected = {
            "14:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "16:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "16:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "26:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "26:73: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "31:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "31:74: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "42:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "44:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "44:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "52:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "56:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "56:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "56:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "61:64: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verifyWithInlineConfigParser(
                getPath("InputParenPadForRecordPattern.java"), expected);
    }

    @Test
    public void parenPadForRecordPatternWithSpaceOption() throws Exception {
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
                getPath("InputParenPadForRecordPatternWithSpaceOption.java"),
                expected);
    }

}

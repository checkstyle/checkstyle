////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.MSG_WS_PRECEDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
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
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        final String[] expected = {
            "58:11: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "74:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "74:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "232:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "241:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "241:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "277:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "277:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "29:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "29:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "37:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "37:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "76:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "76:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "97:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "97:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "98:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "98:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "150:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "150:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "153:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "153:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "160:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "160:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "162:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "165:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "178:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "178:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "225:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "235:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "235:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "252:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "252:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "273:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "273:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "275:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "275:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "276:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "276:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "287:54: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "287:70: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWhitespace.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        final String[] expected = {
            "17:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "20:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "43:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "11:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "11:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "14:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "14:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "20:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "27:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "32:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verify(checkConfig, getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParenPadWithSpace.java"),
               expected);
    }

    @Test
    public void testNospaceWithComplexInput() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.NOSPACE.toString());
        final String[] expected = {
            "44:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "44:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "45:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "57:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "59:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:56: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:40: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "78:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "78:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "79:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "82:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "83:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "83:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "87:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "88:50: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "88:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "88:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "90:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "91:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "92:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "93:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:70: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "95:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "96:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "97:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "99:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "112:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "114:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "114:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "114:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "114:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "119:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "119:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "123:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "123:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "126:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "130:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "130:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "139:9: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "139:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "145:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "145:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "153:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "154:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "155:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "155:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "159:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "159:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "160:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "160:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "160:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "160:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "166:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "167:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "167:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "173:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "174:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "175:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "175:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "185:16: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "185:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "186:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "186:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "190:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "190:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "191:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "191:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "192:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "192:40: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "200:80: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "200:84: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "201:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "202:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "203:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "203:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "206:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "207:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "207:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "212:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "216:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "216:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "218:21: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
    }

    @Test
    public void testConfigureTokens() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_CALL");
        final String[] expected = {
            "90:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "113:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "145:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "145:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "153:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "154:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "155:35: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "155:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "201:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "202:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "203:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "203:25: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:31: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:73: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:81: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "212:54: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
    }

    @Test
    public void testInvalidOption() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", "invalid_option");

        try {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            assertEquals("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "whitespace.ParenPadCheck - "
                    + "Cannot set property 'option' to 'invalid_option'",
                ex.getMessage(), "Invalid exception message");
        }
    }

    @Test
    public void testLambdaAssignment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        final String[] expected = {
            "9:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "9:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "11:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "13:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "17:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "19:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "9:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "9:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "11:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "13:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "15:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "22:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "22:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabled() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "EXPR, METHOD_CALL, METHOD_DEF");
        final String[] expected = {
            "19:61: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:63: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWithDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabledWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        checkConfig.addAttribute("tokens", "EXPR, METHOD_CALL, METHOD_DEF");
        final String[] expected = {
            "22:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "22:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWithSpaceAndDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "9:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "9:45: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "11:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "13:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "17:46: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:57: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaOnly.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "9:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "9:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "11:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "13:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "15:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:47: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaOnlyWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace1() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "5:2: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadStartOfTheLine.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        final String[] expected = {
            "9:37: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "10:61: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadTryWithResources.java"), expected);
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
        final Method method = Whitebox.getMethod(ParenPadCheck.class,
            "isAcceptableToken", DetailAST.class);
        final DetailAstImpl ast = new DetailAstImpl();
        final String message = "Expected that all acceptable tokens will pass isAcceptableToken "
            + "method, but some token don't: ";

        for (int token : check.getAcceptableTokens()) {
            ast.setType(token);
            assertTrue((boolean) method.invoke(check, ast),
                    message + TokenUtil.getTokenName(token));
        }
    }

}

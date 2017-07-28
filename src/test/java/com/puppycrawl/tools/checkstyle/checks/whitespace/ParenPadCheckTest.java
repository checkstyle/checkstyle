////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

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
            "58:12: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "74:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "74:18: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "232:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "241:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "241:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "277:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "277:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "29:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "29:23: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "37:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "37:26: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "41:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "41:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "76:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "76:21: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "97:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "97:28: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "98:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "98:18: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "150:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "150:32: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "153:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "153:20: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "160:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "160:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "162:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "165:10: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "178:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "178:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "225:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "235:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "235:39: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "252:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "252:93: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "273:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "273:36: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "275:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "275:42: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "276:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "276:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "287:55: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
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
            "17:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "20:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "40:14: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "40:36: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "43:14: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "51:26: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
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
            "11:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "11:35: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "14:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "14:34: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "20:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "23:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "27:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "32:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
        };
        verify(checkConfig, getPath("InputParenPadForWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputParenPadWithSpace.java"),
               expected);
    }

    @Test
    public void testNospaceWithComplexInput() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.NOSPACE.toString());
        final String[] expected = {
            "44:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "44:27: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "45:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "48:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "49:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "52:27: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "53:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:52: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "54:52: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "57:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "58:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "59:24: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "60:57: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "61:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "62:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "63:41: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "65:43: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "78:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "78:28: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "79:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "82:33: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "83:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "83:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "86:29: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "87:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "88:51: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "88:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "88:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "90:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "91:32: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "92:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "93:30: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "94:69: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "95:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "96:47: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "97:42: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "99:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "112:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "113:31: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "114:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "114:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "114:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "114:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:28: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "115:55: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "119:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "119:22: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "123:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "123:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "126:22: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "126:22: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "130:19: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "130:19: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "139:10: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "139:20: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "145:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "145:46: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "153:34: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "154:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "155:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "155:46: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "159:26: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "159:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "160:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "160:29: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "160:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "160:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "166:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "167:25: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "167:50: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "173:38: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "174:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "175:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "175:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "185:17: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "185:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "186:20: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "186:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "190:30: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "190:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "191:13: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "191:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "192:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "192:39: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "200:81: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "200:83: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "201:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "202:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "203:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "203:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:14: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "206:22: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "207:18: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "207:46: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:74: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:80: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:82: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:49: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "212:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
    }

    @Test
    public void testConfigureTokens() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_CALL");
        final String[] expected = {
            "90:38: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "113:23: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "115:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "145:33: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "145:46: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "153:34: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "154:48: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "155:36: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "155:46: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:35: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "163:48: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "163:60: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "201:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "202:23: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "203:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "203:24: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "206:32: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:74: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "210:80: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "210:82: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "211:37: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:49: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "212:51: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "212:53: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
    }

    @Test
    public void testInvalidOption() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", "invalid_option");

        try {
            final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

            verify(checkConfig, getPath("InputParenPadLeftRightAndNoSpace.java"), expected);
            fail("exception expected");
        }
        catch (CheckstyleException ex) {
            final String messageStart = "cannot initialize module "
                + "com.puppycrawl.tools.checkstyle.TreeWalker - Cannot set property 'option' to "
                + "'invalid_option' in module";
            assertTrue("Invalid exception message, should start with: " + messageStart,
                ex.getMessage().startsWith(messageStart));
        }
    }

    @Test
    public void testLambdaAssignment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        final String[] expected = {
            "9:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "9:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "11:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "13:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "17:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "19:62: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambda.java"), expected);
    }

    @Test
    public void testLambdaAssignmentWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "9:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "9:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "11:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "13:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "15:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "22:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "22:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaWithSpace.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabled() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "EXPR, METHOD_CALL, METHOD_DEF");
        final String[] expected = {
            "19:62: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "19:62: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "22:21: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "22:34: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWithDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckDisabledWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        checkConfig.addAttribute("tokens", "EXPR, METHOD_CALL, METHOD_DEF");
        final String[] expected = {
            "22:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "22:33: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadWithSpaceAndDisabledLambda.java"), expected);
    }

    @Test
    public void testLambdaCheckOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "9:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "9:44: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "11:43: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "13:42: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
            "17:47: " + getCheckMessage(MSG_WS_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaOnly.java"), expected);
    }

    @Test
    public void testLambdaCheckOnlyWithSpace() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        checkConfig.addAttribute("tokens", "LAMBDA");
        final String[] expected = {
            "9:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "9:43: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "11:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "13:44: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "15:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "15:49: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
            "17:48: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "("),
            "17:56: " + getCheckMessage(MSG_WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputParenPadLambdaOnlyWithSpace.java"), expected);
    }
}

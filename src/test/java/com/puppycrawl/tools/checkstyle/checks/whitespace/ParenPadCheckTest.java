////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck.WS_PRECEDED;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ParenPadCheckTest
    extends BaseCheckTestSupport {
    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "58:12: " + getCheckMessage(WS_FOLLOWED, "("),
            "58:36: " + getCheckMessage(WS_PRECEDED, ")"),
            "74:13: " + getCheckMessage(WS_FOLLOWED, "("),
            "74:18: " + getCheckMessage(WS_PRECEDED, ")"),
            "232:27: " + getCheckMessage(WS_PRECEDED, ")"),
            "241:24: " + getCheckMessage(WS_FOLLOWED, "("),
            "241:30: " + getCheckMessage(WS_PRECEDED, ")"),
            "277:18: " + getCheckMessage(WS_FOLLOWED, "("),
            "277:23: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSpace()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "29:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "29:23: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "37:22: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "37:26: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "41:15: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "41:33: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "76:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "76:21: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "97:22: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "97:28: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "98:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "98:18: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "150:28: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "150:32: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "153:16: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "153:20: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "160:21: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "160:34: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "162:20: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "165:10: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "178:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "178:36: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "225:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "235:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "235:39: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "252:21: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "252:93: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "273:26: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "273:36: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "275:29: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "275:42: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "276:18: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "276:33: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testDefaultForIterator()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        final String[] expected = {
            "17:34: " + getCheckMessage(WS_PRECEDED, ")"),
            "20:35: " + getCheckMessage(WS_PRECEDED, ")"),
            "40:14: " + getCheckMessage(WS_FOLLOWED, "("),
            "40:36: " + getCheckMessage(WS_PRECEDED, ")"),
            "43:14: " + getCheckMessage(WS_FOLLOWED, "("),
            "48:27: " + getCheckMessage(WS_PRECEDED, ")"),
            "51:26: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testSpaceEmptyForIterator()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
            "11:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "11:35: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "14:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "14:34: " + getCheckMessage(WS_NOT_PRECEDED, ")"),
            "17:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "20:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "23:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "27:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
            "32:14: " + getCheckMessage(WS_NOT_FOLLOWED, "("),
        };
        verify(checkConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/ParenPadWithSpace.java"),
               expected);
    }

    @Test
    public void testNospaceWithComplexInput() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("option", PadOption.NOSPACE.toString());
        final String[] expected = {
            "44:27: " + getCheckMessage(WS_FOLLOWED, "("),
            "44:27: " + getCheckMessage(WS_PRECEDED, ")"),
            "45:18: " + getCheckMessage(WS_FOLLOWED, "("),
            "48:27: " + getCheckMessage(WS_FOLLOWED, "("),
            "49:19: " + getCheckMessage(WS_FOLLOWED, "("),
            "49:19: " + getCheckMessage(WS_PRECEDED, ")"),
            "52:27: " + getCheckMessage(WS_FOLLOWED, "("),
            "53:21: " + getCheckMessage(WS_FOLLOWED, "("),
            "54:18: " + getCheckMessage(WS_FOLLOWED, "("),
            "54:52: " + getCheckMessage(WS_FOLLOWED, "("),
            "54:52: " + getCheckMessage(WS_PRECEDED, ")"),
            "57:26: " + getCheckMessage(WS_FOLLOWED, "("),
            "58:22: " + getCheckMessage(WS_FOLLOWED, "("),
            "59:24: " + getCheckMessage(WS_FOLLOWED, "("),
            "60:26: " + getCheckMessage(WS_FOLLOWED, "("),
            "60:51: " + getCheckMessage(WS_FOLLOWED, "("),
            "60:57: " + getCheckMessage(WS_FOLLOWED, "("),
            "61:29: " + getCheckMessage(WS_FOLLOWED, "("),
            "62:43: " + getCheckMessage(WS_FOLLOWED, "("),
            "63:41: " + getCheckMessage(WS_FOLLOWED, "("),
            "65:43: " + getCheckMessage(WS_FOLLOWED, "("),
            "78:28: " + getCheckMessage(WS_FOLLOWED, "("),
            "78:28: " + getCheckMessage(WS_PRECEDED, ")"),
            "79:19: " + getCheckMessage(WS_PRECEDED, ")"),
            "82:33: " + getCheckMessage(WS_PRECEDED, ")"),
            "83:19: " + getCheckMessage(WS_FOLLOWED, "("),
            "83:19: " + getCheckMessage(WS_PRECEDED, ")"),
            "86:29: " + getCheckMessage(WS_PRECEDED, ")"),
            "87:35: " + getCheckMessage(WS_PRECEDED, ")"),
            "88:51: " + getCheckMessage(WS_FOLLOWED, "("),
            "88:51: " + getCheckMessage(WS_PRECEDED, ")"),
            "88:53: " + getCheckMessage(WS_PRECEDED, ")"),
            "90:38: " + getCheckMessage(WS_PRECEDED, ")"),
            "91:32: " + getCheckMessage(WS_PRECEDED, ")"),
            "92:35: " + getCheckMessage(WS_PRECEDED, ")"),
            "93:30: " + getCheckMessage(WS_PRECEDED, ")"),
            "94:60: " + getCheckMessage(WS_PRECEDED, ")"),
            "94:62: " + getCheckMessage(WS_PRECEDED, ")"),
            "94:69: " + getCheckMessage(WS_PRECEDED, ")"),
            "95:34: " + getCheckMessage(WS_PRECEDED, ")"),
            "96:47: " + getCheckMessage(WS_PRECEDED, ")"),
            "97:42: " + getCheckMessage(WS_PRECEDED, ")"),
            "99:44: " + getCheckMessage(WS_PRECEDED, ")"),
            "112:17: " + getCheckMessage(WS_FOLLOWED, "("),
            "113:23: " + getCheckMessage(WS_FOLLOWED, "("),
            "113:25: " + getCheckMessage(WS_FOLLOWED, "("),
            "113:31: " + getCheckMessage(WS_PRECEDED, ")"),
            "114:26: " + getCheckMessage(WS_FOLLOWED, "("),
            "114:28: " + getCheckMessage(WS_FOLLOWED, "("),
            "114:34: " + getCheckMessage(WS_PRECEDED, ")"),
            "114:50: " + getCheckMessage(WS_PRECEDED, ")"),
            "115:26: " + getCheckMessage(WS_FOLLOWED, "("),
            "115:28: " + getCheckMessage(WS_FOLLOWED, "("),
            "115:35: " + getCheckMessage(WS_PRECEDED, ")"),
            "115:53: " + getCheckMessage(WS_PRECEDED, ")"),
            "115:55: " + getCheckMessage(WS_PRECEDED, ")"),
            "119:17: " + getCheckMessage(WS_FOLLOWED, "("),
            "119:22: " + getCheckMessage(WS_PRECEDED, ")"),
            "123:30: " + getCheckMessage(WS_FOLLOWED, "("),
            "123:44: " + getCheckMessage(WS_PRECEDED, ")"),
            "126:22: " + getCheckMessage(WS_FOLLOWED, "("),
            "126:22: " + getCheckMessage(WS_PRECEDED, ")"),
            "130:19: " + getCheckMessage(WS_FOLLOWED, "("),
            "130:19: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("whitespace/InputParenPad.java"), expected);
    }

    @Test
    public void testConfigureTokens() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(ParenPadCheck.class);
        checkConfig.addAttribute("tokens", "METHOD_CALL");
        final String[] expected = {
            "90:38: " + getCheckMessage(WS_PRECEDED, ")"),
            "112:17: " + getCheckMessage(WS_FOLLOWED, "("),
            "113:23: " + getCheckMessage(WS_FOLLOWED, "("),
            "115:53: " + getCheckMessage(WS_PRECEDED, ")"),
            "115:55: " + getCheckMessage(WS_PRECEDED, ")"),
        };
        verify(checkConfig, getPath("whitespace/InputParenPad.java"), expected);
    }
}

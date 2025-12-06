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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ArrayBracketNoWhitespaceCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketnowhitespace";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "23:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "32:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "33:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "33:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "50:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "51:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "54:33: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "55:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "58:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "58:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "74:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "75:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "76:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "79:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "80:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "83:23: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "84:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "87:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "87:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "101:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "101:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "102:16: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "102:18: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "103:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "103:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "111:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "111:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "112:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "112:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "132:25: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "133:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "134:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "140:35: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "154:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "155:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "156:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "157:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "163:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "179:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "180:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "181:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "191:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "207:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "208:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "209:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "210:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "217:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "218:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "238:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "239:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "240:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "241:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "242:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "243:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "244:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "245:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "246:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "256:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "257:36: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "258:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "259:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "260:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "275:23: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "276:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "277:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "284:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "291:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "292:40: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "315:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "315:63: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "323:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "335:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "336:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "337:58: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "348:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "349:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "363:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "364:24: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "375:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "376:35: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDefault.java"), expected);
    }

    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "24:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "25:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "26:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "28:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "30:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "31:14: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "41:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "42:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "45:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "45:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:15: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "46:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "47:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "50:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "51:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "68:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "69:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "72:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "73:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "84:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "85:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "86:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "95:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "96:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "106:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "107:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "107:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "108:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "115:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "116:20: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "116:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "126:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "129:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "130:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "130:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "131:28: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "136:58: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "140:58: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:58: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:60: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:75: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "154:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "157:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "158:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "158:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "159:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "166:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "167:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "167:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "187:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "188:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "191:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "192:35: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "195:58: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "196:64: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "209:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "212:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "213:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "213:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "225:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "226:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "227:38: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "227:40: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "239:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "240:35: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "241:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "241:34: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }
}
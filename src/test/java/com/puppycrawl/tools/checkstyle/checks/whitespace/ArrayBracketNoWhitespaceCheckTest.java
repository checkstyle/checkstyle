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
            "22:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "23:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "25:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "33:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "35:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "46:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "48:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "51:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "52:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "55:33: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "56:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "59:30: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "59:35: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "76:21: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "77:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "78:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "81:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "82:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "85:23: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "86:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "89:20: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "89:24: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "104:13: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "104:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "108:15: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "108:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "112:13: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "112:15: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "123:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "123:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "127:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "127:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "149:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "150:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "156:35: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "170:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "171:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "172:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "173:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "179:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "195:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "205:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "221:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "222:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "223:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "224:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "231:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "232:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "252:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "253:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "254:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "255:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "256:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "257:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "258:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "259:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "260:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "270:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "271:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "272:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "273:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "274:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "289:23: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "290:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "291:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "298:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "305:32: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "305:34: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "309:40: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "309:42: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "335:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "335:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "335:63: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "335:65: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "346:42: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "358:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "359:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "359:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "373:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "374:17: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "388:25: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "389:24: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "400:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "401:35: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDefault.java"), expected);
    }

    @Test
    public void testDeclarations() throws Exception {
        final String[] expected = {
            "22:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "23:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "25:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "26:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "28:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "29:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "39:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "40:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "43:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "43:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "47:11: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "47:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "51:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "51:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "57:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "58:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "75:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "76:22: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "79:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "80:24: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "80:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "94:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "95:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "96:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "105:28: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "106:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "116:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "117:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "118:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "125:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "126:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "136:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "139:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "140:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "141:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "145:51: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "149:52: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "150:53: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "150:71: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "164:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "167:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "168:13: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "169:16: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "176:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "177:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "197:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "198:36: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "201:29: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "201:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "205:35: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "205:38: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "221:13: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "224:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "225:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "237:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "238:44: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "239:41: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "251:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "252:32: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "253:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
            getPath("InputArrayBracketNoWhitespaceDeclarations.java"), expected);
    }
}
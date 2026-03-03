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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketNoWhitespaceCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ArrayBracketNoWhitespaceExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketnowhitespace";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:34: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "19:10: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "22:18: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "22:20: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "28:20: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "31:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "35:35: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "38:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "41:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "44:10: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "47:30: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "50:44: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "52:21: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "57:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

}

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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_CONCISE_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_BREAK_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_SAME;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class GoogleRightCurlyExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/googlerightcurly";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 3),
            "20:5: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 5),
            "27:5: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 5),
            "32:14: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 14),
            "36:5: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 5),
            "42:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "43:22: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 22),
            "49:25: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 25),
            "53:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 36),
            "56:3: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "57:16: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "}", 16),
        };
        verifyWithInlineConfigParser(
                getPath("Example1.java"), expected);
    }

}

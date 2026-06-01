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

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyAloneOrEmptyCheck.MSG_KEY_ALONE_OR_EMPTY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class RightCurlyAloneOrEmptyCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "24:26: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 26),
            "27:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "29:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "32:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "33:19: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 19),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
            "38:17: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 17),
            "41:35: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 35),
            "48:25: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 25),
            "55:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

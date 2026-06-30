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
            "17:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "25:26: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 26),
            "28:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "30:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "33:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "34:19: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 19),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "37:3: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 3),
            "40:3: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 3),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "27:26: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 26),
            "32:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "36:19: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 19),
        };
        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}

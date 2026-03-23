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
            "30:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "58:30: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 30),
            "62:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "65:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
            "69:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "70:25: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 25),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "25:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "40:21: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 21),
            "42:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
            "47:29: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 29),
            "52:17: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 17),
            "54:21: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 21),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

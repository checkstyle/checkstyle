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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceBeforeEmptyBodyCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class WhitespaceBeforeEmptyBodyExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacebeforeemptybody";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_KEY, "Example1"),
            "16:20: " + getCheckMessage(MSG_KEY, "methodWarn"),
            "19:18: " + getCheckMessage(MSG_KEY, "InnerWarn"),
            "22:9: " + getCheckMessage(MSG_KEY, "static"),
            "26:30: " + getCheckMessage(MSG_KEY, "lambda"),
            "30:17: " + getCheckMessage(MSG_KEY, "while"),
            "34:8: " + getCheckMessage(MSG_KEY, "try"),
            "36:24: " + getCheckMessage(MSG_KEY, "catch"),
            "43:33: " + getCheckMessage(MSG_KEY, "anonymous class"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_KEY, "methodWarn"),
            "20:18: " + getCheckMessage(MSG_KEY, "InnerWarn"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

}

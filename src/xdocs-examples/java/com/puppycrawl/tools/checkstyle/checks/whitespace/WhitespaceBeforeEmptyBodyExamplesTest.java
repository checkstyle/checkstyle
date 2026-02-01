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
            "13:13: " + getCheckMessage(MSG_KEY, "Example1"),
            "15:16: " + getCheckMessage(MSG_KEY, "method"),
            "17:14: " + getCheckMessage(MSG_KEY, "Inner"),
            "19:27: " + getCheckMessage(MSG_KEY, "InnerInterface"),
            "22:17: " + getCheckMessage(MSG_KEY, "InnerEnum"),
            "24:9: " + getCheckMessage(MSG_KEY, "static"),
            "26:26: " + getCheckMessage(MSG_KEY, "lambda"),
            "30:17: " + getCheckMessage(MSG_KEY, "while"),
            "34:8: " + getCheckMessage(MSG_KEY, "try"),
            "35:24: " + getCheckMessage(MSG_KEY, "catch"),
            "37:12: " + getCheckMessage(MSG_KEY, "finally"),
            "41:24: " + getCheckMessage(MSG_KEY, "synchronized"),
            "46:15: " + getCheckMessage(MSG_KEY, "switch"),
            "50:29: " + getCheckMessage(MSG_KEY, "anonymous class"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_KEY, "method"),
            "18:14: " + getCheckMessage(MSG_KEY, "Inner"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

}

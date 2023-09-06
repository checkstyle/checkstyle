///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class NoWhitespaceBeforeExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespacebefore";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_KEY, "++"),
            "21:20: " + getCheckMessage(MSG_KEY, ";"),
            "24:21: " + getCheckMessage(MSG_KEY, ","),
            "29:12: " + getCheckMessage(MSG_KEY, ";"),
            "31:14: " + getCheckMessage(MSG_KEY, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "23:31: " + getCheckMessage(MSG_KEY, "..."),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "21:10: " + getCheckMessage(MSG_KEY, "."),
            "23:74: " + getCheckMessage(MSG_KEY, "::"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21:11: " + getCheckMessage(MSG_KEY, "."),
            "23:40: " + getCheckMessage(MSG_KEY, "::"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}

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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class NoWhitespaceBeforeExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nowhitespacebefore";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_KEY, ","),
            "22:31: " + getCheckMessage(MSG_KEY, "..."),
            "25:29: " + getCheckMessage(MSG_KEY, "..."),
            "34:9: " + getCheckMessage(MSG_KEY, "++"),
            "36:20: " + getCheckMessage(MSG_KEY, ";"),
            "47:5: " + getCheckMessage(MSG_KEY, ";"),
            "49:14: " + getCheckMessage(MSG_KEY, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "24:31: " + getCheckMessage(MSG_KEY, "..."),
            "36:9: " + getCheckMessage(MSG_KEY, "++"),
            "38:20: " + getCheckMessage(MSG_KEY, ";"),
            "51:14: " + getCheckMessage(MSG_KEY, ":"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "30:9: " + getCheckMessage(MSG_KEY, "."),
            "31:9: " + getCheckMessage(MSG_KEY, "."),
            "41:9: " + getCheckMessage(MSG_KEY, "."),
            "43:74: " + getCheckMessage(MSG_KEY, "::"),
            "47:9: " + getCheckMessage(MSG_KEY, "."),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "21:11: " + getCheckMessage(MSG_KEY, "."),
            "23:40: " + getCheckMessage(MSG_KEY, "::"),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

}

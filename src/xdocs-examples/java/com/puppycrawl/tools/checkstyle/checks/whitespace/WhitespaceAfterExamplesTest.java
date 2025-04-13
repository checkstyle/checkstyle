///
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_NOT_FOLLOWED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class WhitespaceAfterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "19:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
            "23:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "26:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
            "29:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "32:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "try"),
            "35:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
            "38:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "finally"),
            "41:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "catch"),
            "44:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "synchronized"),
            "51:5: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "return"),
            "59:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "yield"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "22:17: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

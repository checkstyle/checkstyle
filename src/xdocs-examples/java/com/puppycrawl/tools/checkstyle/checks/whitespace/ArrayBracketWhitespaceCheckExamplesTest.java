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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ArrayBracketWhitespaceCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketwhitespace";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "17:15: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED, "["),
            "18:14: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_FOLLOWED, "["),
            "23:15: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED, "["),
            "24:14: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_FOLLOWED, "["),
            "25:17: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED, "]"),
            "27:8: " + getCheckMessage(ArrayBracketWhitespaceCheck.MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("Example1.java"), expected);
    }
}

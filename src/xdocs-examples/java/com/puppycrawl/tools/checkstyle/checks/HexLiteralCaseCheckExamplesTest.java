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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.HexLiteralCaseCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class HexLiteralCaseCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/hexliteralcase";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:14: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "15:14: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "18:19: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "20:20: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "22:12: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "25:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "29:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "33:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "35:14: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "37:14: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "39:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "41:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "45:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "47:13: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
            "49:14: " + getCheckMessage(MSG_KEY, "Should use uppercase hexadecimal letters."),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }
}

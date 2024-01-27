///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_ILLEGAL_FOLLOW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.GenericWhitespaceCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class GenericWhitespaceCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/genericwhitespace";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_WS_NOT_PRECEDED, "<"),
            "34:22: " + getCheckMessage(MSG_WS_ILLEGAL_FOLLOW, ">"),
            "35:7: " + getCheckMessage(MSG_WS_FOLLOWED, "<"),
            "39:16: " + getCheckMessage(MSG_WS_PRECEDED, "<"),
            "43:28: " + getCheckMessage(MSG_WS_PRECEDED, ">"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

}

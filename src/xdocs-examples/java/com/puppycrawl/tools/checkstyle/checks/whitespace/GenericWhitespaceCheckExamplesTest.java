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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GenericWhitespaceCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/genericwhitespace";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_PRECEDED, "<"),
            "16:9: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_NOT_PRECEDED, "<"),
            "17:26: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_FOLLOWED, ">"),
            "18:22: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_ILLEGAL_FOLLOW, ">"),
            "19:19: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_PRECEDED, ">"),
            "20:19: " + getCheckMessage(GenericWhitespaceCheck.MSG_WS_FOLLOWED, ">"),

        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("Example2.java"), expected);
    }
}

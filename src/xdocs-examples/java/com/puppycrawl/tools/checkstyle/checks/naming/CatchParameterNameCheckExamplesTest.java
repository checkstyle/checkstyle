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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class CatchParameterNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    private static final String CATCH_PARAM_NAME_PATTERN_1 = "^(e|t|ex|[a-z][a-z][a-zA-Z]+|_)$";
    private static final String CATCH_PARAM_NAME_PATTERN_2 = "^[a-z][a-zA-Z0-9]+$";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/catchparametername";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:40: " + getCheckMessage(MSG_INVALID_PATTERN, "e123",
                    CATCH_PARAM_NAME_PATTERN_1),
            "20:35: " + getCheckMessage(MSG_INVALID_PATTERN, "ab",
                    CATCH_PARAM_NAME_PATTERN_1),
            "23:35: " + getCheckMessage(MSG_INVALID_PATTERN, "aBC",
                    CATCH_PARAM_NAME_PATTERN_1),
            "26:24: " + getCheckMessage(MSG_INVALID_PATTERN, "EighthException",
                    CATCH_PARAM_NAME_PATTERN_1),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "18:34: " + getCheckMessage(MSG_INVALID_PATTERN, "e",
                    CATCH_PARAM_NAME_PATTERN_2),
            "26:24: " + getCheckMessage(MSG_INVALID_PATTERN, "EighthException",
                    CATCH_PARAM_NAME_PATTERN_2),
            "28:24: " + getCheckMessage(MSG_INVALID_PATTERN, "t",
                    CATCH_PARAM_NAME_PATTERN_2),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

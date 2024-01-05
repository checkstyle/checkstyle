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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class CatchParameterNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    private static final String CATCH_PARAM_NAME_PATTERN = "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/catchparametername";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "21:40: " + getCheckMessage(MSG_INVALID_PATTERN, "e123",
                    CATCH_PARAM_NAME_PATTERN),
            "25:35: " + getCheckMessage(MSG_INVALID_PATTERN, "ab",
                    CATCH_PARAM_NAME_PATTERN),
            "30:35: " + getCheckMessage(MSG_INVALID_PATTERN, "aBC",
                    CATCH_PARAM_NAME_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "28:24: " + getCheckMessage(MSG_INVALID_PATTERN, "FourthException",
                    "^[a-z][a-zA-Z0-9]+$"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

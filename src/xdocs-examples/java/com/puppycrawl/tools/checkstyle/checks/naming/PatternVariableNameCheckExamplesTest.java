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

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class PatternVariableNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/patternvariablename";
    }

    @Test
    public void testExample1() throws Exception {

        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";

        final String[] expected = {
            "15:30: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "17:31: " + getCheckMessage(MSG_INVALID_PATTERN, "num_1", pattern),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {

        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final String[] expected = {
            "17:30: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {

        final String pattern = "^[a-z][_a-zA-Z0-9]{2,}$";

        final String[] expected = {
            "17:30: " + getCheckMessage(MSG_INVALID_PATTERN, "STRING", pattern),
            "20:31: " + getCheckMessage(MSG_INVALID_PATTERN, "n", pattern),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expected = {
            "37:30: " + getCheckMessage(MSG_INVALID_PATTERN, "BAD", "^([a-z][a-zA-Z0-9]*|_)$"),
            "40:36: " + getCheckMessage(MSG_INVALID_PATTERN, "bad", "^[A-Z][A-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example4.java"), expected);
    }
}

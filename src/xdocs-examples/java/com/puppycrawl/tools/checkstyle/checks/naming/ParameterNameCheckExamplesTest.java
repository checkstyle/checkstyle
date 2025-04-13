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

import static com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ParameterNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/parametername";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_INVALID_PATTERN, "V2", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_INVALID_PATTERN, "V3", "^[a-z][_a-zA-Z0-9]+$"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_INVALID_PATTERN, "V2", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "16:20: " + getCheckMessage(MSG_INVALID_PATTERN, "v_2", "^[a-z][a-zA-Z0-9]+$"),
            "17:20: " + getCheckMessage(MSG_INVALID_PATTERN, "V3", "^[a-z][a-zA-Z0-9]+$"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "25:26: " + "Parameter name 'V2' must match pattern '^[a-z]([a-z0-9][a-zA-Z0-9]*)?$'",
            "27:23: " + "Parameter name 'b' must match pattern '^[a-z][a-z0-9][a-zA-Z0-9]*$'",
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

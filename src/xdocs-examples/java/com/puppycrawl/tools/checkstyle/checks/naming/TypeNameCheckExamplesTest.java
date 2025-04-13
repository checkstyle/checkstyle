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

public class TypeNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/typename";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_INVALID_PATTERN, "Third_Name", "^[A-Z][a-zA-Z0-9]*$"),
            "16:17: " + getCheckMessage(MSG_INVALID_PATTERN, "FourthName_", "^[A-Z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:7: " + getCheckMessage(MSG_INVALID_PATTERN, "Example2", "^[a-z](_?[a-zA-Z0-9]+)*$"),
            "18:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "SecondName", "^[a-z](_?[a-zA-Z0-9]+)*$"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19:13: " + getCheckMessage(MSG_INVALID_PATTERN, "SecondName", "^I_[a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}

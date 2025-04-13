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

public class RecordComponentNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/recordcomponentname";
    }

    @Test
    public void testExample1() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "15:22: " + getCheckMessage(MSG_INVALID_PATTERN, "Values", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {

        final String pattern = "^[a-z]+$";

        final String[] expected = {
            "17:22: " + getCheckMessage(MSG_INVALID_PATTERN, "Values", pattern),
            "19:22: " + getCheckMessage(MSG_INVALID_PATTERN, "myNumber", pattern),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("Example2.java"), expected);
    }
}

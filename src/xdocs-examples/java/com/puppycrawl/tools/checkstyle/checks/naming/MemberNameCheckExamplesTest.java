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

import static com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class MemberNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/membername";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "21:14: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM1", "^[a-z][a-zA-Z0-9]*$"),
            "23:17: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM2", "^[a-z][a-zA-Z0-9]*$"),
            "25:7: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM3", "^[a-z][a-zA-Z0-9]*$"),
            "27:15: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM4", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:14: " + getCheckMessage(MSG_INVALID_PATTERN, "num1", "^m[A-Z][a-zA-Z0-9]*$"),
            "20:15: " + getCheckMessage(MSG_INVALID_PATTERN, "num4", "^m[A-Z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM2", "^[a-z][a-zA-Z0-9]*$"),
            "18:7: " + getCheckMessage(MSG_INVALID_PATTERN, "NUM3", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}

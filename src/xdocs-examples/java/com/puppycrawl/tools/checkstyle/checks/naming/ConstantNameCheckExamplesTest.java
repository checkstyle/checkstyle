///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ConstantNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    private static final String DEFAULT_PATTERN = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/constantname";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "17:20: " + getCheckMessage(MSG_INVALID_PATTERN, "third_Constant3", DEFAULT_PATTERN),
            "18:28: " + getCheckMessage(MSG_INVALID_PATTERN, "fourth_Const4", DEFAULT_PATTERN),
            "19:27: " + getCheckMessage(MSG_INVALID_PATTERN, "log", DEFAULT_PATTERN),
            "20:30: " + getCheckMessage(MSG_INVALID_PATTERN, "logger", DEFAULT_PATTERN),
            "21:20: " + getCheckMessage(MSG_INVALID_PATTERN, "loggerMYSELF", DEFAULT_PATTERN),
            "23:30: " + getCheckMessage(MSG_INVALID_PATTERN, "myselfConstant", DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern = "^log(ger)?$|^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_INVALID_PATTERN, "third_Constant3", pattern),
            "21:28: " + getCheckMessage(MSG_INVALID_PATTERN, "fourth_Const4", pattern),
            "24:20: " + getCheckMessage(MSG_INVALID_PATTERN, "loggerMYSELF", pattern),
            "26:30: " + getCheckMessage(MSG_INVALID_PATTERN, "myselfConstant", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_INVALID_PATTERN, "third_Constant3", DEFAULT_PATTERN),
            "21:28: " + getCheckMessage(MSG_INVALID_PATTERN, "fourth_Const4", DEFAULT_PATTERN),
            "24:20: " + getCheckMessage(MSG_INVALID_PATTERN, "loggerMYSELF", DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}

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

package com.puppycrawl.tools.checkstyle.checks.coding;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class MultipleStringLiteralsCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/multiplestringliterals";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY,
                    "\"StringContents\"", 2),
            "18:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\"DuoString\"", 2),
            "20:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "16:14: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY,
                    "\"StringContents\"", 2),
            "21:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\"DuoString\"", 2),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "15:14: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY,
                    "\"StringContents\"", 2),
            "16:15: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\"unchecked\"", 2),
            "20:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\"DuoString\"", 2),
            "22:17: " + getCheckMessage(MultipleStringLiteralsCheck.MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}

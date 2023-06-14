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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_MULTIPLE_LINES_INSIDE;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class EmptyLineSeparatorExamplesTest extends AbstractModuleTestSupport {
    @Override
    protected String getResourceLocation() {
        return "xdocs-examples";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "11:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "18:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "25:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "24:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "26:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "19:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "22:3: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "25:3: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
            "26:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "12:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "19:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "26:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "27:19: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

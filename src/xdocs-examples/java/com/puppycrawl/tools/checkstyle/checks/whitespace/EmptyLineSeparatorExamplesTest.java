///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class EmptyLineSeparatorExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/emptylineseparator";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "15:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "16:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "19:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "26:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "17:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "28:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "17:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "18:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "28:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "17:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "18:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "21:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "24:3: " + getCheckMessage(MSG_MULTIPLE_LINES, "VARIABLE_DEF"),
            "27:3: " + getCheckMessage(MSG_MULTIPLE_LINES, "METHOD_DEF"),
            "28:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "package"),
            "18:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "import"),
            "19:1: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "CLASS_DEF"),
            "22:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "VARIABLE_DEF"),
            "29:3: " + getCheckMessage(MSG_SHOULD_BE_SEPARATED, "METHOD_DEF"),
            "30:17: " + getCheckMessage(MSG_MULTIPLE_LINES_INSIDE),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }
}

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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_CHILD_ERROR_MULTI;
import static com.puppycrawl.tools.checkstyle.checks.indentation.IndentationCheck.MSG_ERROR;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class IndentationCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/indentation";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_CHILD_ERROR, "case", 12, 8),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "26:18: " + getCheckMessage(MSG_ERROR, "int", 17, 8),
            "28:17: " + getCheckMessage(MSG_ERROR, "&&", 16, 12),
        };
        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "13:7: " + getCheckMessage(MSG_ERROR, "member def type", 6, 4),
            "17:11: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 10, 8),
        };
        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "19:7: " + getCheckMessage(MSG_CHILD_ERROR, "method def", 6, 4),
        };
        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_ERROR, "int", 8, 12),
            "27:13: " + getCheckMessage(MSG_ERROR, "&&", 12, 16),
        };
        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_ERROR, "throws", 8, 12),
        };
        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "22:3: " + getCheckMessage(MSG_CHILD_ERROR_MULTI, "array initialization", 2, "6, 8"),
        };
        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_ERROR, "method def lcurly", 4, 6),
            "21:5: " + getCheckMessage(MSG_ERROR, "method def rcurly", 4, 6),
        };
        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }
}

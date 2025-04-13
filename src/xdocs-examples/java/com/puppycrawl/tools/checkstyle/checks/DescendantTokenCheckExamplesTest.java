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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MIN;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MAX;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class DescendantTokenCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/descendanttoken";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
            "63:5: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
            "68:5: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
            "70:9: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "47:5: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "LITERAL_SWITCH", "LITERAL_CASE"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "68:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_SWITCH", "LITERAL_SWITCH"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "21:12: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "FOR_CONDITION", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "21:10: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "FOR_INIT", "EXPR"),
            "33:10: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "FOR_INIT", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_CATCH", "LITERAL_RETURN"),
            "29:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_CATCH", "LITERAL_RETURN"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "37:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_CATCH", "LITERAL_TRY"),
            "43:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_TRY"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expected = {
            "20:3: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "METHOD_DEF", "VARIABLE_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example8.java"), expected);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expected = {
            "24:3: " + getCheckMessage(MSG_KEY_MAX, 3, 2, "METHOD_DEF", "LITERAL_RETURN"),
            "33:3: " + getCheckMessage(MSG_KEY_MAX, 3, 2, "METHOD_DEF", "LITERAL_RETURN"),
        };

        verifyWithInlineConfigParser(getPath("Example9.java"), expected);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expected = {
            "20:22: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "LITERAL_THROWS", "IDENT"),
        };

        verifyWithInlineConfigParser(getPath("Example10.java"), expected);
    }

    @Test
    public void testExample11() throws Exception {
        final String[] expected = {
            "21:3: " + getCheckMessage(MSG_KEY_MAX, 3, 2, "METHOD_DEF", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("Example11.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "23:7: " + "Empty statement is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }

    @Test
    public void testExample13() throws Exception {
        final String[] expected = {
            "22:1: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "CLASS_DEF", "VARIABLE_DEF"),
            "32:1: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "INTERFACE_DEF", "VARIABLE_DEF"),
        };

        verifyWithInlineConfigParser(getPath("Example13.java"), expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
            "23:14: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "NOT_EQUAL"),
        };

        verifyWithInlineConfigParser(getPath("Example14.java"), expected);
    }

    @Test
    public void testExample15() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "EQUAL", "STRING_LITERAL"),
        };

        verifyWithInlineConfigParser(getPath("Example15.java"), expected);
    }

    @Test
    public void testExample16() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_ASSERT", "POST_INC"),
        };

        verifyWithInlineConfigParser(getPath("Example16.java"), expected);
    }
}

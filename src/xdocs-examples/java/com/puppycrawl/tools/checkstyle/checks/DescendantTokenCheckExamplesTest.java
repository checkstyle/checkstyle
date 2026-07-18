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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MIN;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MAX;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class DescendantTokenCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code DescendantTokenCheckExamplesTest} instance.
     */
    public DescendantTokenCheckExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/descendanttoken";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "LITERAL_SWITCH", "LITERAL_CASE"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "40:11: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "FOR_CONDITION", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "40:10: " + getCheckMessage(MSG_KEY_MIN, 0, 1, "FOR_INIT", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "30:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_CATCH", "LITERAL_RETURN"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_CATCH", "LITERAL_TRY"),
            "34:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_FINALLY", "LITERAL_TRY"),
        };

        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "20:3: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "METHOD_DEF", "VARIABLE_DEF"),
        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = {
            "19:3: " + getCheckMessage(MSG_KEY_MAX, 3, 2, "METHOD_DEF", "LITERAL_RETURN"),
        };

        verifyWithInlineConfigParser(getPath("UseCase3.java"), expected);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expected = {
            "20:11: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "LITERAL_THROWS", "IDENT"),
        };

        verifyWithInlineConfigParser(getPath("UseCase4.java"), expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expected = {
            "19:3: " + getCheckMessage(MSG_KEY_MAX, 10, 2, "METHOD_DEF", "EXPR"),
        };

        verifyWithInlineConfigParser(getPath("UseCase5.java"), expected);
    }

    @Test
    public void testExample12() throws Exception {
        final String[] expected = {
            "51:5: " + "Empty statement is not allowed.",
        };

        verifyWithInlineConfigParser(getPath("Example12.java"), expected);
    }

    @Test
    public void testUseCase6() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY_MAX, 2, 1, "CLASS_DEF", "VARIABLE_DEF"),
        };

        verifyWithInlineConfigParser(getPath("UseCase6.java"), expected);
    }

    @Test
    public void testExample14() throws Exception {
        final String[] expected = {
            "47:14: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
        };

        verifyWithInlineConfigParser(getPath("Example14.java"), expected);
    }

    @Test
    public void testUseCase7() throws Exception {
        final String[] expected = {
            "46:29: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "EQUAL", "STRING_LITERAL"),
        };

        verifyWithInlineConfigParser(getPath("UseCase7.java"), expected);
    }

    @Test
    public void testUseCase8() throws Exception {
        final String[] expected = {
            "50:5: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_ASSERT", "POST_INC"),
        };

        verifyWithInlineConfigParser(getPath("UseCase8.java"), expected);
    }

    @Test
    public void testExample17() throws Exception {
        final String[] expected = {
            "24:5: Switch must contain at least one default branch.",
        };

        verifyWithInlineConfigParser(getPath("Example17.java"), expected);
    }

}

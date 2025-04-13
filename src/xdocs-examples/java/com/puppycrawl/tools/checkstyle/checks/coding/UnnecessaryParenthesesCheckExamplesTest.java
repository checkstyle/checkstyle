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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_ASSIGN;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_EXPR;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_IDENT;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LAMBDA;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryParenthesesCheck.MSG_LITERAL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class UnnecessaryParenthesesCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessaryparentheses";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "17:18: " + getCheckMessage(MSG_ASSIGN),
            "19:13: " + getCheckMessage(MSG_IDENT, "square"),
            "25:19: " + getCheckMessage(MSG_LITERAL, "0"),
            "27:15: " + getCheckMessage(MSG_ASSIGN),
            "28:20: " + getCheckMessage(MSG_ASSIGN),
            "28:24: " + getCheckMessage(MSG_IDENT, "square"),
            "36:21: " + getCheckMessage(MSG_LAMBDA),
            "44:9: " + getCheckMessage(MSG_EXPR),
            "44:31: " + getCheckMessage(MSG_EXPR),
            "44:53: " + getCheckMessage(MSG_EXPR),
            "48:9: " + getCheckMessage(MSG_EXPR),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_EXPR),
            "26:19: " + getCheckMessage(MSG_EXPR),
        };
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class UnusedLocalVariableCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedlocalvariable";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "k"),
            "26:5: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "arr"),
            "32:5: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "s"),
            "39:5: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "s"),
            "49:10: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "i"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "k"),
            "27:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "arr"),
            "33:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "34:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "40:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "50:10: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "52:10: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("Example2.java"), expected);
    }
}

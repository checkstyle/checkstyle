///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;

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
            "15:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "k"),
            "25:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "arr"),
            "31:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "37:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "48:10: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }
}

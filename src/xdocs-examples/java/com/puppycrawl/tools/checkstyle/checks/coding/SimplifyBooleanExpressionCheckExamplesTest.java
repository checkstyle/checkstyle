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

public class SimplifyBooleanExpressionCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/simplifybooleanexpression";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
            "19:11: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
            "21:11: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
            "22:13: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
            "24:12: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
            "27:23: " + getCheckMessage(SimplifyBooleanExpressionCheck.MSG_KEY),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }
}

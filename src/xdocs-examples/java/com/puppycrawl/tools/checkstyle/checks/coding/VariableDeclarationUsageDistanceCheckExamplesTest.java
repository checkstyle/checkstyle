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

import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck.MSG_KEY_EXT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class VariableDeclarationUsageDistanceCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/variabledeclarationusagedistance";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY_EXT, "num", 4, 3),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {

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
            "16:5: " + getCheckMessage(MSG_KEY_EXT, "num", 4, 3),
            "28:5: " + getCheckMessage(MSG_KEY_EXT, "count", 4, 3),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_KEY, "num", 4, 3),
            "17:5: " + getCheckMessage(MSG_KEY, "PI", 5, 3),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

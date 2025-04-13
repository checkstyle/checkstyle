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

import static com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.ReturnCountCheck.MSG_KEY_VOID;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ReturnCountCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/returncount";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "25:5: " + getCheckMessage(MSG_KEY, 4, 3),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "25:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "41:5: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, 4, 2),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "26:5: " + getCheckMessage(MSG_KEY, 4, 3),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "34:5: " + getCheckMessage(MSG_KEY, 4, 2),
            "41:42: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

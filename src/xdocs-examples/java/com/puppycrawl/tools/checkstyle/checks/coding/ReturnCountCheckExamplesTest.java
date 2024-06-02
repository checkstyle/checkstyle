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

import com.puppycrawl.tools.checkstyle.checks.coding.returncount.MSG_KEY;
import com.puppycrawl.tools.checkstyle.checks.coding.returncount.MSG_KEY_VOID;
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
            "53:9: " + getCheckMessage(MSG_KEY, 4, 3)
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "40:9: " + getCheckMessage(MSG_KEY, 3, 2),
            "51:13: " + getCheckMessage(MSG_KEY, 4, 2),
            "71:9: " + getCheckMessage(MSG_KEY_VOID, 1, 0)
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY, 3, 2),
            "52:13: " + getCheckMessage(MSG_KEY, 4, 2)
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "41:9: " + getCheckMessage(MSG_KEY, 4, 3),
            "52:13: " + getCheckMessage(MSG_KEY, 5, 3)
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_KEY_VOID, 1, 0),
            "49:9: " + getCheckMessage(MSG_KEY, 3, 2),
            "60:13: " + getCheckMessage(MSG_KEY, 4, 2),
            "69:9: " + getCheckMessage(MSG_KEY, 2, 1)
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

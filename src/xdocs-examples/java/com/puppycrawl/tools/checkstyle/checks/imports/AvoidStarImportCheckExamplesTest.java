///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class AvoidStarImportCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/avoidstarimport";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
                "11:15: " + getCheckMessage(MSG_KEY, "java.io.*"),
                "12:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.*"),
                "13:17: " + getCheckMessage(MSG_KEY, "java.util.*"),
                "14:16: " + getCheckMessage(MSG_KEY, "java.net.*"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
                "15:17: " + getCheckMessage(MSG_KEY, "java.util.*"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
                "14:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.*"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
                "13:15: " + getCheckMessage(MSG_KEY, "java.io.*"),
                "15:17: " + getCheckMessage(MSG_KEY, "java.util.*"),
                "16:16: " + getCheckMessage(MSG_KEY, "java.net.*"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
                "15:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.*"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
                "16:17: " + getCheckMessage(MSG_KEY, "java.util.*"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }
}

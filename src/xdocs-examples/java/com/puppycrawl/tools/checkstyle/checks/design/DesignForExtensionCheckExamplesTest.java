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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class DesignForExtensionCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    /**
     * Creates a new {@code DesignForExtensionCheckExamplesTest} instance.
     */
    public DesignForExtensionCheckExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/designforextension";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_KEY, "Example1", "m1"),
            "18:3: " + getCheckMessage(MSG_KEY, "Example1", "m2"),
            "43:3: " + getCheckMessage(MSG_KEY, "Example1", "toString"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY, "Example2", "m1"),
            "20:3: " + getCheckMessage(MSG_KEY, "Example2", "m2"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY, "Example3", "m1"),
            "20:3: " + getCheckMessage(MSG_KEY, "Example3", "m2"),
            "37:3: " + getCheckMessage(MSG_KEY, "Example3", "m7"),
            "43:3: " + getCheckMessage(MSG_KEY, "Example3", "m8"),
            "45:3: " + getCheckMessage(MSG_KEY, "Example3", "toString"),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_KEY, "Example4", "m1"),
            "21:3: " + getCheckMessage(MSG_KEY, "Example4", "m2"),
            "38:3: " + getCheckMessage(MSG_KEY, "Example4", "m7"),
            "46:3: " + getCheckMessage(MSG_KEY, "Example4", "toString"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

}

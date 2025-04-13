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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class InterfaceMemberImpliedModifierCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/interfacememberimpliedmodifier";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:3: " + getCheckMessage(MSG_KEY, "final"),
            "16:3: " + getCheckMessage(MSG_KEY, "public"),
            "16:3: " + getCheckMessage(MSG_KEY, "static"),
            "24:3: " + getCheckMessage(MSG_KEY, "abstract"),
            "24:3: " + getCheckMessage(MSG_KEY, "public"),
            "28:3: " + getCheckMessage(MSG_KEY, "public"),
            "28:3: " + getCheckMessage(MSG_KEY, "static"),
            "32:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "32:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19:3: " + getCheckMessage(MSG_KEY, "final"),
            "19:3: " + getCheckMessage(MSG_KEY, "public"),
            "19:3: " + getCheckMessage(MSG_KEY, "static"),
            "27:3: " + getCheckMessage(MSG_KEY, "abstract"),
            "27:3: " + getCheckMessage(MSG_KEY, "public"),
            "35:5: " + getCheckMessage(MSG_KEY, "abstract"),
            "35:5: " + getCheckMessage(MSG_KEY, "public"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

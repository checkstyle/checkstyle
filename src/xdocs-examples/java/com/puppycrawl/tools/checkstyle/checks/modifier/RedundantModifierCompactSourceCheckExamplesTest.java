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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCompactSourceCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class RedundantModifierCompactSourceCheckExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/"
                + "redundantmodifiercompactsource";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, "public"),
            "12:8: " + getCheckMessage(MSG_KEY, "static"),
            "17:1: " + getCheckMessage(MSG_KEY, "protected"),
            "17:11: " + getCheckMessage(MSG_KEY, "final"),
            "17:17: " + getCheckMessage(MSG_KEY, "strictfp"),
            "24:1: " + getCheckMessage(MSG_KEY, "private"),
            "26:1: " + getCheckMessage(MSG_KEY, "public"),
            "26:8: " + getCheckMessage(MSG_KEY, "static"),
            "32:1: " + getCheckMessage(MSG_KEY, "protected"),
            "35:1: " + getCheckMessage(MSG_KEY, "private"),
        };
        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

}

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

import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.coding.DefaultComesLastCheck.MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class DefaultComesLastCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/defaultcomeslast";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "34:7: " + getCheckMessage(MSG_KEY),
            "50:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "36:7: " + getCheckMessage(MSG_KEY),
            "45:7: " + getCheckMessage(MSG_KEY_SKIP_IF_LAST_AND_SHARED_WITH_CASE),
            "52:7: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

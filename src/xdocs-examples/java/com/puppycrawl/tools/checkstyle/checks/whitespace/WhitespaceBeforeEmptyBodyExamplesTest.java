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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceBeforeEmptyBodyCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class WhitespaceBeforeEmptyBodyExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespacebeforeemptybody";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_KEY, "{"),
            "18:17: " + getCheckMessage(MSG_KEY, "{"),
            "23:32: " + getCheckMessage(MSG_KEY, "{"),
            "27:17: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("Example1.java"), expected
        );
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "19:16: " + getCheckMessage(MSG_KEY, "{"),
            "21:17: " + getCheckMessage(MSG_KEY, "{"),
            "30:17: " + getCheckMessage(MSG_KEY, "{"),
        };

        verifyWithInlineConfigParser(
                getPath("Example2.java"), expected
        );
    }
}

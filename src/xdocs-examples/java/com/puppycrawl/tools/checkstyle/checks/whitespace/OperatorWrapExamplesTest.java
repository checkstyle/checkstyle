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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_PREVIOUS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class OperatorWrapExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/operatorwrap";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "16:24: " + getCheckMessage(MSG_LINE_NEW, "+"),
            "19:12: " + getCheckMessage(MSG_LINE_NEW, "=="),
            "27:16: " + getCheckMessage(MSG_LINE_NEW, "/"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
            "27:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "+="),
            "31:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "*="),
            "33:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "-="),
            "37:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "/="),
            "39:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "%="),
            "41:13: " + getCheckMessage(MSG_LINE_PREVIOUS, ">>="),
            "43:9: " + getCheckMessage(MSG_LINE_PREVIOUS, ">>>="),
            "45:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&="),
            "47:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "<<="),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_NEW;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OperatorWrapCheck.MSG_LINE_PREVIOUS;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class OperatorWrapExamplesTest extends AbstractModuleTestSupport {
    @Override
    protected String getResourceLocation() {
        return "xdocs-examples";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/operatorwrap";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:24: " + getCheckMessage(MSG_LINE_NEW, "+"),
            "15:12: " + getCheckMessage(MSG_LINE_NEW, "=="),
            "23:16: " + getCheckMessage(MSG_LINE_NEW, "/"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "16:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "="),
            "20:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "+="),
            "24:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "*="),
            "26:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "-="),
            "30:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "/="),
            "32:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "%="),
            "34:13: " + getCheckMessage(MSG_LINE_PREVIOUS, ">>="),
            "36:9: " + getCheckMessage(MSG_LINE_PREVIOUS, ">>>="),
            "38:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "&="),
            "40:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "<<="),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

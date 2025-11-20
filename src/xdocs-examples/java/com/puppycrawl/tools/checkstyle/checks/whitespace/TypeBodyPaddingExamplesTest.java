///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_END_REQUIRED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_START_REQUIRED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class TypeBodyPaddingExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/typebodypadding";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:23: " + getCheckMessage(MSG_START_REQUIRED),
            "42:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "43:1: " + getCheckMessage(MSG_END_REQUIRED),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "23:23: " + getCheckMessage(MSG_START_REQUIRED),
            "26:22: " + getCheckMessage(MSG_START_REQUIRED),
            "32:34: " + getCheckMessage(MSG_START_REQUIRED),
            "35:22: " + getCheckMessage(MSG_START_REQUIRED),
            "38:30: " + getCheckMessage(MSG_START_REQUIRED),
            "41:20: " + getCheckMessage(MSG_START_REQUIRED),
            "42:31: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "23:23: " + getCheckMessage(MSG_START_REQUIRED),
            "35:22: " + getCheckMessage(MSG_START_REQUIRED),
            "41:20: " + getCheckMessage(MSG_START_REQUIRED),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}

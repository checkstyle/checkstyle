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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_AFTER_LCURLY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck.MSG_BEFORE_RCURLY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class TypeBodyPaddingExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/typebodypadding";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "15:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "54:14: " + getCheckMessage(MSG_AFTER_LCURLY),
            "56:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "17:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "26:19: " + getCheckMessage(MSG_AFTER_LCURLY),
            "27:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "56:14: " + getCheckMessage(MSG_AFTER_LCURLY),
            "58:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "58:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "56:14: " + getCheckMessage(MSG_AFTER_LCURLY),
        };
        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "17:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "37:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "39:3: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "56:14: " + getCheckMessage(MSG_AFTER_LCURLY),
            "58:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_AFTER_LCURLY),
            "17:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "48:18: " + getCheckMessage(MSG_AFTER_LCURLY),
            "50:5: " + getCheckMessage(MSG_BEFORE_RCURLY),
            "56:14: " + getCheckMessage(MSG_AFTER_LCURLY),
            "58:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expected = {
            "57:14: " + getCheckMessage(MSG_AFTER_LCURLY),
            "59:1: " + getCheckMessage(MSG_BEFORE_RCURLY),
        };
        verifyWithInlineConfigParser(getPath("Example7.java"), expected);
    }

}

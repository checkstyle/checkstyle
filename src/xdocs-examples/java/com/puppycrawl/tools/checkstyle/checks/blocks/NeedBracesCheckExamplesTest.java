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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck.MSG_KEY_NEED_BRACES;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NeedBracesCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/needbraces";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "24:7: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
            "29:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "32:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "34:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "for"),
            "36:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
            "39:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "if"),
            "26:7: " + getCheckMessage(MSG_KEY_NEED_BRACES, "else"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "33:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "do"),
            "40:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),

        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "39:5: " + getCheckMessage(MSG_KEY_NEED_BRACES, "while"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expected = {
            "25:38: " + getCheckMessage(MSG_KEY_NEED_BRACES, "->"),
        };

        verifyWithInlineConfigParser(getPath("Example6.java"), expected);
    }
}

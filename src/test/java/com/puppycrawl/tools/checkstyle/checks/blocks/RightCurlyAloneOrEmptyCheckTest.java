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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyAloneOrEmptyCheck.MSG_KEY_ALONE_OR_EMPTY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class RightCurlyAloneOrEmptyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testCaseBlocksInSwitchStatementAlone() throws Exception {
        final String[] expected = {
            "32:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "43:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "72:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "72:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "78:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "84:39: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 39),
            "91:42: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 42),
            "92:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
            "101:35: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 35),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchCase.java"), expected);
    }

    @Test
    public void testCaseBlocksInSwitchStatementAloneTwo() throws Exception {
        final String[] expected = {
            "11:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "33:29: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 29),
            "37:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "40:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
            "47:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "51:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchCaseTwo.java"), expected);
    }

    @Test
    public void testCaseBlocksInSwitchStatementAloneThree() throws Exception {
        final String[] expected = {
            "16:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "25:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "37:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "40:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "49:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "70:23: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 23),
            "90:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchCaseThree.java"), expected);
    }

    @Test
    public void testMisc() throws Exception {
        final String[] expected = {
            "11:61: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 61),
            "15:29: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 29),
            "26:21: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 21),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyMisc.java"), expected);
    }

    @Test
    public void testAbstract() throws Exception {
        final String[] expected = {
            "19:24: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 24),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyAbstract.java"), expected);
    }
}

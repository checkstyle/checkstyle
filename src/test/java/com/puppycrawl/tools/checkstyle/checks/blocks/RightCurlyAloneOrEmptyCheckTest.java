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
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RightCurlyAloneOrEmptyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testCaseBlocksInSwitchStatementAlone() throws Exception {
        final String[] expected = {
            "77:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "91:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
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

    @Test
    public void testSwitchBlocksNewStyle() throws Exception {
        final String[] expected = {
            "17:32: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 32),
            "24:34: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 34),
            "26:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchNewStyle.java"), expected);
    }

    @Test
    public void testMultiBlockStatement() throws Exception {
        final String[] expected = {
            "15:25: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 25),
            "16:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "22:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "23:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "24:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
            "30:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "31:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "38:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "40:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "44:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "46:30: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 30),
            "48:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyMultiBlock.java"), expected);
    }

    @Test
    public void testTypeDeclarations() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "21:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
            "26:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "29:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "40:49: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 49),
            "56:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "60:57: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 57),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyTypeDeclarations.java"), expected
        );
    }

    @Test
    public void testLoopsRightCurly() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "37:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "40:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "45:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "50:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "62:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyLoops.java"), expected);
    }

    @Test
    public void testClassMembers() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "17:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "22:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "29:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "33:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "38:16: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 16),
            "47:23: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 23),
            "53:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "59:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "64:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyClassMembers.java"), expected);
    }
}

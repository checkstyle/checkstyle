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
            "20:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "34:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "46:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "76:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "82:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "88:39: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 39),
            "97:42: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 42),
            "98:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
            "108:35: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 35),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchCase.java"), expected);
    }

    @Test
    public void testCaseBlocksInSwitchStatementAloneTwo() throws Exception {
        final String[] expected = {
            "12:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "34:29: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 29),
            "38:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "41:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
            "48:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "52:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyTryCatchFinally.java"), expected);
    }

    @Test
    public void testCaseBlocksInSwitchStatementAloneThree() throws Exception {
        final String[] expected = {
            "17:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "28:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "41:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "44:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "55:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "75:34: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 34),
            "77:23: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 23),
            "79:34: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 34),
            "94:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "98:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
            "113:13: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 13),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchCaseThree.java"), expected);
    }

    @Test
    public void testMisc() throws Exception {
        final String[] expected = {
            "12:61: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 61),
            "16:29: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 29),
            "27:21: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 21),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyMisc.java"), expected);
    }

    @Test
    public void testAbstract() throws Exception {
        final String[] expected = {
            "20:24: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 24),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyAbstract.java"), expected);
    }

    @Test
    public void testSwitchBlocksNewStyle() throws Exception {
        final String[] expected = {
            "18:32: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 32),
            "25:34: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 34),
            "27:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptySwitchNewStyle.java"), expected);
    }

    @Test
    public void testMultiBlockStatement() throws Exception {
        final String[] expected = {
            "16:25: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 25),
            "17:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "23:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "24:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "25:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
            "31:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "32:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "39:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "41:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "45:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "47:30: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 30),
            "49:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyMultiBlock.java"), expected);
    }

    @Test
    public void testTypeDeclarations() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "22:37: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 37),
            "27:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "30:33: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 33),
            "41:49: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 49),
            "57:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "61:57: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 57),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyTypeDeclarations.java"), expected
        );
    }

    @Test
    public void testLoopsRightCurly() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "45:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyLoops.java"), expected);
    }

    @Test
    public void testClassMembers() throws Exception {
        final String[] expected = {
            "14:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "18:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "23:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "30:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "34:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "39:16: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 16),
            "48:23: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 23),
            "54:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "60:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
            "65:5: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 5),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyClassMembers.java"), expected);
    }

    @Test
    public void testAllowMultiBlockStatements() throws Exception {
        final String[] expected = {
            "17:25: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 25),
            "18:15: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 15),
            "27:20: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 20),
            "39:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "51:9: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 9),
            "63:14: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 14),
            "65:30: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 30),
            "67:18: " + getCheckMessage(MSG_KEY_ALONE_OR_EMPTY, "}", 18),
        };

        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyAllowMultiBlock.java"),
                expected);
    }
}

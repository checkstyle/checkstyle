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

import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_CONCISE_BLOCK;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_BREAK_AFTER;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_SAME;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class GoogleRightCurlyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/googlerightcurly";
    }

    @Test
    public void testMultiBlockStatements() throws Exception {
        final String[] expected = {
            "14:25: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 25),
            "15:15: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 15),
            "25:20: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 20),
            "37:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "49:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "61:14: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 14),
            "62:30: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 30),
            "63:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "69:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "78:17: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 17),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMultiBlock.java"),
                expected);
    }

    @Test
    public void testMultiBlockStatements2() throws Exception {
        final String[] expected = {
            "17:29: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 29),
            "25:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "28:28: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 28),
            "33:18: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 18),
            "33:38: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 38),
            "33:46: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 46),
            "46:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "49:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMultiBlock2.java"),
                expected);
    }

    @Test
    public void testTryCatchFinally() throws Exception {
        final String[] expected = {
            "36:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "41:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "55:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "59:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "70:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "70:10: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 10),
            "87:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "91:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "102:53: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 53),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyTryCatchFinally.java"),
                expected);
    }

    @Test
    public void testLoops() throws Exception {
        final String[] expected = {
            "25:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "31:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "40:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "47:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyLoops.java"),
                expected);
    }

    @Test
    public void testSwitchOldStyle() throws Exception {
        final String[] expected = {
            "16:15: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 15),
            "26:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "29:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "33:13: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "44:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "57:13: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "59:24: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "60:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "62:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "72:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 23),
            "78:13: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySwitchOldStyle.java"),
                expected);
    }

    @Test
    public void testSwitchNewStyle() throws Exception {
        final String[] expected = {
            "17:32: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 32),
            "24:34: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 34),
            "27:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "38:31: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 31),
            "42:14: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 14),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySwitchNewStyle.java"),
                expected);
    }

    @Test
    public void testTypesRightCurly() throws Exception {
        final String[] expected = {
            "12:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "20:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "20:6: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 6),
            "25:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 35),
            "39:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "45:33: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 33),
            "60:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "76:5: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "80:5: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "85:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "90:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "95:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "101:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "105:22: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyTypes.java"),
                expected);
    }

    @Test
    public void testMethodsAndConstructors() throws Exception {
        final String[] expected = {
            "13:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "22:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 36),
            "30:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "30:10: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 10),
            "49:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "52:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 35),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMethodsAndConstructors.java"),
                expected
        );
    }

    @Test
    public void testStaticAndInstanceInitializers() throws Exception {
        final String[] expected = {
            "15:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "25:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "29:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "34:5: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
            "35:5: " + getCheckMessage(MSG_KEY_CONCISE_BLOCK),
        };

        verifyWithInlineConfigParser(getPath("InputGoogleRightCurlyInitializers.java"),
                expected);
    }

    @Test
    public void testSynchronized() throws Exception {
        final String[] expected = {
            "14:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "30:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),
            "37:34: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 34),
            "46:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 23),
            "59:47: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 47),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySynchronized.java"),
                expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final String[] expected = {
            "42:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyAnonymousClass.java"),
                expected);
    }

    @Test
    public void testConciseBlockNotAlone() throws Exception {
        final String[] expected = {
            "10:13: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "}", 13),
            "12:20: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "}", 20),
            "17:22: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "}", 22),
            "27:25: " + getCheckMessage(MSG_KEY_LINE_BREAK_AFTER, "}", 25),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyConciseBlockNotAloneOnLine.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 5),
        };
        final String filename = "compact/InputGoogleRightCurlyCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}

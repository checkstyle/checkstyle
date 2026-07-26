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

import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_ALONE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.GoogleRightCurlyCheck.MSG_KEY_LINE_SAME;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GoogleRightCurlyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/googlerightcurly";
    }

    @Test
    public void testMultiBlockStatements() throws Exception {
        final String[] expected = {
            "18:25: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 25),
            "19:15: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 15),
            "29:20: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 20),
            "41:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "53:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "65:14: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 14),
            "66:30: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 30),
            "67:18: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 18),
            "73:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMultiBlock.java"),
                expected);
    }

    @Test
    public void testMultiBlockStatements2() throws Exception {
        final String[] expected = {
            "21:29: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 29),
            "29:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "32:28: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 28),
            "37:18: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 18),
            "37:38: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 38),
            "37:46: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 46),
            "50:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "53:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMultiBlock2.java"),
                expected);
    }

    @Test
    public void testTryCatchFinally() throws Exception {
        final String[] expected = {
            "37:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "42:17: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 17),
            "56:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "60:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "71:10: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 10),
            "84:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "88:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
            "99:53: " + getCheckMessage(MSG_KEY_LINE_BREAK_BEFORE, "}", 53),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyTryCatchFinally.java"),
                expected);
    }

    @Test
    public void testLoops() throws Exception {
        final String[] expected = {
            "26:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "32:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "41:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "48:9: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 9),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyLoops.java"),
                expected);
    }

    @Test
    public void testSwitchOldStyle() throws Exception {
        final String[] expected = {
            "17:15: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 15),
            "27:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "30:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "34:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "45:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
            "60:30: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 30),
            "62:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
            "72:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 23),
            "78:13: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 13),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySwitchOldStyle.java"),
                expected);
    }

    @Test
    public void testSwitchNewStyle() throws Exception {
        final String[] expected = {
            "18:32: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 32),
            "25:34: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 34),
            "28:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "39:31: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 31),
            "43:14: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 14),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySwitchNewStyle.java"),
                expected);
    }

    @Test
    public void testTypesRightCurly() throws Exception {
        final String[] expected = {
            "14:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "22:6: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 6),
            "25:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 35),
            "39:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "45:33: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 33),
            "60:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "76:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
            "80:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyTypes.java"),
                expected);
    }

    @Test
    public void testTypesRightCurly2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyTypes2.java"), expected);
    }

    @Test
    public void testMethodsAndConstructors() throws Exception {
        final String[] expected = {
            "14:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "23:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 36),
            "31:10: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 10),
            "48:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "51:35: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 35),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyMethodsAndConstructors.java"),
                expected
        );
    }

    @Test
    public void testStaticAndInstanceInitializers() throws Exception {
        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "29:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "33:19: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 19),
            "37:5: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 5),
        };

        verifyWithInlineConfigParser(getPath("InputGoogleRightCurlyInitializers.java"),
                expected);
    }

    @Test
    public void testSynchronized() throws Exception {
        final String[] expected = {
            "15:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "31:27: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 27),
            "38:34: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 34),
            "47:23: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 23),
            "60:47: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 47),
        };

        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlySynchronized.java"),
                expected);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final String[] expected = {
            "44:9: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 9),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleRightCurlyAnonymousClass.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY_LINE_SAME, "}", 5),
        };
        final String filename = "compact/InputGoogleRightCurlyCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}

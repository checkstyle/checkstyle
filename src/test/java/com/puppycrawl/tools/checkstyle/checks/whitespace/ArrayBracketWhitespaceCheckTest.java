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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketWhitespaceCheck.MSG_WS_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketWhitespaceCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.ArrayBracketWhitespaceCheck.MSG_WS_PRECEDED;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ArrayBracketWhitespaceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/arraybracketwhitespace";
    }

    @Test
    public void testGetRequiredTokens() {
        final ArrayBracketWhitespaceCheck checkObj = new ArrayBracketWhitespaceCheck();
        final int[] expected = {
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ArrayBracketWhitespaceCheck checkObj = new ArrayBracketWhitespaceCheck();
        final int[] expected = {
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "13:9: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "13:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "13:11: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "14:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "16:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "16:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "19:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "20:18: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "21:21: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "24:12: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "26:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "27:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceDefault.java"), expected);
    }

    @Test
    public void testValid() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceValid.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testArrayCreation() throws Exception {
        final String[] expected = {
            "14:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:23: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "16:26: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "19:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "20:27: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "20:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "21:32: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "23:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "24:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "28:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "31:33: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "32:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceArrayCreation.java"), expected);
    }

    @Test
    public void testExceptions() throws Exception {
        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "24:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "24:37: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "26:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "28:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "30:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "32:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "33:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "34:14: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "39:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "43:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "46:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "48:29: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "48:31: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "50:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "51:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "77:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "79:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "80:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "81:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "83:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "83:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "85:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "90:20: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "97:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "98:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "99:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "100:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "101:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "102:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "103:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "107:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "107:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceExceptions.java"), expected);
    }

    @Test
    public void testMethodDeclarations() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "18:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "18:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "23:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "30:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "31:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "31:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "38:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceMethodDecl.java"), expected);
    }

    @Test
    public void testIssue() throws Exception {
        final String[] expected = {
            "16:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "16:59: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "24:45: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceIssue.java"), expected);
    }
}

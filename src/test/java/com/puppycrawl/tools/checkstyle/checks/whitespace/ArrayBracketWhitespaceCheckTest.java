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
    public void testWhitespaceAroundBracketsInDeclarationsAndAccess() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "13:9: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "13:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "13:11: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "18:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "20:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "20:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "26:19: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:18: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "28:21: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "31:12: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "33:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "34:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceDefault.java"), expected);
    }

    @Test
    public void testNoViolationsForCorrectBracketWhitespace() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceValid.java"), expected);
    }

    @Test
    public void testWhitespaceAroundBracketsInArrayCreation() throws Exception {
        final String[] expected = {
            "14:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:23: " + getCheckMessage(MSG_WS_FOLLOWED, "["),
            "16:26: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "19:26: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "20:27: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "20:29: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "24:32: " + getCheckMessage(MSG_WS_PRECEDED, "]"),
            "26:24: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "27:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "27:25: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "34:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "37:33: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "38:33: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceArrayCreation.java"), expected);
    }

    @Test
    public void testRightBracketFollowedByExceptionCharacters() throws Exception {
        final String[] expected = {
            "16:26: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "24:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
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
            "53:37: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "54:35: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "80:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "82:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "83:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "84:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "86:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "86:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "91:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "96:20: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "101:22: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "102:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "103:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "104:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "105:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "106:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "107:27: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "110:50: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "110:71: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceExceptions.java"), expected);
    }

    @Test
    public void testMethodRef() throws Exception {
        final String[] expected = {
            "16:34: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "20:49: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceMethodRef.java"), expected);
    }

    @Test
    public void testWhitespaceAroundBracketsInMethodDeclarations() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "15:23: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "18:9: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "18:11: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "26:12: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "33:27: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "34:28: " + getCheckMessage(MSG_WS_FOLLOWED, "]"),
            "34:30: " + getCheckMessage(MSG_WS_PRECEDED, "["),
            "44:24: " + getCheckMessage(MSG_WS_PRECEDED, "["),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceMethodDecl.java"), expected);
    }

    @Test
    public void testRightBracketNotFollowedInComplexExpressions() throws Exception {
        final String[] expected = {
            "16:38: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
            "16:59: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "]"),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceIssue.java"), expected);
    }

    @Test
    public void testAnnotatedArrayDeclarations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputArrayBracketWhitespaceAnnotatedArrays.java"), expected);
    }
}

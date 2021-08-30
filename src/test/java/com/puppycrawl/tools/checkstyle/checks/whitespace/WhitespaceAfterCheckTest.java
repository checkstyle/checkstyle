////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_NOT_FOLLOWED;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAfterCheck.MSG_WS_TYPECAST;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class WhitespaceAfterCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter";
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAfterCheck checkObj = new WhitespaceAfterCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "WhitespaceAfterCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "45:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "74:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDefaultConfig.java"),
                expected);
    }

    @Test
    public void testCast() throws Exception {
        final String[] expected = {
            "91:20: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterTypeCast.java"),
                expected);
    }

    @Test
    public void testMultilineCast() throws Exception {
        final String[] expected = {
            "14:23: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterMultilineCast.java"),
                expected);
    }

    @Test
    public void testSemi() throws Exception {
        final String[] expected = {
            "57:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "57:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "106:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterBraces.java"),
                expected);
    }

    @Test
    public void testLiteralWhile() throws Exception {
        final String[] expected = {
            "46:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralWhile.java"),
                expected);
    }

    @Test
    public void testLiteralIf() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralIf.java"),
                expected);
    }

    @Test
    public void testLiteralElse() throws Exception {
        final String[] expected = {
            "34:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralElse.java"),
                expected);
    }

    @Test
    public void testLiteralFor() throws Exception {
        final String[] expected = {
            "58:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralFor.java"),
                expected);
    }

    @Test
    public void testLiteralDo() throws Exception {
        final String[] expected = {
            "70:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "do"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterLiteralDo.java"),
                expected);
    }

    @Test
    public void testDoWhile() throws Exception {
        final String[] expected = {
            "25:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterDoWhile.java"),
                expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception {
        final String[] expected = {
            "18:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "21:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterFor.java"),
                expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "20:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "20:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterGenerics.java"),
                expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterAround.java"),
               expected);
    }

    @Test
    public void testCountUnicodeCorrectly() throws Exception {
        final String[] expected = {
            "14:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verifyWithInlineConfigParser(
                getPath("InputWhitespaceAfterCountUnicodeCorrectly.java"), expected);
    }
}

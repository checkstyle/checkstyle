////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAfterCheck.class);
        final String[] expected = {
            "42:39: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "71:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterDefaultConfig.java"),
                expected);
    }

    @Test
    public void testCast() throws Exception {
        final DefaultConfiguration configurationTestCast =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestCast.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "88:20: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verify(configurationTestCast, getPath("InputWhitespaceAfterTypeCast.java"),
                expected);
    }

    @Test
    public void testMultilineCast() throws Exception {
        final DefaultConfiguration configurationTestCast =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestCast.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "7:23: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verify(configurationTestCast, getPath("InputWhitespaceAfterMultilineCast.java"),
                expected);
    }

    @Test
    public void testSemi() throws Exception {
        final DefaultConfiguration configurationTestSemi =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestSemi.addAttribute("tokens", "SEMI");
        final String[] expected = {
            "54:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "54:28: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "103:18: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(configurationTestSemi, getPath("InputWhitespaceAfterBraces.java"),
                expected);
    }

    @Test
    public void testLiteralWhile() throws Exception {
        final DefaultConfiguration configurationTestLiteralWhile =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralWhile.addAttribute("tokens", "LITERAL_WHILE");
        final String[] expected = {
            "39:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verify(configurationTestLiteralWhile, getPath("InputWhitespaceAfterLiteralWhile.java"),
                expected);
    }

    @Test
    public void testLiteralIf() throws Exception {
        final DefaultConfiguration configurationTestLiteralIf =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralIf.addAttribute("tokens", "LITERAL_IF");
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
        };
        verify(configurationTestLiteralIf, getPath("InputWhitespaceAfterLiteralIf.java"),
                expected);
    }

    @Test
    public void testLiteralElse() throws Exception {
        final DefaultConfiguration configurationTestLiteralElse =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralElse.addAttribute("tokens", "LITERAL_ELSE");
        final String[] expected = {
            "27:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
        };
        verify(configurationTestLiteralElse, getPath("InputWhitespaceAfterLiteralElse.java"),
                expected);
    }

    @Test
    public void testLiteralFor() throws Exception {
        final DefaultConfiguration configurationTestLiteralFor =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralFor.addAttribute("tokens", "LITERAL_FOR");
        final String[] expected = {
            "51:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verify(configurationTestLiteralFor, getPath("InputWhitespaceAfterLiteralFor.java"),
                expected);
    }

    @Test
    public void testLiteralDo() throws Exception {
        final DefaultConfiguration configurationTestLiteralDo =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralDo.addAttribute("tokens", "LITERAL_DO");
        final String[] expected = {
            "63:9: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "do"),
        };
        verify(configurationTestLiteralDo, getPath("InputWhitespaceAfterLiteralDo.java"),
                expected);
    }

    @Test
    public void testDoWhile() throws Exception {
        final DefaultConfiguration configurationTestDoWhile =
                createModuleConfig(WhitespaceAfterCheck.class);
        configurationTestDoWhile.addAttribute("tokens", "DO_WHILE");
        final String[] expected = {
            "18:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verify(configurationTestDoWhile, getPath("InputWhitespaceAfterDoWhile.java"),
                expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAfterCheck.class);
        final String[] expected = {
            "14:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "17:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterFor.java"),
                expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAfterCheck.class);
        final String[] expected = {
            "12:20: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "12:22: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "12:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterGenerics.java"),
                expected);
    }

    @Test
    public void test1322879() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(WhitespaceAfterCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAfterAround.java"),
               expected);
    }

}

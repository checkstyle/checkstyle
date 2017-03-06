////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class WhitespaceAfterCheckTest
    extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(WhitespaceAfterCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "whitespace" + File.separator + "whitespaceafter"
                + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final WhitespaceAfterCheck checkObj = new WhitespaceAfterCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "42:40: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "71:30: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterDefaultConfig.java"),
                expected);
    }

    @Test
    public void testCast() throws Exception {
        final DefaultConfiguration configurationTestCast =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestCast.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "88:21: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verify(configurationTestCast, getPath("InputWhitespaceAfterTypeCast.java"),
                expected);
    }

    @Test
    public void testMultilineCast() throws Exception {
        final DefaultConfiguration configurationTestCast =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestCast.addAttribute("tokens", "TYPECAST");
        final String[] expected = {
            "7:24: " + getCheckMessage(MSG_WS_TYPECAST),
        };
        verify(configurationTestCast, getPath("InputWhitespaceAfterMultilineCast.java"),
                expected);
    }

    @Test
    public void testSemi() throws Exception {
        final DefaultConfiguration configurationTestSemi =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestSemi.addAttribute("tokens", "SEMI");
        final String[] expected = {
            "54:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "54:29: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "103:19: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(configurationTestSemi, getPath("InputWhitespaceAfterBraces.java"),
                expected);
    }

    @Test
    public void testLiteralWhile() throws Exception {
        final DefaultConfiguration configurationTestLiteralWhile =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralWhile.addAttribute("tokens", "LITERAL_WHILE");
        final String[] expected = {
            "39:14: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verify(configurationTestLiteralWhile, getPath("InputWhitespaceAfterLiteralWhile.java"),
                expected);
    }

    @Test
    public void testLiteralIf() throws Exception {
        final DefaultConfiguration configurationTestLiteralIf =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralIf.addAttribute("tokens", "LITERAL_IF");
        final String[] expected = {
            "18:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "if"),
        };
        verify(configurationTestLiteralIf, getPath("InputWhitespaceAfterLiteralIf.java"),
                expected);
    }

    @Test
    public void testLiteralElse() throws Exception {
        final DefaultConfiguration configurationTestLiteralElse =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralElse.addAttribute("tokens", "LITERAL_ELSE");
        final String[] expected = {
            "27:15: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "else"),
        };
        verify(configurationTestLiteralElse, getPath("InputWhitespaceAfterLiteralElse.java"),
                expected);
    }

    @Test
    public void testLiteralFor() throws Exception {
        final DefaultConfiguration configurationTestLiteralFor =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralFor.addAttribute("tokens", "LITERAL_FOR");
        final String[] expected = {
            "51:12: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "for"),
        };
        verify(configurationTestLiteralFor, getPath("InputWhitespaceAfterLiteralFor.java"),
                expected);
    }

    @Test
    public void testLiteralDo() throws Exception {
        final DefaultConfiguration configurationTestLiteralDo =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestLiteralDo.addAttribute("tokens", "LITERAL_DO");
        final String[] expected = {
            "63:11: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "do"),
        };
        verify(configurationTestLiteralDo, getPath("InputWhitespaceAfterLiteralDo.java"),
                expected);
    }

    @Test
    public void testDoWhile() throws Exception {
        final DefaultConfiguration configurationTestDoWhile =
                createCheckConfig(WhitespaceAfterCheck.class);
        configurationTestDoWhile.addAttribute("tokens", "DO_WHILE");
        final String[] expected = {
            "18:16: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, "while"),
        };
        verify(configurationTestDoWhile, getPath("InputWhitespaceAfterDoWhile.java"),
                expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception {
        final String[] expected = {
            "14:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
            "17:31: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ";"),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterFor.java"),
                expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception {
        final String[] expected = {
            "11:21: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "11:23: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
            "11:41: " + getCheckMessage(MSG_WS_NOT_FOLLOWED, ","),
        };
        verify(checkConfig, getPath("InputWhitespaceAfterGenerics.java"),
                expected);
    }

    @Test
    public void test1322879() throws Exception {
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputWhitespaceAfterAround.java"),
               expected);
    }
}

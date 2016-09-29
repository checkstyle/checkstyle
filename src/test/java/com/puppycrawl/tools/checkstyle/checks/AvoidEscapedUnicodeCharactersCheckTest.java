////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidEscapedUnicodeCharactersCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidEscapedUnicodeCharactersCheck checkObj =
            new AvoidEscapedUnicodeCharactersCheck();
        final int[] expected = {
            TokenTypes.STRING_LITERAL,
            TokenTypes.CHAR_LITERAL,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "20: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "27: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "72: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterSet() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowByTailComment", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "59: " + getCheckMessage(MSG_KEY),
            "60: " + getCheckMessage(MSG_KEY),
            "61: " + getCheckMessage(MSG_KEY),
            "62: " + getCheckMessage(MSG_KEY),
            "73: " + getCheckMessage(MSG_KEY),
            "74: " + getCheckMessage(MSG_KEY),
            "75: " + getCheckMessage(MSG_KEY),
            "76: " + getCheckMessage(MSG_KEY),
            "77: " + getCheckMessage(MSG_KEY),
            "79: " + getCheckMessage(MSG_KEY),
            "82: " + getCheckMessage(MSG_KEY),
            "92: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowIfAllCharactersEscaped", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapes() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowNonPrintableEscapes", "true");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY),
            "9: " + getCheckMessage(MSG_KEY),
            "11: " + getCheckMessage(MSG_KEY),
            "15: " + getCheckMessage(MSG_KEY),
            "16: " + getCheckMessage(MSG_KEY),
            "24: " + getCheckMessage(MSG_KEY),
            "25: " + getCheckMessage(MSG_KEY),
            "31: " + getCheckMessage(MSG_KEY),
            "32: " + getCheckMessage(MSG_KEY),
            "33: " + getCheckMessage(MSG_KEY),
            "34: " + getCheckMessage(MSG_KEY),
            "42: " + getCheckMessage(MSG_KEY),
            "86: " + getCheckMessage(MSG_KEY),
            "87: " + getCheckMessage(MSG_KEY),
            "88: " + getCheckMessage(MSG_KEY),
            "89: " + getCheckMessage(MSG_KEY),
            "93: " + getCheckMessage(MSG_KEY),
            "94: " + getCheckMessage(MSG_KEY),
            "98: " + getCheckMessage(MSG_KEY),
            "104: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        final int[] actual = check.getAcceptableTokens();
        final int[] expected = {TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL };
        assertArrayEquals(expected, actual);
    }
}

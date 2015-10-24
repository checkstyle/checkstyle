////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidEscapedUnicodeCharactersCheckTest extends BaseCheckTestSupport {
    private final String msg = getCheckMessage("forbid.escaped.unicode.char");

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
            "7: " + msg,
            "9: " + msg,
            "11: " + msg,
            "15: " + msg,
            "16: " + msg,
            "20: " + msg,
            "24: " + msg,
            "25: " + msg,
            "27: " + msg,
            "31: " + msg,
            "32: " + msg,
            "33: " + msg,
            "34: " + msg,
            "42: " + msg,
            "59: " + msg,
            "60: " + msg,
            "61: " + msg,
            "62: " + msg,
            "72: " + msg,
            "73: " + msg,
            "74: " + msg,
            "75: " + msg,
            "76: " + msg,
            "77: " + msg,
            "79: " + msg,
            "82: " + msg,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterSet() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");
        final String[] expected = {
            "7: " + msg,
            "9: " + msg,
            "11: " + msg,
            "15: " + msg,
            "16: " + msg,
            "24: " + msg,
            "25: " + msg,
            "31: " + msg,
            "32: " + msg,
            "33: " + msg,
            "34: " + msg,
            "42: " + msg,
            "59: " + msg,
            "60: " + msg,
            "61: " + msg,
            "62: " + msg,
            "73: " + msg,
            "74: " + msg,
            "75: " + msg,
            "76: " + msg,
            "77: " + msg,
            "79: " + msg,
            "82: " + msg,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowByTailComment", "true");
        final String[] expected = {
            "7: " + msg,
            "15: " + msg,
            "24: " + msg,
            "31: " + msg,
            "33: " + msg,
            "34: " + msg,
            "59: " + msg,
            "60: " + msg,
            "61: " + msg,
            "62: " + msg,
            "72: " + msg,
            "73: " + msg,
            "74: " + msg,
            "75: " + msg,
            "76: " + msg,
            "77: " + msg,
            "79: " + msg,
            "82: " + msg,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowIfAllCharactersEscaped", "true");
        final String[] expected = {
            "7: " + msg,
            "9: " + msg,
            "11: " + msg,
            "15: " + msg,
            "16: " + msg,
            "31: " + msg,
            "32: " + msg,
            "33: " + msg,
            "42: " + msg,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharacters.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapes() throws Exception {
        final DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowNonPrintableEscapes", "true");
        final String[] expected = {
            "7: " + msg,
            "9: " + msg,
            "11: " + msg,
            "15: " + msg,
            "16: " + msg,
            "24: " + msg,
            "25: " + msg,
            "31: " + msg,
            "32: " + msg,
            "33: " + msg,
            "34: " + msg,
            "42: " + msg,
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

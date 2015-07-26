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

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidEscapedUnicodeCharactersCheckTest extends BaseCheckTestSupport {

    private final String MSG = getCheckMessage("forbid.escaped.unicode.char");

    @Test
    public void testDefault() throws Exception {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        final String[] expected = {
            "7: " + MSG,
            "9: " + MSG,
            "11: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "20: " + MSG,
            "24: " + MSG,
            "25: " + MSG,
            "27: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
            "42: " + MSG,
            "59: " + MSG,
            "60: " + MSG,
            "61: " + MSG,
            "62: " + MSG,
            "72: " + MSG,
            "73: " + MSG,
            "74: " + MSG,
            "75: " + MSG,
            "76: " + MSG,
            "77: " + MSG,
            "79: " + MSG,
            "82: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterst() throws Exception {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");
        final String[] expected = {
            "7: " + MSG,
            "9: " + MSG,
            "11: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "24: " + MSG,
            "25: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
            "42: " + MSG,
            "59: " + MSG,
            "60: " + MSG,
            "61: " + MSG,
            "62: " + MSG,
            "73: " + MSG,
            "74: " + MSG,
            "75: " + MSG,
            "76: " + MSG,
            "77: " + MSG,
            "79: " + MSG,
            "82: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowByTailComment", "true");
        final String[] expected = {
            "7: " + MSG,
            "15: " + MSG,
            "24: " + MSG,
            "31: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
            "59: " + MSG,
            "60: " + MSG,
            "61: " + MSG,
            "62: " + MSG,
            "72: " + MSG,
            "73: " + MSG,
            "74: " + MSG,
            "75: " + MSG,
            "76: " + MSG,
            "77: " + MSG,
            "79: " + MSG,
            "82: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowIfAllCharactersEscaped", "true");
        final String[] expected = {
            "7: " + MSG,
            "9: " + MSG,
            "11: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "42: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapes() throws Exception {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowNonPrintableEscapes", "true");
        final String[] expected = {
            "7: " + MSG,
            "9: " + MSG,
            "11: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "24: " + MSG,
            "25: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
            "42: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        AvoidEscapedUnicodeCharactersCheck check = new AvoidEscapedUnicodeCharactersCheck();
        int[] actual = check.getAcceptableTokens();
        int[] expected = new int[] {TokenTypes.STRING_LITERAL, TokenTypes.CHAR_LITERAL };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }

}

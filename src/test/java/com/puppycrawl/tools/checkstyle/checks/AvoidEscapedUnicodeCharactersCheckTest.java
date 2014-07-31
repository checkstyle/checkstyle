////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

import org.junit.Test;

public class AvoidEscapedUnicodeCharactersCheckTest extends BaseCheckTestSupport
{

    private final String MSG = getCheckMessage("forbid.escaped.unicode.char");

    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        final String[] expected = {
            "5: " + MSG,
            "7: " + MSG,
            "9: " + MSG,
            "13: " + MSG,
            "14: " + MSG,
            "17: " + MSG,
            "21: " + MSG,
            "22: " + MSG,
            "23: " + MSG,
            "27: " + MSG,
            "28: " + MSG,
            "29: " + MSG,
            "30: " + MSG,
            "38: " + MSG,
            "54: " + MSG,
            "55: " + MSG,
            "56: " + MSG,
            "57: " + MSG,
            "68: " + MSG,
            "69: " + MSG,
            "70: " + MSG,
            "71: " + MSG,
            "72: " + MSG,
            "73: " + MSG,
            "75: " + MSG,
            "78: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowEscapesForControlCharacterst() throws Exception
    {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowEscapesForControlCharacters", "true");
        final String[] expected = {
            "5: " + MSG,
            "7: " + MSG,
            "9: " + MSG,
            "13: " + MSG,
            "14: " + MSG,
            "21: " + MSG,
            "22: " + MSG,
            "27: " + MSG,
            "28: " + MSG,
            "29: " + MSG,
            "30: " + MSG,
            "38: " + MSG,
            "54: " + MSG,
            "55: " + MSG,
            "56: " + MSG,
            "57: " + MSG,
            "69: " + MSG,
            "70: " + MSG,
            "71: " + MSG,
            "72: " + MSG,
            "73: " + MSG,
            "75: " + MSG,
            "78: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowByTailComment() throws Exception
    {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowByTailComment", "true");
        final String[] expected = {
            "5: " + MSG,
            "13: " + MSG,
            "21: " + MSG,
            "27: " + MSG,
            "29: " + MSG,
            "30: " + MSG,
            "54: " + MSG,
            "55: " + MSG,
            "56: " + MSG,
            "57: " + MSG,
            "68: " + MSG,
            "69: " + MSG,
            "70: " + MSG,
            "71: " + MSG,
            "72: " + MSG,
            "73: " + MSG,
            "75: " + MSG,
            "78: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void testAllowAllCharactersEscaped() throws Exception
    {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowIfAllCharactersEscaped", "true");
        final String[] expected = {
            "5: " + MSG,
            "7: " + MSG,
            "9: " + MSG,
            "13: " + MSG,
            "14: " + MSG,
            "27: " + MSG,
            "28: " + MSG,
            "29: " + MSG,
            "38: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }

    @Test
    public void allowNonPrintableEscapes() throws Exception
    {
        DefaultConfiguration checkConfig =
                createCheckConfig(AvoidEscapedUnicodeCharactersCheck.class);
        checkConfig.addAttribute("allowNonPrintableEscapes", "true");
        final String[] expected = {
            "5: " + MSG,
            "7: " + MSG,
            "9: " + MSG,
            "13: " + MSG,
            "14: " + MSG,
            "21: " + MSG,
            "22: " + MSG,
            "27: " + MSG,
            "28: " + MSG,
            "29: " + MSG,
            "30: " + MSG,
            "38: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }
}

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
            "10: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "19: " + MSG,
            "24: " + MSG,
            "25: " + MSG,
            "26: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
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
            "10: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "24: " + MSG,
            "25: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
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
            "15: " + MSG,
            "19: " + MSG,
            "24: " + MSG,
            "26: " + MSG,
            "31: " + MSG,
            "33: " + MSG,
            "34: " + MSG,
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
            "10: " + MSG,
            "15: " + MSG,
            "16: " + MSG,
            "31: " + MSG,
            "32: " + MSG,
            "33: " + MSG,
        };
        verify(checkConfig, getPath("InputAvoidEscapedUnicodeCharactersCheck.java"), expected);
    }
}

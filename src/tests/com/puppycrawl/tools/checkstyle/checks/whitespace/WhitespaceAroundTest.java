////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class WhitespaceAroundTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(WhitespaceAroundCheck.class);
    }

    @Test
    public void testIt()
        throws Exception
    {
        final String[] expected = {
            "16:22: '=' is not preceded with whitespace.",
            "16:23: '=' is not followed by whitespace.",
            "18:24: '=' is not followed by whitespace.",
            "26:14: '=' is not preceded with whitespace.",
            "27:10: '=' is not preceded with whitespace.",
            "27:11: '=' is not followed by whitespace.",
            "28:10: '+=' is not preceded with whitespace.",
            "28:12: '+=' is not followed by whitespace.",
            "29:13: '-=' is not followed by whitespace.",
            "37:21: 'synchronized' is not followed by whitespace.",
            "39:12: 'try' is not followed by whitespace.",
            "39:12: '{' is not preceded with whitespace.",
            "41:14: 'catch' is not followed by whitespace.",
            "41:34: '{' is not preceded with whitespace.",
            "58:11: 'if' is not followed by whitespace.",
            "76:19: 'return' is not followed by whitespace.",
            "97:29: '?' is not preceded with whitespace.",
            "97:30: '?' is not followed by whitespace.",
            "97:34: ':' is not preceded with whitespace.",
            "97:35: ':' is not followed by whitespace.",
            "98:15: '==' is not preceded with whitespace.",
            "98:17: '==' is not followed by whitespace.",
            "104:20: '*' is not followed by whitespace.",
            "104:21: '*' is not preceded with whitespace.",
            "119:18: '%' is not preceded with whitespace.",
            "120:20: '%' is not followed by whitespace.",
            "121:18: '%' is not preceded with whitespace.",
            "121:19: '%' is not followed by whitespace.",
            "123:18: '/' is not preceded with whitespace.",
            "124:20: '/' is not followed by whitespace.",
            "125:18: '/' is not preceded with whitespace.",
            "125:19: '/' is not followed by whitespace.",
            "153:15: 'assert' is not followed by whitespace.",
            "156:20: ':' is not preceded with whitespace.",
            "156:21: ':' is not followed by whitespace.",
        };
        verify(checkConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testIt2()
        throws Exception
    {
        final String[] expected = {
            "153:27: '=' is not followed by whitespace.",
            "154:27: '=' is not followed by whitespace.",
            "155:27: '=' is not followed by whitespace.",
            "156:27: '=' is not followed by whitespace.",
            "157:27: '=' is not followed by whitespace.",
            "158:27: '=' is not followed by whitespace.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testIt3()
        throws Exception
    {
        final String[] expected = {
            "41:14: 'while' is not followed by whitespace.",
            "58:12: 'for' is not followed by whitespace.",
            // + ":58:23: ';' is not followed by whitespace.",
            //  + ":58:29: ';' is not followed by whitespace.",
            "115:27: '{' is not followed by whitespace.",
            "115:27: '}' is not preceded with whitespace.",
            "118:40: '{' is not followed by whitespace.",
            "118:40: '}' is not preceded with whitespace.",
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testIt4()
        throws Exception
    {
        checkConfig.addAttribute("allowEmptyMethods", "true");
        checkConfig.addAttribute("allowEmptyConstructors", "true");
        final String[] expected = {
            "41:14: 'while' is not followed by whitespace.",
            "58:12: 'for' is not followed by whitespace.",
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testGenericsTokensAreFlagged()
        throws Exception
    {
        final String[] expected = {
            "6:67: '&' is not preceded with whitespace.",
            "6:68: '&' is not followed by whitespace.",
        };
        verify(checkConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879() throws Exception
    {
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}

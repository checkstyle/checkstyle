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

public class MethodParamPadCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(MethodParamPadCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "11:32: '(' is preceded with whitespace.",
            "13:15: '(' is preceded with whitespace.",
            "17:9: '(' should be on the previous line.",
            "20:13: '(' should be on the previous line.",
            "27:24: '(' is preceded with whitespace.",
            "32:9: '(' should be on the previous line.",
            "36:39: '(' is preceded with whitespace.",
            "38:13: '(' should be on the previous line.",
            "42:16: '(' is preceded with whitespace.",
            "44:13: '(' should be on the previous line.",
            "50:21: '(' is preceded with whitespace.",
            "52:13: '(' should be on the previous line.",
            "56:18: '(' is preceded with whitespace.",
            "58:13: '(' should be on the previous line.",
            "61:36: '(' is preceded with whitespace.",
            "63:13: '(' should be on the previous line.",
        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    @Test
    public void testAllowLineBreaks() throws Exception
    {
        checkConfig.addAttribute("allowLineBreaks", "true");
        final String[] expected = {
            "11:32: '(' is preceded with whitespace.",
            "13:15: '(' is preceded with whitespace.",
            "27:24: '(' is preceded with whitespace.",
            "36:39: '(' is preceded with whitespace.",
            "42:16: '(' is preceded with whitespace.",
            "50:21: '(' is preceded with whitespace.",
            "56:18: '(' is preceded with whitespace.",
            "61:36: '(' is preceded with whitespace.",
        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    @Test
    public void testSpaceOption() throws Exception
    {
        checkConfig.addAttribute("option", "space");
        final String[] expected = {
            "6:31: '(' is not preceded with whitespace.",
            "8:14: '(' is not preceded with whitespace.",
            "17:9: '(' should be on the previous line.",
            "20:13: '(' should be on the previous line.",
            "23:23: '(' is not preceded with whitespace.",
            "32:9: '(' should be on the previous line.",
            "35:58: '(' is not preceded with whitespace.",
            "38:13: '(' should be on the previous line.",
            "41:15: '(' is not preceded with whitespace.",
            "44:13: '(' should be on the previous line.",
            "47:28: '(' is not preceded with whitespace.",
            "49:20: '(' is not preceded with whitespace.",
            "52:13: '(' should be on the previous line.",
            "54:56: '(' is not preceded with whitespace.",
            "55:17: '(' is not preceded with whitespace.",
            "58:13: '(' should be on the previous line.",
            "60:35: '(' is not preceded with whitespace.",
            "63:13: '(' should be on the previous line.",
            "66:25: '(' is not preceded with whitespace.",
            "69:66: '(' is not preceded with whitespace.",
            "70:57: '(' is not preceded with whitespace.",
        };
        verify(checkConfig, getPath("whitespace/InputMethodParamPad.java"), expected);
    }

    @Test
    public void test1322879() throws Exception
    {
        checkConfig.addAttribute("option", PadOption.SPACE.toString());
        final String[] expected = {
        };
        verify(checkConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}

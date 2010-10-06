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

public class WhitespaceAfterCheckTest
    extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(WhitespaceAfterCheck.class);
    }

    @Test
    public void testDefault() throws Exception
    {
        final String[] expected = {
            "42:40: ',' is not followed by whitespace.",
            "71:30: ',' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testCast() throws Exception
    {
        final String[] expected = {
            "88:21: 'cast' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputWhitespace.java"), expected);
    }

    @Test
    public void testSemi() throws Exception
    {
        final String[] expected = {
            "58:23: ';' is not followed by whitespace.",
            "58:29: ';' is not followed by whitespace.",
            "107:19: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputBraces.java"), expected);
    }

    @Test
    public void testEmptyForIterator() throws Exception
    {
        final String[] expected = {
            "14:31: ';' is not followed by whitespace.",
            "17:31: ';' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputForWhitespace.java"), expected);
    }

    @Test
    public void testTypeArgumentAndParameterCommas() throws Exception
    {
        final String[] expected = {
            "11:21: ',' is not followed by whitespace.",
            "11:23: ',' is not followed by whitespace.",
            "11:41: ',' is not followed by whitespace.",
        };
        verify(mCheckConfig, getPath("InputGenerics.java"), expected);
    }

    @Test
    public void test1322879() throws Exception
    {
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("whitespace/InputWhitespaceAround.java"),
               expected);
    }
}

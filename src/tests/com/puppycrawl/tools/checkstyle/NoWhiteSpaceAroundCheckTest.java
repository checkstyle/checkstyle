////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.checks.NoWhiteSpaceAroundCheck;

public class NoWhiteSpaceAroundCheckTest extends BaseCheckTestCase
{
    public NoWhiteSpaceAroundCheckTest(String aName)
    {
        super(aName);
    }

    public void testAllowAtLineBreak() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(NoWhiteSpaceAroundCheck.class.getName());
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:12: '.' is preceeded with whitespace.",
            "5:14: '.' is followed by whitespace.",
            "129:17: '.' is preceeded with whitespace.",
            "129:24: '.' is followed by whitespace.",
            "136:10: '.' is preceeded with whitespace.",
            "136:12: '.' is followed by whitespace."
        };
        verify(c, fname, expected);
    }

    public void testDontAllowAtLineBreak() throws Exception
    {
        final CheckConfiguration checkConfig = new CheckConfiguration();
        checkConfig.setClassname(NoWhiteSpaceAroundCheck.class.getName());
        checkConfig.addProperty("allowLineBreaks", "no");
        final Checker c = createChecker(checkConfig);
        final String fname = getPath("InputWhitespace.java");
        final String[] expected = {
            "5:12: '.' is preceeded with whitespace.",
            "5:14: '.' is followed by whitespace.",
            "6:4: '.' is preceeded with whitespace.",
            "6:12: '.' is followed by whitespace.",
            "129:17: '.' is preceeded with whitespace.",
            "129:24: '.' is followed by whitespace.",
            "132:11: '.' is followed by whitespace.",
            "135:12: '.' is preceeded with whitespace.",
            "136:10: '.' is preceeded with whitespace.",
            "136:12: '.' is followed by whitespace."
        };
        verify(c, fname, expected);
    }
}
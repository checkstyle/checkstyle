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
import org.junit.Before;
import org.junit.Test;

public class TrailingCommentCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp()
    {
        checkConfig = createCheckConfig(TrailingCommentCheck.class);
    }

    @Test
    public void testDefaults() throws Exception
    {
        final String[] expected = {
            "4: Don't use trailing comments.",
            "7: Don't use trailing comments.",
            "8: Don't use trailing comments.",
            "18: Don't use trailing comments.",
            "19: Don't use trailing comments.",
            "29: Don't use trailing comments.",
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testLegalComment() throws Exception
    {
        checkConfig.addAttribute("legalComment", "^NOI18N$");
        final String[] expected = {
            "4: Don't use trailing comments.",
            "7: Don't use trailing comments.",
            "8: Don't use trailing comments.",
            "18: Don't use trailing comments.",
            "19: Don't use trailing comments.",
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }
}

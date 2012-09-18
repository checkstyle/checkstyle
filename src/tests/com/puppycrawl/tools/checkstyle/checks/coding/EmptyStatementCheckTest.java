////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;


public class EmptyStatementCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testEmptyStatements()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EmptyStatementCheck.class);
        final String[] expected = {
            "12:7: Empty statement.",
            "17:7: Empty statement.",
            "22:19: Empty statement.",
            "26:10: Empty statement.",
            "29:16: Empty statement.",
            "33:10: Empty statement.",
            "43:10: Empty statement.",
            "49:13: Empty statement.",
            "51:13: Empty statement.",
            "54:19: Empty statement.",
            "58:10: Empty statement.",
            "61:9: Empty statement.",
            "66:10: Empty statement.",
            "72:10: Empty statement.",
            "76:10: Empty statement.",
            "80:10: Empty statement.",
        };

        verify(checkConfig, getPath("InputEmptyStatement.java"), expected);
    }
}

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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class NeedBracesCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(NeedBracesCheck.class);
        final String[] expected = {
            "29: 'do' construct must use '{}'s." ,
            "41: 'while' construct must use '{}'s." ,
            "42: 'while' construct must use '{}'s." ,
            "44: 'while' construct must use '{}'s." ,
            "45: 'if' construct must use '{}'s." ,
            "58: 'for' construct must use '{}'s." ,
            "59: 'for' construct must use '{}'s." ,
            "61: 'for' construct must use '{}'s." ,
            "63: 'if' construct must use '{}'s." ,
            "82: 'if' construct must use '{}'s." ,
            "83: 'if' construct must use '{}'s." ,
            "85: 'if' construct must use '{}'s." ,
            "87: 'else' construct must use '{}'s." ,
            "89: 'if' construct must use '{}'s." ,
            "97: 'else' construct must use '{}'s." ,
            "99: 'if' construct must use '{}'s." ,
            "100: 'if' construct must use '{}'s." ,
        };
        verify(checkConfig, getPath("InputBraces.java"), expected);
    }
}

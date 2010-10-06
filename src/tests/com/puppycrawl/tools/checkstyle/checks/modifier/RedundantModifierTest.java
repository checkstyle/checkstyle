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
package com.puppycrawl.tools.checkstyle.checks.modifier;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

public class RedundantModifierTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantModifierCheck.class);
        final String[] expected = {
            "57:9: Redundant 'public' modifier.",
            "63:9: Redundant 'abstract' modifier.",
            "66:9: Redundant 'public' modifier.",
            //"69:9: Redundant 'abstract' modifier.",
            "72:9: Redundant 'final' modifier.",
            "79:13: Redundant 'final' modifier.",
            "88:12: Redundant 'final' modifier.",
            "116:5: Redundant 'public' modifier.",
            "117:5: Redundant 'final' modifier.",
            "118:5: Redundant 'static' modifier.",
            "120:5: Redundant 'public' modifier.",
            "121:5: Redundant 'abstract' modifier.",
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}

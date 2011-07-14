////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

public class ModifierOrderCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ModifierOrderCheck.class);
        final String[] expected = {
            "14:10: 'final' modifier out of order with the JLS suggestions.",
            "18:12: 'private' modifier out of order with the JLS suggestions.",
            "24:14: 'private' modifier out of order with the JLS suggestions.",
            "34:13: '@MyAnnotation2' annotation modifier does not preceed non-annotation modifiers.",
            "39:13: '@MyAnnotation2' annotation modifier does not preceed non-annotation modifiers.",
            "49:35: '@MyAnnotation4' annotation modifier does not preceed non-annotation modifiers.",
        };
        verify(checkConfig, getPath("InputModifier.java"), expected);
    }
}

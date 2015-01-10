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
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalInstantiationCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(IllegalInstantiationCheck.class);
        checkConfig.addAttribute(
            "classes",
            "java.lang.Boolean,"
                + "com.puppycrawl.tools.checkstyle.InputModifier,"
                + "java.io.File,"
                + "java.awt.Color");
        final String[] expected = {
            "19:21: Instantiation of java.lang.Boolean should be avoided.",
            "24:21: Instantiation of java.lang.Boolean should be avoided.",
            "31:16: Instantiation of java.lang.Boolean should be avoided.",
            "38:21: Instantiation of "
                + "com.puppycrawl.tools.checkstyle.InputModifier "
                + "should be avoided.",
            "41:18: Instantiation of java.io.File should be avoided.",
            "44:21: Instantiation of java.awt.Color should be avoided.",
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testJava8() throws Exception
    {
        final DefaultConfiguration checkConfig =
                createCheckConfig(IllegalInstantiationCheck.class);
        final String[] expected = {};
        verify(checkConfig,
                new File("src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/"
                          + "coding/InputIllegalInstantiationCheckTest2.java").getCanonicalPath(),
                expected);
    }
}

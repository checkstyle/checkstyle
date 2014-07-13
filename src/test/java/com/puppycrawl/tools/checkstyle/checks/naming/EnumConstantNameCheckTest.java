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
package com.puppycrawl.tools.checkstyle.checks.naming;

import static org.junit.Assert.fail;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import org.junit.Test;

public class EnumConstantNameCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIllegalRegexp()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        checkConfig.addAttribute("format", "\\");
        try {
            createChecker(checkConfig);
            fail();
        }
        catch (CheckstyleException ex) {
            // expected exception
        }
    }

    @Test
    public void testDefault()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EnumConstantNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testCamelCase()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(EnumConstantNameCheck.class);
        final String[] expected = {
            "7:5: Name 'NotGood' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig,
               getPath("naming" + File.separator
                       + "InputEnumConstantNames.java"),
               expected);
    }
}

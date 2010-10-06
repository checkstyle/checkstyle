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
package com.puppycrawl.tools.checkstyle.checks.naming;

import static org.junit.Assert.fail;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import org.junit.Test;

public class ConstantNameCheckTest
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
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
            "25:29: Name 'badConstant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "142:30: Name 'BAD__NAME' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testAccessControlTuning()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        final String[] expected = {
            "142:30: Name 'BAD__NAME' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig, getPath("InputSimple.java"), expected);
    }

    @Test
    public void testInterfaceAndAnnotation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
            "24:16: Name 'data' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "64:16: Name 'data' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        verify(checkConfig, getPath("InputInner.java"), expected);
    }

    @Test
    public void testDefault1()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ConstantNameCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("naming" + File.separator + "InputConstantNames.java"), expected);
    }
}

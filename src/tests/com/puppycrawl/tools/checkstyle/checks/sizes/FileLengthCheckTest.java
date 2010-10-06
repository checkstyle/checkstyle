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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import static org.junit.Assert.fail;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import org.junit.Test;

public class FileLengthCheckTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
    public void testAlarm() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", "20");
        final String[] expected = {
            "1: File length is 225 lines (max allowed is 20).",
        };
        verify(createChecker(checkConfig),
                getPath("InputSimple.java"),
                getPath("InputSimple.java"), expected);
    }

    @Test
    public void testOK() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(FileLengthCheck.class);
        checkConfig.addAttribute("max", "2000");
        final String[] expected = {
        };
        verify(createChecker(checkConfig),
                getPath("InputSimple.java"),
                getPath("InputSimple.java"), expected);
    }

    @Test
    public void testArgs() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileLengthCheck.class);
        try {
            checkConfig.addAttribute("max", "abc");
            createChecker(checkConfig);
            fail("Should indicate illegal args");
        }
        catch (CheckstyleException ex) {
            // Expected Exception because of illegal argument for "max"
        }
    }


}

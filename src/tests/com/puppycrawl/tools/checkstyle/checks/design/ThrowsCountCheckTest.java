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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ThrowsCountCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);

        String[] expected = {
            "14:20: Throws count is 2 (max allowed is 1).",
            "18:20: Throws count is 2 (max allowed is 1).",
            "22:20: Throws count is 3 (max allowed is 1).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }

    @Test
    public void testMax() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(ThrowsCountCheck.class);
        checkConfig.addAttribute("max", "2");

        String[] expected = {
            "22:20: Throws count is 3 (max allowed is 2).",
        };

        verify(checkConfig, getPath("design" + File.separator + "InputThrowsCount.java"), expected);
    }
}

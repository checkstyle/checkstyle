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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

/**
 * Testcase for the JavaNCSS-Check.
 *
 * @author Lars Ködderitzsch
 */
public class JavaNCSSCheckTest extends BaseCheckTestSupport
{

    @Test
    public void test() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(JavaNCSSCheck.class);

        checkConfig.addAttribute("methodMaximum", "0");
        checkConfig.addAttribute("classMaximum", "1");
        checkConfig.addAttribute("fileMaximum", "2");

        String[] expected = {
            "2:1: NCSS for this file is 35 (max allowed is 2).",
            "9:1: NCSS for this class is 22 (max allowed is 1).",
            "14:5: NCSS for this method is 2 (max allowed is 0).",
            "21:5: NCSS for this method is 4 (max allowed is 0).",
            "30:5: NCSS for this method is 12 (max allowed is 0).",
            "42:13: NCSS for this method is 2 (max allowed is 0).",
            "49:5: NCSS for this class is 2 (max allowed is 1).",
            "56:1: NCSS for this class is 10 (max allowed is 1).",
            "61:5: NCSS for this method is 8 (max allowed is 0).",
        };

        verify(checkConfig, getPath("metrics" + File.separator
                + "JavaNCSSCheckTestInput.java"), expected);
    }
}

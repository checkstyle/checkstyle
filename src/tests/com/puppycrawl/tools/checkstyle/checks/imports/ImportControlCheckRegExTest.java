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
package com.puppycrawl.tools.checkstyle.checks.imports;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ImportControlCheckRegExTest extends BaseCheckTestSupport
{
    @Test
    public void testOne() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_one-re.xml");
        final String[] expected = {"5:1: Disallowed import - java.io.File."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", System.getProperty("testinputs.dir")
                + "/import-control_two-re.xml");
        final String[] expected = {
            "3:1: Disallowed import - java.awt.Image.",
            "4:1: Disallowed import - javax.swing.border.*.",
            "6:1: Disallowed import - java.awt.Button.ABORT.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }
}

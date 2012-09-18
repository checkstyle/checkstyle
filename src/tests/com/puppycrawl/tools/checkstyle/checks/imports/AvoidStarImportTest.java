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

public class AvoidStarImportTest
    extends BaseCheckTestSupport
{
    @Test
    public void testDefaultOperation()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        final String[] expected = {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.imports.*.",
            "9: Using the '.*' form of import should be avoided - java.io.*.",
            "10: Using the '.*' form of import should be avoided - java.lang.*.",
            "25: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
            "26: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
            "28: Using the '.*' form of import should be avoided - sun.net.ftpclient.FtpClient.*.",
        };

        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }

    @Test
    public void testExcludes()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("excludes",
            "java.io,java.lang,javax.swing.WindowConstants.*, javax.swing.WindowConstants");
        // allow the java.io/java.lang,javax.swing.WindowConstants star imports
        final String[] expected2 = new String[] {
            "7: Using the '.*' form of import should be avoided - com.puppycrawl.tools.checkstyle.imports.*.",
            "28: Using the '.*' form of import should be avoided - sun.net.ftpclient.FtpClient.*.",
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected2);
    }

    @Test
    public void testAllowClassImports() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(AvoidStarImportCheck.class);
        checkConfig.addAttribute("allowClassImports", "true");
        // allow all class star imports
        final String[] expected2 = new String[] {
            "25: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
            "26: Using the '.*' form of import should be avoided - javax.swing.WindowConstants.*.",
            "28: Using the '.*' form of import should be avoided - sun.net.ftpclient.FtpClient.*.", };
        verify(checkConfig, getPath("imports" + File.separator
            + "InputImport.java"), expected2);
    }
}

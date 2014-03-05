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
package com.puppycrawl.tools.checkstyle.checks.imports;

import static org.junit.Assert.fail;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.io.File;
import org.junit.Test;

public class ImportControlCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testOne() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "src/test/resources/com/puppycrawl/tools/checkstyle/import-control_one.xml");
        final String[] expected = {"5:1: Disallowed import - java.io.File."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testTwo() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file",
                "src/test/resources/com/puppycrawl/tools/checkstyle/import-control_two.xml");
        final String[] expected = {
            "3:1: Disallowed import - java.awt.Image.",
            "4:1: Disallowed import - javax.swing.border.*.",
            "6:1: Disallowed import - java.awt.Button.ABORT.",
        };

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testWrong() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file",
                "src/test/resources/com/puppycrawl/tools/checkstyle/import-control_wrong.xml");
        final String[] expected = {"1:40: Import control file does not handle this package."};

        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testMissing() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        final String[] expected = {"1:40: Missing an import control file."};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testEmpty() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "   ");
        final String[] expected = {"1:40: Missing an import control file."};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportControl.java"), expected);
    }

    @Test
    public void testUnknown() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file", "unknown-file");
        final String[] expected = {};
        try {
            verify(checkConfig, getPath("imports" + File.separator
                    + "InputImportControl.java"), expected);
            fail("should fail");
        }
        catch (CheckstyleException ex) {
            ;
        }
    }

    @Test
    public void testBroken() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(ImportControlCheck.class);
        checkConfig.addAttribute("file",
                "src/test/resources/com/puppycrawl/tools/checkstyle/import-control_broken.xml");
        final String[] expected = {};
        try {
            verify(checkConfig, getPath("imports" + File.separator
                    + "InputImportControl.java"), expected);
            fail("should fail");
        }
        catch (CheckstyleException ex) {
            ;
        }
    }
}

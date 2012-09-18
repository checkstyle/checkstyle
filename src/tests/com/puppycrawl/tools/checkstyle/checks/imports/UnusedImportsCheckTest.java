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

public class UnusedImportsCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:45: Unused import - com.puppycrawl.tools.checkstyle.imports.GlobalProperties.",
            "11:8: Unused import - java.lang.String.",
            "13:8: Unused import - java.util.List.",
            "14:8: Unused import - java.util.List.",
            "17:8: Unused import - java.util.Enumeration.",
            "20:8: Unused import - javax.swing.JToggleButton.",
            "22:8: Unused import - javax.swing.BorderFactory.",
            "27:15: Unused import - java.io.File.createTempFile.",
            //"29:8: Unused import - java.awt.Component.", // Should be detected
            "30:8: Unused import - java.awt.Graphics2D.",
            "31:8: Unused import - java.awt.HeadlessException.",
            "32:8: Unused import - java.awt.Label.",
            "33:8: Unused import - java.util.Date.",
            "34:8: Unused import - java.util.Calendar.",
            "35:8: Unused import - java.util.BitSet.",
        };
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImport.java"), expected);
    }

    @Test
    public void testProcessJavadoc() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        checkConfig.addAttribute("processJavadoc", "true");
        final String[] expected = {
            "8:45: Unused import - com.puppycrawl.tools.checkstyle.imports.GlobalProperties.",
            "11:8: Unused import - java.lang.String.",
            "13:8: Unused import - java.util.List.",
            "14:8: Unused import - java.util.List.",
            "17:8: Unused import - java.util.Enumeration.",
            "20:8: Unused import - javax.swing.JToggleButton.",
            "22:8: Unused import - javax.swing.BorderFactory.",
            "27:15: Unused import - java.io.File.createTempFile.",
            //"29:8: Unused import - java.awt.Component.", // Should be detected
            "32:8: Unused import - java.awt.Label.",
        };
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImport.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "package-info.java"), expected);
    }

    @Test
    public void testBug() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportBug.java"), expected);
    }
}

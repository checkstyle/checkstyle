////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

public class RedundantImportCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testWithChecker()
        throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(RedundantImportCheck.class);
        final String[] expected = {
            "7:1: Redundant import from the same package - com.puppycrawl.tools.checkstyle.imports.*.",
            "8:38: Redundant import from the same package - com.puppycrawl.tools.checkstyle.imports.GlobalProperties.",
            "10:1: Redundant import from the java.lang package - java.lang.*.",
            "11:1: Redundant import from the java.lang package - java.lang.String.",
            "14:1: Duplicate import to line 13 - java.util.List.",
            "26:1: Duplicate import to line 25 - javax.swing.WindowConstants.*.",
        };
        verify(checkConfig, getPath("imports" + File.separator + "InputImport.java"), expected);
    }
}

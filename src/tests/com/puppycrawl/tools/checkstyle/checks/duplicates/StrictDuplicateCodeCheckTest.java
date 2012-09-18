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
package com.puppycrawl.tools.checkstyle.checks.duplicates;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import org.junit.Test;

public class StrictDuplicateCodeCheckTest extends BaseCheckTestSupport
{

    @Override
    protected DefaultConfiguration createCheckerConfig(
            Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    @Test
    public void testDefaultSettings() throws Exception
    {
        final Configuration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        final String innerDupPath = getPath("duplicates/InnerDup.java");
        final String[] expected = {
            "6: Found duplicate of 13 lines in " + innerDupPath + ", starting from line 22",
        };
        final File[] checkedFiles = new File[] {
            new File(innerDupPath),
            new File(getPath("duplicates/Shorty.java")),
        };
        verify(createChecker(checkConfig), checkedFiles, innerDupPath, expected);
    }

    @Test
    public void testSmallMin() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        checkConfig.addAttribute("min", "3");
        final String aPath = getPath("duplicates/A.java");
        final String bPath = getPath("duplicates/B.java");
        final String[] expected = {
            // imports should not be marked because developer cannot avoid them
            // same constant def should not be marked because order is important for this check
        };
        final File[] checkedFiles = new File[] {
            new File(aPath),
            new File(bPath),
        };
        verify(createChecker(checkConfig), checkedFiles, aPath, expected);
    }

    @Test
    public void testOverlapping() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        checkConfig.addAttribute("min", "3");
        final String path = getPath("duplicates/Overlapping.java");
        final String[] expected = {
            "6: Found duplicate of 3 lines in " + path + ", starting from line 13",
            "6: Found duplicate of 5 lines in " + path + ", starting from line 25",
            "7: Found duplicate of 5 lines in " + path + ", starting from line 19",
            "13: Found duplicate of 3 lines in " + path + ", starting from line 25",
            "19: Found duplicate of 4 lines in " + path + ", starting from line 26",
        };
        final File[] checkedFiles = new File[] {
            new File(path),
        };
        final Checker checker = createChecker(checkConfig);
        verify(checker, checkedFiles, path, expected);
    }

    @Test
    public void testWhitespaceIsIgnored() throws Exception
    {
        final DefaultConfiguration checkConfig = createCheckConfig(StrictDuplicateCodeCheck.class);
        final String path = getPath("duplicates/LotsOfEmptyLines.java");
        final String[] expected = {
        };
        final File[] checkedFiles = new File[] {
            new File(path),
        };
        final Checker checker = createChecker(checkConfig);
        verify(checker, checkedFiles, path, expected);
    }
}

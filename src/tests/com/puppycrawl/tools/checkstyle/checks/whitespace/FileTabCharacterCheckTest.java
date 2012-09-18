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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import org.junit.Test;

public class FileTabCharacterCheckTest
    extends BaseCheckTestSupport
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
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig = createConfig(false);
        final String[] expected = {
            "19:25: File contains tab characters (this is the first instance).",
        };
        final File[] files = {
            new File(getPath("InputSimple.java")),
        };
        verify(createChecker(checkConfig), files, getPath("InputSimple.java"),
            expected);
    }

    @Test
    public void testVerbose() throws Exception
    {
        final DefaultConfiguration checkConfig = createConfig(true);
        final String[] expected = {
            "19:25: Line contains a tab character.",
            "145:35: Line contains a tab character.",
            "146:64: Line contains a tab character.",
            "154:9: Line contains a tab character.",
            "155:10: Line contains a tab character.",
            "156:1: Line contains a tab character.",
            "157:3: Line contains a tab character.",
            "158:3: Line contains a tab character.",
        };
        final File[] files = {
            new File(getPath("InputSimple.java")),
        };
        verify(createChecker(checkConfig), files, getPath("InputSimple.java"),
            expected);
    }

    @Test
    public void testBadFile() throws Exception
    {
        final DefaultConfiguration checkConfig = createConfig(false);
        final String path = getPath("Claira");
        final String[] expected = {
            "0: File not found!",
        };
        final File[] files = {
            new File(path),
        };
        verify(createChecker(checkConfig), files, path, expected);
    }

    /**
     * Creates a configuration that is functionally close to that in the docs.
     * @param verbose TODO
     */
    private DefaultConfiguration createConfig(boolean verbose)
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileTabCharacterCheck.class);
        checkConfig.addAttribute("eachLine", Boolean.toString(verbose));
        return checkConfig;
    }
}

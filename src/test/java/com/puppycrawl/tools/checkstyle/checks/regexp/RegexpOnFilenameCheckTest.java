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
package com.puppycrawl.tools.checkstyle.checks.regexp;

import com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test of {@link RegexpOnFilenameCheck}.
 *
 * @author Thomas Jensen
 */
public class RegexpOnFilenameCheckTest
    extends BaseFileSetCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;



    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RegexpOnFilenameCheck.class);
    }



    @Test
    public void testIt()
        throws Exception
    {
        final String filepath = getPath("regexp/InputRegexpOnFilename.txt");
        final String illegal = ".*?[\\\\/]InputRegexpOnFilename\\.txt$";
        mCheckConfig.addAttribute("regexp", illegal);
        mCheckConfig.addAttribute("fileExtensions", "java, txt");
        final String[] expected = {"0: File path '" + filepath + "' matches illegal pattern '" + illegal + "'.",};
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testIt2()
        throws Exception
    {
        final String filepath = getPath("regexp/InputRegexpOnFilename.txt");
        final String illegal = ".*?[\\\\/]src[\\\\/]main[\\\\/]resources[\\\\/]com[\\\\/]puppycrawl[\\\\/].*?\\.txt$";
        mCheckConfig.addAttribute("regexp", illegal);
        final String[] expected = {"0: File path '" + filepath + "' matches illegal pattern '" + illegal + "'.",};
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testNoRegexp()
        throws Exception
    {
        final String filepath = getPath("regexp/InputRegexpOnFilename.txt");
        mCheckConfig.addAttribute("fileExtensions", "txt");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testBrokenRegexp()
        throws Exception
    {
        final String filepath = getPath("regexp/InputRegexpOnFilename.txt");
        final String illegal = "*$"; // incorrect syntax
        mCheckConfig.addAttribute("regexp", illegal);
        mCheckConfig.addAttribute("fileExtensions", "txt");
        try {
            final String[] expected = {};
            verify(mCheckConfig, filepath, expected);
            Assert.fail("CheckstyleException was not thrown");
        }
        catch (CheckstyleException expected) {
            // expected
        }
    }
}

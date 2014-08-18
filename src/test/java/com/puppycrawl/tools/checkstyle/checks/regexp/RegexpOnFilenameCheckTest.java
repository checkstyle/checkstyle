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
    private static final String REAL_EXT = "txt";

    private static final String SIMPLE_FILENAME = "InputRegexpOnFilename." + REAL_EXT;

    private DefaultConfiguration mCheckConfig;



    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RegexpOnFilenameCheck.class);
    }



    @Test
    public void testSelectByExtension_Include1()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("fileExtensions", "java, " + REAL_EXT);
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        mCheckConfig.addAttribute("required", "true");
        final String[] expected =
        {"0: File '" + SIMPLE_FILENAME + "' does not contain required pattern '" + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testSelectByExtension_Include2()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("fileExtensions", REAL_EXT);
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        mCheckConfig.addAttribute("required", "true");
        final String[] expected =
        {"0: File '" + SIMPLE_FILENAME + "' does not contain required pattern '" + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testSelectByExtension_Exclude()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("fileExtensions", "noMatch");
        mCheckConfig.addAttribute("regexp", regexp);
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testSelectByRegexp_Include1()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("fileExtensions", REAL_EXT);
        mCheckConfig.addAttribute("selection", ".*?" + SIMPLE_FILENAME + "$");
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        mCheckConfig.addAttribute("required", "true");
        final String[] expected =
        {"0: File '" + SIMPLE_FILENAME + "' does not contain required pattern '" + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testSelectByRegexp_Include2()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("selection", ".*?" + SIMPLE_FILENAME + "$");
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        mCheckConfig.addAttribute("required", "true");
        final String[] expected =
        {"0: File '" + SIMPLE_FILENAME + "' does not contain required pattern '" + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testSelectByRegexp_Exclude()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("selection", "^no_match");
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        mCheckConfig.addAttribute("required", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testNoRegexpsGiven_Ok()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testIllegal()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = ".*?[\\\\/]src[\\\\/]test[\\\\/]resources[\\\\/].*";
        mCheckConfig.addAttribute("regexp", regexp);
        final String[] expected = {"0: File '" + filepath + "' matches illegal pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequired()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = ".*?[\\\\/]src[\\\\/]test[\\\\/]resources[\\\\/].*";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testIllegal_Not()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequired_Not()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        final String[] expected = {"0: File '" + filepath + "' does not match required pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testIllegalSubstring()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "[\\\\/]src[\\\\/]test[\\\\/]resources[\\\\/]";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {"0: File '" + filepath + "' contains illegal pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequiredSubstring()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "[\\\\/]src[\\\\/]test[\\\\/]resources[\\\\/]";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testIllegalSubstring_Not()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequiredSubstring_Not()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "no_match";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {"0: File '" + filepath + "' does not contain required pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }



    @Test
    public void testIllegalSimple()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "^" + SIMPLE_FILENAME + "$";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("simple", "true");
        final String[] expected = {"0: File '" + SIMPLE_FILENAME + "' matches illegal pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequiredSimple()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = "^" + SIMPLE_FILENAME + "$";
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        mCheckConfig.addAttribute("simple", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testIllegalSimpleSubstring()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = SIMPLE_FILENAME.substring(2, SIMPLE_FILENAME.length() - 5);
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {"0: File '" + SIMPLE_FILENAME + "' contains illegal pattern '"
                + regexp + "'.", };
        verify(mCheckConfig, filepath, expected);
    }

    @Test
    public void testRequiredSimpleSubstring()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String regexp = SIMPLE_FILENAME.substring(2, SIMPLE_FILENAME.length() - 5);
        mCheckConfig.addAttribute("regexp", regexp);
        mCheckConfig.addAttribute("required", "true");
        mCheckConfig.addAttribute("simple", "true");
        mCheckConfig.addAttribute("substring", "true");
        final String[] expected = {};
        verify(mCheckConfig, filepath, expected);
    }


    @Test
    public void testBrokenRegexp()
        throws Exception
    {
        final String filepath = getPath("regexp/" + SIMPLE_FILENAME);
        final String illegal = "*$"; // incorrect syntax
        mCheckConfig.addAttribute("regexp", illegal);
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

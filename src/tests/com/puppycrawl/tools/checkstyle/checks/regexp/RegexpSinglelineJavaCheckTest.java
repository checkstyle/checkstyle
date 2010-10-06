////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Before;
import org.junit.Test;

public class RegexpSinglelineJavaCheckTest extends BaseCheckTestSupport
{
    private DefaultConfiguration mCheckConfig;

    @Before
    public void setUp()
    {
        mCheckConfig = createCheckConfig(RegexpSinglelineJavaCheck.class);
    }

    @Test
    public void testIt() throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
        throws Exception
    {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception
    {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreCase", "false");
        final String[] expected = {};
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "2: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCStyle() throws Exception
    {
        // See if the comment is removed properly
        final String illegal = "c-style 1";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCStyle() throws Exception
    {
        final String illegal = "c-style 1";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "17: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleCStyle() throws Exception
    {
        // See if a second comment on the same line is removed properly
        final String illegal = "c-style 2";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception
    {
        final String illegal = "Let's check multi-line comments";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception
    {
        final String illegal = "long ms /";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception
    {
        final String illegal = "int z";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "20: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception
    {
        final String illegal = "int y";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "21: Line matches the illegal pattern '" + illegal + "'.",
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception
    {
        // make sure the comment is not turned into spaces
        final String illegal = "long ms  ";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void test1371588() throws Exception
    {
        // StackOverflowError with trailing space and ignoreComments
        final String illegal = "\\s+$";
        mCheckConfig.addAttribute("format", illegal);
        mCheckConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testExistingInDoc() throws Exception
    {
        final String required = "Test case file";
        mCheckConfig.addAttribute("format", required);
        mCheckConfig.addAttribute("minimum", "1");
        mCheckConfig.addAttribute("maximum", "1000");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testExistingInCode() throws Exception
    {
        final String required = "package";
        mCheckConfig.addAttribute("format", required);
        mCheckConfig.addAttribute("minimum", "1");
        mCheckConfig.addAttribute("maximum", "1000");
        final String[] expected = {
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMissing() throws Exception
    {
        final String required = "This text is not in the file";
        mCheckConfig.addAttribute("format", required);
        mCheckConfig.addAttribute("minimum", "1");
        mCheckConfig.addAttribute("maximum", "1000");
        final String[] expected = {
            "0: File does not contain at least 1 matches for pattern '" + required + "'.",
        };
        verify(mCheckConfig, getPath("InputSemantic.java"), expected);
    }
}

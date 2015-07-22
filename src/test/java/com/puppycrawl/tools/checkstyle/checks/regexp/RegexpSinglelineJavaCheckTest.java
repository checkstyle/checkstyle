////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.REGEXP_MINIMUM;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class RegexpSinglelineJavaCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(RegexpSinglelineJavaCheck.class);
    }

    @Test
    public void testIt() throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        checkConfig.addAttribute("format", illegal);
        final String[] expected = {
            "69: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
        throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final String message = "Bad line :(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + message,
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String illegal = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = {};
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "4: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "c-style 1";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCStyle() throws Exception {
        final String illegal = "c-style 1";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "19: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleCStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final String illegal = "c-style 2";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final String illegal = "Let's check multi-line comments";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final String illegal = "long ms /";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final String illegal = "int z";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "22: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final String illegal = "int y";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "23: " + getCheckMessage(REGEXP_EXCEEDED, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final String illegal = "long ms  ";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void test1371588() throws Exception {
        // StackOverflowError with trailing space and ignoreComments
        final String illegal = "\\s+$";
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testExistingInDoc() throws Exception {
        final String required = "Test case file";
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testExistingInCode() throws Exception {
        final String required = "package";
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final String required = "This text is not in the file";
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = {
            "0: " + getCheckMessage(REGEXP_MINIMUM, 1, required),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }
}

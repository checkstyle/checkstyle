////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_DUPLICATE_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_ILLEGAL_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_REQUIRED_REGEXP;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class RegexpCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "regexp" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final RegexpCheck checkObj = new RegexpCheck();
        assertArrayEquals(CommonUtils.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
    }

    @Test
    public void testRequiredPass() throws Exception {
        final String required = "Test case file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", required);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testRequiredFail() throws Exception {
        final String required = "This text is not in the file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", required);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REQUIRED_REGEXP, required),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesPass() throws Exception {
        final String required = "Test case file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("duplicateLimit", "0");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testSetDuplicatesTrue() throws Exception {
        final String required = "Test case file";
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("duplicateLimit", "-1");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesFail() throws Exception {
        final String required = "Boolean x = new Boolean";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", required);
        checkConfig.addAttribute("duplicateLimit", "0");
        final String[] expected = {
            "24: " + getCheckMessage(MSG_DUPLICATE_REGEXP, required),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIllegalPass() throws Exception {
        final String illegal = "This text is not in the file";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final String illegal = "^import";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("errorLimit", "4");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
            "8: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
            "9: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailAboveErrorLimit() throws Exception {
        final String illegal = "^import";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("errorLimit", "3");
        final String error = "The error limit has been exceeded, "
                + "the check is aborting, there may be more unreported errors.";
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
            "8: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
            "9: " + getCheckMessage(MSG_ILLEGAL_REGEXP, error + illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessagePropertyGood()
        throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        final String message = "Bad line :(";
        checkConfig.addAttribute("message", message);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, message),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testMessagePropertyBad()
        throws Exception {
        final String illegal = "System\\.(out)|(err)\\.print(ln)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("message", null);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String illegal = "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String illegalTrue = "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfigTrue =
            createCheckConfig(RegexpCheck.class);
        checkConfigTrue.addAttribute("format", illegalTrue);
        checkConfigTrue.addAttribute("illegalPattern", "true");
        final String[] expectedTrue = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegalTrue),
        };
        verify(checkConfigTrue, getPath("InputSemantic.java"), expectedTrue);

        final String illegalFalse = "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(";
        final DefaultConfiguration checkConfigFalse =
            createCheckConfig(RegexpCheck.class);
        checkConfigFalse.addAttribute("format", illegalFalse);
        checkConfigFalse.addAttribute("illegalPattern", "true");
        final String[] expectedFalse = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfigFalse, getPath("InputSemantic.java"), expectedFalse);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "don't use trailing comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCStyle() throws Exception {
        // See if the comment is removed properly
        final String illegal = "c-style 1";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCStyle() throws Exception {
        final String illegal = "c-style 1";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleCStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final String illegal = "c-style 2";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final String illegal = "Let's check multi-line comments";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final String illegal = "long ms /";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final String illegal = "int z";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final String illegal = "int y";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_ILLEGAL_REGEXP, illegal),
        };
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final String illegal = "long ms  ";
        final DefaultConfiguration checkConfig =
            createCheckConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", illegal);
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputTrailingComment.java"), expected);
    }

    @Test
    public void testOnFileStartingWithEmptyLine() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(RegexpCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputStartingWithEmptyLine.java"), expected);
    }
}

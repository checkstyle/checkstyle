////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexp";
    }

    @Test
    public void testGetAcceptableTokens() {
        final RegexpCheck regexpCheck = new RegexpCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, regexpCheck.getAcceptableTokens(),
                "RegexpCheck#getAcceptableTokens should return empty array by default");
    }

    @Test
    public void testGetRequiredTokens() {
        final RegexpCheck checkObj = new RegexpCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "RegexpCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testRequiredPass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "Test case file");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testRequiredFail() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "This text is not in the file");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REQUIRED_REGEXP, "This text is not in the file"),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesPass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "Test case file");
        checkConfig.addAttribute("duplicateLimit", "0");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testSetDuplicatesTrue() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "Test case file");
        checkConfig.addAttribute("duplicateLimit", "-1");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesFail() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "Boolean x = new Boolean");
        checkConfig.addAttribute("duplicateLimit", "0");
        final String[] expected = {
            "24: " + getCheckMessage(MSG_DUPLICATE_REGEXP, "Boolean x = new Boolean"),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testIllegalPass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "This text is not in the file");
        checkConfig.addAttribute("illegalPattern", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "^import");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("errorLimit", "4");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "8: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "9: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testIllegalFailAboveErrorLimit() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "^import");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("errorLimit", "2");
        final String error = "The error limit has been exceeded, "
                + "the check is aborting, there may be more unreported errors.";
        final String[] expected = {
            "7: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "8: " + getCheckMessage(MSG_ILLEGAL_REGEXP, error + "^import"),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testMessagePropertyGood()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("message", "Bad line :(");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Bad line :("),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testMessagePropertyBad()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("message", null);
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testMessagePropertyBad2()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("message", "");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfig.addAttribute("illegalPattern", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final DefaultConfiguration checkConfigTrue =
            createModuleConfig(RegexpCheck.class);
        checkConfigTrue.addAttribute("format", "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfigTrue.addAttribute("illegalPattern", "true");
        final String[] expectedTrue = {
            "69: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verify(checkConfigTrue, getPath("InputRegexpSemantic.java"), expectedTrue);

        final DefaultConfiguration checkConfigFalse =
            createModuleConfig(RegexpCheck.class);
        checkConfigFalse.addAttribute("format", "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfigFalse.addAttribute("illegalPattern", "true");
        final String[] expectedFalse = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfigFalse, getPath("InputRegexpSemantic.java"), expectedFalse);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "don't use trailing comments");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "don't use trailing comments");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "don't use trailing comments"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsBlockStyle() throws Exception {
        // See if the comment is removed properly
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "c-style 1");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "c-style 1");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "c-style 1"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleBlockStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "c-style 2");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "Let's check multi-line comments");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "long ms /");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "int z");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int z"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "int y");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int y"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "long ms  ");
        checkConfig.addAttribute("illegalPattern", "true");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testOnFileStartingWithEmptyLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RegexpCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpStartingWithEmptyLine.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCppStyleWithIllegalPatternFalse() throws Exception {
        // See if the comment is removed properly
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpCheck.class);
        checkConfig.addAttribute("format", "don't use trailing comments");
        checkConfig.addAttribute("illegalPattern", "false");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REQUIRED_REGEXP, "don't use trailing comments"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment.java"), expected);
    }

}

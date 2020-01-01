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

import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpSinglelineJavaCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpsinglelinejava";
    }

    @Test
    public void testGetAcceptableTokens() {
        final RegexpSinglelineJavaCheck regexpSinglelineJavaCheck =
            new RegexpSinglelineJavaCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY,
                regexpSinglelineJavaCheck.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final RegexpSinglelineJavaCheck checkObj = new RegexpSinglelineJavaCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "System\\.(out)|(err)\\.print(ln)?\\(");
        checkConfig.addAttribute("message", "Bad line :(");
        final String[] expected = {
            "69: " + "Bad line :(",
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfig.addAttribute("ignoreCase", "true");
        final String[] expected = {
            "69: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\(");
        checkConfig.addAttribute("ignoreCase", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // See if the comment is removed properly
        checkConfig.addAttribute("format", "don't use trailing comments");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // See if the comment is removed properly
        checkConfig.addAttribute("format", "don't use trailing comments");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "4: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "don't use trailing comments"),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // See if the comment is removed properly
        checkConfig.addAttribute("format", "c-style 1");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "c-style 1");
        checkConfig.addAttribute("ignoreComments", "false");
        final String[] expected = {
            "19: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "c-style 1"),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleBlockStyle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // See if a second comment on the same line is removed properly
        checkConfig.addAttribute("format", "c-style 2");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "Let's check multi-line comments");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "long ms /");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "int z");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "22: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "int z"),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "int y");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "int y"),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // make sure the comment is not turned into spaces
        checkConfig.addAttribute("format", "long ms  ");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void test1371588() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        // StackOverflowError with trailing space and ignoreComments
        checkConfig.addAttribute("format", "\\s+$");
        checkConfig.addAttribute("ignoreComments", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testExistingInDoc() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "Test case file");
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testExistingInCode() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "package");
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RegexpSinglelineJavaCheck.class);
        checkConfig.addAttribute("format", "This text is not in the file");
        checkConfig.addAttribute("minimum", "1");
        checkConfig.addAttribute("maximum", "1000");
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_MINIMUM, 1, "This text is not in the file"),
        };
        verify(checkConfig, getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

}

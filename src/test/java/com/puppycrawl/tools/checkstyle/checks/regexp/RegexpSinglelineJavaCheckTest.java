///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_EXCEEDED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.MultilineDetector.MSG_REGEXP_MINIMUM;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        assertWithMessage("Default acceptable tokens are invalid")
                .that(regexpSinglelineJavaCheck.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    public void testGetRequiredTokens() {
        final RegexpSinglelineJavaCheck checkObj = new RegexpSinglelineJavaCheck();
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEmpty();
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic.java"), expected);
    }

    @Test
    public void testMessageProperty()
            throws Exception {
        final String[] expected = {
            "78: " + "Bad line :(",
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic2.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic3.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic4.java"), expected);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = {
            "16: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "don't\\suse trailing comments"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment2.java"), expected);
    }

    @Test
    public void testIgnoreCommentsBlockStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment3.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseBlockStyle() throws Exception {
        final String[] expected = {
            "31: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "c-style\\s1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment4.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleBlockStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment5.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment6.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment7.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final String[] expected = {
            "34: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "int z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment8.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final String[] expected = {
            "35: " + getCheckMessage(MSG_REGEXP_EXCEEDED, "int y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment9.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment10.java"), expected);
    }

    @Test
    public void test1371588() throws Exception {
        // StackOverflowError with trailing space and ignoreComments
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaTrailingComment11.java"), expected);
    }

    @Test
    public void testExistingInDoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic5.java"), expected);
    }

    @Test
    public void testExistingInCode() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic6.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REGEXP_MINIMUM, 1, "This\\stext is not in the file"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic7.java"), expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSinglelineJavaSemantic8.java"), expected);
    }
}

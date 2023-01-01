///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_DUPLICATE_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_ILLEGAL_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_REQUIRED_REGEXP;

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
        assertWithMessage("RegexpCheck#getAcceptableTokens should return empty array by default")
                .that(regexpCheck.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    public void testGetRequiredTokens() {
        final RegexpCheck checkObj = new RegexpCheck();
        assertWithMessage("RegexpCheck#getRequiredTokens should return empty array by default")
                .that(checkObj.getRequiredTokens())
                .isEmpty();
    }

    @Test
    public void testRequiredPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    public void testRequiredFail() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(RegexpCheck.class);
        checkConfig.addProperty("format", "This\\stext is not in the file");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REQUIRED_REGEXP, "This\\stext is not in the file"),
        };
        verify(checkConfig, getPath("InputRegexpSemantic2.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic3.java"), expected);
    }

    @Test
    public void testSetDuplicatesTrue() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic4.java"), expected);
    }

    @Test
    public void testRequiredNoDuplicatesFail() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_DUPLICATE_REGEXP, "Boolean x = new Boolean"),
            "32: " + getCheckMessage(MSG_DUPLICATE_REGEXP, "Boolean x = new Boolean"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic5.java"), expected);
    }

    @Test
    public void testIllegalPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic6.java"), expected);
    }

    @Test
    public void testStopEarly() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpCheckStopEarly.java"), expected);
    }

    @Test
    public void testIllegalFailBelowErrorLimit() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "17: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic7.java"), expected);
    }

    @Test
    public void testIllegalFailAboveErrorLimit() throws Exception {
        final String error = "The error limit has been exceeded, "
                + "the check is aborting, there may be more unreported errors.";
        final String[] expected = {
            "15: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, error + "^import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic8.java"), expected);
    }

    @Test
    public void testMessagePropertyGood()
            throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Bad line :("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic9.java"), expected);
    }

    @Test
    public void testMessagePropertyBad()
            throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic10.java"), expected);
    }

    @Test
    public void testMessagePropertyBad2()
            throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic11.java"), expected);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String[] expected = {
            "77: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic12.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        final String[] expectedTrue = {
            "77: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic13.java"), expectedTrue);

        final String[] expectedFalse = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic14.java"), expectedFalse);
    }

    @Test
    public void testIgnoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = {
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "don't\\suse trailing comments"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment2.java"), expected);
    }

    @Test
    public void testIgnoreCommentsBlockStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment3.java"), expected);
    }

    @Test
    public void testIgnoreCommentsFalseBlockStyle() throws Exception {
        final String[] expected = {
            "31: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "c-style\\s1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment4.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultipleBlockStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment5.java"), expected);
    }

    @Test
    public void testIgnoreCommentsMultiLine() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment6.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineStart() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment7.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineEnd() throws Exception {
        final String[] expected = {
            "34: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment8.java"), expected);
    }

    @Test
    public void testIgnoreCommentsInlineMiddle() throws Exception {
        final String[] expected = {
            "35: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment9.java"), expected);
    }

    @Test
    public void testIgnoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment10.java"), expected);
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
        checkConfig.addProperty("format", "don't use trailing comments");
        checkConfig.addProperty("illegalPattern", "false");
        checkConfig.addProperty("ignoreComments", "true");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_REQUIRED_REGEXP, "don't use trailing comments"),
        };
        verify(checkConfig, getPath("InputRegexpTrailingComment11.java"), expected);
    }

}

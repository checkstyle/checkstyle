///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_DUPLICATE_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_ILLEGAL_REGEXP;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_REQUIRED_REGEXP;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexp";
    }

    @Test
    void getAcceptableTokens() {
        final RegexpCheck regexpCheck = new RegexpCheck();
        assertWithMessage("RegexpCheck#getAcceptableTokens should return empty array by default")
                .that(regexpCheck.getAcceptableTokens())
                .isEmpty();
    }

    @Test
    void getRequiredTokens() {
        final RegexpCheck checkObj = new RegexpCheck();
        assertWithMessage("RegexpCheck#getRequiredTokens should return empty array by default")
                .that(checkObj.getRequiredTokens())
                .isEmpty();
    }

    @Test
    void requiredPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic.java"), expected);
    }

    @Test
    void requiredFail() throws Exception {
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REQUIRED_REGEXP, "This\\stext is not in the file"),
        };
        verifyWithInlineConfigParser(getPath("InputRegexpSemantic2.java"), expected);
    }

    @Test
    void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpCheckDefault.java"), expected);
    }

    @Test
    void requiredNoDuplicatesPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic3.java"), expected);
    }

    @Test
    void setDuplicatesTrue() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic4.java"), expected);
    }

    @Test
    void requiredNoDuplicatesFail() throws Exception {
        final String[] expected = {
            "27: " + getCheckMessage(MSG_DUPLICATE_REGEXP, "Boolean x = new Boolean"),
            "32: " + getCheckMessage(MSG_DUPLICATE_REGEXP, "Boolean x = new Boolean"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic5.java"), expected);
    }

    @Test
    void illegalPass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic6.java"), expected);
    }

    @Test
    void stopEarly() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpCheckStopEarly.java"), expected);
    }

    @Test
    void illegalFailBelowErrorLimit() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
            "17: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic7.java"), expected);
    }

    @Test
    void illegalFailAboveErrorLimit() throws Exception {
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
    void messagePropertyGood()
            throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Bad line :("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic9.java"), expected);
    }

    @Test
    void messagePropertyBad()
            throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic10.java"), expected);
    }

    @Test
    void messagePropertyBad2()
            throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic11.java"), expected);
    }

    @Test
    void ignoreCaseTrue() throws Exception {
        final String[] expected = {
            "78: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic12.java"), expected);
    }

    @Test
    void ignoreCaseFalse() throws Exception {
        final String[] expectedTrue = {
            "78: " + getCheckMessage(MSG_ILLEGAL_REGEXP,
                    "(?i)SYSTEM\\.(OUT)|(ERR)\\.PRINT(LN)?\\("),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic13.java"), expectedTrue);

        final String[] expectedFalse = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpSemantic14.java"), expectedFalse);
    }

    @Test
    void ignoreCommentsCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment.java"), expected);
    }

    @Test
    void ignoreCommentsFalseCppStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = {
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "don't\\suse trailing comments"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment2.java"), expected);
    }

    @Test
    void ignoreCommentsBlockStyle() throws Exception {
        // See if the comment is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment3.java"), expected);
    }

    @Test
    void ignoreCommentsFalseBlockStyle() throws Exception {
        final String[] expected = {
            "31: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "c-style\\s1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment4.java"), expected);
    }

    @Test
    void ignoreCommentsMultipleBlockStyle() throws Exception {
        // See if a second comment on the same line is removed properly
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment5.java"), expected);
    }

    @Test
    void ignoreCommentsMultiLine() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment6.java"), expected);
    }

    @Test
    void ignoreCommentsInlineStart() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment7.java"), expected);
    }

    @Test
    void ignoreCommentsInlineEnd() throws Exception {
        final String[] expected = {
            "34: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment8.java"), expected);
    }

    @Test
    void ignoreCommentsInlineMiddle() throws Exception {
        final String[] expected = {
            "35: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "int y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment9.java"), expected);
    }

    @Test
    void ignoreCommentsNoSpaces() throws Exception {
        // make sure the comment is not turned into spaces
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRegexpTrailingComment10.java"), expected);
    }

    @Test
    void onFileStartingWithEmptyLine() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputRegexpStartingWithEmptyLine.java"), expected);
    }

    @Test
    void ignoreCommentsCppStyleWithIllegalPatternFalse() throws Exception {
        // See if the comment is removed properly
        final String[] expected = {
            "1: " + getCheckMessage(MSG_REQUIRED_REGEXP, "don't use trailing comments"),
        };
        verifyWithInlineConfigParser(getPath("InputRegexpTrailingComment11.java"), expected);
    }

    @Test
    void stateIsClearedOnBeginTreeErrorCount() throws Exception {
        final String file1 = getPath("InputRegexpCheckB2.java");
        final String file2 = getPath("InputRegexpCheckB1.java");
        final List<String> expectedFromFile1 = List.of(
            "12: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import")
        );
        final List<String> expectedFromFile2 = List.of(
            "12: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "^import")
        );
        verifyWithInlineConfigParser(file1, file2, expectedFromFile1, expectedFromFile2);
    }

    @Test
    void stateIsClearedOnBeginTreeMatchCount() throws Exception {
        final String file1 = getPath("InputRegexpCheckB3.java");
        final String file2 = getPath("InputRegexpCheckB4.java");
        final List<String> expectedFirstInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        verifyWithInlineConfigParser(file1, file2,
                expectedFirstInput, expectedSecondInput);
    }

    @Test
    void onFileStartingWithEmptyLine2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputRegexpCheckEmptyLine2.java"),
                expected);
    }
}

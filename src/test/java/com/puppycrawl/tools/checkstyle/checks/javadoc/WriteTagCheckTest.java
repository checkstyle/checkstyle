///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck.MSG_KEY_UNCLOSED_HTML_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck.MSG_TAG_FORMAT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Unit test for WriteTagCheck.
 */
public class WriteTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/writetag";
    }

    @Test
    public void testDefaultSettings() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputWriteTagDefault.java"), expected);
    }

    @Test
    public void testTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(getPath("InputWriteTag.java"), expected);
    }

    @Test
    public void testMissingFormat() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingFormat.java"), expected);
    }

    @Test
    public void testTagIncomplete() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagIncomplete.java"), expected);
    }

    @Test
    public void testDoubleTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagDoubleTag.java"), expected);
    }

    @Test
    public void testEmptyTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEmptyTag.java"), expected);
    }

    @Test
    public void testMissingTag() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_MISSING_TAG, "@missingtag"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingTag.java"), expected);
    }

    @Test
    public void testMissingTagOnMethod() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMissingTagMethod.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "20:1: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
                getNonCompilablePath("compact/InputWriteTagCheckNonCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getNonCompilablePath("compact/InputWriteTagCheckDefaultTokens.java"), expected);
    }

    @Test
    public void testTagContent() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getNonCompilablePath("compact/InputWriteTagCheckExactTagContent.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagInterface.java"), expected
        );
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
            "24:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEnum.java"), expected
        );
    }

    @Test
    public void testSince() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
            "38:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagSince.java"),
                expected);
    }

    @Test
    public void testBlockComment() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagBlockComment.java"), expected
        );
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagMethod.java"), expected);
    }

    @Test
    public void testIgnoreMissing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(getPath("InputWriteTagIgnore.java"), expected);
    }

    @Test
    public void testRegularEx() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagRegularExpression.java"), expected);
    }

    @Test
    public void testRegularExError() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_TAG_FORMAT, "@author", "ABC"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagExpressionError.java"), expected);
    }

    @Test
    public void testEnumsAndAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagEnumsAndAnnotations.java"), expected);
    }

    @Test
    public void testNoJavadocs() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParserTwice(getPath("InputWriteTagNoJavadoc.java"), expected);
    }

    @Test
    public void testWriteTagRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_TAG_FORMAT, "@incomplete", "\\S"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagRecordsAndCompactCtors.java"), expected);
    }

    @Test
    public void testTagTypeAuthor() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeAuthor.java"), expected);
    }

    @Test
    public void testTagTypeCustom() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeCustom.java"), expected);
    }

    @Test
    public void testTagTypeDeprecated() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeDeprecated.java"), expected);
    }

    @Test
    public void testTagTypeParam() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeParam.java"), expected);
    }

    @Test
    public void testTagTypeReturn() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeReturn.java"), expected);
    }

    @Test
    public void testTagTypeSee() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeSee.java"), expected);
    }

    @Test
    public void testTagTypeSince() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
            "35:19: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeSince.java"), expected);
    }

    @Test
    public void testTagTypeThrows() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTypeThrows.java"), expected);
    }

    @Test
    public void testNonTightHtml() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_UNCLOSED_HTML_TAG, "p"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagNonTightHtml.java"), expected);
    }

    @Test
    public void testCommentAboveJavadoc() throws Exception {
        final String[] expected = {
            "27:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
            "42:5: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
                getPath("InputWriteTagCheckCommentAboveJavadoc.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("package-info.java"), expected);
    }

    @Test
    public void testTwoClasses() throws Exception {
        final String[] expected = {
            "23:1: " + getCheckMessage(MSG_MISSING_TAG, "@incomplete"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagTwoClasses.java"), expected);
    }

    @Test
    public void testAnnotationProcessing() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagAnnotationProcessing.java"), expected);
    }

    @Test
    public void testAnnotationValue() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagAnnotationValue.java"), expected);
    }

    @Test
    public void testMultipleAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagMultipleAnnotationsNoJavadoc.java"),
            expected);
    }

    @Test
    public void testBetweenAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagSingleAnnotation.java"), expected);
    }

    @Test
    public void testIssue20875() throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_MISSING_TAG, "@since"),
        };
        verifyWithInlineConfigParserTwice(
            getPath("InputWriteTagIssue20875.java"), expected);
    }

}

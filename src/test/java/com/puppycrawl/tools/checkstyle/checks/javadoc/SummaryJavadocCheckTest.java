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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_MISSING_PERIOD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SummaryJavadocCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc";
    }

    @Test
    public void testGetRequiredTokens() {
        final SummaryJavadocCheck checkObj = new SummaryJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocCorrect.java"), expected);
    }

    @Test
    public void testCorrect2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocCorrect2.java"), expected);
    }

    @Test
    public void testInlineCorrect() throws Exception {
        final String[] expected = {
            "113:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect.java"), expected);
    }

    @Test
    public void testInlineCorrectTwo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect2.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "21:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "26:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "44:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "65:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "70:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "81:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "41:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "54:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "64:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "70:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "75:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "78:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect4.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final String[] expected = {
            "27:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "32:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "37:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "42:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "46:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "50:12: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "60:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden.java"), expected);
    }

    @Test
    public void testInlineForbidden2() throws Exception {
        final String[] expected = {
            "21:20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "35:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "49:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "55:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden2.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "20:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "38:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriod.java"), expected);
    }

    @Test
    public void testNoPeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocNoPeriod.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "20:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "25:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "43:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "48:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "64:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect2.java"), expected);
    }

    @Test
    public void testDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "39:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "52:13: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "62:13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "68:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "73:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "76:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect5.java"), expected);
    }

    @Test
    public void testIncorrectUsageOfSummaryTag() throws Exception {
        final String[] expected = {
            "36:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "42:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "50:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "59:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "65:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "76:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            // Until https://github.com/checkstyle/checkstyle/issues/11425
            "83:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "95:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "105:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "111:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
            getPath("InputSummaryJavadocIncorrect3.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final String[] expected = {
            "23:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "27:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "31:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "45:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "57:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "61:12: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration2() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "23:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "28:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "38:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "55:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "59:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "81:8: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault2.java"), expected);
    }

    @Test
    public void testInlineReturn() throws Exception {
        final String[] expected = {
            "75:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "91:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn.java"), expected);
    }

    @Test
    public void testInlineReturn2() throws Exception {
        final String[] expected = {
            "16:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn2.java"), expected);
    }

    @Test
    public void testInlineReturnForbidden() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "22:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "29:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnForbidden.java"), expected);
    }

    @Test
    public void testPeriodAtEnd() throws Exception {
        final String[] expected = {
            "20:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "27:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "34:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "61:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "71:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testForbiddenFragmentRelativeToPeriod() throws Exception {
        final String[] expected = {
            "24:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocForbiddenFragmentRelativeToPeriod.java"), expected);
    }

    @Test
    public void testJapanesePeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocJapanesePeriod.java"), expected);
    }

    @Test
    public void testHtmlFormatSummary() throws Exception {
        final String[] expected = {
            "23:17: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "37:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42:11: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocHtmlFormat.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = {
            "11:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoWithAnnotation() throws Exception {
        final String[] expected = {
            "11:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testForbidden() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments.java"), expected);
    }

    @Test
    public void testEmptyPeriod() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocEmptyPeriod.java"), expected);
    }

    @Test
    public void testForbidden3() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments3.java"), expected);
    }

    @Test
    public void testForbidden2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocTestForbiddenFragments2.java"), expected);
    }

    @Test
    public void testSummaryJavaDoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc1.java"), expected);
    }

    @Test
    public void testSummaryJavaDoc2() throws Exception {
        final String[] expected = {
            "16:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc2.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInheritDoc.java"), expected);
    }

    @Test
    public void testSummaryJavadocLargeJavaDoc() throws Exception {
        final String[] expected = {
            "14:4: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "28:8: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "42:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "62:8: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocLargeJavadoc.java"), expected);
    }

    @Test
    public void testForbiddenFragmentsTabFormatted() throws Exception {
        final String[] expected = {
            "16:12: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocForbiddenFragmentsTabFormatted.java"), expected);
    }
}

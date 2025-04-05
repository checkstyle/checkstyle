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
    protected String getPackageLocation() {
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
    public void testInlineCorrect() throws Exception {
        final String[] expected = {
            "112: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "20: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "25: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "43: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "48: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "64: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "80: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "94: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "114: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "127: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "132: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "137: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "143: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "148: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "151: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final String[] expected = {
            "26: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "31: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "45: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "59: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "80: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "94: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "108: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "114: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineForbidden.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "19: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "37: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
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
            "19: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "47: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "63: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "68: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "79: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "113: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "126: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "131: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "136: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "142: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "147: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "150: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect2.java"), expected);
    }

    @Test
    public void testIncorrectUsageOfSummaryTag() throws Exception {
        final String[] expected = {
            "34: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "49: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "64: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "74: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            // Until https://github.com/checkstyle/checkstyle/issues/11425
            "82: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "93: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
            getPath("InputSummaryJavadocIncorrect3.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "26: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "30: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "44: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "56: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "60: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "116: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "120: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "125: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "136: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "153: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "157: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "179: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testInlineReturn() throws Exception {
        final String[] expected = {
            "74: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn.java"), expected);
    }

    @Test
    public void testInlineReturn2() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn2.java"), expected);
    }

    @Test
    public void testInlineReturnForbidden() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "21: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "28: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturnForbidden.java"), expected);
    }

    @Test
    public void testPeriodAtEnd() throws Exception {
        final String[] expected = {
            "19: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "26: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "33: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "40: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "60: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "70: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testForbiddenFragmentRelativeToPeriod() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "36: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocHtmlFormat.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoWithAnnotation() throws Exception {
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("inputs/package-info.java"), expected);
    }

    @Test
    public void testForbidden() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
            "15: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadoc2.java"), expected);
    }

    @Test
    public void testInheritDoc() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInheritDoc.java"), expected);
    }
}

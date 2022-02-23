////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "47: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "57: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "63: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "68: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "79: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "93: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "113: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "126: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "131: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "136: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "142: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "147: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "150: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final String[] expected = {
            "26: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "32: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "38: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "44: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "49: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "54: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "64: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "86: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "99: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "114: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "120: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
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
            "23: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "41: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "46: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "62: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "67: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "78: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "112: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "125: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "130: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "135: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "141: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "146: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "149: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocIncorrect2.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "27: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "32: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "47: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "60: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "65: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "122: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "127: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "132: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "143: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "161: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "166: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "186: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testInlineReturn() throws Exception {
        final String[] expected = {
            "29: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocInlineReturn.java"), expected);
    }

    @Test
    public void testInlineReturnForbidden() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "20: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "27: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
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
        };

        verifyWithInlineConfigParser(
                getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testHtmlFormatSummary() throws Exception {
        final String[] expected = {
            "22: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "37: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
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

}

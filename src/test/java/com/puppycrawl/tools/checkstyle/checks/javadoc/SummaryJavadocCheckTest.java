////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_FIRST_SENTENCE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_JAVADOC_MISSING;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck.MSG_SUMMARY_MISSING_PERIOD;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns *|^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSummaryJavadocCorrect.java"), expected);
    }

    @Test
    public void testInlineCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSummaryJavadocInlineCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns |^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "32: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "37: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "47: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "53: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "83: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "103: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "116: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "121: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "126: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "132: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "137: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "140: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testInlineForbidden() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns |^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = {
            "18: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "33: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "39: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "45: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "51: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "57: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "63: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "74: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "79: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "89: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "103: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "122: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "128: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "141: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "147: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "154: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "169: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "175: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
        };
        verify(checkConfig, getPath("InputSummaryJavadocInlineForbidden.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("period", "_");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "10: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "28: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        verify(checkConfig, getPath("InputSummaryJavadocPeriod.java"), expected);
    }

    @Test
    public void testNoPeriod() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("period", "");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSummaryJavadocNoPeriod.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "32: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "37: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "53: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "69: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "103: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "116: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "121: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "126: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "132: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "137: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "140: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        createChecker(checkConfig);
        verify(checkConfig, getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testInlineDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "29: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "34: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "44: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "49: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "54: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "72: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "77: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "93: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "109: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "144: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "149: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "154: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "159: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "165: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "178: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "183: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "188: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "193: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "198: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "203: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "208: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "216: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "240: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "253: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
        };

        createChecker(checkConfig);
        verify(checkConfig, getPath("InputSummaryJavadocInlineDefault.java"), expected);
    }

    @Test
    public void testPeriodAtEnd() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        checkConfig.addAttribute("period", ".");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "17: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "31: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "51: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verify(checkConfig, getPath("InputSummaryJavadocPeriodAtEnd.java"), expected);
    }

    @Test
    public void testHtmlFormatSummary() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = {
            "13: " + getCheckMessage(MSG_SUMMARY_MISSING_PERIOD),
            "18: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "28: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "38: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "43: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "53: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("InputSummaryJavadocHtmlFormat.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoWithAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(SummaryJavadocCheck.class);
        final String[] expected = {
            "1: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
        };

        verify(checkConfig, getPath("inputs/package-info.java"), expected);
    }

}

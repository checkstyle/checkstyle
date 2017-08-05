////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class SummaryJavadocCheckTest extends AbstractModuleTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createModuleConfig(SummaryJavadocCheck.class);
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc";
    }

    @Test
    public void testGetRequiredTokens() {
        final SummaryJavadocCheck checkObj = new SummaryJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertArrayEquals("Default required tokens are invalid",
            expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testCorrect() throws Exception {
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns *|^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSummaryJavadocCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
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
        };
        verify(checkConfig, getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testPeriod() throws Exception {
        checkConfig.addAttribute("period", "_");
        final String[] expected = {
            "5: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "10: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verify(checkConfig, getPath("InputSummaryJavadocPeriod.java"), expected);
    }

    @Test
    public void testNoPeriod() throws Exception {
        checkConfig.addAttribute("period", "");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputSummaryJavadocNoPeriod.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
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
        };

        createChecker(checkConfig);
        verify(checkConfig, getPath("InputSummaryJavadocIncorrect.java"), expected);
    }

    @Test
    public void testPeriodAtEnd() throws Exception {
        checkConfig.addAttribute("period", ".");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "17: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "24: " + getCheckMessage(MSG_SUMMARY_JAVADOC_MISSING),
            "31: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        verify(checkConfig, getPath("InputPeriodAtEnd.java"), expected);
    }
}

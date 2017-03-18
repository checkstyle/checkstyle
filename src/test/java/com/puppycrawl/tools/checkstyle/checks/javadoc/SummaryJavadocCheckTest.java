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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class SummaryJavadocCheckTest extends BaseCheckTestSupport {
    private DefaultConfiguration checkConfig;

    @Before
    public void setUp() {
        checkConfig = createCheckConfig(SummaryJavadocCheck.class);
    }

    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "javadoc" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final SummaryJavadocCheck checkObj = new SummaryJavadocCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testCorrect() throws Exception {
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns *|^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputCorrectSummaryJavaDoc.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        checkConfig.addAttribute("forbiddenSummaryFragments",
                "^@return the *|^This method returns |^A [{]@code [a-zA-Z0-9]+[}]( is a )");
        final String[] expected = {
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "37: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "47: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "58: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "69: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "83: " + getCheckMessage(MSG_SUMMARY_JAVADOC),
            "103: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "116: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "121: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "126: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "131: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };
        verify(checkConfig, getPath("InputIncorrectSummaryJavaDoc.java"), expected);
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
            "14: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "37: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "69: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "103: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "116: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "121: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "126: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
            "131: " + getCheckMessage(MSG_SUMMARY_FIRST_SENTENCE),
        };

        createChecker(checkConfig);
        verify(checkConfig, getPath("InputIncorrectSummaryJavaDoc.java"), expected);
    }
}

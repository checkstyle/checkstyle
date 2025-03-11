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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocTagContinuationIndentationCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctagcontinuationindentation";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocTagContinuationIndentationCheck checkObj =
            new JavadocTagContinuationIndentationCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testFp() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationGuavaFalsePositive.java"),
               expected);
    }

    @Test
    public void testCheck() throws Exception {
        final String[] expected = {
            "55: " + getCheckMessage(MSG_KEY, 4),
            "117: " + getCheckMessage(MSG_KEY, 4),
            "120: " + getCheckMessage(MSG_KEY, 4),
            "211: " + getCheckMessage(MSG_KEY, 4),
            "214: " + getCheckMessage(MSG_KEY, 4),
            "229: " + getCheckMessage(MSG_KEY, 4),
            "231: " + getCheckMessage(MSG_KEY, 4),
            "293: " + getCheckMessage(MSG_KEY, 4),
            "296: " + getCheckMessage(MSG_KEY, 4),
            "298: " + getCheckMessage(MSG_KEY, 4),
            "318: " + getCheckMessage(MSG_KEY, 4),
            "330: " + getCheckMessage(MSG_KEY, 4),
            "332: " + getCheckMessage(MSG_KEY, 4),
            "350: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentation.java"),
                expected);
    }

    @Test
    public void testCheckWithOffset3() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY, 3),
            "27: " + getCheckMessage(MSG_KEY, 3),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationOffset3.java"),
                expected);
    }

    @Test
    public void testCheckWithDescription() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_KEY, 4),
            "17: " + getCheckMessage(MSG_KEY, 4),
            "18: " + getCheckMessage(MSG_KEY, 4),
            "47: " + getCheckMessage(MSG_KEY, 4),
            "49: " + getCheckMessage(MSG_KEY, 4),
            "50: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationDescription.java"),
                expected);
    }

    @Test
    public void testBlockTag() throws Exception {
        final String[] expected = {
            "21: " + getCheckMessage(MSG_KEY, 4),
            "32: " + getCheckMessage(MSG_KEY, 4),
            "42: " + getCheckMessage(MSG_KEY, 4),
            "62: " + getCheckMessage(MSG_KEY, 4),
            "64: " + getCheckMessage(MSG_KEY, 4),
            "74: " + getCheckMessage(MSG_KEY, 4),
            "75: " + getCheckMessage(MSG_KEY, 4),
            "76: " + getCheckMessage(MSG_KEY, 4),
            "86: " + getCheckMessage(MSG_KEY, 4),
            "87: " + getCheckMessage(MSG_KEY, 4),
            "88: " + getCheckMessage(MSG_KEY, 4),
            "89: " + getCheckMessage(MSG_KEY, 4),
            "90: " + getCheckMessage(MSG_KEY, 4),
            "91: " + getCheckMessage(MSG_KEY, 4),
            "92: " + getCheckMessage(MSG_KEY, 4),
            "93: " + getCheckMessage(MSG_KEY, 4),
            "94: " + getCheckMessage(MSG_KEY, 4),
            "95: " + getCheckMessage(MSG_KEY, 4),
            "96: " + getCheckMessage(MSG_KEY, 4),
            "97: " + getCheckMessage(MSG_KEY, 4),
            "98: " + getCheckMessage(MSG_KEY, 4),
            "99: " + getCheckMessage(MSG_KEY, 4),
            "104: " + getCheckMessage(MSG_KEY, 4),
            "105: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationBlockTag.java"),
                expected);
    }

    @Test
    public void testContinuationIndentation() throws Exception {
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentation1.java"),
                expected);
    }

    @Test
    public void testJavadocTagContinuationIndentationCheck1() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationCheck1.java"),
                expected);
    }

    @Test
    public void testJavadocTagContinuationBreakLine() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY, 4),
            "17: " + getCheckMessage(MSG_KEY, 4),
            "30: " + getCheckMessage(MSG_KEY, 4),
            "53: " + getCheckMessage(MSG_KEY, 4),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocTagContinuationIndentationSeeTag.java"),
                expected);
    }
}

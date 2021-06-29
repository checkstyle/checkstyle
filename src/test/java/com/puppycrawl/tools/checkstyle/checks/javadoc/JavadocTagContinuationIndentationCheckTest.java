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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testFp() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
               getPath("InputJavadocTagContinuationIndentationGuavaFalsePositive.java"),
               expected);
    }

    @Test
    public void testCheck() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
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
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentation.java"),
                expected);
    }

    @Test
    public void testCheckWithOffset3() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        checkConfig.addProperty("offset", "3");
        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY, 3),
            "27: " + getCheckMessage(MSG_KEY, 3),
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentationOffset3.java"),
                expected);
    }

    @Test
    public void testCheckWithDescription() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        final String[] expected = {
            "16: " + getCheckMessage(MSG_KEY, 4),
            "17: " + getCheckMessage(MSG_KEY, 4),
            "18: " + getCheckMessage(MSG_KEY, 4),
            "47: " + getCheckMessage(MSG_KEY, 4),
            "49: " + getCheckMessage(MSG_KEY, 4),
            "50: " + getCheckMessage(MSG_KEY, 4),
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentationDescription.java"),
                expected);
    }

    @Test
    public void testBlockTag() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        checkConfig.addProperty("offset", "4");
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
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentationBlockTag.java"),
                expected);
    }

}

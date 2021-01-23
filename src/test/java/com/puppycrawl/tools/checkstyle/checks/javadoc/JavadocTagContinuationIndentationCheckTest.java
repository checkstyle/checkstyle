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
            "47: " + getCheckMessage(MSG_KEY, 4),
            "109: " + getCheckMessage(MSG_KEY, 4),
            "112: " + getCheckMessage(MSG_KEY, 4),
            "203: " + getCheckMessage(MSG_KEY, 4),
            "206: " + getCheckMessage(MSG_KEY, 4),
            "221: " + getCheckMessage(MSG_KEY, 4),
            "223: " + getCheckMessage(MSG_KEY, 4),
            "285: " + getCheckMessage(MSG_KEY, 4),
            "288: " + getCheckMessage(MSG_KEY, 4),
            "290: " + getCheckMessage(MSG_KEY, 4),
            "310: " + getCheckMessage(MSG_KEY, 4),
            "322: " + getCheckMessage(MSG_KEY, 4),
            "324: " + getCheckMessage(MSG_KEY, 4),
            "342: " + getCheckMessage(MSG_KEY, 4),
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentation.java"),
                expected);
    }

    @Test
    public void testCheckWithOffset3() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        checkConfig.addAttribute("offset", "3");
        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY, 3),
            "19: " + getCheckMessage(MSG_KEY, 3),
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentationOffset3.java"),
                expected);
    }

    @Test
    public void testCheckWithDescription() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocTagContinuationIndentationCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY, 4),
            "10: " + getCheckMessage(MSG_KEY, 4),
            "11: " + getCheckMessage(MSG_KEY, 4),
            "40: " + getCheckMessage(MSG_KEY, 4),
            "42: " + getCheckMessage(MSG_KEY, 4),
            "43: " + getCheckMessage(MSG_KEY, 4),
        };
        verify(checkConfig, getPath("InputJavadocTagContinuationIndentationDescription.java"),
                expected);
    }

}

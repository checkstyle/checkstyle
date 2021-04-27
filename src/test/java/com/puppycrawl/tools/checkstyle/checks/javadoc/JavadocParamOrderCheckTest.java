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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrderCheck.MSG_JAVADOC_TAG_ORDER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocParamOrderCheck checkObj = new JavadocParamOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN};
        assertArrayEquals(expected, checkObj.getAcceptableTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocParamOrderCheck checkObj = new JavadocParamOrderCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocParamOrderCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocParamOrderCheck.class);
        final String[] expected = {
            "15: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "p2"),
            "19: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<T>"),
            "33: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<i>"),
            "45: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<E>"),
            "49: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "CCC"),
            "54: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "e"),
            "56: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "str"),
            "67: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "str"),
            "69: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "i"),
            "80: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<T"),
            "95: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<>"),
            "109: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<i"),
        };
        verify(config, getPath("InputJavadocParamOrderIncorrect.java"), expected);
    }

    @Test
    public void testParameterRecords() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrderCheck.class);
        final String[] expected = {
            "17: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<address>"),
            "45: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<brand"),
            "70: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<TT>"),
        };
        verify(checkConfig, getNonCompilablePath("InputJavadocParamOrderRecords.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocParamOrderNoJavadoc.java"), expected);
    }
}

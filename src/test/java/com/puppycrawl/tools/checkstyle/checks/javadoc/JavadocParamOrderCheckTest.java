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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrder.MSG_JAVADOC_TAG_ORDER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocParamOrder checkObj = new JavadocParamOrder();

        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testGetRequiredTokens() {
        final JavadocParamOrder checkObj = new JavadocParamOrder();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens(),
                "JavadocParamOrder#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrder.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputFile1.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration config = createModuleConfig(JavadocParamOrder.class);
        final String[] expected = {
            "15:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "19:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "33:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "45:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "47:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "49:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "54:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "56:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "67:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
            "69:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER,  "@param"),
        };
        verify(config, getPath("InputFile2.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrder.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputFile3.java"), expected);
    }
}

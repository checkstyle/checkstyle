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
    public void testAcceptableToken() {
        final JavadocParamOrderCheck check =
                new JavadocParamOrderCheck();
        assertArrayEquals(new int[] {TokenTypes.METHOD_DEF}, check.getAcceptableTokens(),
                "Acceptable token should be METHOD_DEF");
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
            "15:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "p2"),
            "19:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<T>"),
            "33:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<i>"),
            "45:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<E>"),
            "49:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "CCC"),
            "54:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "e"),
            "56:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "str"),
            "67:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "str"),
            "69:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "i"),
            "80:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<T"),
            "95:8: " + getCheckMessage(MSG_JAVADOC_TAG_ORDER, "<>"),
        };
        verify(config, getPath("InputJavadocParamOrderIncorrect.java"), expected);
    }

    @Test
    public void testNoJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(JavadocParamOrderCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocParamOrderNoJavadoc.java"), expected);
    }
}

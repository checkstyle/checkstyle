////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingWhitespaceAfterAsteriskCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMissingWhitespaceAfterAsteriskCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc"
                + "/javadocmissingwhitespaceafterasterisk";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocMissingWhitespaceAfterAsteriskCheck checkObj =
                new JavadocMissingWhitespaceAfterAsteriskCheck();
        final int[] expected = {
            JavadocTokenTypes.JAVADOC,
            JavadocTokenTypes.LEADING_ASTERISK,
        };
        assertArrayEquals(expected, checkObj.getAcceptableJavadocTokens(),
                "Default tokens are invalid");
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final JavadocMissingWhitespaceAfterAsteriskCheck checkObj =
                new JavadocMissingWhitespaceAfterAsteriskCheck();
        final int[] expected = {
            JavadocTokenTypes.JAVADOC,
            JavadocTokenTypes.LEADING_ASTERISK,
        };
        assertArrayEquals(expected, checkObj.getRequiredJavadocTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testValid() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocMissingWhitespaceAfterAsteriskCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputJavadocMissingWhitespaceAfterAsteriskValid.java"), expected);
    }

    @Test
    public void testValidWithTabCharacter() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocMissingWhitespaceAfterAsteriskCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputJavadocMissingWhitespaceAfterAsteriskValidWithTab.java"), expected);
    }

    @Test
    public void testInvalid() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocMissingWhitespaceAfterAsteriskCheck.class);
        final String[] expected = {
            "5:4: " + getCheckMessage(MSG_KEY),
            "11:7: " + getCheckMessage(MSG_KEY),
            "16:11: " + getCheckMessage(MSG_KEY),
            "23:11: " + getCheckMessage(MSG_KEY),
            "27:13: " + getCheckMessage(MSG_KEY),
            "33:7: " + getCheckMessage(MSG_KEY),
            "38:7: " + getCheckMessage(MSG_KEY),
            "43:7: " + getCheckMessage(MSG_KEY),
            "48:7: " + getCheckMessage(MSG_KEY),
            "50:7: " + getCheckMessage(MSG_KEY),
            "54:8: " + getCheckMessage(MSG_KEY),
            "57:8: " + getCheckMessage(MSG_KEY),
            "60:10: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
                getPath("InputJavadocMissingWhitespaceAfterAsteriskInvalid.java"), expected);
    }

}

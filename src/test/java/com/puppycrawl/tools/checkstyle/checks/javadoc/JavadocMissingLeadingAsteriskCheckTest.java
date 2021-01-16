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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck.MSG_MISSING_ASTERISK;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMissingLeadingAsteriskCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmissingleadingasterisk";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocMissingLeadingAsteriskCheck checkObj =
            new JavadocMissingLeadingAsteriskCheck();
        final int[] expected = {
            JavadocTokenTypes.NEWLINE,
        };
        assertArrayEquals(expected, checkObj.getAcceptableJavadocTokens(),
            "Default acceptable tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocMissingLeadingAsteriskCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputJavadocMissingLeadingAsteriskCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(JavadocMissingLeadingAsteriskCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "14: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "21: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "27: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "31: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "32: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "37: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "42: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "49: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "58: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "59: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "60: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verify(checkConfig, getPath("InputJavadocMissingLeadingAsteriskIncorrect.java"), expected);
    }

}

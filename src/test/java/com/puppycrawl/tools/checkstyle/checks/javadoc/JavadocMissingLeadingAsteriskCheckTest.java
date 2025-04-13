///
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
///

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck.MSG_MISSING_ASTERISK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableJavadocTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocMissingLeadingAsteriskCorrect.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "13: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "18: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "25: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "31: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "35: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "36: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "41: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "46: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "53: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "62: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "63: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "64: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMissingLeadingAsteriskIncorrect.java"), expected);
    }

}

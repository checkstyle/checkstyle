///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck.MSG_MISSING_ASTERISK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocMissingLeadingAsteriskCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmissingleadingasterisk";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocMissingLeadingAsteriskCheck checkObj =
            new JavadocMissingLeadingAsteriskCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.NEWLINE,
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
            "12: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "17: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "24: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "30: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "34: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "35: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "40: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "45: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "52: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "61: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "62: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "63: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocMissingLeadingAsteriskIncorrect.java"), expected);
    }

}

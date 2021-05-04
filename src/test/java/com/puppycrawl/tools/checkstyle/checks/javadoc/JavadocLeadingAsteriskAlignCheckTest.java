///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_DUPLICATE_ASTERISK;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_WRONG_ALIGNMENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class JavadocLeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocleadingasteriskalign";
    }

    @Test
    public void testCorrectJavadoc() throws Exception{
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignCorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIncorrectJavadoc() throws Exception {
        final String ALIGN = MSG_WRONG_ALIGNMENT;
        final String DUPLICATE = MSG_DUPLICATE_ASTERISK;

        final String[] expected = {
            "12: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 1),
            "17: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "23: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "30: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "35: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "40: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "46: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "53: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "59: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "64: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "65: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 3),
            "71: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 5),
            "76: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 5),
            "82: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, ALIGN, 5),
            "95: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
            "102: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
            "107: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
            "114: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
            "119: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
            "126: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, DUPLICATE),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIncorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }
}

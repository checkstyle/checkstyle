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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocLeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocleadingasteriskalign";
    }

    @Test
    public void testCorrectJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignCorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIncorrectJavadoc() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 2),
            "16:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "21:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 3, 4),
            "27:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "31:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 3, 4),
            "35:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "40:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 4),
            "46:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "51:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 4),
            "55:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "56:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "60:9: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 9, 6),
            "73:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 6),
            "78:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIncorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "49:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "59:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 6),
            "60:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
            "69:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
            "70:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignTabs.java");
        verifyWithInlineConfigParser(filePath, expected);
    }
}

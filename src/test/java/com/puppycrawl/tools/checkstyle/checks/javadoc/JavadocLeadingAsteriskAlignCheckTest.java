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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocLeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
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
            "17:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "24:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 3, 4),
            "30:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "36:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 3, 4),
            "41:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "47:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 4),
            "53:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "59:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 4),
            "66:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 4),
            "67:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "68:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "73:9: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 9, 6),
            "79:8: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 8, 6),
            "89:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 6),
            "95:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
            "96:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIncorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "49:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 4),
            "62:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 6),
            "63:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
            "74:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 7, 6),
            "75:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignTabs.java");
        verifyWithInlineConfigParser(filePath, expected);
    }
}

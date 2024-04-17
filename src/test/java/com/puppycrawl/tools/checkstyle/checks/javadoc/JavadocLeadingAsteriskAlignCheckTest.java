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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_WRONG_ALIGNMENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocLeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocleadingasteriskalign";
    }

    @Test
    public void testMixedTabsAndSpaces() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignMixedTabsAndSpaces.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIncorrectJavadoc() throws Exception {
        final String wrongAlignment = MSG_WRONG_ALIGNMENT;

        final String[] expected = {
            "12:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 2),
            "17:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "26:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "33:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "38:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "43:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "49:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "56:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "62:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "67:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "68:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 4),
            "74:9: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 6),
            "88:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 6),
            "94:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, wrongAlignment, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIncorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }
}

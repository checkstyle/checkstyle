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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.LeadingAsteriskAlignCheck.MSG_DUPLICATE_ASTERISK;
import static com.puppycrawl.tools.checkstyle.checks.LeadingAsteriskAlignCheck.MSG_WRONG_ALIGNMENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class LeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/leadingasteriskalign";
    }

    @Test
    public void testJavadocRight() throws Exception {
        final String[] expected = {
            "7: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "13: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "29: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "46: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "51: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_DUPLICATE_ASTERISK, 51),
            "58: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "64: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
        };

        final String filePath = getPath("InputLeadingAsteriskAlignJavadocRight.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testJavadocLeft() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "24: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "30: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "39: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "51: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_DUPLICATE_ASTERISK, 51),
            "58: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "64: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
        };

        final String filePath = getPath("InputLeadingAsteriskAlignJavadocLeft.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testCommentRight() throws Exception {
        final String[] expected = {
            "7: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "13: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 1),
            "23: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "30: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 6),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "50: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_DUPLICATE_ASTERISK, 50),
            "57: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
            "63: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 5),
        };

        final String filePath = getPath("InputLeadingAsteriskAlignCommentRight.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testCommentLeft() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "24: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "38: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "46: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 0),
            "51: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_DUPLICATE_ASTERISK, 51),
            "58: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
            "64: " + getCheckMessage(LeadingAsteriskAlignCheck.class, MSG_WRONG_ALIGNMENT, 4),
        };

        final String filePath = getPath("InputLeadingAsteriskAlignCommentLeft.java");
        verifyWithInlineConfigParser(filePath, expected);
    }
}

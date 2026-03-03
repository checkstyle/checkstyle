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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyAloneOrEmptyCheck.MSG_KEY_LINE_ALONE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class RightCurlyAloneOrEmptyCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/rightcurlyaloneorempty";
    }

    @Test
    public void testAnnotationsAndEnums() throws Exception {
        final String[] expected = {
            "14:29: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 29),
            "17:21: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 21),
            "24:33: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 33),
            "35:20: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 20),
            "39:18: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 18),
            "41:25: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 25),
            "44:36: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 36),
            "45:31: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 31),
            "55:32: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 32),
            "69:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
            "71:24: " + getCheckMessage(MSG_KEY_LINE_ALONE, "}", 24),
        };
        verifyWithInlineConfigParser(
                getPath("InputRightCurlyAloneOrEmptyNoViolations.java"), expected);
    }
}

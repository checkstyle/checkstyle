///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NestedIfDepthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/nestedifdepth";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "26:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "52:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "77:17: " + getCheckMessage(MSG_KEY, 2, 1),
            "102:18: " + getCheckMessage(MSG_KEY, 2, 1),
            "107:18: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getPath("InputNestedIfDepthDefault.java"), expected);
    }

    @Test
    public void testCustomizedDepth() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputNestedIfDepthMax.java"), expected);
    }

    @Test
    public void testInsideCaseBody() throws Exception {

        final String[] expected = {
            "19:25: " + getCheckMessage(MSG_KEY, 2, 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNestedIfDepthInsideCaseBody.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final NestedIfDepthCheck check = new NestedIfDepthCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

}

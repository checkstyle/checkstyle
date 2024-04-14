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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ConstructorsDeclarationGroupingCheckTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/constructorsdeclarationgrouping";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, 19),
            "27:5: " + getCheckMessage(MSG_KEY, 23),
            "40:9: " + getCheckMessage(MSG_KEY, 36),
            "49:13: " + getCheckMessage(MSG_KEY, 43),
            "52:9: " + getCheckMessage(MSG_KEY, 40),
            "55:5: " + getCheckMessage(MSG_KEY, 27),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGrouping.java"), expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingRecords() throws Exception {

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_KEY, 12),
            "34:9: " + getCheckMessage(MSG_KEY, 30),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final ConstructorsDeclarationGroupingCheck check =
                new ConstructorsDeclarationGroupingCheck();
        assertWithMessage("Acceptable tokens should be CTOR_DEF and COMPACT_CTOR_DEF")
                .that(check.getAcceptableTokens())
                .isEqualTo(new int[] {TokenTypes.CTOR_DEF, TokenTypes.COMPACT_CTOR_DEF});
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

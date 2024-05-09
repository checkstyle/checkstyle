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

public class ConstructorsDeclarationGroupingCheckTest extends AbstractModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/constructorsdeclarationgrouping";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY, 17),
            "27:5: " + getCheckMessage(MSG_KEY, 17),
            "43:9: " + getCheckMessage(MSG_KEY, 35),
            "52:13: " + getCheckMessage(MSG_KEY, 46),
            "55:9: " + getCheckMessage(MSG_KEY, 35),
            "58:5: " + getCheckMessage(MSG_KEY, 17),
            "60:5: " + getCheckMessage(MSG_KEY, 17),
            "78:7: " + getCheckMessage(MSG_KEY, 72),
            "82:7: " + getCheckMessage(MSG_KEY, 72),
            "84:7: " + getCheckMessage(MSG_KEY, 72),
            "87:5: " + getCheckMessage(MSG_KEY, 17),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstructorsDeclarationGrouping.java"), expected);
    }

    @Test
    public void testConstructorsDeclarationGroupingRecords() throws Exception {

        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_KEY, 12),
            "22:9: " + getCheckMessage(MSG_KEY, 12),
            "26:9: " + getCheckMessage(MSG_KEY, 12),
            "42:9: " + getCheckMessage(MSG_KEY, 34),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final ConstructorsDeclarationGroupingCheck check =
                new ConstructorsDeclarationGroupingCheck();
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

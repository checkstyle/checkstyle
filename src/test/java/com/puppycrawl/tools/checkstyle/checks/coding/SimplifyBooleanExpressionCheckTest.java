///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class SimplifyBooleanExpressionCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/simplifybooleanexpression";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "22:18: " + getCheckMessage(MSG_KEY),
            "43:36: " + getCheckMessage(MSG_KEY),
            "44:36: " + getCheckMessage(MSG_KEY),
            "45:16: " + getCheckMessage(MSG_KEY),
            "45:32: " + getCheckMessage(MSG_KEY),
            "95:27: " + getCheckMessage(MSG_KEY),
            "96:24: " + getCheckMessage(MSG_KEY),
            "98:27: " + getCheckMessage(MSG_KEY),
            "104:23: " + getCheckMessage(MSG_KEY),
            "106:17: " + getCheckMessage(MSG_KEY),
            "109:21: " + getCheckMessage(MSG_KEY),
            "110:23: " + getCheckMessage(MSG_KEY),
            "111:20: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputSimplifyBooleanExpression.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final SimplifyBooleanExpressionCheck check = new SimplifyBooleanExpressionCheck();
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

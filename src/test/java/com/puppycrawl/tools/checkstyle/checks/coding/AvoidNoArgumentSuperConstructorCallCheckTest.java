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
import static com.puppycrawl.tools.checkstyle.checks.coding.AvoidNoArgumentSuperConstructorCallCheck.MSG_CTOR;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidNoArgumentSuperConstructorCallCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/avoidnoargumentsuperconstructorcall";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_CTOR),
            "18:9: " + getCheckMessage(MSG_CTOR),
            "23:9: " + getCheckMessage(MSG_CTOR),
        };

        verifyWithInlineConfigParser(
                getPath("InputAvoidNoArgumentSuperConstructorCall.java"),
                expected);
    }

    @Test
    public void testTokens() {
        final AvoidNoArgumentSuperConstructorCallCheck check =
            new AvoidNoArgumentSuperConstructorCallCheck();
        final int[] expected = {
            TokenTypes.SUPER_CTOR_CALL,
        };
        assertWithMessage("Acceptable required tokens are invalid")
                .that(check.getAcceptableTokens())
                .isEqualTo(expected);
        assertWithMessage("Default required tokens are invalid")
                .that(check.getDefaultTokens())
                .isEqualTo(expected);
        assertWithMessage("Required required tokens are invalid")
                .that(check.getRequiredTokens())
                .isEqualTo(expected);
    }
}

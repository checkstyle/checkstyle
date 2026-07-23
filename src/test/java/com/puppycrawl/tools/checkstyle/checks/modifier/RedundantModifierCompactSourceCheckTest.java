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

package com.puppycrawl.tools.checkstyle.checks.modifier;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCompactSourceCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RedundantModifierCompactSourceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modifier/"
                + "redundantmodifiercompactsource";
    }

    @Test
    public void testDirectMethods() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY, "final"),
            "21:1: " + getCheckMessage(MSG_KEY, "strictfp"),
            "23:8: " + getCheckMessage(MSG_KEY, "final"),
            "23:14: " + getCheckMessage(MSG_KEY, "strictfp"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierCompactSourceMethods.java"),
                expected);
    }

    @Test
    public void testOrdinaryCompilationUnit() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputRedundantModifierCompactSourceOrdinary.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testTokenContract() {
        final RedundantModifierCompactSourceCheck check =
                new RedundantModifierCompactSourceCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("default tokens")
            .that(check.getDefaultTokens()).isEqualTo(expected);
        assertWithMessage("acceptable tokens")
            .that(check.getAcceptableTokens()).isEqualTo(expected);
        assertWithMessage("required tokens")
            .that(check.getRequiredTokens()).isEqualTo(expected);
    }

}

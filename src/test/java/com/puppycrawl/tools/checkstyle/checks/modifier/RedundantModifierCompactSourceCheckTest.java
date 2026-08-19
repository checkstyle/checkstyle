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
    public void testDirectMembers() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "public"),
            "15:1: " + getCheckMessage(MSG_KEY, "protected"),
            "16:1: " + getCheckMessage(MSG_KEY, "private"),
            "17:1: " + getCheckMessage(MSG_KEY, "static"),
            "18:1: " + getCheckMessage(MSG_KEY, "public"),
            "18:8: " + getCheckMessage(MSG_KEY, "static"),
            "22:1: " + getCheckMessage(MSG_KEY, "private"),
            "22:9: " + getCheckMessage(MSG_KEY, "static"),
            "26:1: " + getCheckMessage(MSG_KEY, "static"),
            "27:1: " + getCheckMessage(MSG_KEY, "public"),
            "34:1: " + getCheckMessage(MSG_KEY, "public"),
            "35:1: " + getCheckMessage(MSG_KEY, "protected"),
            "36:1: " + getCheckMessage(MSG_KEY, "private"),
            "37:1: " + getCheckMessage(MSG_KEY, "static"),
            "38:1: " + getCheckMessage(MSG_KEY, "final"),
            "39:1: " + getCheckMessage(MSG_KEY, "strictfp"),
            "40:1: " + getCheckMessage(MSG_KEY, "public"),
            "40:8: " + getCheckMessage(MSG_KEY, "static"),
            "44:1: " + getCheckMessage(MSG_KEY, "private"),
            "44:9: " + getCheckMessage(MSG_KEY, "static"),
            "48:1: " + getCheckMessage(MSG_KEY, "public"),
            "48:8: " + getCheckMessage(MSG_KEY, "final"),
            "48:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "55:1: " + getCheckMessage(MSG_KEY, "final"),
            "62:1: " + getCheckMessage(MSG_KEY, "public"),
            "69:1: " + getCheckMessage(MSG_KEY, "static"),
            "69:8: " + getCheckMessage(MSG_KEY, "final"),
            "74:1: " + getCheckMessage(MSG_KEY, "private"),
            "74:9: " + getCheckMessage(MSG_KEY, "final"),
            "79:1: " + getCheckMessage(MSG_KEY, "private"),
            "79:9: " + getCheckMessage(MSG_KEY, "static"),
            "79:16: " + getCheckMessage(MSG_KEY, "final"),
            "84:1: " + getCheckMessage(MSG_KEY, "final"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantModifierCompactSourceMethods.java"),
                expected);
    }

    @Test
    public void testExcludedDeclarations() throws Exception {
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputRedundantModifierCompactSourceExcludedDeclarations.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testInvalidSafeVarargsOnFixedArityMethod() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_KEY, "final"),
            "15:1: " + getCheckMessage(MSG_KEY, "public"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "InputRedundantModifierCompactSourceInvalidSafeVarargs.java"),
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
        final int[] expected = {TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF};
        assertWithMessage("default tokens")
            .that(check.getDefaultTokens()).isEqualTo(expected);
        assertWithMessage("acceptable tokens")
            .that(check.getAcceptableTokens()).isEqualTo(expected);
        assertWithMessage("required tokens")
            .that(check.getRequiredTokens()).isEqualTo(expected);
    }

}

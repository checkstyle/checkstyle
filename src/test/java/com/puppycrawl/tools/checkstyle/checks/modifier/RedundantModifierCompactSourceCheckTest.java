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
            "18:1: " + getCheckMessage(MSG_KEY, "public"),
            "19:1: " + getCheckMessage(MSG_KEY, "protected"),
            "20:1: " + getCheckMessage(MSG_KEY, "private"),
            "21:1: " + getCheckMessage(MSG_KEY, "static"),
            "22:1: " + getCheckMessage(MSG_KEY, "final"),
            "23:1: " + getCheckMessage(MSG_KEY, "strictfp"),
            "24:1: " + getCheckMessage(MSG_KEY, "public"),
            "24:8: " + getCheckMessage(MSG_KEY, "static"),
            "28:1: " + getCheckMessage(MSG_KEY, "private"),
            "28:9: " + getCheckMessage(MSG_KEY, "static"),
            "32:1: " + getCheckMessage(MSG_KEY, "public"),
            "32:8: " + getCheckMessage(MSG_KEY, "final"),
            "32:14: " + getCheckMessage(MSG_KEY, "strictfp"),
            "39:1: " + getCheckMessage(MSG_KEY, "final"),
            "46:1: " + getCheckMessage(MSG_KEY, "public"),
            "49:1: " + getCheckMessage(MSG_KEY, "static"),
            "52:1: " + getCheckMessage(MSG_KEY, "private"),
            "55:1: " + getCheckMessage(MSG_KEY, "static"),
            "55:8: " + getCheckMessage(MSG_KEY, "final"),
            "60:1: " + getCheckMessage(MSG_KEY, "private"),
            "60:9: " + getCheckMessage(MSG_KEY, "final"),
            "65:1: " + getCheckMessage(MSG_KEY, "private"),
            "65:9: " + getCheckMessage(MSG_KEY, "static"),
            "65:16: " + getCheckMessage(MSG_KEY, "final"),
            "70:1: " + getCheckMessage(MSG_KEY, "final"),
            "73:1: " + getCheckMessage(MSG_KEY, "public"),
            "74:1: " + getCheckMessage(MSG_KEY, "protected"),
            "75:1: " + getCheckMessage(MSG_KEY, "private"),
            "76:1: " + getCheckMessage(MSG_KEY, "static"),
            "77:1: " + getCheckMessage(MSG_KEY, "public"),
            "77:8: " + getCheckMessage(MSG_KEY, "static"),
            "81:1: " + getCheckMessage(MSG_KEY, "private"),
            "81:9: " + getCheckMessage(MSG_KEY, "static"),
            "85:1: " + getCheckMessage(MSG_KEY, "static"),
            "86:1: " + getCheckMessage(MSG_KEY, "public"),
            "87:1: " + getCheckMessage(MSG_KEY, "public"),
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

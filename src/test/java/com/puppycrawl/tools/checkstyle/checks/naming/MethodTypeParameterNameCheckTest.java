///
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
///

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MethodTypeParameterNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/methodtypeparametername";
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodTypeParameterNameCheck methodTypeParameterNameCheck =
            new MethodTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};

        assertWithMessage("Default acceptable tokens are invalid")
            .that(methodTypeParameterNameCheck.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodTypeParameterNameCheck checkObj =
            new MethodTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testMethodDefault()
            throws Exception {

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_INVALID_PATTERN, "TT", pattern),
            "16:6: " + getCheckMessage(MSG_INVALID_PATTERN, "e_e", pattern),
            "26:6: " + getCheckMessage(MSG_INVALID_PATTERN, "Tfo$o2T", pattern),
            "30:6: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "35:10: " + getCheckMessage(MSG_INVALID_PATTERN, "_fo", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodTypeParameterName.java"), expected);
    }

    @Test
    public void testMethodFooName()
            throws Exception {

        final String pattern = "^foo$";

        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_INVALID_PATTERN, "TT", pattern),
            "16:6: " + getCheckMessage(MSG_INVALID_PATTERN, "e_e", pattern),
            "26:6: " + getCheckMessage(MSG_INVALID_PATTERN, "Tfo$o2T", pattern),
            "35:10: " + getCheckMessage(MSG_INVALID_PATTERN, "_fo", pattern),
            "42:6: " + getCheckMessage(MSG_INVALID_PATTERN, "E", pattern),
            "44:14: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMethodTypeParameterName1.java"), expected);
    }

}

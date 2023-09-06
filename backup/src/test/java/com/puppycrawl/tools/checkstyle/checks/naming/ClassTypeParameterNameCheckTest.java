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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassTypeParameterNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/classtypeparametername";
    }

    @Test
    public void testGetClassRequiredTokens() {
        final ClassTypeParameterNameCheck checkObj =
            new ClassTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testClassDefault()
            throws Exception {

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "12:42: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "20:14: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "34:24: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
            throws Exception {

        final String pattern = "^foo$";

        final String[] expected = {
            "12:43: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "40:19: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputClassTypeParameterName1.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ClassTypeParameterNameCheck typeParameterNameCheckObj =
            new ClassTypeParameterNameCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.TYPE_PARAMETER,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

}

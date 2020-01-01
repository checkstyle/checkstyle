////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LambdaParameterNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/lambdaparametername";
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.LAMBDA,
        };

        final LambdaParameterNameCheck check = new LambdaParameterNameCheck();
        final int[] requiredTokens = check.getRequiredTokens();
        assertArrayEquals(expected, requiredTokens, "Invalid required tokens");
    }

    @Test
    public void testAcceptableTokens() {
        final int[] expected = {
            TokenTypes.LAMBDA,
        };

        final LambdaParameterNameCheck check = new LambdaParameterNameCheck();
        final int[] acceptableTokens = check.getAcceptableTokens();
        assertArrayEquals(expected, acceptableTokens, "Invalid acceptable tokens");
    }

    @Test
    public void testParametersInLambda() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(LambdaParameterNameCheck.class);

        checkConfig.addAttribute("format", "^(id)|([a-z][a-z0-9][a-zA-Z0-9]+)$");

        final String pattern = "^(id)|([a-z][a-z0-9][a-zA-Z0-9]+)$";

        final String[] expected = {
            "8:68: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
            "10:66: " + getCheckMessage(MSG_INVALID_PATTERN, "st", pattern),
            "12:65: " + getCheckMessage(MSG_INVALID_PATTERN, "s1", pattern),
            "12:69: " + getCheckMessage(MSG_INVALID_PATTERN, "s2", pattern),
            "14:80: " + getCheckMessage(MSG_INVALID_PATTERN, "s", pattern),
        };
        verify(checkConfig, getPath("InputLambdaParameterName.java"), expected);
    }

}

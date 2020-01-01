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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInTryWithResourcesCheck.MSG_SEMI;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnnecessarySemicolonInTryWithResourcesCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/"
            + "unnecessarysemicolonintrywithresources";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessarySemicolonInTryWithResourcesCheck.class);

        final String[] expected = {
            "13:42: " + getCheckMessage(MSG_SEMI),
            "14:72: " + getCheckMessage(MSG_SEMI),
        };

        verify(checkConfig, getPath("InputUnnecessarySemicolonInTryWithResourcesDefault.java"),
            expected);
    }

    @Test
    public void testNoBraceAfterAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessarySemicolonInTryWithResourcesCheck.class);
        checkConfig.addAttribute("allowWhenNoBraceAfterSemicolon", "false");
        final String[] expected = {
            "13:42: " + getCheckMessage(MSG_SEMI),
            "16:13: " + getCheckMessage(MSG_SEMI),
            "19:36: " + getCheckMessage(MSG_SEMI),
        };

        verify(checkConfig,
            getPath("InputUnnecessarySemicolonInTryWithResourcesNoBraceAfterAllowed.java"),
            expected);
    }

    @Test
    public void testTokensAreCorrect() {
        final UnnecessarySemicolonInTryWithResourcesCheck check =
            new UnnecessarySemicolonInTryWithResourcesCheck();
        final int[] expected = {
            TokenTypes.RESOURCE_SPECIFICATION,
        };
        assertArrayEquals(expected, check.getAcceptableTokens(),
                "Acceptable required tokens are invalid");
        assertArrayEquals(expected, check.getDefaultTokens(),
                "Default required tokens are invalid");
        assertArrayEquals(expected, check.getRequiredTokens(),
                "Required required tokens are invalid");
    }
}

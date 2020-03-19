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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonAfterOuterTypeDeclarationCheck.MSG_SEMI;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnnecessarySemicolonAfterOuterTypeDeclarationCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/"
            + "unnecessarysemicolonafteroutertypedeclaration";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(UnnecessarySemicolonAfterOuterTypeDeclarationCheck.class);

        final String[] expected = {
            "24:2: " + getCheckMessage(MSG_SEMI),
            "28:2: " + getCheckMessage(MSG_SEMI),
            "32:2: " + getCheckMessage(MSG_SEMI),
            "36:2: " + getCheckMessage(MSG_SEMI),
        };

        verify(checkConfig, getPath("InputUnnecessarySemicolonAfterOuterTypeDeclaration.java"),
            expected);
    }

    @Test
    public void testTokens() {
        final UnnecessarySemicolonAfterOuterTypeDeclarationCheck check =
            new UnnecessarySemicolonAfterOuterTypeDeclarationCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
        };
        assertArrayEquals(expected, check.getAcceptableTokens(),
                "Acceptable required tokens are invalid");
        assertArrayEquals(expected, check.getDefaultTokens(),
                "Default required tokens are invalid");
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, check.getRequiredTokens(),
                "Required required tokens are invalid");
    }
}

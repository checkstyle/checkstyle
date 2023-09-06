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

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.InterfaceIsTypeCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InterfaceIsTypeCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/interfaceistype";
    }

    @Test
    public void testDefault()
            throws Exception {
        final String[] expected = {
            "28:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceIsType.java"), expected);
    }

    @Test
    public void testAllowMarker()
            throws Exception {
        final String[] expected = {
            "23:5: " + getCheckMessage(MSG_KEY),
            "28:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputInterfaceIsTypeAllowMarker.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InterfaceIsTypeCheck obj = new InterfaceIsTypeCheck();
        final int[] expected = {TokenTypes.INTERFACE_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final InterfaceIsTypeCheck obj = new InterfaceIsTypeCheck();
        final int[] expected = {TokenTypes.INTERFACE_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(obj.getRequiredTokens())
            .isEqualTo(expected);
    }

}

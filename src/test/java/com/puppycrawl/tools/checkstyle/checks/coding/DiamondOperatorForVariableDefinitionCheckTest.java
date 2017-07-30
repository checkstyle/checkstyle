////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.DiamondOperatorForVariableDefinitionCheck.MSG_KEY;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class DiamondOperatorForVariableDefinitionCheckTest extends AbstractModuleTestSupport {

    private final DefaultConfiguration checkConfig =
        createModuleConfig(DiamondOperatorForVariableDefinitionCheck.class);

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/diamondoperatorforvariabledefinition";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "19:47: " + getCheckMessage(MSG_KEY),
            "21:13: " + getCheckMessage(MSG_KEY),
            "22:39: " + getCheckMessage(MSG_KEY),
            "23:27: " + getCheckMessage(MSG_KEY),
            "53:26: " + getCheckMessage(MSG_KEY),
            "54:62: " + getCheckMessage(MSG_KEY),
            "71:35: " + getCheckMessage(MSG_KEY),
            "81:35: " + getCheckMessage(MSG_KEY),
            "84:57: " + getCheckMessage(MSG_KEY),
            "85:57: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig,
            getPath("InputDiamondOperatorForVariableDefinition.java"), expected);
    }

}

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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.NumericalCharacterCaseCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class NumericalCharacterCaseCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/numericalcharactercase";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:14: " + getCheckMessage(MSG_KEY),
            "15:14: " + getCheckMessage(MSG_KEY),
            "18:16: " + getCheckMessage(MSG_KEY),
            "21:19: " + getCheckMessage(MSG_KEY),
            "24:16: " + getCheckMessage(MSG_KEY),
            "27:17: " + getCheckMessage(MSG_KEY),
            "30:16: " + getCheckMessage(MSG_KEY),
            "33:17: " + getCheckMessage(MSG_KEY),
            "34:17: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }
}

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

import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_INFIX;
import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_PREFIX;
import static com.puppycrawl.tools.checkstyle.checks.NumericalPrefixesInfixesSuffixesCharacterCaseCheck.MSG_SUFFIX;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class NumericalPrefixesInfixesSuffixesCharacterCaseCheckExamplesTest
    extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return
            "com/puppycrawl/tools/checkstyle/checks/numericalprefixesinfixessuffixescharactercase";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "13:14: " + getCheckMessage(MSG_PREFIX),
            "16:14: " + getCheckMessage(MSG_PREFIX),
            "19:16: " + getCheckMessage(MSG_INFIX),
            "22:19: " + getCheckMessage(MSG_INFIX),
            "25:16: " + getCheckMessage(MSG_SUFFIX),
            "28:17: " + getCheckMessage(MSG_SUFFIX),
            "31:16: " + getCheckMessage(MSG_INFIX),
            "34:17: " + getCheckMessage(MSG_INFIX),
            "35:17: " + getCheckMessage(MSG_SUFFIX),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }
}

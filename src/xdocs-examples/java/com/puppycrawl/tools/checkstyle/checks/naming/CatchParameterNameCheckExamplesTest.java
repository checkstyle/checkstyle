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

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck.MSG_INVALID_PATTERN;

public class CatchParameterNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/catchparametername";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
                "21:40: " + getCheckMessage(MSG_INVALID_PATTERN, "e123", "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$"),
                "24:35: " + getCheckMessage(MSG_INVALID_PATTERN, "ab", "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$"),
                "28:35: " + getCheckMessage(MSG_INVALID_PATTERN, "aBC", "^(e|t|ex|[a-z][a-z][a-zA-Z]+)$")
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
                "28:24: " + getCheckMessage(MSG_INVALID_PATTERN, "FourthException", "^[a-z][a-zA-Z0-9]+$"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

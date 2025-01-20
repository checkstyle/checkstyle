///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class IllegalIdentifierNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/illegalidentifiername";
    }

    @Test
    public void testExample1() throws Exception {
        final String format = "^(?!var$|.*\\$).+";

        final String[] expected = {
            "17:11: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "21:10: " + getCheckMessage(MSG_INVALID_PATTERN, "test$stuff", format),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String format =
            "(?i)^(?!(when|record|yield|var|permits|sealed|open|transitive|_)$|(.*\\$)).+$";

        final String[] expected = {
            "17:11: " + getCheckMessage(MSG_INVALID_PATTERN, "var", format),
            "18:7: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "19:10: " + getCheckMessage(MSG_INVALID_PATTERN, "yield", format),
            "21:10: " + getCheckMessage(MSG_INVALID_PATTERN, "test$stuff", format),
            "22:10: " + getCheckMessage(MSG_INVALID_PATTERN, "when", format),
            "23:10: " + getCheckMessage(MSG_INVALID_PATTERN, "Record", format),
            "25:19: " + getCheckMessage(MSG_INVALID_PATTERN, "record", format),
            "33:7: " + getCheckMessage(MSG_INVALID_PATTERN, "open", format),
            "34:10: " + getCheckMessage(MSG_INVALID_PATTERN, "transitive", format),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example2.java"), expected);
    }
}

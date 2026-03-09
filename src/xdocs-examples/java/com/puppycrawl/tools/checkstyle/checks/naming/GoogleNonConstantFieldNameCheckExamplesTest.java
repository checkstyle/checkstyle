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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleNonConstantFieldNameCheck.MSG_KEY_INVALID_FORMAT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class GoogleNonConstantFieldNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/googlenonconstantfieldname";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "31:13: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "Bad"),
            "35:20: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "mValue"),
            "39:21: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "f"),
            "42:23: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "foo_bar"),
            "46:7: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "fA"),
            "49:7: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "xml$parser"),
            "52:7: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "gradle_9_5_1"),
            "55:7: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "_foo2"),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

}

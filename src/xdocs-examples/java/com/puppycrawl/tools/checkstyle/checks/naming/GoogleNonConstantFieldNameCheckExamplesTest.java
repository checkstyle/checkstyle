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
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleNonConstantFieldNameCheck.MSG_KEY_INVALID_UNDERSCORE;

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
            "20:13: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "Bad"),
            "23:20: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "mValue"),
            "26:21: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "f"),
            "29:23: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_bar"),
            "32:7: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "fA"),
            "35:14: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "mField"),
            "38:15: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "foo$bar"),
            "41:17: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "a"),
            "44:7: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "gradle_9_5_1"),
            "47:7: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo1_bar"),
            "50:7: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "_foo"),
        };
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }
}

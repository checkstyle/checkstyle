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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class RecordComponentNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/recordcomponentname";
    }

    @Test
    public void testGetClassRequiredTokens() {
        final RecordComponentNameCheck checkObj =
                new RecordComponentNameCheck();
        final int[] expected = {TokenTypes.RECORD_COMPONENT_DEF};
        assertWithMessage("Default required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testRecordDefault()
            throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "19:34: " + getCheckMessage(MSG_INVALID_PATTERN, "value_123", pattern),
            "20:15: " + getCheckMessage(MSG_INVALID_PATTERN, "Values", pattern),
            "23:35: " + getCheckMessage(MSG_INVALID_PATTERN, "_value123", pattern),
            "24:9: " + getCheckMessage(MSG_INVALID_PATTERN, "$age", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNameDefault.java"), expected);
    }

    @Test
    public void testClassFooName()
            throws Exception {

        final String pattern = "^[a-z0-9]+$";

        final String[] expected = {
            "19:36: " + getCheckMessage(MSG_INVALID_PATTERN, "value_123", pattern),
            "20:15: " + getCheckMessage(MSG_INVALID_PATTERN, "Values", pattern),
            "23:37: " + getCheckMessage(MSG_INVALID_PATTERN, "V", pattern),
            "24:9: " + getCheckMessage(MSG_INVALID_PATTERN, "$age", pattern),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRecordComponentNameLowercase.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RecordComponentNameCheck typeParameterNameCheckObj =
                new RecordComponentNameCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.RECORD_COMPONENT_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }
}

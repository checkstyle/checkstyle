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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ExplicitInitializationCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/explicitinitialization";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "13:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "17:18: " + getCheckMessage(MSG_KEY, "y4", 0),
            "18:21: " + getCheckMessage(MSG_KEY, "b1", "false"),
            "22:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "22:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "23:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "26:11: " + getCheckMessage(MSG_KEY, "f1", 0),
            "27:12: " + getCheckMessage(MSG_KEY, "d1", 0),
            "30:17: " + getCheckMessage(MSG_KEY, "ch1", "\\0"),
            "31:17: " + getCheckMessage(MSG_KEY, "ch2", "\\0"),
            "47:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "48:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "55:21: " + getCheckMessage(MSG_KEY, "x", 0),
            "56:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "58:31: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "61:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "62:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "63:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "98:19: " + getCheckMessage(MSG_KEY, "shortVariable", "0"),
            "99:18: " + getCheckMessage(MSG_KEY, "bite", "0"),
            "100:12: " + getCheckMessage(MSG_KEY, "d", "0"),
        };
        verifyWithInlineConfigParser(
                getPath("InputExplicitInitialization.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final ExplicitInitializationCheck check = new ExplicitInitializationCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testOnlyObjectReferences() throws Exception {
        final String[] expected = {
            "13:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "22:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "22:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "23:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "47:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "48:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "56:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "58:31: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "62:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "63:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
        };
        verifyWithInlineConfigParser(
                getPath("InputExplicitInitializationOnlyObjectReference.java"),
                expected);
    }

}

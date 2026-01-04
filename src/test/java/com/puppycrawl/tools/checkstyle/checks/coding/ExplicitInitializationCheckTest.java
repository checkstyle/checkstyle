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
            "12:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "14:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "19:18: " + getCheckMessage(MSG_KEY, "y4", 0),
            "21:21: " + getCheckMessage(MSG_KEY, "b1", "false"),
            "25:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "25:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "27:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "31:11: " + getCheckMessage(MSG_KEY, "f1", 0),
            "33:12: " + getCheckMessage(MSG_KEY, "d1", 0),
            "37:17: " + getCheckMessage(MSG_KEY, "ch1", "\\0"),
            "39:17: " + getCheckMessage(MSG_KEY, "ch2", "\\0"),
            "56:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "58:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "66:21: " + getCheckMessage(MSG_KEY, "x", 0),
            "68:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "70:31: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "74:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "76:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "78:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "114:19: " + getCheckMessage(MSG_KEY, "shortVar", "0"),
            "116:18: " + getCheckMessage(MSG_KEY, "bite", "0"),
            "118:12: " + getCheckMessage(MSG_KEY, "d", "0"),
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
            "24:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "49:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "51:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "60:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "62:31: " + getCheckMessage(MSG_KEY, "barArr", "null"),
            "67:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "69:27: " + getCheckMessage(MSG_KEY, "barArr", "null"),
        };
        verifyWithInlineConfigParser(
                getPath("InputExplicitInitializationOnlyObjectReference.java"),
                expected);
    }

}

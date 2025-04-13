///
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
///

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class ExplicitInitializationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/explicitinitialization";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "11:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "12:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "16:18: " + getCheckMessage(MSG_KEY, "y4", 0),
            "17:21: " + getCheckMessage(MSG_KEY, "b1", "false"),
            "21:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "21:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "22:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "25:11: " + getCheckMessage(MSG_KEY, "f1", 0),
            "26:12: " + getCheckMessage(MSG_KEY, "d1", 0),
            "29:17: " + getCheckMessage(MSG_KEY, "ch1", "\\0"),
            "30:17: " + getCheckMessage(MSG_KEY, "ch2", "\\0"),
            "46:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "47:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "54:21: " + getCheckMessage(MSG_KEY, "x", 0),
            "55:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "56:31: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "59:17: " + getCheckMessage(MSG_KEY, "x", 0),
            "60:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "61:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "96:19: " + getCheckMessage(MSG_KEY, "shortVariable", "0"),
            "97:18: " + getCheckMessage(MSG_KEY, "bite", "0"),
            "98:12: " + getCheckMessage(MSG_KEY, "d", "0"),
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
            "12:20: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "21:22: " + getCheckMessage(MSG_KEY, "str1", "null"),
            "21:35: " + getCheckMessage(MSG_KEY, "str3", "null"),
            "22:9: " + getCheckMessage(MSG_KEY, "ar1", "null"),
            "46:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "47:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "55:29: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "56:31: " + getCheckMessage(MSG_KEY, "barArray", "null"),
            "60:25: " + getCheckMessage(MSG_KEY, "bar", "null"),
            "61:27: " + getCheckMessage(MSG_KEY, "barArray", "null"),
        };
        verifyWithInlineConfigParser(
                getPath("InputExplicitInitializationOnlyObjectReference.java"),
                expected);
    }

}

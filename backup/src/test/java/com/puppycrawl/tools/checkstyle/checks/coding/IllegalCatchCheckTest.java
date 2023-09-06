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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class IllegalCatchCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalcatch";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "15:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "16:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "22:11: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "23:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "24:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalCatch.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception {
        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "15:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "22:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "23:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalCatch3.java"), expected);
    }

    @Test
    public void testIllegalClassNamesBad() throws Exception {
        // check that incorrect names don't break the Check
        final String[] expected = {
            "15:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "23:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalCatch4.java"), expected);
    }

    @Test
    public void testMultipleTypes() throws Exception {
        final String[] expected = {
            "15:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "15:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "18:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "18:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "18:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "21:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "21:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "21:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "24:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "24:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "24:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalCatch2.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalCatchCheck check = new IllegalCatchCheck();
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

}

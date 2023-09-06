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
import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class IllegalThrowsCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalthrows";
    }

    @Test
    public void testDefault() throws Exception {

        final String[] expected = {
            "19:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "24:45: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "25:22: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsTestDefault.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception {
        // check that incorrect names don't break the Check
        final String[] expected = {
            "15:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "24:73: " + getCheckMessage(MSG_KEY, "java.lang.Error"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsTestIllegalClassNames.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with ignoredMethodNames attribute.
     */
    @Test
    public void testIgnoreMethodNames() throws Exception {

        final String[] expected = {
            "19:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "28:35: " + getCheckMessage(MSG_KEY, "Throwable"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsIgnoreMethodNames.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with both the attributes specified.
     */
    @Test
    public void testIllegalClassNamesWithIgnoreMethodNames() throws Exception {
        final String[] expected = {
            "14:33: " + getCheckMessage(MSG_KEY, "NullPointerException"),
            "27:35: " + getCheckMessage(MSG_KEY, "Throwable"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsTestClassNames.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck with <b>ignoreOverriddenMethods</b>
     * property.
     */
    @Test
    public void testIgnoreOverriddenMethods() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsIgnoreOverriddenMethods.java"), expected);
    }

    /**
     * Test to validate the IllegalThrowsCheck without <b>ignoreOverriddenMethods</b>
     * property.
     */
    @Test
    public void testNotIgnoreOverriddenMethods() throws Exception {

        final String[] expected = {
            "17:36: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "22:51: " + getCheckMessage(MSG_KEY, "RuntimeException"),
        };

        verifyWithInlineConfigParser(
                getPath("InputIllegalThrowsNotIgnoreOverriddenMethod.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalThrowsCheck check = new IllegalThrowsCheck();
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

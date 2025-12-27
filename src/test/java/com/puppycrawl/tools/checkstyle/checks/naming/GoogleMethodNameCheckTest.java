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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class GoogleMethodNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/googlemethodname";
    }

    @Test
    public void testGetRequiredTokens() {
        final GoogleMethodNameCheck checkObj = new GoogleMethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final GoogleMethodNameCheck checkObj = new GoogleMethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final GoogleMethodNameCheck checkObj = new GoogleMethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testValidRegularMethodNames() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameValidRegular.java"), expected);
    }

    @Test
    public void testInvalidRegularMethodNames() throws Exception {
        final String[] expected = {
            "9:10: " + getCheckMessage(MSG_KEY, "Foo"),
            "11:10: " + getCheckMessage(MSG_KEY, "f"),
            "13:10: " + getCheckMessage(MSG_KEY, "foo_bar"),
            "15:10: " + getCheckMessage(MSG_KEY, "foo_Bar"),
            "17:10: " + getCheckMessage(MSG_KEY, "foo__bar"),
            "20:10: " + getCheckMessage(MSG_KEY, "gradle_9_5_1"),
            "23:10: " + getCheckMessage(MSG_KEY, "jdk_9_0_392"),
            "26:10: " + getCheckMessage(MSG_KEY, "guava_33_4_5"),
            "29:10: " + getCheckMessage(MSG_KEY, "a_1"),
            "31:10: " + getCheckMessage(MSG_KEY, "guava33_4_5_"),
            "34:10: " + getCheckMessage(MSG_KEY, "guava33__4_5"),
            "37:10: " + getCheckMessage(MSG_KEY, "guava33_4_a"),
            "40:10: " + getCheckMessage(MSG_KEY, "_foo"),
            "42:10: " + getCheckMessage(MSG_KEY, "foo_"),
            "44:10: " + getCheckMessage(MSG_KEY, "__foo"),
            "46:10: " + getCheckMessage(MSG_KEY, "FOO"),
            "48:10: " + getCheckMessage(MSG_KEY, "f$oo"),
            "50:10: " + getCheckMessage(MSG_KEY, "transferMoney_deductsFromSource"),
            "53:10: " + getCheckMessage(MSG_KEY, "foo_bar_baz"),
            "56:10: " + getCheckMessage(MSG_KEY, "foo123_456_"),
            "59:10: " + getCheckMessage(MSG_KEY, "fO"),
            "61:10: " + getCheckMessage(MSG_KEY, "xY_z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInvalidRegular.java"), expected);
    }

    @Test
    public void testValidTestMethodNames() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameValidTest.java"), expected);
    }

    @Test
    public void testInvalidTestMethodNames() throws Exception {
        final String[] expected = {
            "12:10: " + getCheckMessage(MSG_KEY, "transferMoney_DeductsFromSource"),
            "16:10: " + getCheckMessage(MSG_KEY, "Testing_Foo"),
            "20:10: " + getCheckMessage(MSG_KEY, "testing__foo"),
            "24:10: " + getCheckMessage(MSG_KEY, "testing_foo_"),
            "28:10: " + getCheckMessage(MSG_KEY, "_testing"),
            "32:10: " + getCheckMessage(MSG_KEY, "TestingFooBad"),
            "36:10: " + getCheckMessage(MSG_KEY, "test_1value"),
            "40:10: " + getCheckMessage(MSG_KEY, "test_FOO_bar"),
            "44:10: " + getCheckMessage(MSG_KEY, "testing_a"),
            "48:10: " + getCheckMessage(MSG_KEY, "test_fO_bar"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInvalidTest.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final String[] expected = {
            "9:20: " + getCheckMessage(MSG_KEY, "Foo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameOverride.java"), expected);
    }

    @Test
    public void testNumberingSuffix() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameNumberingSuffix.java"), expected);
    }
}

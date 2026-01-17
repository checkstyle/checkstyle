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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY_CHARACTER_USAGE;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY_FORMAT_REGULAR;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY_FORMAT_TEST;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY_UNDERSCORE_REGULAR;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMethodNameCheck.MSG_KEY_UNDERSCORE_TEST;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class GoogleMethodNameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/googlemethodname";
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
    public void testValidRegularMethodNames() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameValidRegular.java"), expected);
    }

    @Test
    public void testInvalidRegularMethodNames() throws Exception {
        final String[] expected = {
            "9:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Foo"),
            "11:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "f"),
            "12:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_bar"),
            "14:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_Bar"),
            "16:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo__bar"),
            "19:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "gradle_9_5_1"),
            "22:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "jdk_9_0_392"),
            "25:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava_33_4_5"),
            "28:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "a_1"),
            "30:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33_4_5_"),
            "33:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33__4_5"),
            "36:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33_4_a"),
            "39:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "_foo"),
            "41:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_"),
            "43:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "__foo"),
            "45:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "FOO"),
            "47:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR,
                    "transferMoney_deductsFromSource"),
            "50:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_bar_baz"),
            "53:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo123_456_"),
            "56:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "fO"),
            "59:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "xY_z"),
            "61:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mName"),
            "64:10: " + getCheckMessage(MSG_KEY_CHARACTER_USAGE, "foo$bar"),
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
            "12:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "transferMoney_DeductsFromSource"),
            "16:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "Testing_Foo"),
            "20:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "testing__foo"),
            "24:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "testing_foo_"),
            "28:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "_testing"),
            "32:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "TestingFooBad"),
            "36:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "test_1value"),
            "40:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "test_FOO_bar"),
            "44:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "testing_a"),
            "48:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "test_fO_bar"),
            "52:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "solve6x6_returnsTrue"),
            "56:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST,
                    "solve6x6_noSolution_returnsFalse"),
            "60:10: " + getCheckMessage(MSG_KEY_CHARACTER_USAGE, "foo$bar"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInvalidTest.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final String[] expected = {
            "9:20: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Foo"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameOverride.java"), expected);
    }

    @Test
    public void testMethodNumberingSuffix() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameNumberingSuffix.java"), expected);
    }

    @Test
    public void testInsideInterface() throws Exception {
        final String[] expected = {
            "15:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Bar"),
            "19:18: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mValue"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInsideInterface.java"), expected);
    }

    @Test
    public void testInsideRecord() throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Foo"),
            "19:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mName"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInsideRecord.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "InvalidMethodName"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputGoogleMethodNameCompactSourceFile.java"),
                expected
        );
    }
}

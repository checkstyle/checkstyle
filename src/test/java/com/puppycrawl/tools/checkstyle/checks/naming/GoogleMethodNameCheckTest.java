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
            "13:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "f"),
            "17:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_bar"),
            "21:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_Bar"),
            "25:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo__bar"),
            "29:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "gradle_9_5_1"),
            "33:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "jdk_9_0_392"),
            "37:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava_33_4_5"),
            "41:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "a_1"),
            "45:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33_4_5_"),
            "49:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33__4_5"),
            "53:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "guava33_4_a"),
            "57:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "_foo"),
            "61:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_"),
            "65:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "__foo"),
            "69:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "FOO"),
            "73:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR,
                    "transferMoney_deductsFromSource"),
            "77:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo_bar_baz"),
            "81:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "foo123_456_"),
            "85:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "fO"),
            "89:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_REGULAR, "xY_z"),
            "93:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mName"),
            "97:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "fooBAR"),
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
            "18:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "Testing_Foo"),
            "24:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "testing__foo"),
            "29:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "testing_foo_"),
            "34:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "_testing"),
            "39:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "TestingFooBad"),
            "45:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "test_1value"),
            "50:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "test_FOO_bar"),
            "57:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "testing_a"),
            "63:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "test_fO_bar"),
            "70:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST, "solve6x6_returnsTrue"),
            "75:10: " + getCheckMessage(MSG_KEY_UNDERSCORE_TEST,
                    "solve6x6_noSolution_returnsFalse"),
            "80:10: " + getCheckMessage(MSG_KEY_FORMAT_TEST, "fooBAR"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInvalidTest.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final String[] expected = {
            "11:20: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Foo"),
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
            "21:18: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mValue"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInsideInterface.java"), expected);
    }

    @Test
    public void testInsideRecord() throws Exception {
        final String[] expected = {
            "16:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "Foo"),
            "20:10: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "mName"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMethodNameInsideRecord.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY_FORMAT_REGULAR, "InvalidMethodName"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputGoogleMethodNameCompactSourceFile.java"),
                expected
        );
    }

}

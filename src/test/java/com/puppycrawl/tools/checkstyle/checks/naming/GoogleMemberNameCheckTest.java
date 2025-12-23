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
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMemberNameCheck.MSG_KEY_INVALID_FORMAT;
import static com.puppycrawl.tools.checkstyle.checks.naming.GoogleMemberNameCheck.MSG_KEY_INVALID_UNDERSCORE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class GoogleMemberNameCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/googlemembername";
    }

    @Test
    public void testGetAcceptableTokens() {
        final GoogleMemberNameCheck checkObj = new GoogleMemberNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testValidMemberNames() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameValid.java"), expected);
    }

    @Test
    public void testInvalidMemberNames() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "Foo"),
            "13:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "f"),
            "15:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_bar"),
            "17:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_Bar"),
            "19:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo__bar"),
            "22:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "gradle_9_5_1"),
            "25:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "jdk_9_0_392"),
            "28:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "guava_33_4_5"),
            "31:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "a_1"),
            "34:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "guava33_4_5_"),
            "37:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "guava33__4_5"),
            "40:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "guava33_4_a"),
            "43:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "_foo"),
            "45:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_"),
            "47:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "__foo"),
            "49:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "FOO"),
            "51:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "f$bar"),
            "53:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "fO"),
            "55:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "mField"),
            "58:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "pValue"),
            "61:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "sInstance"),
            "64:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "bFlag"),
            "67:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "nCount"),
            "70:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "iIndex"),
            "73:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "a"),
            "75:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "x"),
            "77:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "z"),
            "79:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "aB"),
            "81:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "xY"),
            "83:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "zA"),
            "85:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "FooBar"),
            "88:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "XMLParser"),
            "91:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "HTTPClient"),
            "94:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_1bar"),
            "97:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo1_bar"),
            "100:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_bar_baz"),
            "103:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "foo$bar"),
            "106:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "$foo"),
            "109:9: " + getCheckMessage(MSG_KEY_INVALID_FORMAT, "bar$"),
            "112:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo_1_a"),
            "115:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "foo1__2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameInvalid.java"), expected);
    }

    @Test
    public void testNumberingSuffix() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameNumberingSuffix.java"), expected);
    }

    @Test
    public void testStaticMemberNameSkipped() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameStaticSkipped.java"), expected);
    }

    @Test
    public void testLocalVariables() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameLocal.java"), expected);
    }

    @Test
    public void testInterfaceAndAnnotation() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameInterfaceAnnotation.java"), expected);
    }

    @Test
    public void testInnerClasses() throws Exception {
        final String[] expected = {
            "12:13: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Inner_Bad"),
            "19:17: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Nested_Bad"),
            "28:13: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Static_Bad"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameInnerClasses.java"), expected);
    }

    @Test
    public void testStaticFinalSkipped() throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameStaticFinal.java"), expected);
    }

    @Test
    public void testMixedModifiers() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Static_Bad"),
            "16:9: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Instance_Bad"),
            "19:15: " + getCheckMessage(MSG_KEY_INVALID_UNDERSCORE, "Final_Instance"),
        };
        verifyWithInlineConfigParser(
                getPath("InputGoogleMemberNameMixedModifiers.java"), expected);
    }
}

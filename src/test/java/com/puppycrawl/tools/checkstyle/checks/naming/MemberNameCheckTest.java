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
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MemberNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/membername";
    }

    @Test
    public void testGetRequiredTokens() {
        final MemberNameCheck checkObj = new MemberNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testSpecifiedOne()
            throws Exception {

        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "41:17: " + getCheckMessage(MSG_INVALID_PATTERN, "badMember", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberNameSimpleOne.java"), expected);
    }

    @Test
    public void testSpecifiedTwo()
            throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputMemberNameSimpleTwo.java"), expected);
    }

    @Test
    public void testSpecifiedThree()
            throws Exception {

        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "50:17: " + getCheckMessage(MSG_INVALID_PATTERN, "someMember", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberNameSimpleThree.java"), expected);
    }

    @Test
    public void testInnerClass()
            throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "63:25: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberNameInner.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "22:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "23:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "24:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testUnderlined() throws Exception {

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "17:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "18:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName2.java"), expected);
    }

    @Test
    public void testPublicOnly() throws Exception {

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName3.java"), expected);
    }

    @Test
    public void testProtectedOnly() throws Exception {

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName4.java"), expected);
    }

    @Test
    public void testPackageOnly() throws Exception {

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName5.java"), expected);
    }

    @Test
    public void testPrivateOnly() throws Exception {

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName6.java"), expected);
    }

    @Test
    public void testNotPrivate() throws Exception {

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "22:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "23:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberName7.java"), expected);
    }

    @Test
    public void memberNameExtended() throws Exception {

        final String pattern = "^[a-z][a-z0-9][a-zA-Z0-9]*$";

        final String[] expected = {
            "20:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "21:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "22:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "23:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "25:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "26:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "27:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "28:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "31:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "32:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "33:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "34:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "36:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "37:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "38:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "39:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "43:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "44:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "45:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "46:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "48:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "49:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "50:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "51:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "75:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "76:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "77:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "78:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "80:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "81:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "82:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "83:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputMemberNameExtended.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MemberNameCheck memberNameCheckObj = new MemberNameCheck();
        final int[] actual = memberNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

}

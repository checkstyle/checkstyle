////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testSpecified()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^m[A-Z][a-zA-Z0-9]*$");

        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "41:17: " + getCheckMessage(MSG_INVALID_PATTERN, "badMember", pattern),
            "230:17: " + getCheckMessage(MSG_INVALID_PATTERN, "someMember", pattern),
        };
        verify(checkConfig, getPath("InputMemberNameSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "63:25: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
        };
        verify(checkConfig, getPath("InputMemberNameInner.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "22:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "23:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "24:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testUnderlined() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^_[a-z]*$");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "17:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "18:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verify(checkConfig, getPath("InputMemberName2.java"), expected);
    }

    @Test
    public void testPublicOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^_[a-z]*$");
        checkConfig.addProperty("applyToProtected", "false");
        checkConfig.addProperty("applyToPackage", "false");
        checkConfig.addProperty("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "16:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
        };
        verify(checkConfig, getPath("InputMemberName3.java"), expected);
    }

    @Test
    public void testProtectedOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^_[a-z]*$");
        checkConfig.addProperty("applyToPublic", "false");
        checkConfig.addProperty("applyToPackage", "false");
        checkConfig.addProperty("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "17:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
        };
        verify(checkConfig, getPath("InputMemberName4.java"), expected);
    }

    @Test
    public void testPackageOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^_[a-z]*$");
        checkConfig.addProperty("applyToPublic", "false");
        checkConfig.addProperty("applyToProtected", "false");
        checkConfig.addProperty("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
        };
        verify(checkConfig, getPath("InputMemberName5.java"), expected);
    }

    @Test
    public void testPrivateOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^_[a-z]*$");
        checkConfig.addProperty("applyToPublic", "false");
        checkConfig.addProperty("applyToProtected", "false");
        checkConfig.addProperty("applyToPackage", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "19:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verify(checkConfig, getPath("InputMemberName6.java"), expected);
    }

    @Test
    public void testNotPrivate() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("applyToPrivate", "false");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "21:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "22:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "23:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
        };
        verify(checkConfig, getPath("InputMemberName7.java"), expected);
    }

    @Test
    public void memberNameExtended() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MemberNameCheck.class);
        checkConfig.addProperty("format", "^[a-z][a-z0-9][a-zA-Z0-9]*$");

        final String pattern = "^[a-z][a-z0-9][a-zA-Z0-9]*$";

        final String[] expected = {
            "19:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "20:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "21:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "22:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "24:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "25:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "26:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "27:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "30:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "31:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "32:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "33:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "35:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "36:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "37:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "38:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "42:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "43:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "44:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "45:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "47:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "48:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "49:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "50:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "74:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "75:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "76:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "77:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "79:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "80:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "81:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "82:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verify(checkConfig, getPath("InputMemberNameExtended.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MemberNameCheck memberNameCheckObj = new MemberNameCheck();
        final int[] actual = memberNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}

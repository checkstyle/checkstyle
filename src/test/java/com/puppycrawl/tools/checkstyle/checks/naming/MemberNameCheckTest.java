////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MemberNameCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "naming" + File.separator
                + "membername" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final MemberNameCheck checkObj = new MemberNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testSpecified()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^m[A-Z][a-zA-Z0-9]*$");

        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "35:17: " + getCheckMessage(MSG_INVALID_PATTERN, "badMember", pattern),
            "224:17: " + getCheckMessage(MSG_INVALID_PATTERN, "someMember", pattern),
        };
        verify(checkConfig, getPath("InputMemberNameSimple.java"), expected);
    }

    @Test
    public void testInnerClass()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "56:25: " + getCheckMessage(MSG_INVALID_PATTERN, "ABC", pattern),
        };
        verify(checkConfig, getPath("InputMemberNameInner.java"), expected);
    }

    @Test
    public void testDefaults() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "10:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "11:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "12:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "13:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testUnderlined() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "6:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "7:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "8:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testPublicOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testProtectedOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToPackage", "false");
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "6:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testPackageOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "7:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testPrivateOnly() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^_[a-z]*$");
        checkConfig.addAttribute("applyToPublic", "false");
        checkConfig.addAttribute("applyToProtected", "false");
        checkConfig.addAttribute("applyToPackage", "false");

        final String pattern = "^_[a-z]*$";

        final String[] expected = {
            "8:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void testNotPrivate() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("applyToPrivate", "false");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "10:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "11:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "12:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
        };
        verify(checkConfig, getPath("InputMemberName.java"), expected);
    }

    @Test
    public void memberNameExtended() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(MemberNameCheck.class);
        checkConfig.addAttribute("format", "^[a-z][a-z0-9][a-zA-Z0-9]*$");

        final String pattern = "^[a-z][a-z0-9][a-zA-Z0-9]*$";

        final String[] expected = {
            "8:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "9:19: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "10:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "11:17: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "13:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "14:19: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "15:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "16:17: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "19:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "20:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "21:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "22:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "24:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "25:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "26:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "27:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "31:20: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "32:23: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "33:13: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "34:21: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "36:20: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "37:23: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "38:13: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "39:21: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
            "63:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "64:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "65:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "66:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "68:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "69:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "70:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "71:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
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
        assertArrayEquals(expected, actual);
    }
}

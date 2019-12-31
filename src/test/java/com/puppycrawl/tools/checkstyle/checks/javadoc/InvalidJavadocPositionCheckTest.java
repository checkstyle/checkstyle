////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class InvalidJavadocPositionCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/invalidjavadocposition";
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        final InvalidJavadocPositionCheck check = new InvalidJavadocPositionCheck();
        final int[] actual = check.getAcceptableTokens();

        assertArrayEquals(expected, actual, "Acceptable tokens differs from expected");
    }

    @Test
    public void testGetRequiredTokens() {
        final int[] expected = {
            TokenTypes.BLOCK_COMMENT_BEGIN,
        };
        final InvalidJavadocPositionCheck check = new InvalidJavadocPositionCheck();
        final int[] actual = check.getRequiredTokens();

        assertArrayEquals(expected, actual, "Required tokens differ from expected");
    }

    @Test
    public void testDefault() throws Exception {
        final Configuration checkConfig = createModuleConfig(InvalidJavadocPositionCheck.class);
        final String[] expected = {
            "1:9: " + getCheckMessage(MSG_KEY),
            "3:1: " + getCheckMessage(MSG_KEY),
            "6:1: " + getCheckMessage(MSG_KEY),
            "9:5: " + getCheckMessage(MSG_KEY),
            "14:5: " + getCheckMessage(MSG_KEY),
            "17:5: " + getCheckMessage(MSG_KEY),
            "27:9: " + getCheckMessage(MSG_KEY),
            "28:17: " + getCheckMessage(MSG_KEY),
            "29:17: " + getCheckMessage(MSG_KEY),
            "39:10: " + getCheckMessage(MSG_KEY),
            "40:19: " + getCheckMessage(MSG_KEY),
            "41:19: " + getCheckMessage(MSG_KEY),
            "42:21: " + getCheckMessage(MSG_KEY),
            "43:23: " + getCheckMessage(MSG_KEY),
            "44:23: " + getCheckMessage(MSG_KEY),
            "47:1: " + getCheckMessage(MSG_KEY),
            "52:7: " + getCheckMessage(MSG_KEY),
            "53:36: " + getCheckMessage(MSG_KEY),
            "56:9: " + getCheckMessage(MSG_KEY),
            "57:9: " + getCheckMessage(MSG_KEY),
            "58:9: " + getCheckMessage(MSG_KEY),
            "61:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputInvalidJavadocPosition.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final Configuration checkConfig = createModuleConfig(InvalidJavadocPositionCheck.class);
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("package-info.java"), expected);
    }

    @Test
    public void testPackageInfoComment() throws Exception {
        final Configuration checkConfig = createModuleConfig(InvalidJavadocPositionCheck.class);
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("comment/package-info.java"), expected);
    }

}

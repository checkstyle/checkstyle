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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY_ON_SEPARATE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class NoWhitespaceBeforeCaseDefaultColonCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace"
                + "/nowhitespacebeforecasedefaultcolon";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                NoWhitespaceBeforeCaseDefaultColonCheck.class);
        final String[] expected = {
            "11:20: " + getCheckMessage(MSG_KEY, ":"),
            "15:21: " + getCheckMessage(MSG_KEY, ":"),
            "28:37: " + getCheckMessage(MSG_KEY, ":"),
            "36:21: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
            "40:28: " + getCheckMessage(MSG_KEY, ":"),
            "43:1: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
            "46:27: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
            "57:38: " + getCheckMessage(MSG_KEY, ":"),
            "62:38: " + getCheckMessage(MSG_KEY, ":"),
            "75:24: " + getCheckMessage(MSG_KEY, ":"),
            "78:21: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
            "85:38: " + getCheckMessage(MSG_KEY, ":"),
            "88:35: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeCaseDefaultColonEnumAndStrings.java"), expected);
    }

    @Test
    public void testDefaultNonCompilable() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                NoWhitespaceBeforeCaseDefaultColonCheck.class);
        final String[] expected = {
                "33:22: " + getCheckMessage(MSG_KEY, ":"),
                "36:21: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
                "39:25: " + getCheckMessage(MSG_KEY, ":"),
                "51:32: " + getCheckMessage(MSG_KEY, ":"),
                "68:17: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
                "71:20: " + getCheckMessage(MSG_KEY, ":"),
                "74:21: " + getCheckMessage(MSG_KEY_ON_SEPARATE),
        };
        verify(checkConfig, getNonCompilablePath("InputNoWhitespaceBeforeCaseDefaultColonEnumAndStrings.java"), expected);
    }

    @Test
    public void testAcceptableTokenIsColon() {
        final NoWhitespaceBeforeCaseDefaultColonCheck check =
                new NoWhitespaceBeforeCaseDefaultColonCheck();
        assertArrayEquals(new int[] {TokenTypes.COLON}, check.getAcceptableTokens(),
                "Acceptable token should be colon");
    }

}

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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCaseDefaultColonCheck.MSG_KEY;

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
            "15:20: " + getCheckMessage(MSG_KEY, ":"),
            "19:21: " + getCheckMessage(MSG_KEY, ":"),
            "32:37: " + getCheckMessage(MSG_KEY, ":"),
            "40:21: " + getCheckMessage(MSG_KEY, ":"),
            "44:28: " + getCheckMessage(MSG_KEY, ":"),
            "47:1: " + getCheckMessage(MSG_KEY, ":"),
            "50:27: " + getCheckMessage(MSG_KEY, ":"),
            "61:38: " + getCheckMessage(MSG_KEY, ":"),
            "66:38: " + getCheckMessage(MSG_KEY, ":"),
            "79:24: " + getCheckMessage(MSG_KEY, ":"),
            "82:21: " + getCheckMessage(MSG_KEY, ":"),
            "89:38: " + getCheckMessage(MSG_KEY, ":"),
            "92:35: " + getCheckMessage(MSG_KEY, ":"),
        };
        verify(checkConfig, getPath("InputNoWhitespaceBeforeCaseDefaultColon.java"),
                expected);
    }

    @Test
    public void testDefaultNonCompilable() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(
                NoWhitespaceBeforeCaseDefaultColonCheck.class);
        final String[] expected = {
            "33:22: " + getCheckMessage(MSG_KEY, ":"),
            "36:21: " + getCheckMessage(MSG_KEY, ":"),
            "39:25: " + getCheckMessage(MSG_KEY, ":"),
            "58:20: " + getCheckMessage(MSG_KEY, ":"),
            "71:32: " + getCheckMessage(MSG_KEY, ":"),
            "88:17: " + getCheckMessage(MSG_KEY, ":"),
            "91:20: " + getCheckMessage(MSG_KEY, ":"),
            "94:21: " + getCheckMessage(MSG_KEY, ":"),
        };
        verify(checkConfig,
                getNonCompilablePath("InputNoWhitespaceBeforeCaseDefaultColonEnumAndStrings.java"),
                expected);
    }

    @Test
    public void testAcceptableTokenIsColon() {
        final NoWhitespaceBeforeCaseDefaultColonCheck check =
                new NoWhitespaceBeforeCaseDefaultColonCheck();
        assertWithMessage("Acceptable token should be colon").that(new int[] {TokenTypes.COLON})
                .isEqualTo(check.getAcceptableTokens());
    }

}

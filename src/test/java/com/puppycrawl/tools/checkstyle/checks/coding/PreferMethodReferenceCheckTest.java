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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertThat;
import static com.puppycrawl.tools.checkstyle.checks.coding.PreferMethodReferenceCheck.MSG_METHOD_REF;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PreferMethodReferenceCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/prefermethodreference";
    }

    @Test
    public void testMethodCallsOnEverything() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
        checkConfig.addAttribute("detectForExpression", "true");
        final String[] expected = {
            "23:28: " + getCheckMessage(MSG_METHOD_REF),
            "24:40: " + getCheckMessage(MSG_METHOD_REF),
            "25:41: " + getCheckMessage(MSG_METHOD_REF),
            "26:32: " + getCheckMessage(MSG_METHOD_REF),
            "27:36: " + getCheckMessage(MSG_METHOD_REF),
            "28:21: " + getCheckMessage(MSG_METHOD_REF),
            "29:37: " + getCheckMessage(MSG_METHOD_REF),
            "30:52: " + getCheckMessage(MSG_METHOD_REF),
            "31:42: " + getCheckMessage(MSG_METHOD_REF),
            "32:38: " + getCheckMessage(MSG_METHOD_REF),
            "33:40: " + getCheckMessage(MSG_METHOD_REF),
            "34:32: " + getCheckMessage(MSG_METHOD_REF),
            "35:41: " + getCheckMessage(MSG_METHOD_REF),
            "36:38: " + getCheckMessage(MSG_METHOD_REF),
            "38:18: " + getCheckMessage(MSG_METHOD_REF),
            "39:42: " + getCheckMessage(MSG_METHOD_REF),
            "40:52: " + getCheckMessage(MSG_METHOD_REF),
            "42:36: " + getCheckMessage(MSG_METHOD_REF),
            "43:60: " + getCheckMessage(MSG_METHOD_REF),
        };
        verify(checkConfig,
            getPath("InputPreferMethodReferenceMethodCallsOnExpression.java"),
            expected);
    }

    @Test
    public void testMethodCallsNoExpressions() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
        final String[] expected = {
            "12:43: " + getCheckMessage(MSG_METHOD_REF),
            "14:43: " + getCheckMessage(MSG_METHOD_REF),
            "16:45: " + getCheckMessage(MSG_METHOD_REF),
            "18:47: " + getCheckMessage(MSG_METHOD_REF),
            "20:46: " + getCheckMessage(MSG_METHOD_REF),
            "22:66: " + getCheckMessage(MSG_METHOD_REF),
            "24:53: " + getCheckMessage(MSG_METHOD_REF),
            "27:36: " + getCheckMessage(MSG_METHOD_REF),
            "30:36: " + getCheckMessage(MSG_METHOD_REF),
            "34:36: " + getCheckMessage(MSG_METHOD_REF),
            "37:26: " + getCheckMessage(MSG_METHOD_REF),
            "39:32: " + getCheckMessage(MSG_METHOD_REF),
            "41:33: " + getCheckMessage(MSG_METHOD_REF),
        };
        verify(checkConfig, getPath("InputPreferMethodReferenceMethodCallsDefault.java"), expected);
    }

    @Test
    public void testStatementsListsWithExpressions() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
        checkConfig.addAttribute("detectForExpression", "true");
        final String[] expected = {
            "23:29: " + getCheckMessage(MSG_METHOD_REF),
            "25:41: " + getCheckMessage(MSG_METHOD_REF),
            "27:34: " + getCheckMessage(MSG_METHOD_REF),
            "29:37: " + getCheckMessage(MSG_METHOD_REF),
            "31:40: " + getCheckMessage(MSG_METHOD_REF),
            "33:38: " + getCheckMessage(MSG_METHOD_REF),
            "35:53: " + getCheckMessage(MSG_METHOD_REF),
            "38:15: " + getCheckMessage(MSG_METHOD_REF),
            "40:23: " + getCheckMessage(MSG_METHOD_REF),
            "42:32: " + getCheckMessage(MSG_METHOD_REF),
            "44:40: " + getCheckMessage(MSG_METHOD_REF),
            "46:40: " + getCheckMessage(MSG_METHOD_REF),
            "48:40: " + getCheckMessage(MSG_METHOD_REF),
            "50:40: " + getCheckMessage(MSG_METHOD_REF),
            "52:21: " + getCheckMessage(MSG_METHOD_REF),
            "54:52: " + getCheckMessage(MSG_METHOD_REF),
            "57:16: " + getCheckMessage(MSG_METHOD_REF),
            "59:38: " + getCheckMessage(MSG_METHOD_REF),
            "61:40: " + getCheckMessage(MSG_METHOD_REF),
            "63:32: " + getCheckMessage(MSG_METHOD_REF),
            "65:41: " + getCheckMessage(MSG_METHOD_REF),
            "68:38: " + getCheckMessage(MSG_METHOD_REF),
            "70:46: " + getCheckMessage(MSG_METHOD_REF),
            "73:43: " + getCheckMessage(MSG_METHOD_REF),
            "75:52: " + getCheckMessage(MSG_METHOD_REF),
            "78:36: " + getCheckMessage(MSG_METHOD_REF),
            "80:38: " + getCheckMessage(MSG_METHOD_REF),
            "82:38: " + getCheckMessage(MSG_METHOD_REF),
            "84:39: " + getCheckMessage(MSG_METHOD_REF),
            "86:34: " + getCheckMessage(MSG_METHOD_REF),
        };
        verify(checkConfig,
            getPath("InputPreferMethodReferenceStatementsOnExpression.java"),
            expected);
    }

    @Test
    public void testObjectCreation() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
        final String[] expected = {
            "15:29: " + getCheckMessage(MSG_METHOD_REF),
            "16:39: " + getCheckMessage(MSG_METHOD_REF),
            "17:39: " + getCheckMessage(MSG_METHOD_REF),
            "18:39: " + getCheckMessage(MSG_METHOD_REF),
            "19:54: " + getCheckMessage(MSG_METHOD_REF),
            "20:43: " + getCheckMessage(MSG_METHOD_REF),
            "21:43: " + getCheckMessage(MSG_METHOD_REF),
        };
        verify(checkConfig, getPath("InputPreferMethodReferenceObjectCreation.java"), expected);
    }

    @Test
    public void testTokens() {
        final PreferMethodReferenceCheck check = new PreferMethodReferenceCheck();
        final int[] expected = {
            TokenTypes.LAMBDA,
        };
        assertThat(check.getAcceptableTokens()).isEqualTo(expected);
        assertThat(check.getDefaultTokens()).isEqualTo(expected);
        assertThat(check.getRequiredTokens()).isEqualTo(expected);
    }
}

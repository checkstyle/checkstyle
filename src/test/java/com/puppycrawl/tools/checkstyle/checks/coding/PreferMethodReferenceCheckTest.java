////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.coding.PreferMethodReferenceCheck.MSG_METHOD_REF;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PreferMethodReferenceCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/prefermethodreference";
    }

    @Test
    public void testMethodCalls() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
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
        verify(checkConfig, getPath("InputPreferMethodReferenceMethodCalls.java"), expected);
    }

    @Test
    public void testStatementsLists() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PreferMethodReferenceCheck.class);
        final String[] expected = {
            "23:29: " + getCheckMessage(MSG_METHOD_REF),
            "24:41: " + getCheckMessage(MSG_METHOD_REF),
            "25:34: " + getCheckMessage(MSG_METHOD_REF),
            "26:37: " + getCheckMessage(MSG_METHOD_REF),
            "27:40: " + getCheckMessage(MSG_METHOD_REF),
            "28:38: " + getCheckMessage(MSG_METHOD_REF),
            "29:53: " + getCheckMessage(MSG_METHOD_REF),
            "31:15: " + getCheckMessage(MSG_METHOD_REF),
            "32:23: " + getCheckMessage(MSG_METHOD_REF),
            "33:32: " + getCheckMessage(MSG_METHOD_REF),
            "34:40: " + getCheckMessage(MSG_METHOD_REF),
            "35:40: " + getCheckMessage(MSG_METHOD_REF),
            "36:40: " + getCheckMessage(MSG_METHOD_REF),
            "37:40: " + getCheckMessage(MSG_METHOD_REF),
            "38:21: " + getCheckMessage(MSG_METHOD_REF),
            "39:52: " + getCheckMessage(MSG_METHOD_REF),
            "41:16: " + getCheckMessage(MSG_METHOD_REF),
            "42:38: " + getCheckMessage(MSG_METHOD_REF),
            "43:40: " + getCheckMessage(MSG_METHOD_REF),
            "44:32: " + getCheckMessage(MSG_METHOD_REF),
            "45:41: " + getCheckMessage(MSG_METHOD_REF),
            "47:38: " + getCheckMessage(MSG_METHOD_REF),
            "48:46: " + getCheckMessage(MSG_METHOD_REF),
            "50:43: " + getCheckMessage(MSG_METHOD_REF),
            "51:52: " + getCheckMessage(MSG_METHOD_REF),
            "53:36: " + getCheckMessage(MSG_METHOD_REF),
            "54:38: " + getCheckMessage(MSG_METHOD_REF),
            "55:38: " + getCheckMessage(MSG_METHOD_REF),
        };
        verify(checkConfig, getPath("InputPreferMethodReferenceStatements.java"), expected);
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
        Assert.assertArrayEquals("Acceptable required tokens are invalid",
            expected, check.getAcceptableTokens());
        Assert.assertArrayEquals("Default required tokens are invalid",
            expected, check.getDefaultTokens());
        Assert.assertArrayEquals("Required required tokens are invalid",
            expected, check.getRequiredTokens());
    }
}

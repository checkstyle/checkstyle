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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkLineWrappingCheck.MSG_ERROR_MULTI;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OpenjdkLineWrappingCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/indentation/openjdklinewrapping";
    }

    @Test
    public void testTokens() {
        final OpenjdkLineWrappingCheck check = new OpenjdkLineWrappingCheck();
        final int[] expected = {TokenTypes.EXPR};
        assertWithMessage("Default tokens")
                .that(check.getDefaultTokens()).isEqualTo(expected);
        assertWithMessage("Acceptable tokens")
                .that(check.getAcceptableTokens()).isEqualTo(expected);
        assertWithMessage("Required tokens")
                .that(check.getRequiredTokens()).isEqualTo(expected);
    }

    @Test
    public void testExpressions() throws Exception {
        final String[] expected = {
            "61:14: " + getCheckMessage(MSG_ERROR_MULTI, "+", 13, "16, 24"),
            "63:20: " + getCheckMessage(MSG_ERROR_MULTI, "second", 19, "16, 23, 24"),
            "65:22: " + getCheckMessage(MSG_ERROR_MULTI, "+", 21, "16, 20, 28"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrapping.java"), expected);
    }

    @Test
    public void testNestedExpressions() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_ERROR_MULTI, "second", 12, "16, 20"),
            "22:19: " + getCheckMessage(MSG_ERROR_MULTI, "3", 18, "20"),
            "28:22: " + getCheckMessage(MSG_ERROR_MULTI, "3", 21, "16, 20, 24, 28"),
            "62:41: " + getCheckMessage(MSG_ERROR_MULTI, "\"x\"", 40, "16, 29"),
            "64:21: " + getCheckMessage(MSG_ERROR_MULTI, "3", 20, "16, 28"),
            "68:14: " + getCheckMessage(MSG_ERROR_MULTI, "2", 13, "16, 27, 31"),
            "70:14: " + getCheckMessage(MSG_ERROR_MULTI, "TYPE_ARGUMENT", 13, "16, 33"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingNested.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(MSG_ERROR_MULTI, "+", 11, "12, 16, 24"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputOpenjdkLineWrappingCompact.java"), expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "14:13: " + getCheckMessage(MSG_ERROR_MULTI, "+", 12, "16, 20, 28"),
        };
        verifyWithInlineConfigParser(getPath("InputOpenjdkLineWrappingTabs.java"), expected);
    }

}

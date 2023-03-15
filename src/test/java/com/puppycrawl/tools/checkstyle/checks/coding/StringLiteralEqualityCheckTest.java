///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.StringLiteralEqualityCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class StringLiteralEqualityCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/stringliteralequality";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "17:18: " + getCheckMessage(MSG_KEY, "=="),
            "22:20: " + getCheckMessage(MSG_KEY, "=="),
            "27:22: " + getCheckMessage(MSG_KEY, "=="),
        };
        verifyWithInlineConfigParser(
                getPath("InputStringLiteralEquality.java"), expected);
    }

    @Test
    public void testStringLiteralEqualityTextBlocks() throws Exception {
        final String[] expected = {
            "14:34: " + getCheckMessage(MSG_KEY, "=="),
            "22:21: " + getCheckMessage(MSG_KEY, "=="),
            "25:24: " + getCheckMessage(MSG_KEY, "!="),
            "28:34: " + getCheckMessage(MSG_KEY, "=="),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputStringLiteralEqualityCheckTextBlocks.java"),
            expected);
    }

    @Test
    public void testConcatenatedStringLiterals() throws Exception {
        final String[] expected = {
            "14:15: " + getCheckMessage(MSG_KEY, "=="),
            "17:24: " + getCheckMessage(MSG_KEY, "=="),
            "20:31: " + getCheckMessage(MSG_KEY, "!="),
            "23:15: " + getCheckMessage(MSG_KEY, "=="),
            "28:26: " + getCheckMessage(MSG_KEY, "=="),
            "31:26: " + getCheckMessage(MSG_KEY, "!="),
            "34:15: " + getCheckMessage(MSG_KEY, "!="),
            "37:32: " + getCheckMessage(MSG_KEY, "=="),
            "39:33: " + getCheckMessage(MSG_KEY, "!="),
            "41:31: " + getCheckMessage(MSG_KEY, "!="),
            "42:27: " + getCheckMessage(MSG_KEY, "=="),
        };
        verifyWithInlineConfigParser(
                getPath("InputStringLiteralEqualityConcatenatedString.java"), expected);
    }

    @Test
    public void testConcatenatedTextBlocks() throws Exception {
        final String[] expected = {
            "15:15: " + getCheckMessage(MSG_KEY, "=="),
            "21:23: " + getCheckMessage(MSG_KEY, "=="),
            "26:23: " + getCheckMessage(MSG_KEY, "!="),
            "29:15: " + getCheckMessage(MSG_KEY, "=="),
            "38:26: " + getCheckMessage(MSG_KEY, "=="),
            "42:26: " + getCheckMessage(MSG_KEY, "!="),
            "46:15: " + getCheckMessage(MSG_KEY, "!="),
            "51:28: " + getCheckMessage(MSG_KEY, "!="),
            "53:31: " + getCheckMessage(MSG_KEY, "=="),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputStringLiteralEqualityConcatenatedTextBlocks.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final StringLiteralEqualityCheck check = new StringLiteralEqualityCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

}

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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ArrayTypeStyleCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/arraytypestyle";
    }

    @Test
    public void testGetRequiredTokens() {
        final ArrayTypeStyleCheck checkObj = new ArrayTypeStyleCheck();
        final int[] expected = {TokenTypes.ARRAY_DECLARATOR};
        assertWithMessage("Required tokens differs from expected")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testJavaStyleOn()
            throws Exception {
        final String[] expected = {
            "13:23: " + getCheckMessage(MSG_KEY),
            "14:18: " + getCheckMessage(MSG_KEY),
            "20:44: " + getCheckMessage(MSG_KEY),
            "44:33: " + getCheckMessage(MSG_KEY),
            "49:34: " + getCheckMessage(MSG_KEY),
            "49:36: " + getCheckMessage(MSG_KEY),
            "55:27: " + getCheckMessage(MSG_KEY),
            "55:29: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayTypeStyle.java"), expected);
    }

    @Test
    public void testJavaStyleOff()
            throws Exception {
        final String[] expected = {
            "12:16: " + getCheckMessage(MSG_KEY),
            "16:39: " + getCheckMessage(MSG_KEY),
            "22:18: " + getCheckMessage(MSG_KEY),
            "30:20: " + getCheckMessage(MSG_KEY),
            "44:33: " + getCheckMessage(MSG_KEY),
            "49:34: " + getCheckMessage(MSG_KEY),
            "49:36: " + getCheckMessage(MSG_KEY),
            "55:27: " + getCheckMessage(MSG_KEY),
            "55:29: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayTypeStyleOff.java"), expected);
    }

    @Test
    public void testNestedGenerics()
            throws Exception {
        final String[] expected = {
            "22:45: " + getCheckMessage(MSG_KEY),
            "23:61: " + getCheckMessage(MSG_KEY),
            "24:76: " + getCheckMessage(MSG_KEY),
            "31:16: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputArrayTypeStyleNestedGenerics.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expected = {TokenTypes.ARRAY_DECLARATOR };
        final ArrayTypeStyleCheck check = new ArrayTypeStyleCheck();
        final int[] actual = check.getAcceptableTokens();
        assertWithMessage("Amount of acceptable tokens differs from expected")
                .that(actual)
                .hasLength(1);
        assertWithMessage("Acceptable tokens differs from expected")
                .that(actual)
                .isEqualTo(expected);
    }

}

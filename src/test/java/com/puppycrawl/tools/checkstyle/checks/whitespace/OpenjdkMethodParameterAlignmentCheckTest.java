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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OpenjdkMethodParameterAlignmentCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OpenjdkMethodParameterAlignmentCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/openjdkmethodparameteralignment";
    }

    @Test
    public void testGetRequiredTokens() {
        final OpenjdkMethodParameterAlignmentCheck checkObj =
                new OpenjdkMethodParameterAlignmentCheck();
        assertWithMessage("OpenjdkMethodParameterAlignmentCheck#getRequiredTokens should return "
                + "empty array by default")
                .that(checkObj.getRequiredTokens())
                .isEmpty();
    }

    @Test
    public void testGetAcceptableTokens() {
        final OpenjdkMethodParameterAlignmentCheck checkObj =
                new OpenjdkMethodParameterAlignmentCheck();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final OpenjdkMethodParameterAlignmentCheck checkObj =
                new OpenjdkMethodParameterAlignmentCheck();
        assertWithMessage("Default tokens are invalid")
                .that(checkObj.getDefaultTokens())
                .isEqualTo(checkObj.getAcceptableTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "31:34: " + getCheckMessage(MSG_KEY),
            "35:35: " + getCheckMessage(MSG_KEY),
            "41:32: " + getCheckMessage(MSG_KEY),
            "41:39: " + getCheckMessage(MSG_KEY),
            "72:53: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentDefault.java"), expected);
    }

    @Test
    public void testConstructors() throws Exception {
        final String[] expected = {
            "21:62: " + getCheckMessage(MSG_KEY),
            "32:22: " + getCheckMessage(MSG_KEY),
            "48:22: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentConstructors.java"), expected);
    }

    @Test
    public void testParameterTypes() throws Exception {
        final String[] expected = {
            "16:49: " + getCheckMessage(MSG_KEY),
            "22:40: " + getCheckMessage(MSG_KEY),
            "27:45: " + getCheckMessage(MSG_KEY),
            "33:36: " + getCheckMessage(MSG_KEY),
            "38:57: " + getCheckMessage(MSG_KEY),
            "43:25: " + getCheckMessage(MSG_KEY),
            "48:36: " + getCheckMessage(MSG_KEY),
            "53:34: " + getCheckMessage(MSG_KEY),
            "68:61: " + getCheckMessage(MSG_KEY),
            "73:47: " + getCheckMessage(MSG_KEY),
            "85:36: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentParameterTypes.java"), expected);
    }

    @Test
    public void testTokens() throws Exception {
        final String[] expected = {
            "17:24: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentTokens.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "20:26: " + getCheckMessage(MSG_KEY),
            "24:28: " + getCheckMessage(MSG_KEY),
        };

        final String filename =
                "compact/InputOpenjdkMethodParameterAlignmentCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}

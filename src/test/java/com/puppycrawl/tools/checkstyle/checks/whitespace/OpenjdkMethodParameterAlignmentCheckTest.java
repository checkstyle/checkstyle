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
import static com.puppycrawl.tools.checkstyle.checks.whitespace.OpenjdkMethodParameterAlignmentCheck.MSG_WRAP;

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
            "30:27: " + getCheckMessage(MSG_KEY),
            "35:28: " + getCheckMessage(MSG_KEY),
            "40:25: " + getCheckMessage(MSG_KEY),
            "58:38: " + getCheckMessage(MSG_WRAP),
            "72:32: " + getCheckMessage(MSG_KEY),
            "79:37: " + getCheckMessage(MSG_WRAP),
            "85:39: " + getCheckMessage(MSG_WRAP),
            "92:43: " + getCheckMessage(MSG_WRAP),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentDefault.java"), expected);
    }

    @Test
    public void testConstructors() throws Exception {
        final String[] expected = {
            "20:54: " + getCheckMessage(MSG_KEY),
            "31:15: " + getCheckMessage(MSG_KEY),
            "47:15: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentConstructors.java"), expected);
    }

    @Test
    public void testParameterTypes() throws Exception {
        final String[] expected = {
            "15:30: " + getCheckMessage(MSG_KEY),
            "20:33: " + getCheckMessage(MSG_KEY),
            "26:38: " + getCheckMessage(MSG_KEY),
            "32:29: " + getCheckMessage(MSG_KEY),
            "37:36: " + getCheckMessage(MSG_KEY),
            "42:18: " + getCheckMessage(MSG_KEY),
            "47:27: " + getCheckMessage(MSG_KEY),
            "52:29: " + getCheckMessage(MSG_KEY),
            "67:43: " + getCheckMessage(MSG_KEY),
            "73:28: " + getCheckMessage(MSG_KEY),
            "78:40: " + getCheckMessage(MSG_KEY),
            "90:29: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentParameterTypes.java"), expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "21:25: " + getCheckMessage(MSG_WRAP),
            "27:27: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentTabs.java"), expected);
    }

    @Test
    public void testTokens() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentTokens.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "19:19: " + getCheckMessage(MSG_KEY),
            "24:21: " + getCheckMessage(MSG_KEY),
        };

        final String filename =
                "compact/InputOpenjdkMethodParameterAlignmentCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}

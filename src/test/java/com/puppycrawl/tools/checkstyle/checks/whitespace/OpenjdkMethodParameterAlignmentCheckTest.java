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
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        assertWithMessage("Required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final OpenjdkMethodParameterAlignmentCheck checkObj =
                new OpenjdkMethodParameterAlignmentCheck();
        assertWithMessage("Acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(checkObj.getRequiredTokens());
    }

    @Test
    public void testGetDefaultTokens() {
        final OpenjdkMethodParameterAlignmentCheck checkObj =
                new OpenjdkMethodParameterAlignmentCheck();
        assertWithMessage("Default tokens are invalid")
                .that(checkObj.getDefaultTokens())
                .isEqualTo(checkObj.getRequiredTokens());
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "29:5: " + getCheckMessage(MSG_KEY),
            "34:5: " + getCheckMessage(MSG_KEY),
            "39:5: " + getCheckMessage(MSG_KEY),
            "57:5: " + getCheckMessage(MSG_WRAP),
            "71:5: " + getCheckMessage(MSG_KEY),
            "78:5: " + getCheckMessage(MSG_WRAP),
            "84:5: " + getCheckMessage(MSG_WRAP),
            "91:5: " + getCheckMessage(MSG_WRAP),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentDefault.java"), expected);
    }

    @Test
    public void testConstructors() throws Exception {
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_KEY),
            "30:9: " + getCheckMessage(MSG_KEY),
            "46:9: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentConstructors.java"), expected);
    }

    @Test
    public void testParameterTypes() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_KEY),
            "19:5: " + getCheckMessage(MSG_KEY),
            "25:5: " + getCheckMessage(MSG_KEY),
            "30:5: " + getCheckMessage(MSG_KEY),
            "36:5: " + getCheckMessage(MSG_KEY),
            "41:5: " + getCheckMessage(MSG_KEY),
            "46:5: " + getCheckMessage(MSG_KEY),
            "51:5: " + getCheckMessage(MSG_KEY),
            "62:5: " + getCheckMessage(MSG_WRAP),
            "68:5: " + getCheckMessage(MSG_KEY),
            "73:5: " + getCheckMessage(MSG_KEY),
            "79:5: " + getCheckMessage(MSG_KEY),
            "84:5: " + getCheckMessage(MSG_KEY),
            "96:9: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentParameterTypes.java"), expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_WRAP),
            "27:9: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOpenjdkMethodParameterAlignmentTabs.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY),
            "23:1: " + getCheckMessage(MSG_KEY),
        };

        final String filename =
                "compact/InputOpenjdkMethodParameterAlignmentCompactSourceFile.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}

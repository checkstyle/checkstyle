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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class IllegalImportCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/illegalimport";
    }

    @Test
    public void testGetRequiredTokens() {
        final IllegalImportCheck checkObj = new IllegalImportCheck();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testWithSupplied()
            throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "28:1: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "32:1: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault1.java"), expected);
    }

    @Test
    public void testWithDefault()
            throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault2.java"), expected);
    }

    @Test
    public void testCustomSunPackageWithRegexp()
            throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY, "sun.reflect.*"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalImportDefault.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalImportCheck testCheckObject =
                new IllegalImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testIllegalClasses()
            throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, "java.sql.Connection"),
            "20:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "33:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault3.java"), expected);
    }

    @Test
    public void testIllegalClassesStarImport()
            throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "20:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "33:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault4.java"), expected);
    }

    @Test
    public void testIllegalPackagesRegularExpression()
            throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "18:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "22:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "39:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "40:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "41:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault5.java"), expected);
    }

    @Test
    public void testIllegalClassesRegularExpression()
            throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "18:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "22:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault6.java"), expected);
    }

    @Test
    public void testIllegalPackagesAndClassesRegularExpression()
            throws Exception {
        final String[] expected = {
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "18:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "22:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "35:1: " + getCheckMessage(MSG_KEY, "java.awt.Component"),
            "36:1: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "37:1: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "38:1: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "39:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "40:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "41:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault7.java"), expected);
    }

}

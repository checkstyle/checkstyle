///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/illegalimport";
    }

    @Test
    public void getRequiredTokens() {
        final IllegalImportCheck checkObj = new IllegalImportCheck();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.MODULE_IMPORT,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void withSupplied()
            throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "27:1: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "31:1: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault1.java"), expected);
    }

    @Test
    public void withDefault()
            throws Exception {
        final String[] expected = {};
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault2.java"), expected);
    }

    @Test
    public void customSunPackageWithRegexp()
            throws Exception {
        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_KEY, "sun.reflect.*"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalImportDefault.java"), expected);
    }

    @Test
    public void getAcceptableTokens() {
        final IllegalImportCheck testCheckObject =
                new IllegalImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.MODULE_IMPORT,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void illegalClasses()
            throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY, "java.sql.Connection"),
            "19:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "32:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault3.java"), expected);
    }

    @Test
    public void illegalClassesStarImport()
            throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "19:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "32:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault4.java"), expected);
    }

    @Test
    public void illegalPackagesRegularExpression()
            throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "20:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "38:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "39:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "40:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault5.java"), expected);
    }

    @Test
    public void illegalClassesRegularExpression()
            throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault6.java"), expected);
    }

    @Test
    public void illegalPackagesAndClassesRegularExpression()
            throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "20:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "34:1: " + getCheckMessage(MSG_KEY, "java.awt.Component"),
            "35:1: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "36:1: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "37:1: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "38:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "39:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "40:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verifyWithInlineConfigParser(
                getPath("InputIllegalImportDefault7.java"), expected);
    }

    @Test
    public void illegalModulesNoRegex() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "java.base"),
            "17:1: " + getCheckMessage(MSG_KEY, "java.logging"),
            "20:1: " + getCheckMessage(MSG_KEY, "java.sql.Connection"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalImportModuleNoRegex.java"), expected);
    }

    @Test
    public void illegalModulesWithRegex() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "java.base"),
            "17:1: " + getCheckMessage(MSG_KEY, "java.logging"),
            "20:1: " + getCheckMessage(MSG_KEY, "java.sql.Connection"),
            "21:1: " + getCheckMessage(MSG_KEY, "java.sql.Driver"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputIllegalImportModuleWithRegex.java"), expected);
    }

}

////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testWithSupplied()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java.io");
        final String[] expected = {
            "5:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "19:1: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "23:1: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault1.java"), expected);
    }

    @Test
    public void testWithDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalImportCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("InputIllegalImportDefault2.java"), expected);
    }

    @Test
    public void testCustomSunPackageWithRegexp()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("regexp", "true");
        checkConfig.addAttribute("illegalPkgs", "sun.reflect");
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "sun.reflect.*"),
        };
        verify(checkConfig, getNonCompilablePath("InputIllegalImportDefault.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final IllegalImportCheck testCheckObject =
                new IllegalImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.IMPORT, TokenTypes.STATIC_IMPORT};

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testIllegalClasses()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalClasses", "java.sql.Connection");
        checkConfig.addAttribute("illegalPkgs", "org.junit.jupiter.api");
        final String[] expected = {
            "7:1: " + getCheckMessage(MSG_KEY, "java.sql.Connection"),
            "11:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "24:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault3.java"), expected);
    }

    @Test
    public void testIllegalClassesStarImport()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalClasses", "java.io.*");
        checkConfig.addAttribute("illegalPkgs", "org.junit.jupiter.api");
        final String[] expected = {
            "5:1: " + getCheckMessage(MSG_KEY, "java.io.*"),
            "11:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
            "24:1: " + getCheckMessage(MSG_KEY, "org.junit.jupiter.api.*"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault4.java"), expected);
    }

    @Test
    public void testIllegalPackagesRegularExpression()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java\\.util");
        checkConfig.addAttribute("regexp", "true");
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "9:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "12:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "13:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "30:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "31:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "32:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault5.java"), expected);
    }

    @Test
    public void testIllegalClassesRegularExpression()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "");
        checkConfig.addAttribute("illegalClasses", "^java\\.util\\.(List|Arrays)");
        checkConfig.addAttribute("regexp", "true");
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "9:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "13:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault6.java"), expected);
    }

    @Test
    public void testIllegalPackagesAndClassesRegularExpression()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(IllegalImportCheck.class);
        checkConfig.addAttribute("illegalPkgs", "java\\.util");
        checkConfig.addAttribute("illegalClasses",
                "^java\\.awt\\..*");
        checkConfig.addAttribute("regexp", "true");
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "9:1: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "12:1: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "13:1: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "26:1: " + getCheckMessage(MSG_KEY, "java.awt.Component"),
            "27:1: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "28:1: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "29:1: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "30:1: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "31:1: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "32:1: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
        };
        verify(checkConfig, getPath("InputIllegalImportDefault7.java"), expected);
    }

}

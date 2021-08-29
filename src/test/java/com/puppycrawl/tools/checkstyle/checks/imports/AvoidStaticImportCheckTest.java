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

import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidStaticImportCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/avoidstaticimport";
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidStaticImportCheck checkObj = new AvoidStaticImportCheck();
        final int[] expected = {TokenTypes.STATIC_IMPORT};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testDefaultOperation()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStaticImportCheck.class);
        final String[] expected = {
            "26:27: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "28:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "30:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "31:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "32:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "33:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "34:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testStarExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStaticImportCheck.class);
        checkConfig.addProperty("excludes", "java.io.File.*, sun.net.ftpclient.FtpClient.*");
        // allow the "java.io.File.*" AND "sun.net.ftpclient.FtpClient.*" star imports
        final String[] expected = {
            "28:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "31:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "32:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "33:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "34:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault2.java"), expected);
    }

    @Test
    public void testMemberExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStaticImportCheck.class);
        checkConfig.addProperty("excludes", "java.io.File.listRoots, java.lang.Math.E");
        // allow the java.io.File.listRoots and java.lang.Math.E member imports
        final String[] expected = {
            "28:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "30:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "32:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "33:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "34:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault3.java"), expected);
    }

    @Test
    public void testBogusMemberExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStaticImportCheck.class);

        // should NOT mask anything
        checkConfig.addProperty(
            "excludes",
            "java.io.File.listRoots.listRoots, javax.swing.WindowConstants, javax.swing.*,"
            + " sun.net.ftpclient.FtpClient.*FtpClient, sun.net.ftpclient.FtpClientjunk,"
            + " java.io.File.listRootsmorejunk");
        final String[] expected = {
            "28:27: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "30:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "31:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "32:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "33:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "34:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "35:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "36:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault4.java"), expected);
    }

    @Test
    public void testInnerClassMemberExcludesStar()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AvoidStaticImportCheck.class);

        // should mask com.puppycrawl.tools.checkstyle.imports.avoidstaticimport.
        // InputAvoidStaticImportNestedClass.InnerClass.one
        checkConfig.addProperty(
            "excludes",
            "com.puppycrawl.tools.checkstyle.checks.imports."
                + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.*");
        final String[] expected = {
            "27:27: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "29:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "30:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "31:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "32:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "33:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "34:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault5.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidStaticImportCheck testCheckObject =
                new AvoidStaticImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.STATIC_IMPORT};

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}


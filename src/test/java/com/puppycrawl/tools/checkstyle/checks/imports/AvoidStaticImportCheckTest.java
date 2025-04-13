///
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
///

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefaultOperation()
            throws Exception {
        final String[] expected = {
            "24:27: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "26:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "29:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "30:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "31:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "32:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testStarExcludes()
            throws Exception {
        // allow the "java.io.File.*" AND "sun.net.ftpclient.FtpClient.*" star imports
        final String[] expected = {
            "26:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "30:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "31:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "32:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault2.java"), expected);
    }

    @Test
    public void testMemberExcludes()
            throws Exception {
        // allow the java.io.File.listRoots and java.lang.Math.E member imports
        final String[] expected = {
            "26:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "30:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "31:113: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass"),
            "32:124: " + getCheckMessage(MSG_KEY,
                    "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "avoidstaticimport.InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidStaticImportDefault3.java"), expected);
    }

    @Test
    public void testBogusMemberExcludes()
            throws Exception {
        // should NOT mask anything
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
                getPath("InputAvoidStaticImportDefault4.java"), expected);
    }

    @Test
    public void testInnerClassMemberExcludesStar()
            throws Exception {
        // should mask com.puppycrawl.tools.checkstyle.imports.avoidstaticimport.
        // InputAvoidStaticImportNestedClass.InnerClass.one
        final String[] expected = {
            "25:27: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "27:42: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "28:27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "29:27: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "30:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.E"),
            "31:29: " + getCheckMessage(MSG_KEY, "java.lang.Math.sqrt"),
            "32:113: " + getCheckMessage(MSG_KEY,
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

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

}


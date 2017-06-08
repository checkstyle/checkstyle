////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AvoidStaticImportCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "imports" + File.separator + "avoidstaticimport" + File.separator + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final AvoidStaticImportCheck checkObj = new AvoidStaticImportCheck();
        final int[] expected = {TokenTypes.STATIC_IMPORT};
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    @Test
    public void testDefaultOperation()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStaticImportCheck.class);
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "29: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass"),
            "30: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass.one"),
        };

        verify(checkConfig, getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testStarExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStaticImportCheck.class);
        checkConfig.addAttribute("excludes", "java.io.File.*,sun.net.ftpclient.FtpClient.*");
        // allow the "java.io.File.*" AND "sun.net.ftpclient.FtpClient.*" star imports
        final String[] expected = {
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "29: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass"),
            "30: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verify(checkConfig, getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testMemberExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStaticImportCheck.class);
        checkConfig.addAttribute("excludes", "java.io.File.listRoots");
        // allow the java.io.File.listRoots member imports
        final String[] expected = {
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "29: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass"),
            "30: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verify(checkConfig, getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testBogusMemberExcludes()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStaticImportCheck.class);

        // should NOT mask anything
        checkConfig.addAttribute(
            "excludes",
            "java.io.File.listRoots.listRoots, javax.swing.WindowConstants, javax.swing.*,"
            + "sun.net.ftpclient.FtpClient.*FtpClient, sun.net.ftpclient.FtpClientjunk,"
            + " java.io.File.listRootsmorejunk");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "29: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass"),
            "30: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass.one"),
        };
        verify(checkConfig, getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testInnerClassMemberExcludesStar()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(AvoidStaticImportCheck.class);

        // should mask com.puppycrawl.tools.checkstyle.imports.InputAvoidStaticImportNestedClass.
        // InnerClass.one
        checkConfig.addAttribute(
            "excludes",
            "com.puppycrawl.tools.checkstyle.checks.imports."
                + "InputAvoidStaticImportNestedClass.InnerClass.*");
        final String[] expected = {
            "23: " + getCheckMessage(MSG_KEY, "java.io.File.listRoots"),
            "25: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "26: " + getCheckMessage(MSG_KEY, "javax.swing.WindowConstants.*"),
            "27: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            "28: " + getCheckMessage(MSG_KEY, "java.io.File.pathSeparator"),
            "29: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "InputAvoidStaticImportNestedClass.InnerClass"),
        };
        verify(checkConfig, getPath("InputAvoidStaticImportDefault.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AvoidStaticImportCheck testCheckObject =
                new AvoidStaticImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {TokenTypes.STATIC_IMPORT};

        assertArrayEquals(expected, actual);
    }
}

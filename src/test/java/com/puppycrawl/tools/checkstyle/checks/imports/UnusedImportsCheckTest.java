////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UnusedImportsCheckTest extends BaseCheckTestSupport {
    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:45: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.imports.InputImportBug"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "22:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            //"29:8: Unused import - java.awt.Component.", // Should be detected
            "30:8: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "31:8: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "32:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "33:8: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "34:8: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "35:8: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
            "37:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Checker"),
            "38:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.CheckerTest"),
            "39:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport"),
            "40:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Definitions"),
            "41:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Input15Extensions"),
            "42:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.ConfigurationLoaderTest"),
            "43:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.PackageNamesLoader"),
            "44:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultConfiguration"),
            "45:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultLogger"),
        };
        verify(checkConfig, getPath("imports" + File.separator
                + "InputUnusedImportsCheck.java"), expected);
    }

    @Test
    public void testProcessJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        checkConfig.addAttribute("processJavadoc", "true");
        final String[] expected = {
            "8:45: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.imports.InputImportBug"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "22:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            //"29:8: Unused import - java.awt.Component.", // Should be detected
            "32:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "45:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultLogger"),
        };
        verify(checkConfig, getPath("imports" + File.separator
                + "InputUnusedImportsCheck.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "package-info.java"), expected);
    }

    @Test
    public void testBug() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {};
        verify(checkConfig, getPath("imports" + File.separator
                + "InputImportBug.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        UnusedImportsCheck testCheckObject =
                new UnusedImportsCheck();
        int[] actual = testCheckObject.getRequiredTokens();
        int[] expected = new int[]{
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Definitions that may contain Javadoc...
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetAcceptableTokens() {
        UnusedImportsCheck testCheckObject =
                new UnusedImportsCheck();
        int[] actual = testCheckObject.getAcceptableTokens();
        int[] expected = new int[]{
            TokenTypes.IDENT,
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            // Definitions that may contain Javadoc...
            TokenTypes.PACKAGE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
        };

        assertArrayEquals(expected, actual);
    }

    @Test
    public void testFileInUnnamedPackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "3:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "4:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/imports/"
                + "InputRedundantImportCheck_UnnamedPackage.java").getCanonicalPath(), expected);
    }

}

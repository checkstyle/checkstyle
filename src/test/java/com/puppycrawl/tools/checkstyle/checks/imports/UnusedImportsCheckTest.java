////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class UnusedImportsCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "imports" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "imports" + File.separator + filename);
    }

    @Test
    public void testWithoutProcessJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        checkConfig.addAttribute("processJavadoc", "false");
        final String[] expected = {
            "8:45: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports.InputImportBug"),
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
            "39:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.BaseFileSetCheckTestSupport"),
            "40:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Definitions"),
            "41:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.Input15Extensions"),
            "42:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.ConfigurationLoaderTest"),
            "43:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.PackageNamesLoader"),
            "44:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.DefaultConfiguration"),
            "45:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultLogger"),
        };
        verify(checkConfig, getPath("InputUnusedImports.java"), expected);
    }

    @Test
    public void testProcessJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:45: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports.InputImportBug"),
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
        verify(checkConfig, getPath("InputUnusedImports.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    public void testBug() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputImportBug.java"), expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedImportsCheck testCheckObject =
                new UnusedImportsCheck();
        final int[] actual = testCheckObject.getRequiredTokens();
        final int[] expected = {
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
        final UnusedImportsCheck testCheckObject =
                new UnusedImportsCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {
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
            "5:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "6:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verify(checkConfig, getNonCompilablePath("InputRedundantImport_UnnamedPackage.java"),
            expected);
    }

}

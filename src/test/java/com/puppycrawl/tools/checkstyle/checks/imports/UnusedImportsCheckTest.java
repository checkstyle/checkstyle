////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnusedImportsCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/unusedimports";
    }

    @Test
    public void testReferencedStateIsCleared() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String inputWithoutWarnings = getPath("InputUnusedImportsWithoutWarnings.java");
        final String inputWithWarnings = getPath("InputUnusedImportsCheckClearState.java");
        final List<String> expectedFirstInput = Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = Arrays.asList(
                "3:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
                "4:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
                "5:8: " + getCheckMessage(MSG_KEY, "java.util.Set")
        );
        final File[] inputsWithWarningsFirst =
            {new File(inputWithWarnings), new File(inputWithoutWarnings)};
        final File[] inputsWithoutWarningFirst =
            {new File(inputWithoutWarnings), new File(inputWithWarnings)};

        verify(createChecker(checkConfig), inputsWithWarningsFirst, ImmutableMap.of(
                inputWithoutWarnings, expectedFirstInput,
                inputWithWarnings, expectedSecondInput));
        verify(createChecker(checkConfig), inputsWithoutWarningFirst, ImmutableMap.of(
                inputWithoutWarnings, expectedFirstInput,
                inputWithWarnings, expectedSecondInput));
    }

    @Test
    public void testWithoutProcessJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        checkConfig.addAttribute("processJavadoc", "false");
        final String[] expected = {
            "8:16: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks."
                + "imports.unusedimports.InputUnusedImportsBug"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "22:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            // "29:8: Unused import - java.awt.Component.", // Should be detected
            "30:8: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "31:8: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "32:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "33:8: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "34:8: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "35:8: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
            "37:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Checker"),
            "38:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.CheckerTest"),
            "39:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.Definitions"),
            "40:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks.imports.unusedimports."
                        + "InputUnusedImports15Extensions"),
            "41:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.ConfigurationLoaderTest"),
            "42:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.PackageNamesLoader"),
            "43:8: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.DefaultConfiguration"),
            "44:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultLogger"),
        };
        verify(checkConfig, getPath("InputUnusedImports.java"), expected);
    }

    @Test
    public void testProcessJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "8:16: " + getCheckMessage(MSG_KEY,
                "com.puppycrawl.tools.checkstyle.checks."
                        + "imports.unusedimports.InputUnusedImportsBug"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "22:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            // "29:8: Unused import - java.awt.Component.", // Should be detected
            "32:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "44:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.DefaultLogger"),
        };
        verify(checkConfig, getPath("InputUnusedImports.java"), expected);
    }

    @Test
    public void testProcessJavadocWithLinkTag() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUnusedImportsWithValueTag.java"), expected);
    }

    @Test
    public void testProcessJavadocWithBlockTagContainingMethodParameters() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUnusedImportsWithBlockMethodParameters.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputUnusedImportsAnnotations.java"), expected);
    }

    @Test
    public void testBug() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUnusedImportsBug.java"), expected);
    }

    @Test
    public void testNewlinesInsideTags() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputUnusedImportsWithNewlinesInsideTags.java"), expected);
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

        assertArrayEquals(expected, actual, "Default required tokens are invalid");
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

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testFileInUnnamedPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "5:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "6:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verify(checkConfig, getNonCompilablePath("InputUnusedImportsFileInUnnamedPackage.java"),
            expected);
    }

    @Test
    public void testImportsFromJavaLang() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "3:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "4:8: " + getCheckMessage(MSG_KEY, "java.lang.Math"),
            "5:8: " + getCheckMessage(MSG_KEY, "java.lang.Class"),
            "6:8: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "7:8: " + getCheckMessage(MSG_KEY, "java.lang.Runnable"),
            "8:8: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "9:8: " + getCheckMessage(MSG_KEY, "java.lang.ProcessBuilder"),
            "10:8: " + getCheckMessage(MSG_KEY, "java.lang.Double"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.Integer"),
            "12:8: " + getCheckMessage(MSG_KEY, "java.lang.Float"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.lang.Short"),
        };
        verify(checkConfig, getPath("InputUnusedImportsFromJavaLang.java"), expected);
    }

    @Test
    public void testImportsJavadocQualifiedName() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UnusedImportsCheck.class);
        final String[] expected = {
            "4:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verify(checkConfig, getPath("InputUnusedImportsJavadocQualifiedName.java"), expected);
    }

}

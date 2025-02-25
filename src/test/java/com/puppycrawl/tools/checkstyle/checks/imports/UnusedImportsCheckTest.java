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
import static com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck.MSG_KEY;

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
                "10:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
                "11:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
                "12:8: " + getCheckMessage(MSG_KEY, "java.util.Set")
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
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY,
                "com.google.errorprone.annotations."
                + "concurrent.GuardedBy"),
            "15:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "18:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "21:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "24:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "26:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "31:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            // "33:8: Unused import - java.awt.Component.", // Should be detected
            "34:8: " + getCheckMessage(MSG_KEY, "java.awt.Graphics2D"),
            "35:8: " + getCheckMessage(MSG_KEY, "java.awt.HeadlessException"),
            "36:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "37:8: " + getCheckMessage(MSG_KEY, "java.util.Date"),
            "38:8: " + getCheckMessage(MSG_KEY, "java.util.Calendar"),
            "39:8: " + getCheckMessage(MSG_KEY, "java.util.BitSet"),
            "41:8: " + getCheckMessage(MSG_KEY, "com.google.errorprone."
                    + "annotations.CheckReturnValue"),
            "42:8: " + getCheckMessage(MSG_KEY, "com.google.errorprone."
                    + "annotations.CanIgnoreReturnValue"),
            "43:8: " + getCheckMessage(MSG_KEY, "com.google.errorprone."
                    + "annotations.CompatibleWith"),
            "44:8: " + getCheckMessage(MSG_KEY,
                "com.google.errorprone.annotations.concurrent."
                        + "LazyInit"),
            "45:8: " + getCheckMessage(MSG_KEY,
                "com.google.errorprone.annotations.DoNotCall"),
            "46:8: " + getCheckMessage(MSG_KEY,
                "com.google.errorprone.annotations.CompileTimeConstant"),
            "47:8: " + getCheckMessage(MSG_KEY,
                "com.google.errorprone.annotations.FormatMethod"),
            "48:8: " + getCheckMessage(MSG_KEY, "com.google.errorprone.annotations.FormatString"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImports2.java"), expected);
    }

    @Test
    public void testProcessJavadoc() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY,
                    "com.google.errorprone.annotations."
                    + "concurrent.GuardedBy"),
            "15:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "18:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "21:8: " + getCheckMessage(MSG_KEY, "java.util.Enumeration"),
            "24:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
            "26:8: " + getCheckMessage(MSG_KEY, "javax.swing.BorderFactory"),
            "31:15: " + getCheckMessage(MSG_KEY, "java.io.File.createTempFile"),
            // "30:8: Unused import - java.awt.Component.", // Should be detected
            "36:8: " + getCheckMessage(MSG_KEY, "java.awt.Label"),
            "48:8: " + getCheckMessage(MSG_KEY, "com.google.errorprone.annotations.ForOverride"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImports.java"), expected);
    }

    @Test
    public void testProcessJavadocWithLinkTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsWithValueTag.java"), expected);
    }

    @Test
    public void testProcessJavadocWithBlockTagContainingMethodParameters() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsWithBlockMethodParameters.java"), expected);
    }

    @Test
    public void testAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedImportsAnnotations.java"), expected);
    }

    @Test
    public void testArrayRef() throws Exception {
        final String[] expected = {
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.ArrayList"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsArrayRef.java"), expected);
    }

    @Test
    public void testBug() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsBug.java"), expected);
    }

    @Test
    public void testNewlinesInsideTags() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsWithNewlinesInsideTags.java"), expected);
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.OBJBLOCK,
            TokenTypes.SLIST,
        };

        assertWithMessage("Default required tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
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
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.OBJBLOCK,
            TokenTypes.SLIST,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testFileInUnnamedPackage() throws Exception {
        final String[] expected = {
            "12:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedImportsFileInUnnamedPackage.java"),
            expected);
    }

    @Test
    public void testImportsFromJavaLang() throws Exception {
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.Math"),
            "12:8: " + getCheckMessage(MSG_KEY, "java.lang.Class"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.lang.Runnable"),
            "15:8: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "16:8: " + getCheckMessage(MSG_KEY, "java.lang.ProcessBuilder"),
            "17:8: " + getCheckMessage(MSG_KEY, "java.lang.Double"),
            "18:8: " + getCheckMessage(MSG_KEY, "java.lang.Integer"),
            "19:8: " + getCheckMessage(MSG_KEY, "java.lang.Float"),
            "20:8: " + getCheckMessage(MSG_KEY, "java.lang.Short"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsFromJavaLang.java"), expected);
    }

    @Test
    public void testImportsJavadocQualifiedName() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsJavadocQualifiedName.java"), expected);
    }

    @Test
    public void testSingleWordPackage() throws Exception {
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_KEY, "module"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedImportsSingleWordPackage.java"),
                expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "19:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToolBar"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedImportsRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testShadowedImports() throws Exception {
        final String[] expected = {
            "12:8: " + getCheckMessage(MSG_KEY, "java.util.Map"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.util.Set"),
            "16:8: " + getCheckMessage(MSG_KEY, "com.puppycrawl.tools.checkstyle.checks.imports."
                    + "unusedimports.InputUnusedImportsShadowed"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsShadowed.java"), expected);
    }

    @Test
    public void testUnusedImports3() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY, "java.awt.Rectangle"),
            "13:8: " + getCheckMessage(MSG_KEY, "java.awt.event.KeyEvent"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImports3.java"), expected);
    }

    @Test
    public void testStateIsClearedOnBeginTreeCollect() throws Exception {
        final String file1 = getNonCompilablePath(
                "InputUnusedImportsRecordsAndCompactCtors.java");
        final String file2 = getNonCompilablePath(
                "InputUnusedImportsSingleWordPackage.java");
        final List<String> expectedFirstInput = List.of(
            "19:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToolBar"),
            "20:8: " + getCheckMessage(MSG_KEY, "javax.swing.JToggleButton")
        );
        final List<String> expectedSecondInput = List.of(
            "10:8: " + getCheckMessage(MSG_KEY, "module")
        );
        verifyWithInlineConfigParser(file1, file2, expectedFirstInput, expectedSecondInput);
    }

    @Test
    public void testStaticMethodRefImports() throws Exception {
        final String[] expected = {
            "26:15: " + getCheckMessage(MSG_KEY, "java.lang.String.format"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.util.Arrays.sort"),
            "28:15: " + getCheckMessage(MSG_KEY, "java.util.List.of"),
            "29:15: " + getCheckMessage(MSG_KEY, "java.util.Collections.emptyMap"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsFromStaticMethodRef.java"), expected);
    }

    @Test
    public void testStaticMethodRefImportsExtended() throws Exception {
        final String[] expected = {
            "17:8: " + getCheckMessage(MSG_KEY, "java.util.Objects"),
            "18:15: " + getCheckMessage(MSG_KEY, "java.util.Arrays.toString"),
            "19:15: " + getCheckMessage(MSG_KEY, "java.util.Arrays.asList"),
            "20:15: " + getCheckMessage(MSG_KEY, "java.lang.Integer.parseInt"),
            "21:15: " + getCheckMessage(MSG_KEY, "java.util.Collections.emptyList"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsFromStaticMethodRefExtended.java"), expected);
    }

    @Test
    public void testStaticMethodRefImportsWithJavadocDisabled() throws Exception {
        final String[] expected = {
            "24:8: " + getCheckMessage(MSG_KEY, "java.util.Arrays"),
            "25:15: " + getCheckMessage(MSG_KEY, "java.lang.Integer.parseInt"),
            "26:15: " + getCheckMessage(MSG_KEY, "java.lang.String.format"),
            "27:15: " + getCheckMessage(MSG_KEY, "java.util.List.of"),
            "28:15: " + getCheckMessage(MSG_KEY, "java.util.Collections.emptyMap"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsFromStaticMethodRefJavadocDisabled.java"), expected);
    }

    @Test
    public void testStaticMethodRefImportsInDocsOnly() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY, "java.lang.Integer"),
            "12:15: " + getCheckMessage(MSG_KEY, "java.util.Collections.emptyEnumeration"),
            "13:15: " + getCheckMessage(MSG_KEY, "java.util.Arrays.sort"),
            "14:15: " + getCheckMessage(MSG_KEY, "java.util.Collections.shuffle"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsFromStaticMethodRefInDocsOnly.java"), expected);
    }

    @Test
    public void testUnusedImportsJavadocAboveComments() throws Exception {
        final String[] expected = {
            "11:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsJavadocAboveComments.java"), expected);
    }

    @Test
    public void testImportJavaLinkTag() throws Exception {
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_KEY, "java.util.List"),
            "12:8: " + getCheckMessage(MSG_KEY, "java.util.TreeSet"),

        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsWithLinkTag.java"), expected);

    }

    @Test
    public void testImportJavaLinkTagWithMethod() throws Exception {
        final String[] expected = {
            "10:8: " + getCheckMessage(MSG_KEY, "java.util.Collections"),
            "12:8: " + getCheckMessage(MSG_KEY, "java.util.Set"),
            "14:8: " + getCheckMessage(MSG_KEY, "java.util.PriorityQueue"),
            "16:8: " + getCheckMessage(MSG_KEY, "java.util.Queue"),
            "20:8: " + getCheckMessage(MSG_KEY, "java.util.LinkedList"),
            "24:8: " + getCheckMessage(MSG_KEY, "java.time.LocalDateTime"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedImportsWithLinkAndMethodTag.java"), expected);

    }

}

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
import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_DUPLICATE;
import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_LANG;
import static com.puppycrawl.tools.checkstyle.checks.imports.RedundantImportCheck.MSG_SAME;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RedundantImportCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/redundantimport";
    }

    @Test
    public void testGetRequiredTokens() {
        final RedundantImportCheck checkObj = new RedundantImportCheck();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testStateIsClearedOnBeginTree1()
            throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(RedundantImportCheck.class);
        final String inputWithWarnings = getPath("InputRedundantImportCheckClearState.java");
        final String inputWithoutWarnings = getPath("InputRedundantImportWithoutWarnings.java");
        final List<String> expectedFirstInput = Arrays.asList(
            "10:1: " + getCheckMessage(MSG_DUPLICATE, 9, "java.util.Arrays.asList"),
            "13:1: " + getCheckMessage(MSG_DUPLICATE, 12, "java.util.List")
        );
        final List<String> expectedSecondInput = Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);
        final File[] inputs = {new File(inputWithWarnings), new File(inputWithoutWarnings)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            inputWithWarnings, expectedFirstInput,
            inputWithoutWarnings, expectedSecondInput));
    }

    @Test
    public void testWithChecker()
            throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_SAME,
                "com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.*"),
            "10:1: " + getCheckMessage(MSG_SAME,
                "com.puppycrawl.tools.checkstyle.checks.imports.redundantimport."
                        + "InputRedundantImportBug"),
            "12:1: " + getCheckMessage(MSG_LANG, "java.lang.*"),
            "13:1: " + getCheckMessage(MSG_LANG, "java.lang.String"),
            "16:1: " + getCheckMessage(MSG_DUPLICATE, 15, "java.util.List"),
            "28:1: " + getCheckMessage(MSG_DUPLICATE, 27, "javax.swing.WindowConstants.*"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantImportWithChecker.java"), expected);
    }

    @Test
    public void testUnnamedPackage()
            throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_DUPLICATE, 9, "java.util.List"),
            "12:1: " + getCheckMessage(MSG_LANG, "java.lang.String"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantImport_UnnamedPackage.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RedundantImportCheck testCheckObject =
                new RedundantImportCheck();
        final int[] actual = testCheckObject.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.PACKAGE_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testBeginTreePackage() throws Exception {
        final String file1 = getPath("InputRedundantImportCheckClearState.java");
        final String file2 = getPath("InputRedundantImportWithoutPackage.java");
        final List<String> expectedFirstInput = Arrays.asList(
            "10:1: " + getCheckMessage(MSG_DUPLICATE, 9, "java.util.Arrays.asList"),
            "13:1: " + getCheckMessage(MSG_DUPLICATE, 12, "java.util.List")
        );
        final List<String> expectedSecondInput = Arrays.asList(
            "9:1: " + getCheckMessage(MSG_LANG, "java.lang.*"),
            "10:1: " + getCheckMessage(MSG_LANG, "java.lang.String")
        );
        verifyWithInlineConfigParser(file1, file2, expectedFirstInput, expectedSecondInput);
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.imports.ModuleImportOrderCheck.MSG_ORDERING_LEX;
import static com.puppycrawl.tools.checkstyle.checks.imports.ModuleImportOrderCheck.MSG_POSITION;
import static com.puppycrawl.tools.checkstyle.checks.imports.ModuleImportOrderCheck.MSG_SEPARATION;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModuleImportOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/moduleimportorder";
    }

    @Test
    public void testGetTokens() {
        final ModuleImportOrderCheck checkObj = new ModuleImportOrderCheck();
        final int[] expected = {
            TokenTypes.IMPORT,
            TokenTypes.STATIC_IMPORT,
            TokenTypes.MODULE_IMPORT,
        };
        assertWithMessage("Default tokens differs from expected")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
        assertWithMessage("Acceptable tokens differs from expected")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Required tokens differs from expected")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderDefault.java"), expected);
    }

    @Test
    public void testTopViolation() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
            "17:1: " + getCheckMessage(MSG_POSITION, "java.sql"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderTopViolation.java"), expected);
    }

    @Test
    public void testLexOrder() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_ORDERING_LEX, "java.desktop", "java.sql"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderLexOrder.java"), expected);
    }

    @Test
    public void testBottom() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderBottom.java"), expected);
    }

    @Test
    public void testBottomViolation() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderBottomViolation.java"), expected);
    }

    @Test
    public void testBottomAdjacentViolation() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
            "16:1: " + getCheckMessage(MSG_POSITION, "java.sql"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderBottomAdjacentViolation.java"),
            expected);
    }

    @Test
    public void testSeparatedTop() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SEPARATION, "java.util.List"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderSeparatedTop.java"), expected);
    }

    @Test
    public void testSeparatedTopClean() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderSeparatedTopClean.java"), expected);
    }

    @Test
    public void testSeparatedBottom() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_SEPARATION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderSeparatedBottom.java"), expected);
    }

    @Test
    public void testSeparatedMisplaced() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderSeparatedMisplaced.java"), expected);
    }

    @Test
    public void testOnlyModules() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderOnlyModules.java"), expected);
    }

    @Test
    public void testOnlyModulesBottom() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderOnlyModulesBottom.java"), expected);
    }

    @Test
    public void testNoModules() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputModuleImportOrderNoModules.java"), expected);
    }

    @Test
    public void testCompactDefault() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputModuleImportOrderCompactDefault.java"),
            expected);
    }

    @Test
    public void testCompactNonDefault() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_SEPARATION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputModuleImportOrderCompactNonDefault.java"),
            expected);
    }

    @Test
    public void testStateIsClearedBetweenFiles() throws Exception {
        final String filePath1 = getNonCompilablePath("InputModuleImportOrderStateOne.java");
        final String filePath2 = getNonCompilablePath("InputModuleImportOrderStateTwo.java");
        final List<String> expectedFromFile1 = List.of();
        final List<String> expectedFromFile2 = List.of();

        verifyWithInlineConfigParser(filePath1, filePath2, expectedFromFile1, expectedFromFile2);
    }

    @Test
    public void testDuplicateModuleImports() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderDuplicate.java"), expected);
    }

    @Test
    public void testSeparatedPositionViolation() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderSeparatedPositionViolation.java"),
            expected);
    }

    @Test
    public void testMultilineModuleImport() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_SEPARATION, "java.util.List"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputModuleImportOrderMultiline.java"), expected);
    }

    @Test
    public void testOptionWhitespace() throws Exception {
        final String[] expected = {
            "16:1: " + getCheckMessage(MSG_POSITION, "java.desktop"),
        };

        verifyWithInlineXmlConfig(
                getNonCompilablePath("InputModuleImportOrderOptionWhitespace.java"), expected);
    }

    @Test
    public void testInvalidOption() {
        final CheckstyleException exc = getExpectedThrowable(CheckstyleException.class, () -> {
            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

            verifyWithInlineConfigParser(
                    getPath("InputModuleImportOrderInvalidOption.java"), expected);
        });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                    + ".imports.ModuleImportOrderCheck");
    }

}

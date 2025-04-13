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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISSING;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import de.thetaphi.forbiddenapis.SuppressForbidden;

public class PackageDeclarationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/packagedeclaration";
    }

    @Test
    public void testDefaultNoPackage() throws Exception {

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verifyWithInlineConfigParser(
                getPath("InputPackageDeclarationNoPackage.java"), expected);
    }

    @Test
    public void testDefaultWithPackage() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPackageDeclarationPlain.java"), expected);
    }

    @Test
    public void testOnFileWithCommentOnly() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputPackageDeclarationWithCommentOnly.java"), expected);
    }

    @Test
    public void testFileForDiffDirectoryMismatch() throws Exception {

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectory.java"), expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtParent() throws Exception {

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtParent.java"),
                expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtSubpackage() throws Exception {

        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtSubpackage.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDiffDirectoryMismatch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectory2.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtParent() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtParent2.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtSubpackage() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtSubpackage2.java"),
                expected);
    }

    @Test
    public void testNoPackage() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputPackageDeclarationNoPackage.java"),
                expected);
    }

    @SuppressForbidden
    @Test
    public void testEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationEmptyFile.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final PackageDeclarationCheck check = new PackageDeclarationCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testBeginTreeClear() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(PackageDeclarationCheck.class);
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY_MISSING),
        };
        final Checker checker = createChecker(checkConfig);
        final String fileName1 = getPath("InputPackageDeclarationPlain.java");
        final String fileName2 = getPath("InputPackageDeclarationNoPackage.java");
        final File[] files = {
            new File(fileName1),
            new File(fileName2),
        };
        verify(checker, files, fileName2, expected);
    }
}

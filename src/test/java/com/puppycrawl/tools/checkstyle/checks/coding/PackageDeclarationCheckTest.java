////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISSING;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PackageDeclarationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/packagedeclaration";
    }

    @Test
    public void testDefaultNoPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "2: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationNoPackage.java"), expected);
    }

    @Test
    public void testDefaultWithPackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageDeclarationPlain.java"), expected);
    }

    @Test
    public void testOnFileWithCommentOnly() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verify(checkConfig, getPath("InputPackageDeclarationWithCommentOnly.java"), expected);
    }

    @Test
    public void testFileForDiffDirectoryMismatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationDiffDirectory.java"), expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtParent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtParent.java"),
                expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtSubpackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtSubpackage.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDiffDirectoryMismatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("InputPackageDeclarationDiffDirectory.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtParent() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtParent.java"),
                expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtSubpackage() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationDiffDirectoryAtSubpackage.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final PackageDeclarationCheck check = new PackageDeclarationCheck();
        Assert.assertNotNull("Acceptable tokens should not be null", check.getAcceptableTokens());
        Assert.assertNotNull("Default tokens should not be null", check.getDefaultTokens());
        Assert.assertNotNull("Required tokens should not be null", check.getRequiredTokens());
    }

}

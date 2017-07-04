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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISMATCH;
import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY_MISSING;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class PackageDeclarationCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator
                + "packagedeclaration" + File.separator
                + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "coding" + File.separator
                + "packagedeclaration" + File.separator
                + filename);
    }

    @Test
    public void testDefaultNoPackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "4: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verify(checkConfig,
                getNonCompilablePath("InputPackageDeclarationNoPackage.java"), expected);
    }

    @Test
    public void testDefaultWithPackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageDeclarationPlain.java"), expected);
    }

    @Test
    public void testOnFileWithCommentOnly() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISSING),
        };

        verify(checkConfig, getPath("InputPackageDeclarationWithCommentOnly.java"), expected);
    }

    @Test
    public void testFileForDiffDirectoryMismatch() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig, getPath("InputPackageDeclarationDiffDirectory.java"), expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtParent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig, getPath("InputPackageDeclarationDiffDirectoryAtParent.java"), expected);
    }

    @Test
    public void testFileForDirectoryMismatchAtSubpackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        final String[] expected = {
            "1: " + getCheckMessage(MSG_KEY_MISMATCH),
        };

        verify(checkConfig,
                getPath("InputPackageDeclarationDiffDirectoryAtSubpackage.java"), expected);
    }

    @Test
    public void testFileIgnoreDiffDirectoryMismatch() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageDeclarationDiffDirectory.java"), expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtParent() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageDeclarationDiffDirectoryAtParent.java"), expected);
    }

    @Test
    public void testFileIgnoreDirectoryMismatchAtSubpackage() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);
        checkConfig.addAttribute("matchDirectoryStructure", "false");
        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputPackageDeclarationDiffDirectoryAtSubpackage.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final PackageDeclarationCheck check = new PackageDeclarationCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}

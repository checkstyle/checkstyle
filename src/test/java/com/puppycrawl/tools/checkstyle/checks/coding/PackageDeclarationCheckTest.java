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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.PackageDeclarationCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class PackageDeclarationCheckTest extends BaseCheckTestSupport {
    @Test
    public void testDefault() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
            "4: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/coding/InputNoPackage.java").getCanonicalPath(), expected);
    }

    @Test
    public void testOnFileWithCommentOnly() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
            "1: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("coding/InputWithCommentOnly.java"), expected);
    }

    @Test
    public void testCorrectFile() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(PackageDeclarationCheck.class);

        String[] expected = {
        };

        verify(checkConfig, getPath("coding/InputPackageDeclaration.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        PackageDeclarationCheck check = new PackageDeclarationCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}

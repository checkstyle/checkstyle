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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class PackageAnnotationTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "annotation" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "annotation" + File.separator + filename);
    }

    /**
     * This tests a package annotation that is in the package-info.java file.
     */
    @Test
    public void testGoodPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getNonCompilablePath("package-info.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final PackageAnnotationCheck constantNameCheckObj = new PackageAnnotationCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.PACKAGE_DEF };
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testAnnotationNotInPackageInfo() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageAnnotation.java"), expected);
    }

    @Test
    public void testWithoutAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = {
            "0: Package annotations must be in the package-info.java info.",
        };

        verify(checkConfig, getNonCompilablePath("InputPackageAnnotation2.java"), expected);
    }
}

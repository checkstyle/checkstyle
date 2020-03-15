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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.PackageAnnotationCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class PackageAnnotationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/packageannotation";
    }

    /**
     * This tests a package annotation that is in the package-info.java file.
     */
    @Test
    public void testGoodPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageAnnotationCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("package-info.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final PackageAnnotationCheck constantNameCheckObj = new PackageAnnotationCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.PACKAGE_DEF};
        assertArrayEquals(expected, actual, "Invalid acceptable tokens");
    }

    @Test
    public void testNoPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageAnnotationCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputPackageAnnotation.java"), expected);
    }

    @Test
    public void testBadPackageAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(PackageAnnotationCheck.class);

        final String[] expected = {
            "4:1: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getNonCompilablePath("InputPackageAnnotation2.java"), expected);
    }

}

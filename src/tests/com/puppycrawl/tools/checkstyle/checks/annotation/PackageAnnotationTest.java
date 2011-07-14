////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import org.junit.Test;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class PackageAnnotationTest extends BaseCheckTestSupport
{
    /**
     * This tests 1 package annotation that is not in the package-info.java file.
     *
     * @throws Exception
     */
    @Test
    public void testBadPackageAnnotation1() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = {
            "0: Package annotations must be in the package-info.java info.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadPackageAnnotation1.java"), expected);
    }

    /**
     * This tests 2 package annotations that are not in the package-info.java file.
     *
     * @throws Exception
     */
    @Test
    public void testBadPackageAnnotation2() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = {
            "0: Package annotations must be in the package-info.java info.",
        };

        verify(checkConfig, getPath("annotation" + File.separator + "BadPackageAnnotation2.java"), expected);
    }

    /**
     * This tests a package annotation that is in the package-info.java file.
     *
     * @throws Exception
     */
    @Test
    public void testGoodPackageAnnotation() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(PackageAnnotationCheck.class);

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "package-info.java"), expected);
    }
}

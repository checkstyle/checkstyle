////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import com.google.common.collect.Sets;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.util.Arrays;
import java.util.Set;
import org.junit.Test;

/**
 * Enter a description of class PackageNamesLoaderTest.java.
 * @author Rick Giles
 * @author lkuehne
 */
public class PackageNamesLoaderTest
{
    @Test
    public void testDefault()
        throws CheckstyleException
    {
        final Set<String> packageNames = PackageNamesLoader
                .getPackageNames(Thread.currentThread()
                        .getContextClassLoader());
        validatePackageNames(packageNames);
    }

    private void validatePackageNames(Set<String> aPkgNames)
    {
        final String[] checkstylePackages = {
            "com.puppycrawl.tools.checkstyle.",
            "com.puppycrawl.tools.checkstyle.checks.",
            "com.puppycrawl.tools.checkstyle.checks.annotation.",
            "com.puppycrawl.tools.checkstyle.checks.blocks.",
            "com.puppycrawl.tools.checkstyle.checks.coding.",
            "com.puppycrawl.tools.checkstyle.checks.design.",
            "com.puppycrawl.tools.checkstyle.checks.duplicates.",
            "com.puppycrawl.tools.checkstyle.checks.header.",
            "com.puppycrawl.tools.checkstyle.checks.imports.",
            "com.puppycrawl.tools.checkstyle.checks.indentation.",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.",
            "com.puppycrawl.tools.checkstyle.checks.metrics.",
            "com.puppycrawl.tools.checkstyle.checks.modifier.",
            "com.puppycrawl.tools.checkstyle.checks.naming.",
            "com.puppycrawl.tools.checkstyle.checks.regexp.",
            "com.puppycrawl.tools.checkstyle.checks.sizes.",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.",
            "com.puppycrawl.tools.checkstyle.filters.",

        };

        assertEquals("pkgNames.length.", checkstylePackages.length,
            aPkgNames.size());
        final Set<String> checkstylePackagesSet =
            Sets.newHashSet(Arrays.asList(checkstylePackages));
        assertEquals("names set.", checkstylePackagesSet, aPkgNames);
    }
}

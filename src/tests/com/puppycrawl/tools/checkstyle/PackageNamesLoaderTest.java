package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

/**
 * Enter a description of class PackageNamesLoaderTest.java.
 * @author Rick Giles
 * @author lkuehne
 * @version $Revision$
 */
public class PackageNamesLoaderTest
{
    @Test
    public void testDefault()
        throws CheckstyleException
    {
        Set<String> packageNames = PackageNamesLoader
                .getPackageNames(Thread.currentThread()
                        .getContextClassLoader());
        validatePackageNames(packageNames);
    }

    private void validatePackageNames(Set<String> aPkgNames)
    {
        final String[] checkstylePackages = {
            "com.puppycrawl.tools.checkstyle.",
            "com.puppycrawl.tools.checkstyle.checks.",
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
            "com.puppycrawl.tools.checkstyle.checks.sizes.",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.",
            "com.puppycrawl.tools.checkstyle.filters.",

        };

        assertEquals("pkgNames.length.", checkstylePackages.length,
            aPkgNames.size());
        Set<String> checkstylePackagesSet =
            new HashSet<String>(Arrays.asList(checkstylePackages));
        assertEquals("names set.", checkstylePackagesSet, aPkgNames);
    }
}

package com.puppycrawl.tools.checkstyle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import junit.framework.TestCase;

/**
 * Enter a description of class PackageNamesLoaderTest.java.
 * @author Rick Giles
 * @author lkuehne
 * @version $Revision$
 */
public class PackageNamesLoaderTest extends TestCase
{
    public void testDefault()
        throws CheckstyleException
    {
        ModuleFactory moduleFactory = PackageNamesLoader.loadModuleFactory(
            this.getClass().getClassLoader());
        validateFactory(moduleFactory);
    }

    public void testNoFile()
    {
        try {
            PackageNamesLoader.loadModuleFactory("NoFile");
            fail("Loaded non-existant file.");
        }
        catch (CheckstyleException ex) {
            assertEquals("CheckstyleException.message.",
                "unable to find NoFile",
                ex.getMessage());
        }
    }

    public void testFile()
        throws CheckstyleException
    {
        final ModuleFactory moduleFactory =
            PackageNamesLoader.loadModuleFactory(
                "src/checkstyle/com/puppycrawl/tools/checkstyle/checkstyle_packages.xml");
        validateFactory(moduleFactory);
    }

    private void validateFactory(ModuleFactory aModuleFactory)
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

        PackageObjectFactory factory = (PackageObjectFactory) aModuleFactory;
        String[] pkgNames = factory.getPackages();

        assertEquals("pkgNames.length.", checkstylePackages.length,
            pkgNames.length);
        Set checkstylePackagesSet =
            new HashSet(Arrays.asList(checkstylePackages));
        Set pkgNamesSet = new HashSet(Arrays.asList(pkgNames));
        assertEquals("names set.", checkstylePackagesSet, pkgNamesSet);
    }

}

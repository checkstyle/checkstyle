package com.puppycrawl.tools.checkstyle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import junit.framework.TestCase;

/**
 * Enter a description of class PackageNamesLoaderTest.java.
 * @author Rick Giles
 * @version 8-Dec-2002
 */
public class PackageNamesLoaderTest extends TestCase
{
    public void testDefault()
        throws CheckstyleException
    {
        String[] pkgNames = PackageNamesLoader.loadPackageNames(
            this.getClass().getClassLoader());
        validatePackageNames(pkgNames);
    }
    
    public void testNoFile()
        throws CheckstyleException
    {
        try {
            PackageNamesLoader.loadPackageNames("NoFile");
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
        String[] pkgNames =
            PackageNamesLoader.loadPackageNames("src/checkstyle/checkstyle_packages.xml");
        validatePackageNames(pkgNames);
    }
    
    private void validatePackageNames(String[] pkgNames)
    {
        String[] checkstylePackages = {
            "com.puppycrawl.tools.checkstyle.",
            "com.puppycrawl.tools.checkstyle.checks."
        };
        assertEquals("pkgNames.length.", checkstylePackages.length,
            pkgNames.length);
        Set checkstylePackagesSet =
            new HashSet(Arrays.asList(checkstylePackages));
        Set pkgNamesSet = new HashSet(Arrays.asList(pkgNames));
        assertEquals("names set.", checkstylePackagesSet, pkgNamesSet); 
    }
        
}

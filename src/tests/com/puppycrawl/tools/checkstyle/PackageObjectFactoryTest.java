package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.ConstantNameCheck;

import junit.framework.TestCase;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 * @author Rick Giles
 * @version 8-Dec-2002
 */
public class PackageObjectFactoryTest extends TestCase
{

    private PackageObjectFactory mFactory = new PackageObjectFactory();

    public void setUp()
    {
        mFactory = new PackageObjectFactory();
    }

    public void testMakeObjectFromName()
        throws CheckstyleException
    {
        final PackageObjectFactory factory =
                (PackageObjectFactory) mFactory.createModule(
                        "com.puppycrawl.tools.checkstyle.PackageObjectFactory");
        assertNotNull(factory);
    }

    public void testMakeCheckFromName()
        throws CheckstyleException
    {
        final ConstantNameCheck check =
                (ConstantNameCheck) mFactory.createModule(
                        "com.puppycrawl.tools.checkstyle.checks.ConstantName");
        assertNotNull(check);
    }
    
    public void testMakeObectFromList()
        throws CheckstyleException
    {
        mFactory.addPackage("com.");
        final PackageObjectFactory factory =
                (PackageObjectFactory) mFactory.createModule(
                        "puppycrawl.tools.checkstyle.PackageObjectFactory");
        assertNotNull(factory);
    }
    
    public void testMakeObectNoClass()
        throws CheckstyleException
    {
        try {
            mFactory.createModule("NoClass");
            fail("Instantiated non-existant class");
        }
        catch (CheckstyleException ex) {
            assertEquals("CheckstyleException.message",
                "Unable to instantiate NoClass",
                ex.getMessage());
        }
    }
}
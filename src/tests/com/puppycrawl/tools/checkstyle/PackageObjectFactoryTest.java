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

    public void testMakeObjectFromName()
        throws CheckstyleException
    {
        final PackageObjectFactory factory =
            (PackageObjectFactory) PackageObjectFactory.makeObject(
                new String[] {},
                getClass().getClassLoader(),
                "com.puppycrawl.tools.checkstyle.PackageObjectFactory");
        assertNotNull(factory);
    }

    public void testMakeCheckFromName()
        throws CheckstyleException
    {
        final ConstantNameCheck check =
            (ConstantNameCheck) PackageObjectFactory.makeObject(
                new String[] {},
                getClass().getClassLoader(),
                "com.puppycrawl.tools.checkstyle.checks.ConstantName");
        assertNotNull(check);
    }
    
    public void testMakeObectFromList()
        throws CheckstyleException
    {    
        final PackageObjectFactory factory =
            (PackageObjectFactory) PackageObjectFactory.makeObject(
                new String [] {"com."},
                this.getClass().getClassLoader(),
                "puppycrawl.tools.checkstyle.PackageObjectFactory");
        assertNotNull(factory);
    }
    
    public void testMakeObectNoClass()
        throws CheckstyleException
    {
        try {
            PackageObjectFactory.makeObject(new String[] {},
                                            getClass().getClassLoader(),
                                            "NoClass");
            fail("Instantiated non-existant class");
        }
        catch (CheckstyleException ex) {
            assertEquals("CheckstyleException.message",
                "Unable to instantiate NoClass",
                ex.getMessage());
        }
    }
}
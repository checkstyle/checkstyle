package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import junit.framework.TestCase;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 * @author Rick Giles
 * @version 8-Dec-2002
 */
public class PackageObjectFactoryTest extends TestCase
{

    public void testMakeObectFromName()
        throws CheckstyleException
    {
        PackageObjectFactory factory =
            (PackageObjectFactory) PackageObjectFactory.makeObject(
                new String [] {},
                this.getClass().getClassLoader(),
                "com.puppycrawl.tools.checkstyle.PackageObjectFactory");
    }
    
    public void testMakeObectFromList()
        throws CheckstyleException
    {    
        PackageObjectFactory factory =
            (PackageObjectFactory) PackageObjectFactory.makeObject(
                new String [] {"com."},
                this.getClass().getClassLoader(),
                "puppycrawl.tools.checkstyle.PackageObjectFactory");
    }
    
    public void testMakeObectNoClass()
        throws CheckstyleException
    {
        try {
            PackageObjectFactory factory =
                (PackageObjectFactory) PackageObjectFactory.makeObject(
                    new String [] {},
                    this.getClass().getClassLoader(),
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
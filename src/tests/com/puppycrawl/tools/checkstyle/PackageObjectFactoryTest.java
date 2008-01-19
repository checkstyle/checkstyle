package com.puppycrawl.tools.checkstyle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import org.junit.Test;

/**
 * Enter a description of class PackageObjectFactoryTest.java.
 * @author Rick Giles
 * @version 8-Dec-2002
 */
public class PackageObjectFactoryTest
{

    private final PackageObjectFactory mFactory = new PackageObjectFactory();

    @Test
    public void testMakeObjectFromName()
        throws CheckstyleException
    {
        final Checker checker =
            (Checker) mFactory.createModule(
                        "com.puppycrawl.tools.checkstyle.Checker");
        assertNotNull(checker);
    }

    @Test
    public void testMakeCheckFromName()
        throws CheckstyleException
    {
        final ConstantNameCheck check =
                (ConstantNameCheck) mFactory.createModule(
                        "com.puppycrawl.tools.checkstyle.checks.naming.ConstantName");
        assertNotNull(check);
    }

    @Test
    public void testMakeObectFromList()
        throws CheckstyleException
    {
        mFactory.addPackage("com.");
        final Checker checker =
                (Checker) mFactory.createModule(
                        "puppycrawl.tools.checkstyle.Checker");
        assertNotNull(checker);
    }

    @Test
    public void testMakeObectNoClass()
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

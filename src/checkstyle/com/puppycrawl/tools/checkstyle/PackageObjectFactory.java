package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * A factory for creating objects from package names and names.
 * <code>PackageObjectFactory</code> has no public constructor.
 * @author Rick Giles
 * @version 4-Dec-2002
 */
public class PackageObjectFactory
{
    /**
     * Creates a new <code>PackageObjectFactory</code> instance.
     */
    private PackageObjectFactory()
    {
    }

    /**
     * Creates a new instance of a class from a given name. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given
     * to a package name from a given list of package names.
     * @param aPackages list of package names.
     * @param aLoader the <code>ClassLoader</code> to create the instance with.
     * @param aName the name of a class.
     * @return the <code>Object</code> created by aLoader.
     * @throws CheckstyleException if an error occurs.
     */
    private Object doMakeObject(String[] aPackages,
                                ClassLoader aLoader,
                                String aName)
        throws CheckstyleException
    {
        //try aName first
        try {
            return createObject(aLoader, aName);
        }
        catch (CheckstyleException ex) {
            ; // keep looking
        }

        //now try packages
        for (int i = 0; i < aPackages.length; i++) {
            final String className = aPackages[i] + aName;
            try {
                return createObject(aLoader, className);
            }
            catch (CheckstyleException ex) {
                ; // keep looking
            }
        }

        throw new CheckstyleException("Unable to instantiate " + aName);
    }

    /**
     * Creates a new instance of a named class.
     * @param aLoader the <code>ClassLoader</code> to create the instance with.
     * @param aClassName the name of the class to instantiate.
     * @return the <code>Object</code> created by aLoader.
     * @throws CheckstyleException if an error occurs.
     */
    private Object createObject(ClassLoader aLoader, String aClassName)
        throws CheckstyleException
    {
        try {
            final Class clazz = Class.forName(aClassName, true, aLoader);
            return clazz.newInstance();
        }
        catch (ClassNotFoundException e) {
            throw new CheckstyleException(
                "Unable to find class for " + aClassName);
        }
        catch (InstantiationException e) {
            throw new CheckstyleException(
                "Unable to instantiate " + aClassName);
        }
        catch (IllegalAccessException e) {
            throw new CheckstyleException(
                "Unable to instantiate " + aClassName);
        }
    }

    /**
     * Creates a new instance of a class from a given name. If the name is
     * a classname, creates an instance of the named class. Otherwise, creates
     * an instance of a classname obtained by concatenating the given
     * to a package name from a given list of package names.
     * @param aPackages list of package names.
     * @param aLoader the <code>ClassLoader</code> to create the instance with.
     * @param aName the name of a class.
     * @return the <code>Object</code> created by aLoader.
     * @throws CheckstyleException if an error occurs.
     */
    public static Object makeObject(String[] aPackages, ClassLoader aLoader,
        String aName)
        throws CheckstyleException
    {
        final PackageObjectFactory factory = new PackageObjectFactory();
        return factory.doMakeObject(aPackages, aLoader, aName);
    }
}

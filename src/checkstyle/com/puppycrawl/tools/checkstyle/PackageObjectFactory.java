package com.puppycrawl.tools.checkstyle;

import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * A factory for creating objects by mapping a name to a class name.
 * <code>MappedObjectFactory</code> has no public constructor..
 * @author Rick Giles
 * @version 4-Dec-2002
 */
public class PackageObjectFactory
{
    /**
     * Creates a new <code>MappedObjectFactory</code> instance.
     */    
    private PackageObjectFactory()
    {
    }

    /**
     * Creates a new instance of a class. Determines the name of the
     * class to instantiate by mapping a given name to a class name,
     *  using a provided <code>Map</code>.
     * @param aMap the mapping from aName to a class name.
     * @param aLoader the <code>ClassLoader</code> to create the instance with.
     * @param aName the name to map to the class name.
     * @return the <code>Object</code> created by aLoader after mapping
     * aName to a class name.
     * @throws CheckstyleException if an error occurs.
     */            
    private Object doMakeObject(String[] aPackages, ClassLoader aLoader,
            String aName)
        throws CheckstyleException
    {
        Object retVal = null;
        //try aName first
        try {
            return createObject(aLoader, aName);
        }
        catch (CheckstyleException ex) {
            retVal = null;
        }
        //now try packages
        for (int i = 0; i < aPackages.length; i++) {
            final String className = aPackages[i] + aName;
            try {
                return createObject(aLoader, className);
            }
            catch (CheckstyleException ex) {
                retVal = null;
            }
        }   
        throw new CheckstyleException(
            "Unable to instantiate " + aName);
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
     * Creates a new instance of a class. Determines the name of the
     * class to instantiate by mapping a given name to a class name,
     *  using a provided <code>Map</code>.
     * @param aMap the mapping from aName to a class name.
     * @param aLoader the <code>ClassLoader</code> to create the instance with.
     * @param aName the name that will be mapped to the name of a class.
     * @return the <code>Object</code> created by aLoader after mapping
     * aName to a class name.
     * @throws CheckstyleException if an error occurs.
     */
    public static Object makeObject(String[] aPackages, ClassLoader aLoader,
        String aClassName)
        throws CheckstyleException
    {
        final PackageObjectFactory factory = new PackageObjectFactory();
        return factory.doMakeObject(aPackages, aLoader, aClassName);
    }
}

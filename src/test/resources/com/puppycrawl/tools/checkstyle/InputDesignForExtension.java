////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

/**
 * Test case for the "design for inheritance" check.
 * @author Lars Kühne
 **/
public abstract class InputDesignForExtension
{
    // some methods that are OK

    public interface InterfaceOK
    {
        void implicitlyAbstract();
    }

    final class ClassOK
    {
        protected void finalThroughClassDef()
        {
            System.out.println("no way to override");
        }
    }

    protected void nonFinalButEmpty()
    {
    }

    public void nonFinalButEmpty2()
    {
        // comments don't count as content...
    }

    private void aPrivateMethod()
    {
        System.out.println("no way to override");
    }

    protected abstract void nonFinalButAbstract();

    // this one is bad: neither abstract, final, or empty

    protected void doh()
    {
        System.out.println("nonempty and overriding possible");
    }

    // has a potentially complex implementation in native code.
    // We can't check that, so to be safe DesignForExtension requires
    // native methods to also be final
    public native void aNativeMethod();

    // tries to trigger bug #884035
    // MyComparator is a private class, so there cannot be subclasses
    // and it should not be neccessary to declare compare() as final
    private class MyComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2)
        {
            // some complex stuff that would normally trigger an error report
            if (o1.hashCode() > o2.hashCode()) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }
}

// enums should be skipped
enum TEnum
{
    FIRST,
    SECOND;

    public int value()
    {
        return 3;
    }
}

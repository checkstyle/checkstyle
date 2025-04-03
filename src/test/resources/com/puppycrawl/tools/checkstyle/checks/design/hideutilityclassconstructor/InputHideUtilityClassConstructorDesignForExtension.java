/*
HideUtilityClassConstructor
ignoreAnnotatedBy = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

/**
 * Test case for the "design for inheritance" check.
 * @author Lars Kühne
 **/
public abstract class InputHideUtilityClassConstructorDesignForExtension
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
            System.identityHashCode("no way to override");
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
        System.identityHashCode("no way to override");
    }

    protected abstract void nonFinalButAbstract();

    // this one is bad: neither abstract, final, nor empty

    protected void doh()
    {
        System.identityHashCode("nonempty and overriding possible");
    }

    public final void aFinalMethod()
    {
    System.identityHashCode("no way to override");
    }

    public static void aStaticMethod()
    {
    System.identityHashCode("no way to override");
    }

    // tries to trigger bug #884035
    // MyComparator is a private class, so there cannot be subclasses
    // and it should not be necessary to declare compare() as final
    private class MyComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2)
        {
            // some complex stuff that would normally trigger a violation report
            if (o1.hashCode() > o2.hashCode()) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    public final class aFinalClass
    {
        public void someMethod()
        {
        System.identityHashCode("nonempty and overriding is possible");
        }
    }

    public class nonFinalClass
    {
    //private ctor
    private nonFinalClass(){}
        public void someMethod()
        {
        System.identityHashCode("nonempty and overriding is possible");
        }
    }

    public class anotherNonFinalClass
    {
    //nonPrivate ctor
    public anotherNonFinalClass(){}
        public void someMethod()
        {
        System.identityHashCode("nonempty and overriding is possible");
        }
    }

    // enums should be skipped
    public enum TEnum
    {
        FIRST,
        SECOND;

        public int value()
        {
            return 3;
        }
    }
}

/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

/**
 * Test case for the "design for inheritance" check.
 * @author Lars Kühne
 **/
public abstract class InputDesignForExtensionMethods
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

    // violation below ''doh' does not have javadoc that explains how to do that safely'
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
}


/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public class InputDesignForExtensionNonFinalClasses {
    public final class aFinalClass
    {
        public void someMethod()
        {
        System.identityHashCode("nonempty and overriding is possible");
        }
    }

    public class nonFinalClass
    {
    // private ctor
    private nonFinalClass(){}
        public void someMethod()
        {
        System.identityHashCode("nonempty and overriding is possible");
        }
    }

    public class anotherNonFinalClass
    {
    // nonPrivate ctor
    public anotherNonFinalClass(){}
        // violation below ''someMethod' does not have javadoc that explains how to do that safely'
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

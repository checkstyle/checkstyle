////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/* Config:
 *
 * scope = protected
 */

public class InputJavadocTypeScopeInnerInterfaces1
{


    private interface PrivateInterface // ok
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    interface PackageInnerInterface // ok
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    protected interface ProtectedInnerInterface // ok
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    public interface PublicInnerInterface // ok
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    private
    class
    MyClass1 {
    }

    class
    MyClass2 {
    }

    private
    interface
    MyInterface1 { // ok
    }

    interface
    MyInterface2 { // ok
    }

    protected
    enum
    MyEnum { // ok
    }

    private
    @interface
    MyAnnotation { // ok
    }
}

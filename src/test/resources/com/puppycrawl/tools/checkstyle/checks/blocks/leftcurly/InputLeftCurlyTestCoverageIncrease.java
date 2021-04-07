////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = nlow
 */
public class InputLeftCurlyTestCoverageIncrease {
    // inner interfaces with different scopes


    private interface PrivateInterface
    { // violation
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    interface PackageInnerInterface
    { // violation
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    protected interface ProtectedInnerInterface
    { // violation
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    public interface PublicInnerInterface
    { // violation
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    private
    class
 MyClass1
  {
    }
    class
    MyClass2 { // violation
    }

    private
    interface
    MyInterface1 { // violation
    }

    interface
    MyInterface2 { // violation
    }

    protected
    enum
    MyEnum { // violation
    }

    private
    @interface
    MyAnnotation { // violation
    }
}

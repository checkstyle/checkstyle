package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

/*
 * Config:
 * scope = protected
 * excludeScope = null
 * skipAnnotation = Generated
 */
public class InputMissingJavadocTypeScopeInnerInterfaces2 // violation
{
    // inner interfaces with different scopes

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

    protected interface ProtectedInnerInterface // violation
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    public interface PublicInnerInterface // violation
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    private
    class
    MyClass1 { // ok
    }

    class
    MyClass2 { // ok
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
    MyEnum { // violation
    }

    private
    @interface
    MyAnnotation { // ok
    }
}

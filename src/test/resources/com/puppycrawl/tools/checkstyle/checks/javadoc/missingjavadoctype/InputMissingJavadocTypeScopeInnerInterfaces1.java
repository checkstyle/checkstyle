/*
MissingJavadocType
scope = (default)public
excludeScope = (default)null
skipAnnotations = (default)Generated
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

public class InputMissingJavadocTypeScopeInnerInterfaces1 // violation
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

    protected interface ProtectedInnerInterface // ok
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
    MyEnum { // ok
    }

    private
    @interface
    MyAnnotation { // ok
    }
}

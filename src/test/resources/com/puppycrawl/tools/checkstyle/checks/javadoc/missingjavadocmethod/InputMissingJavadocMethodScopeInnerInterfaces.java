package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "public"
 */
public class InputMissingJavadocMethodScopeInnerInterfaces
{
    // inner interfaces with different scopes

    private interface PrivateInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma(); // ok
        void mb(); // ok
    }

    interface PackageInnerInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma(); // ok
        void mb(); // ok
    }

    protected interface ProtectedInnerInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma(); // ok
        void mb(); // ok
    }

    public interface PublicInnerInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma(); // violation
        void mb(); // violation
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
    MyInterface1 {
    }

    interface
    MyInterface2 {
    }

    protected
    enum
    MyEnum {
    }

    private
    @interface
    MyAnnotation {
    }
}

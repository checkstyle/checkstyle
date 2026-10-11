/*
MissingJavadocType
excludeScope = (default)null
scope = protected
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

// violation below 'Missing a Javadoc comment.'
public class InputMissingJavadocTypeScopeInnerInterfaces2
{
    // inner interfaces with different scopes

    private interface PrivateInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    interface PackageInnerInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    // violation below 'Missing a Javadoc comment.'
    protected interface ProtectedInnerInterface
    {
        public String CA = "CONST A";
        String CB = "CONST b";

        public void ma();
        void mb();
    }

    // violation below 'Missing a Javadoc comment.'
    public interface PublicInnerInterface
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
    MyInterface1 {
    }

    interface
    MyInterface2 {
    }

    // violation below 'Missing a Javadoc comment.'
    protected
    enum
    MyEnum {
    }

    private
    @interface
    MyAnnotation {
    }
}

/*
JavadocMethod
allowedAnnotations = (default)Override
validateThrows = (default)false
accessModifiers = public
allowMissingParamTags = (default)false
allowMissingReturnTag = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF, ANNOTATION_FIELD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

public class InputJavadocMethodScopeInnerInterfaces
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

    public interface PublicInnerInterface // ok
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

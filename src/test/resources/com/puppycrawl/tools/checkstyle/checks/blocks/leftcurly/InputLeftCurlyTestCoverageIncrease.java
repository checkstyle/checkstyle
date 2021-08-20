/*
LeftCurly
option = NLOW
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

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

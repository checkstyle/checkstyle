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

public class InputLeftCurlyDefaultTestNlow
{ // violation
    private interface PrivateInterface
    { // violation
    }

    interface PackageInnerInterface
    { // violation
    }

    protected interface ProtectedInnerInterface
    { // violation
    }

    public interface PublicInnerInterface
    { // violation
    }

    private
    class
    MyClass1 { // violation
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

    @Deprecated
    public
    class MyClass3 { // violation
    }

    public class MyClass4 { // ok
        void method() { // ok
            while(true) {/*foo*/} // ok
        }
    }
}

/*
LeftCurly
option = invalid_option
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestInvalidOption
{ // ok
    private interface PrivateInterface
    { // ok
    }

    interface PackageInnerInterface
    { // ok
    }

    protected interface ProtectedInnerInterface
    { // ok
    }

    public interface PublicInnerInterface
    { // ok
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

    @Deprecated
    public
    class MyClass3 { // ok
    }

    public class MyClass4 { // ok
        void method() { // ok
            while(true) {/*foo*/} // ok
        }
    }
}

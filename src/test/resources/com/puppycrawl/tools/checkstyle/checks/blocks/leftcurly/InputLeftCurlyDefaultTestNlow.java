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
{ // violation ''{' at column 1 should be on the previous line'
    private interface PrivateInterface
    { // violation ''{' at column 5 should be on the previous line'
    }

    interface PackageInnerInterface
    { // violation ''{' at column 5 should be on the previous line'
    }

    protected interface ProtectedInnerInterface
    { // violation ''{' at column 5 should be on the previous line'
    }

    public interface PublicInnerInterface
    { // violation ''{' at column 5 should be on the previous line'
    }

    private
    class
    MyClass1 { // violation ''{' at column 14 should be on a new line'
    }

    class
    MyClass2 { // violation ''{' at column 14 should be on a new line'
    }

    private
    interface
    MyInterface1 { // violation ''{' at column 18 should be on a new line'
    }

    interface
    MyInterface2 { // violation ''{' at column 18 should be on a new line'
    }

    protected
    enum
    MyEnum { // violation ''{' at column 12 should be on a new line'
    }

    private
    @interface
    MyAnnotation { // violation ''{' at column 18 should be on a new line'
    }

    @Deprecated
    public
    class MyClass3 { // violation ''{' at column 20 should be on a new line'
    }

    public class MyClass4 { // ok
        void method() { // ok
            while(true) {/*foo*/} // ok
        }
    }
}

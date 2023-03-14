/*
LeftCurly
option = NL
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyDefaultTestNl
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

    public class MyClass4 { // violation ''{' at column 27 should be on a new line'
        void method() { // violation ''{' at column 23 should be on a new line'
            while(true) {/*foo*/} // violation ''{' at column 25 should be on a new line'
        }
    }
}

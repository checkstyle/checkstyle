/*
LeftCurly
option = (default)eol
ignoreEnums = (default)true
tokens = (default)ANNOTATION_DEF, CLASS_DEF, CTOR_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, INTERFACE_DEF, LAMBDA, LITERAL_CASE, LITERAL_CATCH, \
         LITERAL_DEFAULT, LITERAL_DO, LITERAL_ELSE, LITERAL_FINALLY, LITERAL_FOR, \
         LITERAL_IF, LITERAL_SWITCH, LITERAL_SYNCHRONIZED, LITERAL_TRY, LITERAL_WHILE, \
         METHOD_DEF, OBJBLOCK, STATIC_INIT, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyTestDefault
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

    @Deprecated
    public
    class MyClass3 {
    }

    public class MyClass4 {
        void method() {
            while(true) {/*foo*/}
        }
    }
}

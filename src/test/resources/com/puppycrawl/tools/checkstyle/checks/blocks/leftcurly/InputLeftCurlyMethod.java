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

class InputLeftCurlyMethod
{ // violation
    InputLeftCurlyMethod() {}
    InputLeftCurlyMethod(String aOne) { // ok
    }
    InputLeftCurlyMethod(int aOne)
    { // violation
    }

    void method1() {}
    void method2() { // ok
    }
    void method3()
    { // violation
    }
    void                                                               method4()
    { // violation
    }
    void method5(String aOne,
                 String aTwo)
    { // violation
    }
    void method6(String aOne,
                 String aTwo) { // ok
    }
}

enum InputLeftCurlyMethodEnum
{ // violation
    CONSTANT1("hello")
    { // violation
        void method1() {}
        void method2() { // ok
        }
        void method3()
        { // violation
        }
        void                                                               method4()
        { // violation
        }
        void method5(String aOne,
                     String aTwo)
        { // violation
        }
        void method6(String aOne,
                     String aTwo) { // ok
        }
    },

    CONSTANT2("hello") { // ok

    },

    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
    { // violation
    };

    private InputLeftCurlyMethodEnum(String value)
    { // violation

    }

    void method1() {}
    void method2() { // ok
    }
    void method3()
    { // violation
    }
    void                                                               method4()
    { // violation
    }
    void method5(String aOne,
                 String aTwo)
    { // violation
    }
    void method6(String aOne,
                 String aTwo) { // ok
    }
}


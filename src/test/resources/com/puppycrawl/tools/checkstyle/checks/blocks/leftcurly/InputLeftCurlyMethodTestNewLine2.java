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

class InputLeftCurlyMethodTestNewLine2
{ // ok
    void InputLeftCurlyMethod() {}
    void InputLeftCurlyMethod(String aOne) { // violation
    }
    void InputLeftCurlyMethod(int aOne)
    { // ok
    }

    void method1() {}
    void method2() { // violation
    }
    void method3()
    { // ok
    }
    void                                                               method4()
    { // ok
    }
    void method5(String aOne,
                 String aTwo)
    { // ok
    }
    void method6(String aOne,
                 String aTwo) { // violation
    }
}

enum InputLeftCurlyMethodEnumTestNewLine2
{ // ok
    CONSTANT1()
    { // ok
        void method1() {}
        void method2() { // violation
        }
        void method3()
        { // ok
        }
        void                                                               method4()
        { // ok
        }
        void method5(String aOne,
                     String aTwo)
        { // ok
        }
        void method6(String aOne,
                     String aTwo) { // violation
        }
    },

    CONSTANT2() { // violation

    },

    CONSTANT3()
    { // ok
    };

    private void InputLeftCurlyMethodEnum(String value)
    { // ok

    }

    void method1() {}
    void method2() { // violation
    }
    void method3()
    { // ok
    }
    void                                                               method4()
    { // ok
    }
    void method5(String aOne,
                 String aTwo)
    { // ok
    }
    void method6(String aOne,
                 String aTwo) { // violation
    }
}

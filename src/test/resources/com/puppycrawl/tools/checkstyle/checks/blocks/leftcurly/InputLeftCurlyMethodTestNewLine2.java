////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/**
 * Config:
 * option = nl
 *
 * Test case for correct use of braces.
 * @author Oliver Burn
 **/
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

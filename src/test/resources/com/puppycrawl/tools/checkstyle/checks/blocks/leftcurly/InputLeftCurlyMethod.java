////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/**
 * Test case for correct use of braces.
 * @author Oliver Burn
 **/
class InputLeftCurlyMethod
{
    InputLeftCurlyMethod() {}
    InputLeftCurlyMethod(String aOne) {
    }
    InputLeftCurlyMethod(int aOne)
    {
    }

    void method1() {}
    void method2() {
    }
    void method3()
    {
    }
    void                                                               method4()
    {
    }
    void method5(String aOne,
                 String aTwo)
    {
    }
    void method6(String aOne,
                 String aTwo) {
    }
}

enum InputLeftCurlyMethodEnum
{
    CONSTANT1("hello")
    {
        void method1() {}
        void method2() {
        }
        void method3()
        {
        }
        void                                                               method4()
        {
        }
        void method5(String aOne,
                     String aTwo)
        {
        }
        void method6(String aOne,
                     String aTwo) {
        }
    },

    CONSTANT2("hello") {

    },
    
    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
    {
    };

    private InputLeftCurlyMethodEnum(String value)
    {

    }

    void method1() {}
    void method2() {
    }
    void method3()
    {
    }
    void                                                               method4()
    {
    }
    void method5(String aOne,
                 String aTwo)
    {
    }
    void method6(String aOne,
                 String aTwo) {
    }
}

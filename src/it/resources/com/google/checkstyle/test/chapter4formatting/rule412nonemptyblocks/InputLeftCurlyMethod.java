package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyMethod
{ //warn
    InputLeftCurlyMethod() {} 
    InputLeftCurlyMethod(String aOne) {
    }
    InputLeftCurlyMethod(int aOne)
    { //warn
    }

    void method1() {}
    void method2() {
    }
    void method3()
    { //warn
    }
    void                                                               method4()
    { //warn
    }
    void method5(String aOne,
                 String aTwo)
    {//warn
    }
    void method6(String aOne,
                 String aTwo) {
    }
}

enum InputLeftCurlyMethodEnum
{//warn
    CONSTANT1("hello")
    {//warn
        void method1() {}
        void method2() {
        }
        void method3()
        {//warn
        }
        void                                                               method4()
        { //warn
        }
        void method5(String aOne,
                     String aTwo)
        {//warn
        }
        void method6(String aOne,
                     String aTwo) {
        }
    },

    CONSTANT2("hello") {

    },

    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
    {//warn
    };

    private InputLeftCurlyMethodEnum(String value)
    {//warn

    }

    void method1() {}
    void method2() {
    }
    void method3()
    {//warn
    }
    void                            method4()
    {//warn
    }
    void method5(String aOne,
                 String aTwo)
    {//warn
    }
    void method6(String aOne,
                 String aTwo) {
    }
}


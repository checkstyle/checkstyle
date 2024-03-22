package com.google.checkstyle.test.chapter4formatting.rule412nonemptyblocks;

class InputLeftCurlyMethod
{ //warn
    InputLeftCurlyMethod() {} //ok
    InputLeftCurlyMethod(String aOne) {//ok
    }
    InputLeftCurlyMethod(int aOne)
    { //warn
    }

    void method1() {}//ok
    void method2() {//ok
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
        void method1() {}//ok
        void method2() {
        }//ok
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
                     String aTwo) {//ok
        }
    },

    CONSTANT2("hello") {//ok

    },

    CONSTANT3("hellohellohellohellohellohellohellohellohellohellohellohellohellohello")
    {//warn
    };

    private InputLeftCurlyMethodEnum(String value)
    {//warn

    }

    void method1() {}//ok
    void method2() {//ok
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


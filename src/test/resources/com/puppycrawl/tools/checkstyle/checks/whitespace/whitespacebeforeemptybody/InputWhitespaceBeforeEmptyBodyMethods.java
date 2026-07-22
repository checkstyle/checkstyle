/*
WhitespaceBeforeEmptyBody
tokens = METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacebeforeemptybody;

public class InputWhitespaceBeforeEmptyBodyMethods {

    void method() {}

    void method1(){    // violation ''{' is not preceded with whitespace'
    }

    void method2(){    // violation ''{' is not preceded with whitespace'
        // comment
        /* comment */
    }

    void method3(){
        int a = 1;
    }

    void method4()
    {}

    void method5() throws Exception{} // violation ''{' is not preceded with whitespace'

    void method6() throws java.io.IOException{};
    // violation above ''{' is not preceded with whitespace'
}

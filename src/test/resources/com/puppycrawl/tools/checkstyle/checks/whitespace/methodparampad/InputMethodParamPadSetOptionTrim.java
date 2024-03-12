/*
MethodParamPad
allowLineBreaks = (default)false
option = \tspace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

public class InputMethodParamPadSetOptionTrim {

    public void method1() { // violation ''(' is not preceded with whitespace'
        int a = 1, b = 2;
        if (a > b) {
            System.out.println (a);
        }
    }

    public int method2 () {
        return 1;
    }

    public Boolean method3() { // violation ''(' is not preceded with whitespace'
        if (4 < 5) {
            return true;
        }
        return false;
    }
}

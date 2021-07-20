package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

/* Config: default
 *
 */
public class InputFinalLocalVariableCheckPostfixWithTypecast {

    void method1(String... analyzed) {
        int x = 0;
        method2(analyzed.length - 1, (byte) x++);

    }

    void method2(int z, byte y) {

    }

    void method3(String... analyzed) {
        int x = 0;
        method2(analyzed.length - 1, (byte) x); // violation
    }
}

package org.checkstyle.checks.suppressionxpathfilter.requirethis;

public class InputXpathRequireThisTwo {
    void method1() {
        int i = 3;
    }

    void method2(int i) {
        method1(); //warn
    }
}

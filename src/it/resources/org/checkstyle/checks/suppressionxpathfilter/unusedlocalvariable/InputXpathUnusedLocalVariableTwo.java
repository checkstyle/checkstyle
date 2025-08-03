package org.checkstyle.checks.suppressionxpathfilter.unusedlocalvariable;

import java.util.function.Predicate;

public class InputXpathUnusedLocalVariableTwo {

    int b = 21;

    void foo() {
        int b = 12; // warn
        a(this.b);
    }

    static void a(int a) {
    }

}

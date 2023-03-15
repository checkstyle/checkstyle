package org.checkstyle.suppressionxpathfilter.unusedlocalvariable;

import java.util.function.Predicate;

public class SuppressionXpathRegressionUnusedLocalVariableTwo {

    int b = 21;

    void foo() {
        int b = 12; // warn
        a(this.b);
    }

    static void a(int a) {
    }

}

/*
VariableDeclarationUsageDistance
allowedDistance = 1

*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

import java.util.Calendar;

class Test {
    public Test test(Object a) {
        Test obj = test("abc");
        int var = 12; // violation '.*'
        obj.test("abc");
        obj.test("abc");
        obj.test("abc");
        int k = var + 3;
        return this;
    }

    public void doSomething() {
        int a;
        for (int i = 0;;) {
        }

    }
}

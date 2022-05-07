/*
UnusedLocalVariable


*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableTernaryAndExpressions {
    void m1() {
        int time = new Integer(2); // ok
        assert(time < new Integer(3));
    }

    Object m2() {
        Object x = getY(); // ok
        String z = null;
        return z == null ? x : new Object();
    }

    String getY() {
        return "y";
    }

    int m3() {
        boolean b = m2().equals(new Object()); // ok
        return b ? 1 : new Integer(3);
    }
}

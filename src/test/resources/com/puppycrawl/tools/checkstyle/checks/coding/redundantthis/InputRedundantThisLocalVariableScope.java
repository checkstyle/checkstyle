/*
RedundantThis
checkMethodCall=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

public class InputRedundantThisLocalVariableScope {
    private int age;
    private Exception e;

    public void methodLocalShadow() {
        int age = 25;
        this.age = age;          // ok, local 'age' shadows field

        if (true) {
            int age2 = 30;
            this.age = age2;     // ok, outer 'age' still shadows field
        }

        for (int i = 0; i < 1; i++) {
            int age3 = 35;
            this.age = age3;     // ok, outer 'age' still shadows field
        }
    }

    public void methodScopeExit() {
        if (true) {
            int age = 6;
            this.age = age;      // ok, local 'age' shadows field in this block
        }
        this.age = 1;
        // violation above, 'Redundant "this", field 'age' can be accessed directly.'
    }

    public void methodNestedBlock() {
        {
            int age = 6;
            {
                // empty
            }
            this.age = 1;        // ok, local 'age' still in scope (same enclosing block)
        }
    }

    public void methodCatchShadow() {
        try {
            // do something
        } catch (Exception e) {
            this.e = e;          // ok, catch param 'e' shadows field 'e'
        }

        try {
            // do something
        } catch (Exception ex) {
            this.e = ex;
            // violation above, 'Redundant "this", field 'e' can be accessed directly.'
        }
    }
}

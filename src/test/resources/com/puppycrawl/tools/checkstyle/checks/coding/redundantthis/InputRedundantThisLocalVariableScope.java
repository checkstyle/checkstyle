/*
RedundantThis
checkMethods=(default)false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.redundantthis;

import java.util.Scanner;

public class InputRedundantThisLocalVariableScope {
    private int age;
    private Exception e;
    Scanner scanner;

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
        } catch (Exception e) {}

        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;
        } catch (Exception e) {
            this.e = e;          // ok, catch param 'e' shadows field 'e'
        }

        try (Scanner in = new Scanner(System.in)) {
            this.scanner = in;
            // violation above, 'Redundant "this", field 'scanner' can be accessed directly.'
        } catch (Exception ex) {
            this.e = ex;
            // violation above, 'Redundant "this", field 'e' can be accessed directly.'
        }
    }
}

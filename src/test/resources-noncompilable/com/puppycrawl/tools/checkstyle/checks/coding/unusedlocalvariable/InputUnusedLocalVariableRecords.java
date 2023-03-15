/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.function.Predicate;

//non-compiled with javac: Compilable with Java14
public record InputUnusedLocalVariableRecords(int a, int b) {
    public InputUnusedLocalVariableRecords {
        int ab = 12; // ok
        ab += a;
        int var1 = 1; // violation
        new nested() {
            void method() {
                var1 += 12;
            }
        };
    }

    static Predicate<Integer> obj = a -> {
        int var1 = 1; // violation
        nested obj = new nested() { // violation
            void method() {
                var1 += 12;
            }
        };
        return a == 12;
    };

    public InputUnusedLocalVariableRecords(int a) {
        this(a, -1);
        int var2 = 12; // violation
        inner obj = new InputUnusedLocalVariableRecords.inner() {
            {
                var2 += 12;
            }
        };
        obj.getClass();
    }


    static class nested {
        int var1 = 12;
    }

    class inner {
        int var2 = 1;
    }
}

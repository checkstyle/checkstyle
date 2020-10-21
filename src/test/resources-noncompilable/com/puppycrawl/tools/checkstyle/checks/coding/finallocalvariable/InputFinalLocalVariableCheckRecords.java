//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

/* Config:
 * default
 */
public record InputFinalLocalVariableCheckRecords(boolean t, boolean f) {
    public InputFinalLocalVariableCheckRecords {
        int a = 0; // ok
        a = 1;
    }

    record bad(int i) {
        public bad {
            int b = 0; // violation
        }
    }
}


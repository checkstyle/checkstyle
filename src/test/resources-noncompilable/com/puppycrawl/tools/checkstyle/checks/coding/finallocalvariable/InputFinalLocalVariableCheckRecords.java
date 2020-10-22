//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

/* Config:
 * None
 */
public record InputFinalLocalVariableCheckRecords(boolean t, boolean f) {
    public InputFinalLocalVariableCheckRecords { // ok
        int a = 0;
        a = 1;
    }
}

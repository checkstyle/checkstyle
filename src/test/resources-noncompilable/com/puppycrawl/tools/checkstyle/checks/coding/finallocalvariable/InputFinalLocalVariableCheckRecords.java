//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

/* Config:
 * None
 */
public record InputFinalLocalVariableCheckRecords(boolean t, boolean f) {
    public MyRecord { // ok
        int a = 0;
        a = 1;
    }
}

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.finalclass;

/* Config:
 *
 * default
 */
public record InputFinalClassConstructorInRecord(String string) { // ok

    public InputFinalClassConstructorInRecord {
    }
    public InputFinalClassConstructorInRecord(int x) {
        this(String.valueOf(x));
    }
}

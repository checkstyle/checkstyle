//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

/* Config:
 *
 * default
 */
public record InputDesignForExtensionRecords(String string) { // ok

    @Override
    public String toString() {
        return string + "my string!";
    }
}

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentname;

/* Config:
 * format = ^[a-z][a-zA-Z0-9]*$
 */
public class InputXpathRecordComponentNameFormat {
    public record MyRecord(int _underscoreValue, // ok
                           int otherValue) { } // warn
}

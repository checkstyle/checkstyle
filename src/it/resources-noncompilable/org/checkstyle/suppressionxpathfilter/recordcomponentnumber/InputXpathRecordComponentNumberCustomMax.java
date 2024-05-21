//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 *
 * max = 1
 */
public class InputXpathRecordComponentNumberCustomMax {
    public record MyRecord(int x, int y) { } // warn
}

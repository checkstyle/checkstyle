//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 *
 * max = 1
 */
public class InputXpathRecordComponentNumber2 {
    public record MyRecord(int x, int y) { } // warn
}

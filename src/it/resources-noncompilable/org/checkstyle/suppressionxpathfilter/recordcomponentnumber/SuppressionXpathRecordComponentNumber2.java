//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.sizes.recordcomponentnumber;

/* Config:
 * maxPublic = 10
 * maxProtected = 10
 * maxPackage = 10
 * maxPrivate = 10
 *
 */
public class SuppressionXpathRecordComponentNumber2 {
    public record MyRecord(int x, int y) { } // warn
}

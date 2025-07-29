// Java17
package org.checkstyle.suppressionxpathfilter.recordcomponentnumber;

/* Config:
 *
 * max = 1
 */
public class InputXpathRecordComponentNumberCustomMax {
    public record MyRecord(int x, int y) { } // warn
}

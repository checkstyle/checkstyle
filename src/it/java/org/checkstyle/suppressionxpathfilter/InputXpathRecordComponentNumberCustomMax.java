package org.checkstyle.suppressionxpathfilter;

/* Config:
 *
 * max = 1
 */
public class InputXpathRecordComponentNumberCustomMax {
    public record MyRecord(int x, int y) { } // warn
}

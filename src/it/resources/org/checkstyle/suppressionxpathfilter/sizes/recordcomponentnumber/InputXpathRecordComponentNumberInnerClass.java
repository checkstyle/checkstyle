// Java17
package org.checkstyle.suppressionxpathfilter.sizes.recordcomponentnumber;

/* Config:
 *
 * max = 8
 */
public class InputXpathRecordComponentNumberInnerClass {
    class Inner {
        public record MyRecord(int x, int y, int z, // warn
                               int a, int b, int c,
                               int d, int e, int f,
                               int g, int h, int i,
                               int j) { }
    }
}

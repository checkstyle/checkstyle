/*
RecordComponentNumber
max = (default)8
accessModifiers = (default)public, protected, package, private


*/

package org.checkstyle.suppressionxpathfilter.sizes.recordcomponentnumber;

public class InputXpathRecordComponentNumberInnerClass {
    class Inner {
        // violation below 'Number of record components is 13 (max allowed is 8).'
        public record MyRecord(int x, int y, int z,
                               int a, int b, int c,
                               int d, int e, int f,
                               int g, int h, int i,
                               int j) { }
    }
}

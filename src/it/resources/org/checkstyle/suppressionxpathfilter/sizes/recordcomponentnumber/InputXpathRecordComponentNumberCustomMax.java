/*
RecordComponentNumber
max = 1
accessModifiers = (default)public, protected, package, private


*/

package org.checkstyle.suppressionxpathfilter.sizes.recordcomponentnumber;

public class InputXpathRecordComponentNumberCustomMax {
    // violation below 'Number of record components is 2 (max allowed is 1).'
    public record MyRecord(int x, int y) { }
}

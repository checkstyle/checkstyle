// Java17
package org.checkstyle.suppressionxpathfilter.naming.recordtypeparametername;

public class InputXpathRecordTypeParameterNameNested {
    class Inner {
        record MyRecord<abc>(Integer x, String str) { } // warn
    }
}

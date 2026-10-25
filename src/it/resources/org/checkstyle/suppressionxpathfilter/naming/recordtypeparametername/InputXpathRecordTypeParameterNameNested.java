/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordtypeparametername;

public class InputXpathRecordTypeParameterNameNested {
    class Inner {
        // violation below 'Name 'abc' must match pattern '^[A-Z]$'.'
        record MyRecord<abc>(Integer x, String str) { }
    }
}

/*
RecordTypeParameterName
format = (default)^[A-Z]$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordtypeparametername;

public class InputXpathRecordTypeParameterNameNested {
    class Inner {
        record MyRecord<abc>(Integer x, String str) { } // violation 'Name 'abc' must match pattern '^[A-Z]$'.'
    }
}

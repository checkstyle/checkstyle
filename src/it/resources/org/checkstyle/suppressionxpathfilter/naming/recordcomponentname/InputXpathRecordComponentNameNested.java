/*
RecordComponentName
format = (default)^[a-z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordcomponentname;

public class InputXpathRecordComponentNameNested {
    class Inner {
        // violation below "Name '_value' must match pattern '^[a-z][a-zA-Z0-9]*$'."
        public record MyRecord(int _value) { }
    }
}

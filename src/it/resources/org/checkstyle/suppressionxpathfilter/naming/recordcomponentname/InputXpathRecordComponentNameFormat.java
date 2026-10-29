/*
RecordComponentName
format = ^_[a-z][a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.recordcomponentname;

public class InputXpathRecordComponentNameFormat {
    public record MyRecord(int _underscoreValue,
        // violation below "Name 'otherValue' must match pattern '^_[a-z][a-zA-Z0-9]*$'."
        int otherValue) { }
}

/*
RecordComponentName
format = ^[a-z0-9]+$


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

public record InputRecordComponentNameLowercase(Integer x, String str, Double val123) {
}

record LowercaseFirstRecord(String value_123, // violation
    String... Values) { // violation
}

record LowercaseSecondRecord(String V, // violation
    int $age) { // violation
}

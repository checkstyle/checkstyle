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

record LowercaseFirstRecord(String value_123, // violation, 'Name 'value_123' must match pattern'
    String... Values) { // violation, 'Name 'Values' must match pattern'
}

record LowercaseSecondRecord(String V, // violation, 'Name 'V' must match pattern'
    int $age) { // violation, 'Name '\$age' must match pattern'
}

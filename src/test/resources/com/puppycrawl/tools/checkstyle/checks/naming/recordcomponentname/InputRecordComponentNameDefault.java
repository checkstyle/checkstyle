/*
RecordComponentName
format = (default)^[a-z][a-zA-Z0-9]*$


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

public record InputRecordComponentNameDefault(int x, String str, Double myValue1, int i1) {
}

record DefaultFirstRecord(String value_123, // violation, 'Name 'value_123' must match pattern'
    String... Values) { // violation, 'Name 'Values' must match pattern'
}

record DefaultSecondRecord(String _value123, // violation, 'Name '_value123' must match pattern'
    int $age) { // violation, 'Name '\$age' must match pattern'
}

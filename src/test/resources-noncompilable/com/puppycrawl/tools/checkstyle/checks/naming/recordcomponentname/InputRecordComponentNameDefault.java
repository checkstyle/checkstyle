/*
RecordComponentName
format = (default)^[a-z][a-zA-Z0-9]*$


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

public record InputRecordComponentNameDefault(int x, String str, Double myValue1, int i1) { // ok
}

record DefaultFirstRecord(String value_123, // violation
    String... Values) { // violation
}

record DefaultSecondRecord(String _value123, // violation
    int $age) { // violation
}

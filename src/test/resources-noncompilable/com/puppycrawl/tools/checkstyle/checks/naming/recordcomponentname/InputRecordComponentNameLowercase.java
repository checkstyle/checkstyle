/*
RecordComponentName
format = ^[a-z0-9]+$


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

public record InputRecordComponentNameLowercase(Integer x, String str, Double val123) { // ok
}

record LowercaseFirstRecord(String value_123, // violation
    String... Values) { // violation
}

record LowercaseSecondRecord(String V, // violation
    int $age) { // violation
}

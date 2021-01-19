//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.recordcomponentname;

import java.io.Serializable;
import java.util.LinkedHashMap;

import org.w3c.dom.Node;

/* Config:
 * pattern = "^[a-z\\d]+$"
 */
public record InputRecordComponentNameLowercase(Integer x, String str, Double val123) { // ok
}

record LowercaseFirstRecord(String value_123, // violation
    String... Values) { // violation
}

record LowercaseSecondRecord(String V, // violation
    int $age) { // violation
}

/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;
import java.util.Map;

public record InputRequireThisRecordCompactCtors(String name, Map<String, String> items){ // ok
    // 'this' cannot be used in compact constructor
    public InputRequireThisRecordCompactCtors {
        if (name == null) { // ok
            name = "<unknown>"; // ok
        }
        items = Map.copyOf(items); // ok
    }
}

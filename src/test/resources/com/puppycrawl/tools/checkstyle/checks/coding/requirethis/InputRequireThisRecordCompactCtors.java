/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;
import java.util.Map;

public record InputRequireThisRecordCompactCtors(String name, Map<String, String> items){
    // 'this' cannot be used in compact constructor
    public InputRequireThisRecordCompactCtors {
        if (name == null) {
            name = "<unknown>";
        }
        items = Map.copyOf(items);
    }
}

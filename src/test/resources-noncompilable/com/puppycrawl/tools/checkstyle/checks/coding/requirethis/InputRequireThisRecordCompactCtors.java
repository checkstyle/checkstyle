//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;
import java.util.Map;

/* Config:
 * validateOnlyOverlapping = false
 *
 */
public record InputRequireThisRecordCompactCtors(String name, Map<String, String> items){
    // 'this' cannot be used in compact constructor
    public InputRequireThisRecordCompactCtors {
        if (name == null) { // ok
            name = "<unknown>"; // ok
        }
        items = Map.copyOf(items); // ok
    }
}

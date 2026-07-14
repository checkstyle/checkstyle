/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariablesRecordPatternBinding {
    private String component;
    private String binding;

    record R(String component) { }

    public void test(Object value) {
        if (value instanceof R(String component) binding) {
            System.out.println(component);
            System.out.println(binding);
        }
        else {
            component = "field"; // violation '.*variable 'component'.*'
            binding = "field"; // violation '.*variable 'binding'.*'
        }
    }
}

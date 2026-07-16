/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java19
// Named record patterns were removed in Java 20, see https://openjdk.org/jeps/432.
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariablesRecordPatternBinding {
    private String component;
    private String binding;

    record R(String component) { }
    record Empty() { }

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

    public void testEmptyRecordPattern(Object value) {
        if (value instanceof Empty() component) {
            System.out.println(component);
        }
    }

    public void testTrueBranchIntersection(Object first, Object second, Object third) {
        if ((first instanceof String component && second instanceof String binding)
                || third instanceof String component) {
            System.out.println(component);
            System.out.println(binding); // violation '.*variable 'binding'.*'
        }
    }

    public void testFalseBranchIntersection(Object first, Object second, Object third) {
        if ((!(first instanceof String component) || !(second instanceof String binding))
                && !(third instanceof String component)) {
            System.out.println();
        }
        else {
            System.out.println(component);
            System.out.println(binding); // violation '.*variable 'binding'.*'
        }
    }
}

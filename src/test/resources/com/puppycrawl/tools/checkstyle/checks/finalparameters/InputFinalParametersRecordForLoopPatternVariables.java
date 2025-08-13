/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = PATTERN_VARIABLE_DEF,FOR_EACH_CLAUSE


*/

// Java20
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersRecordForLoopPatternVariables {
    record ARecord(String name, int age) {
    }
    static void method(final ARecord[] records) {
        for (ARecord rec : records) { // violation, 'name' should be final
            String name = rec.name();
            final int age = rec.age();
        }
        for (ARecord rec : records) { // violation, 'name' should be final
            final String name = rec.name();
            final int age = rec.age();
        }
    }
}

/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = PATTERN_VARIABLE_DEF,FOR_EACH_CLAUSE


*/

// non-compiled with javac: Compilable with Java20
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersRecordForLoopPatternVariables {
    record ARecord(String name, int age) {
    }
    static void method(final List<ARecord> records) {
        for (ARecord(String name, final int age) : records) { // violation, 'name' should be final
        }
    }
}

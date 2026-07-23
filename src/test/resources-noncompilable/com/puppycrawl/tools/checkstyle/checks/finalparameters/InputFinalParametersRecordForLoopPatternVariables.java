/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = PATTERN_VARIABLE_DEF,FOR_EACH_CLAUSE


*/

// non-compiled with javac: but was compiled on jdk before 21, so we need to continue to support
package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersRecordForLoopPatternVariables {
    record ARecord(String name, int age) {
    }
    static void method(final ARecord[] records) {
        for (ARecord(String name, final int age) : records) { // violation 'name' should be final
        }
        for (ARecord(final String name, final int age) : records) {
        }
    }
}

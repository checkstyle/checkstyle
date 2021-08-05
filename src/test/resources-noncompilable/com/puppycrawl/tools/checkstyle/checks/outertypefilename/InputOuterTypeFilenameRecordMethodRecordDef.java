/*
OuterTypeFilename


*/

//non-compiled with javac: contains different class name by demand of test
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

public record IncorrectName2(int x, int y, String str) { // violation
    class LocalRecordHelper {
        Record recordMethod(int x) {
            record R76 (int x) { }  // Issue #8598: OuterTypeFileName
                                    // throws NPE on record definition in method
            return new R76(42);
        }
    }
}

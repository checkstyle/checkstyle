/*
OuterTypeFilename


*/

// non-compiled with javac: contains different class name by demand of test
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;
// violation below 'The name of the outer type and the file do not match.'
public record IncorrectName2(int x, int y, String str) {
    class LocalRecordHelper {
        Record recordMethod(int x) {
            record R76 (int x) { }  // Issue #8598: OuterTypeFileName
                                    // throws NPE on record definition in method
            return new R76(42);
        }
    }
}

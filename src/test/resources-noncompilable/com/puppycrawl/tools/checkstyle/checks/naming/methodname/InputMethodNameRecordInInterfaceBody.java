/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public interface InputMethodNameRecordInInterfaceBody {
    int record = 1;
    static record MyRedundantStaticRecord() {}
    record MyRecord1(){}
    record MyRecord2(int x, int y, int z){}
    record MyRecord3(int[][] x, String... z){
        String record() {
            return null;
        }
        void VIOLATION() { // violation 'VIOLATION' must match the pattern

        }
    }
    default void record(int x, int y, int z) {}
}

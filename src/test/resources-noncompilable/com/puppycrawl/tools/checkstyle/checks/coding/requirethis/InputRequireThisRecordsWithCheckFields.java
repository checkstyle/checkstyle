/*
RequireThis
checkFields = true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public record InputRequireThisRecordsWithCheckFields(int a) {
    void method() {
        String a = "BUG";
        a = a.substring(0, 1); // violation 'Reference to instance variable 'a' needs "this."'
    }

    void method2() {
        String a = "BUG".substring(0, 1); // ok
    }
}
class InputRequireThisRecordsWithCheckFieldsClass {
    int a;

    public InputRequireThisRecordsWithCheckFieldsClass(int a) {
        this.a = a;
    }

    void method() {
        String a = "BUG";
        a = a.substring(0, 1); // violation 'Reference to instance variable 'a' needs "this."'
    }

    void method2() {
        String a = "BUG".substring(0, 1); // ok
    }

    public int a() {
        return a;
    }
}

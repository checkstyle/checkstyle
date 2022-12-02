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
        a = a.substring(0, 1); // ok, field 'a' is final and cannot be changed
    }
}
class InputRequireThisRecordsWithCheckFieldsClass {
    public final int a;

    public InputRequireThisRecordsWithCheckFieldsClass(int a) {
        this.a = a;
    }

    void method() {
        String a = "BUG";
        a = a.substring(0, 1); // ok, field 'a' is final and cannot be changed
    }

    public int a() {
        return a;
    }
}

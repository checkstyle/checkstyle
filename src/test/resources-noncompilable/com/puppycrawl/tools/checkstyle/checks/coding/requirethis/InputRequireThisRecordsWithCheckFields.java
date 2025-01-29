/*
RequireThis
checkFields = true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public record InputRequireThisRecordsWithCheckFields(String a) {
    void method() {
        String a = "BUG";
        a = a.substring(0, 1); //field 'a' is final and cannot be changed
    }

    void method2() {
        String x = a; //not overlapping
        String y = this.a; //uses 'this'
        String a = this.a; //local variable assigned from field
        a += a; //local variable
    }
}
class InputRequireThisRecordsWithCheckFieldsClass {
    public final String a;

    public InputRequireThisRecordsWithCheckFieldsClass(String a) {
        this.a = a;
    }

    void method() {
        String a = "BUG";
        a = a.substring(0, 1); //field 'a' is final and cannot be changed
    }

    void method2() {
        String x = a; //not overlapping
        String y = this.a; //uses 'this'
        String a = this.a; //local variable assigned from field
        a += a; //local variable
    }

    public String a() {
        return a;
    }
}

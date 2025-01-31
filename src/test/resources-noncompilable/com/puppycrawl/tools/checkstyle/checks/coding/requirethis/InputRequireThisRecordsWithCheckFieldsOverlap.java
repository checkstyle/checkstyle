/*
RequireThis
checkFields = true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public record InputRequireThisRecordsWithCheckFieldsOverlap(String a) {
    void method() {
        String a = "BUG";
        a = a.substring(0, 1); //field 'a' is final and cannot be changed
    }

    void method2() {
        String x = a; // violation 'Reference to instance variable 'a' needs "this.".'
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
        String x = a; // violation 'Reference to instance variable 'a' needs "this.".'
        String y = this.a; //uses 'this'
        String a = this.a; //local variable assigned from field
        a += a; //local variable
    }

    public String a() {
        return a; // violation 'Reference to instance variable 'a' needs "this.".'
    }
}

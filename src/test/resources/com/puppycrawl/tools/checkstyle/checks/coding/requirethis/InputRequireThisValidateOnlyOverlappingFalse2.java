/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.BitSet;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InputRequireThisValidateOnlyOverlappingFalse2 {

    private static String fieldStatic = "fieldStatic";
    private final long fieldFinal1 = 0L;
    private String field1;

    String addSuffixToParameter(String methodParam) {
        return methodParam += "suffix";
    }

    String addSuf2F(String field1) {
        return field1 += "suffix";
    }

    String addSuffixToThisField(String field1) {
        return this.field1 += "suffix";
    }

    static void foo7(String fieldStatic) {
//        this.fieldStatic = fieldStatic; <- fieldStatic cannot be referenced from a static context
        fieldStatic = fieldStatic;
    }

    void foo8(Long field1) {
        field1 += field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo9(Long fieldFinal1) {
//        this.fieldFinal1 += fieldFinal1; <- cannot assign value to a final variable
        fieldFinal1 += fieldFinal1;
    }

    void method1() {
        field1 = "2"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void method2() {
        method1() ; // violation 'Method call to 'method1' needs "this.".'
    }

    void method3() {
        staticFoo();
        staticTwoArgs("message", "arg");
        staticTwoArgs("message", 1);
        this.method1() ;
    }

    static void staticFoo() { }

    static void foo10() {
        staticFoo();
        staticTwoArgs("message", "arg");
    }

//    void staticFoo() {} -> compile-time error. Already defined in the scope.

    static void staticTwoArgs(String message1, String argument) {}

    void staticTwoArgs(String message1, int argument) {}

    static void foo16() {
        long fieldFinal1 = 5L;
// this.fieldFinal1 = fieldFinal1; // compile-time error: cannot be referenced from a static context
        fieldFinal1 = 11L;
    }

    static void foo17() {
        String fieldStatic = "";
// this.fieldStatic = fieldStatic; // compile-time error: cannot be referenced from a static context
        fieldStatic = "Hello, World!";
    }


}

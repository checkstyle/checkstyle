/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.*;

public class InputRequireThisValidateOnlyOverlappingFalse {

    private static String fieldStatic = "fieldStatic";

    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;

    private String field1;
    private String field2;
    private String field3 = "some string";
    private boolean booleanField;
    private int intField;

    public InputRequireThisValidateOnlyOverlappingFalse(String field1) {
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0; // violation '.* variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }
    public InputRequireThisValidateOnlyOverlappingFalse() {
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        long fieldFinal2 = 0L;
        fieldFinal2 = 1L;
        this.fieldFinal2 = fieldFinal2;
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse(String name, long id) {
        fieldFinal1 = 0; // violation '.* variable 'fieldFinal1' needs "this.".'
        long field1 = 0L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        this.fieldFinal2 = 1L;
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
    }

    public InputRequireThisValidateOnlyOverlappingFalse(int param) {
        fieldFinal2 = 0L; // violation '.* variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long fieldFinal1 = 1L;
        try {
            fieldFinal1 = 2L;
        }
        catch (Exception ex) {}
        this.fieldFinal1 = fieldFinal1;
    }

    public InputRequireThisValidateOnlyOverlappingFalse(BitSet fieldFinal3) {
        fieldFinal1 = 1L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet();
        if (true) {
            fieldFinal3 = (BitSet) fieldFinal3.clone();
        }
        this.fieldFinal3 = fieldFinal3;
    }

    void foo3() {
        String field1 = "values";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo8(Long field1) {
        field1 += field1; // violation 'Reference to instance variable 'field1' needs "this.".'
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

    static void staticTwoArgs(String message1, String argument) {}

    void staticTwoArgs(String message1, int argument) {}

    void foo24() {
        String field1 = "Hello";
        field1 = "Java"; // No violation. Local var allowed
        this.booleanField = true;
        this.booleanField = booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }


    void foo26(String field1) {
        field1 = field1.replace('/', '*'); // violation '.* variable 'field1' needs "this.".'
    }

    void foo28() {
        boolean booleanField = true;
        booleanField = !booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }
}

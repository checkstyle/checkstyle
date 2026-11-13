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

public class InputRequireThisValidateOnlyOverlappingFalse3 {

    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;
    private String field1;
    private boolean booleanField;
    private int intField;

    public InputRequireThisValidateOnlyOverlappingFalse3() {
        this.fieldFinal1 = 0L;
        this.fieldFinal2 = 0L;
        this.fieldFinal3 = new BitSet();
    }
    InputRequireThisValidateOnlyOverlappingFalse3(boolean flag) {
        fieldFinal1 = 0L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    InputRequireThisValidateOnlyOverlappingFalse3(boolean flag, String name) {
        fieldFinal1 = 0L; // violation 'Reference to instance variable 'fieldFinal1' needs "this.".'
        fieldFinal2 = 0L; // violation 'Reference to instance variable 'fieldFinal2' needs "this.".'
        fieldFinal3 = new BitSet(); // violation '.* variable 'fieldFinal3' needs "this.".'
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        return;
    }

    void foo18() {
        field1 = "Hello"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo19(String field1) {
        field1 = "Hello"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo20() {
        boolean foo21 = this.foo21("");
        if (foo21) {

        }
    }

    boolean foo21(String s) {
        return true;
    }

    void foo22() {
        long fieldFinal1 = 1L;
//        this.fieldFinal1 = fieldFinal1; <- cannot assign value to a final variable
        fieldFinal1 = fieldFinal1;
    }

    void foo23() {
        field1 = "Hello!"; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo24() {
        String field1 = "Hello";
        field1 = "Java"; // ok, Local var allowed
        this.booleanField = true;
        this.booleanField = booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }

    void foo25() {
        try {
            if (true) {
                String field1 = "Hello, World!";
                if (true) {
                    field1 = new String(); // ok, Local var allowed
                }
                else {
                    field1 += field1; // violation '.* variable 'field1' needs "this.".'
                }
            }
        }
        catch (Exception ex) {

        }
    }


}

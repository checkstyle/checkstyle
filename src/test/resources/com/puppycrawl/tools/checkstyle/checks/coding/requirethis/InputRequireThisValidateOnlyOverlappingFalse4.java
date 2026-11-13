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

public class InputRequireThisValidateOnlyOverlappingFalse4 {

    private static String fieldStatic = "fieldStatic";
    private String field1;
    private boolean booleanField;
    private int intField;

    String addSuf2F(String field1) {
        return field1 + "suffix";
    }
    void foo26(String field1) {
        field1 = field1.replace('/', '*'); // violation '.* variable 'field1' needs "this.".'
    }

    void foo27() {
        int intField = -1;
        if (intField == -1) {
            intField = 20;
        }
        else {
            intField = this.intField / 100;
        }
    }

    void foo28() {
        boolean booleanField = true;
        booleanField = !booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }

    static void foo29(String field1) {
//this.field1 = true ? "field1" : field1; /compile error: cannot be referenced from a static context
        field1 = true ? "field1" : field1;
    }

    void foo30(String field1) {
        field1 = true ? "field1" : field1; // violation '.* variable 'field1' needs "this.".'
    }

    void foo31(String field1) {
        field1 = this.field1;
    }

    String foo32(String field1) {
        field1 = addSuf2F(field1); // violation 'Method call to 'addSuf2F' needs "this.".'
        return field1;
    }

    String foo33(String field1 ) {
        field1 = addSuf2F(field1);
        // 2 violations above:
        //    'Reference to instance variable 'field1' needs "this.".'
        //    'Method call to 'addSuf2F' needs "this.".'
        return "New String";
    }

    String foo34(String field1) {
        field1 = field1.replace('A', 'B');
        if (field1.contains("C")) {
            return field1;
        }
        else {
            return field1 + 'C';
        }
    }

    String foo35() {
        String field1 = "values";
        field1 = field1;
        return field1;
    }

    void foo36(String field1) {
        field1 = field1.replace('/', '*');
        field1 = this.field1;
    }

    String foo37(String field1) {
        field1 += "suffix"; // violation 'Reference to instance variable 'field1' needs "this.".'
        return "New string";
    }

    static void foo38() {
//        this.fieldStatic = ""; <-- compile-time error: cannot be referenced from a static context
        fieldStatic = "";
    }

    static void foo39() {
        boolean booleanField = true;
// this.booleanField = !booleanField; compile-time error: cannot be referenced from a static context
        booleanField = !booleanField;
    }


}

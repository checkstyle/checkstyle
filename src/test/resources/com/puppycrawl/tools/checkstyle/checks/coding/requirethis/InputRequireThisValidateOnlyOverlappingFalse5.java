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

public class InputRequireThisValidateOnlyOverlappingFalse5 {

    private static String fieldStatic = "fieldStatic";
    private final long fieldFinal1 = 0L;
    private String field1;
    static void foo40() {
        try {
            boolean booleanField = true;
//this.booleanField = !booleanField; <-- compile error: cannot be referenced from a static context
            booleanField = !booleanField;
        }
        catch (Exception e) {}
    }

    static {
//        this.fieldStatic = ""; <-- compile-time error: cannot be referenced from a static context
        fieldStatic = "";
    }

    {
//        if we assign variable to a final variable in initialization block,
//        it will lead to compile-time error in constructor block: variable migh have been
//        already assigned

//        fieldFinal1 = 1;
    }

    {
        String field1 = "";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    static {
        fieldStatic = "";
        String field1 = "";
//        this.field1 = field1; <-- compile-time error: cannot be referenced from a static context
        field1 = field1;
    }

    void foo41(long fieldFinal1) {
//        this.fieldFinal1 = 1L; <- cannot assign value to a final variable
        fieldFinal1 = fieldFinal1;
    }

    void foo42(String fieldStatic) {
        this.fieldStatic = fieldStatic;
    }

    void foo43(String fieldStatic) {
        fieldStatic = fieldStatic;
    }

    void foo44(String fieldStatic) {
        fieldStatic = this.fieldStatic;
    }

    private String action;

    public String getAction() {
        return this.action;
    }

    public String foo45() {
        String action = getAction(); // violation 'Method call to 'getAction' needs "this.".'
        if (true) {
            return processAction("action"); // violation 'Method .* 'processAction' needs "this.".'
        }
        else if (action.endsWith("/")) {
            if (action.startsWith("/")) {
                action = "" + action;
            }
        }
        action = "action"; // ok, Local var allowed
        return processAction(action); // violation 'Method call to 'processAction' needs "this.".'
    }

    private String processAction(String action) {
        return "";
    }


}

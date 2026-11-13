/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.BitSet;

public class InputRequireThisValidateOnlyOverlappingTrue4 {

    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;
    private String field1;
    private boolean booleanField;
    private int intField;

    public InputRequireThisValidateOnlyOverlappingTrue4() {
        fieldFinal1 = 0L;
        fieldFinal2 = 0L;
        fieldFinal3 = new BitSet();
    }
    InputRequireThisValidateOnlyOverlappingTrue4(boolean flag) {
        fieldFinal1 = 0L;
        fieldFinal2 = 0L;
        fieldFinal3 = new BitSet();
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    InputRequireThisValidateOnlyOverlappingTrue4(boolean flag, String name) {
        fieldFinal1 = 0L;
        fieldFinal2 = 0L;
        fieldFinal3 = new BitSet();
        long field1 = 1L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        return;
    }

    void foo18() {
        field1 = "Hello";
    }

    void foo19(String field1) {
        field1 = "Hello";
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
        field1 = "Hello!";
    }

    void foo24() {
        String field1 = "Hello";
        field1 = "Java";
        this.booleanField = true;
        this.booleanField = booleanField;
    }

    void foo25() {
        try {
            if (true) {
                String field1 = "Hello, World!";
                if (true) {
                    field1 = new String();
                }
                else {
                    field1 = new String();
                }
            }
        }
        catch (Exception ex) {

        }
    }


}

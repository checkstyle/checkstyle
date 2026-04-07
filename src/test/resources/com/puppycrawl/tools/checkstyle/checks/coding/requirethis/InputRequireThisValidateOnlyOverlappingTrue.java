/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.BitSet;

public class InputRequireThisValidateOnlyOverlappingTrue {

    private static String fieldStatic = "fieldStatic";

    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;

    private String field1;
    private String field2;
    private String field3 = "some string";
    private boolean booleanField;
    private int intField;

    public InputRequireThisValidateOnlyOverlappingTrue(String field1) {
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        fieldFinal1 = 0;
        fieldFinal2 = 0;
        fieldFinal3 = new BitSet();
    }
    public InputRequireThisValidateOnlyOverlappingTrue() {
        fieldFinal1 = 0;
        long fieldFinal2 = 0L;
        fieldFinal2 = 1L;
        this.fieldFinal2 = fieldFinal2;
        fieldFinal3 = new BitSet();
    }

    public InputRequireThisValidateOnlyOverlappingTrue(String name, long id) {
        fieldFinal1 = 0;
        long field1 = 0L;
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
        this.fieldFinal2 = 1L;
        fieldFinal3 = new BitSet();
    }

    public InputRequireThisValidateOnlyOverlappingTrue(int param) {
        fieldFinal2 = 0L;
        fieldFinal3 = new BitSet();
        long finalField1 = 1L;
        try {
            finalField1 = 2L;
        }
        catch (Exception ex) {}
        this.fieldFinal1 = finalField1;
    }

    public InputRequireThisValidateOnlyOverlappingTrue(BitSet fieldFinal3) {
        fieldFinal1 = 1L;
        fieldFinal2 = 0L;
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

    String addSuf2F(String field1) {
        return field1 += "suffix";
    }

    void foo8(Long field1) {
        field1 += field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

    void foo26(String field1) {
        field1 = field1.replace('/', '*'); // violation '.* variable 'field1' needs "this.".'
    }

    void foo28() {
        boolean booleanField = true;
        booleanField = !booleanField; // violation '.* variable 'booleanField' needs "this.".'
    }

    void foo30(String field1) {
        field1 = true ? "field1" : field1; // violation '.* variable 'field1' needs "this.".'
    }

    String foo33(String field1) {
        field1 = addSuf2F(field1); // violation '.* variable 'field1' needs "this.".'
        return "New String";
    }

    String foo37(String field1) {
        field1 += "suffix"; // violation 'Reference to instance variable 'field1' needs "this.".'
        return "New string";
    }

    {
        String field1 = "";
        field1 = field1; // violation 'Reference to instance variable 'field1' needs "this.".'
    }

}

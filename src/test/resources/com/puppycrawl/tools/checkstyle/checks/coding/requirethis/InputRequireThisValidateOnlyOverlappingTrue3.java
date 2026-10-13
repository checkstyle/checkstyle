/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisValidateOnlyOverlappingTrue3 {
    private String field1;
    private boolean booleanField;

    void foo58(String field1) {
        field1 = field1;
        if (true) {
            long temp = 0L;
        }
        if (true) {
            long temp = 1L;
        }
        if (true) {
            long temp = 2L;
        }
        if (true) {
            long temp = 3L;
        }
        this.field1 = field1;
    }

    String foo59(String field1) {
        field1 = field1;
        if (true) {
            long temp = 0L;
        }
        if (true) {
            long temp = 1L;
        }
        if (true) {
            long temp = 2L;
        }
        if (true) {
            long temp = 3L;
        }
        return field1;
    }

    boolean foo60(boolean booleanField) {
        if (booleanField) {
            booleanField = false;
        }
        if (booleanField) {
            booleanField = false;
        }
        if (booleanField) {
            booleanField = false;
        }
        return booleanField;
    }
}

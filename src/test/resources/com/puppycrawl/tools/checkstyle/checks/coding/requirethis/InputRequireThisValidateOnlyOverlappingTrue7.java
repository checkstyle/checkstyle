/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.BitSet;

public class InputRequireThisValidateOnlyOverlappingTrue7 {

    private static String fieldStatic = "fieldStatic";
    private final long fieldFinal1;
    private final long fieldFinal2;
    private final BitSet fieldFinal3;
    private boolean booleanField;

    public InputRequireThisValidateOnlyOverlappingTrue7() {
        fieldFinal1 = 0L;
        fieldFinal2 = 0L;
        fieldFinal3 = new BitSet();
    }
    public InputRequireThisValidateOnlyOverlappingTrue7(long fieldFinal1, long fieldFinal2,
                                                       BitSet fieldFinal3, boolean booleanField) {
        this.fieldFinal1 = fieldFinal1;
        this.fieldFinal2 = fieldFinal2;
        this.fieldFinal3 = fieldFinal3;

        booleanField = this.booleanField;
        if (booleanField) {
            booleanField = "Hello, World!".equals("Hello, Checkstyle!");
        }

        this.booleanField = booleanField;
    }

    void foo46(boolean booleanField) {
        booleanField = this.booleanField;
        if (booleanField) {
            booleanField = "Hello, World!".equals("Hello, Checkstyle!");
        }

        this.booleanField = booleanField;
    }

    static void foo47(String fieldStatic) {
        fieldStatic = "Andrei";
    }

    void foo48(long fieldFinal1) {
        fieldFinal1 = 1L;
    }

    private boolean foo49(boolean booleanField) {
        boolean suppressionSourceExists = true;
        try {

        }
        catch (Exception ex) {
            suppressionSourceExists = false;
        }
        finally {
            if (booleanField) {
                try {
                }
                catch (Exception ignored) {
                    this.booleanField = false;
                }
            }
        }
        return suppressionSourceExists;
    }

    void foo50(String fieldStatic) {
        fieldStatic = fieldStatic;
    }

    void foo51(String methodParam) {
        fieldStatic = methodParam;
    }

    void foo52(String fieldStatic) {
        fieldStatic += fieldStatic;
    }

    void foo53(String fieldStatic) {
        fieldStatic += fieldStatic;
    }

    void foo54(String methodParam) {
        fieldStatic += methodParam;
    }

    void foo55(String methodParam) {
        fieldStatic += fieldStatic;
    }

    void foo56(boolean booleanField) { booleanField = this.booleanField; }

    boolean foo57(boolean booleanField) { booleanField = !booleanField;  return booleanField; }
}

/*
UnusedPrivateField
checkUnusedPrivateField = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField3 {
    public int publicField; //ok
    private int usedField; // ok
    private int copyfield;

    void setCopyfield(){
        int copy = copyfield;
    }

    private static final int CONSTANT = 10; //ok
    void useField() {
        System.out.println(usedField);
    }

    void methodWithLocal() {
        int localVar = 10;
        System.out.println(localVar);
    } // ok
}

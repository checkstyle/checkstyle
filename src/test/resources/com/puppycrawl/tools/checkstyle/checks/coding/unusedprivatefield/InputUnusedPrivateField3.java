/*
UnusedPrivateField
checkUnusedPrivateField = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField3 {
    public int publicField; //ok, as the field is public
    private int usedField; // ok,private field is used
    private int copyfield;

    void setCopyfield(){
        int copy = copyfield;
    }

    private static final int CONSTANT = 10;
    void useField() {
        System.out.println(usedField);
    }

    void methodWithLocal() {
        int localVar = 10;
        System.out.println(localVar);
    }
}

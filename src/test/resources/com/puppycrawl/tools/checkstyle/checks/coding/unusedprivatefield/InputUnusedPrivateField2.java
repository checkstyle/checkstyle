/*
UnusedPrivateField
checkUnusedPrivateField = false

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField2 {
    private int unused; // ok, as private field is false

    private int used;


    private int getUsed(){
        return used;
    }

    private static final int CONSTANT = 10; // ok, constant is used

    void foo() {
       System.out.println(used); // ok, used in inner class
    }

}

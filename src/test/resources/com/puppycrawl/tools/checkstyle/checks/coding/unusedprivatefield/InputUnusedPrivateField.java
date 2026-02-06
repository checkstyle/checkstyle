/*
UnusedPrivateField
checkUnusedPrivateField = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField {

    private int unused; // violation, unused private field

    private int used;

    private int getUsed(){
        return used;
    }

    private static final int CONSTANT = 10; // ok, constant is used

    void foo() {
       System.out.println(used); // ok, used in inner class
    }
    class Inner {
        private int innerUnused; // violation, unused private field
        private int innerUsed;
        void useInner() { System.out.println(innerUsed); }
    }

    private static int notfield; // violation, unused private field
}


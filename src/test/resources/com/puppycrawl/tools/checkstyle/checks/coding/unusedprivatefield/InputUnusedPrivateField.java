/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField {

    private int unused; // violation, 'Unused private field 'unused''
    private int usedInMethod;
    private int assignedOnly; // violation, 'Unused private field 'assignedOnly''
    private static final int CONSTANT = 10; // violation, 'Unused private field 'CONSTANT''
    private static int staticUnused; // violation, 'Unused private field 'staticUnused''

    public void foo() {
        System.out.println(usedInMethod);
        assignedOnly = 5;
    }
}

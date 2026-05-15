/*
UnusedPrivateField

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField2 {

    private int unused; // violation, unused private field

    private int anotherUnused; // violation, unused private field

    private String yetAnother; // violation, unused private field

    public void someMethod() {
        System.out.println("No fields used");
    }
}

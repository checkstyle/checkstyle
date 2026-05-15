/*
UnusedPrivateField
checkUnusedPrivateField = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField2 {

    private int unused; // ok, because checkUnusedPrivateField is false

    private int anotherUnused;

    private String yetAnother;

    public void someMethod() {
        System.out.println("No fields used");
    }
}

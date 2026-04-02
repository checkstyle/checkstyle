/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.ArrayList;

public class InputUnusedPrivateFieldQualifiedNew {

    private int used;
    private int unused; // violation, 'Unused private field 'unused''

    public void foo() {
        Object list = new java.util.ArrayList();
        int x = used;
    }
}

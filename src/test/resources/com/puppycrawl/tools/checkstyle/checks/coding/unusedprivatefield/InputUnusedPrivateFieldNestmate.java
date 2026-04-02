/*
UnusedPrivateField

*/
package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldNestmate {

    class Inner1 {
        private int x;
        private int y; // violation, 'Unused private field 'y''
        int getX() { return x; }
    }

    class Inner2 {
        Inner1 obj = new Inner1();
        int read = obj.x;
    }
}

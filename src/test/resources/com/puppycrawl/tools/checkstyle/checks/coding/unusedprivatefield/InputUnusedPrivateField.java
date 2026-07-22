/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField {

    private int unused; // violation, unused private field

    private int used; // ok, used in getUsed

    private int getUsed(){
        return used;
    }

    private static final int CONSTANT = 10; // violation, 'Unused private field'

    void foo() {
       System.out.println(used); // ok, used in inner class
    }
    class Inner {
        private int innerUnused; // violation, 'Unused private field'
        private int innerUsed;
        void useInner() { System.out.println(innerUsed); }
    }

    private static int field; // violation, 'Unused private field'

    private final int field2 = 1; // violation, 'Unused private field'

    protected int value; // ok, as value is not private

    class Test {
        public int value; // ok, as value is public
    }

    @interface test2 {
        int value = 0; // ok, as it is not field
    }

    interface Testthree {
        int value = 0; // ok as it is not field
    }

    private int x; // violation, 'Unused private field'

    void m() {
        int x = 0; // ok, it is is not field
    }
}


/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateField {

    private int unused; // violation 'Unused private field'

    private int used; // ok, private field is used

    private int getUsed(){
        return used;
    }

    private static final int CONSTANT = 10; // violation 'Unused private field'

    void foo() {
       System.out.println(used);
    }
    class Inner {
        private int innerUnused; // violation 'Unused private field'
        private int innerUsed; // ok, private field is used
        void useInner() { System.out.println(innerUsed); }
    }

    private static int field; // violation 'Unused private field'

    private final int field2 = 1; // violation 'Unused private field'

    protected int value; //ok, as the field is protected

    class Test {
        public int value;  //ok, as the field is public
    }

    @interface test2 {
        int value = 0; // ok, as it is not field
    }

    interface Testthree {
        int value = 0; // ok as it is not field
    }

    private int x; // violation 'Unused private field'

    void m() {
        int x = 0; // ok, it is is not field
    }
}


/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

public class InputUnusedPrivateFieldScopeEdgeCases {

    private int value; // ok, as value is used

    void method() {
        System.out.println(value);
    }

    private int count; // violation, 'Unused private field'

    void localShadow() {
        int count = 0;
        System.out.println(count);
    }

    abstract static class WithAbstract {
        private int size; // violation, 'Unused private field'

        abstract void process(int size);
    }

    private int flag; // violation, unused private field

    {
        int flag = 1;
        System.out.println(flag);
    }

    interface Processor {
        void execute(int limit);
    }

    private int limit; // violation, unused private field

    private int index; // violation, unused private field

    static {
        int index = 0;
        System.out.println(index);
    }
}

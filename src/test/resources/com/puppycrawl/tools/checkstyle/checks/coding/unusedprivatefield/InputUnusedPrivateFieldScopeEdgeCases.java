/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.io.Serial;

public class InputUnusedPrivateFieldScopeEdgeCases {

    private int value; // ok, private field is used

    void method() {
        System.out.println(value);
    }

    private int count; // violation 'Unused private field'

    void localShadow() {
        int count = 0;
        System.out.println(count);
    }

    abstract static class WithAbstract {
        private int size; // violation 'Unused private field'

        abstract void process(int size);
    }

    private int flag; // violation 'Unused private field'

    {
        int flag = 1;
        System.out.println(flag);
    }

    interface Processor {
        void execute(int limit);
    }

    private int limit; // violation 'Unused private field'

    private int index; // violation 'Unused private field'

    static {
        int index = 0;
        System.out.println(index);
    }

    @Serial
    private static final long serialVersionUID = 1434589190483306227L;

}

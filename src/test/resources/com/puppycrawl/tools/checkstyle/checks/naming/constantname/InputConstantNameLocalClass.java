/*
ConstantName
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

public class InputConstantNameLocalClass {

    void method() {
        class Local {
            static final int max = 1; // violation 'Name 'max' must match pattern'
            static final int MAX = 2;
            final int notStatic = 3;
            static int notFinal = 4;

            interface Nested {
                int lower = 5; // violation 'Name 'lower' must match pattern'
            }

            void inner() {
                final int localFinal = 6;
                notFinal = localFinal;
            }
        }

        interface LocalInterface {
            int lower = 7; // violation 'Name 'lower' must match pattern'
            int UPPER = 8;

            default void run() {
                final int localInDefault = 9;
                int local = localInDefault;
            }
        }

        record LocalRecord(int value) {
            static final int min = 10; // violation 'Name 'min' must match pattern'
        }
    }

    Object anon = new Object() {
        static final int anonField = 11; // violation 'Name 'anonField' must match pattern'
    };

    void anonInMethod() {
        Object o = new Object() {
            static final int anonField = 12; // violation 'Name 'anonField' must match pattern'
        };
    }

    Runnable lambda = () -> {
        class InLambda {
            static final int inLambda = 13; // violation 'Name 'inLambda' must match pattern'
        }
    };
}

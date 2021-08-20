/*
NPathComplexity
max = 1


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityRecords {

    // in compact ctor, NP=3
    record MyRecord1(boolean t, boolean f) {
    public MyRecord1 { // violation
            int i = 1;
            // NP = (if-range=1) + (else-range=2) + 0 = 3
            if (t) {
            } else if (f) {
            } else {
            }
        }

        // in method, NP=2
        public void foo() { // violation
            //NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
            while (true) {
                Runnable runnable = new Runnable() {
                    // NP = 2
                    public void run() { // violation
                        // NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
                        while (true) {
                        }
                    }
                };

                new Thread(runnable).start();
            }
        }
    }

    // in ctor NP=3
    record MyRecord2(boolean a, boolean b) {
        MyRecord2() { // violation
            this(true, false);
            int i = 1;
            // NP = (if-range=1) + (else-range=2) + 0 = 3
            if (b) {
            } else if (a) {
            } else {
            }
        }
    }
}

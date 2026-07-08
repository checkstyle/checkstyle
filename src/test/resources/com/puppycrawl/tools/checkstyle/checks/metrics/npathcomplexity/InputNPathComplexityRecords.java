/*
NPathComplexity
max = 1


*/


package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityRecords {

    // in compact ctor, NP=3
    record MyRecord1(boolean t, boolean f) {
        // violation below 'NPath Complexity is 3 (max allowed is 1).'
    public MyRecord1 {
            int i = 1;
            // NP = (if-range=1) + (else-range=2) + 0 = 3
            if (t) {
            } else if (f) {
            } else {
            }
        }

        // violation below 'NPath Complexity is 2 (max allowed is 1).'
        public void foo() {
            //NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
            while (true) {
                Runnable runnable = new Runnable() {
                    // violation below 'NPath Complexity is 2 (max allowed is 1).'
                    public void run() {
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
        // violation below 'NPath Complexity is 3 (max allowed is 1).'
        MyRecord2() {
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

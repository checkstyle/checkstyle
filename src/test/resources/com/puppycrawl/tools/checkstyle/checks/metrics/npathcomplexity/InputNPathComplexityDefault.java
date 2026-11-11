/*
NPathComplexity
max = 0


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityDefault {
    // violation below 'NPath Complexity is 2 (max allowed is 0).'
    public void foo() {
        //NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
        while (true) {
            Runnable runnable = new Runnable() {
               // violation below 'NPath Complexity is 2 (max allowed is 0).'
                public void run() {
                    // NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    // violation below 'NPath Complexity is 10 (max allowed is 0).'
    public void bar() {
        // NP = (if-range=3*3) + (expr=0) + 1 = 10
        if (System.currentTimeMillis() == 0) {
            //NP = (if-range=1) + 1 + (expr=1) = 3
            if (System.currentTimeMillis() == 0 && System.currentTimeMillis() == 0) {
            }
            //NP = (if-range=1) + 1 + (expr=1) = 3
            if (System.currentTimeMillis() == 0 || System.currentTimeMillis() == 0) {
            }
        }
    }

    // violation below 'NPath Complexity is 3 (max allowed is 0).'
    public void simpleElseIf() {
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // violation below 'NPath Complexity is 7 (max allowed is 0).'
    public void stupidElseIf() {
        // NP = (if-range=1) + (else-range=3*2) + (expr=0) = 7
        if (System.currentTimeMillis() == 0) {
        } else {
            // NP = (if-range=1) + (else-range=2) + (expr=0) = 3
            if (System.currentTimeMillis() == 0) {
            } else {
                // NP = (if-range=1) + 1 + (expr=0) = 2
                if (System.currentTimeMillis() == 0) {
                }
            }
            // NP = (if-range=1) + 1 + (expr=0) = 2
            if (System.currentTimeMillis() == 0) {
            }
        }
    }
}

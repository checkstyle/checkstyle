/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR, LITERAL_WHEN


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexity {
    // NP = 2
    public void foo() { // violation, 'Cyclomatic Complexity is 2 (max allowed is 0).'
        //NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
        while (true) {
            Runnable runnable = new Runnable() {
               // NP = 2
                public void run() { // violation, 'Cyclomatic Complexity is 2 (max allowed is 0).'
                    // NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
                    while (true) {
                    }
                }
            };

            new Thread(runnable).start();
        }
    }

    // NP = 10
    public void bar() { // violation, 'Cyclomatic Complexity is 6 (max allowed is 0).'
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

    // NP = 3
    public void simpleElseIf() { // violation, 'Cyclomatic Complexity is 3 (max allowed is 0).'
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // NP = 7
    public void stupidElseIf() { // violation, 'Cyclomatic Complexity is 5 (max allowed is 0).'
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

    // NP = 3
    // violation below, 'Cyclomatic Complexity is 3 (max allowed is 0).'
    public InputCyclomaticComplexity()
    {
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // STATIC_INIT
    // NP = 3
    static { // violation, 'Cyclomatic Complexity is 3 (max allowed is 0).'
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    // INSTANCE_INIT
    // NP = 3
    { // violation, 'Cyclomatic Complexity is 3 (max allowed is 0).'
        int i = 1;
        // NP = (if-range=1) + (else-range=2) + 0 = 3
        if (System.currentTimeMillis() == 0) {
        // NP(else-range) = (if-range=1) + (else-range=1) + (expr=0) = 2
        } else if (System.currentTimeMillis() == 0) {
        } else {
        }
    }

    /** Inner */
    // NP = 0
    // violation below, 'Cyclomatic Complexity is 1 (max allowed is 0).'
    public InputCyclomaticComplexity(int aParam)
    {
        Runnable runnable = new Runnable() {
            // NP = 2
            public void run() { // violation, 'Cyclomatic Complexity is 2 (max allowed is 0).'
                // NP(while-statement) = (while-range=1) + (expr=0) + 1 = 2
                while (true) {
                }
            }
        };
        new Thread(runnable).start();
    }
}

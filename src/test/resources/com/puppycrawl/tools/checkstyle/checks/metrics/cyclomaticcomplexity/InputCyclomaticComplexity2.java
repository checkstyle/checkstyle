/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = (default)false
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR, LITERAL_WHEN


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexity2 {
    // NP = 3
    // violation below, 'Cyclomatic Complexity is 3 (max allowed is 0).'
    public InputCyclomaticComplexity2()
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
    public InputCyclomaticComplexity2(int aParam)
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

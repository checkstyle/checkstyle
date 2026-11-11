/*
NPathComplexity
max = 0


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;
// Advise: for lack of ambiguity try to make all factors prime numbers
public class InputNPathComplexity {
    // violation below 'NPath Complexity is 5 (max allowed is 0).'
    void testIfWithExpression() {
        // NP = (if-range=1) + 1 + (expr=3) = 5
        if (true && true || (true || true)) { }
    }

    // violation below 'NPath Complexity is 5 (max allowed is 0).'
    void testIfElseWithExpression() {
        // NP = (if-range=1) + (else-range=1) + (expr=3) = 5
        if (true && true || (true || true)) { }
        else { }
    }

    // violation below 'NPath Complexity is 4 (max allowed is 0).'
    int testSimpleSwitch() {
        int a = 0;
        // NP = (case-range[1]=1) + (case-range[2]=1) + (case-range[3]=1)
        //         + (default-range=1) + (expr=0) = 4
        switch(a) {
        case 1:
            break;
        case 2:
        case 3:
            break;
        }
        return a;
    }

    // violation below 'NPath Complexity is 4 (max allowed is 0).'
    void testSimpleSwitchWithDefault() {
        int a = 0;
        // NP = (case-range[1]=1) + (case-range[2]=1) + (case-range[3]=1)
        //         + (default-range=1) + (expr=0) = 4
        switch(a) {
        case 1:
            break;
        case 2:
        case 3:
            break;
        default:
            break;
        }
    }

    // violation below 'NPath Complexity is 6 (max allowed is 0).'
    void testSwitchWithExpression() {
        int a = 0;
        // NP = (case-range[1]=1) + (case-range[2]=1) + (case-range[3]=1)
        //         + (default-range=1) + (expr=2) = 6
        switch(true ? a : a) {
        case 1:
            break;
        case 2:
        case 3:
            break;
        default:
            break;
        }
    }

    // violation below 'NPath Complexity is 15 (max allowed is 0).'
    void testComplexSwitch() {
        int a = 0;
        // NP = (case-range[1]=2) + (case-range[2]=5*2) + (case-range[3]=2)
        //         + (default-range=1) + (expr=0) = 15
        switch(a) {
        case 1:
            // NP(case-range) = (if-range=1) + 1 + (expr=0) = 2
            if (true) { }
            break;
        case 2:
            // NP(case-range) = (if-range=1) + (else-range=1) + (expr=3) = 5
            if (true && true || (true || true)) { }
            else { }
            // NP(case-range) = (if-range=1) + 1 + (expr=0) = 2
            if (true) { }
        case 3:
            // NP(case-range) = (if-range=1) + 1 + (expr=0) = 2
            if (true) { }
            break;
        default:
            break;
        }
    }
}

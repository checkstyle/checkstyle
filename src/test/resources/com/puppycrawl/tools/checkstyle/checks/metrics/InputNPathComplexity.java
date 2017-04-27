package com.puppycrawl.tools.checkstyle.checks.metrics;
// Advise: for lack of ambiguity try to make all factors prime numbers
public class InputNPathComplexity {
    //NP = 5
    void testIfWithExpression() {
        // NP = (if-range=1) + 1 + (expr=3) = 5
        if (true && true || (true || true)) { }
    }
    
    //NP = 5
    void testIfElseWithExpression() {
        // (if-range=1) + (else-range=1) + (expr=3) = 5
        if (true && true || (true || true)) { }
        else { }
    }
    
    //NP = 4
    int testSimpleSwitch() {
        int a = 0;
        // NP = (case-range(1)=1) + (case-range(1)=1) + (case-range(1)=1)
        // + (default-range=1) + (expr=0) = 4
        switch(a) {
        case 1:
            break;
        case 2:
        case 3:
            break;
        }
        return a;
    }
    
    //NP = 4
    void testSimpleSwitchWithDefault() {
        int a = 0;
        // NP = (case-range(1)=1) + (case-range(1)=1) + (case-range(1)=1)
        // + (default-range=1) + (expr=0) = 4
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
    
    //NP = 6
    void testSwitchWithExpression() {
        int a = 0;
        // NP = (case-range(1)=1) + (case-range(1)=1) + (case-range(1)=1)
        //     + (default-range=1) + (expr=2) = 6
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
    
    //NP = 15
    void testComplexSwitch() {
        int a = 0;
        // NP = (case-range(1)=2) + (case-range(2)=5*2) + (case-range(3)=2)
        //     + (default-range=1) + (expr=0) = 15
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
    
    // NP = 11   
    void testComplexIfElse() {
        // NP = (if-range=1) + (else-range=9) + (expr=1) = 11
        if (true && true) { }
        // NP(else-range) = (if-range=1) + (else-range=6) + (expr=2) = 9
        else if (true || true || true) { }
        // NP(else-range) = (if-range=1) + 1 + (expr=4) = 6
        else if (true && true && true || true || true) { }
    }
    
    // NP = 8
    boolean testComplexReturn() {
        // NP = (if-range=3) + (else-range=4) + (expr=1) = 8
        if (true && true) {
            // NP(if-range) = 3
            return true && true || (true && true);
        } else {
            // NP(else-range) = (expr(1)=0) + (expr(2)=1) + (expr(3)=1) + 2 = 4
            return true ? true && true : true || true;
        }
    }
    
    // NP = (for-statement(1)=1) * (for-statement(2)=2)
    //         * (for-statement(3)=3) * (for-statement(4)=4) = 24
    void testForCyclesComplex() {
        // NP(for-statement(1)) = (for-range=1) + (expr(1)=0) + (expr(2)=0) + (expr(3)=0) = 1
        for (int i = 0; i < 10; i++);
        // NP(for-statement(2)) = (for-range=1) + (expr(1)=0) + (expr(2)=1) + (expr(3)=0) = 2
        for (int i = 0; i < 10 && true; i++);
        // NP(for-statement(3)) = (for-range=1) + (expr(1)=2) + (expr(2)=0) + (expr(3)=0) = 3
        for (int i = true ? 0 : 0; i < 10; i++);
        // NP(for-statement(4)) = (for-range=1) + (expr(1)=0) + (expr(2)=1+2) + (expr(3)=0) = 4
        for (int i = 0; true ? i < 10 : true || true; i++);
    }
    
    // NP = (do-statement(1)=5) * (do-statement(2)=2) = 12
    void testDoWhileCyclesComplex() {
        int a = 0;
        // NP(do-statement(1)) = (do-range=1) + (expr=1) = 2
        do { } while (a < 10 && true);
        // NP(do-statement(2)) = 
        //         (do-range=2) + ((expr(1)=0) + (expr(2)=0) + (expr(3)=1) + 2) = 5
        do {
            // NP(do-range) = (do-range=1) + (expr=1) = 2
            do { } while (a < 10 || true);
        } while (true ? a > 10 : (a < 10 || true));
    }
    
    // NP = (question-statement(1)=5) * (question-statement(2)=7) = 35
    void testComplexTernaryOperator() {
        // NP(question-statement(1)) = (expr(1)=0) + (expr(2)=2) + (expr(3)=1+2) + 2 = 7
        boolean a = true ? (true ? true : true) : (false ? (true || false) : true);
        // NP(question-statement(2)) = (expr(1)=0) + (expr(2)=2) + (expr(3)=1) + 2 = 5;
        boolean b = true ? (true ? true : true) : true || true;
    }
    
    // NP = (if-expression(1)=5) * (if-expression(2)=5) = 25
    void testSimpleTernaryBadFormatting() {
        // NP(if-expression(1)) = (if-range=2) + 1 + (expr=2) = 5
        if(
           true ? true : true
                ) { boolean a = true ? true
                        : true;
        }
        // NP(if-expression(1)) = (if-range=2) + 1 + (expr=2) = 5
        if(
                true ? true : true) { boolean b = true ? true : true;
             }
    }

    //Calculation for try-catch is wrong now
    //See issue #3814 https://github.com/checkstyle/checkstyle/issues/3814
    void testTryCatch() {
       try {
       }
       catch (Exception e) {
       }
    }

}

package com.puppycrawl.tools.checkstyle.checks.metrics;
// Advise: for lack of ambiguity try to make all factors prime numbers
public class InputNPathComplexity {
    //NP = 5
    void testIfWithExpression() {
        if (true && true || (true || true)) {
            
        }
    }
    
    //NP = 5
    void testIfElseWithExpression() {
        if (true && true || (true || true)) {
            
        }
        else {
            
        }
    }
    
    //NP = 4
    void testSimpleSwitch() {
        int a = 0;
        switch(a) {
        case 1:
            break;
        case 2:
        case 3:
            break;
        }
    }
    
    //NP = 4
    void testSimpleSwitchWithDefault() {
        int a = 0;
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
        switch(a) {
        case 1:
            if (true) {
            }
            break;
        case 2:
            if (true && true || (true || true)) {
            }
            else {  
            }
            if (true) {
            }
        case 3:
            if (true) {
            }
            break;
        default:
            break;
        }
    }
    
    //NP = 11   
    void testComplexIfElse() {
        //NP = 2
        if (true && true) {
        }
        //NP += 3
        else if (true || true || true) {
        }
        //NP += 5
        else if (true && true && true || true || true) {
        }
        //NP += 1
    }
    
    //NP = 10
    boolean testComplexReturn() {
        if (true && true) {
            //NP(return) = 4
            return true && true || (true && true);
        }
        else {
            //NP(return) = 5
            return true ? true && true : true || true;
        }
    }
    
    //NP = 120
    void testForCyclesComplex() {
        //NP(for) = 2
        for (int i = 0; i < 10; i++);
        //NP(for) = 3
        for (int i = 0; i < 10 && true; i++);
        //NP(for) = 4
        for (int i = true ? 0 : 0; i < 10; i++);
        //NP(for) = 5
        for (int i = 0; true ? i < 10 : true || true; i++);
    }
    
    //NP = 21
    void testDoWhileCyclesComplex() {
        int a = 0;
        //NP(do-while) = 3
        do {
        } while (a < 10 && true);
        //NP(do-while) = 3 + 3 + 1 = 7
        do {
            //NP(do-while) = 3
            do {
            } while (a < 10 || true);
        } while (true ? a > 10 : (a < 10 || true));
    }
    
    //NP = 35
    void testComplexTernaryOperator() {
        //NP(t) = 7
        boolean a = true ? (true ? true : true) : (false ? (true || false) : true);
        //NP(t) = 5
        boolean b = true ? (true ? true : true) : true || true;
    }
    
    //NP = 25
    void testSimpleTernaryBadFormatting() {
        if(
           true ? true : true
                ) { boolean a = true ? true : true;
        }
        if(
                true ? true : true) { boolean b = true ? true : true;
             }
    }
}

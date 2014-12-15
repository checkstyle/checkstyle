package com.puppycrawl.tools.checkstyle;

public class InputBracesSingleLineIfBlock
{
    private static class SomeClass {
        boolean flag = true;
        private static boolean test(boolean k) {
            return k;
        }
    }
    
    private int foo() {
        if (SomeClass.test(true) == true) return 4; //No warning if 'mAllowSingleLineIf' is true
        return 0; 
    }
    
    private int foo1() {
        if (SomeClass.test(true) == true) return 4; int k = 3; //No warning if 'mAllowSingleLineIf' is true
        return 0; 
    }
    
    private int foo2() {
        if (SomeClass.test(true) == true) //Warning, not single-line if-statement
            return 4;
        return 0; 
    }
    
    private int foo3() {
        if (SomeClass.test(true) == true) if (true) return 4; //Warning, complex block
        return 0; 
    }
}

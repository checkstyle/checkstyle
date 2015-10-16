////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.whitespace;

/**
 * Test case for detecting operator wrapping.
 * @author Lars K�hne
 **/
class InputOpWrap
{
    void test()
    {
        int x = 1 +
            2 -
            3
            -
            4;
        x = x + 2;
        boolean y = true
            &&
            false;
        y = true &&
            false;
        y = false
            && true;
    }
    
    void testAssignment()
    {
        int x
            = 0; //error when checking assignment operators with EOL wrap option
        int y =
            0;
    }
    
    <
        T extends Comparable &
        java.io.Serializable
    >
    void testGenerics1()
    {
        Comparable
            <
            String
            >
            c = new String(); 
    }
}

class badCase2<T extends Foo &
    Bar> {
}

class goodCase<T extends Foo & Bar> {
}

class Switch {
    public void test(int i, int j) {
        switch(j) {
        case 7:
            return;
        }
        switch(i) {
        case 1:
            break;
        default:
            ;
        }
        for (int k : new int[]{1,2,3}) {}
    }
}

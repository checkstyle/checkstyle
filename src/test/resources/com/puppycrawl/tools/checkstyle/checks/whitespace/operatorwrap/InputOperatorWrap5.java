/*
OperatorWrap
option = EOL
tokens = ASSIGN


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.util.Arrays;

/**
 * Test case for detecting operator wrapping.
 * @author Lars K�hne
 **/
class InputOperatorWrap5
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
        Arrays.sort(null, String
                    ::
                    compareToIgnoreCase);
        Arrays.sort(null, String::
                    compareToIgnoreCase);
        Arrays.sort(null, String
                    ::compareToIgnoreCase);
    }

    void testAssignment()
    {
        int x
            = 0; // violation ''=' should be on the previous line.'
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

class badCase25<T extends Foo5 &
    Bar5> {
}

class goodCase5<T extends Foo5 & Bar5> {
}

class Switch5 {
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
interface Foo5 {}
interface Bar5 {}

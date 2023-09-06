/*
OperatorWrap
option = EOL
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.operatorwrap;

import java.util.Arrays;

/**
 * Test case for detecting operator wrapping.
 * @author Lars K�hne
 **/
class InputOperatorWrap2
{
    void test()
    {
        int x = 1 +
            2 -
            3
            - // violation ''-' should be on the previous line.'
            4;
        x = x + 2;
        boolean y = true
            && // violation ''&&' should be on the previous line.'
            false;
        y = true &&
            false;
        y = false
            && true; // violation ''&&' should be on the previous line.'
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
            = 0; //violation when checking assignment operators with EOL wrap option
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

class badCase22<T extends Foo2 &
    Bar2> {
}

class goodCase2<T extends Foo2 & Bar2> {
}

class Switch2 {
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

interface Foo2 {}
interface Bar2 {}

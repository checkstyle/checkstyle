/*
OperatorWrap
option = (default)nl
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
class InputOperatorWrap1
{
    void test()
    {
        int x = 1 + // violation ''\+' should be on a new line.'
            2 - // violation ''-' should be on a new line.'
            3
            -
            4;
        x = x + 2;
        boolean y = true
            &&
            false;
        y = true && // violation ''&&' should be on a new line.'
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
            = 0; //violation when checking assignment operators with EOL wrap option
        int y =
            0;
    }

    <
        T extends Comparable & // violation ''&' should be on a new line.'
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

class badCase21<T extends Foo & // violation ''&' should be on a new line.'
    Bar> {
}

class goodCase1<T extends Foo & Bar> {
}

class Switch1 {
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
interface Foo {}
interface Bar {}

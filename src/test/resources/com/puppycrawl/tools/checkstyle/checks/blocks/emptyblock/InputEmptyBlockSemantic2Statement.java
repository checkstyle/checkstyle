/*
EmptyBlock
option = (default)STATEMENT
tokens = LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_DO, LITERAL_IF, LITERAL_ELSE, \
         INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

class InputEmptyBlockSemantic2Statement
{
    public void fooMethod()
    {
        int a = 1;
        if (a == 1) {}  // violation 'Must have at least one statement'
        char[] s = {'1', '2'};
        int index = 2;
        if (doSideEffect() == 1) {} // violation 'Must have at least one statement'
        while ((a = index - 1) != 0) {} // ok
        for (; index < s.length && s[index] != 'x'; index++) {} // ok
        if (a == 1) {} else {a++;}// violation 'Must have at least one statement'
        switch (a) {}   // violation 'Must have at least one statement'
        switch (a) {    // ok
            case 1:
                a = 2;
            case 2:
                a = 3;
            default:
                a = 0;
        }
    }

    public int doSideEffect()
    {
        return 1;
    }
}

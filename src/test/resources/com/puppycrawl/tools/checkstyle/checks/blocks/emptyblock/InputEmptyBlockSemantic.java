/*
EmptyBlock
option = (default)statement
tokens = (default)LITERAL_WHILE, LITERAL_TRY, LITERAL_FINALLY, LITERAL_DO, \
         LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

/**
 * Test case for detecting empty block statements.
**/
class InputEmptyBlockSemantic
{
    static {
        Boolean x = new Boolean(true);
    }

    {
        Boolean x = new Boolean(true);
        Boolean[] y = new Boolean[]{Boolean.TRUE, Boolean.FALSE};
    }

    Boolean getBoolean()
    {
        return new java.lang.Boolean(true);
    }

    void exHandlerTest()
    {
        try {   // violation 'Must have at least one statement.'
        }
        finally {   // violation 'Must have at least one statement'
        }
        try {   // violation 'Must have at least one statement'
        // something
        }
        finally {   // violation 'Must have at least one statement'
            // something
        }
        try {   // ok
            ; // something
        }
        finally {   // ok
            ; // statement
        }
    }

    /** test **/
    private static final long IGNORE = 666l + 666L;

    public class EqualsVsHashCode1
    {
        public boolean equals(int a)
        {
            return a == 1;
        }
    }

    // empty instance initializer
    {   // violation 'Must have at least one statement'
    }

    private class InputBraces {

    }

    synchronized void foo() {
        synchronized (this) {}  // violation 'Must have at least one statement'
        synchronized (Class.class) { // ok
            synchronized (new Object()) {   // violation 'Must have at least one statement'
                // text
            }
        }
    }


    static {

    int a = 0;}

    static {    // violation 'Must have at least one statement'

    }
}

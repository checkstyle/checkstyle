package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

/* Config:
 * option = "text"
 * tokens = "LITERAL_WHILE, LITERAL_TRY, LITERAL_FINALLY, LITERAL_DO, LITERAL_IF, LITERAL_ELSE,
 *           LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, LITERAL_SWITCH, LITERAL_SYNCHRONIZED"
 */
/**
 * Test case for detecting empty block statements.
 **/
class InputEmptyBlockSemanticText
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
        try {
        }
        finally {
        }
        try {   // ok
            // something
        }
        finally {   // ok
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
    {
    }

    private class InputBraces {

    }

    synchronized void foo() {
        synchronized (this) {}  // violation
        synchronized (Class.class) { // OK
            synchronized (new Object()) {   // ok
                // text
            }
        }
    }

    static {

        int a = 0;}

    static {

    }
}

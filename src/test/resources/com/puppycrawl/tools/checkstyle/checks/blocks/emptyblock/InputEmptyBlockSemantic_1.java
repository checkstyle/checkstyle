////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

/* Config: default */

/**
 * Test case for detecting empty block statements.
 * @author Lars Kühne
 **/
class InputEmptyBlockSemantic_1
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
        try { // violation
        }
        finally { // violation
        }
        try {  // violation
        // something
        }
        finally { // violation
            // something
        }
        try {
            ;
        }
        finally {
            ;
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

    /* empty instance initializer*/
    { // violation
    }

    private class InputBraces {

    }

    synchronized void foo() {
        synchronized (this) {} // violation
        synchronized (Class.class) { // ok
            synchronized (new Object()) { // violation

            }
        }
    }


    static {

    int a = 0;}

    static { // violation

    }
}

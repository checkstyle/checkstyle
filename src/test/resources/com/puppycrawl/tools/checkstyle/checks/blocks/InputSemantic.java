////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.io.*; // star import for instantiation tests
import java.awt.Dimension; // explicit import for instantiation tests
import java.awt.Color;

/**
 * Test case for detecting empty block statements.
 * @author Lars Kühne
 **/
class InputSemantic
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
        try {
        // something
        }
        finally {
            // something
        }
        try {
            ; // something
        }
        finally {
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
        synchronized (this) {} // not OK
        synchronized (Class.class) { // OK
            synchronized (new Object()) {
                // not OK if checking statements
            }
        }
    }
    
    
    static {
       
    int a = 0;}
    
    static {
        
    }
}

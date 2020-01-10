package com.puppycrawl.tools.checkstyle.checks.coding.avoiddoublebraceinitialization;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Config = default
 */
public class InputAvoidDoubleBraceInitialization {
    void m() {
        ArrayList<?> list = new ArrayList<Object>() { // violation
            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation
            {
                add(null);
            }

            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation
            // some comment
            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation
            {
                add(null);
            } // some comment
        };
        list = new ArrayList<Object>() { // violation
            {
                add(null);
            }
            // some comment
        };
        list = new ArrayList<Object>() { // violation
            /* comment */ {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation
            {
                add(null);
            }
            /* comment */
        };
        list = new ArrayList<Object>() { // violation
            {
                add(null);
            } /* comment */
        };
        list = new ArrayList<Object>() {
            ;
            ;
            {}
            ;
            ;
            ;
        };
        Object obj = new Serializable() {  // violation
            {}
        };
        new ArrayList<Object>() {{ // violation
                add("1");
                add("2");
        }};
        new ArrayList<Object>() {{ add("1");}}; // violation
        new ArrayList<Object>() {{ add("1");}{ add("2");}}; // violation
        list = new ArrayList<Object>() {
            {
                add(null);
            }

            public void foo() {
            }
        };
        list = new ArrayList<Object>() {
            private Object o;

            {
                add(null);
            }
        };
        list = new ArrayList<Object>() {
            private Object o;
        };
        list = new ArrayList<Object>() {
            ;
        };
    }

    {
    }

    enum AA {
        ;
        {}
    }

    class Inner {
        {}
    }
}

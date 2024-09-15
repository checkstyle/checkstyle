/*
AvoidDoubleBraceInitialization


*/

package com.puppycrawl.tools.checkstyle.checks.coding.avoiddoublebraceinitialization;

import java.io.Serializable;
import java.util.ArrayList;

public class InputAvoidDoubleBraceInitialization {
    void m() {
        // violation below 'Avoid double brace initialization.'
        ArrayList<?> list = new ArrayList<Object>() {
            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            {
                add(null);
            }

            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            // some comment
            {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            {
                add(null);
            } // some comment
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            {
                add(null);
            }
            // some comment
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            /* comment */ {
                add(null);
            }
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            {
                add(null);
            }
            /* comment */
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            {
                add(null);
            } /* comment */
        };
        list = new ArrayList<Object>() { // violation 'Avoid double brace initialization.'
            ;
            ;
            {}
            ;
            ;
            ;
        };
        Object obj = new Serializable() { // violation 'Avoid double brace initialization.'
            {}
        };
        new ArrayList<Object>() {{ // violation 'Avoid double brace initialization.'
                add("1");
                add("2");
        }};
        new ArrayList<Object>() {{ add("1");}}; // violation 'Avoid double brace initialization.'
        // violation below 'Avoid double brace initialization.'
        new ArrayList<Object>() {{ add("1");}{ add("2");}};
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

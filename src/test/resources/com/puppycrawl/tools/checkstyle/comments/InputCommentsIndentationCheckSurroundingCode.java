// comment
package com.puppycrawl.tools.checkstyle.comments;

import java.util.*;

// some
public class InputCommentsIndentationCheckSurroundingCode
{
    private void foo() {
        if (true) {
            // here initialize some variables
            int k = 0; // trailing comment
              // initialize b
            int b = 10;
            // sss
        }
    }

    private void foo1() {
        if (true) {
            /* some */
            int k = 0;
                /* some */
            int b = 10;
                /*
                 * */
            double d; /* trailing comment */
                /*
             * 
                */
            boolean bb;
            /***/
            /* my comment*/
            /*
             * 
             * 
             *  some
             */
            /*
             * comment
             */
            boolean x;
        }
    }
    
    private void foo3() {
        int a = 5, b = 3, v = 6;
        if (a == b
                && v == b || ( a ==1
                           /// What about that case ? the same for block comments
                       /*
                        * one fine day ... */
                                    && b == 1)   ) {
              // code
           }
    }
    
    // Comments here should be ok by Check
    @SuppressWarnings("unused") // trailing
    private static void check () { // trailing
        if (true) // trailing comment
        {
            // some comment
        }
        if (true) { // trailing comment

        }
        /**
         * 
         */
    }
    
    private static void com() {
        /* here's my weird trailing comment */ boolean b = true;
    }


    private static void cases() {
        switch("") {
            case "0": //some comment
            case "1":
                // my comment
                com();
                break;
            case "2":
            // my comment
                //comment
                check();
                // comment
                break;
            case "3":
            /* com */
            check();
                /* com */
                break;
            case "5":
                check();
                // fall through
            case "6":
                int k = 7;
                // fall through
            case "7":
                if (true) {}
                // fall through
            case "8":
                break;
            default:
                // comment
                break;
        }
    }

    private static final String[][] mergeMatrix = {
        // TOP ALWAYS NEVER UNKNOWN
        /* TOP */{ "", },
        /* ALWAYS */{ "", "", },
       /* NEVER */{ "NEVER", "UNKNOWN", "NEVER", },
       /* UNKNOWN */{ "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN" }, };

    private void foo4() {
        if (!Arrays.equals(new String[]{""}, new String[]{""})
              /* wierd trailing comment */) {
        }
    }
    /**
     * some javadoc
     */
    private static void l() {}
    
    private void foo32() {
        if (true) {
            switch(1) {
            case 0:
                
            case 1:
                    // initialize b
                int b = 10;
            }
            
        }
    }
} // The Check should not throw NPE here!

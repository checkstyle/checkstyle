package com.google.checkstyle.test.chapter4formatting.rule4861blockcommentstyle;

public class InputCommentsIndentationInEmptyBlock {

    private void foo1() {
        int a = 5, b = 3, v = 6;
        if (a == b
            && v == b || ( a ==1
                   /// // warn
                       /* // warn
                        * one fine day ... */
                               && b == 1)   ) {
            // Cannot clearly detect user intention of explanation target.
        }
    }

    private void foo2() {
        int a = 5, b = 3, v = 6;
        if (a == b
            && v == b || ( a ==1
            && b == 1)   ) {


             // comment
        }
    }

    private void foo3() {
        int a = 5, b = 3, v = 6;
        if (a == b
            && v == b || (a == 1
            && b == 1)) {
// warn
        }
    }

    // Comments here should be ok by Check
    @SuppressWarnings("unused") // trailing
    private static void foo4() { // trailing
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

    // Comments here should be ok by Check
    @SuppressWarnings("unused") // trailing
    private static void foo5() { // trailing
        if (true) // trailing comment
        {
// warn
        }
        if (true) { // trailing comment

        }
        /**
         *
         */
    }

    public void foo6() {
        try {

        } catch (Exception e) {
// warn
        }
    }

    public void foo7() {
        try {

        } catch (Exception e) {
            // OOOO: handle exception here
        }
    }

    private static class MyClass extends Object {
           // no members
    }
}

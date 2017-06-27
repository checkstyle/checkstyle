package com.puppycrawl.tools.checkstyle.checks.indentation.commentsindentation;

public class InputCommentsIndentationInEmptyBlock {

    private void foo1() {
        int a = 5, b = 3, v = 6;
        if (a == b
            && v == b || ( a ==1
                   /// violation
                       /* violation
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
// violation
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
// violation
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
// violation
        }
    }

    public void foo7() {
        try {

        } catch (Exception e) {
            // TODO: handle exception here
        }
    }

    private static class MyClass extends Object {
           // no members
    }

    private static class MyClass1 extends Object {

           // no members
    }

    public void foo8() {
        String[] array1 = {
                // comment
        };
        String[] array2 = {
                    // comment
        };
        String[] array3 = {
        // comment
        };
        String[] array4 = {
// violation
        };
        String[] array5 = {

// violation
        };
    }
}

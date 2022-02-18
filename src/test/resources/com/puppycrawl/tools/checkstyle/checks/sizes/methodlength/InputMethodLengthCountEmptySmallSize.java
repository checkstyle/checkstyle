/*
MethodLength
max = 2
countEmpty = false
tokens = (default)METHOD_DEF, CTOR_DEF, COMPACT_CTOR_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodlength;

public class InputMethodLengthCountEmptySmallSize {}
class AA {
    AA() { // ok, 2 lines

    }

    AA(int a) { // ok, 2 lines
        /*
        my comment
         */
        // other comment
    }

    AA(String a) {  // violation 'Method .* length is 3 lines (max allowed is 2).'
        oneLine();
    }

    void twoLines() { // ok

    }

    void twoLines2() { // ok
        oneLine();}

    void twoLinesAndComment() { // ok
        // some comment
    }

    void oneLine() { oneLine();/* ok */ }

    void threeLines() { // violation 'Method threeLines length is 3 lines (max allowed is 2).'
        oneLine();
    }

    void threeLinesAndComments() { // violation 'Method .* length is 3 lines (max allowed is 2).'
        // comment above
        oneLine();
        /*
        block comment below
         */
    }

    void threeLinesWrap() // violation 'Method .* length is 3 lines (max allowed is 2).'

    {
        // comment above
        oneLine();
        /*
        block comment below
         */
    }

    void m2() { // violation 'Method .* length is 10 lines (max allowed is 2).'
        String a = "1";
        a.concat("")
            .concat("")
            .concat("")
            .concat("")
            .concat("")
            .concat("")
            .concat("");
    }
}

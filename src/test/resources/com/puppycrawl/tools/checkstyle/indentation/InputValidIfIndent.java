/*
 * ValidIndent.java
 *
 * Created on November 6, 2002, 9:39 PM
 */

package com.puppycrawl.tools.checkstyle.indentation;

/**
 *
 * @author  jrichard
 */
public class InputValidIfIndent {


    // test ifs
    public void emptyIfTest()
    {
        boolean test = true;

        // lcurly on same line
        if (test) {
        }

        // lcurly on next line
        if (test)
        {
        }

        // lcurly for if and else on same line
        if (test) {
        } else {
        }

        // lcurly for if and else on same line
        if (test)
        {
        }
        else
        {
        }

        // lcurly for if and else on same line -- mixed braces
        if (test) {
        }
        else
        {
        }


        // lcurly for if and else on same line -- mixed braces
        if (test)
        {
        } else
        {
        }

        // lcurly for if and else on same line -- mixed braces
        if (test)
        {
        } else {
        }

        // lcurly for if and else on same line -- mixed braces, unnested
        if (test) {
        }
        else {
        }

        if (foo()
            || test
            || test)
        {
        }

    }

    /////  same as above, with statements
    public void  populatedIfTest()
    {
        boolean test = false;
        // no braces if
        if (test)
            System.getProperty("blah");

        // no braces if/else
        if (test)
            System.getProperty("blah");
        else
            System.getProperty("blah");


        // lcurly on same line, and stmt
        if (test) {
            System.getProperty("blah");
        }

        // lcurly on next line and stmt
        if (test)
        {
            System.getProperty("blah");
        }
        // lcurly for if and else on same line
        if (test) {
            System.getProperty("blah");
        } else {
            System.
                getProperty("blah");
        }

        // lcurly for if and else on same line
        if (test)
        {
            System.getProperty("blah");
            System.getProperty("blah");
        }
        else
        {
            System.getProperty("blah");
        }

        // lcurly for if and else on same line -- mixed braces
        if (test) {
            System.getProperty("blah");
        }
        else
        {
            System.getProperty("blah");
        }


        // lcurly for if and else on same line -- mixed braces
        if (test)
        {
            System.getProperty("blah");
        } else
        {
            System.getProperty("blah");
        }

        // lcurly for if and else on same line -- mixed braces
        if (test)
        {
            System.getProperty("blah");
        } else {
            System.getProperty("blah");
        }

        // lcurly for if and else on same line -- mixed braces, unnested
        if (test) {
            System.getProperty("blah");
        }
        else {
            System.getProperty("blah");
        }

        if (test) System.getProperty("blah");

        if (test) System.getProperty("blah");
        else System.getProperty("foo");

        if (test) System.getProperty("blah");
        else
            System.getProperty("foo");

        if (test)
            System.getProperty("blah");
        else System.getProperty("foo");

        if (test
            && 7 < 8 && 8 < 9
            && 10 < 11) {
        }

        if (test)
            return;


        if (test) {
        } else if (7 < 8) {
        } else if (8 < 9) {
        }

        if (test) {
            System.getProperty("blah");
        } else if (7 < 8) {
            System.getProperty("blah");
        } else if (8 < 9) {
            System.getProperty("blah");
        }


        if (test)
            System.getProperty("blah");
        else if (7 < 8)
            System.getProperty("blah");
        else if (8 < 9)
            System.getProperty("blah");


        // TODO: bother to support this style?
        if (test) {
            System.getProperty("blah");
        } else
            if (7 < 8) {
                System.getProperty("blah");
            } else
                if (8 < 9) {
                    System.getProperty("blah");
                }

    }

    public void  parenIfTest() {
        boolean test = true;

        if (test
        ) {
            System.getProperty("blah");
        }
//
        if (test
        )
        {
            System.getProperty("blah");
        }

        if
        (
            test
        ) {
            System.getProperty("blah");
        }

        if (test
            )
        {
        }
    }

    boolean foo() {
        return true;
    }
}

class FooFoo {
    void foo42() {
        if (test) {
            System.getProperty("blah");
        } else if (7 < 8
            && 8 < 9) {
            System.getProperty("blah");
        } else if (8 < 9) {
            System.getProperty("blah");
        }
    }
}

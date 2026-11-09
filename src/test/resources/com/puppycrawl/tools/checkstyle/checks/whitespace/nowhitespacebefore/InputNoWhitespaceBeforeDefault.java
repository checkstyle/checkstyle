/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

package com . puppycrawl
        .tools.
        checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeDefault
{
    void method1()
    {
        int b= 1; // Ignore 1
        b = b ++ + b --;
        // 2 violations above:
        //    ''\+\+' is preceded with whitespace.'
        //    ''\-\-' is preceded with whitespace.'
    }

    void rfe521323()
    {
        doStuff() ; // violation '';' is preceded with whitespace.'
        //       ^ whitespace
        for (int i = 0 ; i < 5; i++) { // violation '';' is preceded with whitespace.'
            //        ^ whitespace
        }
    }

    private int i ; // violation '';' is preceded with whitespace.'
    //           ^ whitespace
    private int i1, i2, i3 ; // violation '';' is preceded with whitespace.'
    //                    ^ whitespace
    private int i4, i5, i6;

    void bug806243()
    {
        Object o = new InputNoWhitespaceBeforeDefault() {
            private int j ; // violation '';' is preceded with whitespace.'
            //           ^ whitespace
        };
    }

    void doStuff() {
    }
}

interface IFoo_NoWhitespaceBeforeDefault
{
    void foo() ; // violation '';' is preceded with whitespace.'
    //        ^ whitespace
}

class SpecialCasesInForLoop_NoWhitespaceBeforeDefault
{
    void forIterator()
    {
        // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
        for (int i = 0; i++ < 5;) {
            //                  ^ no whitespace
        }

        // avoid conflict between ParenPad(space) and NoWhiteSpace before ';'
        int i = 0;
        for ( ; i < 5; i++ ) {
            //   ^ whitespace
        }
        for (int anInt : getSomeInts()) {
            //Should be ignored
        }
    }

    int[] getSomeInts() {
        return null;
    }

    public void mySuperMethod() {
        Runnable[] runs = new Runnable[] {new Runnable() {
                public void run() {
                }
            },
            new Runnable() {
                public void run() {
                }
            }};
        runs[0]
.
 run()
; // violation '';' is preceded with whitespace.'
    }

    public void testNullSemi() {
        return ; // violation '';' is preceded with whitespace.'
    }

    public void parentheses() {
        testNullSemi
(
)
; // violation '';' is preceded with whitespace.'
    }

    public static void testNoWhitespaceBeforeEllipses(String ... args) {
        // violation above ''...' is preceded with whitespace.'
    }

    {
        label1 : // violation '':' is preceded with whitespace.'
        for(int i = 0; i < 10; i++) {}
    }

    public void foo() {
        label2:
        while (true) {}
    }
}

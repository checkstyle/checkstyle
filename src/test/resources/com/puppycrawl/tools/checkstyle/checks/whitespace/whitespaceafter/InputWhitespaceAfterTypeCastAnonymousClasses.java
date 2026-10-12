/*
WhitespaceAfter
tokens = TYPECAST


*/

package com . puppycrawl
    .tools.
    checkstyle.checks.whitespace.whitespaceafter;

class InputWhitespaceAfterTypeCastAnonymousClasses
{
    /** another check */
    void donBradman(Runnable aRun)
    {
        donBradman(new Runnable() {
            public void run() {
            }
        });

        final Runnable r = new Runnable() {
            public void run() {
            }
        };
    }

    /** rfe 521323, detect whitespace before ';' */
    void rfe521323()
    {
        doStuff() ;
        //       ^ whitespace
        for (int i = 0 ; i < 5; i++) {
            //        ^ whitespace
        }
    }


    /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    private int i ;
    //           ^ whitespace
    private int i1, i2, i3 ;
    //                    ^ whitespace
    private int i4, i5, i6;

    /** bug 806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    void bug806243()
    {
        Object o = new InputWhitespaceAfterTypeCastAnonymousClasses() {
            private int j ;
            //           ^ whitespace
        };
    }

    void doStuff() {
    }
}


/**
 * Bug 806242 (NoWhitespaceBeforeCheck violation with an interface).
 * @author o_sukhodolsky
 * @version 1.0
 */
interface IFoo
{
    void foo() ;
    //        ^ whitespace
}

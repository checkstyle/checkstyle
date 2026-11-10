/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = TYPECAST


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

class InputNoWhitespaceAfterTestTypecast2
{
    /** boolean test **/
    private void boolTest()
    {
        boolean a = true;
        boolean x = ! a;
        int z = ~1 + ~ 2;
    }

    /** division test **/
    private void divTest()
    {
        int a = 4 % 2;
        int b = 4% 2;
        int c = 4 %2;
        int d = 4%2;
        int e = 4 / 2;
        int f = 4/ 2;
        int g = 4 /2;
        int h = 4/2;
    }

    /** @return dot test **/
    private java .lang.  String dotTest()
    {
        Object o = new java.lang.Object();
        o.
            toString();
        o
            .toString();
        o . toString();
        return o.toString();
    }

    /** assert statement test */
    public void assertTest()
    {

        assert true;


        assert true : "Whups";

        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";

        // missing WS around assert
        assert(true);

        // missing WS around colon
        assert true:"Whups";
    }

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
        Object o = new InputNoWhitespaceAfterTestTypecast() {
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
interface IFooTestTypecast
{
    void foo() ;
    //        ^ whitespace
}

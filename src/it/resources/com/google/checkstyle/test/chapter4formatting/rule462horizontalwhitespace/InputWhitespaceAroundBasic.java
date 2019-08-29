package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/**
 * Class for testing whitespace issues.
 * violation missing author tag
 **/
class InputWhitespaceAroundBasic
{
    /** warn **/
    private int mVar1= 1; // warn
    /** warn **/
    private int mVar2 =1; // warn
    /** Should be ok **/
    private int mVar3 = 1;

    /** method **/
    void method1()
    {
        final int a = 1;
        int b= 1; // warn
        b= 1; // warn
        b +=1; // warn
        b -=- 1 + (+ b); // warn
        b = b ++ + b --; // ok
        b = ++ b - -- b; // ok
    }

    /** method **/
    void method2()
    {
        synchronized(this) { //warn
        }
        try {//warn
        }
        catch (RuntimeException e) {//warn
        }
    }

    /**
       skip blank lines between comment and code,
       should be ok
    **/


    private int mVar4 = 1;


    /** test WS after void return */
    private void fastExit()
    {
        boolean complicatedStuffNeeded = true;
        if( !complicatedStuffNeeded) //warn
        {
            return; // should not complain about missing WS after return
        }
        else
        {
            // do complicated stuff
        }
    }


    /** test WS after non void return
     @return 2
    */
    private int nonVoid()
    {
        if ( true )
        {
            return(2); // //warn
        }
        else
        {
            return 2; // this is ok
        }
    }

    /** test casts **/
    private void testCasts()
    {
        Object o = (Object) new Object(); // ok
        o = (Object)o; // ok
        o = ( Object ) o; // ok
        o = (Object)
            o; // ok
    }

    /** test questions **/
    private void testQuestions()
    {

        boolean b = (1 ==2) ? false : true; //warn
    }

    /** star test **/
    private void starTest()
    {
        int x = 2 * 3* 4; //warn
    }

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
        int b = 4% 2;//warn
        int c = 4 %2;//warn
        int d = 4% 2;//warn
        int e = 4 / 2;
        int f = 4/ 2;//warn
        int g = 4 /2;//warn

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
        // OK
        assert true;

        // OK
        assert true : "Whups";

        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";

        // missing WS around assert
        assert(true);//warn

        // missing WS around colon
        assert true: "Whups";//warn
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


    /** bug  806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    private int i ;
    //           ^ whitespace
    private int i1, i2, i3 ;
    //                    ^ whitespace
    private int i4, i5, i6;

    /** bug  806243 (NoWhitespaceBeforeCheck violation for anonymous inner class) */
    void bug806243()
    {
        Object o = new InputWhitespaceAroundBasic() {
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

/**
 * Avoid Whitespace violations in for loop.
 * @author lkuehne
 * @version 1.0
 */
class SpecialCasesInForLoop
{
    void forIterator()
    {
        // avoid conflict between WhiteSpaceAfter ';' and ParenPad(nospace)
        for (int i = 0; i++ < 5;) {
        //                  ^ no whitespace
    }

        // bug 895072
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
        int i = (int) ( 2 / 3 );
        return null;
    }
}

/**
 * Operators mentioned in Google Coding Standards 2016-07-12
 */
class NewGoogleOperators
{
    NewGoogleOperators()
    {
       Runnable l;

       l = ()-> { }; //warn
       l = () ->{ }; //warn
       l = () -> { }; //ok
       l = () -> {}; //ok

       java.util.Arrays.sort(null, String :: compareToIgnoreCase);
       java.util.Arrays.sort(null, String::compareToIgnoreCase);

       new Object().toString();
       new Object() . toString();
    }
}

package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionKeywordsAndOperators3 {
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
        Object o = new InputAntlr4AstRegressionKeywordsAndOperators() {
            private int j ;
            //           ^ whitespace
        };
    }

    void doStuff() {
    }
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

    public void myMethod() {
        new Thread() {
            public void run() {
            }
        }.start();
    }

    public void foo(java.util.List<? extends String[]> bar, Comparable<? super Object[]> baz) { }

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
;
    }

    public void testNullSemi() {
        return ;
    }

    public void register(Object obj) { }

    public void doSomething(String args[]) {
        register(boolean[].class);
        register( args );
    }

    public void parentheses() {
        testNullSemi
(
)
;
    }

    public static void testNoWhitespaceBeforeEllipses(String ... args) {
    }
    public String test() {
        int pc = 0;
        return ((100000+pc)+"").substring(1);
    }
}

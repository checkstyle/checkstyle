/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, \
         TYPECAST, ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

/**
 * Avoid Whitespace violations in for loop.
 * @author lkuehne
 * @version 1.0
 */
class InputNoWhitespaceAfterTestAllTokens3
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
        int i = (int) ( 2 / 3 ); // violation '')' is followed by whitespace.'
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
. // violation ''.' is followed by whitespace.'
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
    @ interface BAD{} // violation ''@' is followed by whitespace.'
    @   interface BAD2{} // violation ''@' is followed by whitespace.'
    @ // violation ''@' is followed by whitespace.'
    interface BAD3{}
    @interface Ok{}

    static {
        int[]err = new int [50]; // violation ''int' is followed by whitespace.'
    }

    Object foo() {
        return ( (Object // violation '')' is followed by whitespace.'
                ) "");
    }

    public Object[]
        variable;

    int someStuff8
    []; // violation ''someStuff8' is followed by whitespace.'
}

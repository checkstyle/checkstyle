package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionKeywordsAndOperators2 {
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

/*
NoWhitespaceBefore
allowLineBreaks = yes
tokens = DOT


*/

package com . puppycrawl // violation ''.' is preceded with whitespace.'
    .tools.
    checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeDotAllowLineBreaks
{
    private java .lang.  String dotTest() // violation ''.' is preceded with whitespace.'
    {
        Object o = new java.lang.Object();
        o.
            toString();
        o
            .toString();
        o . toString(); // violation ''.' is preceded with whitespace.'
        return o.toString();
    }

    public void assertTest()
    {
        // evil colons, should be OK
        assert "OK".equals(null) ? false : true : "Whups";
    }
}

class SpecialCasesInForLoop_NoWhitespaceBeforeDotAllowLineBreaks
{
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

    public void register(Object obj) { }

    public void doSomething(String args[]) {
        register(boolean[].class);
        register( args );
    }
}

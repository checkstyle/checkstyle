/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.io.PrintStream;

public class InputNoWhitespaceAfterNewGenericTypeArgument {
    static interface SomeInterface {
        int SEVENS = 777;
    }

    static interface AnotherInterface {
        int THIRDS = 33;
    }

    static class SomeClass implements SomeInterface {
        int i;

        SomeClass(int i) {
            this.i = i;
        }
    }

    static class ImmediateSubclass extends SomeClass implements SomeInterface {
        float f;

        ImmediateSubclass(int i, float f) {
            super(i);
            this.f = f;
        }
    }

    static final class FinalSubclass extends ImmediateSubclass implements AnotherInterface {
        double d;

        FinalSubclass(int i, float f, double d) {
            super(i, f);
            this.d = d;
        }
    }

    static int errorStatus = 0/*STATUS_PASSED*/;

    static void errorAlert(PrintStream out, int errorLevel) {
        out.println("Test: failure #" + errorLevel);
        errorStatus = 2/*STATUS_FAILED*/;
    }

    static SomeClass[] v2 = new FinalSubclass[4];

    public static int run(String args[],PrintStream out) {
        int i [], j []; // violation 2x
        SomeInterface u []; // violation
        SomeInterface v[] [] = null; // violation
        AnotherInterface w []; // violation
        SomeClass x [] []; // violation 2x

        x = (SomeClass [] []) v; // violation 2x

        x[0] = (SomeClass []) new ImmediateSubclass [4]; // violation 2x
        if (! (x[0] instanceof ImmediateSubclass [])) // violation 2x
            errorAlert(out, 8);

        if (! (x[1] instanceof FinalSubclass []))  // violation 2x
            errorAlert(out, 10);

        w = (AnotherInterface []) x[1]; // violation

        return errorStatus;
    }
}

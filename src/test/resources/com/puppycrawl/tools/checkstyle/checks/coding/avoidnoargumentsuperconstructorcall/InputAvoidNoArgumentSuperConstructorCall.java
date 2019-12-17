package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

/*
 * Config = default
 */
public class InputAvoidNoArgumentSuperConstructorCall extends java.util.ArrayList {

    public InputAvoidNoArgumentSuperConstructorCall() {
        super(); // violation
    }

    public InputAvoidNoArgumentSuperConstructorCall(int a, long b) {
        super(/**/); // violation
    }

    public InputAvoidNoArgumentSuperConstructorCall(long a, long b) {
        super( // violation
        );
    }

    public InputAvoidNoArgumentSuperConstructorCall(int a) {
        super(10);
    }

    public InputAvoidNoArgumentSuperConstructorCall(int a, int b) {
        super(a);
    }
}

class WithTypeArgs {
    public WithTypeArgs() {
        <String> super();
    }
}

class Outer {

    class InnerNonStatic {
        public InnerNonStatic() {
        }
    }
}

class Derived extends Outer.InnerNonStatic {
    public Derived(Outer s) {
        s.super(); // Not a redundant call
    }
    public Derived() {
        new Outer().super(); // Not a redundant call
    }
}

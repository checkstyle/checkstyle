/*
AvoidNoArgumentSuperConstructorCall


*/

package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

public class InputAvoidNoArgumentSuperConstructorCall extends java.util.ArrayList {

    public InputAvoidNoArgumentSuperConstructorCall() {
        // violation below,  'Unnecessary call to superclass constructor with no arguments.'
        super();
    }

    public InputAvoidNoArgumentSuperConstructorCall(int a, long b) {
        // violation below,  'Unnecessary call to superclass constructor with no arguments.'
        super(/**/);
    }

    public InputAvoidNoArgumentSuperConstructorCall(long a, long b) {
        // violation below,  'Unnecessary call to superclass constructor with no arguments.'
        super(
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

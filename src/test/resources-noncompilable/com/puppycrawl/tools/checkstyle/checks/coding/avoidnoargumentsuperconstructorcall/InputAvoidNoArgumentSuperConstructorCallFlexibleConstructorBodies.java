/*
AvoidNoArgumentSuperConstructorCall


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.coding.avoidnoargumentsuperconstructorcall;

public class InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies extends SuperTest {

    public InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must be non-negative");
        }
        super();
    }

    public InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies(String s) {
        System.out.println(s);
        super();
    }

    public InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies(long val) {
        val = 10L;
        super();
    }

    public InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies() {
        // violation below 'Unnecessary call to superclass constructor with no arguments.'
        super();
    }

    public InputAvoidNoArgumentSuperConstructorCallFlexibleConstructorBodies(double d) {
        // comment before super call
        /* multi-line
           comment */
        // violation below 'Unnecessary call to superclass constructor with no arguments.'
        super();
    }
}

class SuperTest {
    public SuperTest() {
    }
}
